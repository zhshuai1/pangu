<%@ page language="java" import="java.util.* , com.sepism.pangu.util.LocaleUtil, com.sepism.pangu.constant.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<link rel="stylesheet" href="css/bootstrap.min.css" />
	<link rel="shortcut icon" type="image/x-icon" href="icon/sepism.ico" media="screen" />
	<title>Sepism, Find Unique Yourself</title>
    <style>
    body {
      padding-top: 40px;
      padding-bottom: 40px;
      background-color: #eee;
    }

    .form-signin {
      max-width: 330px;
      padding: 15px;
      margin: 0 auto;
    }
    .form-signin .form-signin-heading,
    .form-signin .checkbox {
      margin-bottom: 10px;
    }
    .form-signin .checkbox {
      font-weight: normal;
    }
    .form-signin .form-control {
      position: relative;
      height: auto;
      -webkit-box-sizing: border-box;
         -moz-box-sizing: border-box;
              box-sizing: border-box;
      padding: 10px;
      font-size: 16px;
    }
    .form-signin .form-control:focus {
      z-index: 2;
    }
    .form-signin input[type="email"] {
      margin-bottom: -1px;
      border-bottom-right-radius: 0;
      border-bottom-left-radius: 0;
    }
    .form-signin input[type="password"] {
      margin-bottom: 10px;
      border-top-left-radius: 0;
      border-top-right-radius: 0;
    }
    </style>
</head>
<% Locale locale = (Locale) request.getAttribute(RequestAttribute.LOCALE); %>
<body>
	<div class="container" style="margin:40px auto; max-width:330px">
      <form class="form-signin" action="/login" method="POST">
        <h2 class="form-signin-heading"><%= LocaleUtil.localize(locale,"PLEASE_SIGN_IN_001") %></h2>
        <label for="userName" class="sr-only">Email address</label>
        <input id="userName" name="userName" class="form-control" placeholder='<%= LocaleUtil.localize(locale,"ACCOUNT_NAME_004") %>' required="" autofocus="" value="" type="text">
        <label for="password" class="sr-only">Password</label>
        <input id="password" name="password" class="form-control" placeholder='<%= LocaleUtil.localize(locale,"PASSWORD_005") %>' required="" type="password">
        <div class="checkbox">
          <label>
            <input value="remember-me" type="checkbox"> <%= LocaleUtil.localize(locale,"REMEMBER_ME_002") %>
          </label>
          <div style="float:right;"> <a href="#" ><%= LocaleUtil.localize(locale,"FORGET_PASSWORD_003") %></a></div>
        </div>
        <button class="btn btn-lg btn-success btn-block" type="submit"><%= LocaleUtil.localize(locale,"SIGN_IN_006") %></button>
      </form>
    </div> <!-- /container -->
</body>
<script src="js/bootstrap.min.js"></script>
</html>