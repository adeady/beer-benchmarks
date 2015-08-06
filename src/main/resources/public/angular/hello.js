var myAppModule = angular.module('myApp', ['ui.bootstrap','ui.grid', "ui.bootstrap.modal"])
    .controller('UsersController', function($scope, $http, $modal, $log) {
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
        $scope.items = ['item1', 'item2', 'item3'];

        $scope.open = function () {

            var modalInstance = $modal.open({
                animation: true,
                templateUrl: 'myModalContent.html',
                controller: ModalInstanceCtrl,
                size: '',
                resolve: {
                    items: function () {
                        return $scope.items;
                    }
                }
            });

            modalInstance.result.then(function (selectedItem) {
                $scope.selected = selectedItem;
            }, function () {
                $log.info('Modal dismissed at: ' + new Date());
            });
        };

    });

var bob = angular.module('foobar', [])
    .controller('ModalInstanceCtrl', function ($scope, $modalInstance, items) {

    $scope.items = items;
    $scope.selected = {
        item: $scope.items[0]
    };

    $scope.ok = function () {
        $modalInstance.close($scope.selected.item);
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
});
