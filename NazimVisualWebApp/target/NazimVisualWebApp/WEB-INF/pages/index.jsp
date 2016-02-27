<html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<head>
<META http-equiv=content-type content=text/html;charset=iso-8859-9>
<META http-equiv=content-type content=text/html;charset=windows-1254>
<META http-equiv=content-type content=text/html;charset=x-mac-turkish>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<script src="${pageContext.request.contextPath}/resources/jquery-2.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/bootstrapDir/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/angular.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/bootstrapDir/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/bootstrapDir/css/bootstrap-theme.min.css">	

</head>
<body>

<div class="container">
	<ul class="nav nav-tabs">
		<li role="presentation"><a href="#">Kaydol</a></li>
		<li role="presentation"><a href="#">Giriş Yap</a></li>
	</ul>
	<div class="row col-md-4">
		<form role="form">
			<div class="form-group">
				<label for="email">Email address:</label>
				<input type="email" class="form-control" id="email">
			</div>
			<div class="form-group">
				<label for="pwd">Password:</label>
				<input type="password" class="form-control" id="pwd">
			</div>
			<div class="checkbox">
				<label><input type="checkbox"> Remember me</label>
			</div>
			<button type="submit" class="btn btn-default">Submit</button>
		</form>
	</div>
</div>
</body>
</html>