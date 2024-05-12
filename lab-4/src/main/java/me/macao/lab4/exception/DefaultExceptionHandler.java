package me.macao.lab4.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@ControllerAdvice
public class DefaultExceptionHandler {
    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<ApiError>
    handleInvalidOperationException(
            final InvalidOperationException e,
            final HttpServletRequest request
    ) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ApiError>
    handleObjectNotFoundException(
            final ObjectNotFoundException e,
            final HttpServletRequest request
    ) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError>
    handleAccessDeniedException(
            final AccessDeniedException e,
            final HttpServletRequest request
    ) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(EmailCreateException.class)
    public ResponseEntity<ApiError>
    handleEmailCreateException(
            final EmailCreateException e,
            final HttpServletRequest request
    ) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(PasswordCreateException.class)
    public ResponseEntity<ApiError>
    handlePasswordCreateException(
            final PasswordCreateException e,
            final HttpServletRequest request
    ) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiError>
    handleNoSuchElementException(
            final NoSuchElementException e,
            final HttpServletRequest request
    ) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                "No such elem exept " + e.getMessage(),
                HttpStatus.NO_CONTENT.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.NO_CONTENT);
    }
}
