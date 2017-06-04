var app = angular.module("app", ['ui.router','lr.upload', 'ngMaterial', 'ui.bootstrap']);

app.config(function($stateProvider, $urlRouterProvider, $httpProvider, $locationProvider) {
    //
    // For any unmatched url, redirect to /state1
    $urlRouterProvider.otherwise("/");
    //
    // Now set up the states
    $stateProvider
        .state('default', {
            url: "/",
            templateUrl: "partials/home.html"
        })
   
    .state('register', {
        url: "/register",
        templateUrl: "partials/create-account.html"
    })
    
    .state('login', {
        url: "/login",
        templateUrl: "partials/login.html"
    })
    
    .state('admin', {
        url: "/admin",
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
    
      .state('article', {
        url: "/article/:id",
        templateUrl: "partials/article.html"
    })
    
      .state('user', {
        url: "/user/:id",
        templateUrl: "partials/user-page.html"
    })
    
     .state('arhiva', {
        url: "/arhiva",
        templateUrl: "partials/arhiva.html"
    })
    
    .state('search-tag', {
        url: "/search-tag",
        templateUrl: "partials/search-tag.html"
    })
    
    .state('modal', {
        url: "/modal",
        templateUrl: "partials/modal.html"
    })
    
     .state('cautare', {
        url: "/cautare",
        templateUrl: "partials/queries.html"
    })
     
   $httpProvider.interceptors.push('AuthInterceptor');
    //pentru # la rute
  // $locationProvider.html5Mode(true);
    
         
});