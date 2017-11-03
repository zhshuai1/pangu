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

        .reports {
            list-style: none;
            display: flex;
            flex-direction: row;
            flex-wrap: wrap;
            max-width: 300px;
            margin: 0 auto;
            text-align: center;
            padding: 0px;
        }

        .report-box {
            list-style: none;
            outline: 1px red solid;
        }

        .report-box > report > a > div {
            width: 300px;
            height: 280px;
            margin: 0 20px 20px 30px;
        }
    </style>
</head>
<body>
<%@include file="head.jsp" %>

<div class="container" ng-app="showReportApp">
    <div class="jumbotron questionnaire-list" ng-controller="showReportController">
        <ul ng-cloak class="ng-cloak reports">
            <li class="report-box" ng-repeat="report in questionReports" on-finish-render-filters>
                <report report-data="report"></report>
            </li>
        </ul>
    </div>
</div>

<%@include file="foot.jsp" %>
</body>
<script src="/js/jquery.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/bootstrap-validator.js"></script>
<script src="/js/angular-1.6.4.js"></script>
<script src="/js/echarts.js"></script>
<script src="/js/show-questionnaire/show-report.js"></script>

</html>
