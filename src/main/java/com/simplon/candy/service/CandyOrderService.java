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
import java.util.Arrays;
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
            if (!candyTagRepository.existsByCandytag(candytagEnum)) {
                CandytagEntity candytagEntity = new CandytagEntity();
                candytagEntity.setCandytag(candytagEnum);
                candyTagRepository.save(candytagEntity);
            }
        }

        UtilisateurEntity utilisateurEntity = new UtilisateurEntity();
        utilisateurEntity.setNom("admin");
        this.utilisateurRepository.save(utilisateurEntity);
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
            case SAKURA -> orderOutputModel.setCommande(this.sakuraAlgo(orderModel));
            case TSUBAKI -> orderOutputModel.setCommande(this.tsubakiAlgo(orderModel));
            case MOMO -> orderOutputModel.setCommande(this.momoAlgo(orderModel));
            case ASAGAO -> orderOutputModel.setCommande(this.asagaoAlgo(orderModel));
            case KIKU -> orderOutputModel.setCommande(this.kikuAlgo(orderModel));
            default -> orderOutputModel.setMessage("Candytag not found");
        }

        if (orderOutputModel.getMessage() != null) {
            orderOutputModel.setStatus(StatusEnum.ERREUR);
            return orderOutputModel;
        }

        orderOutputModel.setStatus(StatusEnum.OK);

        return orderOutputModel;
    }

    private CommandeEntity sakuraAlgo(OrderModel orderModel) {
        CommandeEntity commandeEntity = this.commandeRepository.save(this.mapper.toEntity(orderModel, utilisateurRepository, candyTagRepository, new CommandeEntity()));
        List<ItemcandyboxEntity> itemcandyboxEntities = new ArrayList<>();
        int quantity = orderModel.getQte();
        if (quantity < 10) {
            itemcandyboxEntities.addAll(generateItems(commandeEntity, "red", 5));
            itemcandyboxEntities.addAll(generateItems(commandeEntity, "blue", quantity - 5));
        } else if (quantity <= 150) {
            itemcandyboxEntities.addAll(generateItems(commandeEntity, "green", 10));
            int rest = Math.min(quantity - 10, 50);
            itemcandyboxEntities.addAll(generateItems(commandeEntity, "white", rest));
            if (quantity > 60) {
                itemcandyboxEntities.addAll(generateItems(commandeEntity, "yellow", quantity - 60));
            }
        } else {
            int randomRed = new Random().nextInt(quantity / 3);
            int randomCyan = randomRed + new Random().nextInt((quantity - randomRed) / 2);
            int randomPurple = randomCyan + (quantity - randomCyan);

            itemcandyboxEntities.addAll(generateItems(commandeEntity, "red", randomRed));
            itemcandyboxEntities.addAll(generateItems(commandeEntity, "cyan", randomCyan - randomRed));
            itemcandyboxEntities.addAll(generateItems(commandeEntity, "purple", randomPurple - randomCyan));
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

    private CommandeEntity tsubakiAlgo(OrderModel orderModel) {
        CommandeEntity commandeEntity = this.commandeRepository.save(this.mapper.toEntity(orderModel, utilisateurRepository, candyTagRepository, new CommandeEntity()));
        List<ItemcandyboxEntity> itemcandyboxEntities = new ArrayList<>();
        int quantity = orderModel.getQte();

        if (quantity <= 10 || quantity % 2 != 0) {
            throw new RuntimeException("Invalid quantity");
        }

        List<String> colors = Arrays.asList("Red", "Green", "Blue", "Yellow", "Purple");

        int candyBoxesToCreate = 5;
        int candiesPerBox = quantity / candyBoxesToCreate;
        if (candiesPerBox % 2 != 0) {
            throw new RuntimeException("Impossible de répartir équitablement les bonbons dans les boîtes et respecter la parité");
        }

        int colorsPerBox = candiesPerBox > 2 ? 2 : 1;
        int candiesPerColor = candiesPerBox / colorsPerBox;
        if (candiesPerColor % 2 != 0) {
            throw new RuntimeException("Impossible de répartir équitablement les bonbons dans les boîtes et respecter la parité");
        }


        for (int i = 0; i < candyBoxesToCreate; i++) {
            CandyboxEntity candybox = new CandyboxEntity(commandeEntity);
            commandeEntity.getCandyboxes().add(candybox);
            candybox = candyboxRepository.save(candybox); // Save the CandyboxEntity before associating it with ItemcandyboxEntity

            List<String> colorsUsed = new ArrayList<>(); // To make sure we do not use the same color twice in the same box
            for (int j = 0; j < colorsPerBox; j++) {
                String color;
                do {
                    color = colors.get(new Random().nextInt(colors.size()));
                } while (colorsUsed.contains(color));
                colorsUsed.add(color);
                CouleurEntity couleur = this.couleurRepository.findByCouleur(color);
                ItemcandyboxEntity item = new ItemcandyboxEntity(candybox, couleur, candiesPerColor);
                itemcandyboxEntities.add(item);
                candybox.getItemCandyboxes().add(item);
            }
        }

        this.commandeRepository.save(commandeEntity);
        this.itemcandyboxRepository.saveAll(itemcandyboxEntities);

        return commandeEntity;
    }

    private CommandeEntity momoAlgo(OrderModel orderModel) {
        CommandeEntity commandeEntity = this.commandeRepository.save(this.mapper.toEntity(orderModel, utilisateurRepository, candyTagRepository, new CommandeEntity()));
        int quantity = orderModel.getQte();
        final int CANDY_BOX_SIZE = 20;


        if (quantity % 2 == 0) {
            return null; // La quantité ne respecte pas les conditions (doit être impaire)
        }

        List<String> colors = Arrays.asList("Red", "Green", "Blue", "Yellow", "Purple", "White", "Cyan"); // Remplacez par vos couleurs

        List<Integer> primeNumbers = generatePrimeNumbers(quantity);
        List<ItemcandyboxEntity> itemcandyboxEntities = new ArrayList<>();

        int colorIndex = 0;
        int previousPrime = 0;
        for (int prime : primeNumbers) {
            int candiesForThisColor = prime - previousPrime;
            String colorName = colors.get(colorIndex);
            CouleurEntity couleur = this.couleurRepository.findByCouleur(colorName);
            while (candiesForThisColor > 0) {
                CandyboxEntity candybox;
                if (itemcandyboxEntities.isEmpty() || sumCandyInBox(itemcandyboxEntities.get(itemcandyboxEntities.size() - 1).getCandybox(), itemcandyboxEntities) >= CANDY_BOX_SIZE) {
                    candybox = new CandyboxEntity(commandeEntity);
                    commandeEntity.getCandyboxes().add(candybox);
                    candybox = candyboxRepository.save(candybox);
                } else {
                    candybox = itemcandyboxEntities.get(itemcandyboxEntities.size() - 1).getCandybox();
                }

                int currentCandiesForThisColor = Math.min(candiesForThisColor, CANDY_BOX_SIZE - sumCandyInBox(candybox, itemcandyboxEntities));
                ItemcandyboxEntity item = new ItemcandyboxEntity(candybox, couleur, currentCandiesForThisColor);
                itemcandyboxEntities.add(item);
                candybox.getItemCandyboxes().add(item);

                candiesForThisColor -= currentCandiesForThisColor;
            }

            colorIndex = (colorIndex + 1) % colors.size();
            previousPrime = prime;
        }

        this.commandeRepository.save(commandeEntity);
        this.itemcandyboxRepository.saveAll(itemcandyboxEntities);

        return commandeEntity;
    }

    private int sumCandyInBox(CandyboxEntity candybox, List<ItemcandyboxEntity> itemcandyboxEntities) {
        int sum = 0;
        for(ItemcandyboxEntity item : itemcandyboxEntities) {
            if(item.getCandybox().equals(candybox)) {
                sum += item.getQuantite();
            }
        }
        return sum;
    }

    private List<Integer> generatePrimeNumbers(int limit) {
        List<Integer> primeNumbers = new ArrayList<>();
        for (int i = 2; i <= limit; i++) {
            if (isPrime(i)) {
                primeNumbers.add(i);
            }
        }
        return primeNumbers;
    }

    private boolean isPrime(int num) {
        if (num <= 1) return false;
        if (num <= 3) return true;
        if (num % 2 == 0 || num % 3 == 0) return false;
        for (int i = 5; i * i <= num; i += 6) {
            if (num % i == 0 || num % (i + 2) == 0) return false;
        }
        return true;
    }

    private CommandeEntity asagaoAlgo(OrderModel orderModel) {
        return new CommandeEntity();
    }

    private CommandeEntity kikuAlgo(OrderModel orderModel) {
        return new CommandeEntity();
    }


}
