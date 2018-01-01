<%--
  Created by IntelliJ IDEA.
  User: CJ
  Date: 2017/12/27
  Time: 16:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>
    <meta charset=utf-8 /> <meta http-equiv=x-ua-compatible content="ie=edge"> <meta name=viewport content="width=device-width,initial-scale=1"> <meta name=keywords content=MMall,电商,网购平台,网购,数码3C,手机,箱包,服装> <meta name=description content=MMall电商平台,提供数码通讯、电脑、家居百货、服装服饰、母婴、图书、食品等数万种优质商品,让您享受最优质、最便捷的网上购物服务。> <link rel=dns-prefetch href=//s.happymmall.com /> <link rel=dns-prefetch href=//cdn.bootcss.com/ /> <script>var _hmt=_hmt||[];!function(){var e=document.createElement("script");e.src="https://hm.baidu.com/hm.js?bc210cd22928076d28296716d9dfcf01";var t=document.getElementsByTagName("script")[0];t.parentNode.insertBefore(e,t)}()</script>
    <title>操作结果--MMall电商平台</title>
    <link rel="shortcut icon" href="//s.happymmall.com/mmall_fe/dist/favicon.ico"><link href="../../../../css/common.css" rel="stylesheet"><link href="../../../../css/result.css" rel="stylesheet"></head>
<body>
<div class=header-simple> <div class=w> <a href=./index.do class=logo>MMall</a> </div> </div>
<div class="result-wrap w">
    <div class="result-con register-success">
        <h1 class="result-title">恭喜您，注册成功！</h1>
        <p class="result-content">
            <a class="link" href="./login.do">立即去登录</a>
        </p>
    </div>
    <div class="result-con pass-reset-success">
        <h1 class="result-title">恭喜您，重置密码成功！</h1>
        <p class="result-content">
            <a class="link" href="./login.do">立即去登录</a>
        </p>
    </div>
    <c:if test="${RESULT == 0}">
    <div >
        <h1 class="result-title">您的商品已成功加入购物车</h1>
        <p class="result-content">
            <a class="link" href="../product/index.do">继续购物</a>
            <a class="link" href="../cart/list.do">去购物车查看</a>
        </p>
    </div>
    </c:if>
    <div class="result-con payment-success">
        <h1 class="result-title">您的订单支付成功！</h1>
        <p class="result-content">
            <a class="link" href="./index.do">继续购物</a>
            <a class="link my-order" href="./order-detail.do?orderNumber=">查看我的订单</a>
        </p>
    </div>
    <div class="result-con default-success">
        <h1 class="result-title">恭喜您，操作成功</h1>
        <p class="result-content">
            <a class="link" href="./index.do">返回首页</a>
        </p>
    </div>
</div>
<div class=footer> <div class=w> <div class=links> <a class=link href=http://www.imooc.com/ >慕课网</a> | <a class=link href=http://www.baidu.com/ >百度</a> | <a class=link href=http://www.zhihu.com/ >知乎</a> | <a class=link href=http://www.taobao.com/ >淘宝</a> </div> <p class=copyright> Copyright © 2017 happymmall.com All Rights Reserved </p> </div> </div> <script src=//cdn.bootcss.com/jquery/1.11.3/jquery.js></script>
<script type="text/javascript" src="../../../../js/base.js"></script><script type="text/javascript" src="../../../../js/common.js"></script><script type="text/javascript" src="../../../../js/result.js"></script></body>
</html>
