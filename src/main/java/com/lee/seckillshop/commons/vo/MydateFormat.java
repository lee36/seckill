package com.lee.seckillshop.commons.vo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.format.Formatter;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MydateFormat implements Formatter<Timestamp> {
    @Override
    public Timestamp parse(String text, Locale locale) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ss");
        try {
            Date date = format.parse(text);
            Timestamp timestamp = new Timestamp(date.getTime());
            return timestamp;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String print(Timestamp object, Locale locale) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(object.getTime());
        try {
            return new ObjectMapper().writeValueAsString(date);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
