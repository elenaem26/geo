angular.module('myApp')
    .controller("myCtrl", ['$scope', 'chatsService', function($scope, chatsService) {
        $scope.firstName = "John";
        $scope.lastName= "Doe";
        $scope.chats = [];
        angular.element(document).ready(function () {
            //$loading.start($scope.dwLoader);
            getChats(51.656919, 39.186008).then(function (response) {
                $scope.chats = response.data;
            });
            //$loading.finish($scope.dwLoader);
        });

        function getChats(latitude, longitude) {
            return chatsService.findChats(latitude, longitude);
        }
}]);