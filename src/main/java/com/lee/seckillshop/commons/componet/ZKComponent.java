package com.lee.seckillshop.commons.componet;

import io.netty.channel.EventLoopGroup;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheMode;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;


@Slf4j
public class ZKComponent {
    private CuratorFramework curator;
    //挂起请求
    private CountDownLatch count=new CountDownLatch(1);
    //锁的工作空间
    private static final String PARENT="lock";
    //锁的名字
    private String lockname="seckill";

    public ZKComponent(CuratorFramework curator,String lockname){

        this.curator=curator;
        this.lockname=lockname;
    }

    public void init(){
        try {
            if(curator.checkExists().forPath("/"+PARENT)==null){
               curator.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
                       .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                       .forPath("/"+PARENT);
            }
            log.info("初始化成功..........");
            addWatch("/"+PARENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addWatch(String s) throws Exception{

            PathChildrenCache cache=new PathChildrenCache(curator,s,true);
            cache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
            cache.getListenable().addListener(new PathChildrenCacheListener() {
                @Override
                public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                     if(pathChildrenCacheEvent.getType().equals(PathChildrenCacheEvent.Type.CHILD_REMOVED)){
                        String path=pathChildrenCacheEvent.getData().getPath();
                        log.info("断开上一次的会话......");
                        if(path.contains(lockname)){
                            //唤醒等待线程
                            count.countDown();
                        }
                     }
                }
            });

    }

    public void getLock(Integer id){
        while (true){
            try{
                curator.create().creatingParentsIfNeeded()
                        .withMode(CreateMode.EPHEMERAL)
                        .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                        .forPath("/"+PARENT+"/"+lockname+":"+id);
                log.info("获取锁成功..........");
                return ;
            }catch (Exception e){
                //获取锁失败
                try {
                    if(count.getCount()<=0){
                        count=new CountDownLatch(1);
                    }
                    log.info("获取锁失败..........");
                    //阻塞等待唤醒
                    count.await();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public Boolean realse(Integer id){
        try {
            if(curator.checkExists().forPath("/"+PARENT+"/"+lockname+":"+id)!=null){
                curator.delete().forPath("/"+PARENT+"/"+lockname+":"+id);
            }
            log.info("释放锁成功..........");
        } catch (Exception e) {
            log.info("释放锁失败..........");
           return false;
        }
        return true;
    }

}
