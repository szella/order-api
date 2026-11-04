package br.com.szella.orderapi.infrastructure.adapters.in.web;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        List<String> details,
        String path
) {
    public static ErrorResponse of(int status, String error, String message, String path) {
        return new ErrorResponse(LocalDateTime.now(), status, error, message, null, path);
    }

    public static ErrorResponse of(int status, String error, String message, List<String> details, String path) {
        return new ErrorResponse(LocalDateTime.now(), status, error, message, details, path);
    }
}
