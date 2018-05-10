/**
 * Created by CJ on 2017/12/27.
 */

$(".p-count-btn").click(function () {
    var e = $(this).text(),
        r = $(".p-count"),
        n = parseInt(r.val()),
        i = 1,
        pre_s = $("#stock");
        s = parseInt(pre_s.val());
    "+" === e ? r.val(n < s ? n + 1 : s) : "-" === e && r.val(n > i ? n - 1 : 1)
})
$(".cart-add").click(function () {
    $.ajax({
        type:'POST',
        url:'http://localhost:8088/cart/add.do',
        data:{productId: $("#productId").val(), count: $(".p-count").val()},
        dataType: 'json',
        success:function(result){
            if (result.status == 0){
                window.location.href='http://localhost:8088/cart/result.do?status='+result.status;
            }else if(result.status == 10){
                window.location.href='../user/goLogin.do';
            }

        }
    })
})