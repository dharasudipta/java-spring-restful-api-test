package uk.co.huntersix.spring.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PersonNotFoundException extends RuntimeException {
    /**
     * @param message
     * @param cause
     */
    public PersonNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
