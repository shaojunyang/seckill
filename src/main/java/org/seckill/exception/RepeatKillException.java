package org.seckill.exception;

/**
 * 重复秒杀异常(运行期异常)
 *
 * @author yangshaojun
 * @create 2017-11-20 下午12:31
 **/

public class RepeatKillException extends SeckillException {
    //异常信息
    public RepeatKillException(String message) {
        super(message);
    }

    //异常原因
    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
