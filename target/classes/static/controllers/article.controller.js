angular.module("app").controller("ArticleController", function($scope,$stateParams,$sce, Article, ServicePDF){
	
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