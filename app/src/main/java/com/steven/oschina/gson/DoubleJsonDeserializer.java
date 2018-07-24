package com.steven.oschina.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.greenfarm.client.base_library.log.LogUtils;

import java.lang.reflect.Type;


public class DoubleJsonDeserializer implements JsonDeserializer<Double> {
    @Override
    public Double deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            return json.getAsDouble();
        } catch (Exception e) {
            LogUtils.i("DoubleJsonDeserializer-deserialize-error:" + (json != null ? json.toString() : ""));
            return 0D;
        }
    }
}
