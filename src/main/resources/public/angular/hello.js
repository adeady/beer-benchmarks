function Users($scope, $http) {
    $http.get('/api/users').success(function(data) {
            $scope.names = data;
        });
}