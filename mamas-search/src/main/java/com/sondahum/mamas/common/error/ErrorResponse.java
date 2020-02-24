package com.sondahum.mamas.common.error;

import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public class ErrorResponse {
    public ErrorResponse(String message, String code, int status, List<FieldError> errors) {
        this.message = message;
        this.code = code;
        this.status = status;
        this.errors = initErrors(errors);
    }

    private List<FieldError> initErrors(List<FieldError> errors) {
        return ((List<FieldError>) ((errors == null) ? new ArrayList<Object>() : errors));
    }

    private String message;
    private String code;
    private int status;
    private List<FieldError> errors = new ArrayList<FieldError>();

    public static class FieldError {
        public FieldError(String field, String value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        private String field;
        private String value;
        private String reason;
    }
}
