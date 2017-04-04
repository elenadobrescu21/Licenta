angular.module("app").controller("UploadController", function($scope,$http,$state,$window, upload, User) {
	
	$scope.hasError = false;
	$scope.errorMessage = "";
	$scope.existaCoautori = false;
	$scope.numberOfCoauthors = 0;
	$scope.models = [];
	$scope.users = [];
	$scope.coAuthorShow = false;
	$scope.coAuthors = [];
	$scope.coAuthor = {};
	$scope.coAuthorsWithoutAccount = [];
	$scope.finalizat = true;
	$scope.tags = [];
	
	$scope.allUsers = [];
	
	$scope.coAuthorWithouthAccount = false;
	
	 User.all().then(function(result) {
	      console.log("Members", result.data);
	      $scope.allUsers = result.data;
	      for(i=0; i<result.data.length; i++) {
	    	  console.log(result.data[i].id);
	    	  var name = result.data[i].nume + " " + result.data[i].prenume;
	    	  var obj = {id: result.data[i].id, fullname: name};
	    	  $scope.users.push(obj);
	      }
	  }, function(err) {
	      console.error(err);
	  })
	  
	  $scope.addCoauthor = function(){
	    $scope.coAuthors.push($scope.coAuthor);
	  };
	  
	  $scope.adaugaCoautori = function() {
		  $scope.coAuthorShow = !$scope.coAuthorShow;
		  $scope.finalizat = false;
	  }
	  
	  $scope.addCoauthorWithoutAccount = function(){
		$scope.coAuthorWithouthAccount = true;
	  }
	  
	  $scope.showCoauthors = function() {
		  console.log("Users:");
		  console.log($scope.users);
		  console.log("Coautori")
		  console.log($scope.coAuthors);
	  }
	  
	  $scope.saveCoauthorWithoutAccount = function(){
		 $scope.coAuthorsWithoutAccount.push($scope.coAuthorWithoutAccount);
		  console.log("A fost adaugat");
		  console.log($scope.coAuthorWithoutAccount);
	  }
	  
	  $scope.finalizare = function() {
		  $scope.finalizat = true;
	  }
	  
	  $scope.addTag = function() {
		  event.preventDefault();
		  if($scope.tag.length>2) {
		  $scope.tags.push($scope.tag);
		  $scope.tag = null;
		  }
	  }
	  
	  
	$scope.doUpload = function() {

		var file = $scope.myFile;
		console.log('file is ' );
		console.dir(file);
		var uploadUrl = "http://localhost:8080/upload";
		var fd = new FormData();
		fd.append('title',angular.toJson($scope.title,true));
		fd.append('file', file);
		fd.append('coauthors', angular.toJson($scope.coAuthors,true));
		fd.append('tags', angular.toJson($scope.tags,true));
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

