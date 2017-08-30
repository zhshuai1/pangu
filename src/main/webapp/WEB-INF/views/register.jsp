<%@ page language="java"
         import="com.sepism.pangu.constant.RegularExpression,com.sepism.pangu.constant.RequestAttribute,com.sepism.pangu.util.LocaleUtil,java.util.Locale"
         pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<% Locale locale = (Locale) request.getAttribute(RequestAttribute.LOCALE); %>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="css/bootstrap3.css"/>
    <link rel="stylesheet" href="css/sep-style.css"/>
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
            padding: 10px;
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
                <div class="form-group">
                    <label for="password">密码</label>
                    <input type="password" class="form-control" id="password" name="password"
                           pattern="<%=RegularExpression.PASSWORD_PATTERN%>"
                           data-error="The password should follow the pattern: <%=RegularExpression.PASSWORD_PATTERN%>"
                           required>
                    <div class="help-block with-errors"></div>
                </div>
                <div class="form-group">
                    <label for="confirm">确认密码</label>
                    <input type="password" class="form-control" id="confirm" data-match="#password"
                           data-match-error="The confirm password should match with password"
                           required>
                    <div class="help-block with-errors"></div>
                </div>
                <div class="btn-group btn-group-justified btn-submit">
                    <div class="btn-group">
                        <button id="btn-create" type="submit" class="btn btn-success">创建账户</button>
                    </div>
                    <div class="btn-group">
                        <button id="btn-cancel" type="button" class="btn btn-default">返回</button>
                    </div>
                </div>
            </form>
        </li>
        <li id="complete-info" class="step" ng-app="questionnaireApp">
            <%-- In controller, when angular retrieve values from attributes, it will eval the value, so here we should
            use "'Cn'". After eval, the string will get a string and not a variable name.
            Besides, here we use JSP comment to avoid this shown to the user.--%>
            <questionnaire locale="'Cn'" questionnaire-id="2" url="/complete-info"></questionnaire>
            <input type="hidden" name="username" id="complete-info-username" form="questionnaire-form">
            <input type="hidden" name="token" id="authentication-token" form="questionnaire-form">
        </li>
        <li id="congratulations" class="step">
            <div style="text-align: center; margin-top: 50px;">
                <p><span class="glyphicon glyphicon-ok" aria-hidden="true" style="font-size: 300%"></span></p>
                <p><a href="/login">恭喜您完成注册，点此登录</a></p>
            </div>

        </li>
    </ul>
</div>
<%@include file="foot.jsp" %>
</body>
<script src="/js/jquery.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/bootstrap-validator.js"></script>
<script src="/js/angular-1.6.4.js"></script>
<script src="/js/show-questionnaire/show-questionnaire.js"></script>
<script>
    function getFormData(selector) {
        var data = {};
        $(selector).serializeArray().map(function (x) {
            if (data[x.name] !== undefined) {
                if (!data[x.name].push) {
                    data[x.name] = [data[x.name]];
                }
                data[x.name].push(x.value || '');
            } else {
                data[x.name] = x.value || '';
            }
        });
        return data;
    }
    function completeCallback() {
        $(".steps .step").hide();
        $("#congratulations").show();
    }
    $(document).ready(function () {
        $(".steps .step").hide();
        $("#select-method").show();
        $(".register-method").click(function () {
            var usernameId = "#" + $(this).data()["type"] + "-username";
            $("#holder").html($($(usernameId).html()));
            $(".steps .step").hide();
            $("#registration").show();
            $("#registration-form").validator("destroy").validator();
            $(".steps-nav > li").removeClass("current-step");
            $("#step2").addClass("current-step");
        });
        $("#btn-create").click(function (e) {
            e.preventDefault();
            if ($(this).hasClass("disabled")) {
                return;
            }
            var data = getFormData("#registration-form");
            $.ajax({
                url: "/register",
                type: "POST",
                dataType: "json",
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(data),
                success: function (response) {
                    var errorCode = response.errorCode;
                    if (errorCode == "SUCCESS") {
                        $(".steps .step").hide();
                        $("#complete-info").show();
                        $(".steps-nav > li").removeClass("current-step");
                        $("#step3").addClass("current-step");
                        $("#complete-info-username").val(response.userId);
                        $("#authentication-token").val(response.token);
                    } else if (errorCode == "USER_EXIST") {
                        alert("该用户名已被注册.");
                    } else if (errorCode == "INVALID_INPUT") {
                        alert("您填入的信息包含无效的信息.");
                    } else {
                        alert("啊哦，出现了未知的问题，请联系: zh_ang_ok@yeah.net");
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    console.log(XMLHttpRequest.status);
                    console.log(XMLHttpRequest.readyState);
                    console.log(textStatus);
                    console.log(errorThrown);
                    alert("啊哦，出现了未知的问题，请联系: zh_ang_ok@yeah.net");
                }
            });

        });
        $("#btn-cancel").click(function () {
            $(".steps .step").hide();
            $("#select-method").show();
            $(".steps-nav > li").removeClass("current-step");
            $("#step1").addClass("current-step");

        });
        $("#holder").on("click", "#get-validation-code", function () {
            var phone = $("#phone-number").val();
            $.ajax({
                url: "/validationCode?phone=" + phone,
                type: "POST",
                dataType: "json",
                success: function (response) {
                    if (response.errorCode == "SUCCESS") {
                        alert("获取验证码成功.");
                    } else {
                        alert("服务器异常，请联系zh_ang_ok@yeah.net.");
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    alert("获取验证码失败，请联系zh_ang_ok@yeah.net");
                }
            });
        });
    });
</script>
<template id="phone-username">
    <input type="hidden" name="type" value="phone">
    <div class="form-group">
        <label for="username">手机号</label>
        <div class="input-group">
            <span class="input-group-addon">+86</span>
            <input type="text" class="form-control" id="phone-number" name="username"
                   pattern="<%=RegularExpression.PHONE_PATTERN%>"
                   data-error="Phone number should follow the pattern: <%=RegularExpression.PHONE_PATTERN%>"
                   required>
        </div>
        <div class="help-block with-errors"></div>
    </div>
    <div class="form-group">
        <label for="validation-code">验证码</label>
        <div class="input-group">
            <input type="text" class="form-control" id="validation-code" name="validationCode" required>
            <span class="input-group-addon btn btn-default" id="get-validation-code">获取验证码</span>
        </div>
        <div class="help-block with-errors"></div>
    </div>
</template>
<template id="username-username">
    <input type="hidden" name="type" value="username">
    <div class="form-group">
        <label for="username">用户名</label>
        <input type="text" class="form-control" id="username" name="username"
               pattern="<%=RegularExpression.USERNAME_PATTERN%>"
               data-error="Username should follow the pattern: <%=RegularExpression.USERNAME_PATTERN%>"
               required>
        <div class="help-block with-errors"></div>
    </div>
</template>
<template id="email-username">
    <input type="hidden" name="type" value="email">
    <div class="form-group">
        <label for="username">邮箱</label>
        <input type="email" class="form-control" id="email" name="username"
               data-error="You should fill in a valid email address"
               required>
        <div class="help-block with-errors"></div>
    </div>
</template>
</html>