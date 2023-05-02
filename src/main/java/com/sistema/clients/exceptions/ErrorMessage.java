package com.sistema.clients.exceptions;

import lombok.Data;

import java.util.List;

@Data
public class ErrorMessage {

    private int code;
    private String message;
    private List<String> messages;
}
