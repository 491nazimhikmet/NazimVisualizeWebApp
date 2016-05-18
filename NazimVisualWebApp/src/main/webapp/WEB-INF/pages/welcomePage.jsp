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
<script src="${pageContext.request.contextPath}/resources/CTRLs/app.js"></script>
<script src="${pageContext.request.contextPath}/resources/CTRLs/anaSayfaCTRL.js"></script>
<script src="${pageContext.request.contextPath}/resources/CTRLs/girisCTRL.js"></script>
<script src="${pageContext.request.contextPath}/resources/CTRLs/siirAraCtrl.js"></script>
<script src="${pageContext.request.contextPath}/resources/VisualWebAppCTRL.js"></script>
<script src="${pageContext.request.contextPath}/resources/processing-1.4.8.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/bootbox.min.js"></script>
<title>Giriş yapıldı</title>
<script type="text/javascript">
	var showGiris = false;
	var showKaydol = false;
</script>
<style type="text/css">
	.sections{
		margin-top: 50px;
	}
	.bottomArrow{
		position: fixed;
	    bottom: 0;
	    font-size: -webkit-xxx-large;
	    left: 50%;
	    z-index: 15;
	    display: none;
	}

	.topumArr{
		position: fixed;
	    font-size: -webkit-xxx-large;
	    z-index: 15;
	    left: 50%;
	    display: none;
	}
</style>

</head>
<body ng-app="myApp" ng-controller="anaSayfaCTRL">

	<div class="progress ng-hide" style="position:fixed; top:30%; left: 25%; width: 50%; position:center; " ng-show="showProgressBar">
	    <div class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%;">
	     	İşlem yapılıyor lütfen bekleyiniz
	    </div>
	</div>

	<div class="container" ng-show="!showProgressBar">
		<%@ include file="header.jsp" %>

		<div class="progress">
		  <div class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="45" aria-valuemin="0" aria-valuemax="100" style="width: 45%">
		    <span class="sr-only">45% Complete</span>
		  </div>
		</div>

		
		<div class="sections" style="margin-bottom: 50px;">

			<div class="topumArr">
				<span class="glyphicon glyphicon-circle-arrow-up" aria-hidden="true"></span>
			</div>
			<%@ include file="signinModal.html" %>
			<div ng-show="showGirisSayfasi">
				<%@ include file="girisSayfasi.html" %>
			</div>
			<div ng-controller="siirAraCtrl">
				<div ng-show="showAramaSayfasi" >
					<%@ include file="searchPoem.html" %>
				</div>
				<div ng-show="showAramaSonucSayfasi">
					<%@ include file="gorselSonuc.html" %>
				</div>
			</div>
			<div class="bottomArrow">
				<span class="glyphicon glyphicon-circle-arrow-down" aria-hidden="true"></span>
			</div>
			
		</div>		

		
	</div>

</body>
</html>