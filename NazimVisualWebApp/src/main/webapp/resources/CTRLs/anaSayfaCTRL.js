app.controller('anaSayfaCTRL', function($scope) {
	$scope.showGirisSayfasi = true;
	$scope.showAramaSayfasi = false;
	$scope.showDetayliAramaSayfasi = false;

	$scope.showSection = function (secNum){
		showMainModal();
		$scope.showSignIn = false;
		if(secNum == 1){
			$scope.showGirisSayfasi = true;
			$scope.showAramaSayfasi = false;
			$scope.showAramaSonucSayfasi = false;
			$scope.showDetayliAramaSayfasi = false;
		}else if(secNum == 2){
			$scope.showGirisSayfasi = false;
			$scope.showAramaSayfasi = true;
			$scope.showAramaSonucSayfasi = false;
			$scope.showDetayliAramaSayfasi = false;
		}else if(secNum == 3){
			$scope.showGirisSayfasi = false;
			$scope.showAramaSayfasi = false;
			$scope.showAramaSonucSayfasi = true;
			$scope.showDetayliAramaSayfasi = false;
		}else if(secNum == 4){
			$scope.showGirisSayfasi = false;
			$scope.showAramaSayfasi = false;
			$scope.showAramaSonucSayfasi = false;
			$scope.showDetayliAramaSayfasi = true;
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


	$scope.showSignInModal = function(){
		$scope.showSignIn = true;
	}

	$scope.showSignUpModal = function(){
		$scope.showSignUp = true;
	}
});
