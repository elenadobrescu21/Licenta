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
	
	userFactory.getUserDTOById = function(userId) {
		return $http.get('http://localhost:8080/userDTOExtended/'+ userId).then(function(result){
			return result.data;
		})
	}
	
	userFactory.getAuthorByArticleId = function(id,title, filename, uploadedOn) {
		return $http.get('http://localhost:8080/user/article/'+id);
	}
	
	userFactory.getUserWithMostArticles = function() {
		return $http.get('http://localhost:8080/appUserDTO/first');
	}
	
	userFactory.getUserWithMostDownloadedArticles = function() {
		return $http.get('http://localhost:8080/appUserDTO/topDownloadsFirst');
	}
	
	userFactory.getTopUserByArticleType = function(id) {
		return $http.get('http://localhost:8080/user/topUserByArticleType/' + id);
	}
	
	userFactory.getTopUserByYear = function(year) {
		return $http.get('http://localhost:8080/user/topUserByYear/' + year);
	}
	
	userFactory.getUsersOrderedByNumberOfArticles = function() {
		return $http.get('http://localhost:8080/appUserDTO/top');
	}
	
	userFactory.getUsersOrderedByNumberOfDownloads = function() {
		return $http.get('http://localhost:8080/appUserDTO/topDownloads');
	}
	
	userFactory.getUsersByTipArticol = function(id) {
		return $http.get('http://localhost:8080/user/allByTipArticol/'+id);
	}
		
	return userFactory;
	
})