

var myAppModule = angular.module('myApp', [])
    .controller('UsersController', function($scope, $http) {
        $http.get('/api/users').success(function(data) {
            $scope.names = data;
        });
    });
