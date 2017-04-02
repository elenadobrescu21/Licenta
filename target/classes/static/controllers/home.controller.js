angular.module("app").controller("HomeController",function($scope, $rootScope, $location, $window,
		$state, $q, $http, Auth, Article, User, ServicePDF){
	
	var vm = this;

	$scope.isLoggedIn = Auth.isLoggedIn();
	vm.badCredentials = false;
	$scope.latestArticles = [];
	$scope.articlesWithAuthors = [];
	$scope.users = [];
		
	vm.isAuthenticated = Auth.isLoggedIn();
	console.log("Userul este autentificat: " +  Auth.isLoggedIn());

	Auth.getUser(function(result){
		vm.user = result;
	});
	
	Auth.isAdmin(function(result){
		vm.isAdmin = result;
	})
	
	 $q.all([
	    $http.get('http://localhost:8080/latestArticles').then(function(response) {
	      $scope.latestArticles = response.data;
	      console.log('one')
	    }),
	    $http.get('http://localhost:8080/user/all').then(function(response) {
	      $scope.users = response.data;
	      console.log('two')
	    }),
    
	  ]).then(function() {
		console.log("Latest articles:")
		console.log($scope.latestArticles);
		console.log("All users");
		console.log($scope.users);
	    console.log('all')
	    for(i=0; i< $scope.latestArticles.length; i++) {
	    	for(j=0; j<$scope.users.length; j++) {
	    		for(k=0; k<$scope.users[j].uploadedArticles.length; k++) {
	    			if($scope.latestArticles[i].uploadedArticleId == 
	    				$scope.users[j].uploadedArticles[k].uploadedArticleId) {
	    				var obj = {
	    						id: $scope.latestArticles[i].uploadedArticleId,
	    						title: $scope.latestArticles[i].title,
	    						filename: $scope.latestArticles[i].filename,
	    						uploadedOn: $scope.latestArticles[i].uploadedOn,
	    						author: $scope.users[j].nume + " " + $scope.users[j].prenume
	    				}
	    			$scope.articlesWithAuthors.push(obj);
	    				
	    			}
	    		}
	    	}
	    }
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
			if(articleId == $scope.latestArticles[i].uploadedArticleId) {
				return $scope.latestArticles[i].filename;
			}
		}
	}
	
	
	$scope.downloadArticle = function(articleId) {
		var fileName = $scope.getArticleFilename(articleId);
		console.log("Filename: " + fileName );
        var a = document.createElement("a");
        document.body.appendChild(a);
        a.style = "display: none";
        ServicePDF.downloadPdf(articleId).then(function (result) {
            var file = new Blob([result.data], {type: 'application/pdf'});
            var fileURL = window.URL.createObjectURL(file);
            a.href = fileURL;
            a.download = fileName;
            a.click();
        });
		
	}
		
})
