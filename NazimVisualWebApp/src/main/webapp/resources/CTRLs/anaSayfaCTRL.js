app.controller('anaSayfaCTRL', function($scope) {
	$scope.showGirisSayfasi = true;
	$scope.showAramaSayfasi = false;

	$scope.showSection = function (secNum){
		if(secNum == 1){
			$scope.showGirisSayfasi = true;
			$scope.showAramaSayfasi = false;
		}else if(secNum == 2){
			$scope.showGirisSayfasi = false;
			$scope.showAramaSayfasi = true;
		}
	}

});
