angular.module("app").controller("UploadController", function($scope,$http,$state,$window, upload) {
	
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
		}).success(function() {
			$state.go('default');
			console.log('success');
		}).error(function() {
			console.log('error');
		});
		}
	
	
//	$scope.doUpload = function () {
//	    upload({
//	      url: '/uploadArticle',
//	      method: 'POST',
//	      data: {
//	          title: $scope.title,
//	          file: $scope.myFile, // a jqLite type="file" element, upload() will extract all the files from the input and put them into the FormData object before sending.
//	        }
//	    }).then(
//	      function (response) {
//	        console.log(response.data); // will output whatever you choose to return from the server on a successful upload
//	      },
//	      function (response) {
//	          console.error(response); //  Will return if status code is above 200 and lower than 300, same as $http
//	      }
//	    );
//	  }
//	
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

