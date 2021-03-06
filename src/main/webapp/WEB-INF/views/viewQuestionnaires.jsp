<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@include file="common.jsp" %>
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
        body {
            padding-bottom: 20px;
            padding-top: 20px;
        }

        .navbar {
            margin-bottom: 20px;
        }

        ul, li {
            list-style: none;
            display: block;
            text-align: left;
        }

        .questionnaire-list .btn {
            text-align: left;
        }
    </style>
</head>
<body>
<%@ include file="head.jsp" %>
<div class="container" ng-app="viewQuestionnairesApp">
    <div class="jumbotron questionnaire-list" ng-controller="viewQuestionnairesController">
        <h2>热门问卷</h2>
        <ul ng-cloak class="ng-cloak">
            <li ng-repeat="questionnaire in questionnaires" class="btn btn-default form-control ">
                <span class="glyphicon glyphicon-certificate" aria-hidden="true"></span>
                <a href="/questionnairePage/{{questionnaire.id}}">{{questionnaire.titleCn}}</a>
            </li>
        </ul>
    </div>
</div>
<%@include file="foot.jsp" %>
</body>
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="/js/angular-1.6.4.js"></script>
<script src="/js/show-questionnaire/view-questionnairelist.js"></script>
<script>
    $(document).ready(function () {
        $("#navbarSupportedContent li").removeClass("active");
        $("#nav-submit").addClass("active");
    });
</script>
<script>
</script>
</html>