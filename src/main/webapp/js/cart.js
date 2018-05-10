/**
 * Created by CJ on 2017/12/27.
 // */
$(document).ready(function () {
    $(".count-btn").click(function () {
        var e = $(this);
        var r = e.siblings(".count-input");
        var n = e.data("opera-type");
        var s = e.parents(".cart-table").data("product-id");
        var price_s = e.parent().siblings(".cell-price").text();
        var productTotle_s = e.parent().siblings(".cell-total").text();
        var price = price_s.substring(1);
        var productTotle = productTotle_s.substring(1);
        var totle_s = $(".submit-total").text();
        var totle = totle_s.substring(1);
        var i = parseInt(r.val());
        var o = 1;
        var u = parseInt(r.data("max"));
        var l = 0;

        if ("plus" === n) {
            if (i >= u)return void alert("该商品数量已达到最大限额");
            l = i + 1;
        } else if ("minus" === n) {
            if (i <= o)return;
            l = i - 1
        }
        $.ajax({
            method: "POST",
            data: {
                productId: s,
                count: l
            },
            dataType: "json",
            url: "./update.do",
            success: function (res) {
                if (res.status == 0) {
                    r.val(l);
                    if ("plus" === n) {
                        e.parent().siblings(".cell-total").text("￥" + (parseInt(productTotle) + parseInt(price)));
                        $(".submit-total").text("￥" + (parseInt(totle) + parseInt(price)));
                    } else if ("minus" === n) {
                        e.parent().siblings(".cell-total").text("￥" + (parseInt(productTotle) - parseInt(price)));
                        $(".submit-total").text("￥" + (parseInt(totle) - parseInt(price)));
                    }
                } else {
                    alert(res.msg);
                }
            }
        })

    })
    $(".count-input").change(function () {
        var e = $(this);
        var u = e.data("max");
        var i = parseInt(e.val());
        var s = e.parents(".cart-table").data("product-id");
        if (i < 1) {
            alert("请输入正确的值");
            return;
        } else if (i > u) {
            alert("该商品数量已达到最大限额");
            i = u;
        }
        $.ajax({
            method: "POST",
            data: {
                productId: s,
                count: i
            },
            dataType: "json",
            url: "./update.do",
            success: function (res) {
                if (res.status == 0) {
                    window.location.reload();
                } else {
                    alert(res.msg);
                    return false;
                }
            }
        })
    })
    $(".cart-delete").click(function () {
        if (window.confirm("确认要删除当前商品？")) {
            var e = $(this);
            var s = e.parents(".cart-table").data("product-id");
            $.ajax({
                method: "get",
                url: "./delete_product_one.do",
                data: {
                    productId: s
                },
                dataType: "json",
                success: function (res) {
                    if (res.status != 0) {
                        alert(res.msg);
                    }
                    window.location.reload();
                }
            })
        }
    })
    $(".cart-select-all").click(function () {
        var e = $(this);
        if (e.attr("checked")) {
            $.ajax({
                method: "get",
                url: "./un_select_all.do",
                success: function (res) {
                    if (res.status != 0) {
                        alert(res.msg);
                        return;
                    }
                    $(":checkbox").removeAttr("checked");
                }
            })
        } else {
            $.ajax({
                method: "get",
                url: "./select_all.do",
                success: function (res) {
                    if (res.status != 0) {
                        alert(res.msg);
                        return;
                    }
                    $(":checkbox").attr("checked","checked");
                }
            })
        }
    })
    $(document).on("click", ".submit-btn", function () {
        window.location.href = "./confirm.do"
    })
})