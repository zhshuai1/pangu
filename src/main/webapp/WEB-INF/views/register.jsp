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
            background-color: #eee;
        }

        .main {
            max-width: 500px;
            margin: 20px auto;
            padding: 20px;
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
                    <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                </div>
                <div class="step-content">
                    第一步：<br>
                    创建账号
                </div>
            </li>
            <li>
                <div class="step-content step-icon">
                    <span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
                </div>
                <div class="step-content">
                    第二步：<br>
                    完善个人信息
                </div>
            </li>
            <li>
                <div class="step-content step-icon">
                    <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                </div>
                <div class="step-content">
                    第三步：<br>
                    完成！
                </div>
            </li>
        </ol>
    </div>
    <div id="register-form container">

    </div>

</div>
</body>
</html>