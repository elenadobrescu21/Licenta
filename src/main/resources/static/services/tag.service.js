angular.module("app").factory("Tag", function($http){
	
	var tagFactory = {};
	
	tagFactory.getAllTags = function() {
		return $http.get('http://localhost:8080/tag').then(function(result){
			return result;
		})
	}
	
	return tagFactory;
	
})