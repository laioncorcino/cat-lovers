package com.corcino.catlovers.error.handler;

import com.corcino.catlovers.error.StandardError;
import com.corcino.catlovers.error.ValidationError;
import com.corcino.catlovers.error.exception.BadRequestException;
import com.corcino.catlovers.error.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<StandardError> handleNotFound(NotFoundException notFoundException) {
        StandardError error = StandardError.builder()
                .title("Object Not Found Exception. Check documentation")
                .status(HttpStatus.NOT_FOUND.value())
                .errorMessage(notFoundException.getMessage())
                .developerMessage(notFoundException.getClass().getName())
                .dateTime(getDateTime())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<StandardError> handleNotFound(BadRequestException badRequestException) {
        StandardError error = StandardError.builder()
                .title("Bad Request. Check documentation")
                .status(HttpStatus.NOT_FOUND.value())
                .errorMessage(badRequestException.getMessage())
                .developerMessage(badRequestException.getClass().getName())
                .dateTime(getDateTime())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> handleInternalException(Exception exception) {
        StandardError error = StandardError.builder()
                .title("Internal error in server")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .errorMessage(exception.getMessage())
                .developerMessage(exception.getClass().getName())
                .dateTime(getDateTime())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(Error.class)
    public ResponseEntity<StandardError> handleInternalError(Error err) {
        StandardError error = StandardError.builder()
                .title("Internal error in server ")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .errorMessage(err.getMessage())
                .developerMessage(err.getClass().getName())
                .dateTime(getDateTime())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        List<ValidationError> standardErrors = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        fieldErrors.forEach(fieldError -> {
            standardErrors.add(buildErrors(exception, status, fieldError));
            log.error("Erro de validação no campo " + fieldError.getField() + " para se criar ou atualizar recurso");
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardErrors);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        StandardError error = StandardError.builder()
                .title("Method not allowed. Check documentation")
                .status(status.value())
                .errorMessage(ex.getMessage())
                .developerMessage(ex.getClass().getName())
                .dateTime(getDateTime())
                .build();

        log.error("Method not allowed " + ex.getClass());

        return ResponseEntity.status(status.value()).headers(headers).body(error);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception exception, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        StandardError error = StandardError.builder()
                .title(exception.getCause().getMessage())
                .status(status.value())
                .errorMessage(exception.getMessage())
                .developerMessage(exception.getClass().getName())
                .dateTime(getDateTime())
                .build();

        log.error("Internal error in server " + exception.getClass());

        return ResponseEntity.status(status.value()).headers(headers).body(error);
    }

    private ValidationError buildErrors(MethodArgumentNotValidException exception, HttpStatus status, FieldError fieldError) {
        return ValidationError.builder()
                .title("Bad Request Exception. Invalid fields.")
                .status(status.value())
                .errorMessage(fieldError.getDefaultMessage())
                .developerMessage(exception.getClass().getName())
                .dateTime(getDateTime())
                .field(fieldError.getField())
                .build();
    }

    private LocalDateTime getDateTime() {
        return LocalDateTime.now();
    }

}
