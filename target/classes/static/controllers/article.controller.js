angular.module("app").controller("ArticleController", function($scope,$stateParams,$sce,$http, $state, Article, ServicePDF){
	
	$scope.article = [];
	$scope.articlePath = [];
	$scope.viewUrl = [];
	$scope.showModalFavourite = false;
	
	$scope.loadArticle = function() {
		Article.articleById($stateParams.id).then(function(result){
			$scope.article = result;
			console.log("Articolul");
			console.log(result);
			$scope.viewUrl="http://localhost:8080/file-storage/recents/"+result.filePath;
			$scope.articlePath = $sce.trustAsResourceUrl("../file-storage/recents/"+result.filePath);
			$scope.hasCoauthors = ($scope.article.coauthors.length > 0) ? true : false;
			$scope.hasTags = ($scope.article.tags.length > 0) ? true : false;
			console.log("Id-ul articolului" + $scope.article.id);
			$http.get('http://localhost:8080/user/checkIfArticleIsFavourited/'+ $scope.article.id)
			.then(
				 function successCallback(response) {
						$scope.articleIsFavourited = response.data.response;
						console.log("Articolul este favorit:");
						console.log($scope.articleIsFavourited);
						//console.log(response);
				  },
				 function errorCallback(response) {
				    	 console.log("eroare din functia care verifica articolul favorit");
				 });
		})
	}
	
	
	$scope.addToFavourites = function() {
		$http.post('http://localhost:8080/addToFavourites/' + $scope.article.id)
		.then(
			      function successCallback(response) {
					console.log ("Am adaugat la favorite");
			      },
			      function errorCallback(response) {
			    	 console.log("Nu s-a putut adauga");
			    });
		$scope.showModalFavourite = true;
	}
	
	$scope.ok = function () {
		$scope.showModalFavourite = false;
		$state.reload();
		
	}
	
	$scope.downloadArticle = function(articleId) {
		$scope.fileName = $scope.article.filePath;
		console.log("Filename: " + $scope.fileName );
        var a = document.createElement("a");
        document.body.appendChild(a);
        a.style = "display: none";
        ServicePDF.downloadPdf(articleId).then(function (result) {
            var file = new Blob([result.data], {type: 'application/pdf'});
            var fileURL = window.URL.createObjectURL(file);
            a.href = fileURL;
            a.download = $scope.fileName;
            a.click();
        });
        
        $scope.article.numberOfDownloads++;
		
	}
	
})