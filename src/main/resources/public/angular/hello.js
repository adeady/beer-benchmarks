var myAppModule = angular.module('myApp', ['ui.bootstrap','ui.grid'])
    .controller('UsersController', function($scope, $http) {
        $http.get('/api/users').success(function(data) {
            $scope.names = data;
            $scope.gridOptions = {
                columnDefs: [
                    {field: 'name', displayName: 'Name'},
                    {
                        displayName: 'Edit',
                        cellTemplate: '<button id="editBtn" type="button" class="btn-small" >Edit</button> '
                    }
                ]
            };
        });
    });
