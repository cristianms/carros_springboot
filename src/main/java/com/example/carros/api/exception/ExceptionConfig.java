package com.example.carros.api.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Classe responsável por tratar as exceções lançadas
 */
@RestControllerAdvice
public class ExceptionConfig extends ResponseEntityExceptionHandler {

    /**
     * Exception lançada principalmente quando a URL não é encontrada
     *
     * @return ResponseEntity
     */
    @ExceptionHandler({ EmptyResultDataAccessException.class })
    public ResponseEntity errorNotFound() {
        return ResponseEntity.notFound().build();
    }

    /**
     * Exception lançada principalmente ao validar argumentos com Assert nas ações do controller
     *
     * @return ResponseEntity
     */
    @ExceptionHandler({ IllegalArgumentException.class })
    public ResponseEntity errorBadRequest() {
        return ResponseEntity.badRequest().build();
    }

    /**
     * Exception lançada principalmente ao tentar acessar um método não permitido na role do usuário
     *
     * @return ResponseEntity
     */
    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity accessDenied() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Error("Acesso negado"));
    }

}

/**
 * Classe que permite serialização, serve só para que o retorno fique bonitinho (JSON)
 */
class Error {
    public String error;
    public Error(String error) {
        this.error = error;
    }
}
