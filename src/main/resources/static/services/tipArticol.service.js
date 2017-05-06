angular.module("app").factory("TipArticol", function($http){
	
	var tipArticolFactory = {};
	
	tipArticolFactory.getAllTipArticole = function() {
		return $http.get('http://localhost:8080/tipArticol').then(function(result){
			return result.data;
		})
	}
	
	return tipArticolFactory;
	
})