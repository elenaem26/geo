angular.module('myApp')
    .controller("chatCtrl", ['$scope', 'chatsService', '$stateParams', 'stompHelper', '$state', function($scope, chatsService, $stateParams, stompHelper, $state) {

        var id = $stateParams.id | 0;
        $scope.chat = null;
        $scope.messages = null;
        $scope.error = null;
        $scope.message = {};

        angular.element(document).ready(function () {
                joinChat().then(function () {
                    getChat().then(function (response) {
                        $scope.chat = response.data;
                    }, function () {
                        $scope.error = "Не удалось загрузить чат";
                    });
                    getMessages().then(function (response) {
                        $scope.messages = response.data;
                    }, function () {
                        $scope.error = "Не удалось загрузить сообщения";
                    });
                }, function() {
                    $scope.error = "Не удалось присоединиться к чату";
                });
                connectToSocket("/topic/" + id);
            });

        $scope.sendMessage = function() {
            $scope.message.chatId = id;
            sendMessage($scope.message);
            $scope.message = {};
        };

        $scope.leaveChat = function() {
            leaveChat().then(function (response) {
                $state.go('home');
            }, function () {

            })
        };

        function joinChat() {
            return chatsService.joinChat(id, 51.656919, 39.186008);
        }
        function leaveChat() {
            return chatsService.leaveChat(id);
        }
        function getChat() {
            return chatsService.getChat(id);
        }
        function getMessages() {
            return chatsService.getMessages(id);
        }
        function sendMessage(message) {
            return chatsService.sendMessage(message);
        }

        function connectToSocket(endpoint) {
            function addMessage(frame) {
                if (frame.body != null) {
                    $scope.messages.push(JSON.parse(frame.body));
                    $scope.$apply();
                }
            }
            stompHelper.subscribe(endpoint, addMessage);
        }
    }]);
