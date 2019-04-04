package com.lee.seckillshop.commons.vo;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyTimeStampDeserializer extends JsonSerializer<Timestamp> {

    @Override
    public void serialize(Timestamp timestamp, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        long time = timestamp.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = simpleDateFormat.format(new Date(time));
        jsonGenerator.writeString(s);
    }
}
