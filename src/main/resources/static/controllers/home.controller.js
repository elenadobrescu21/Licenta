angular.module("app").controller("HomeController",function($scope, $rootScope, $location, $window,
		$state, $q, $http, Auth, Article, User, ServicePDF){
	
	var vm = this;

	$scope.isLoggedIn = Auth.isLoggedIn();
	vm.badCredentials = false;
	$scope.latestArticles = [];
	$scope.articlesWithAuthors = [];
	$scope.users = [];
	$scope.articleFavourite = [];
		
	vm.isAuthenticated = Auth.isLoggedIn();
	console.log("Userul este autentificat: " +  Auth.isLoggedIn());

	Auth.getUser(function(result){
		vm.user = result;
	});
	
	Auth.isAdmin(function(result){
		vm.isAdmin = result;
	})
	
	Article.latestArticles().then(function(result){
		$scope.latestArticles = result;
		console.log(result);	
	})
		
	$rootScope.$on('$routeChangeStart', function(){
		vm.isAuthenticated = Auth.isLoggedIn();
		Auth.getUser(function(result){
			vm.user = result;
		});
	});
	
	vm.login = function () {
		vm.processing = true;
		vm.error = "";
		Auth.login(vm.username, vm.password, function(data){
			vm.processing = false;
			console.log("Vm processing:" + vm.processing);
			vm.user = Auth.getUser();
			Auth.getUser(function(result){
				vm.user = result;		
			});
			if(typeof data !== "undefined") {
				if(data.token) {
					$state.go('default');
					$window.location.reload();		
				} else {
					console.log("Bad credentials din HomeController");
					vm.badCredentials = true;
					vm.badCredentialsMessage = "Username sau parola incorecte";
				}	
			}
		})
				
	}
	
	vm.doLogout = function() {
		Auth.logout();
		$state.go('default');
		$window.location.reload();	
	}
	
	$scope.getArticleFilename = function(articleId) {
		for(i=0; i<$scope.latestArticles.length; i++) {
			if(articleId == $scope.latestArticles[i].id) {
				return $scope.latestArticles[i].filePath;
			}
		}
	}
	
	
	$scope.downloadArticle = function(articleId) {
		$scope.fileName = $scope.getArticleFilename(articleId);
		console.log("Filename: " + $scope.fileName );
        var a = document.createElement("a");
        document.body.appendChild(a);
        a.style = "display: none";
        ServicePDF.downloadPdf(articleId).then(function (result) {
            var file = new Blob([result.data], {type: 'application/pdf'});
            var fileURL = window.URL.createObjectURL(file);
            a.href = fileURL;
            a.download = $scope.fileName;
            a.click();
        });
		
	}
	
	$scope.addToFavourites = function(articleId) {
		Article.addArticleToFavourites(articleId).then(function succesCallback(response){
			console.log("Status:" , response.status);
			console.log("Article added to favourites");
		}, function errorCallback(result){
			console.log("Status: ", response.status);
			console.log("Avem eroare");
		})
	}
		
})
