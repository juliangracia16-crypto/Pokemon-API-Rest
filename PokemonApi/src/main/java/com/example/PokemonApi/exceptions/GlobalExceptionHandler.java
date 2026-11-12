package com.example.PokemonApi.exceptions;

import com.example.PokemonApi.dtos.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> manejarValidciones(MethodArgumentNotValidException ex){
        List<String> detalles = new ArrayList<>();
        for(FieldError error: ex.getBindingResult().getFieldErrors()){
            detalles.add(error.getField() + ":" + error.getDefaultMessage());
        }
        ErrorDTO errorDTO = new ErrorDTO("Error de validacion en los datos enviados",detalles, LocalDateTime.now());
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDTO> manejarRecusoNoEncontrado(ResourceNotFoundException ex){
        List<String> detalles = new ArrayList<>();
        detalles.add(ex.getMessage());

        ErrorDTO errorDTO = new ErrorDTO("Recurso no encontrado",detalles, LocalDateTime.now());

        return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> manejarExcepionGlobal(Exception ex){
        List<String> detalles = new ArrayList<>();
        detalles.add(ex.getLocalizedMessage());

        ErrorDTO errorDTO = new ErrorDTO("Ocurrió un error interno del servidor",detalles, LocalDateTime.now());

        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
