package com.sistema.clients.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistema.clients.service.ClientsService;
import com.sistema.clients.utils.ObjectMapperUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@ControllerAdvice
public class CustomExceptionHandler {

    private final ObjectMapper objectMapper;

//    utilizar o service caso precise passar algo especifico.
//    private final ClientsService clientsService;

    @ExceptionHandler(DBException.class)
    public ResponseEntity<ErrorMessage> handleDBException(final DBException e){

        return getPayloadMessageDetail(
                HttpStatus.BAD_REQUEST,
                "Falha ao se comunicar com o banco de dados",
                Arrays.asList(e.getCause().getMessage()),
                e);

    }

    private ResponseEntity<ErrorMessage> getPayloadMessageDetail(final HttpStatus httpStatus, final String message, final List<String> detail, Exception e){

        ErrorMessage errorMessageDetail = new ErrorMessage();
        errorMessageDetail.setCode(httpStatus.value());
        errorMessageDetail.setMessage(message);
        errorMessageDetail.setMessages(detail);

        return ResponseEntity.status(httpStatus).body(errorMessageDetail);
    }
}
