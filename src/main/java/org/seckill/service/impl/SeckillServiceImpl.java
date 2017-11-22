package org.seckill.service.impl;

import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStatEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * service接口实现
 *
 * @author yangshaojun
 * @create 2017-11-20 下午1:29
 **/
@Service
public class SeckillServiceImpl implements SeckillService {
    //定义slf4j 日志的对象
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //注入 dao
    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private SuccessKilledDao successKilledDao;

    //md5盐值字符串，用于混淆MD5
    private final String salt = "3io2e23jfdie4fj@(#@*#@re*#@&*ewrfdgff";

    /**
     * 查询所有 秒杀商品
     *
     * @return
     */
    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 4);
    }

    /**
     * 根据 id 查询秒杀商品
     *
     * @param seckill_id
     * @return
     */
    public Seckill getById(long seckill_id) {
        return seckillDao.queryById(seckill_id);
    }

    /**
     * 秒杀开启时输出秒杀接口地址
     * 否则输出系统时间和秒杀时间
     *
     * @param seckill_id
     * @return
     */
    public Exposer exportSeckillUrl(long seckill_id) {
        Seckill seckill = seckillDao.queryById(seckill_id);
        if (seckill == null) {
            //    如果 查不到该商品.  返回
            return new Exposer(false, seckill_id);
        }
        //获取 该商品 秒杀开启时间 和结束时间
        Date start_time = seckill.getStart_time();
        Date end_time = seckill.getEnd_time();
        //获取 系统时间
        final Date nowDate = new Date();
        //判断  秒杀是否开始
        if (nowDate.getTime() < start_time.getTime() || nowDate.getTime() > end_time.getTime()) {
            //返回 秒杀信息未开始，
            return new Exposer(false, seckill_id, nowDate.getTime(),
                    start_time.getTime(), end_time.getTime());
        }
        //秒杀已经开启了、返回 秒杀接口地址
        //转换特定字符串的过程、不可逆
        String md5 = getMd5(seckill_id);
        return new Exposer(true, md5, seckill_id);
    }

    /**
     * 生成 MD5加密字符串
     *
     * @param seckill_id
     * @return
     */
    private String getMd5(long seckill_id) {
        String base = seckill_id + "/" + salt;
        // 使用spring的工具类生成MD5
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    /**
     * 执行秒杀方法的实现、返回秒杀后的信息
     *
     * @param seckill_id
     * @param user_phone
     * @param md5
     * @return
     * @throws SeckillException
     * @throws RepeatKillException
     * @throws SeckillCloseException
     */
    @Transactional
    /**
     * 使用注解控制事务方法的优点
     * 1、开发团队达成一致约定、明确标注事务方法的编程风格
     * 2、保证事务方法的执行事件尽可能短、
     * 尽量不要穿插其他的网络操作（RPC、HTTP）。或者剥离事务 外部
     * 3、不是所有的方法都需要事务、如查询操作、只读操作、只有一条修改操作的
     */
    public SeckillExecution executeSeckill(long seckill_id, long user_phone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        //判断 用户输过来的md5是否和 之前的 不一样
        if (md5 == null || !md5.equals(getMd5(seckill_id))) {
            throw new SeckillException("秒杀数据被重写了");
        }
        //执行秒杀逻辑 :减库存+ 记录购买行为
        Date nowTime = new Date();
        try {
            //减库存
            int updateCount = seckillDao.reduceNumber(seckill_id, nowTime);
            if (updateCount <= 0) {
                //没有更新到 记录 ，没有减库存、可能是不在秒杀时间段内
                throw new SeckillCloseException("秒杀已经结束了");
            } else {
                //    减库存成功了 。就记录购买行为
                int insertCount = successKilledDao.insertSuccessKilled(seckill_id, user_phone);
                // 唯一 验证 id 和 手机号
                if (insertCount <= 0) {
                    //重复秒杀了
                    throw new RepeatKillException("重复秒杀");
                } else {
                    //秒杀成功了、返回 秒杀成功的 信息
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckill_id, user_phone);
                    return new SeckillExecution(seckill_id, SeckillStatEnum.SUCCESS, successKilled);
                }
            }
        } catch (SeckillCloseException e1) {
            throw e1;//抛出秒杀关闭异常
        } catch (RepeatKillException e2) {
            throw e2;
        } catch (Exception e) {
            // 如果出现其他异常，有 logger打印异常日志信息
            logger.error(e.getMessage(), e);
            //    所有 编译器异常 转换为运行期异常
            throw new SeckillException("秒杀内部 错误 " + e.getMessage());
        }
    }
}
