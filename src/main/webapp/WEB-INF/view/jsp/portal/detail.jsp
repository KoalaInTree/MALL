<%--
  Created by IntelliJ IDEA.
  User: CJ
  Date: 2017/12/27
  Time: 11:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

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
    <title>商品详情--MMall电商平台</title>
    <link rel="shortcut icon" href="//s.happymmall.com/mmall_fe/dist/favicon.ico">
    <link href="../../../../css/common.css" rel="stylesheet">
    <link href="../../../../css/detail.css" rel="stylesheet">
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
            <span class="crumb-item">商品详情</span>
        </div>
    </div>
</div>
<div class="page-container w">
    <c:if test="${res.status == '0'}">
        <div class="intro-wrap clear">
            <div class="p-img-wrap">
                <div class="main-img-con">
                    <img class="main-img" src="${res.data.imageHost}${res.data.mainImage}">
                </div>
                <ul class="p-img-list">
                    <c:set var="strSub" value="${res.data.subImages}"/>
                    <c:set var="subArr" value="${fn:split(strSub, ',')}"/>
                    <c:forEach items="${subArr}" var="subImage">
                        <li class="p-img-item">
                            <img class="p-img" src="${res.data.imageHost}${subImage}">
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div class="p-info-wrap">
                <p class="p-name">${res.data.name}</p>
                <p class="p-subtitle">${res.data.subtitle}</p>
                <div class="info-item p-price-con">
                    <span class="label">价格:</span>
                    <span class="p-price">￥${res.data.price}</span>
                </div>
                <div class="info-item p-quantity-con">
                    <span class="label">库存:</span>
                    <span class="p-price">${res.data.stock}</span>
                    <input id="stock" type="hidden" value="${res.data.stock}" />
                    <input id="productId" type="hidden" value="${res.data.id}" />
                </div>
                <div class="info-item">
                    <span class="label">数量:</span>
                    <input class="p-count" value="1" readonly="readonly">
                    <span class="p-count-btn plus" data-opera-type="plus">+</span>
                    <span class="p-count-btn minus" data-opera-type="minus">-</span>
                </div>
                <div class="info-item">
                    <a class="btn cart-add">加入购物车</a>
                </div>
            </div>
        </div>
        <div class="detail-wrap">
            <div class="detail-tab-con">
                <ul class="tab-list">
                    <li class="tab-item active">商品详情</li>
                </ul>
            </div>
            <div class="detail-con">
                    ${res.data.detail}
            </div>
        </div>
    </c:if>
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
<script type="text/javascript" src="../../../../js/detail.js"></script>
</html>

