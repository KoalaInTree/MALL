/**
 * Created by CJ on 2018/1/1.
 */
$(document).ready(function () {
    var orderNo = $("#orderNo").val();
    $(".order-cancel").click(function () {
        var res = confirm("确认要取消该订单吗？");
        if (res){
            $.ajax({
                method: "POST",
                url: "./cancel.do",
                data: {
                    orderNo: orderNo
                },
                dataType: "json",
                success: function (res) {
                    if (res.status == 0){
                        alert("该订单已取消！");
                        window.location.reload();
                    }else {
                        alert("取消订单失败,请重试！");
                    }
                }
            })
        }
    })
})