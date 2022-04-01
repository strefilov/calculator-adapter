package com.service.adapter.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.service.adapter.model.error.Error;


@Data
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class SoapResultException extends RuntimeException {

    private Error error;

    public SoapResultException(Error error) {
        this.error = error;
    }
}
