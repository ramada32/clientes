package com.sistema.clients.mapper;

import com.sistema.clients.model.entity.ClientsEntity;
import com.sistema.clients.model.request.ClientsRequest;
import com.sistema.clients.model.response.ClientsResponse;

import java.util.List;
import java.util.stream.Collectors;

public class ClientsMapper {


    public static ClientsEntity mapRequestClient(ClientsRequest clientsRequest){

        return ClientsEntity.builder()
                .id(clientsRequest.getId())
                .employee(clientsRequest.getEmployee())
                .address(clientsRequest.getAddress())
                .clientTypeEnum(clientsRequest.getClientTypeEnum())
                .imageClient(clientsRequest.getImageClient())
                .cpf(clientsRequest.getCpf())
                .dateBirth(clientsRequest.getDateBirth())
                .invoice(clientsRequest.getInvoice())
                .name(clientsRequest.getName())
                .phone(clientsRequest.getPhone())
                .build();
    }

    public static ClientsResponse mapResponseClient(ClientsEntity clients){

        return ClientsResponse.builder()
                .id(clients.getId())
                .employee(clients.getEmployee())
                .address(clients.getAddress())
                .clientTypeEnum(clients.getClientTypeEnum())
                .imageClient(clients.getImageClient())
                .cpf(clients.getCpf())
                .dateBirth(clients.getDateBirth())
                .invoice(clients.getInvoice())
                .name(clients.getName())
                .phone(clients.getPhone())
                .build();
    }

    public static List<ClientsResponse> mapResponseList(List<ClientsEntity> clientsList){
        return clientsList.stream()
                .map(ClientsMapper::mapResponseClient)
                .collect(Collectors.toList());
    }
}
