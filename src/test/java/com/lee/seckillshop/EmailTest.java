package com.lee.seckillshop;

import com.lee.seckillshop.commons.componet.EmailComponet;
import com.lee.seckillshop.commons.componet.ZKComponent;
import com.lee.seckillshop.commons.constraint.EmailUniqueConstraint;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EmailTest {
    @Autowired
    private EmailComponet componet;
    @Autowired
    private ZKComponent zkComponent;
    @Test
    public void sendEmail() throws MessagingException {
        ExecutorService service =
                Executors.newFixedThreadPool(10);
        for(int i=0;i<2;i++){
            service.execute(()->{

            });
        }

    }
}
