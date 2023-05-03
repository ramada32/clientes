package com.sistema.clients.service;

import com.sistema.clients.model.entity.ClientsEntity;
import com.sistema.clients.model.request.ClientsRequest;
import com.sistema.clients.model.response.ClientsResponse;
import java.util.List;

public interface ClientsService {

    List<ClientsResponse> consultClientAll();
    ClientsResponse consultClientId(Integer id);
    ClientsResponse insert(ClientsRequest clientsRequest);
    ClientsResponse update(Integer id, ClientsRequest clientsRequest);
    void saveDocuments(ClientsEntity clientsEntity);
}
