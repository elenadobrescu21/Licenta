(function () {
  'use strict';
  angular
      .module('app')
      .controller('SearchTagCtrl', SearchTagCtrl);

  function SearchTagCtrl($timeout, $q, $log, $http, User, Tag) {
    var self = this;

    self.simulateQuery = false;
    self.isDisabled    = false;
    self.searchDisabled = true;

    // list of `state` value/display objects
    self.tags  = [];
    self.userTags = [];
    self.querySearch   = querySearch;
    self.selectedItemChange = selectedItemChange;
    self.searchTextChange   = searchTextChange;
    self.articles = [];
    self.visibleTable = false;
    	
	 Tag.getAllTags().then(function(result) {
	      console.log("Members", result.data);
	      self.tags = result.data; 
	      console.log(self.tags);
	     
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
      var results = query ? self.tags.filter( createFilterFor(query) ) : self.tags,
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
      $log.info('Item changed to ' + JSON.stringify(item.denumire));
      console.log("Selected item: ");
      console.log(item);
     // console.log(item);
      self.userTags.push(item);
      var id = item.tagId;
  	  console.log("Item id:");
  	  console.log(id);
      
    }
    
    self.clickMeButton = function() {
    	console.log("Am apasat pe buton");
    }
    
    function clickMe() {
    	console.log("Am apasat pe buton");
    }
    
   self.sendSelectedItem = function(item) {
	    console.log("am apelat functia send selected item");
    	//var id = item.tagId;
    	console.log("Item id:");
    	//console.log(id);
    	self.userTags.push(item);
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