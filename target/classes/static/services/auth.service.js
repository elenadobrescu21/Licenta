angular.module("app").factory('Auth', function($http, $q, $state, AuthToken){
	
	var authFactory = {};
	
	authFactory.login = function(username, password,callback) {
		
		$http.post('http://localhost:8080/auth', {
			username: username,
			password: password
		})
		.success(function(data, status, headers, config){
			AuthToken.setToken(data.token);
			callback(data);
		})	
		.error(function(error){
				console.log("Bad credentials din AuthFactory");
				errorMessage = "Bad Credentials";
				callback(errorMessage);
		})
	}
	
	authFactory.logout = function() {
		AuthToken.setToken();
	}
	
	authFactory.isLoggedIn = function () {
		if(AuthToken.getToken()) {
			console.log("User is logged in");
			return true;
		}else {
			console.log("User is not logged in");
			return false;
		}		
	}
	
	authFactory.getLoggedInUser = function () {
		if(AuthToken.getToken()) {
			$http.get("http://localhost:8080/me").then(function(result){
				return result.data;
			})
		}
	}
	
	authFactory.getUser = function (callback) {
		if(AuthToken.getToken()) {
			$http.get("http://localhost:8080/me").success(function(data){
				console.log("Data:", data);
				callback(data);
			})
			.error(function(error){
				console.log("User couldn't be retrieved");
				callback(false);
			})		
		} else {
			return $q.reject({message: "User has no token"});
		}
	}
	
	authFactory.isAdmin = function(callback) {
	    isAdmin = false;	
		if(AuthToken.getToken()) {
			$http.get("http://localhost:8080/me").success(function(data){
				console.log("Data:", data);
				console.log("Authorities:" , data.authorities);
				console.log("Authorities length", data.authorities.length);
				for (var i = 0; i < data.authorities.length; i++) {
				    console.log(data.authorities[i].authority);
				    if(data.authorities[i].authority == "ROLE_ADMIN") {
				    	console.log("Userul este admin");
				    	isAdmin = true;
				    }
				}
				callback(isAdmin);
			})
			.error(function(error){
				console.log("User couldn't be retrieved");
				callback(false);
			})		
		} else {
			return $q.reject({message: "User has no token"});
		}
		
		
	}
	
	
	
	return authFactory;

})

.factory('AuthToken', function($window){
	
	var authTokenFactory = {};
	
	authTokenFactory.getToken = function() {
		return $window.localStorage.getItem('token');
	}
	
	authTokenFactory.setToken = function(token) {
		if(token) {
			$window.localStorage.setItem('token', token);
		}else {
			$window.localStorage.removeItem('token');
		}
		
 	}
	return authTokenFactory;
	
})

.factory('AuthInterceptor', function($q, $location,AuthToken) {
	
	var interceptorFactory = {};
	interceptorFactory.request = function(config) {
		var token = AuthToken.getToken();
		if(token) {
			config.headers['X-Auth-Token'] = token;
		}
		return config;
	}
	interceptorFactory.responseError = function(response) {
		//403 = forbidden
		if(response.status == 403 || response.status==401) {
			//$state.go('login');
			console.log("Avem status code:"  + response.status);
			return $q.reject(response);		
		}
	}
	
	return interceptorFactory;
})