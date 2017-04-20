angular.module("app").controller("UserController", function($scope, $stateParams, User){
	
	$scope.user = [];
	
	User.getUserDTOById($stateParams.id).then(function(result){
		$scope.user = result;
	})
	
})