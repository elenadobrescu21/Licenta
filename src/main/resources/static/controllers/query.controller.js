angular.module("app").controller("QueryController", function($scope, $http, $state) {
	
	
	$scope.articles = [];
	$scope.showResults = false;
	//$scope.hasResults = true;
	
	$scope.sendQuery = function () {
		console.log("selected value: " + $scope.radioInput);
		
		console.log("titlu: " +  $scope.title);
		console.log("author" + $scope.author);
		console.log("tag" + $scope.tag);
		console.log("an" + $scope.an);
		
		 $scope.ok = function() {
			  $state.reload();
			  $scope.showResults = false;
			    
		   };

		   $scope.cancel = function() {
			    //$state.reload();
			    $scope.showResults = false;
		   };
		
		var uploadUrl = "http://localhost:8080/query";
		var fd = new FormData();
		fd.append('article-type',angular.toJson($scope.radioInput, true));
		fd.append('title', angular.toJson($scope.title, true));
		fd.append('author', angular.toJson($scope.author,true));
		fd.append('tag', angular.toJson($scope.tag, true));
		fd.append('an', angular.toJson($scope.an, true));
		
		$http.post(uploadUrl, fd, {
			transformRequest : angular.identity,
			headers : {
			'Content-Type' : undefined
			}
			}).then(function successCallback(response) {
			    console.log(response.data);
			    $scope.showResults = true;
			    $scope.articles = response.data;
			    if(response.status == 200) {
			    	$scope.hasResults = true;
			    } else {
			    	$scope.hasResults = false;
			    }
			    console.log(response.status);
			  }, function errorCallback(response) {
			    // called asynchronously if an error occurs
			    // or server returns response with an error status.
			  });
	}
})