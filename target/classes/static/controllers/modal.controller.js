angular.module("app").controller("ModalController", function($scope, $timeout, $dialog){
	
	$scope.open = function() {
	    $scope.showModal = true;
	  };

	  $scope.ok = function() {
	    $scope.showModal = false;
	  };

	  $scope.cancel = function() {
	    $scope.showModal = false;
	  };
	
})