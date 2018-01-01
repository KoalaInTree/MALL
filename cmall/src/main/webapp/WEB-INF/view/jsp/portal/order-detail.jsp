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
    <title>订单详情--MMall电商平台</title>
    <link rel="shortcut icon" href="//s.happymmall.com/mmall_fe/dist/favicon.ico">
    <link href="../../../../css/common.css" rel="stylesheet">
    <link href="../../../../css/order-detail.css" rel="stylesheet">
</head>
<body>
<div class=site-nav>
    <div class=w>
        <div class=user-info>
            <c:if test="${CURRENT_USER == null}">
                <span class="link"><a class="link" href="../user/goLogin.do">登录</a></span>
                <span class="link"><a class="link" href="../register.do">注册</a></span>
                </span>
            </c:if>
            <c:if test="${CURRENT_USER != null}">
            <span class="">
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
            <span class="crumb-item">订单详情</span>
        </div>
    </div>
</div>
<div class="order-detail-wrap w">
    <div class="left-con">
        <ul class="menu">
            <li class="menu-item"><a class="link" href="../user/get_userInfo.do">个人中心</a></li>
            <li class="menu-item active"><a class="link" href="../order/order-list.do">我的订单</a></li>
            <li class="menu-item"><a class="link" href="../user/pass-update.do">修改密码</a></li>
            <li class="menu-item"><a class="link" href="../about.do">关于MMall</a></li>
        </ul>
    </div>
    <div class="right-con">
        <div class="panel">
            <div class="panel-title"> 订单信息</div>
            <div class="panel-body">
                <div class="order-info">
                    <input id="orderNo" type="hidden" value="${res.data.orderNo}" />
                    <div class="text-line"><span class="text">订单号：${res.data.orderNo}</span> <span
                            class="text">创建时间：${res.data.createTime}</span>
                    </div>
                    <div class="text-line"><span
                            class="text">收件人：${res.data.receiverName} ${res.data.shippingVo.receiverProvince} ${res.data.shippingVo.receiverCity} ${res.data.shippingVo.receiverAddress}  </span>
                    </div>
                    <div class="text-line"><span class="text">订单状态：${res.data.statusDesc}</span></div>
                    <div class="text-line"><span class="text">支付方式：${res.data.paymentTypeDesc}</span></div>
                    <c:if test="${res.data.status == 10}" >
                    <div class="text-line"><a class="btn btn-mini"
                                              href="./payment.do?orderNo=${res.data.orderNo}">去支付</a> <a
                            class="btn btn-mini order-cancel">取消订单</a></div>
                    </c:if>
                    <c:if test="${res.data.status != 10}">
                    <div class="text-line"></div>
                    </c:if>
                </div>
            </div>
        </div>
        <div class="panel">
            <div class="panel-title"> 商品清单</div>
            <div class="panel-body">
                <table class="order-list-table header">
                    <tbody>
                    <tr>
                        <th class="order-list-cell cell-img">&nbsp;</th>
                        <th class="order-list-cell cell-info">商品信息</th>
                        <th class="order-list-cell cell-price">单价</th>
                        <th class="order-list-cell cell-count">数量</th>
                        <th class="order-list-cell cell-total">合计</th>
                    </tr>
                    </tbody>
                </table>
                <table class="order-list-table order-item">
                    <tbody>
                    <c:forEach items="${res.data.orderItemVoList}" var="item">
                        <tr>
                            <td class="order-list-cell cell-img"><a href="../product/detail.do?productId=${item.productId}" target="_blank"><img
                                    class="p-img" src="${res.data.imageHost}${item.productImage}"
                                    alt="${item.productName}"></a></td>
                            <td class="order-list-cell cell-info"><a class="link p-name" href="../product/detail.do?productId=${item.productId}"
                                                                     target="_blank">${item.productName}</a></td>
                            <td class="order-list-cell cell-price">￥${item.currentUnitPrice}</td>
                            <td class="order-list-cell cell-count">${item.quantity}</td>
                            <td class="order-list-cell cell-total">￥${item.totalPrice}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <p class="total pull-right"><span>订单总价：</span> <span class="enhance">￥${res.data.payment}</span></p></div>
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
<script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript" src="../../../../js/order-detail.js"></script>
</body>
</html>