<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
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
<link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Raleway" />
<script src="${pageContext.request.contextPath}/resources/CTRLs/app.js"></script>
<script src="${pageContext.request.contextPath}/resources/CTRLs/ecevitDenemeCTRL.js"></script>
<script src="${pageContext.request.contextPath}/resources/processing-1.4.8.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/bootbox.min.js"></script>
<!-- bu script font için script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.2/jspdf.debug.js"></script--> 
<title>Ecevit Yazıları Görselleştirme</title>
<style type="text/css">
	.scroll {
	   overflow-y: scroll; 
	   height:500px;
	   height: 100vh;
	}
	.scroll::-webkit-scrollbar {
	    width: 12px;
	}

	.scroll::-webkit-scrollbar-track {
	    -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3); 
	    border-radius: 10px;
	}

	.scroll::-webkit-scrollbar-thumb {
	    border-radius: 10px;
	    -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.5); 
	}
</style>


<body ng-app="myApp" ng-controller="ecevitDenemeCTRL"  style="font-family: Raleway;">
	<div class="mainContainer" style="margin-top: 30px;padding: 0;">
		
		<div class="col-sm-3" role="search">
			<form ng-submit="callSearch()">
				<div class="input-group">
					<input type="text" class="form-control" id="searchText" ng-model="searchTextM" ng-disabled="gorselSonucShow" placeholder="Kelime Ara" />
					<span class="input-group-btn">
						<button class="btn btn-default" type="button" ng-click="callSearch()" ng-disabled="gorselSonucShow">Ara</button>
					</span>
				</div>
			</form>
			<!-- sonuçların  listesi start -->
			<div ng-show="showResults" class="scroll">
					<table class="table table-condensed" style="font-size:small;">
					<thead>
						<tr>
							<th></th>
							<th style="font-size:initial;">Aranan Kelimenin Geçtiği Ecevit Yazıları</th>
							<!--<th></th>-->
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="sonuc in aramaSonucList">
							<td>

								<label>
									 <input type="radio" ng-click="setSelectedWork(sonuc)" name="optRadio">
								</label>
							</td>
							<td>{{sonuc.header}}</td>

						</tr>
					</tbody>
				</table>	
			</div>
			<!-- sonuçların  listesi end -->
		</div>

		<div class="col-sm-9" role="search">
			<div class="row">
				<button class="btn btn-info" type="button" ng-click="makeOperation(1)">Affect Analyzer</button>
				<button class="btn btn-info" type="button" ng-click="makeOperation(2)">Seçili Çalışmayı Oku</button>
			</div>

			<div class="row" ng-show="gorselSonucShow" style="text-align:center;">
				<div class="row">
					<h5><span class="label label-info">Seçili Çalışma:</span>  {{shownWorkName}}</h5>
				</div>
				<div class="row"> 
					<canvas id="canvasGorselSonuc" width="1170" height="657" ></canvas>
				</div>
			</div>

		</div>

			
	</div>

</body>
</html>
