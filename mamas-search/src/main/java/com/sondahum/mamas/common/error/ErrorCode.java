package com.sondahum.mamas.common.error;


import lombok.Getter;

import java.util.LinkedHashMap;

@Getter
public enum ErrorCode { // TODO 코드 정리하기

    /* duplicated entity */
    DUPLICATED_USER("DUP-001", "THIS USER ALREADY EXISTS.", 400)
    ,DUPLICATED_ESTATE("DUP-002", "THIS ESTATE ALREADY EXISTS.", 400)
    ,DUPLICATED_BID("DUP-003", "THIS BID ALREADY EXISTS.", 400)
    ,DUPLICATED_CONTRACT("DUP-004", "THIS CONTRACT ALREADY EXISTS.", 400)

    /* entity not found */
    , CAN_NOT_FOUND_SUCH_USER("NOF-001", "CAN NOT FOUND SUCH USER.", 404)
    , CAN_NOT_FOUND_SUCH_ESTATE("NOF-002", "CAN NOT FOUND SUCH ESTATE.", 404)
    , CAN_NOT_FOUND_SUCH_BID("NOF-003", "CAN NOT FOUND SUCH BID.", 404)
    , CAN_NOT_FOUND_SUCH_CONTRACT("NOF-004", "CAN NOT FOUND SUCH CONTRACT.", 404)

    /* input valid check violation */
    , ILLEGAL_INPUT("ILL-001", "ILLEGAL INPUT HAS COME.", 400);

    private final String code;
    private final String message;
    private final int status;



    ErrorCode(String code, String message, int status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

}
