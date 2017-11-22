package org.seckill.exception;

/**
 * 秒杀关闭异常
 *
 * @author yangshaojun
 * @create 2017-11-20 下午12:33
 **/

public class SeckillCloseException extends SeckillException {

    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
