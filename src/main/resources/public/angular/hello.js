var myAppModule = angular.module('myApp', ['ui.bootstrap'])
    .controller('UsersController', function($scope, $http) {
        $http.get('/api/users').success(function(data) {
            $scope.names = data;
        });
    });
