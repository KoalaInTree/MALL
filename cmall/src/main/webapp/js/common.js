/**
 * Created by CJ on 2017/12/26.
 */
webpackJsonp([16], {
    0: function (n, t, c) {
        n.exports = c(49)
    }, 17: function (n, t) {
    }, 20: function (n, t) {
    }, 49: function (n, t, c) {
        "use strict";
        c(20), c(17)
    }
});

$(document).ready(function () {
    $(".link-logout").click(function (data) {
        $.ajax({
            type:'get',
            url:'http://localhost:8088/user/logout.do',
            success:function(result){
                window.location.reload();
            }
        })
    })
    $.ajax({
        type:'get',
        url:'http://localhost:8088/cart/get_cart_product_count.do',
        success:function(result){
            if (result.status == 0){
                $(".cart-count").text(result.data);
            }
            return;
        }
    })
})