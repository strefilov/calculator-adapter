package com.service.adapter.mapper;

import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import com.service.adapter.model.error.Error;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Mapper {

    public List<Error> objectErrorsToValidationErrors(List<ObjectError> errors){
        return errors.stream()
                .map(error -> error instanceof FieldError ?
                        new Error(((FieldError) error).getField() + " " + error.getDefaultMessage()) :
                        new Error(error.getDefaultMessage())).collect(Collectors.toList());
    }

}
