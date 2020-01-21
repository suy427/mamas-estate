package com.sondahum.mamas.elasticsearch.dto

abstract class EsDto implements Serializable { // 데이터를 조각내서 보내지 않고 Object단위로 보낼수있게 해준다고..?

    List<?> getItemList() {}

}
