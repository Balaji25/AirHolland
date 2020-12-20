package com.bg.airholland.network;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.ResponseBody;
import retrofit2.Converter;


final class StringResponseConverter implements Converter<ResponseBody, String> {

    public static String fromStream(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder out = new StringBuilder();
        String newLine = System.getProperty("line.separator");
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
            out.append(newLine);
        }
        return out.toString();
    }

    @Override
    public String convert(ResponseBody value) {
        try {
            return fromStream(value.byteStream());
        } catch (IOException e) {
            Log.e("",e.getMessage());
        }
        return "Error";
    }
}
