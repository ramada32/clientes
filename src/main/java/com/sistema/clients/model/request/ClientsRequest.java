package com.sistema.clients.model.request;

import com.sistema.clients.model.Enum.ClientTypeEnum;
import com.sistema.clients.model.entity.EmployeeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientsRequest {

    private Integer id;
    private String name;
    private String phone;
    private LocalDateTime dateBirth;
    private String address;
    private String cpf;
    private BigDecimal invoice;
    private byte[] imageClient;
    private ClientTypeEnum clientTypeEnum;
    private EmployeeEntity employee;
}
