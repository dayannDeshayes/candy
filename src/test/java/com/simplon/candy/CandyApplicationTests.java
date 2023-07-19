package com.simplon.candy;

import com.simplon.candy.entity.CandytagEntity;
import com.simplon.candy.entity.CommandeEntity;
import com.simplon.candy.entity.ItemcandyboxEntity;
import com.simplon.candy.entity.UtilisateurEntity;
import com.simplon.candy.entity.enums.CandytagEnum;
import com.simplon.candy.entity.model.OrderModel;
import com.simplon.candy.entity.model.OrderOutputModel;
import com.simplon.candy.repository.CandytagRepository;
import com.simplon.candy.repository.CommandeRepository;
import com.simplon.candy.repository.CouleurRepository;
import com.simplon.candy.repository.ItemCandyBoxRepository;
import com.simplon.candy.service.CandyOrderService;
import com.simplon.candy.service.IService.ICandyOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CandyApplicationTests {

    @Autowired
    CandyOrderService candyOrderService;

    @Autowired
    CandytagRepository candyTagRepository;

    @Autowired
    CouleurRepository couleurRepository;

    @Autowired
    CommandeRepository commandeRepository;

    @Autowired
    ItemCandyBoxRepository itemcandyboxRepository;

    @ParameterizedTest
    @MethodSource("provideOrderData")
    void testSakuraAlgo(int userId, String userName, CandytagEnum candyTag, int qte, Map<String, Integer> expectedDistribution) {
        UtilisateurEntity user = new UtilisateurEntity();
        user.setId(userId);
        user.setNom(userName);

        CandytagEntity candytag = candyTagRepository.findByCandytag(candyTag);
        assertNotNull(candytag);

        OrderModel orderModel = new OrderModel();
        orderModel.setUtilisateurId(user.getId());
        orderModel.setCandytag(candyTag);
        orderModel.setQte(qte);

        OrderOutputModel commande = candyOrderService.processOrder(orderModel);

        assertNotNull(commande);
        assertEquals(user.getId(), commande.getCommande().getUtilisateur().getId());
        assertEquals(candytag.getId(), commande.getCommande().getCandytag().getId());

        List<ItemcandyboxEntity> items = itemcandyboxRepository.findByCandybox_Commande_Id(commande.getCommande().getId());
        assertFalse(items.isEmpty());

        Map<String, Integer> colorDistribution = items.stream()
                .collect(Collectors.groupingBy(i -> i.getCouleur().getCouleur(), Collectors.summingInt(ItemcandyboxEntity::getQuantite)));

        expectedDistribution.forEach((color, expectedQty) -> assertEquals(expectedQty, colorDistribution.get(color)));
    }

    static Stream<Arguments> provideOrderData() {
        return Stream.of(
                Arguments.of(1, "Test User 1", CandytagEnum.SAKURA, 7, Map.of("red", 5, "blue", 2)),
                Arguments.of(2, "Test User 2", CandytagEnum.SAKURA, 60, Map.of("green", 10, "white", 50)),
                Arguments.of(3, "Test User 3", CandytagEnum.SAKURA, 70, Map.of("green", 10, "white", 50, "yellow", 10)),
                Arguments.of(4, "Test User 4", CandytagEnum.SAKURA, 151, Map.of())
                // ajouter d'autres données de test si nécessaire
        );
    }

   /* @Test
    void testSakuraAlgo() {
        UtilisateurEntity user = new UtilisateurEntity();
        user.setId(1);
        user.setNom("Test User");

        CandytagEntity candytag = candyTagRepository.findByCandytag(CandytagEnum.SAKURA);
        assertNotNull(candytag);

        OrderModel orderModel = new OrderModel();
        orderModel.setUtilisateurId(user.getId());
        orderModel.setCandytag(CandytagEnum.SAKURA);
        orderModel.setQte(7);

        OrderOutputModel commande = candyOrderService.processOrder(orderModel);

        assertNotNull(commande);
        assertEquals(user.getId(), commande.getCommande().getUtilisateur().getId());
        assertEquals(candytag.getId(), commande.getCommande().getCandytag().getId());

        List<ItemcandyboxEntity> items = itemcandyboxRepository.findByCandybox_Commande_Id(commande.getCommande().getId());
        assertFalse(items.isEmpty());


        int totalQuantity = items.stream().mapToInt(ItemcandyboxEntity::getQuantite).sum();
        assertEquals(orderModel.getQte(), totalQuantity);
    }*/

    @Test
    void contextLoads() {
    }

}
