package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Seckill;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;

public interface SeckillDao {
    /**
     * 减库存
     *
     * @param seckill_id 减库存的商品
     * @param killTime   执行减库存的时间
     * @return  如果影响行数 >=1 、表示更新的记录行数
     */
    int reduceNumber(@Param("seckill_id") long seckill_id, @Param("killTime") Date killTime);

    /**
     * 根据 商品id 查询 秒杀库存商品 这个对象
     *
     * @param seckill_id
     * @return
     */
    Seckill queryById(long seckill_id);

    /**
     * 根据 偏移量 查询 秒杀商品列表
     *
     * @param offset
     * @param limit
     * @return
     */
    //，java没有保存形参的记录 。针对两个参数、会变成 arg1 arg2
    //需要告诉 mybatis的param的注解告诉mybatis第一个参数实际形参是offset
    //通过 注解告诉mybatis第二个参数实际形参是limit
    List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);
}
