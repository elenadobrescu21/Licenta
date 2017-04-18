angular.module("app").controller("ArticleController", function($scope,$stateParams,$sce, Article){
	
	$scope.article = [];
	$scope.articlePath = [];
	$scope.loadArticle = function() {
		Article.articleById($stateParams.id).then(function(result){
			$scope.article = result;
			$scope.articlePath = $sce.trustAsResourceUrl("../file-storage/recents/"+result.filePath);
			$scope.hasCoauthors = ($scope.article.coauthors.length > 0) ? true : false;
			$scope.hasTags = ($scope.article.tags.length > 0) ? true : false;
		})
	}
	
	$scope.downloadArticle = function(articleId) {
		Article.downloadArticle(articleId);
	}
	
})