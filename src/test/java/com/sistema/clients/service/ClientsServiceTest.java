package com.sistema.clients.service;

import com.sistema.clients.controller.ClientsController;
import com.sistema.clients.exceptions.DBException;
import com.sistema.clients.model.Enum.ClientTypeEnum;
import com.sistema.clients.model.entity.ClientsEntity;
import com.sistema.clients.model.entity.EmployeeEntity;
import com.sistema.clients.model.request.ClientsRequest;
import com.sistema.clients.repository.ClientsRepository;
import com.sistema.clients.service.impl.ClientsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.inject.Inject;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class ClientsServiceTest {

    @InjectMocks
    private ClientsServiceImpl clientsService;

    @Mock
    private ClientsController clientsController;

    @Mock
    private ClientsRepository clientsRepository;

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

//        doNothing().when(clientsService).saveDocuments(clientsEntity);
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
}
