package com.sistema.clients.model.entity;

import com.sistema.clients.model.Enum.EmployeeAccessTypeEnum;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "Employee")
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Integer id;

    @Column(name = "nome_Funcionario")
    private String name;

    @Column(name = "acesso_Funcionario")
    private EmployeeAccessTypeEnum employeeAccessType;

    @Column(name = "salario", precision = 10, scale = 2)
    private BigDecimal salary;
}
