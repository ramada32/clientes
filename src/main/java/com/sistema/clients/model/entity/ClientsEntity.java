package com.sistema.clients.model.entity;


import com.sistema.clients.model.Enum.ClientTypeEnum;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@Builder
@Table(name = "clients")
public class ClientsEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "clients_id")
    private Integer id;

    @NotBlank
    @Column(name = "nome")
    private String name;

    @Column(name = "telefone")
    private String phone;

    @Column(name = "data_nascimento")
    private LocalDateTime dateBirth;

    @Column(name = "endereco")
    private String address;

    @Column(name = "CPF" , unique = true)
    private String cpf;

    @Column(name = "fatura", precision = 10, scale = 2)
    private BigDecimal invoice;

    @Lob
    @Column(name = "foto_Cliente")
    private byte[] imageClient;

    @Column(name = "tipo_cliente")
    private ClientTypeEnum clientTypeEnum;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "employee_id", name = "employee_id")
    private EmployeeEntity employee;
}
