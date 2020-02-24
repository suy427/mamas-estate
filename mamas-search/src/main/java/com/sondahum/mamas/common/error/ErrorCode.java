package com.sondahum.mamas.common.error;

import lombok.Getter;

import java.util.LinkedHashMap;

@Getter
public enum ErrorCode { // TODO 코드 정리하기
    DUPLICATED_ENTITY("DUP-001", "THIS ENTITY IS ALREADY EXISTS.", 400)
    , NOT_FOUND_SUCH_ENTITY("NOF-001", "CAN NOT FOUND SUCH ENTITY.", 404)
    , INVALID_INPUT("INV-001", "INVALID INPUT HAS COME.", 400);

    private final String code;
    private final String message;
    private final int status;
    private static final LinkedHashMap<String, ErrorCode> codeMap = new LinkedHashMap<String, ErrorCode>();
    private static final LinkedHashMap<String, ErrorCode> nameMap = new LinkedHashMap<String, ErrorCode>();

    static {
        for (ErrorCode errCode : values()) {
            codeMap.put(errCode.code, errCode);
            nameMap.put(errCode.message, errCode);
        }
    }

    ErrorCode(String code, String message, int status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public static ErrorCode findByCode(int code) {
        return codeMap.get(code);
    }

    public static ErrorCode findByName(String name) {
        return nameMap.get(name);
    }


}
