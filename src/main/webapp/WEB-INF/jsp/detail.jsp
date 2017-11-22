<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!-- 引入jstl -->
<%@include file="common/tag.jsp" %>


<!DOCTYPE html>
<html>
<head>
    <title>秒杀详情页</title>
    <%-- 静态 包含 指定jsp页面 --%>
    <%@include file="common/head.jsp" %>
</head>
<body>

<div class="container">
    <div class="panel panel-default text-center">
        <div class="panel-heading">
            <h1>${seckill.name}</h1>
        </div>
    </div>
    <div class="panel-body">
        <h2 class="text-danger  text-center">
            <!-- 显示time图标 -->
            <span class="glyphicon glyphicon-time"></span>
            <%-- 展示倒计时   --%>
            <span class="glyphicon" id="seckill-box"></span>
        </h2>
    </div>
</div>
<%--  登录 弹出层、输出电话  --%>
<div id="killPhoneModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-header">
            <h3 class="modal-title text-center">
                <span class="glyphicon glyphicon-phone"></span>
            </h3>
        </div>

        <div class="modal-body">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2">
                    <input type="text" name="killPhone" id="killPhoneKey" class="form-control" placeholder="请填入手机号">
                </div>
            </div>
        </div>

        <div class="modal-footer">
            <%-- 验证信息 --%>
            <span id="killPhoneMessage" class="glyphicon"></span>
            <button type="button" id="killPhoneBtn" class="btn btn-success">
                <span class="glyphicon glyphicon-phone"></span>
                submit
            </button>
        </div>


    </div>

</div>
</body>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<%--jquery 的 cookie插件 --%>
<script src="https://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.js"></script>
<%-- -jquery 的  计时插件--%>
<script src="<%=request.getContextPath() %>/webjars/jquery.countdown/2.1.0/dist/jquery.countdown.min.js"></script>

<%--引入 外部 js--%>
<script src="/resources/script/seckill.js"></script>
<script>
    $(function () {
//        默认 执行 该函数，函数的参数 是一个 对象，

        //使用 el表达式 传入参数
        seckill.detail.init({
            seckill_id:${seckill.seckill_id},
            start_time: ${seckill.start_time.time},//毫秒
            end_time: ${seckill.end_time.time}
        });
    })
</script>
</html>
