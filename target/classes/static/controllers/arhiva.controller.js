angular.module("app").controller("ArhivaController", function($scope, Article){
	
	$scope.allArticles = [];
	$scope.filteredArticles = [];
	
	Article.allArticlesDTO().then(function(result){
		$scope.allArticles = result;
		console.log("Toate articolele");
		console.log($scope.allArticles);
		
	})
	
	$scope.getArticlesThatStartWithLetter = function(letter) {
		for(i=0; i<$scope.allArticles.length; i++) {
			if($scope.allArticles[i].titlu.charAt(0) == letter) {
				$scope.filteredArticles.push($scope.allArticles[i]);
			}
		}
		console.log("Articolele filtrate");
		console.log($scope.filteredArticles);
	}
	
})