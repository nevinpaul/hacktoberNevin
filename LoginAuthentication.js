<div class="container" ng-app="myApp">
  <div class="row">
    <div class="col-md-12">
      <div class="page-header">
        <h1>{{ title }}</h1>
      </div>
    </div>
    
    <div ui-view></div>
    
  </div>
  
  <script type="text/ng-template" id="login.html">
    <div class="col-md-12">
      <h3>Login Page</h3>
      
      <form ng-submit="formSubmit()" class="form">
        <div class="col-md-4">
          <div class="form-group">
            <input type="text" class="form-control" ng-model="username" placeholder="username" required=""/>
          </div> 

          <div class="form-group">
            <input type="password" class="form-control" ng-model="password" placeholder="password" required=""/>
          </div>

          <div class="form-group">
            <button type="submit" class="btn btn-success">Login</button>
            <span class="text-danger">{{ error }}</span>
          </div>

        </div>
      </form>
    </div>
  </script>
  
  <script type="text/ng-template" id="home.html">
    <div class="col-md-12">
      <h3>Home Page</h3>
    
      <a ui-sref="login">Back</a>
    </div>
  </script>
</div>
(function() {
  var app = angular.module('myApp', ['ui.router']);
  
  app.run(function($rootScope, $location, $state, LoginService) {
    $rootScope.$on('$stateChangeStart', 
      function(event, toState, toParams, fromState, fromParams){ 
          console.log('Changed state to: ' + toState);
      });
    
      if(!LoginService.isAuthenticated()) {
        $state.transitionTo('login');
      }
  });
  
  app.config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('/home');
    
    $stateProvider
      .state('login', {
        url : '/login',
        templateUrl : 'login.html',
        controller : 'LoginController'
      })
      .state('home', {
        url : '/home',
        templateUrl : 'home.html',
        controller : 'HomeController'
      });
  }]);

  app.controller('LoginController', function($scope, $rootScope, $stateParams, $state, LoginService) {
    $rootScope.title = "AngularJS Login Sample";
    
    $scope.formSubmit = function() {
      if(LoginService.login($scope.username, $scope.password)) {
        $scope.error = '';
        $scope.username = '';
        $scope.password = '';
        $state.transitionTo('home');
      } else {
        $scope.error = "Incorrect username/password !";
      }   
    };
    
  });
  
  app.controller('HomeController', function($scope, $rootScope, $stateParams, $state, LoginService) {
    $rootScope.title = "AngularJS Login Sample";
    
  });
  
  app.factory('LoginService', function() {
    var admin = 'admin';
    var pass = 'pass';
    var isAuthenticated = false;
    
    return {
      login : function(username, password) {
        isAuthenticated = username === admin && password === pass;
        return isAuthenticated;
      },
      isAuthenticated : function() {
        return isAuthenticated;
      }
    };
    
  });
  
})();
