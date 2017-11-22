package org.seckill.dto;

/**
 * 暴露秒杀地址DTO
 *
 * @author yangshaojun
 * @create 2017-11-20 下午12:18
 **/

public class Exposer {
    //是否开启秒杀、、是否暴露
    private boolean exposed;

    //    一种加密措施
    private String md5;
    //秒杀商品id
    private long seckill_id;
    //系统当前时间（毫秒）
    private long now;
    //秒杀开始时间
    private long start;
    //秒杀结束时间
    private long end;

    /**
     * 秒杀开始  返回的信息
     *
     * @param exposed
     * @param md5
     * @param seckill_id
     */
    public Exposer(boolean exposed, String md5, long seckill_id) {
        this.exposed = exposed;
        this.md5 = md5;
        this.seckill_id = seckill_id;
    }

    /**
     * 没有 到 秒杀时间，的返回信息
     *
     * @param exposed
     * @param seckill_id
     * @param now
     * @param start
     * @param end
     */
    public Exposer(boolean exposed, long seckill_id, long now, long start, long end) {
        this.exposed = exposed;
        this.seckill_id = seckill_id;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    /**
     * 秒杀中没有查询到该商品返回的信息
     *
     * @param exposed
     * @param seckill_id
     */
    public Exposer(boolean exposed, long seckill_id) {
        this.exposed = exposed;
        this.seckill_id = seckill_id;
    }

    public Exposer() {
    }

    public boolean isExposed() {
        return exposed;
    }

    public void setExposed(boolean exposed) {
        this.exposed = exposed;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getSeckill_id() {
        return seckill_id;
    }

    public void setSeckill_id(long seckill_id) {
        this.seckill_id = seckill_id;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {

        this.end = end;
    }

    @Override
    public String toString() {
        return "Exposer{" +
                "exposed=" + exposed +
                ", md5='" + md5 + '\'' +
                ", seckill_id=" + seckill_id +
                ", now=" + now +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
