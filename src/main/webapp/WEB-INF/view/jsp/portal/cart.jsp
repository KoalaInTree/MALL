<%--
  Created by IntelliJ IDEA.
  User: CJ
  Date: 2017/12/27
  Time: 17:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>购物车--MMall电商平台</title>
    <link rel="shortcut icon" href="//s.happymmall.com/mmall_fe/dist/favicon.ico">
    <link href="../../../../css/common.css" rel="stylesheet">
    <link href="../../../../css/cart.css" rel="stylesheet">
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
            <span class="link">
                <span>欢迎，
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
            <span class="crumb-item">我的购物车</span>
        </div>
    </div>
</div>
<div class="cart-wrap w">
    <div class="cart-header">
        <table class="cart-table">
            <tbody>
            <tr>
                <th class="cart-cell cell-check"><label> <input type="checkbox" class="cart-select-all"
                                                                checked="checked"> <span>全选</span> </label></th>
                <th class="cart-cell cell-info">商品信息</th>
                <th class="cart-cell cell-price">单价</th>
                <th class="cart-cell cell-count">数量</th>
                <th class="cart-cell cell-total">合计</th>
                <th class="cart-cell cell-opera">操作</th>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="cart-list">
        <c:if test="${res.status == 0}">
            <c:forEach items="${res.data.cartProductVoList}" var="pro">
                <table class="cart-table" data-product-id="${pro.productId}" data-checked="1">
                    <tbody>
                    <tr>
                        <td class="cart-cell cell-check"><input type="checkbox" class="cart-select" checked="checked">
                        </td>
                        <td class="cart-cell cell-img">
                            <a href="./detail.do?productId=${pro.productId}" target="_blank">
                                <img class="p-img" src="${res.data.imageHost}${pro.productMainImage}"
                                     alt="${pro.productName}"></a>
                        </td>
                        <td class="cart-cell cell-info">
                            <a class="link p-name" href="./detail.do?productId=${pro.productId}"
                               target="_blank">${pro.productName}</a>
                        </td>
                        <td class="cart-cell cell-price">￥${pro.productPrice}</td>
                        <td class="cart-cell cell-count">
                            <span class="count-btn" data-opera-type="minus">-</span>
                            <input class="count-input" data-max="${pro.productStock}" value="${pro.quantity}">
                            <span class="count-btn" data-opera-type="plus">+</span>
                        </td>
                        <td class="cart-cell cell-total">￥${pro.productTotalPrice}</td>
                        <td class="cart-cell cell-opera"><a class="link cart-delete">删除</a></td>
                    </tr>
                    </tbody>
                </table>
            </c:forEach>
        </c:if>
    </div>
    <div class="cart-footer clear">
        <div class="select-con"><label> <input type="checkbox" class="cart-select-all" checked="checked">
            <span>全选</span> </label></div>
        <div class="delete-con"><a class="cart-delete-seleced link"> <i class="fa fa-trash-o" aria-hidden="true"></i>
            <span>删除选中</span> </a></div>
        <div class="submit-con"><span>总价：</span> <span class="submit-total">￥${res.data.cartTotalPrice}</span> <span class="btn submit-btn">去结算</span>
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
<script type="text/javascript" src="../../../../js/cart.js"></script>
</body>
</html>
