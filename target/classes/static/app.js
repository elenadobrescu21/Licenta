var app = angular.module("app", ['ui.router','lr.upload', 'ngMaterial']);

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
    
     .state('upload', {
        url: "/upload",
        templateUrl: "partials/upload-file.html"
    })
    
     .state('download', {
        url: "/download",
        templateUrl: "partials/download-pdf.html"
    })
    
     .state('search', {
        url: "/search",
        templateUrl: "partials/search.html"
    })
    
   
    
   
    
   $httpProvider.interceptors.push('AuthInterceptor');
    //pentru # la rute
  // $locationProvider.html5Mode(true);
    
         
});