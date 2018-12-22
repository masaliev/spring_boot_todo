package com.github.masaliev.passport.config;

import com.github.masaliev.passport.Utils;
import com.github.masaliev.passport.exceptions.DuplicateUsernameException;
import com.github.masaliev.passport.exceptions.ValidationException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(value = {ValidationException.class})
    public ResponseEntity validationException(ValidationException e, WebRequest request) {

        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        Map<String, String> errors = new HashMap<>(fieldErrors.size());

        fieldErrors.forEach((error) -> {
            String key = Utils.camelCaseToSnakeCase(error.getField());
            if (!errors.containsKey(key)) {
                errors.put(key, error.getDefaultMessage());
            }
        });

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(value = {DuplicateUsernameException.class})
    public ResponseEntity duplicateUsernameException(DuplicateUsernameException e, WebRequest request) {
        return ResponseEntity.badRequest().body(Collections.singletonMap("username", e.getMessage()));
    }
}
