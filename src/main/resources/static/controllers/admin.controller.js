angular.module("app").controller("AdminController", function($scope, $http, $state, User,Auth){
	
	
	 $scope.userStatistics = {};
	 $scope.showStatisticsModal = false;
	 
	 $scope.numeCurent = {};
	 $scope.prenumeCurent = {};
	  
	 // var vm = this;
	  $scope.isLoggedIn = Auth.isLoggedIn();
	  $scope.isAdmin = false;
	  $scope.showStatistics = false;
	  $scope.userStatistics = {};
	  
	  if($scope.isLoggedIn == true) {
		  Auth.isAdmin(function(result){
				$scope.isAdmin = result;
		})
	  }
	  
	  
	  User.all().then(function(result) {
	      console.log("Members", result.data);
	      $scope.members = result.data;
	  }, function(err) {
	      console.error(err);
	  });
	  
	  $scope.showStatistics = function(id,nume,prenume) {
		  $scope.showStatisticsModal = true;
		  $scope.numeCurent = nume;
		  $scope.prenumeCurent = prenume;
		  User.getUserStatistics(id).then(function(result){
			  console.log(result);
				$scope.userStatistics = result;
				console.log($scope.userStatistics);
			})
	  }
	  
	  $scope.ok = function() {
		 // $state.reload();
		  $scope.showStatisticsModal = false;
		    
	   };

	   $scope.cancel = function() {
		    //$state.reload();
		    $scope.showStatisticsModal = false;
	   };
	  
	  	
})