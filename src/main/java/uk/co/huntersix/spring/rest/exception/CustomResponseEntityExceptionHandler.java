package uk.co.huntersix.spring.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.Optional;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PersonNotFoundException.class)
    public final ResponseEntity<Object> handlePersonNotFoundException(PersonNotFoundException ex, WebRequest request) throws Exception {
        String description = request.getDescription(false);
        ExceptionResponse exRes = new ExceptionResponse(new Date(), HttpStatus.NOT_FOUND.value(), Optional.ofNullable(ex.getCause()).isPresent() ? ex.getCause().toString() : "null", ex.getMessage(), description.split("=")[1]);
        return new ResponseEntity(exRes, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PersonExistException.class)
    public final ResponseEntity<Object> handlePersonExistException(PersonExistException ex, WebRequest request) throws Exception {
        String description = request.getDescription(false);
        ExceptionResponse exRes = new ExceptionResponse(new Date(), HttpStatus.CONFLICT.value(), Optional.ofNullable(ex.getCause()).isPresent() ? ex.getCause().toString() : "null", ex.getMessage(), description.split("=")[1]);
        return new ResponseEntity(exRes, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<Object> handleBadRequestException(BadRequestException ex, WebRequest request) throws Exception {
        String description = request.getDescription(false);
        ExceptionResponse exRes = new ExceptionResponse(new Date(), HttpStatus.BAD_REQUEST.value(), Optional.ofNullable(ex.getCause()).isPresent() ? ex.getCause().toString() : "null", ex.getMessage(), description.split("=")[1]);
        return new ResponseEntity(exRes, HttpStatus.BAD_REQUEST);
    }
}
