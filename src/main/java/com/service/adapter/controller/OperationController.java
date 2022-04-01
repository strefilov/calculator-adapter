package com.service.adapter.controller;

import com.service.adapter.exception.RequestValueException;
import com.service.adapter.mapper.Mapper;
import com.service.adapter.model.CorrelationIdRequest;
import com.service.adapter.model.CorrelationIdResponse;
import com.service.adapter.model.OperationRequest;
import com.service.adapter.model.OperationResponse;
import com.service.adapter.service.CalculatorService;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@RequestMapping("/calculator")
public class OperationController {

    private Validator requestValidator;

    private CalculatorService calculatorService;

    private Mapper mapper;

    public OperationController(CalculatorService calculatorService, Mapper mapper,
        @Qualifier("operationRequestValidator") Validator requestValidator) {
        this.calculatorService = calculatorService;
        this.mapper = mapper;
        this.requestValidator = requestValidator;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(requestValidator);
    }

    @RolesAllowed("user")
    @PostMapping(path = "/add", produces = "application/json")
    public CorrelationIdResponse addNumbers(@RequestBody @Valid OperationRequest request, BindingResult bindingResult,
        @RequestHeader(value = "Authorization", required = false) String Authorization) {
        if (bindingResult.hasErrors())
            throw new RequestValueException(
                mapper.objectErrorsToValidationErrors(
                    bindingResult.getAllErrors()
                )
            );
        String correlationId = genarationId();
        calculatorService.add(
            Integer.valueOf(request.getFirstNumber()),
            Integer.valueOf(request.getSecondNumber()),
            correlationId
        );
        return new CorrelationIdResponse(correlationId);
    }

    @RolesAllowed("user")
    @PostMapping(path = "/addresult", produces = "application/json")
    public OperationResponse addResult(@RequestBody @Valid CorrelationIdRequest request, BindingResult bindingResult,
        @RequestHeader(value = "Authorization", required = false) String Authorization) {
        if (bindingResult.hasErrors())
            throw new RequestValueException(
                mapper.objectErrorsToValidationErrors(
                    bindingResult.getAllErrors()
                )
            );
        return new OperationResponse(calculatorService.getResoult4add(request.getCorrelationId()));
    }

    @RolesAllowed("user")
    @PostMapping(path = "/divideresult", produces = "application/json")
    public OperationResponse divideNumbersResult(@RequestBody @Valid CorrelationIdRequest request,
        BindingResult bindingResult, @RequestHeader(value = "Authorization", required = false) String Authorization) {
        if (bindingResult.hasErrors())
            throw new RequestValueException(
                mapper.objectErrorsToValidationErrors(
                    bindingResult.getAllErrors()
                )
            );
        return new OperationResponse(calculatorService.getResoult4divide(request.getCorrelationId()));
    }

    @RolesAllowed("user")
    @PostMapping(path = "/multiplyresult", produces = "application/json")
    public OperationResponse multiplyNumbersResult(@RequestBody @Valid CorrelationIdRequest request,
        BindingResult bindingResult, @RequestHeader(value = "Authorization", required = false) String Authorization) {
        if (bindingResult.hasErrors())
            throw new RequestValueException(
                mapper.objectErrorsToValidationErrors(
                    bindingResult.getAllErrors()
                )
            );
        return new OperationResponse(calculatorService.getResoult4multiply(request.getCorrelationId()));
    }

    @RolesAllowed("user")
    @PostMapping(path = "/subtractresult", produces = "application/json")
    public OperationResponse subtractNumbersResult(@RequestBody @Valid CorrelationIdRequest request,
        BindingResult bindingResult, @RequestHeader(value = "Authorization", required = false) String Authorization) {
        if (bindingResult.hasErrors())
            throw new RequestValueException(
                mapper.objectErrorsToValidationErrors(
                    bindingResult.getAllErrors()
                )
            );
        return new OperationResponse(calculatorService.getResoult4subtract(request.getCorrelationId()));
    }

    private String genarationId() {
        return java.util.UUID.randomUUID().toString();
    }

    @RolesAllowed("user")
    @PostMapping(path = "/divide", produces = "application/json")
    public CorrelationIdResponse divideNumbers(@RequestBody @Valid OperationRequest request,
        BindingResult bindingResult, @RequestHeader(value = "Authorization", required = false) String Authorization) {
        if (bindingResult.hasErrors())
            throw new RequestValueException(
                mapper.objectErrorsToValidationErrors(
                    bindingResult.getAllErrors()
                )
            );
        String correlationId = genarationId();
        calculatorService.divide(
            Integer.valueOf(request.getFirstNumber()),
            Integer.valueOf(request.getSecondNumber()),
            correlationId
        );
        return new CorrelationIdResponse(correlationId);
    }

    @RolesAllowed("user")
    @PostMapping(path = "/multiply", produces = "application/json")
    public CorrelationIdResponse multiplyNumbers(@RequestBody @Valid OperationRequest request,
        BindingResult bindingResult, @RequestHeader(value = "Authorization", required = false) String Authorization) {
        if (bindingResult.hasErrors())
            throw new RequestValueException(
                mapper.objectErrorsToValidationErrors(
                    bindingResult.getAllErrors()
                )
            );
        String correlationId = genarationId();
        calculatorService.multiply(
            Integer.valueOf(request.getFirstNumber()),
            Integer.valueOf(request.getSecondNumber()),
            correlationId
        );
        return new CorrelationIdResponse(correlationId);
    }

    @RolesAllowed("user")
    @PostMapping(path = "/subtract", produces = "application/json")
    public CorrelationIdResponse subtractNumbers(@RequestBody @Valid OperationRequest request,
        BindingResult bindingResult, @RequestHeader(value = "Authorization", required = false) String Authorization) {
        if (bindingResult.hasErrors())
            throw new RequestValueException(
                mapper.objectErrorsToValidationErrors(
                    bindingResult.getAllErrors()
                )
            );
        String correlationId = genarationId();
        calculatorService.subtract(
            Integer.valueOf(request.getFirstNumber()),
            Integer.valueOf(request.getSecondNumber()),
            correlationId
        );
        return new CorrelationIdResponse(correlationId);
    }

}
