angular.module("app").factory('Auth', function($http, $q, $state, AuthToken){
	
	var authFactory = {};
	
	authFactory.login = function(username, password) {
		
		return $http.post('http://localhost:8080/auth', {
			username: username,
			password: password
		})
		.success(function(data){
			AuthToken.setToken(data.token);
			return data;
		})	
		.error(function(error){
				console.log("Bad credentials");
				return $q.reject({message: "Bad credentials"});
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

.factory('AuthInterceptor', function($q, $location, AuthToken) {
	
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
		if(response.status == 403) {
			$state.go('login');
			return $q.reject(response);		
		}
	}
	
	return interceptorFactory;
})