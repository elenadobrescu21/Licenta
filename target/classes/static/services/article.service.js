angular.module("app").factory('Article', function($http, ServicePDF) {
	
	var articleFactory = {};
	
	
	articleFactory.articleById = function(articleId) {
		return $http.get('http://localhost:8080/articleDTO/'+articleId).then(function(result){
			return result.data;
		})
	}
	
	articleFactory.latestArticles = function(){
		return $http.get('http://localhost:8080/articleDTO/latest').then(function(result){
			return result.data;
		});
	}
	
	articleFactory.checkIfArticleIsFavourited = function(article){
		return $http.get('http://localhost:8080/user/checkIfArticleIsFavourited/'+article.id).then(function(result){
			return result.data;
		})
	}
	
	function getArticleFilename(articleId) {
		return $http.get('http://localhost:8080/articleDTO/'+articleId).then(function(result){
			return result.data.filePath;
			console.log("Se apeleaza functia getArticleFilename: ", result.data.filename);
		})
	}
	
	articleFactory.downloadArticle = function(articleId) {
		var fileName = [];
		getArticleFilename(articleId).then(function(result,fileName){
			fileName = result;
		});
		console.log("Filename: ", fileName);
        var a = document.createElement("a");
        document.body.appendChild(a);
        a.style = "display: none";
        ServicePDF.downloadPdf(articleId).then(function (result) {
            var file = new Blob([result.data], {type: 'application/pdf'});
            var fileURL = window.URL.createObjectURL(file);
            a.href = fileURL;
            a.download = fileName;
            a.click();
        });
	}
	
	
	articleFactory.allArticles = function() {
		return $http.get('http://localhost:8080/articleDTO/all').then(function(result){
			return result.data;
		})
	}
	
	articleFactory.addArticleToFavourites = function(articleId) {
		return $http.post('http://localhost:8080/addToFavourites/'+articleId);
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