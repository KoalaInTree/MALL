/**
 * Created by CJ on 2017/12/31.
 */
$(document).ready(function () {
    var phone = $("#phone").val();
    var email = $("#email").val();
    var question = $("#question").val();
    var answer = $("#answer").val();
    $(".btn-submit").click(function () {
        $.ajax({
            method: 'POST',
            url: "./update_information.do",
            data:{
                phone: phone,
                email: email,
                question: question,
                answer: answer
            },
            dataType:'json',
            success: (function (res) {
                if (res.status == '0'){
                    alert('更新个人信息成功');
                    window.location.href='./get_userInfo.do'
                }else {
                    alert('更新个人信息失败，请重试');
                }

            })
    })
    })
})