angular.module("app").controller("HomeController",function($scope, $rootScope, $location, $window,
		$state, Auth){
	
	var vm = this;

	$scope.isLoggedIn = Auth.isLoggedIn();
		
	vm.isAuthenticated = Auth.isLoggedIn();
	console.log("Userul este autentificat: " +  Auth.isLoggedIn());

	Auth.getUser(function(result){
		vm.user = result;
	});
	
	
	$rootScope.$on('$routeChangeStart', function(){
		vm.isAuthenticated = Auth.isLoggedIn();
		Auth.getUser(function(result){
			vm.user = result;
		});
	});
	
	vm.login = function () {
		vm.processing = true;
		vm.error = "";
		Auth.login(vm.username, vm.password)
		.success(function(data){
			vm.processing = false;
			console.log("Vm processing:" + vm.processing);
			vm.user = Auth.getUser();
			Auth.getUser(function(result){
				vm.user = result;
			});
			if(data.token) {
				$state.go('default');
				$window.location.reload();		
			} else {
				console.log("Bad credentials");
				vm.error = "Eroare";
			}					
		})
		.error(function(error){
				console.log("Bad credentials!!");
		
		})
	}
	
	vm.doLogout = function() {
		Auth.logout();
		$state.go('default');
		$state.reload();
	}
		
})
