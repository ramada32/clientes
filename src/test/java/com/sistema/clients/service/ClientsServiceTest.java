package com.sistema.clients.service;

import com.sistema.clients.controller.ClientsController;
import com.sistema.clients.exceptions.DBException;
import com.sistema.clients.model.Enum.ClientTypeEnum;
import com.sistema.clients.model.entity.ClientsEntity;
import com.sistema.clients.model.entity.EmployeeEntity;
import com.sistema.clients.model.request.ClientsRequest;
import com.sistema.clients.model.response.ClientsResponse;
import com.sistema.clients.repository.ClientsRepository;
import com.sistema.clients.service.impl.ClientsServiceImpl;
import com.sistema.clients.service.impl.SaveClientRetryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;


import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class ClientsServiceTest {

    @InjectMocks
    private ClientsServiceImpl clientsService;

    @Mock
    private ClientsController clientsController;

    @Mock
    private ClientsRepository clientsRepository;

    @Mock
    private SaveClientRetryServiceImpl saveClientRetryService;

    @Test
    public void testSaveRetry() {

        EmployeeEntity employee = new EmployeeEntity();
        employee.setId(2);
        employee.setName("sadas");
        employee.setSalary(new BigDecimal(12));

        ClientsEntity clientsEntity = ClientsEntity.builder()
                .clientTypeEnum(ClientTypeEnum.LIMPO)
                .cpf("234234234")
                .id(1)
                .phone("sadasd")
                .dateBirth(LocalDateTime.of(2022,01,01,1,1))
                .name("felipe")
                .employee(employee)
                .invoice(new BigDecimal(1))
                .address("test").build();

        when(clientsService.insert(getClientRequest())).thenReturn(getClientResponse());
//
        doThrow(new RuntimeException()).when(clientsRepository).save(clientsEntity);

        try {
            clientsController.insert(getClientRequest());
        } catch (Exception ignored) {
        } finally {
            verify(clientsRepository, times(0)).save(clientsEntity);
        }
    }

    private ClientsRequest getClientRequest(){
        ClientsRequest clientsRequest = ClientsRequest.builder()
                .clientTypeEnum(ClientTypeEnum.LIMPO)
                .cpf("234234234")
                .id(1)
                .phone("sadasd")
                .dateBirth(LocalDateTime.of(2022,01,01,1,1))
                .name("felipe")
                .invoice(new BigDecimal(1))
                .address("test").build();
        return clientsRequest;
    }

    private ClientsResponse getClientResponse(){
        ClientsResponse clientResponse = ClientsResponse.builder()
                .clientTypeEnum(ClientTypeEnum.LIMPO)
                .id(1)
                .address("test")
                .name("felipe")
                .phone("sadasd")
                .dateBirth(LocalDateTime.of(2022,01,01,1,1))
                .invoice(new BigDecimal(1))
                .cpf("asdas").build();

        return clientResponse;
    }
}
