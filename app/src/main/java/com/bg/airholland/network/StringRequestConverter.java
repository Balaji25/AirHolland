package com.bg.airholland.network;

import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

final class StringRequestConverter implements Converter<String, RequestBody> {
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    @Override
    public RequestBody convert(String value) {
        return RequestBody.create(MEDIA_TYPE, value.getBytes());
    }
}
