<%@ page language="java"
         import="com.sepism.pangu.constant.RequestAttribute , com.sepism.pangu.util.LocaleUtil, java.util.Locale"
         pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<% Locale locale = (Locale) request.getAttribute(RequestAttribute.LOCALE); %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="/css/bootstrap3.css"/>
    <link rel="stylesheet" href="/css/sep-style.css"/>
    <link rel="shortcut icon" type="image/x-icon" href="/icon/sepism.ico" media="screen"/>
    <title><%= LocaleUtil.localize(locale, "TITLE_900") %>
    </title>
    <style>
        .questionnaire {
            max-width: 500px;
            margin: 50px auto;
        }

        body {
            padding-bottom: 20px;
            padding-top: 20px;
        }

        .navbar {
            margin-bottom: 20px;
        }

        .thankyou {
            max-width: 500px;
            margin: 50px auto;
        }
    </style>
</head>
<body>
<%@include file="head.jsp" %>
<div id="main">
    <div class="questionnaire" ng-app="questionnaireApp">
        <questionnaire locale="'Cn'" questionnaire-id="'<%=request.getAttribute("id")%>'"
                       target-url="'/submit-questionnaire'"></questionnaire>
    </div>
</div>
<%@include file="foot.jsp" %>
</body>
<script src="/js/jquery.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/bootstrap-validator.js"></script>
<script src="/js/angular-1.6.4.js"></script>
<script src="/js/show-questionnaire/show-questionnaire.js"></script>
<script>
    $(document).ready(function () {
        $("#navbarSupportedContent li").removeClass("active");
        $("#nav-submit").addClass("active");
    });
    function completeCallback() {
        $("#main").html($("#thankyou-page").html());
    }
</script>
<template id="thankyou-page">
    <div class="thankyou">
        <h3>感谢您的作答</h3>
        <div>
            <a href="/reports/<%=request.getAttribute("id")%>">查看问卷结果</a>
        </div>
        <div>
            <a href="/viewQuestionnaires">再回答一份问卷</a>
        </div>
    </div>
</template>
</html>
