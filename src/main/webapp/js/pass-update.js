/**
 * Created by CJ on 2018/1/1.
 */
$(document).ready(function () {
    $(".btn-submit").click(function () {
        var password = $("#password").val();
        var passwordNew = $("#password-new").val();
        var passwordConfirm = $("#password-confirm").val();
        if(passwordNew.length < 6){
            alert("密码不能小于6位");
            return
        }
        if(passwordNew !== passwordConfirm){
            alert("输入的两次密码不一致");
            return
        }
        $.ajax({
            method: "POST",
            url: "../user/reset_password.do",
            data : {
                passwordOld: password,
                passwordNew: passwordNew
            },
            dataType: "json",
            success: function(res){
                alert(res.msg);
            }
        })
    })
})