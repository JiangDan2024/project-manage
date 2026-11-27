package com.plm.project.utils;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ValidatorUtils {
    @Autowired
    Validator globalValidator;

    public <T> void validate(T object) throws ValidationException {
        Set<ConstraintViolation<T>> validate = globalValidator.validate(object);
        if(!validate.isEmpty()){
            throw new ValidationException(validate.iterator().next().getMessage());
        }
    }
}
