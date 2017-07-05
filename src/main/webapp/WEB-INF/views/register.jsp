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

        .steps-nav {
            margin: 15px 0;
        }

        ol.steps-nav li {
            display: table-cell;
            border: 1px solid #ddd;
            margin-left: 15px;
        }

        ol.steps-nav li:not(first-child) {
            border-left: 0px;
        }

        ol.steps-nav li:first-child {
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

        .steps {
            list-style: none;
        }

        #registration {
            padding: 0px 30px;
        }

        .btn-submit {
            margin-top: 30px;
        }

        .steps .step {
            display: none;
        }

        .steps .selected-step {
            display: block;
        }
    </style>
</head>
<body>

<div class="main">
    <h1>加入七棱镜</h1>
    <p>发现不一样的自己</p>
    <ol class="steps-nav">
        <li class="current-step" id="step1">
            <div class="step-content step-icon">
                <span class="glyphicon glyphicon-check" aria-hidden="true"></span>
            </div>
            <div class="step-content">
                <b>第一步：</b><br>
                选择注册方式
            </div>
        </li>
        <li id="step2">
            <div class="step-content step-icon">
                <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
            </div>
            <div class="step-content">
                <b>第二步：</b><br>
                创建账号
            </div>
        </li>
        <li id="step3">
            <div class="step-content step-icon">
                <span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
            </div>
            <div class="step-content">
                <b>第三步：</b><br>
                完善个人信息
            </div>
        </li>
    </ol>
    <ul class="steps">
        <li id="select-method" class="step selected-step">
            <div class="register-method btn btn-success" data-type="phone">
                <span class="glyphicon glyphicon-phone" aria-hidden="true"></span>&nbsp;手机号
            </div>
            <div class="register-method btn btn-success" data-type="username">
                <span class="glyphicon glyphicon-user" aria-hidden="true"></span>&nbsp;用户名
            </div>
            <div class="register-method btn btn-success" data-type="email">
                <span class="glyphicon glyphicon-envelope" aria-hidden="true"></span>&nbsp;邮箱
            </div>
        </li>
        <li id="registration" class="step">
            <form method="post" action="/register" id="registration-form">
                <div id="holder"></div>
                <label for="password">密码</label>
                <input type="password" class="form-control" id="password" name="password">
                <label for="confirm">确认密码</label>
                <input type="password" class="form-control" id="confirm">
                <div class="btn-group btn-group-justified btn-submit">
                    <div class="btn-group">
                        <button id="btn-create" type="button" class="btn btn-success">创建账户</button>
                    </div>
                    <div class="btn-group">
                        <button id="btn-cancel" type="button" class="btn btn-default">返回</button>
                    </div>
                </div>
            </form>
        </li>
        <li id="complete-info" class="step">
            <form method="post" action="/complete-info" ng-app="questionnaireApp">
                <%-- In controller, when angular retrieve values from attributes, it will eval the value, so here we should
                use "'Cn'". After eval, the string will get a string and not a variable name.
                Besides, here we use JSP comment to avoid this shown to the user.--%>
                <questionnaire locale="'Cn'" questionnaire-id="2"></questionnaire>
                <div class="btn-group btn-group-justified btn-submit">
                    <div class="btn-group">
                        <button id="btn-complete" type="button" class="btn btn-success">完成</button>
                    </div>
                </div>
            </form>
        </li>
    </ul>

</div>
</body>
<script src="/js/jquery.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/angular-1.6.4.js"></script>
<script src="/js/show-questionnaire/show-questionnaire.js"></script>
<script>
    $(document).ready(function () {
        $(".steps .step").hide();
        $("#select-method").show();
        $(".register-method").click(function () {
            var usernameId = "#" + $(this).data()["type"] + "-username";
            $("#holder").html($($(usernameId).html()));
            $(".steps .step").hide();
            $("#registration").show();
            $(".steps-nav > li").removeClass("current-step");
            $("#step2").addClass("current-step");
        });
        $("#btn-create").click(function (e) {
            e.preventDefault();
            var data = $("#registration-form").serialize();
            $.ajax({
                url: "/register",
                type: "POST",
                dataType: "json",
                contentType: "application/x-www-form-urlencoded",
                data: data,
                success: function (response) {
                    $(".steps .step").hide();
                    $("#complete-info").show();
                    $(".steps-nav > li").removeClass("current-step");
                    $("#step3").addClass("current-step");
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    console.log(XMLHttpRequest.status);
                    console.log(XMLHttpRequest.readyState);
                    console.log(textStatus);
                    console.log(errorThrown);
                }
            });

        });
        $("#btn-cancel").click(function () {
            $(".steps .step").hide();
            $("#select-method").show();
            $(".steps-nav > li").removeClass("current-step");
            $("#step1").addClass("current-step");

        });
    });
</script>
<template id="phone-username">
    <input type="hidden" name="type" value="phone">
    <label for="username">手机号</label>
    <div class="input-group">
        <span class="input-group-addon">+86</span>
        <input type="text" class="form-control" id="phone-number" name="username">
    </div>
    <label for="validation-code">验证码</label>
    <div class="input-group">
        <input type="text" class="form-control" id="validation-code" name="validation-code">
        <span class="input-group-addon btn btn-default">获取验证码</span>
    </div>
</template>
<template id="username-username">
    <input type="hidden" name="type" value="username">
    <label for="username">用户名</label>
    <input type="text" class="form-control" id="username" name="username">
</template>
<template id="email-username">
    <input type="hidden" name="type" value="email">
    <label for="username">邮箱</label>
    <input type="text" class="form-control" id="email" name="username">
</template>
</html>