angular.module("app").controller("UploadController", function($scope,$http,$state,$window, upload) {
	
	$scope.hasError = false;
	$scope.errorMessage = "";
	$scope.doUpload = function() {

		var file = $scope.myFile;
		console.log('file is ' );
		console.dir(file);
		var uploadUrl = "http://localhost:8080/uploadArticle";
		var fd = new FormData();
		fd.append('title',angular.toJson($scope.title,true));
		fd.append('file', file);	
		console.log('Title '+ $scope.title);
		$http.post(uploadUrl, fd, {
		transformRequest : angular.identity,
		headers : {
		'Content-Type' : undefined
		}
		}).success(function(data, status, headers, config) {
			//226 = IM_USED
			if(status == 226) {
				$scope.hasError = true;
				$scope.errorMessage = data.message;
				
			}
			if(status == 200) {
				$state.go('default');
				console.log('Data message din success: '+ data.message);
				console.log('Status din success: ' + status);
				console.log('success');			
			}
			
		}).error(function() {
			console.log('error');
			console.log('Data message din error: '+ data.message);
			console.log('Status din error:'+ status);
		});
		}

})

angular.module("app").directive('fileModel', ['$parse', function ($parse) {
	    return {
	        restrict: 'A',
	        link: function(scope, element, attrs) {
	            var model = $parse(attrs.fileModel);
	            var modelSetter = model.assign;
	            
	            element.bind('change', function(){
	                scope.$apply(function(){
	                    modelSetter(scope, element[0].files[0]);
	                });
	            });
	        }
	    };
	}]);

