//  存放 主要 交互逻辑 js代码
//javascript 模块化

var seckill = {
    //封装秒杀相关ajax的durl
    URL: {
        now: function () {
            return '/seckill/time/now';
        },
        exposer: function (seckill_id) {
            return "/seckill/" + seckill_id + "/exposer";
        },
        execution:function (seckill_id,md5) {
            return '/seckill/' + seckill_id + '/' + md5+'/execution';
        }
    },
    handlerSeckillKill: function (seckill_id, node) {
        //获取 秒杀地址、控制 显示 逻辑、执行秒杀
        node.hide() //控制 按钮先隐藏
            .html('<button class="btn btn-primary" id="killBtn">开始秒杀</button> ');//按钮

        //ajax 发送一个 post请求
        $.post(seckill.URL.exposer(seckill_id), {}, function (result) {
            //在 回调函数中 执行 交互流程
            if (result && result['success']) {
                //获取  exposer 里面 封装了 秒杀接口地址 等等信息
                var exposer = result['data'];
                //根据 返回的结果判定是否开始秒杀
                if (exposer['exposed']) {
                    //如果 开始 秒杀就 显示 秒杀 按钮
                    //获取 秒杀地址
                    var md5 = exposer['md5'];
                    var killUrl = seckill.URL.execution(seckill_id,md5);
                    console.log("killUrl:"+killUrl);
                    //绑定一次点击事件
                    $('#killBtn').one('click',function () {
                        //执行秒杀请求的操作
                        //1、先禁用按钮
                        $(this).addClass("disabled");
                        //2、发送ajax秒杀请求秒杀
                        $.post(seckill.URL.execution(seckill_id,md5),{},function (result) {
                            if (result && result['success']){
                                //取出秒杀 成功的信息
                                var kill_result =  result['data'];
                                var state = kill_result['state'];
                                var stateInfo =kill_result['stateInfo'];
                                // 3、显示 秒杀 信息
                                node.html('<span class="label label-success">'+stateInfo+'</span>');
                            }
                        })
                    })
                //显示  秒杀 按钮
                node.show();
                    
                } else {
                    //未开启秒杀
                    var now = exposer['now']; //就 获取  传过来的时间
                    var start = exposer['start'];
                    var end = exposer['end'];
                    //重新计算 倒计时 逻辑
                    seckill.countdown(seckill_id, now, start, end);
                }

            } else {
                console.log("result" + result);
            }
        })


    },
    //秒杀 倒计时  逻辑
    countdown: function (seckill_id, now_time, start_time, end_tiem) {
        //    时间判断
        if (now_time > end_tiem) {
            //    秒杀结束了
            $('#seckill-box').html("秒杀结束了");
        } else if (now_time < start_time) {
            //秒杀未开始、  开始计时、计时事件绑定
            var kill_time = new Date(start_time + 1000);//用js的Date对象
            $('#seckill-box').countdown(kill_time, function (event) {
                //控制时间的格式
                var format = event.strftime("秒杀倒计时 ： %D天 %H时 %M分 %S秒");
                //把格式 输出到节点时上
                $('#seckill-box').html(format);
                //时间完成后 回调事件
            }).on('finish.countdown', function () {
                //获取 秒杀地址、控制 显示 逻辑、执行秒杀
                seckill.handlerSeckillKill(seckill_id, $('#seckill-box'));

            })
        } else {
            //秒杀开始了
            seckill.handlerSeckillKill(seckill_id, $('#seckill-box'));
        }
    },
    //验证手机号的方法
    validatePhone: function (phone) {
        if (phone && phone.length == 11 && !isNaN(phone)) {
            return true;
        } else {
            return false;
        }
    },
    //详情页 秒杀逻辑
    detail: {
        //详情页初始化
        init: function (params) {
            // 用户手机验证 和 登录 、计时交互
            //规划 我们的交互流程
            // 在cookie查找手机号
            var user_phone = $.cookie('killPhone');

            //验证 手机号
            if (!seckill.validatePhone(user_phone)) {
                //如果 没有 登录 就是  弹出层 显示
                //控制 弹出层输出
                var killPhoneModal = $('#killPhoneModal');

                killPhoneModal.modal({

                    show: true,//显示弹出层
                    backdrop: 'static',//禁止点击 别的位置关闭 弹出层
                    keyboard: false  //禁止 点击键盘 关闭弹出层
                });
                $('#killPhoneBtn').click(function () {
                    var user_phone = $('#killPhoneKey').val();
                    console.log(user_phone);
                    if (seckill.validatePhone(user_phone)) {
                        //写入电话 写入cookie
                        //这里用的是jquery的 cookie插件
                        //向 cookie中设置 值
                        $.cookie('killPhone', user_phone, {expires: 7, path: '/seckill'});
                        //如果验证通过 刷新页面
                        window.location.reload();
                    } else {
                        //  如果 用户填写的手机号有问题
                        $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号填写错误</label>').show(300);
                    }
                })
            }
            //已经登录
            //计时交互 .
            //使用 ajax 请求后台的 服务器时间

            var start_time = params['start_time'];
            var end_time = params['end_time'];
            var seckill_id = params['seckill_id'];// 获取 传给来的 时间
            $.get(seckill.URL.now(), {}, function (result) {
                console.log("result=" + result);
                if (result && result['success']) {
                    // debugger;
                    //拿到系统 时间
                    var now_time = result['data'];
                    //时间判断、调用 计时交互
                    seckill.countdown(seckill_id, now_time, start_time, end_time);
                } else {
                    console.log("result=" + result);
                }
            })
        }
    }
}