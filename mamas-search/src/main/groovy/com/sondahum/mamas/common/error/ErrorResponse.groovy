package com.sondahum.mamas.common.error

import groovy.transform.builder.Builder

@Builder
class ErrorResponse {

    private String message;
    private String code;
    private int status;
    private List<FieldError> errors = new ArrayList<>();

    ErrorResponse(String message, String code, int status, List<FieldError> errors) {
        this.message = message;
        this.code = code;
        this.status = status;
        this.errors = initErrors(errors);
    }

    private List<FieldError> initErrors(List<FieldError> errors) {
        return (errors == null) ? new ArrayList<>() : errors;
    }

    static class FieldError {
        private String field;
        private String value;
        private String reason;

        FieldError(String field, String value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }
    }

}
