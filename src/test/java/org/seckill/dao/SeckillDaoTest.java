package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 配置 spring和junit整合、使junit启动时加载springIOC容器
 * spring-test  。junit
 */
//使junit启动时加载springIOC容器
@RunWith(SpringJUnit4ClassRunner.class)//junit依赖
//告诉junit、spring配置文件的位置
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

    //注入dao实现类依赖
    @Resource
    private SeckillDao seckillDao;

    @Test
    public void reduceNumber() throws Exception {
        Date date = new Date();
        System.out.println(date);
        int updateCount = seckillDao.reduceNumber(100L, date);
        System.out.println("updateCount=" + updateCount);
    }

    @Test
    public void queryById() throws Exception {
        long id = 1001;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
    }

    @Test
    public void queryAll() throws Exception {
        //java没有保存形参的记录
        //需要告诉mybatis
        List<Seckill> seckills = seckillDao.queryAll(0, 100);
        for (int i = 0; i < seckills.size(); i++) {
            System.out.println(seckills.get(i));
        }
    }

}