package com.example.ApiRestFipe.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<Map<String, Object>> tratarErroApiExterna(HttpClientErrorException e) {

        logger.error("Erro na chamada à API externa: {}", e.getMessage(), e);

        Map<String, Object> corpoErro = new LinkedHashMap<>();
        corpoErro.put("timestamp", LocalDateTime.now());
        corpoErro.put("status", e.getStatusCode().value());
        corpoErro.put("erro", "Falha ao consultar a API FIPE");
        corpoErro.put("detalhe", e.getResponseBodyAsString());

        return ResponseEntity.status(e.getStatusCode()).body(corpoErro);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> tratarErroGenerico(Exception e) {

        logger.error("Erro inesperado na aplicação", e);

        Map<String, Object> corpoErro = new LinkedHashMap<>();
        corpoErro.put("timestamp", LocalDateTime.now());
        corpoErro.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        corpoErro.put("erro", "Erro interno no servidor");
        corpoErro.put("detalhe", e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(corpoErro);
    }
}