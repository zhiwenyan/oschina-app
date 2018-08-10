package com.steven.oschina.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.oschina.client.base_library.log.LogUtils;

import java.lang.reflect.Type;


public class StringJsonDeserializer implements JsonDeserializer<String> {
    @Override
    public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            return json.getAsString();
        } catch (Exception e) {
            LogUtils.i("StringJsonDeserializer-deserialize-error:" + (json != null ? json.toString() : ""));
            return null;
        }
    }
}
