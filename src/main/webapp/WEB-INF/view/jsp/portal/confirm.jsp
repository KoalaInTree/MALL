<%--
  Created by IntelliJ IDEA.
  User: CJ
  Date: 2017/12/28
  Time: 16:01
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
    <title>订单确认--MMall电商平台</title>
    <link rel="shortcut icon" href="//s.happymmall.com/mmall_fe/dist/favicon.ico">
    <link href="../../../../css/common.css" rel="stylesheet">
    <link href="../../../../css/confirm.css" rel="stylesheet">
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
            <span class="crumb-item">我的订单</span>
        </div>
    </div>
</div>
<div class="confirm-wrap w">
    <div class="panel">
        <h2 class="panel-title">收货地址</h2>
        <c:if test="${res1.status == '0' && res1.data.size != '0'}">
        <input type="hidden" id="selectId" value="" />
            <div class="panel-body address-list">
                <c:forEach items="${res1.data.list}" var="shipping">
                <a class="address-item" data-id="${shipping.id}">
                <div class="address-title"><span class="city">${shipping.receiverProvince} ${shipping.receiverCity}</span> <span class="name">（ ${shipping.receiverName} 收 ）</span></div>
                <div class="address-detail"> ${shipping.receiverAddress} ${shipping.receiverPhone}</div>
                <div class="address-opera"><span class="link address-update">编辑</span> <span
                        class="link address-delete">删除</span></div>
                </a>
                </c:forEach>
        </c:if>
            <div class="address-item add">
                <div class="address-new"><i class="fa fa-plus"></i>
                    <div class="text">使用新地址</div>
                </div>
            </div>
        </div>
    </div>


    <div class="panel">
        <h2 class="panel-title">商品清单</h2>
        <c:if test="${res.status == '0'}">
        <div class="panel-body order-product">
            <table class="product-table" data-product-id="" data-checked="">
                <tbody>
                <tr>
                    <th width="10%">&nbsp;</th>
                    <th width="30%" align="left">商品描述</th>
                    <th width="20%" align="center">价格</th>
                    <th width="20%" align="center">数量</th>
                    <th width="20%" align="center">小计</th>
                </tr>
                <c:forEach items="${res.data.cartProductVoList}" var="pro">
                <tr>
                    <td class="product-cell cell-img"><a href="../product/detail.do?productId=${pro.productId}" target="_blank"><img
                            class="p-img" src="${res.data.imageHost}${pro.productMainImage}"
                            alt="${pro.productName}"></a></td>
                    <td align="left"><a class="link p-name" href="../product/detail.do?productId=${pro.productId}" target="_blank">${pro.productSubtitle}</a></td>
                    <td align="center">￥${pro.productPrice}</td>
                    <td align="center">x${pro.quantity}</td>
                    <td align="center">￥${pro.productTotalPrice}</td>
                </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="submit-con"><span>订单总价：</span> <span class="submit-total">￥${res.data.cartTotalPrice}</span> <span
                    class="btn order-submit">提交订单</span></div>
        </div>
    </div>
    </c:if>
</div>
<div class="modal-wrap"></div>
<div class=footer>
    <div class=w>
        <div class=links><a class=link href=http://www.imooc.com/ >慕课网</a> | <a class=link href=http://www.baidu.com/ >百度</a>
            | <a class=link href=http://www.zhihu.com/ >知乎</a> | <a class=link href=http://www.taobao.com/ >淘宝</a></div>
        <p class=copyright> Copyright © 2017 happymmall.com All Rights Reserved </p></div>
</div>
<script src=//cdn.bootcss.com/jquery/1.11.3/jquery.js></script>
<script type="text/javascript" src="../../../../js/base.js"></script>
<script type="text/javascript" src="../../../../js/common.js"></script>
<script type="text/javascript" src="../../../../js/confirm.js"></script>
</body>
</html>
