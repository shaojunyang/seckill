<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>秒杀列表页</title>
    <%-- 静态 包含 指定jsp页面 --%>
    <%@include file="common/head.jsp" %>
</head>
<body>

<!--   页面显示部分  -->
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading  text-center">
            <h1>秒杀列表</h1>
            <div class="panel-body">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <td>名称</td>
                        <td>库存</td>
                        <td>开始时间</td>
                        <td>结束时间</td>
                        <td>创建时间</td>
                        <td>详情页</td>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${seckillList}" var="sk">
                        <tr>
                            <td>${sk.name}</td>
                            <td>${sk.number}</td>
                            <td>
                                <fmt:formatDate value="${sk.start_time}" pattern="yyyy-MM-dd  HH:mm:ss"/>
                            </td>
                            <td>
                                <fmt:formatDate value="${sk.end_time}" pattern="yyyy-MM-dd  HH:mm:ss"/>
                            </td>
                            <td>
                                <fmt:formatDate value="${sk.create_time}" pattern="yyyy-MM-dd  HH:mm:ss"/>
                            </td>
                            <!--  使用 restful 风格的 url  -->
                            <td><a href="/seckill/${sk.seckill_id}/detail" class="btn btn-info"
                                   target="_blank">秒杀商品详情页</a></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>


</body>


<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</html>
