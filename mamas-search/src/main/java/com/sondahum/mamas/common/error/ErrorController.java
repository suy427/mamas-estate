package com.sondahum.mamas.common.error;

import com.sondahum.mamas.common.error.exception.NoSuchEntityException;
import com.sondahum.mamas.common.error.exception.EntityAlreadyExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@ResponseBody
class ErrorController {

    private static final Logger logger =  LoggerFactory.getLogger(ErrorController.class);


    @ExceptionHandler(value = {NoSuchEntityException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ErrorResponse entityNotFoundExceptionHandler(NoSuchEntityException e) {
        final ErrorCode notFoundEntity = ErrorCode.CAN_NOT_FOUND_SUCH_ENTITY;
        logger.error(notFoundEntity.getMessage(), e.getId());
        return buildError(notFoundEntity);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse invalidInputExceptionHandler(MethodArgumentNotValidException e) {
        final List<ErrorResponse.FieldError> fieldErrors = getFieldErrors(e.getBindingResult());
        return buildFieldErrors(ErrorCode.INVALID_INPUT, fieldErrors);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse bindExceptionHandler(BindException e) {
        final List<ErrorResponse.FieldError> fieldErrors = getFieldErrors(e.getBindingResult());
        return buildFieldErrors(ErrorCode.INVALID_INPUT, fieldErrors);
    }


    @ExceptionHandler(EntityAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse entityAlreadyExistExceptionHandler(EntityAlreadyExistException e) {
        final ErrorCode errorCode = ErrorCode.DUPLICATED_ENTITY;
        logger.error(errorCode.getMessage(), e.getName()); // todo 여기서 getField()를 지웠다..
        return buildError(errorCode);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse dataIntegrityViolationExceptionHandler(DataIntegrityViolationException e) {
        logger.error(e.getMessage());
        return buildError(ErrorCode.INVALID_INPUT);
    }


    private List<ErrorResponse.FieldError> getFieldErrors(BindingResult bindingResult) {
        final List<FieldError> errors = bindingResult.getFieldErrors();

        return errors.parallelStream()
                .map(error -> ErrorResponse.FieldError.builder()
                        .reason(error.getDefaultMessage())
                        .field(error.getField())
                        .value((String) error.getRejectedValue())
                        .build())
                .collect(Collectors.toList());
    }

    private ErrorResponse buildError(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .code(errorCode.getCode())
                .status(errorCode.getStatus())
                .message(errorCode.getMessage())
                .build();
    }

    private ErrorResponse buildFieldErrors(ErrorCode errorCode, List<ErrorResponse.FieldError> errors) {
        return ErrorResponse.builder()
                .code(errorCode.getCode())
                .status(errorCode.getStatus())
                .message(errorCode.getMessage())
                .errors(errors)
                .build();
    }

}
