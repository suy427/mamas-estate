package com.sondahum.mamas.common.config;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.data.domain.Sort;

import java.io.IOException;

public class SortSerializer extends JsonSerializer<Sort> {

    @Override
    public void serialize(Sort sort, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
            gen.writeFieldName("sort");
            gen.writeStartArray();
                for (Sort.Order order : sort) {
                    gen.writeStartObject();
                    gen.writeStringField("property", order.getProperty());
                    gen.writeStringField("direction", order.getDirection().name());
                    gen.writeStringField("ignoreCase", String.valueOf(order.isIgnoreCase()));
                    gen.writeStringField("nullHandling", order.getNullHandling().name());
                    gen.writeStringField("ascending", String.valueOf(order.isAscending()));
                    gen.writeEndObject();
                }
            gen.writeEndArray();
        gen.writeEndObject();
    }
}
