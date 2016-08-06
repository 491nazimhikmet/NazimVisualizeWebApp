<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page language="java" session="true" %>
<%
	//allow access only if session exists
	String userName = null;
	Integer userType = 0;
	if(session.getAttribute("userNameSurname") != null){
	    userName = (String) session.getAttribute("userNameSurname");
	    userType = (Integer) session.getAttribute("userType");
	}
%>

<style type="text/css">

	@media screen and (min-width: 767px) {
	    .navbar-right .navbar-brand{
			padding-right: 25px;
			padding-left: 25px;
		} 

		.navbar-right{
			margin-top: 20px;
		}

		.mainContainer{
			margin-top:50px;
		}

	}

	@media screen and (max-width: 767px) {

		.mainContainer{
			margin-top:55px;
		}
	}

	.navbar-nav>li>a:hover {
	    color: #333;
	    background-color: #00ADEF !important;
	}
		
</style>

	<nav class="navbar navbar-default navbar-fixed-top">
	  <div class="container-fluid">
	    <!-- Brand and toggle get grouped for better mobile display -->
	    <div class="navbar-header" style="height:100px;">
	      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
	        <span class="sr-only">Toggle navigation</span>
	        <span class="icon-bar"></span>
	        <span class="icon-bar"></span>
	        <span class="icon-bar"></span>
	      </button>
	      <a class="navbar-brand" href="anasayfa" ng-click="showSection(1)" style="padding:0">
	      	<img src="${pageContext.request.contextPath}/resources/images/logo_renkli_zeminsiz1000.png" class="img-rounded" height="100">
	      </a>
	    </div>

	    <!-- Collect the nav links, forms, and other content for toggling -->
	    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
		    
	    	<ul class="nav navbar-nav navbar-right">
		    	<li>
		    		<a class="navbar-brand" href="#anasayfa" ng-click="showSection(1)">
				      	Ana Sayfa
				    </a>
		    	</li>
		    	<li>
		    		<a class="navbar-brand" href="#hakkimizda" ng-click="showSection(5)">
				      	Proje Hakkında
				    </a>
		    	</li>
		    	
		    	<li>
		    		<a class="navbar-brand" href="#" ng-click="showSection(4)">
				      	Detaylı Arama
				    </a>
		    	</li>
		    	<li>
		    		<a class="navbar-brand" href="#" ng-click="showSection(6)">
				      	İletişim
				    </a>
		    	</li>
		    	<li>
		    		<a class="navbar-brand" href="#arama" ng-click="showSection(2)">
				      	<button type="button" class="btn btn-default btn-lg" aria-label="Left Align" style="padding:0px;background:none;border:none;">
							<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
						</button>
				    </a>
		    	</li>
		   

		    <% if(userName == null)
	      		{ 
	      	%>

		    	<div class="btn-group" role="group">
		    		<button type="button" class="btn btn-info navbar-btn" onclick="showSignIn()" style="background-color:#00ADEF !important;">Giriş Yap</button>
  					<button type="button" class="btn btn-default navbar-btn" onclick="showSignUp()">Kaydol</button>
		    	</div>

		    <% } %>

	      	<% if(userName != null)
	      		{ 
	      	%>
			 	<li class="dropdown">
			 		<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><%=userName %> <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<%
							if(userType == 2){
						%>
						<li><a href="adminOperations">Admin İşlemleri</a></li>
						<%
							}
						%>
						<li><a href="#">Hesap Ayarları</a></li>
						<li role="separator" class="divider"></li>
						<li><a href="logout">Çıkış Yap</a></li>
					</ul>
			 	</li>
	      	<% } %>
	      	 </ul>
	    </div><!-- /.navbar-collapse -->
	  </div><!-- /.container-fluid -->
	</nav>