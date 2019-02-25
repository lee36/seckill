package com.lee.seckillshop.util;

import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.SolrResultPage;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author admin
 * @date 2018-10-10
 */

public class ParseSolrDocumentUtil {

    public static <T> void parseHightLight(Class<T> tClass, Page pageable) throws Exception {
        SolrResultPage<T> page = (SolrResultPage<T>) pageable;
        List<HighlightEntry<T>> highlighted = page.getHighlighted();
        for (HighlightEntry<T> entry : highlighted) {
            T entity = entry.getEntity();
            List<HighlightEntry.Highlight> highlights = entry.getHighlights();
            for (HighlightEntry.Highlight highlight : highlights) {
                org.springframework.data.solr.core.query.Field field = highlight.getField();
                String lightContent = highlight.getSnipplets().get(0);
                String methodName = "set" + transUnderLine(field.getName());
                Method method = tClass.getMethod(methodName, String.class);
                method.invoke(entity, lightContent);
            }
        }
    }

    /**
     * 下划线装换成大写，所有单词首字母大写
     *
     * @param filed
     * @return
     */
    private static String transUnderLine(String filed) {
        String[] split = filed.split("_");
        StringBuilder sb = new StringBuilder();
        for (String s : split) {
            String s1 = s.substring(0, 1).toUpperCase() + s.substring(1, s.length());
            sb.append(s1);
        }
        return sb.toString();
    }
}
