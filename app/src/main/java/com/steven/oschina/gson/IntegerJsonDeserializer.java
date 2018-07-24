package com.steven.oschina.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.greenfarm.client.base_library.log.LogUtils;

import java.lang.reflect.Type;


public class IntegerJsonDeserializer implements JsonDeserializer<Integer> {
    @Override
    public Integer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            return json.getAsInt();
        } catch (Exception e) {
            LogUtils.i("IntegerJsonDeserializer-deserialize-error:" + (json != null ? json.toString() : ""));
            return 0;
        }
    }
}
