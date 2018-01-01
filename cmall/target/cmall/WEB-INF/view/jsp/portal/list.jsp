<%--
  Created by IntelliJ IDEA.
  User: CJ
  Date: 2017/12/26
  Time: 21:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <title>商品列表--MMall电商平台</title>
    <link rel="shortcut icon" href="//s.happymmall.com/mmall_fe/dist/favicon.ico">
    <link href="../../../../css/common.css" rel="stylesheet">
    <link href="../../../../css/list.css" rel="stylesheet">
</head>
<body>
<div class=site-nav>
    <div class=w>
        <div class=user-info>
            <c:if test="${CURRENT_USER == null}">
                <span class="link"><a class="link" href="http://localhost:8088/user/goLogin.do">登录</a></span>
                <span class="link"><a class="link" href="../register.do">注册</a></span>
                </span>
            </c:if>
            <c:if test="${CURRENT_USER != null}">
            <span class="">
                <span >欢迎，
                    <span>${CURRENT_USER.username}</span>
                </span>
                <span class="link link-logout">退出</span>
            </span>
            </c:if>
        </div>
        <ul class=nav-list>
            <li class=nav-item><a class=link href=../cart/list.do> <i class="fa fa-shopping-cart"></i> 购物车(<span
                    class=cart-count>0</span>)</a></li>
            <li class=nav-item><a class=link href=../order/order-list.do>我的订单</a></li>
            <li class=nav-item><a class=link href=../user/get_userInfo.do>我的MMall</a></li>
            <li class=nav-item><a class=link href=../about.do>关于MMall</a></li>
        </ul>
    </div>
</div>
<div class=header>
    <div class=w><a class=logo href=../product/index.do>MMall</a>
        <div class=search-form><input class=search-input id=search-input placeholder=请输入商品名称 />
            <button class=search-btn id=search-btn>搜索</button>
        </div>
    </div>
</div>
<div class="crumb">
    <div class="w">
        <div class="crumb-list">
            <a href="../product/index.do" class="crumb-item">MMall</a>
            <span>></span>
            <span class="crumb-item">商品列表</span>
        </div>
    </div>
</div>
<div class="list-wrap w">
    <ul class="sorter-con">
        <li class="sorter-item active" data-type="default">
            <span>推荐排序</span>
        </li>
        <li class="sorter-item" data-type="price">
            <span>价格</span>
            <i class="fa fa-sort-asc"></i>
            <i class="fa fa-sort-desc"></i>
        </li>
    </ul>
    <!-- list容器 -->
    <ul class="p-list-con">
        <c:if test="${res.status == '0'}">
            <c:forEach items="${res.data.list}" var="pro">
                <li class="p-item">
                    <div class="p-img-con">
                        <a class="link" href="./detail.do?productId=${pro.id}" target="_blank">
                            <img class="p-img" src="${pro.imageHost}${pro.mainImage}" alt="${pro.name}"/>
                        </a>
                    </div>
                    <div class="p-price-con"><span class="p-price">¥${pro.price}</span></div>
                    <div class="p-name-con"><a href="./detail.do?productId=${pro.id}" target="_blank" class="p-name"
                                               title="${pro.name}">${pro.name} </a></div>
                </li>
            </c:forEach>
        </c:if>
    </ul>
    <!-- 分页容器 -->
    <div class="pagination"></div>
</div>
<div class=footer>
    <div class=w>
        <div class=links><a class=link href=http://www.imooc.com/ >慕课网</a> | <a class=link href=http://www.baidu.com/ >百度</a>
            | <a class=link href=http://www.zhihu.com/ >知乎</a> | <a class=link href=http://www.taobao.com/ >淘宝</a></div>
        <p class=copyright> Copyright © 2017 happymmall.com All Rights Reserved </p></div>
</div>
</body>
<script src="../../../../js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="../../../../js/base.js"></script>
<script type="text/javascript" src="../../../../js/common.js"></script>
</html>