angular.module("app").controller("UploadController", function($scope,$http,$state,$window, upload, User) {
	
	$scope.hasError = false;
	$scope.errorMessage = "";
	$scope.existaCoautori = false;
	$scope.numarCoautori = 0;
	
	$scope.allUsers = [];
	
	 User.all().then(function(result) {
	      console.log("Members", result.data);
	      $scope.allUsers = result.data;
	  }, function(err) {
	      console.error(err);
	  })
	 
	$scope.adaugaCoautori = function() {
		//$scope.existaCoautori = true;
		// var $div = $("<div ng-controller='MyCtrl'>new: {{content.label}}</div>");
		var $div = $("<p> Coautor: </p> <select> <option ng-repeat='user in allUsers' data-id='{{ user.id }}'>{{user.nume}}  {{user.prenume}} </option>  </select>")
        var target = angular.element(document.querySelector(' #coautori'));

		angular.element(target).injector().invoke(function($compile) {
		    var $scope = angular.element(target).scope();
		    target.append($compile($div)($scope));
		    //$scope.$apply();
		  });

	}
	
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

