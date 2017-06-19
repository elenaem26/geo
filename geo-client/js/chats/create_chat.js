angular.module('myApp')
    .controller("createChatCtrl", ['$scope', 'chatsService', '$stateParams', 'stompHelper', '$state', '$loading', 'toastr',
        function($scope, chatsService, $stateParams, stompHelper, $state, $loading, toastr) {

        $scope.dwLoader = "loader-title";
        $scope.user_chat = {};

        var chatSaved = false;

        function setLocation() {
            $scope.user_chat.latitude = 51.656920;
            $scope.user_chat.longitude = 39.18601;
        }

        $scope.save = function() {
            if (chatSaved) {
                return;
            }
            $loading.start($scope.dwLoader);
            chatSaved = true;
            setLocation();
            chatsService.saveChat($scope.user_chat).then(function (response) {
                $loading.finish($scope.dwLoader);
                $state.go("my_chats");
            }, function (response) {
               $loading.finish($scope.dwLoader);
                chatSaved = false;
                toastr.error(response.data);
            });
        };
    }]);
