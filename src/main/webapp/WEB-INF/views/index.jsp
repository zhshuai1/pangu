<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@include file="common.jsp" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="/css/bootstrap3.css"/>
    <link rel="stylesheet" href="/css/sep-style.css"/>
    <link rel="shortcut icon" type="image/x-icon" href="icon/sepism.ico" media="screen"/>
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

        .questionnaire-list .btn {
            text-align: left;
        }
    </style>
</head>
<body>
<%@ include file="head.jsp" %>
<div class="container">
    <%--
    <div id="myCarousel" class="carousel slide" data-ride="carousel">
        <ol class="carousel-indicators">
            <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
            <li data-target="#myCarousel" data-slide-to="1"></li>
            <li data-target="#myCarousel" data-slide-to="2"></li>
        </ol>
        <div class="carousel-inner">
            <div class="item active">
                <h1>First Item</h1>
            </div>
            <div class="item">
                <h1>Second Item</h1>
            </div>
            <div class="item">
                <h1>Third Item</h1>
            </div>
        </div>
        <a class="carousel-control left" href="#myCarousel"
           data-slide="prev">&lsaquo;</a>
        <a class="carousel-control right" href="#myCarousel"
           data-slide="next">&rsaquo;</a>
    </div>
    --%>
    <div class="container" ng-app="viewQuestionnaireReportsApp">
        <div class="jumbotron questionnaire-list" ng-controller="viewQuestionnaireReportsController">
            <h2>热门数据</h2>
            <ul ng-cloak class="ng-cloak">
                <li ng-repeat="report in reports" class="btn btn-default form-control ">
                    <span class="glyphicon glyphicon-certificate" aria-hidden="true"></span>
                    <a href="/reports/{{report.id}}">{{report.titleCn}}</a>
                </li>
            </ul>
        </div>
    </div>
</div>
<%@include file="foot.jsp" %>
</body>
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="/js/angular-1.6.4.js"></script>
<script src="/js/show-questionnaire/view-reportlist.js"></script>
<script>
</script>
</html>