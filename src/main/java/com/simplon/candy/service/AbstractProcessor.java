package com.simplon.candy.service;

import com.simplon.candy.entity.CandytagEntity;
import com.simplon.candy.entity.CouleurEntity;
import com.simplon.candy.entity.UtilisateurEntity;
import com.simplon.candy.entity.enums.CandytagEnum;
import com.simplon.candy.mapper.Mapper;
import com.simplon.candy.repository.*;
import com.simplon.candy.service.IService.IProcessor;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractProcessor implements IProcessor {

    @Autowired
    Mapper mapper;
    @Autowired
    CouleurRepository couleurRepository;
    @Autowired
    CommandeRepository commandeRepository;
    @Autowired
    ItemCandyBoxRepository itemcandyboxRepository;
    @Autowired
    CandyboxRepository candyboxRepository;
    @Autowired
    CandytagRepository candyTagRepository;
    @Autowired
    UtilisateurRepository utilisateurRepository;

    protected AbstractProcessor() {
    }

    @PostConstruct
    public void init() {
        saveColorIfNotExists("rouge");
        saveColorIfNotExists("bleu");
        saveColorIfNotExists("vert");
        saveColorIfNotExists("blanc");
        saveColorIfNotExists("jaune");
        saveColorIfNotExists("cyan");
        saveColorIfNotExists("mauve");

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
}
