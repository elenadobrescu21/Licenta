angular.module("app").controller("HomeController",function($scope, $rootScope, $location, $window,
		$state, Auth){
	
	var vm = this;

	$scope.isLoggedIn = Auth.isLoggedIn();
	vm.badCredentials = false;
		
	vm.isAuthenticated = Auth.isLoggedIn();
	console.log("Userul este autentificat: " +  Auth.isLoggedIn());

	Auth.getUser(function(result){
		vm.user = result;
	});
	
	Auth.isAdmin(function(result){
		vm.isAdmin = result;
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
		
})
