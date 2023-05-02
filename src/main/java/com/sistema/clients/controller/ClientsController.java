package com.sistema.clients.controller;

import com.sistema.clients.model.request.ClientsRequest;
import com.sistema.clients.model.response.ClientsResponse;
import com.sistema.clients.service.ClientsService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/consult")
public class ClientsController {

    private final ClientsService clientsService;

    @GetMapping
    public ResponseEntity<List<ClientsResponse>> consultClientAll(){

        log.info("entrando clientsService.consultClientAll()");

        List<ClientsResponse> clientsResponses = clientsService.consultClientAll();

        log.info("clientsService.consultClientAll() sucesso");

        return ResponseEntity.ok(clientsResponses);
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<ClientsResponse> consultClientId(@PathVariable("clientId") Integer id){

        log.info("entrando clientsService.consultClientId()");

        ClientsResponse clientsResponse = clientsService.consultClientId(id);

        log.info("clientsService.consultClientId() sucesso");

        return ResponseEntity.ok(clientsResponse);
    }

    @PostMapping
    public ResponseEntity<ClientsResponse> insert(@RequestBody @Valid ClientsRequest clientsRequest) {

        log.info("entrando clientsService.insert()");

        ClientsResponse clientsResponse = clientsService.insert(clientsRequest);

        log.info("clientsService.insert() sucesso");

        return ResponseEntity.ok(clientsResponse);
    }


    @PatchMapping("/{clientId}")
    public ResponseEntity<ClientsResponse> update(@PathVariable("clientId") Integer id, @RequestBody @Valid ClientsRequest clientsRequest) {

        log.info("entrando clientsService.update()");

        ClientsResponse clientsResponse = clientsService.update(id, clientsRequest);

        log.info("clientsService.update() sucesso");

        return ResponseEntity.ok(clientsResponse);
    }

}
