package org.seckill.dto;

import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStatEnum;

/**
 * 封装 执行秒杀后的结果
 *
 * @author yangshaojun
 * @create 2017-11-20 下午12:25
 **/

public class SeckillExecution {
    //秒杀执行 结果 的 秒杀商品 id
    private long seckill_id;
    //秒杀执行结果状态
    private int state;
    //秒杀执行结果  状态表示
    private String stateInfo;
    //成功的时候，返回 秒杀成功记录对象
    private SuccessKilled successKilled;

    public SeckillExecution(long seckill_id, SeckillStatEnum statEnum, SuccessKilled successKilled) {
        this.seckill_id = seckill_id;
        this.state = statEnum.getState();
        this.stateInfo = statEnum.getStateInfo();
        this.successKilled = successKilled;
    }

    //失败的状态 给出的信息
    public SeckillExecution(long seckill_id, SeckillStatEnum statEnum) {
        this.seckill_id = seckill_id;
        this.state = statEnum.getState();
        this.stateInfo = statEnum.getStateInfo();
    }

    public long getSeckill_id() {
        return seckill_id;
    }

    public void setSeckill_id(long seckill_id) {
        this.seckill_id = seckill_id;
    }

    @Override
    public String toString() {
        return "SeckillExecution{" +
                "seckill_id=" + seckill_id +
                ", state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", successKilled=" + successKilled +
                '}';
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SuccessKilled successKilled) {
        this.successKilled = successKilled;
    }
}
