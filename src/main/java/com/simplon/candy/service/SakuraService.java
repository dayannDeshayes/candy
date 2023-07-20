package com.simplon.candy.service;

import com.simplon.candy.entity.CandyboxEntity;
import com.simplon.candy.entity.CommandeEntity;
import com.simplon.candy.entity.CouleurEntity;
import com.simplon.candy.entity.ItemcandyboxEntity;
import com.simplon.candy.entity.model.OrderModel;
import com.simplon.candy.service.IService.IProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
@Component
@RequiredArgsConstructor
public class SakuraService extends AbstractProcessor implements IProcessor {

    @Override
    public CommandeEntity filterOrder(OrderModel orderModel) {
        CommandeEntity commandeEntity = this.commandeRepository.save(this.mapper.toEntity(orderModel, utilisateurRepository, candyTagRepository, new CommandeEntity()));
        List<ItemcandyboxEntity> itemcandyboxEntities = new ArrayList<>();
        int quantity = orderModel.getQte();
        if (quantity < 10) {
            itemcandyboxEntities.addAll(generateItems(commandeEntity, "rouge", 5));
            itemcandyboxEntities.addAll(generateItems(commandeEntity, "bleu", quantity - 5));
        } else if (quantity <= 150) {
            itemcandyboxEntities.addAll(generateItems(commandeEntity, "vert", 10));
            int rest = Math.min(quantity - 10, 50);
            itemcandyboxEntities.addAll(generateItems(commandeEntity, "blanc", rest));
            if (quantity > 60) {
                itemcandyboxEntities.addAll(generateItems(commandeEntity, "jaune", quantity - 60));
            }
        } else {
            int randomRed = new Random().nextInt(quantity / 3);
            int randomCyan = randomRed + new Random().nextInt((quantity - randomRed) / 2);
            int randomPurple = randomCyan + (quantity - randomCyan);

            itemcandyboxEntities.addAll(generateItems(commandeEntity, "rouge", randomRed));
            itemcandyboxEntities.addAll(generateItems(commandeEntity, "cyan", randomCyan - randomRed));
            itemcandyboxEntities.addAll(generateItems(commandeEntity, "mauve", randomPurple - randomCyan));
        }

        this.itemcandyboxRepository.saveAll(itemcandyboxEntities);

        return commandeEntity;
    }

    private List<ItemcandyboxEntity> generateItems(CommandeEntity commande, String colorName, int quantity) {
        CouleurEntity couleur = this.couleurRepository.findByCouleur(colorName);
        List<ItemcandyboxEntity> items = new ArrayList<>();
        final int CANDY_BOX_SIZE = 20;
        int remainingQuantity = quantity;
        while (remainingQuantity > 0) {
            int currentQuantity = Math.min(remainingQuantity, CANDY_BOX_SIZE);
            CandyboxEntity candybox = candyboxRepository.save(new CandyboxEntity(commande));
            ItemcandyboxEntity item = new ItemcandyboxEntity(candybox, couleur, currentQuantity);
            items.add(item);
            candybox.getItemCandyboxes().add(item);
            commande.getCandyboxes().add(candybox);
            remainingQuantity -= currentQuantity;
        }
        return items;
    }


}
