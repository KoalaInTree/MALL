/**
 * Created by CJ on 2017/12/29.
 */
$(document).on("click", ".order-submit", function () {
    var shippingId = $("#selectId").val();
    if (shippingId.length>0){

        $.ajax({
            method: "POST",
            url: "../order/create.do?shippingId="+ shippingId,
            success : function(res){
                var orderNo = res.data.orderNo;
                window.location.href = "../order/payment.do?orderNo=" + orderNo;
            }
        })
    }else{
        alert("请选择地址后再提交订单");
    }
    //var e = t.data.selectedAddressId;
    //e ? s.create({shippingId: e}, function (t) {
    //    window.location.href = "./payment.html?orderNumber=" + t.orderNo
    //}, function (t) {
    //    i.errorTips(t)
    //}) : i.errorTips("请选择地址后再提交订单")
})
$(document).on("click", ".address-item", function () {
    var e = $(this);
    e.addClass("active").siblings(".address-item").removeClass("active");
    $("#selectId").val(e.data('id'));
})