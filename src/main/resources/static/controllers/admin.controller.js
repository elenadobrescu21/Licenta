angular.module("app").controller("AdminController", function($scope, $http, $state, User){
	
	  User.all().then(function(result) {
	      console.log("Members", result.data);
	      $scope.members = result.data;
	  }, function(err) {
	      console.error(err);
	  });
	  
	
})