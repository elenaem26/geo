angular.module('myApp')
    .controller("usersCtrl", ['$scope', 'usersService', 'NgMap', 'authService', 'mapService', function($scope, usersService, NgMap, authService, mapService) {
        $scope.users = [];
        authService.checkExpiration();
        angular.element(document).ready(function () {
            //$loading.start($scope.dwLoader);
            getUsers();

        });


        function getUsers() {
            usersService.getAllUsers().then(function (response) {
                $scope.users = response.data;
            });
        }

    }]);