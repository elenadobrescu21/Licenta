angular.module("app").controller("AdminController", function($scope, $http, $state, User,Auth){
	
	
	  var vm = this;
	  vm.isLoggedIn = Auth.isLoggedIn();
	  vm.isAdmin = false;
	  if(vm.isLoggedIn == true) {
		  Auth.isAdmin(function(result){
				vm.isAdmin = result;
		})
	  }
	  
	  
	  User.all().then(function(result) {
	      console.log("Members", result.data);
	      $scope.members = result.data;
	  }, function(err) {
	      console.error(err);
	  });
	  
	  
	
})