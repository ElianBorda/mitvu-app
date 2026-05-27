package com.unq.mitvu.controller;

import com.unq.mitvu.controller.dto.ErrorResponseDTO;
import com.unq.mitvu.exceptions.RecursoNoEncontradoException;
import com.unq.mitvu.exceptions.ReglaDeNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorResponseDTO> manejarRecursoNoEncontrado(RecursoNoEncontradoException ex, WebRequest request) {

        ErrorResponseDTO error = ErrorResponseDTO.builder()
                .mensaje(ex.getMessage())
                .codigoEstado(HttpStatus.NOT_FOUND.value())
                .ruta(request.getDescription(false))
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReglaDeNegocioException.class)
    public ResponseEntity<ErrorResponseDTO> manejarReglaNegocio(ReglaDeNegocioException ex, WebRequest request) {

        ErrorResponseDTO error = ErrorResponseDTO.builder()
                .mensaje(ex.getMessage())
                .codigoEstado(HttpStatus.BAD_REQUEST.value())
                .ruta(request.getDescription(false))
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> manejarErroresGlobales(Exception ex, WebRequest request) throws NoResourceFoundException {

        if (ex instanceof org.springframework.web.servlet.resource.NoResourceFoundException) {
            throw (org.springframework.web.servlet.resource.NoResourceFoundException) ex;
        }

        ErrorResponseDTO error = ErrorResponseDTO.builder()
                .mensaje("Ocurrió un error interno en el servidor: " + ex.getMessage())
                .codigoEstado(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .ruta(request.getDescription(false))
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidacion(
            MethodArgumentNotValidException ex, WebRequest request) {

        List<String> errores = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "codigoEstado", 400,
                "error", "VALIDACION_FALLIDA",
                "mensajes", errores,
                "ruta", request.getDescription(false),
                "timestamp", LocalDateTime.now()
        ));
    }
}