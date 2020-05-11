package com.sondahum.mamas.common.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class SortDeserializer extends JsonDeserializer {
    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return null;
    }
}
