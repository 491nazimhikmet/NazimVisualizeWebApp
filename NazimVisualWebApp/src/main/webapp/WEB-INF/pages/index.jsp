<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
	<nav class="navbar navbar-default">
	  <div class="container-fluid">
	    <!-- Brand and toggle get grouped for better mobile display -->
	    <div class="navbar-header">
	      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
	        <span class="sr-only">Toggle navigation</span>
	        <span class="icon-bar"></span>
	        <span class="icon-bar"></span>
	        <span class="icon-bar"></span>
	      </button>
	      <a class="navbar-brand" href="#">
	      	Nazım
	      </a>
	    </div>

	    <!-- Collect the nav links, forms, and other content for toggling -->
	    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
	      
	      <ul class="nav navbar-nav navbar-right">
	      		<c:choose>
					<c:when test="${empty isLogged}">
						
					</c:when>
		            <c:otherwise>
			      		<c:choose>
							<c:when test="${isLogged == false}">
							<li>
				                <div class="alert alert-danger" role="alert">
									Kullanıcı adı ya da şifre hatalı
								</div>
				            </li>
				            </c:when>
			            </c:choose>
			        </c:otherwise>
       		 	</c:choose>
	        <li>
	        	<form:form commandName="user" method="POST" action="login" class="navbar-form navbar-left" role="search">
			        <div class="form-group">
			          <form:input type="text" path="userName" class="form-control" placeholder="Kullanıcı Adı" />
			          <form:input type="password" path="password" class="form-control" placeholder="Şifre" />
			        </div>
			        <button type="submit" class="btn btn-default">Giriş</button>
			    </form:form>
	        </li>
	      </ul>
	    </div><!-- /.navbar-collapse -->
	  </div><!-- /.container-fluid -->
	</nav>

	<div class="col-md-4"></div>
	<div class="row col-md-4">

		<c:choose>
			<c:when test="${empty isSuccess}">
				
			</c:when>
            <c:otherwise>
            	<c:choose>
					<c:when test="${isSuccess == false}">
		                <div class="alert alert-danger" role="alert">
							${addErrormessage}
						</div>
		            </c:when>
		            <c:otherwise>
		            	<div class="alert alert-success" role="alert">
							${addErrormessage}
						</div>
		            </c:otherwise>
	            </c:choose>
            </c:otherwise>
        </c:choose>

		
		<form:form method="POST" commandName="user" action="addMee" role="form">
			<div class="form-group">
				<form:label path="name" for="adi">Ad:</form:label>
				<form:input path="name" class="form-control" id="adi"/>
			</div>
			<div class="form-group">
				<form:label path="surname" for="soyadi">Soyad:</form:label>
				<form:input path="surname" class="form-control" id="soyadi"/>
			</div>
			<div class="form-group">
				<form:label path="email" for="email">Email address:</form:label>
				<form:input type="email" path="email" class="form-control" id="email"/>
			</div>
			<div class="form-group">
				<form:label path="userName" for="useradi">Kullanıcı Adı:</form:label>
				<form:input path="userName" class="form-control" id="useradi"/>
			</div>
			<div class="form-group">
				<form:label path="password" for="pwd">Parola:</form:label>
				<form:input type="password" path="password" class="form-control" id="pwd"/>
			</div>
			
			<button type="submit" class="btn btn-default">Kaydol</button>
		</form:form>
	</div>
</div>
</body>
</html>