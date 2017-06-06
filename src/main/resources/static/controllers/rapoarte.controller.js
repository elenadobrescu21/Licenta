angular.module("app").controller("RapoarteController", function($scope, $http, User, TipArticol){
	
	$scope.userWithMostArticles = [];
	$scope.userWithMostDownloadedArticles = [];
	$scope.topUserByArticleType = [];
	$scope.topUserByYear = [];
	
	$scope.topUsersByNumberOfArticles = {}
	$scope.topUsersByNumberOfDownloads = {};
	
	$scope.usersByArticleType = {};
	
	$scope.showTableWithResultsArticles = false;
	$scope.showTableWithResultsDownloads = false;
	
	$scope.showTableWithUsersByArticleType = false;
	

	User.getUserWithMostArticles().then(function(result){
		$scope.userWithMostArticles = result.data;
		console.log("Result");
		console.log($scope.userWithMostArticles);
	})
	
	User.getUserWithMostDownloadedArticles().then(function(result){
		$scope.userWithMostDownloadedArticles = result.data;
	})
	
	TipArticol.getAllTipArticole().then(function(result){
		$scope.tipArticole = result;
	})
	
	  $scope.getSelectedArticleType = function() {
		var id = 0;
		   switch ($scope.articleType.denumire) {
		    case "Carte-versiune completa":
		        id = 1;
		        break;
		    case "Conferinta":
		        id = 3
		        break;
		    case "Jurnal/Revista":
		        id = 4;
		        break;
		    case "Carte-capitol":
		        id = 2;
		        break;
		}
		   if(id!=0) {
			   User.getTopUserByArticleType(id).then(function(result){
				   $scope.topUserByArticleType = result.data;
			   })
		   }
	   }
	
		$scope.getTopUserByYear = function() {
			if($scope.an != null) {
				User.getTopUserByYear($scope.an).then(function(result){
					$scope.topUserByYear = result.data;
				})
			}
		
		}
		
		$scope.sortUsersBy = function() {
			$scope.showTableWithResultsArticles = false;
			$scope.showTableWithResultsDownloads = false;
			if($scope.data.singleSelect == 1) {
				$scope.showTableWithResultsArticles = true;
				User.getUsersOrderedByNumberOfArticles().then(function(result){
					$scope.topUsersByNumberOfArticles = result.data;
				})
			}
			
			if($scope.data.singleSelect == 2) {
				$scope.showTableWithResultsDownloads = true;
				User.getUsersOrderedByNumberOfDownloads().then(function(result){
					$scope.topUsersByNumberOfDownloads = result.data;
				})
				
			}
			console.log($scope.data.singleSelect);
		}
		
		$scope.sortUsersByArticleType = function() {
			console.log("tip articol " + $scope.selectArticleType );
			
			if($scope.selectArticleType == 1) {
				User.getUsersByTipArticol(1).then(function(result){
					$scope.usersByArticleType = result.data;
				})
				
			}
			
			if($scope.selectArticleType == 2) {
				User.getUsersByTipArticol(2).then(function(result){
					$scope.usersByArticleType = result.data;
				})
			}
			
			if($scope.selectArticleType == 3) {
				User.getUsersByTipArticol(3).then(function(result){
					$scope.usersByArticleType = result.data;
				})
			}
			
			if($scope.selectArticleType == 4) {
				User.getUsersByTipArticol(3).then(function(result){
					$scope.usersByArticleType = result.data;
				})
			}
			
			if($scope.selectArticleType == 1 || $scope.selectArticleType == 2 
					|| $scope.selectArticleType == 3 || $scope.selectArticleType == 4) {
				console.log("ajunge aici");
				
				
				$scope.showTableWithUsersByArticleType = true;
				
			}
			
		}
	
	
	
	
	
	
})