package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKilled;

public interface SuccessKilledDao {
    /**
     * 插入购买明细、可过滤重复
     *
     * @param seckill_id
     * @param user_phone
     * @return 插入的行数
     */
    int insertSuccessKilled(@Param("seckill_id") long seckill_id, @Param("user_phone") long user_phone);

    /**
     * 根据id 查询 成功秒杀详情 SuccessKilled
     * 并携带秒杀商品对象实体
     *
     * @param seckill_id
     * @return
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckill_id") long seckill_id, @Param("user_phone") long user_phone);

}
