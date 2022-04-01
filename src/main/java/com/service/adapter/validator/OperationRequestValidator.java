package com.service.adapter.validator;

import com.service.adapter.model.CorrelationIdRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.service.adapter.model.OperationRequest;


@Component
public class OperationRequestValidator implements Validator {


    private static final String VALUE_SHOULD_HAVE_TYPE_INT = "value must be type int";
    private static final String VALUE_MUST_NOT_BE_NULL = "value must be set";
    private static final String VALUE_CANNOT_BE_EMPTY = "value can't be null";

    private static final String VALUE_NEGATIVE = "value.negative";

    private static final String FIRST_NUMBER = "firstNumber";
    private static final String SECOND_NUMBER = "secondNumber";
    private static final String correlationId = "correlationId";

    private static final String VALUE_EMPTY = "";


    @Override
    public boolean supports(Class<?> aClass) {
        return OperationRequest.class.equals(aClass) || CorrelationIdRequest.class.equals(aClass) ;
    }

    @Override
    public void validate(Object obj, Errors errors) {
        if(obj instanceof OperationRequest ){
        OperationRequest request = (OperationRequest) obj;
            fieldValidate(FIRST_NUMBER, request.getFirstNumber(), errors);
            fieldValidate(SECOND_NUMBER, request.getSecondNumber(), errors);
        }
        if(obj instanceof CorrelationIdRequest){
            CorrelationIdRequest request = (CorrelationIdRequest) obj;
            fieldValidateString(correlationId, request.getCorrelationId(),errors);
        }
    }

    private void fieldValidate(String fieldName, String fieldValue, Errors errors){
        if (fieldValue == null) {
            errors.rejectValue(fieldName, VALUE_NEGATIVE, VALUE_MUST_NOT_BE_NULL);
        } else if (fieldValue.equals(VALUE_EMPTY)) {
            errors.rejectValue(fieldName, VALUE_NEGATIVE, VALUE_CANNOT_BE_EMPTY);
        } else if (!isInteger(fieldValue)) {
            errors.rejectValue(fieldName, VALUE_NEGATIVE, VALUE_SHOULD_HAVE_TYPE_INT);
        }
    }
    private void fieldValidateString(String fieldName, String fieldValue, Errors errors){
        if (fieldValue == null) {
            errors.rejectValue(fieldName, VALUE_NEGATIVE, VALUE_MUST_NOT_BE_NULL);
        } else if (fieldValue.equals(VALUE_EMPTY)) {
            errors.rejectValue(fieldName, VALUE_NEGATIVE, VALUE_CANNOT_BE_EMPTY);
        }
    }
    private boolean isInteger(String str)
    {
        try {
            Integer.parseInt(str);
            return true;
        }
        catch(NumberFormatException e) {
            return false;
        }
    }
}