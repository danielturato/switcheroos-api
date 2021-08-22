package online.switcheroos.accounts.exception;

import online.switcheroos.accounts.dto.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class AccountExceptionHandler {

    @ExceptionHandler(value = AccountAlreadyExistsException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ExceptionResponse accountAlreadyExistsException(AccountAlreadyExistsException ex, HttpServletRequest request) {
        return new ExceptionResponse(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                "Please select a different email or username on registration",
                request.getServletPath()
        );
    }

    @ExceptionHandler(value = AccountNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ExceptionResponse accountNotFoundException(AccountNotFoundException ex, HttpServletRequest request) {
        return new ExceptionResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                "The account resource you've requested does not exist",
                request.getServletPath()
        );
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ExceptionResponse illegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        return new ExceptionResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                ex.getMessage(),
                "You've used an illegal argument within this request",
                request.getServletPath()
        );
    }

    @ExceptionHandler(value = NullPointerException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ExceptionResponse nullPointerException(NullPointerException ex, HttpServletRequest request) {
        return new ExceptionResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                ex.getMessage(),
                "You've included a null value within this request",
                request.getServletPath()
        );
    }

}
