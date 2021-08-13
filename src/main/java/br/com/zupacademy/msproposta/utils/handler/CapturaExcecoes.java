package br.com.zupacademy.msproposta.utils.handler;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class CapturaExcecoes {

    private final MessageSource messageSource;

    public CapturaExcecoes(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErrorResponse> capturaCamposInvalidos(MethodArgumentNotValidException e) {
        List<ErrorResponse> errors = new ArrayList<>();

        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        fieldErrors.forEach(error -> {
            String msg = messageSource.getMessage(error, LocaleContextHolder.getLocale());
            ErrorResponse er = new ErrorResponse(error.getField(), msg);
            errors.add(er);
        });

        return errors;
    }
}
