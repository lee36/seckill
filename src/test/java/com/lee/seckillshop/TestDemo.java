package com.lee.seckillshop;

import org.junit.Test;

import java.util.HashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * @author admin
 * @date 2018-10-12
 */
public class TestDemo {
   @Test
   public void test() throws InterruptedException {
       LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>(2);

       deque.offer("zhangsan",1L,TimeUnit.SECONDS);
       deque.offer("lisi",1L,TimeUnit.SECONDS);
       deque.offer("wangwu", 1L, TimeUnit.SECONDS);
       for (String s : deque) {
           System.out.println(s);
       }
       String poll = deque.poll(1, TimeUnit.SECONDS);
       System.out.println(poll);
   }
}
