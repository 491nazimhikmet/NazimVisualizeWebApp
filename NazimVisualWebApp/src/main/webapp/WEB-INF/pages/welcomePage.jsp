<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html >
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
<script src="${pageContext.request.contextPath}/resources/VisualWebAppCTRL.js"></script>
<script src="${pageContext.request.contextPath}/resources/processing-1.4.8.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/angular.min.js"></script>			
<title>Giriş yapıldı</title>
<script type="text/javascript">
	var showGiris = false;
	var showKaydol = false;
</script>

</head>
<body>
	<div class="container">
	<%@ include file="header.jsp" %>
   

	<div class="row">
		<div class="col-md-4"></div>

		<div class="col-md-4">

			<div class="row" id="siginModal">
				<div class="row">
					<c:choose>
						<c:when test="${empty isLogged}">
							
						</c:when>
			            <c:otherwise>
				      		<c:choose>
								<c:when test="${isLogged == false}">
					                <div class="alert alert-danger" role="alert">
										Kullanıcı adı ya da şifre hatalı
									</div>
									<script type="text/javascript">
										showGiris = true;
									</script>
					            </c:when>
				            </c:choose>
				        </c:otherwise>
	       		 	</c:choose>
				</div>
				<form:form commandName="user2" method="POST" action="login" class="form" role="search">
			        <div class="form-group">
			          	<form:input type="text" path="userName" class="form-control" placeholder="Kullanıcı Adı" />
					</div>
					<div class="form-group">
			          	<form:input type="password" path="password" class="form-control" placeholder="Şifre" />
					 </div>
			        <button type="submit" class="btn btn-default">Giriş</button>
			    </form:form>
			</div>

			<div class="row" id="sigUpModal">
			
				<div class="row">
					<c:choose>
						<c:when test="${empty isSuccess}">
							
						</c:when>
			            <c:otherwise>
			            	<c:choose>
								<c:when test="${isSuccess == false}">
					                <div class="alert alert-danger" role="alert">
										${addErrormessage}
									</div>
									<script type="text/javascript">
										showKaydol = true;
									</script>
					            </c:when>
					            <c:otherwise>
					            	<div class="alert alert-success" role="alert">
										${addErrormessage}
									</div>
									<script type="text/javascript">
										showKaydol = true;
									</script>
					            </c:otherwise>
				            </c:choose>
			            </c:otherwise>
			        </c:choose>
				</div>
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
					<div class="checkbox">
				    	<label><form:checkbox path="isActivated" value="1"/>Admin olarak kaydolmak için başvur</label>
				  	</div>
					<button type="submit" class="btn btn-default">Kaydol</button>
				</form:form>
			</div>
		</div>
		<div class="col-md-4"></div>
	</div>
	<section style="height: 700px !important;">
		<div class="row" id="mainModal">
			
			<div class="row">
				<div class="col-md-6">
					<img src="${pageContext.request.contextPath}/resources/images/nazimIcon.jpg" class="img-rounded" alt="Cinque Terre" width="304" height="236">
					<canvas data-processing-sources="${pageContext.request.contextPath}/resources/sketch_160313a.pde"></canvas>
				</div>
				<div class="col-md-6">
					<button class="btn btn-success" onclick="getPng()">Karmaşık Şekilleri Getir</button>
					<div class="row">
						<img id="imgResult" src="#" width="304" height="236" lass="img-rounded">
					</div> 
				</div>
			</div>
			<div class="row" style="margin-top: 50px;">
				<button class="btn btn-success" onclick="getWordCloud()">Word Cloud Getir</button>
				<div class="row">
					<img id="imgCloud" src="#" width="304" height="236" lass="img-rounded">
				</div> 
			</div>
		</div>
	</section>
		<%@ include file="searchPoem.html" %>
		

	</div>

</body>
</html>