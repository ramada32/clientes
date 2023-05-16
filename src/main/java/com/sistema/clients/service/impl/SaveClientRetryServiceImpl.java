package com.sistema.clients.service.impl;

import com.sistema.clients.exceptions.DBException;
import com.sistema.clients.model.entity.ClientsEntity;
import com.sistema.clients.repository.ClientsRepository;
import com.sistema.clients.service.SaveClientRetryService;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

@Slf4j
@Service
@RequiredArgsConstructor
public class SaveClientRetryServiceImpl implements SaveClientRetryService {

    private final ClientsRepository clientsRepository;

    //    @Retry(name = "clientsDBRetry", fallbackMethod = "registryErrorFallback")
    @Retry(name = "clientsDBRetry")
    @CircuitBreaker(name = "clientsDBRetry",fallbackMethod = "registryErrorFallback")
    public void saveDocuments(ClientsEntity clientsEntity){
        try {
            clientsRepository.save(clientsEntity);
        }catch (Exception ex){
            throw new DBException(new RuntimeException("erro de base de dados"));
        }
    }

    public void registryErrorFallback(ClientsEntity clientsEntity, RestClientException restClientException){
        log.error(String.format("Tentativas de envio falha: %s\n $s", clientsEntity.getCpf(), restClientException.getMessage()));
    }
}
