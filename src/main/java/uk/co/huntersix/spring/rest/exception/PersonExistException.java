package uk.co.huntersix.spring.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PersonExistException extends RuntimeException {
    /**
     * @param message
     * @param cause
     */
    public PersonExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
