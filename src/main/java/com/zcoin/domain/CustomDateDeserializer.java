package com.zcoin.domain;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class CustomDateDeserializer extends StdDeserializer<Date> {

   // private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);

    public CustomDateDeserializer() {
        this(null);
    }

    public CustomDateDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Date deserialize(JsonParser jsonparser, DeserializationContext context) throws IOException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        String date = jsonparser.getText();
        try {
            if(date.length() == 17){
                formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'", Locale.US);
            }else{
                formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
            }
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            return new Date(formatter.parse(date).getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}