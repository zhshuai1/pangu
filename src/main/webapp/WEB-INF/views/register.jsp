<%@ page language="java"
         import="com.sepism.pangu.constant.RequestAttribute , com.sepism.pangu.util.LocaleUtil, java.util.Locale"
         pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<% Locale locale = (Locale) request.getAttribute(RequestAttribute.LOCALE); %>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="css/bootstrap3.css"/>
    <link rel="stylesheet" href="css/glyphicons.min.css"/>
    <link rel="shortcut icon" type="image/x-icon" href="icon/sepism.ico" media="screen"/>
    <title><%= LocaleUtil.localize(locale, "TITLE_900") %>
    </title>
    <style>
        body {
            padding-top: 40px;
            padding-bottom: 40px;
            background-color: #ddd;
        }

        .main {
            max-width: 500px;
            margin: 20px auto;
            padding: 20px;
            background-color: #eee;
            border-radius: 10px;
        }

        .nav-step {
            margin: 15px 0;
        }

        ol.steps li {
            display: table-cell;
            border: 1px solid #ddd;
            margin-left: 15px;
        }

        ol.steps li:not(first-child) {
            border-left: 0px;
        }

        ol.steps li:first-child {
            border-left: 1px solid #ddd;
        }

        ol {
            display: table;
            list-style: none;
            width: 100%;
            padding: 0px;
        }

        .step-content {
            display: inline-block;
            color: #aaa;
        }

        .step-icon {
            font-size: 200%;
        }

        .current-step .step-content {
            color: #000;
        }

        .current-step .step-icon {
            color: #0366d6;
        }

        .register-method {
            text-align: center;
            display: flex;
            max-width: 50%;
            height: 50px;
            margin: 10px auto;
            inline-height: 100%;
            justify-content: center;
            align-items: center;
        }

    </style>
</head>
<body>

<div class="main">
    <h1>加入七棱镜</h1>
    <p>发现不一样的自己</p>
    <div class="nav-step">
        <ol class="steps">
            <li class="current-step">
                <div class="step-content step-icon">
                    <span class="glyphicon glyphicon-check" aria-hidden="true"></span>
                </div>
                <div class="step-content">
                    <b>第一步：</b><br>
                    选择注册方式
                </div>
            </li>
            <li>
                <div class="step-content step-icon">
                    <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                </div>
                <div class="step-content">
                    <b>第二步：</b><br>
                    创建账号
                </div>
            </li>
            <li>
                <div class="step-content step-icon">
                    <span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
                </div>
                <div class="step-content">
                    <b>第三步：</b><br>
                    完善个人信息
                </div>
            </li>

        </ol>
    </div>
    <div id="select-method">
        <div class="register-method btn btn-success" data-type="phone">
            <span class="glyphicon glyphicon-phone" aria-hidden="true"></span>&nbsp;手机号
        </div>
        <div class="register-method btn btn-success" data-type="username">
            <span class="glyphicon glyphicon-user" aria-hidden="true"></span>&nbsp;用户名
        </div>
        <div class="register-method btn btn-success" disabled="1" data-type="email">
            <span class="glyphicon glyphicon-envelope" aria-hidden="true"></span>&nbsp;邮箱
        </div>
    </div>
    <div id="register-form-phone" style="display: none;">
        <form method="post" action="/register" id="phone-registration">
            <label for="username">手机号</label>
            <div class="input-group">
                <span class="input-group-addon">+86</span>
                <input type="text" class="form-control" id="username" name="username">
            </div>
            <label for="validation-code">验证码</label>
            <div class="input-group">
                <input type="text" class="form-control" id="validation-code" name="validation-code">
                <span class="input-group-addon btn btn-default">获取验证码</span>
            </div>
            <label for="password">密码</label>
            <input type="password" class="form-control" id="password" name="password">
            <label for="confirm">确认密码</label>
            <input type="password" class="form-control" id="confirm">
            <div class="btn-group">
                <button type="button" class="btn btn-success">创建账户</button>
                <button type="button" class="btn btn-default">返回</button>
            </div>
        </form>
    </div>

</div>
</body>
<script src="/js/jquery.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script>
    $(document).ready(function () {
        $(".register-method").click(function () {
            $("#register-form-phone").show();
            $("#select-method").hide();
        });
    });
</script>
</html>