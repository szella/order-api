package br.com.szella.orderapi.infrastructure.adapters.in.web;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex,
            HttpServletRequest request) {

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorResponse response = ErrorResponse.of(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI());

        return ResponseEntity
                .status(httpStatus)
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> String.format("Campo '%s': '%s'", error.getField(), error.getDefaultMessage()))
                .toList();

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorResponse response = ErrorResponse.of(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                "Erro de validação nos campos da requisição",
                errors,
                request.getRequestURI()
        );

        return ResponseEntity
                .status(httpStatus)
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request) {

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse response = ErrorResponse.of(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                "Ocorreu um erro interno inesperado no servidor",
                request.getRequestURI()
        );

        return ResponseEntity.status(httpStatus).body(response);
    }
}
