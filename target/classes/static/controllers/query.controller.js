angular.module("app").controller("QueryController", function($scope, $http) {
	
	
	$scope.sendQuery = function () {
		console.log("checkbox-jurnal" + $scope.checkbox.jurnal);
		console.log("checkbox-carte" + $scope.checkbox.carteCapitol);
		console.log("checkbox-carte completa" + $scope.checkbox.carteCompleta);
		console.log("checkbox-conferinta" + $scope.checkbox.conferinta);
		
		console.log("titlu: " +  $scope.title);
		console.log("author" + $scope.author);
		console.log("tag" + $scope.tag);
		console.log("an" + $scope.an);
		
		var uploadUrl = "http://localhost:8080/query";
		var fd = new FormData();
		fd.append('checkbox-jurnal',angular.toJson($scope.checkbox.jurnal,true));
		fd.append('checkbox-carte-capitol', angular.toJson($scope.checkbox.carteCapitol, true));
		fd.append('checkbox-carte-completa', angular.toJson($scope.checkbox.carteCompleta, true));
		fd.append('checkbox-conferinta', angular.toJson($scope.checkbox.conferinta, true));
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
			    console.log(response.status);
			  }, function errorCallback(response) {
			    // called asynchronously if an error occurs
			    // or server returns response with an error status.
			  });
	}
})