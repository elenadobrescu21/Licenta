(function () {
  'use strict';
  angular
      .module('app')
      .controller('SearchCtrl', DemoCtrl);

  function DemoCtrl ($timeout, $q, $log, $http, User) {
    var self = this;

    self.simulateQuery = false;
    self.isDisabled    = false;
    self.searchDisabled = true;

    // list of `state` value/display objects
    self.users  = [];
    self.usersWithFullname = [];
    self.querySearch   = querySearch;
    self.selectedItemChange = selectedItemChange;
    self.searchTextChange   = searchTextChange;
    self.articles = [];
    self.visibleTable = false;
    	
	 User.all().then(function(result) {
	      console.log("Members", result.data);
	      self.users = result.data; 
	      for( var i=0; i<result.data.length; i++) {
	    	  console.log(result.data[i].id);
	    	  var name = result.data[i].nume + " " + result.data[i].prenume;
	    	  var obj = {id: result.data[i].id, fullname: name};
	    	  self.usersWithFullname.push(obj);
	      }
	  }, function(err) {
	      console.error(err);
	  })

   

    // ******************************
    // Internal methods
    // ******************************

    /**
     * Search for states... use $timeout to simulate
     * remote dataservice call.
     */
    function querySearch (query) {
      var results = query ? self.usersWithFullname.filter( createFilterFor(query) ) : self.usersWithFullname,
          deferred;
      if (self.simulateQuery) {
        deferred = $q.defer();
        $timeout(function () { deferred.resolve( results ); }, Math.random() * 1000, false);
        return deferred.promise;
      } else {
        return results;
      }
    }

    function searchTextChange(text) {
      $log.info('Text changed to ' + text);
      return true;
    }

    function selectedItemChange(item) {
      $log.info('Item changed to ' + JSON.stringify(item.fullname));
      console.log("Selected item: ");
      console.log(item);
      var id = item.id;
  	  console.log("Item id:");
  	  console.log(id);
  	  $http.get('http://localhost:8080/articleByAuthor/'+id)
  	  .success(function(data, status, headers, config){
			self.articles = data;
			self.visibleTable = true;
			console.log("Din functia sendSelectedItem");
			console.log(data);
		})	
		.error(function(error){
				console.log("Bad credentials din AuthFactory");
				errorMessage = "Bad Credentials";
				callback(errorMessage);
		})
    }
    
    self.clickMeButton = function() {
    	console.log("Am apasat pe buton");
    }
    
    function clickMe() {
    	console.log("Am apasat pe buton");
    }
    
   self.sendSelectedItem = function(item) {
	    console.log("am apelat functia send selected item");
    	var id = item.id;
    	console.log("Item id:");
    	console.log(id);
    	$http.post('http://localhost:8080/articleByAuthor/'+id)
    	.success(function(data, status, headers, config){
			self.articles = data;
			console.log("Din functia sendSelectedItem");
			console.log(data);
		})	
		.error(function(error){
				console.log("Bad credentials din AuthFactory");
				errorMessage = "Bad Credentials";
				callback(errorMessage);
		})
    	
    }


    /**
     * Create filter function for a query string
     */
    function createFilterFor(query) {
      var lowercaseQuery = angular.lowercase(query);

      return function filterFn(state) {
        return (state.value.indexOf(lowercaseQuery) === 0);
      };

    }
  }
})();