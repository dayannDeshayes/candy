package com.simplon.candy.service;

import com.simplon.candy.entity.*;
import com.simplon.candy.entity.enums.CandytagEnum;
import com.simplon.candy.entity.enums.StatusEnum;
import com.simplon.candy.entity.model.OrderModel;
import com.simplon.candy.entity.model.OrderOutputModel;
import com.simplon.candy.mapper.Mapper;
import com.simplon.candy.repository.*;
import com.simplon.candy.service.IService.ICandyOrderService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class CandyOrderService implements ICandyOrderService {

    private final Mapper mapper;

    private final CouleurRepository couleurRepository;

    private final CommandeRepository commandeRepository;

    private final ItemCandyBoxRepository itemcandyboxRepository;

    private final CandyboxRepository candyboxRepository;

    private final CandytagRepository candyTagRepository;

    private final UtilisateurRepository utilisateurRepository;

    private final int CANDY_BOX_SIZE = 20;

    @PostConstruct
    public void init() {
        saveColorIfNotExists("red");
        saveColorIfNotExists("blue");
        saveColorIfNotExists("green");
        saveColorIfNotExists("white");
        saveColorIfNotExists("yellow");
        saveColorIfNotExists("cyan");
        saveColorIfNotExists("purple");

        for (CandytagEnum candytagEnum : CandytagEnum.values()) {
            Logger logger = Logger.getLogger("CandyOrderService");
            logger.info("CandytagEnum: " + candytagEnum);
            if (!candyTagRepository.existsByCandytag(candytagEnum)) {
                CandytagEntity candytagEntity = new CandytagEntity();
                candytagEntity.setCandytag(candytagEnum);
                candyTagRepository.save(candytagEntity);
            }
        }

        UtilisateurEntity user = new UtilisateurEntity().builder()
                .nom("User")
                .build();

        this.utilisateurRepository.save(user);
    }

    private void saveColorIfNotExists(String colorName) {
        if (!this.couleurRepository.existsByCouleur(colorName)) {
            CouleurEntity color = new CouleurEntity();
            color.setCouleur(colorName);
            this.couleurRepository.save(color);
        }
    }

    @Override
    public OrderOutputModel processOrder(OrderModel orderModel) {
        final OrderOutputModel orderOutputModel = new OrderOutputModel();
        switch (orderModel.getCandytag()) {
            case SAKURA -> this.sakuraAlgo(orderModel);
            case TSUBAKI -> this.tsubakiAlgo(orderModel);
            case MOMO -> this.momoAlgo(orderModel);
            case ASAGAO -> this.asagaoAlgo(orderModel);
            case KIKU -> this.kikuAlgo(orderModel);
            default -> orderOutputModel.setMessage("Candytag not found");
        }

        if (orderOutputModel.getMessage() != null) {
            orderOutputModel.setStatus(StatusEnum.ERREUR);
            return orderOutputModel;
        }

        return orderOutputModel;
    }
    private void sakuraAlgo(OrderModel orderModel) {
        CommandeEntity commandeEntity = this.commandeRepository.save(this.mapper.toEntity(orderModel,utilisateurRepository,candyTagRepository,new CommandeEntity()));
        List<ItemcandyboxEntity> itemcandyboxEntities = new ArrayList<>();
        int quantity = orderModel.getQte();
        if (quantity < 10) {
            CouleurEntity red = this.couleurRepository.findByCouleur("red");
            CouleurEntity blue = this.couleurRepository.findByCouleur("blue");

            itemcandyboxEntities.addAll(generateItems(commandeEntity, red, 5));
            itemcandyboxEntities.addAll(generateItems(commandeEntity, blue, quantity - 5));
        } /*else if (quantity <= 150) {
            itemcandyboxEntities.addAll(generateItems(commandeEntity, green, 10));
            int rest = Math.min(quantity - 10, 50);
            itemcandyboxEntities.addAll(generateItems(commandeEntity, white, rest));
            if (quantity > 60) {
                itemcandyboxEntities.addAll(generateItems(commandeEntity, yellow, quantity - 60));
            }
        } else {
            int randomRed = new Random().nextInt(quantity / 3);
            int randomCyan = randomRed + new Random().nextInt((quantity - randomRed) / 2);
            int randomPurple = randomCyan + (quantity - randomCyan);
            itemcandyboxEntities.addAll(generateItems(commandeEntity, red, randomRed));
            itemcandyboxEntities.addAll(generateItems(commandeEntity, cyan, randomCyan - randomRed));
            itemcandyboxEntities.addAll(generateItems(commandeEntity, purple, randomPurple - randomCyan));*/

        this.itemcandyboxRepository.saveAll(itemcandyboxEntities);
    }

    private List<ItemcandyboxEntity> generateItems(CommandeEntity commande, CouleurEntity couleur, int quantity) {
        List<ItemcandyboxEntity> items = new ArrayList<>();
        int remainingQuantity = quantity;
        while (remainingQuantity > 0) {
            int currentQuantity = Math.min(remainingQuantity, CANDY_BOX_SIZE);
            CandyboxEntity candybox = candyboxRepository.save(new CandyboxEntity(commande));
            ItemcandyboxEntity item = new ItemcandyboxEntity(candybox, couleur, currentQuantity);
            items.add(item);
            remainingQuantity -= currentQuantity;
        }
        return items;
    }

    private void tsubakiAlgo(OrderModel orderModel) {
    }

    private void momoAlgo(OrderModel orderModel) {
    }

    private void asagaoAlgo(OrderModel orderModel) {
    }

    private void kikuAlgo(OrderModel orderModel) {
    }


}
