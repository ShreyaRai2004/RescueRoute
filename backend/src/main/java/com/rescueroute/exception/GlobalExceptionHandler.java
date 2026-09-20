package com.rescueroute.exception;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<?> notFound(NotFoundException e){return ResponseEntity.status(404).body(Map.of("error",e.getMessage()));}
    @ExceptionHandler(BusinessException.class)
    ResponseEntity<?> business(BusinessException e){return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));}
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<?> validation(MethodArgumentNotValidException e){return ResponseEntity.badRequest().body(Map.of("error","Invalid request data")); }
}
