package com.sondahum.mamas.error;

import com.sondahum.mamas.error.exception.NoSuchEntityException;
import com.sondahum.mamas.bid.exception.BidAlreadyExistException;
import com.sondahum.mamas.contract.exception.ContractAlreadyExistException;
import com.sondahum.mamas.estate.exception.EstateAlreadyExistException;
import com.sondahum.mamas.client.exception.UserAlreadyExistException;
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
public class ErrorController {


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


    /**********************************
     *
     *       DUPLICATED ENTITY
     *
     * *********************************/
    @ExceptionHandler(UserAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse userAlreadyExistExceptionHandler(UserAlreadyExistException e) {
        final ErrorCode dupUser = ErrorCode.DUPLICATED_USER;
        log.error(dupUser.getMessage(), e.getMessage()); // todo 여기서 getField()를 지웠다..
        return buildError(dupUser);
    }

    @ExceptionHandler(EstateAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse estateAlreadyExistExceptionHandler(EstateAlreadyExistException e) {
        final ErrorCode dupEstate = ErrorCode.DUPLICATED_ESTATE;
        log.error(dupEstate.getMessage(), e.getMessage());
        return buildError(dupEstate);
    }

    @ExceptionHandler(BidAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse bidAlreadyExistExceptionHandler(BidAlreadyExistException e) {
        final ErrorCode dupBid = ErrorCode.DUPLICATED_BID;
        log.error(dupBid.getMessage(), e.getMessage());
        return buildError(dupBid);
    }

    @ExceptionHandler(ContractAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse contractAlreadyExistExceptionHandler(ContractAlreadyExistException e) {
        final ErrorCode dupContract = ErrorCode.DUPLICATED_CONTRACT;
        log.error(dupContract.getMessage(), e.getMessage());
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
