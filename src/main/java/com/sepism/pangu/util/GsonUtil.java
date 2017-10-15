package com.sepism.pangu.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil {
    // For @Expose: This annotation has no effect unless you build Gson with a GsonBuilder and invoke
    // GsonBuilder.excludeFieldsWithoutExposeAnnotation() method. What a pity!
    // TODO: By using ExposeEnabled Gson, it is easy to  hide some and `expose` only some of the fields to the client
    // https://static.javadoc.io/com.google.code.gson/gson/2.6.2/index.html?com/google/gson/annotations/Expose.html
    private static Gson NORMAL_GSON = new Gson();
    private static Gson ENABLE_EXPOSE_GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    public static Gson getGson() {
        return NORMAL_GSON;
    }

    public static Gson getGsonWithExposeEnabled() {
        return ENABLE_EXPOSE_GSON;
    }
}
