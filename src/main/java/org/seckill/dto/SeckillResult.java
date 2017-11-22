package org.seckill.dto;

/**
 * 所有ajax请求的返回类型
 * 封装 json结果
 *  定义一个 返回 json数据 的 dto 传输数据类
 * @author yangshaojun
 * @create 2017-11-20 下午9:06
 **/

public class SeckillResult <T> { //使用 泛型\可以 返回 exposer 和 seckillExecution执行秒杀结果的类型
    //判断请求是否成功
    private boolean success;
    private T data;//泛型数据
    private String error;//错误信息

    //如果 是true ’、肯定有数据
    public SeckillResult(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    //如果是false 、有错误信息
    public SeckillResult(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
