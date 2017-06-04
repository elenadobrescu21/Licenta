angular.module("app").controller("RegisterController", function($scope, $http, $state){
	
	//$http.defaults.headers.post["Content-Type"] = "application/json";
	$scope.fields = {
			  nume:'',
			  prenume: '',
			  username: '',
			  password: '',
			  email: '',
			  authorities:'',
			  pozitie:''
			};
	$scope.hasError = false;
	$scope.errorMessage = "";

  $scope.addMember= function() {
	    var newMember = {};
	    newMember.nume = $scope.fields.nume;
	    newMember.prenume = $scope.fields.prenume;
	    newMember.username = $scope.fields.username;
	    newMember.password = $scope.fields.password;
	    newMember.email = $scope.fields.email;
	    newMember.authorities = "ROLE_USER";
	    newMember.pozitie = $scope.fields.pozitie;
	    
	    console.log(newMember);
	    
	    $http.post("http://localhost:8080/user", newMember).success(function(data, status, headers, config) {
	    	console.log(status);
	    	//226 = IM_USED
	    	if(status==226) {
	    		$scope.hasError = true;
	    		$scope.errorMessage = data.message;
	    		console.log("Avem eroare: " + $scope.hasError);
	    		console.log($scope.errorMessage);  		
	    	}
	    	if(status==200) {
	    		$state.go('default');
    			console.log("Userul a fost creat");
	    	}	   
	    				
	    }).error(function(error) {
	       // $scope.hasError = true;
	        console.log("Avem eroare: " + $scope.hasError);
	        console.log(JSON.stringify("User couldn't be created " + error));
	    });
	   
	  }
	
});
