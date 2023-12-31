package com.simplon.candy.mapper;

import com.simplon.candy.entity.CandytagEntity;
import com.simplon.candy.entity.CommandeEntity;
import com.simplon.candy.entity.DTO.OrderInputDTO;
import com.simplon.candy.entity.model.OrderModel;
import com.simplon.candy.repository.CandytagRepository;
import com.simplon.candy.repository.UtilisateurRepository;
import org.mapstruct.Context;


@org.mapstruct.Mapper(componentModel = "spring", uses = {})
public interface Mapper {

    OrderModel toModel(OrderInputDTO orderInputDTO);

    OrderInputDTO toDto(OrderModel orderModel);


    default CommandeEntity toEntity(OrderModel orderModel, @Context UtilisateurRepository utilisateurRepository, @Context CandytagRepository candytagRepository, @org.mapstruct.MappingTarget CommandeEntity commandeEntity) {
        CandytagEntity candytag = candytagRepository.findByCandytag(orderModel.getCandytag());
        commandeEntity.setUtilisateur(utilisateurRepository.findById(orderModel.getUtilisateurId()).get());
        commandeEntity.setCandytag(candytag);
        return commandeEntity;
    }
}
