package org.seckill.exception;

/**
 * 秒杀相关业务异常 （所有秒杀异常）
 *
 * @author yangshaojun
 * @create 2017-11-20 下午12:34
 **/

public class SeckillException extends RuntimeException {

    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
