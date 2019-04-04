package com.lee.seckillshop.commons.vo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.Formatter;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MydateCoverter implements Converter<String,Timestamp> {

    @Override
    public Timestamp convert(String s) {
        SimpleDateFormat format = new SimpleDateFormat("yyy/MM/ddTHH:mm:ss");
        try {
            Date date = format.parse(s);
            Timestamp timestamp = new Timestamp(date.getTime());
            return timestamp;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
