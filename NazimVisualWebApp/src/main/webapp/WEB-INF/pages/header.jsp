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
	<nav class="navbar navbar-default navbar-fixed-top">
	  <div class="container-fluid">
	    <!-- Brand and toggle get grouped for better mobile display -->
	    <div class="navbar-header">
	      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
	        <span class="sr-only">Toggle navigation</span>
	        <span class="icon-bar"></span>
	        <span class="icon-bar"></span>
	        <span class="icon-bar"></span>
	      </button>
	      <a class="navbar-brand" href="#anasayfa" ng-click="showSection(1)">
	      	Anasayfa
	      </a>
	    </div>

	    <!-- Collect the nav links, forms, and other content for toggling -->
	    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
		    <ul class="nav navbar-nav navbar-left">
		    	<li>
		    		<a class="navbar-brand" href="#girisim" >
				      	Örnek Görseller
				    </a>
		    	</li>
		    	<li>
		    		<a class="navbar-brand" href="#arama" ng-click="showSection(2)">
				      	Arama
				    </a>
		    	</li>
		    	<!--<li>
		    		<a class="navbar-brand" href="#arama" ng-show="showAramaSonuc" ng-click="showSection(3)">
				      	Arama Görselleri
				    </a>
		    	</li>-->
		    	<li>
		    		<a class="navbar-brand" href="#">
				      	Detaylı Arama
				    </a>
		    	</li>
		    </ul>

		    <% if(userName == null)
	      		{ 
	      	%>

		    <ul class="nav navbar-nav navbar-right">
		    	<div class="btn-group" role="group">
		    		<button type="button" class="btn btn-info navbar-btn" onclick="showSignIn()">Giriş Yap</button>
  					<button type="button" class="btn btn-default navbar-btn" onclick="showSignUp()">Kaydol</button>
  					<!--<button type="button" class="btn btn-info navbar-btn" ng-click="showSignInModal()">Giriş Yap</button>
  					<button type="button" class="btn btn-default navbar-btn" ng-click="showSignUpModal()">Kaydol</button>-->
		    	</div>
		    </ul>

		    <% } %>

	      	<% if(userName != null)
	      		{ 
	      	%>
	      	<ul class="nav navbar-nav navbar-right">
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
	      	</ul>
	      	<% } %>
	    </div><!-- /.navbar-collapse -->
	  </div><!-- /.container-fluid -->
	</nav>