app.controller('anaSayfaCTRL', function($scope,$rootScope) {
	$scope.showGirisSayfasi = true;
	$scope.showAramaSayfasi = false;
	$scope.showDetayliAramaSayfasi = false;

	$scope.changeSearchText = function(){
		$rootScope.$broadcast('changeSearch',{searchText : $scope.searchText});
	}

	$scope.callSearch = function(){
		$scope.showSection(2);
		$rootScope.$broadcast('callSearch',{searchText : $scope.searchText});
		$scope.searchText = "";
	}

	$scope.showSection = function (secNum){
		showMainModal();
		$scope.showSignIn = false;
		if(secNum == 1){
			$scope.showGirisSayfasi = true;
			$scope.showAramaSayfasi = false;
			$scope.showAramaSonucSayfasi = false;
			$scope.showDetayliAramaSayfasi = false;
			$scope.showHakkimizdaSayfasi = false;
			$scope.showIletisimSayfasi = false;
		}else if(secNum == 2){
			$scope.showGirisSayfasi = false;
			$scope.showAramaSayfasi = true;
			$scope.showAramaSonucSayfasi = false;
			$scope.showDetayliAramaSayfasi = false;
			$scope.showHakkimizdaSayfasi = false;
			$scope.showIletisimSayfasi = false;
		}else if(secNum == 3){
			$scope.showGirisSayfasi = false;
			$scope.showAramaSayfasi = false;
			$scope.showAramaSonucSayfasi = true;
			$scope.showDetayliAramaSayfasi = false;
			$scope.showHakkimizdaSayfasi = false;
			$scope.showIletisimSayfasi = false;
		}else if(secNum == 4){
			$scope.showGirisSayfasi = false;
			$scope.showAramaSayfasi = false;
			$scope.showAramaSonucSayfasi = false;
			$scope.showDetayliAramaSayfasi = true;
			$scope.showHakkimizdaSayfasi = false;
			$scope.showIletisimSayfasi = false;
		}else if(secNum == 5){
			$scope.showGirisSayfasi = false;
			$scope.showAramaSayfasi = false;
			$scope.showAramaSonucSayfasi = false;
			$scope.showDetayliAramaSayfasi = false;
			$scope.showHakkimizdaSayfasi = true;
			$scope.showIletisimSayfasi = false;
		}else if(secNum == 6){
			$scope.showGirisSayfasi = false;
			$scope.showAramaSayfasi = false;
			$scope.showAramaSonucSayfasi = false;
			$scope.showDetayliAramaSayfasi = false;
			$scope.showHakkimizdaSayfasi = false;
			$scope.showIletisimSayfasi = true;
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
