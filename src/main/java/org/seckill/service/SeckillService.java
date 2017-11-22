package org.seckill.service;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;

import java.util.List;

/**
 * 业务接口：站在"使用者"的角度去设计接口
 * 三个方法：方法定义粒度、参数、返回类型(return 类型/异常)
 */
public interface SeckillService {
    /**
     * 查询所有秒杀记录
     * @return
     */
    List<Seckill> getSeckillList();
    /**
     * 根据 id 查询单个秒杀记录
     * @param seckill_id
     * @return
     */
    Seckill getById(long seckill_id);

    /**
     * 秒杀开启时输出秒杀接口地址
     * 否则输出系统时间和秒杀时间
     * @param seckill_id
     */
    Exposer exportSeckillUrl(long seckill_id);

    /**
     * 执行秒杀操作、返回秒杀后的信息
     * @param seckill_id
     * @param user_phone
     * @param md5
     */
    SeckillExecution executeSeckill(long seckill_id, long user_phone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException;
}
