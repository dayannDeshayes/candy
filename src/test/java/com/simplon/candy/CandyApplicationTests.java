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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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

    @Test
    void testSakuraAlgo() {
        UtilisateurEntity user = new UtilisateurEntity();
        user.setId(1);
        user.setNom("Test User");

        CandytagEntity candytag = candyTagRepository.findByCandytag(CandytagEnum.SAKURA);
        assertNotNull(candytag);

        OrderModel orderModel = new OrderModel();
        orderModel.setUtilisateurId(user.getId());
        orderModel.setCandytag(CandytagEnum.SAKURA);
        orderModel.setQte(5);

        candyOrderService.processOrder(orderModel);


        CommandeEntity commande = commandeRepository.findByUtilisateurId(user.getId());
        assertNotNull(commande);
        assertEquals(user.getId(), commande.getUtilisateur().getId());
        assertEquals(candytag.getId(), commande.getCandytag().getId());

        List<ItemcandyboxEntity> items = itemcandyboxRepository.findByCandyboxId(commande.getCandyboxes().get(0).getId());
        assertFalse(items.isEmpty());


        int totalQuantity = items.stream().mapToInt(ItemcandyboxEntity::getQuantite).sum();
        assertEquals(orderModel.getQte(), totalQuantity);
    }

    @Test
    void contextLoads() {
    }

}
