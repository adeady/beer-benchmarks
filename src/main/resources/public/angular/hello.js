var myAppModule = angular.module('myApp', ['ui.bootstrap','ui.grid'])
    .controller('UsersController', function($scope, $http) {
        $http.get('/api/users').success(function(data) {
            $scope.names = data;

        });
        $scope.addUser = function() {
            var n = $scope.names.length + 1;
            $http.post('/api/users', {name:'user '+n}).
                success(function(data, status, headers, config) {
                    $scope.names.push(data);
                }).
                error(function(data, status, headers, config) {
                    console.log(data)
                });
        };
    });
