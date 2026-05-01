package com.biblioteca.api_publica.exceptions;
import com.biblioteca.api_publica.domain.dto.ErrorDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // Interceptor global para exceções
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Trata as exceções específicas da API
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorDTO> handleApi(ApiException ex) {
        log.error("ApiException: {}", ex.getMessage());
        return ResponseEntity.status(ex.status)
                .body(new ErrorDTO(ex.getMessage(), ex.key));
    }

    // Trata qualquer outro erro inesperado
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleGeneric(Exception ex) {
        log.error("Exception não tratada", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDTO("Erro interno no servidor", "biblioteca.global.handler.exception"));
    }
}