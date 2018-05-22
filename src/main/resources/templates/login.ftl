<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>欢迎登录</title>
    <#include "/common/header.ftl" >
    <link href="/Wopop_files/style_log.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" type="text/css" href="/Wopop_files/style.css">
    <link rel="stylesheet" type="text/css" href="/Wopop_files/userpanel.css">
    <#--<link rel="stylesheet" type="text/css" href="/Wopop_files/jquery.ui.all.css">-->

</head>

<body class="login" mycollectionplug="bind">
<div class="login_m">
    <div class="login_logo"><img src="/Wopop_files/timg.jpg" width="300"></div>
    <div class="login_boder">
        <form name="baseInfo" id="baseInfo" autocomplete="off" novalidate>
            <div class="login_padding" id="login_model">

                <h2>用户名</h2>
                <label>
                    <input type="text" id="username" autocomplete="new-username" class="txt_input txt_input2"
                           onfocus="if (value ==&#39;Your name&#39;){value =&#39;&#39;}"
                           onblur="if (value ==&#39;&#39;){value=&#39;Your name&#39;}">
                </label>
                <h2>密码</h2>
                <label>
                    <input type="password" autocomplete="new-password" name="textfield2" id="userpwd" class="txt_input"
                           onfocus="if (value ==&#39;******&#39;){value =&#39;&#39;}"
                           onblur="if (value ==&#39;&#39;){value=&#39;******&#39;}">
                </label>


                <p class="forgot"><a id="iforget" href="javascript:void(0);">忘记密码?</a></p>
                <div class="rem_sub">
                    <div class="rem_sub_l">
                        <input type="checkbox" name="checkbox" id="save_me">
                        <label for="checkbox">Remember me</label>
                    </div>
                    <label>
                        <input type="button" onclick="login()" class="sub_button" name="button" id="button"
                               value="SIGN-IN"
                               style="opacity: 0.7;">
                    </label>
                </div>
            </div>
        </form>
    <#--<div class="copyrights">******* <a href="http://www.cssmoban.com/">****</a></div>-->

    <#--忘记密码-->
        <div id="forget_model" class="login_padding" style="display:none">
            <br>

            <h1>Forgot password</h1>
            <br>
            <div class="forget_model_h2">(Please enter your registered email below and the system will automatically
                reset users’ password and send it to user’s registered email address.)
            </div>
            <label>
                <input type="text" id="usrmail" class="txt_input txt_input2">
            </label>


            <div class="rem_sub">
                <div class="rem_sub_l">
                </div>
                <label>
                    <input type="submit" class="sub_buttons" name="button" id="Retrievenow" value="Retrieve now"
                           style="opacity: 0.7;">
                    　　　
                    <input type="submit" class="sub_button" name="button" id="denglou" value="Return"
                           style="opacity: 0.7;">　　

                </label>
            </div>
        </div>


        <!--login_padding  Sign up end-->
    </div><!--login_boder end-->
</div><!--login_m end-->
<br> <br>
<#--<p align="center"> More Templates <a href="http://www.cssmoban.com/" target="_blank" title="模板之家">模板之家</a> - Collect-->
<#--from <a href="http://www.cssmoban.com/" title="网页模板" target="_blank">网页模板</a></p>-->
</body>
<script>
    function login() {
        var username = $("#username").val();
        var password = $("#userpwd").val();
        $.ajax({
            url: "/ajaxLogin.do",
            data: {username: username, password: password},
            success: function (result) {
//                console.log(result);
                if (result.status == 200) {
                    window.location.href = "layout.do";
                } else {
                    $.messager.alert('登录失败', '用户名或密码不正确', "error", function () {
                        $("#username").focus();
                        $("#username").select();
                    });
                }

            },
            error: function () {
//                $.messager.alert('Warning','The warning message');
            }
        })
    }
</script>
</html>