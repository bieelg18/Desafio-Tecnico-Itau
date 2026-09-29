package dev.bieelg18.DesafioItau.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(ValorInvalidoException.class)
    public ResponseEntity<String> tratarValorInvalido(
            ValorInvalidoException e
    ){
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT)
                .body(e.getMessage());
    }

    @ExceptionHandler(DataInvalidaException.class)
    public ResponseEntity<String> tratarDataInvalida(
            DataInvalidaException e
    ){
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT)
                .body(e.getMessage());
    }

}
