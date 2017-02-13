angular.module("app").controller("RegisterController", function($scope, $http, $state){
	
	$http.defaults.headers.post["Content-Type"] = "application/json";
	$scope.fields = {
			  nume:'',
			  prenume: '',
			  username: '',
			  password: '',
			  email: '',
			  authorities:''
			};

  $scope.addMember= function() {
	    var newMember = {};
	    newMember.nume = $scope.fields.nume;
	    newMember.prenume = $scope.fields.prenume;
	    newMember.username = $scope.fields.username;
	    newMember.password = $scope.fields.password;
	    newMember.email = $scope.fields.email;
	    newMember.authorities = "user";
	    
	    console.log(newMember);
	    
	    $http.post("http://localhost:8080/user", newMember).success(function(data) {
	    	console.log(data);
			$state.go('default');
	    }).error(function(error) {
	        // error
	        console.log(JSON.stringify("User couldn't be created " + error));
	    });
	   
	    /*
	    $http.post('http://localhost:8080/user', newMember).then(
			function successCallback(response) {
			  console.log(response);
			  $state.go('default');
	    }, function errorCallback(response){
	      console.log("User counldn't be created");
	    }); */

	  }
	

});
