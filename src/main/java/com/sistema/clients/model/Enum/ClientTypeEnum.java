package com.sistema.clients.model.Enum;

import lombok.Getter;

@Getter
public enum ClientTypeEnum {

    LIMPO("NOME_LIMPO"),
    SUJO("DEVEDOR");

    private String message;

    ClientTypeEnum(String message){
        this.message = message;
    }
}
