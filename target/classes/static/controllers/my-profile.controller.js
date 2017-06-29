angular.module("app").controller("MyProfileController", function($scope, $http, $state, $stateParams, User, Auth){
	
	$scope.user = [];
	$scope.showModalStergere = false;
	
	$scope.modalEditCarteCompleta = false;
	$scope.modalEditCarteCapitol = false;
	$scope.modalEditConferinta = false;
	$scope.modalEditJurnalRevista = false;

	
	User.getAuthenticatedUser().then(function(result){
		$scope.user = result.data;
		console.log("Userul din my profile controller");
		console.log($scope.user);
	})
	
	$scope.openModalStergere = function(article) {
		$scope.showModalStergere = true;
		$scope.articleToBeDeleted = article;
	}
	
	$scope.cancel = function () {
		$scope.showModalStergere = false;
	}
	
	$scope.ok = function() {
		deleteArticle($scope.articleToBeDeleted);
	}
	
	$scope.cancelEditCarteCapitol = function() {
		$scope.modalEditCarteCapitol = false;
	}
	
	$scope.cancelEditConferinta = function() {
		$scope.modalEditConferinta = false;
	}
	
	$scope.cancelJurnalRevista = function() {
		$scope.modalEditJurnalRevista = false;
	}
	
	$scope.editArticle = function(article) {

		var id = article.uploadedArticleId;
		$scope.selectedId = id;
		console.log("id tip articol" + article.tipArticol.id);
		if(article.tipArticol.id == 1 ) {
			$http.get('http://localhost:8080/carteCompleta/' + article.uploadedArticleId)
			.then(
				function successCallback(response){
					$scope.carteCompletaEdit = response.data;
					console.log($scope.carteCompletaEdit);
					$scope.modalEditCarteCompleta = true;
				},
				function errorCallback(response) {
					console.log('eroare');
				});
			
		}
		
		if(article.tipArticol.id == 2 ) {
			$http.get('http://localhost:8080/carteCapitol/' + article.uploadedArticleId)
			.then(
				function successCallback(response){
					$scope.carteCapitolEdit = response.data;
					console.log($scope.carteCapitolEdit);
					$scope.modalEditCarteCapitol = true;
					
				},
				
				function errorCallback(response) {
					console.log('eroare')
				});
		}
		
		if(article.tipArticol.id == 3) {
			$http.get('http://localhost:8080/conferinta/' + article.uploadedArticleId)
			.then(
				function successCallback(response){
					$scope.conferintaEdit = response.data;
					console.log($scope.conferintaEdit);	
					$scope.modalEditConferinta = true;
				},
				
				
				function errorCallback(response) {
					console.log('edit');
				});
		}
		
		if(article.tipArticol.id == 4 ) {
			$http.get('http://localhost:8080/jurnalRevista/' + article.uploadedArticleId)
			.then(
				function successCallback(response){
					$scope.jurnalRevistaEdit = response.data;
					console.log($scope.jurnalRevistaEdit);
					$scope.modalEditJurnalRevista = true;
				},
				
				function errorCallback(response) {
					console.log('eroare')
				});
		}
		
	}
	
	
	function  deleteArticle (article) {
		console.log("id-ul este:" + article.uploadedArticleId);
		var id = article.uploadedArticleId;
		console.log("id tip articol" + article.tipArticol.id);
		if(article.tipArticol.id == 1 ) {
			$http.delete('http://localhost:8080/carteCompleta/' + article.uploadedArticleId)
			.then(
				function successCallback(response){
					angular.element('[data-id=' + id + ']').remove();
					$http.delete('http://localhost:8080/article/' + id)
					console.log("S-a sters cu succes");
					
				},
				
				function errorCallback(response) {
					console.log('nu s-a putut sterge')
				});
		}
		
		if(article.tipArticol.id == 2 ) {
			$http.delete('http://localhost:8080/carteCapitol/' + article.uploadedArticleId)
			.then(
				function successCallback(response){
					angular.element('[data-id=' + id + ']').remove();
					$http.delete('http://localhost:8080/article/' + id)
					console.log("S-a sters cu succes");
					
				},
				
				function errorCallback(response) {
					console.log('nu s-a putut sterge')
				});
		}
		
		if(article.tipArticol.id == 3) {
			$http.delete('http://localhost:8080/conferinta/' + article.uploadedArticleId)
			.then(
				function successCallback(response){
					angular.element('[data-id=' + id + ']').remove();
					$http.delete('http://localhost:8080/article/' + id)
					console.log("S-a sters cu succes");
					
				},
				
				function errorCallback(response) {
					console.log('nu s-a putut sterge')
				});
		}
		
		if(article.tipArticol.id == 4 ) {
			$http.delete('http://localhost:8080/jurnalRevista/' + article.uploadedArticleId)
			.then(
				function successCallback(response){
					angular.element('[data-id=' + id + ']').remove();
					$http.delete('http://localhost:8080/article/' + id)
					console.log("S-a sters cu succes");		
				},
				
				function errorCallback(response) {
					console.log('nu s-a putut sterge')
				});
		}
		
		$scope.showModalStergere = false;
	
	  };
	  
	  $scope.cancelEditCarteCompleta = function(){
		  $scope.modalEditCarteCompleta = false;
	  }
	  
	  $scope.okCarteCompleta = function() {
		  var uploadUrl = "http://localhost:8080/editCarteCompleta/"+ $scope.selectedId;
		  var fd = new FormData();
	
	      fd.append('editura-carte-completa', angular.toJson($scope.carteCompletaEdit.editura, true));
		  fd.append('editie-carte-completa', angular.toJson($scope.carteCompletaEdit.editie, true));
		  fd.append('an-aparitie-carte-completa', angular.toJson($scope.carteCompletaEdit.anPublicare,true));
		  fd.append('issn-carte-completa', angular.toJson($scope.carteCompletaEdit.issn,true));
		  fd.append('isbn-carte-completa', angular.toJson($scope.carteCompletaEdit.isbn,true));
		  fd.append('wos', angular.toJson($scope.carteCompletaEdit.uploadedArticle.wos,true));
		  fd.append('doi', angular.toJson($scope.carteCompletaEdit.uploadedArticle.doi,true));
		  fd.append('abstract-section', angular.toJson($scope.carteCompletaEdit.uploadedArticle.abstractSection,true));
		  fd.append('title', angular.toJson($scope.carteCompletaEdit.uploadedArticle.title, true));
		  $http.post(uploadUrl, fd, {
				transformRequest : angular.identity,
				headers : {
				'Content-Type' : undefined
				}
				}).then (function successCallback(response){
					//226 = IM_USED
					console.log("editare cu succes");
					//console.log(response.data);
					$scope.modalEditCarteCompleta = false;
					$state.reload();
					
				},
				function errorCallback(response) {
					console.log('editarea nu a reusit')
				});
	  }
	  
	  $scope.okConferinta = function() {
		  var uploadUrl = "http://localhost:8080/editConferinta/"+ $scope.selectedId;
		  var fd = new FormData();

	      fd.append('nume-conferinta', angular.toJson($scope.conferintaEdit.nume, true));
		  fd.append('locatie-conferinta', angular.toJson($scope.conferintaEdit.locatie, true));
		  fd.append('data-conferinta', angular.toJson($scope.conferintaEdit.data,true));
	
		  fd.append('wos', angular.toJson($scope.conferintaEdit.uploadedArticle.wos,true));
		  fd.append('doi', angular.toJson($scope.conferintaEdit.uploadedArticle.doi,true));
		  fd.append('abstract-section', angular.toJson($scope.conferintaEdit.uploadedArticle.abstractSection,true));
		  fd.append('title', angular.toJson($scope.conferintaEdit.uploadedArticle.title, true));
		  $http.post(uploadUrl, fd, {
				transformRequest : angular.identity,
				headers : {
				'Content-Type' : undefined
				}
				}).then (function successCallback(response){
					//226 = IM_USED
					console.log("editare cu succes");
					//console.log(response.data);
					$scope.modalEditConferinta = false;
					$state.reload();
					
				},
				function errorCallback(response) {
					console.log('editarea nu a reusit')
				});
	  }
	  
	  $scope.okCarteCapitol = function() {
		  var uploadUrl = "http://localhost:8080/editCarteCapitol/"+ $scope.selectedId;
		  var fd = new FormData();
		 
	      fd.append('editura-carte-capitol', angular.toJson($scope.carteCapitolEdit.editura, true));
		  fd.append('editie-carte-capitol', angular.toJson($scope.carteCapitolEdit.editie, true));
		  fd.append('an-aparitie-carte-capitol', angular.toJson($scope.carteCapitolEdit.anPublicare,true));
		  fd.append('issn-carte-capitol', angular.toJson($scope.carteCapitolEdit.issn,true));
		  fd.append('isbn-carte-capitol', angular.toJson($scope.carteCapitolEdit.isbn,true));
		  fd.append('wos', angular.toJson($scope.carteCapitolEdit.uploadedArticle.wos,true));
		  fd.append('doi', angular.toJson($scope.carteCapitolEdit.uploadedArticle.doi,true));
		  fd.append('abstract-section', angular.toJson($scope.carteCapitolEdit.uploadedArticle.abstractSection,true));
		  fd.append('title', angular.toJson($scope.carteCapitolEdit.uploadedArticle.title, true));
		  fd.append('nume-capitol', angular.toJson($scope.carteCapitolEdit.numeCapitol, true));
		  fd.append('pagina-inceput', angular.toJson($scope.carteCapitolEdit.paginaInceput, true));
		  fd.append('pagina-sfarsit', angular.toJson($scope.carteCapitolEdit.paginaSfarsit, true));
		  fd.append('titlu-carte', angular.toJson($scope.carteCapitolEdit.titlu, true));
		  fd.append('autori-carte', angular.toJson($scope.carteCapitolEdit.autoriCarte, true));
		  fd.append('editori-carte', angular.toJson($scope.carteCapitolEdit.editoriCarte, true));
		  
		  $http.post(uploadUrl, fd, {
				transformRequest : angular.identity,
				headers : {
				'Content-Type' : undefined
				}
				}).then (function successCallback(response){
					//226 = IM_USED
					console.log("editare cu succes");
					//console.log(response.data);
					$scope.modalEditCarteCompleta = false;
					$state.reload();
					
				},
				function errorCallback(response) {
					console.log('editarea nu a reusit')
				});
	  }
	  
	  
	  $scope.okJurnalRevista = function() {
		  var uploadUrl = "http://localhost:8080/editJurnalRevista/"+ $scope.selectedId;
		  var fd = new FormData();
	
	      fd.append('titlu-jurnal-revista', angular.toJson($scope.jurnalRevistaEdit.titlu, true));
		  fd.append('numar-jurnal-revista', angular.toJson($scope.jurnalRevistaEdit.numar, true));
		  fd.append('data-aparitie', angular.toJson($scope.jurnalRevistaEdit.dataAparitie,true));
		  fd.append('volum-jurnal-revista', angular.toJson($scope.jurnalRevistaEdit.volum,true));
		  
		  fd.append('isbn-jurnal-revista', angular.toJson($scope.jurnalRevistaEdit.isbn,true));
		  fd.append('issn-jurnal-revista', angular.toJson($scope.jurnalRevistaEdit.issn,true));
		  
		  fd.append('wos', angular.toJson($scope.jurnalRevistaEdit.uploadedArticle.wos,true));
		  fd.append('doi', angular.toJson($scope.jurnalRevistaEdit.uploadedArticle.doi,true));
		  fd.append('abstract-section', angular.toJson($scope.carteCapitolEdit.uploadedArticle.abstractSection,true));
		  fd.append('title', angular.toJson($scope.carteCapitolEdit.uploadedArticle.title, true));
	
		  fd.append('pagina-inceput', angular.toJson($scope.jurnalRevistaEdit.paginaStart, true));
		  fd.append('pagina-sfarsit', angular.toJson($scope.jurnalRevistaEdit.paginaSfarsit, true));
		
		  
		  $http.post(uploadUrl, fd, {
				transformRequest : angular.identity,
				headers : {
				'Content-Type' : undefined
				}
				}).then (function successCallback(response){
					//226 = IM_USED
					console.log("editare cu succes");
					//console.log(response.data);
					$scope.modalEditCarteCompleta = false;
					$state.reload();
					
				},
				function errorCallback(response) {
					console.log('editarea nu a reusit')
				});
	  }
	
})