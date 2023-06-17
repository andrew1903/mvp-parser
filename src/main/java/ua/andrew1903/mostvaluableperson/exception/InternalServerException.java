package ua.andrew1903.mostvaluableperson.exception;

import lombok.experimental.StandardException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Used whenever an unexpected error occurs.
 * @status 500 Internal Server Error
 */
@StandardException
@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerException extends RuntimeException {
}
