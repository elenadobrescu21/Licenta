angular.module("app").controller("SearchTagController", function($scope, Article, Tag){
	
	$scope.filteredArticles = [];
	$scope.allTags = {};
	
	Tag.getAllTags().then(function(result){
		$scope.allTags = result.data;
		console.log("taguri");
		console.log($scope.allTags);
	})
	
	$scope.filterArticles = function() {	
		event.preventDefault();
		if($scope.tag !=null && $scope.tag.length>2) {
			var gasitTag = 0;
			for(var i=0; i<$scope.allTags.length;i++) {
				if($scope.allTags[i].denumire == $scope.tag ){
					gasitTag++;
				}
			}
			if(gasitTag == 0) {
				$scope.hasResults = false;
			} else {
				event.preventDefault();
				Article.getArticlesByTag($scope.tag).then(function(result){
					$scope.filteredArticles = result;
				})
				$scope.hasResults = true;
				
			}
			
		}
		
	}
	
})