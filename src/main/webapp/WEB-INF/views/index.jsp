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
      padding-bottom: 20px;
      padding-top: 20px;
    }
    .navbar {
      margin-bottom: 20px;
    }
  </style>
</head>
<% Locale locale = (Locale) request.getAttribute(RequestAttribute.LOCALE); %>
<body>
  <nav class="navbar navbar-inverse bg-inverse navbar-toggleable-md">
    <div class="container">
      <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <a class="navbar-brand" href="#"><%=LocaleUtil.localize(locale,"SEPISM_041")%></a>

      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
          <li class="nav-item active">
            <a class="nav-link" href="#"><%=LocaleUtil.localize(locale,"INDEX_042")%> <span class="sr-only">(current)</span></a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#"><%=LocaleUtil.localize(locale,"ANSWER_043")%></a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#"><%=LocaleUtil.localize(locale,"ACCOUNT_045")%></a>
          </li>
        </ul>
        <form class="form-inline my-2 my-lg-0">
          <input class="form-control mr-sm-2" type="text" placeholder="<%=LocaleUtil.localize(locale,"SEARCH_044")%>">
          <button class="btn btn-outline-success my-2 my-sm-0" type="submit"><%=LocaleUtil.localize(locale,"SEARCH_044")%></button>
        </form>
      </div>
    </div>
  </nav>
  <div class="container">
    <div class="jumbotron">
    </div>
  </div>
</body>
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script>
</script>
</html>