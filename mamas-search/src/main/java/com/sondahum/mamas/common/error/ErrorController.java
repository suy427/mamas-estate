package com.sondahum.mamas.common.error;

import com.sondahum.mamas.common.error.exception.NoSuchEntityException;
import com.sondahum.mamas.common.error.exception.EntityAlreadyExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ResponseBody
@RestControllerAdvice
class ErrorController {


    /**********************************
     *
    *       ENTITY NOT FOUND
     *
    * *********************************/
    @ExceptionHandler(value = {NoSuchEntityException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ErrorResponse userNotFoundExceptionHandler(NoSuchEntityException e) {
        final ErrorCode userNotFound = ErrorCode.CAN_NOT_FOUND_SUCH_USER;
        log.error(userNotFound.getMessage(), e.getId());
        return buildError(userNotFound);
    }

    @ExceptionHandler(value = {NoSuchEntityException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ErrorResponse estateNotFoundExceptionHandler(NoSuchEntityException e) {
        final ErrorCode estateNotFound = ErrorCode.CAN_NOT_FOUND_SUCH_ESTATE;
        log.error(estateNotFound.getMessage(), e.getId());
        return buildError(estateNotFound);
    }

    @ExceptionHandler(value = {NoSuchEntityException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ErrorResponse bidNotFoundExceptionHandler(NoSuchEntityException e) {
        final ErrorCode bidNotFound = ErrorCode.CAN_NOT_FOUND_SUCH_BID;
        log.error(bidNotFound.getMessage(), e.getId());
        return buildError(bidNotFound);
    }

    @ExceptionHandler(value = {NoSuchEntityException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ErrorResponse contractNotFoundExceptionHandler(NoSuchEntityException e) {
        final ErrorCode contractNotFound = ErrorCode.CAN_NOT_FOUND_SUCH_CONTRACT;
        log.error(contractNotFound.getMessage(), e.getId());
        return buildError(contractNotFound);
    }




    /**********************************
     *
     *       DUPLICATED ENTITY
     *
     * *********************************/
    @ExceptionHandler(EntityAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse userAlreadyExistExceptionHandler(EntityAlreadyExistException e) {
        final ErrorCode dupUser = ErrorCode.DUPLICATED_USER;
        log.error(dupUser.getMessage(), e.getName()); // todo 여기서 getField()를 지웠다..
        return buildError(dupUser);
    }

    @ExceptionHandler(EntityAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse estateAlreadyExistExceptionHandler(EntityAlreadyExistException e) {
        final ErrorCode dupEstate = ErrorCode.DUPLICATED_USER;
        log.error(dupEstate.getMessage(), e.getName());
        return buildError(dupEstate);
    }

    @ExceptionHandler(EntityAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse bidAlreadyExistExceptionHandler(EntityAlreadyExistException e) {
        final ErrorCode dupBid = ErrorCode.DUPLICATED_USER;
        log.error(dupBid.getMessage(), e.getName());
        return buildError(dupBid);
    }

    @ExceptionHandler(EntityAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse contractAlreadyExistExceptionHandler(EntityAlreadyExistException e) {
        final ErrorCode dupContract = ErrorCode.DUPLICATED_USER;
        log.error(dupContract.getMessage(), e.getName());
        return buildError(dupContract);
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse invalidInputExceptionHandler(MethodArgumentNotValidException e) {
        final List<ErrorResponse.FieldError> fieldErrors = getFieldErrors(e.getBindingResult());
        return buildFieldErrors(ErrorCode.ILLEGAL_INPUT, fieldErrors);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse bindExceptionHandler(BindException e) {
        final List<ErrorResponse.FieldError> fieldErrors = getFieldErrors(e.getBindingResult());
        return buildFieldErrors(ErrorCode.ILLEGAL_INPUT, fieldErrors);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse dataIntegrityViolationExceptionHandler(DataIntegrityViolationException e) {
        log.error(e.getMessage());
        return buildError(ErrorCode.ILLEGAL_INPUT);
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
