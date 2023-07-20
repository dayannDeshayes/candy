package com.simplon.candy.service;

import com.simplon.candy.entity.CandyboxEntity;
import com.simplon.candy.entity.CommandeEntity;
import com.simplon.candy.entity.CouleurEntity;
import com.simplon.candy.entity.ItemcandyboxEntity;
import com.simplon.candy.entity.model.OrderModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class TsubakiService extends AbstractProcessor {

    @Override
    public CommandeEntity filterOrder(OrderModel orderModel) {
        CommandeEntity commandeEntity = this.commandeRepository.save(this.mapper.toEntity(orderModel, utilisateurRepository, candyTagRepository, new CommandeEntity()));
        List<ItemcandyboxEntity> itemcandyboxEntities = new ArrayList<>();
        int quantity = orderModel.getQte();

        if (quantity <= 10 || quantity % 2 != 0) {
            throw new RuntimeException("Invalid quantity");
        }

        List<String> colors = Arrays.asList("rouge", "vert", "bleu", "jaune", "mauve");

        int candyBoxesToCreate = 5;
        int candyPerBox = quantity / candyBoxesToCreate;
        if (candyPerBox % 2 != 0) {
            throw new RuntimeException("Impossible de répartir équitablement les bonbons dans les boîtes et respecter la parité");
        }

        int colorsPerBox = candyPerBox > 2 ? 2 : 1;
        int candyPerColor = candyPerBox / colorsPerBox;
        if (candyPerColor % 2 != 0) {
            throw new RuntimeException("Impossible de répartir équitablement les bonbons dans les boîtes et respecter la parité");
        }


        for (int i = 0; i < candyBoxesToCreate; i++) {
            CandyboxEntity candybox = new CandyboxEntity(commandeEntity);
            commandeEntity.getCandyboxes().add(candybox);
            candybox = candyboxRepository.save(candybox);

            List<String> colorsUsed = new ArrayList<>();
            for (int j = 0; j < colorsPerBox; j++) {
                String color;
                do {
                    color = colors.get(new Random().nextInt(colors.size()));
                } while (colorsUsed.contains(color));
                colorsUsed.add(color);
                CouleurEntity couleur = this.couleurRepository.findByCouleur(color);
                ItemcandyboxEntity item = new ItemcandyboxEntity(candybox, couleur, candyPerColor);
                itemcandyboxEntities.add(item);
                candybox.getItemCandyboxes().add(item);
            }
        }

        this.commandeRepository.save(commandeEntity);
        this.itemcandyboxRepository.saveAll(itemcandyboxEntities);

        return commandeEntity;
    }
}
