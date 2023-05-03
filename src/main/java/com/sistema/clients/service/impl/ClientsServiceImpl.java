package com.sistema.clients.service.impl;

import com.sistema.clients.exceptions.DBException;
import com.sistema.clients.mapper.ClientsMapper;
import com.sistema.clients.model.entity.ClientsEntity;
import com.sistema.clients.model.request.ClientsRequest;
import com.sistema.clients.model.response.ClientsResponse;
import com.sistema.clients.repository.ClientsRepository;
import com.sistema.clients.service.ClientsService;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientsServiceImpl implements ClientsService {

    private static final String EXCEPTION_ERROR = "ID inexistente na base de dados";

    private final ClientsRepository clientsRepository;

    private ClientsMapper clientsMapper;

    @Override
    public List<ClientsResponse> consultClientAll() {

        try {
            List<ClientsEntity> clientsEntities = clientsRepository.findAll();

            return ClientsMapper.mapResponseList(clientsEntities);
        }catch (Exception ex){
            throw new DBException(new RuntimeException("erro de base de dados"));
        }
    }


    @Cacheable(
            cacheManager = "redisCacheManager",
            cacheNames = {"clientsCache"},
            key = "#id"
    )
    @Override
    public ClientsResponse consultClientId(Integer id) {

        try {
            Optional<ClientsEntity> optionalClients = clientsRepository.findById(id);

            if (optionalClients.isPresent()) {

                return ClientsMapper.mapResponseClient(optionalClients.get());
            }

            throw new DBException(new RuntimeException(EXCEPTION_ERROR));
        }catch (Exception ex){
            throw new DBException(new RuntimeException(EXCEPTION_ERROR));
        }
    }

    @Override
    public ClientsResponse insert(ClientsRequest clientsRequest){

        try {
            ClientsEntity clientsForm = clientsRepository.findByIdAndCpf(clientsRequest.getId(), clientsRequest.getCpf());

            if (clientsForm != null) {
                throw new DBException(new RuntimeException(EXCEPTION_ERROR));
            }

            clientsForm = ClientsMapper.mapRequestClient(clientsRequest);

            saveDocuments(clientsForm);

            return ClientsMapper.mapResponseClient(clientsForm);
        }catch (Exception ex){
            throw new DBException(new RuntimeException("erro de base de dados"));
        }
    }

//    @Retry(name = "clientsDBRetry", fallbackMethod = "registryErrorFallback")
    @Retry(name = "clientsDBRetry")
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

    @CacheEvict(
            cacheManager = "redisCacheManager",
            cacheNames = "clientsCache",
            key = "#id"
    )
    @Override
    public ClientsResponse update(Integer id, ClientsRequest clientsRequest){

        try {
            Optional<ClientsEntity> clientsEntity = clientsRepository.findById(id);

            if (clientsEntity.isPresent()) {

                clientsRepository.save(clientsEntity.get());
                return ClientsMapper.mapResponseClient(clientsEntity.get());
            }

            throw new DBException(new RuntimeException(EXCEPTION_ERROR));
        }catch (Exception ex){
            throw new DBException(new RuntimeException(EXCEPTION_ERROR));
        }
    }
}
