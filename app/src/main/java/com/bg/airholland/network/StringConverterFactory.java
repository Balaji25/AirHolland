package com.bg.airholland.network;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.http.FormUrlEncoded;

public final class StringConverterFactory extends Converter.Factory {
    private StringConverterFactory() {
    }

    public static StringConverterFactory create() {
        return new StringConverterFactory();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (isFormEncoded(annotations)) {
            return new StringResponseConverter();
        }
        return null;
    }

    private boolean isFormEncoded(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(FormUrlEncoded.class)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        if (isFormEncoded(parameterAnnotations)) {
            return new StringRequestConverter();
        }
        return null;
    }
}
