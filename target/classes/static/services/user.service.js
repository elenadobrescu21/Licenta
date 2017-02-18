angular.module("app").factory('User', function($http) {
	
	var userFactory = {};
	
	userFactory.all = function(){
		return $http.get('http://localhost:8080/user/all');
	}
	
	return userFactory;
	
})