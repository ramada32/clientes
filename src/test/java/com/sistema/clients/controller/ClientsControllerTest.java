package com.sistema.clients.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistema.clients.exceptions.CustomExceptionHandler;
import com.sistema.clients.exceptions.DBException;
import com.sistema.clients.model.Enum.ClientTypeEnum;
import com.sistema.clients.model.entity.ClientsEntity;
import com.sistema.clients.model.entity.EmployeeEntity;
import com.sistema.clients.model.request.ClientsRequest;
import com.sistema.clients.model.response.ClientsResponse;
import com.sistema.clients.model.response.MessageResponse;
import com.sistema.clients.repository.ClientsRepository;
import com.sistema.clients.service.ClientsService;
import com.sistema.clients.utils.ObjectMapperUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import io.github.resilience4j.retry.RetryRegistry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class ClientsControllerTest {

    @InjectMocks
    private ClientsController clientsController;

    @Mock
    private ClientsService clientsService;

    @Mock
    private ClientsRepository clientsRepository;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    private RetryRegistry retryRegistry;



    @BeforeEach
    public void init(){
        objectMapper = ObjectMapperUtil.getObjectMapper();

        mockMvc = MockMvcBuilders
                .standaloneSetup(new ClientsController(clientsService))
                .setControllerAdvice(new CustomExceptionHandler(objectMapper))
                .setMessageConverters(ObjectMapperUtil.mappingJackson2HttpMessageConverter())
                .build();


////     para funcionar o init de baixo seguir alguns passos
////     passar o service no customerException em vez do objectMapper
//        mockMvc = MockMvcBuilders
//                .standaloneSetup(clientsController)
//                .setControllerAdvice(new CustomExceptionHandler(clientsService))
//                .build();
    }

    @Test
    public void consultClientAllOk() throws Exception {

        List<ClientsResponse> clientsResponsesList = new ArrayList<>();
        clientsResponsesList.add(getClientResponse());

        when(clientsService.consultClientAll()).thenReturn(clientsResponsesList);

        mockMvc.perform(get("/consult")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].address", is(clientsResponsesList.get(0).getAddress())));
    }

    @Test
    public void consultClientAllError() throws Exception {

        var message = UUID.randomUUID().toString();

        when(clientsService.consultClientAll()).thenThrow(new DBException(new RuntimeException(message)));

        var mvcResult = mockMvc.perform(get("/consult")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code", is(400)))
                .andReturn();
        //nem precisava dessas ultimas 3 linhas do teste até essa linha de cima já estaria aderente, adicionei para ter um verificação a mas no teste
        var messageResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), MessageResponse.class);

        assertEquals(400, messageResponse.getCode());
        assertEquals("Falha ao se comunicar com o banco de dados", messageResponse.getMessage());
    }

    @Test
    public void consultClientIdOk() throws Exception {

        when(clientsService.consultClientId(1)).thenReturn(getClientResponse());

        mockMvc.perform(
                get("/consult".concat("/").concat(getClientResponse().getId().toString()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address", is(getClientResponse().getAddress())));
    }

    @Test
    public void consultClientIdError() throws Exception {

        var message = UUID.randomUUID().toString();

        when(clientsService.consultClientId(any())).thenThrow(new DBException(new RuntimeException(message)));

        var mvcResult = mockMvc.perform(get("/consult".concat("/").concat("1"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code", is(400)))
                .andReturn();
        //nem precisava dessas ultimas 3 linhas do teste até essa linha de cima já estaria aderente, adicionei para ter um verificação a mas no teste
        var messageResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), MessageResponse.class);

        assertEquals(400, messageResponse.getCode());
        assertEquals("Falha ao se comunicar com o banco de dados", messageResponse.getMessage());
    }

    @Test
    public void insertClientOk() throws Exception {

        when(clientsService.insert(getClientRequest())).thenReturn(getClientResponse());

        mockMvc.perform(
                post("/consult")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(getClientRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address", is(getClientResponse().getAddress())));
    }

    @Test
    public void insertClientError() throws Exception {

        var message = UUID.randomUUID().toString();

        when(clientsService.insert(getClientRequest())).thenThrow(new DBException(new RuntimeException(message)));

        var mvcResult = mockMvc.perform(post("/consult")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getClientRequest())))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code", is(400)))
                .andReturn();
        //nem precisava dessas ultimas 3 linhas do teste até essa linha de cima já estaria aderente, adicionei para ter um verificação a mas no teste
        var messageResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), MessageResponse.class);

        assertEquals(400, messageResponse.getCode());
        assertEquals("Falha ao se comunicar com o banco de dados", messageResponse.getMessage());
    }
    @Test
    public void patchClientOk() throws Exception {

        when(clientsService.update(1, getClientRequest())).thenReturn(getClientResponse());

        mockMvc.perform(
                patch("/consult".concat("/").concat(getClientResponse().getId().toString()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getClientRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address", is(getClientResponse().getAddress())));
    }

    @Test
    public void patchClientError() throws Exception {

        var message = UUID.randomUUID().toString();

        when(clientsService.update(1, getClientRequest())).thenThrow(new DBException(new RuntimeException(message)));

        var mvcResult = mockMvc.perform(patch("/consult".concat("/").concat("1"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getClientRequest())))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code", is(400)))
                .andReturn();
        //nem precisava dessas ultimas 3 linhas do teste até essa linha de cima já estaria aderente, adicionei para ter um verificação a mas no teste
        var messageResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), MessageResponse.class);

        assertEquals(400, messageResponse.getCode());
        assertEquals("Falha ao se comunicar com o banco de dados", messageResponse.getMessage());
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

        doThrow(DBException.class).when(clientsService).insert(getClientRequest());

        try {
            clientsController.insert(getClientRequest());
        } catch (Exception ignored) {
        } finally {
            verify(clientsRepository, times(0)).save(clientsEntity);
        }
    }
}
