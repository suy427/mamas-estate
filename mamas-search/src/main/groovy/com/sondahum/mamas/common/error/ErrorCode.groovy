package com.sondahum.mamas.common.error

enum ErrorCode {

    DUPLICATED_ENTITY_ERROR(1,"THIS ENTITY IS ALREADY EXISTS."),
    NOT_FOUND_SUCH_ENTITY(2,"CAN NOT FOUND SUCH ENTITY."),


    private final int code
    private final String msg
    private static final LinkedHashMap<Integer, ErrorCode> valueMap = [:]
    private static final LinkedHashMap<String, ErrorCode> nameMap = [:]

    static {
        for (ErrorCode errorCode : values()) {
            valueMap.put(errorCode.code, errorCode)
            nameMap.put(errorCode.msg, errorCode)
        }
    }

    ErrorCode(int code, String msg) {
        this.code = code
        this.msg = msg
    }

    static ErrorCode findByValue(int value) {
        return valueMap[value]
    }

    static ErrorCode findByName(String name) {
        return nameMap[name]
    }

}