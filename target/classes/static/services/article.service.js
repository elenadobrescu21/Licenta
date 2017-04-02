angular.module("app").factory('Article', function($http) {
	
	var articleFactory = {};
	
	articleFactory.latestArticles = function(){
		return $http.get('http://localhost:8080/latestArticles');
	}
	
	articleFactory.getArticlesByAuthor = function(authorId, response) {
		$http.post('http://localhost:8080/articleByAuthor/'+authorId)
		.success(function(data, status, headers, config){
			response = data;
		})	
		.error(function(error){
				console.log("Bad credentials din AuthFactory");
				errorMessage = "Bad Credentials";
				callback(errorMessage);
		})
	}
	
	return articleFactory;
	
})