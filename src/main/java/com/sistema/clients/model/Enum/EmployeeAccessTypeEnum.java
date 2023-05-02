package com.sistema.clients.model.Enum;

import lombok.Getter;

@Getter
public enum EmployeeAccessTypeEnum {

    BASIC("ACESSO_NIVEL_1"),
    DEVELOPER("ACESSO_NIVEL_2"),
    TECH_LEAD("ACESSO_NIVEL_3"),
    ARCHITECT("ACESSO NIVEL 3");

    private String message;

    EmployeeAccessTypeEnum(String message){
        this.message = message;
    }
}
