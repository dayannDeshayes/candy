package com.simplon.candy.mapper;

import com.simplon.candy.entity.CandytagEntity;
import com.simplon.candy.entity.CommandeEntity;
import com.simplon.candy.entity.DTO.OrderInputDTO;
import com.simplon.candy.entity.DTO.OrderOutputDto;
import com.simplon.candy.entity.UtilisateurEntity;
import com.simplon.candy.entity.model.OrderModel;
import com.simplon.candy.entity.model.OrderOutputModel;
import com.simplon.candy.repository.CandytagRepository;
import com.simplon.candy.repository.UtilisateurRepository;
import com.simplon.candy.service.IService.ICandyOrderService;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mappings;
import org.mapstruct.Named;


@org.mapstruct.Mapper(componentModel = "spring", uses = {})
public interface Mapper {

    OrderModel toModel(OrderInputDTO orderInputDTO);

    OrderInputDTO toDto(OrderModel orderModel);

    OrderOutputDto toDto(OrderOutputModel orderOutputModel);

    default CommandeEntity toEntity(OrderModel orderModel, @Context UtilisateurRepository utilisateurRepository, @Context CandytagRepository candytagRepository, @org.mapstruct.MappingTarget CommandeEntity commandeEntity) {
        CandytagEntity candytag = candytagRepository.findByCandytag(orderModel.getCandytag());
        commandeEntity.setUtilisateur(utilisateurRepository.findById(orderModel.getUtilisateurId()).get());
        commandeEntity.setCandytag(candytag);
        return commandeEntity;
    }
}
