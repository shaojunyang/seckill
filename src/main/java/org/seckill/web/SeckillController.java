package org.seckill.web;

import com.sun.org.apache.regexp.internal.RE;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.dto.SeckillResult;
import org.seckill.entity.Seckill;
import org.seckill.enums.SeckillStatEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 秒杀商品 的handler
 *
 * @author yangshaojun
 * @create 2017-11-20 下午8:47
 **/
@Controller
@RequestMapping("/seckill") //url : 模块/资源/{id}/细分
public class SeckillController {
    //日志对象
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    /**
     * 商品列表
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        //获取 秒杀商品列表页
        List<Seckill> seckillList = seckillService.getSeckillList();
        model.addAttribute("seckillList", seckillList);
        return "list";//转发到 /WEB-INF/jsp/list.jsp

    }

    /**
     * 详情页
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/{seckill_id}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("seckill_id") Long seckill_id, Model model) {
        //判断 商品的 id 是否存在
        if (seckill_id == null) {
            return "redirect:/seckill/list";//重定向到商品列表的handler请求
        }
        Seckill seckill = seckillService.getById(seckill_id);
        if (seckill == null) { //如果 商品 不存在
            return "forward:/seckill/list";//转发到商品列表的handler请求
        }
        model.addAttribute("seckill", seckill);//数据添加到model中
        return "detail";//转发到 /WEB-INF/jsp/list.jsp
    }

    /**
     * ajax  json
     * 输出秒杀地址的接口
     */
    @ResponseBody //把对象 转换为json返回
    @RequestMapping(value = "/{seckill_id}/exposer", method = RequestMethod.POST
            , produces = "application/json;charset=UTF-8")//最后告诉浏览器返回的是json
    public SeckillResult<Exposer> exposer(@PathVariable("seckill_id") Long seckill_id) {
        SeckillResult<Exposer> result; //定义需要 返回的数据
        try {
            //调用service查询 该商品 秒杀地址的接口信息
            Exposer exposer = seckillService.exportSeckillUrl(seckill_id);
            //返回给前台 json信息，里面是  秒杀地址的接口信息
            result = new SeckillResult<Exposer>(true, exposer);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //如果 出现异常，返回给 前台 json信息中是 错误的信息
            result = new SeckillResult<Exposer>(false, e.getMessage());
        }
        return result;
    }


    /**
     * 执行秒杀
     * 所有的ajax请求都统一 返回 SeckllResult <T>这个 dao传输数据类型
     */
    @RequestMapping(value = "/{seckill_id}/{md5}/execution",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(
            @PathVariable("seckill_id") Long seckill_id,
            @PathVariable("md5") String md5,
            //从浏览器 cookid中 获取 用户手机号、不是必须的
            @CookieValue(value = "killPhone", required = false) Long user_phone) {

        if (user_phone == null) {
            //如果 用户手机号 为空，就返回  未注册
            return new SeckillResult<SeckillExecution>(false, "未注册");
        }
        //定义需要 返回的数据
        try {
            //调用service执行 商品 秒杀。返回 给 前台 执行秒杀成功的 信息
            SeckillExecution execution = seckillService.executeSeckill(seckill_id, user_phone, md5);
            return new SeckillResult<SeckillExecution>(true, execution);
        } catch (RepeatKillException e) {
            //如果 出现 重复秒杀异常。就返回  重复秒杀信息
            SeckillExecution execution = new SeckillExecution(seckill_id, SeckillStatEnum.REPEAT_KILL);
            return new SeckillResult<SeckillExecution>(true, execution);
        } catch (SeckillCloseException e) {
            //如果 出现 重复秒杀异常。就返回  重复秒杀结束
            SeckillExecution execution = new SeckillExecution(seckill_id, SeckillStatEnum.END);
            return new SeckillResult<SeckillExecution>(true, execution);
        } catch (Exception e) {
            //除此以外的所有异常   都算作 内部错误
            logger.error(e.getMessage(), e);
            return new SeckillResult<SeckillExecution>(true, "内部错误");
        }

    }

    /**
     * 获取 系统时间
     * 所有的ajax请求都统一 返回 SeckllResult <T>这个 dao传输数据类型
     *
     * @return
     */
    @RequestMapping(value = "/time/now", method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> time() {
        Date date = new Date();
        SeckillResult<Long> dateSeckillResult = new SeckillResult<Long>(true, date.getTime());
        //返回 dto数据对象 ，里面封装了 true。和 当前的时间戳
        return dateSeckillResult;
    }
}
