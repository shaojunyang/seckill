package org.seckill.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml"})
public class SeckillServiceImplTest {

    //日志
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> seckillList = seckillService.getSeckillList();
        logger.info("seckillList={}", seckillList);//用logger对象 的info方法输出
    }

    @Test
    public void getById() throws Exception {
        Seckill seckill = seckillService.getById(1000);
        logger.info("seckill={}", seckill);
    }

    @Test
    public void exportSeckillUrl() throws Exception {
        long id = 1003;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        logger.info("exposer={}", exposer);
    }

    @Test
    public void executeSeckill() throws Exception {
        long id = 1003;
        long phone = 5553322L;
        String md5 = "8d2c2c2eb66f85128df5e889bf43b7c0";
        try {
            SeckillExecution seckillExecution = seckillService.executeSeckill(id, phone, md5);
            logger.info("seckillExecution={}", seckillExecution);
        } catch (RepeatKillException e) {
            logger.error(e.getMessage());
        } catch (SeckillCloseException e1) {
            logger.error(e1.getMessage());
        }
    }

    /*
    * //1、字符串转换为日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse("2017-11-01 12:12:12");
            System.out.println(date);
            //Wed Nov 01 12:12:12 CST 2017
            //2、日期转字符串
            String format = sdf.format(new Date());
            String format1 = sdf.format(date);
            System.out.println(format);//2017-11-20 18:54:34
            System.out.println(format1);//2017-11-01 12:12:12
    * */
    @Test
    public void test() {
        logger.info("你好");
    }

    //集成测试代码完整逻辑，可以重复执行
    @Test
    public void testSeckillLogic() throws Exception {
        long id = 1003;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        if (exposer.isExposed()) {
            logger.info("exposer={}", exposer);
            long phone = 12333232L;
            String md5 = exposer.getMd5();
            try {
                SeckillExecution seckillExecution = seckillService.executeSeckill(id, phone, md5);
                logger.info("seckillExecution={}", seckillExecution);
            } catch (RepeatKillException e) {
                logger.error(e.getMessage());
            } catch (SeckillCloseException e1) {
                logger.error(e1.getMessage());
            }
        } else {
            //    秒杀未开启
            logger.warn("exposer={}", exposer);
        }
    }

}