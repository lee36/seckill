package com.lee.seckillshop.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.solr.common.util.Hash;

import java.util.HashMap;
import java.util.Map;

/**
 * @author admin
 * @date 2018-09-19
 */
public class JsonUtil {
    private static final ObjectMapper objectMapper=new ObjectMapper();

    /**
     * 将对象转换成json字符串
     * @param object
     * @param <T>
     * @return
     */
    public static <T> String obj2Json(Object object) throws JsonProcessingException {
        Class<?> clazz = object.getClass();
        if(clazz==Integer.class||clazz==int.class){
            return (int)object+"";
        }else if(clazz==long.class||clazz==Long.class){
            return Long.toString((Long) object);
        }else if(clazz==String.class){
            return (String)object;
        }
        return objectMapper.writeValueAsString(object);
    }

    /**
     * json字符串转成对象
     * @param str
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T json2Obj(String str, Class<T> clazz) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        if(str==null||str.length()<0||clazz==null) {
            return null;
        }
        if(clazz==int.class||clazz==Integer.class) {
            return (T) Integer.valueOf(str);
        }else if(clazz==String.class) {
            return (T) str;
        }else if(clazz==long.class||clazz==Long.class) {
            return (T) Long.valueOf(str);
        }else {
            return (T)mapper.readValue(str,clazz);
        }
    }
}
