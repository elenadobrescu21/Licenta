var app = angular.module("app", ['ui.router']);

app.config(function($stateProvider, $urlRouterProvider, $httpProvider, $locationProvider) {
    //
    // For any unmatched url, redirect to /state1
    $urlRouterProvider.otherwise("/");
    //
    // Now set up the states
    $stateProvider
        .state('default', {
            url: "/",
            templateUrl: "partials/first-page.html"
        })
   
    .state('register', {
        url: "/register",
        templateUrl: "partials/create-account.html"
    })
    
    .state('login', {
        url: "/login",
        templateUrl: "partials/login.html"
    })
    
    .state('users', {
        url: "/users",
        templateUrl: "partials/user-management.html"
    })
    
   $httpProvider.interceptors.push('AuthInterceptor');
   $locationProvider.html5Mode(true);
    
         
});