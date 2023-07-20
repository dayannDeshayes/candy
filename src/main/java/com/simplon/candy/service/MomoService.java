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

@RequiredArgsConstructor
@Component
public class MomoService extends AbstractProcessor {

    @Override
    public CommandeEntity filterOrder(OrderModel orderModel) {
        CommandeEntity commandeEntity = this.commandeRepository.save(this.mapper.toEntity(orderModel, utilisateurRepository, candyTagRepository, new CommandeEntity()));
        int quantity = orderModel.getQte();
        final int CANDY_BOX_SIZE = 12;


        if (quantity % 2 == 0) {
            throw new RuntimeException("Impossible, la quantité doit être impaire");
        }

        List<String> colors = Arrays.asList("rouge", "vert", "bleu", "jaune", "mauve", "blanc", "Cyan");

        List<Integer> primeNumbers = generatePrimeNumbers(quantity);
        List<ItemcandyboxEntity> itemcandyboxEntities = new ArrayList<>();

        int colorIndex = 0;
        int previousPrime = 0;
        for (int prime : primeNumbers) {
            int candyForThisColor = prime - previousPrime;
            String colorName = colors.get(colorIndex);
            CouleurEntity couleur = this.couleurRepository.findByCouleur(colorName);
            while (candyForThisColor > 0) {
                CandyboxEntity candybox;
                if (itemcandyboxEntities.isEmpty() || sumCandyInBox(itemcandyboxEntities.get(itemcandyboxEntities.size() - 1).getCandybox(), itemcandyboxEntities) >= CANDY_BOX_SIZE) {
                    candybox = new CandyboxEntity(commandeEntity);
                    commandeEntity.getCandyboxes().add(candybox);
                    candybox = candyboxRepository.save(candybox);
                } else {
                    candybox = itemcandyboxEntities.get(itemcandyboxEntities.size() - 1).getCandybox();
                }

                int currentCandyForThisColor = Math.min(candyForThisColor, CANDY_BOX_SIZE - sumCandyInBox(candybox, itemcandyboxEntities));
                ItemcandyboxEntity item = new ItemcandyboxEntity(candybox, couleur, currentCandyForThisColor);
                itemcandyboxEntities.add(item);
                candybox.getItemCandyboxes().add(item);

                candyForThisColor -= currentCandyForThisColor;
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
}
