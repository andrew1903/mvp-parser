package ua.andrew1903.mostvaluableperson.exception;

import lombok.experimental.StandardException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Used whenever a logical error occurs while parsing a file.
 * @status 422 Unprocessable Entity
 */
@StandardException
@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class ParseException extends RuntimeException {
}
