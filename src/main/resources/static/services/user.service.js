angular.module("app").factory('User', function($http) {
	
	var userFactory = {};
	
	userFactory.all = function(){
		return $http.get('http://localhost:8080/user/all');
	}
	
	userFactory.getAllUsersDTO = function() {
		return $http.get('http://localhost:8080/appUserDTO/all').then(function(result){
			return result.data;
		})
	}
	
	userFactory.getAuthorByArticleId = function(id,title, filename, uploadedOn) {
		return $http.get('http://localhost:8080/user/article/'+id);
	}
	
	return userFactory;
	
})