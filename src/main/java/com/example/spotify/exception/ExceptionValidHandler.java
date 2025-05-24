package com.example.spotify.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@ControllerAdvice
public class ExceptionValidHandler {

    @Autowired
    private MessageSource messageSource;

    // Обработка @Valid с BindingResult (например, TrackController)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationException(MethodArgumentNotValidException ex, Model model) {
        Locale locale = LocaleContextHolder.getLocale();
        List<String> errorMessages = new ArrayList<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            String message = messageSource.getMessage(error.getDefaultMessage(), null, locale);
            errorMessages.add(error.getField() + ": " + message);
        }

        model.addAttribute("errorMessage", String.join("<br>", errorMessages));
        return "error";
    }

    // Обработка валидации в параметрах (@Validated в контроллере)
    @ExceptionHandler(ConstraintViolationException.class)
    public String handleConstraintViolation(ConstraintViolationException ex, Model model) {
        Locale locale = LocaleContextHolder.getLocale();
        List<String> errorMessages = new ArrayList<>();

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String message = messageSource.getMessage(violation.getMessage(), null, locale);
            errorMessages.add(violation.getPropertyPath() + ": " + message);
        }

        model.addAttribute("errorMessage", String.join("<br>", errorMessages));
        return "error";
    }

    // Общий fallback для всех других исключений
    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex, HttpServletRequest request, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("url", request.getRequestURI());
        return "error";
    }
}
