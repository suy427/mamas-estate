package com.sondahum.mamas.common.error;

import java.util.LinkedHashMap;

public enum ErrorCode {
    DUPLICATED_ENTITY_ERROR(1, "THIS ENTITY IS ALREADY EXISTS.")
    , NOT_FOUND_SUCH_ENTITY(2, "CAN NOT FOUND SUCH ENTITY.");

    private final int code;
    private final String name;
    private static final LinkedHashMap<Integer, ErrorCode> codeMap = new LinkedHashMap<Integer, ErrorCode>();
    private static final LinkedHashMap<String, ErrorCode> nameMap = new LinkedHashMap<String, ErrorCode>();

    static {
        for (ErrorCode errCode : values()) {
            codeMap.put(errCode.code, errCode);
            nameMap.put(errCode.name, errCode);
        }
    }

    ErrorCode(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static ErrorCode findByCode(int code) {
        return codeMap.get(code);
    }

    public static ErrorCode findByName(String name) {
        return nameMap.get(name);
    }


}
