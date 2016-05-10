app.controller('anaSayfaCTRL', function($scope) {
	$scope.showGirisSayfasi = true;
	$scope.showAramaSayfasi = false;

	$scope.showSection = function (secNum){
		if(secNum == 1){
			$scope.showGirisSayfasi = true;
			$scope.showAramaSayfasi = false;
			$scope.showAramaSonucSayfasi = false;
		}else if(secNum == 2){
			$scope.showGirisSayfasi = false;
			$scope.showAramaSayfasi = true;
			$scope.showAramaSonucSayfasi = false;
		}else if(secNum == 3){
			$scope.showGirisSayfasi = false;
			$scope.showAramaSayfasi = false;
			$scope.showAramaSonucSayfasi = true;
		}
	}

	$scope.showAramaSonuc = false;
	$scope.showProgressBar = false;

	$scope.showLoading = function(){
		$scope.showProgressBar = true;
	}

	$scope.showLoaded= function(){
		$scope.showProgressBar = false;
	}
});
