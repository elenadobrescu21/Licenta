angular.module("app").controller("UploadController", function($scope,$http,$state,$window, $filter,
		upload, User, Auth, TipArticol) {
	
	$scope.hasError = false;
	$scope.errorMessage = "";
	$scope.existaCoautori = false;
	$scope.numberOfCoauthors = 0;
	$scope.models = [];
	$scope.users = [];
	$scope.coAuthorShow = false;
	$scope.coAuthors = [];
	$scope.coAuthor = {};
	$scope.coAuthorsWithoutAccount = [];
	$scope.coAuthorWithoutAccountShow = false;
	$scope.moreCoauthorsWithoutAccount = false;
	$scope.finalizat = true;
	$scope.tags = [];
	$scope.abstract = [];

	$scope.allUsers = [];
	
	$scope.tipArticole = [];
	
	$scope.showModalSuccess = false;
	$scope.showModalError = false;
	
	
	$scope.showModalCarteTip1 = false;
	$scope.showModalCarteTip2 = false;
	$scope.showModalConferinta = false;
	$scope.showModalRevista = false;
	
	$scope.esteConferinta = false;
	$scope.esteCarteTip1 = false;
	$scope.esteCarteTip2 = false;
	$scope.esteRevista = false;
	
	$scope.autoriCarteCapitol = [];
	$scope.editoriCarteCapitol = [];
	
	TipArticol.getAllTipArticole().then(function(result){
		$scope.tipArticole = result;
	})
	
		
	Auth.getUser(function(result){
		$scope.user = result;
		console.log("Currently logged in user");
		console.log($scope.user);
	});
	
	 User.getAllUsersDTO().then(function(result) {
		  console.log(result);
	      $scope.users = result;	    
	    for(i=0; i<$scope.users.length; i++) {
	    	if($scope.users[i].username == $scope.user.username) {
	    		var index = i;
	    		break;
	    	}
	    }
	    console.log("index: ", index);
	    $scope.users.splice(index,1);
	  }, function(err) {
	      console.error(err);
	  })
	  
	  $scope.addCoauthor = function(){
	    $scope.coAuthors.push($scope.coAuthor);
	  };
	  
	  $scope.adaugaCoautori = function() {
		  $scope.coAuthorShow = !$scope.coAuthorShow;
		  $scope.finalizat = false;
	  }
	  
	  $scope.addCoauthorWithoutAccount = function(){
		$scope.coAuthorWithouthAccountShow = true;
		$scope.moreCoauthorsWithoutAccount = true;
	  }
	  
	  $scope.showCoauthors = function() {
		  console.log("Users:");
		  console.log($scope.users);
		  console.log("Coautori")
		  console.log($scope.coAuthors);
	  }
	  
	  $scope.saveCoauthorWithoutAccount = function(){
		 $scope.coAuthorsWithoutAccount.push($scope.coAuthorWithoutAccount);
		 $scope.coAuthorWithoutAccount = null;
		 $scope.moreCoauthorsWithoutAccount = false;
		 console.log("A fost adaugat");
		 console.log($scope.coAuthorWithoutAccount);
	  }
	  
	  $scope.saveAutorCarteCapitol = function() {
		  $scope.autoriCarteCapitol.push($scope.autorCarteCapitol);
		  $scope.autorCarteCapitol = null;
	  }
	  
	  $scope.saveEditorCarteCapitol = function() {
		  $scope.editoriCarteCapitol.push($scope.editorCarteCapitol);
		  $scope.editorCarteCapitol = null;
	  }
	  
	  $scope.finalizare = function() {
		  $scope.finalizat = true;
	  }
	  
	  $scope.addTag = function() {
		  event.preventDefault();
		  if($scope.tag.length>2) {
			  $scope.tags.push($scope.tag);
			  $scope.tag = null;
		  }
	  }
	   	  
	  $scope.ok = function() {
		  $state.reload();
		  $scope.showModal = false;
		    
	   };

	   $scope.cancel = function() {
		    //$state.reload();
		    $scope.showModal = false;
	   };
	   
	   $scope.openModalCarteTip1 = function() {
		   $scope.showModalCarteTip1 = true;
		   
	   }
	   
	   $scope.openModalCarteTip2 = function() {
		   $scope.showModalCarteTip2 = true;
	   }
	   
	   $scope.openModalConferinta = function() {
		   $scope.showModalConferinta = true;
	   }
	   
	   $scope.openModalRevista = function () {
		   $scope.showModalRevista = true;
	   }
	   
	   $scope.getSelectedArticleType = function() {
		   switch ($scope.articleType.denumire) {
		    case "Carte-versiune completa":
		        $scope.showModalCarteTip1 = true;
		        break;
		    case "Conferinta":
		        $scope.showModalConferinta = true;
		        break;
		    case "Jurnal/Revista":
		        $scope.showModalRevista = true;
		        break;
		    case "Carte-capitol":
		        $scope.showModalCarteTip2 = true;
		        break;
		}
	   }
	   
	   $scope.okConferinta = function() {
		   $scope.dataConferintaAfisata = new Date($scope.dataConferinta);
		   $scope.dataConferintaAfisata = $filter('date')($scope.dataConferintaAfisata,'yyyy-MM-dd'); 
		   console.log("Data", $scope.dataConferinta);
		   $scope.esteConferinta = true;
		   $scope.showModalConferinta = false;
	   }
	   
	   $scope.cancelConferinta = function() {
		   $scope.showModalConferinta = false;
	   }
	   
	   $scope.okCapitolCarte = function() {
		   $scope.esteCarteTip2 = true;
		   $scope.showModalCarteTip2 = false;
	   }
	   
	   $scope.cancelCapitolCarte = function() {
		   $scope.showModalCarteTip2 = false;
	   }
	   
	   $scope.okCarteCompleta = function() {
		   $scope.esteCarteTip1 = true;
		   $scope.showModalCarteTip1 = false;
	   }
	   
	   $scope.cancelCarteCompleta = function() {
		   $scope.showModalCarteTip1 = false;
	   }
	   
	   $scope.okRevista = function() {
		   $scope.dataAparitieRevista = new Date($scope.dataAparitieRevista);
		   $scope.dataAparitieRevista = $filter('date')($scope.dataAparitieRevista,'yyyy-MM-dd'); 
		   $scope.esteRevista = true;   
		   $scope.showModalRevista = false;
	   }
	   
	   $scope.cancelRevista = function() {
		   $scope.showModalRevista = false;
	   }
	  
	  
	$scope.doUpload = function() {

		var file = $scope.myFile;
		console.log('file is ' );
		console.dir(file);
		var uploadUrl = "http://localhost:8080/upload";
		var fd = new FormData();
		fd.append('title',angular.toJson($scope.title,true));
		fd.append('file', file);
		fd.append('coauthors', angular.toJson($scope.coAuthors,true));
		fd.append('tags', angular.toJson($scope.tags,true));
		fd.append('abstract', angular.toJson($scope.abstract,true));
		fd.append('coauthors-without-account', angular.toJson($scope.coAuthorsWithoutAccount,true));
		fd.append('doi', angular.toJson($scope.doi,true));
		fd.append('wos', angular.toJson($scope.wos,true));
		fd.append('article-type', angular.toJson($scope.articleType,true));
		 switch ($scope.articleType.denumire) {
		    case "Carte-versiune completa":
		        fd.append('editura-carte-completa', angular.toJson($scope.edituraCarteCompleta,true));
		        fd.append('editie-carte-completa', angular.toJson($scope.editieCarteCompleta, true));
		        fd.append('an-aparitie-carte-completa', angular.toJson($scope.anAparitieCarteCompleta,true));
		        fd.append('issn-carte-completa', angular.toJson($scope.ISSNCarteCompleta, true));
		        fd.append('isbn-carte-completa', angular.toJson($scope.ISBNCarteCompleta, true));        
		        break;
		    case "Conferinta":
		    	fd.append('nume-conferinta', angular.toJson($scope.numeConferinta, true));
		    	fd.append('locatie-conferinta', angular.toJson($scope.locatieConferinta,true));
		    	fd.append('data-conferinta', angular.toJson($scope.dataConferintaAfisata, true));       
		        break;
		    case "Jurnal/Revista":
		        fd.append('nume-revista', angular.toJson($scope.numeRevista, true));
		        fd.append('numar-revista', angular.toJson($scope.numarRevista, true));
		        fd.append('volum-revista', angular.toJson($scope.volumRevista, true));
		        fd.append('data-aparitie-revista', angular.toJson($scope.dataAparitieRevista, true));
		        fd.append('issn-revista', angular.toJson($scope.ISSNRevista, true));
		        fd.append('isbn-revista', angular.toJson($scope.ISBNRevista, true));
		        fd.append('pagina-inceput-revista',angular.toJson($scope.paginaInceputRevista, true));
		        fd.append('pagina-sfarsit-revista', angular.toJson($scope.paginaSfarsitRevista, true));
		        break;
		    case "Carte-capitol":
		        fd.append('titlu-carte-capitol', angular.toJson($scope.titluCapitolCarte, true));
		        fd.append('nume-capitol-carte', angular.toJson($scope.numeCapitolCarte, true));
		        fd.append('autori-capitol-carte', angular.toJson($scope.autoriCarteCapitol, true));
		        fd.append('editori-capitol-carte', angular.toJson($scope.editoriCarteCapitol, true));
		        fd.append('an-aparitie-capitol-carte', angular.toJson($scope.anAparitieCapitolCarte,true));
		        fd.append('issn-capitol-carte', angular.toJson($scope.ISSNCapitolCarte, true));
		        fd.append('isbn-capitol-carte', angular.toJson($scope.ISBNCapitolCarte,true));
		        fd.append('editura-capitol-carte', angular.toJson($scope.edituraCarteCapitol, true));
		        fd.append('editie-carte-capitol', angular.toJson($scope.editieCarteCapitol,true));
		        fd.append('pagina-inceput-carte-capitol', angular.toJson($scope.paginaInceputCapitolCarte,true));
		        fd.append('pagina-sfarsit-carte-capitol', angular.toJson($scope.paginaSfarsitCapitolCarte,true));
		        break;
		}
		console.log('Title '+ $scope.title);
		$http.post(uploadUrl, fd, {
		transformRequest : angular.identity,
		headers : {
		'Content-Type' : undefined
		}
		}).success(function(data, status, headers, config) {
			//226 = IM_USED
			if(status == 226) {
				$scope.showModalError = true;
				$scope.errorMessage = data.message;
				
			}
			
			if(status == 406) {
				$scope.showModalError = true;
			}
			
			if(status == 200) {
				//$state.go('default');
				$scope.showModalSuccess = true;
				console.log('Data message din success: '+ data.message);
				console.log('Status din success: ' + status);
				console.log('success');			
			}
			
		}).error(function() {
			console.log('error');
			console.log('Data message din error: '+ data.message);
			console.log('Status din error:'+ status);
		});
		}

})

angular.module("app").directive('fileModel', ['$parse', function ($parse) {
	    return {
	        restrict: 'A',
	        link: function(scope, element, attrs) {
	            var model = $parse(attrs.fileModel);
	            var modelSetter = model.assign;
	            
	            element.bind('change', function(){
	                scope.$apply(function(){
	                    modelSetter(scope, element[0].files[0]);
	                });
	            });
	        }
	    };
	}]);

app.directive('enterDirective', function () {
    return {
        link: function (scope, element, attrs) {
            $(element).keypress(function (e) {
                if (e.keyCode == 13) {
                    console.log("Enter pressed " + element.val())
                }
            });
        }
    }
});

