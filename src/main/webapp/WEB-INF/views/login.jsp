<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<link rel="stylesheet" href="css/bootstrap.min.css" />
</head>
<body>
	<div class="container-fluid">
	<form method=post >
		<div class="row">
			<div class="col-xs-8 col-md-6 col-md-offset-3 col-lg-2 col-lg-offset-5">
				<div class="input-group">
				  	<span class="input-group-addon glyphicon glyphicon-phone" id="basic-addon1"></span>
				  	<input type="text" class="form-control" id="username" name="phone" aria-describedby="basic-addon1">
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-8 col-md-6 col-md-offset-3 col-lg-2 col-lg-offset-5">
				<div class="input-group">
					<input type="text" class="form-control" name="code" >
				  	<span class="input-group-btn">
						<button class="btn btn-default" id="get-code" type="button">验证码</button>
				  	</span>
				</div><!-- /input-group -->
			</div>
		</div>
		<div class="row">
			<div class="col-xs-8 col-md-6 col-md-offset-3 col-lg-2 col-lg-offset-5">
				<button class="btn btn-success" type="submit">登录</button>
			</div>
		</div>
	</form>
	</div>
</body>
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script>
$(document).ready(function(){
	$('#get-code').click(function(){
		var req={
				'phoneNumber':$('#username').val()
		};
		$.ajax({
			  url: '/guide2/getCode.htm',
			  method: 'POST',
			  data: JSON.stringify(req),
			  headers: {
			    'Accepts': 'application/json',
			    'Content-type': 'application/json'
			  },
			  success: function (data) {
			    console.log(data);
			  }
			});

	});
});
</script>
</html>