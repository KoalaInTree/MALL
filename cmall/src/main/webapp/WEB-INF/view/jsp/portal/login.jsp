<%--
  Created by IntelliJ IDEA.
  User: CJ
  Date: 2017/12/27
  Time: 14:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset=utf-8/>
    <meta http-equiv=x-ua-compatible content="ie=edge">
    <meta name=viewport content="width=device-width,initial-scale=1">
    <meta name=keywords content=MMall,电商,网购平台,网购,数码3C,手机,箱包,服装>
    <meta name=description content=MMall电商平台,提供数码通讯、电脑、家居百货、服装服饰、母婴、图书、食品等数万种优质商品,让您享受最优质、最便捷的网上购物服务。>
    <link rel=dns-prefetch href=//s.happymmall.com/>
    <link rel=dns-prefetch href=//cdn.bootcss.com/ />
    <script>var _hmt = _hmt || [];
    !function () {
        var e = document.createElement("script");
        e.src = "https://hm.baidu.com/hm.js?bc210cd22928076d28296716d9dfcf01";
        var t = document.getElementsByTagName("script")[0];
        t.parentNode.insertBefore(e, t)
    }()</script>
    <title>用户登录--MMall电商平台</title>
    <link rel="shortcut icon" href="//s.happymmall.com/mmall_fe/dist/favicon.ico">
    <link href="../../../../css/common.css" rel="stylesheet">
    <link href="../../../../css/login.css" rel="stylesheet">
</head>
<body>
<div class=header-simple>
    <div class=w><a href=../index.do class=logo>MMall</a></div>
</div>
<div class="user-wrap">
    <div class="user-con w">
        <div class="user-title">用户登录</div>
        <div class="user-box">
            <div class="err-item">
                <i class="fa fa-minus-circle err-icon"></i>
                <p class="err-msg">Error</p>
            </div>
            <div class="user-item">
                <label class="user-label" for="username"><i class="fa fa-user"></i></label>
                <input type="text" class="user-content" id="username" placeholder="请输入用户名" autocomplete="off"/>
            </div>
            <div class="user-item">
                <label class="user-label" for="password"><i class="fa fa-lock"></i></label>
                <input type="password" class="user-content" id="password" placeholder="请输入密码"/>
            </div>
            <a class="user-btn" id="submit">登录</a>
            <div class="link-item">
                <a class="link pass-forget" href="./pass-reset.html" target="_blank">忘记密码</a>
                <a class="link register" href="../register.do" target="_blank">免费注册</a>
            </div>
        </div>
    </div>
</div>
<div class=footer>
    <div class=w>
        <div class=links><a class=link href=http://www.imooc.com/ >慕课网</a> | <a class=link href=http://www.baidu.com/ >百度</a>
            | <a class=link href=http://www.zhihu.com/ >知乎</a> | <a class=link href=http://www.taobao.com/ >淘宝</a></div>
        <p class=copyright> Copyright © 2017 happymmall.com All Rights Reserved </p></div>
</div>
<script src=//cdn.bootcss.com/jquery/1.11.3/jquery.js></script>
<script type="text/javascript" src="../../../../js/base.js"></script>
<script type="text/javascript" src="../../../../js/common.js"></script>
<script type="text/javascript" src="../../../../js/login.js"></script>
</body>
</html>
