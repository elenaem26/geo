angular.module('myApp')
    .factory('chatsService',['$http',  function($http) {
        return {
            'findChats' : function(latitude, longitude) {
                var req = {
                    'method' : 'GET',
                    'url' : 'http://localhost:8080/mygeo/api/chat/by_location',
                    'params' : {'latitude': latitude, 'longitude': longitude}
                };
                return $http(req);
            },
            'findAllChats' : function() {
                var req = {
                    'method' : 'GET',
                    'url' : 'http://localhost:8080/mygeo/admin/allchats',
                };
                return $http(req);
            },
            'getMyChats' : function() {
                var req = {
                    'method' : 'GET',
                    'url' : 'http://localhost:8080/mygeo/api/chat',
                    'headers': {
                        'Content-Type': 'application/json'
                    },
                    'data': {}
                };
                return $http(req);
            },
            'getChat' : function(chatId) {
                var req = {
                    'method' : 'GET',
                    'url' : 'http://localhost:8080/mygeo/api/chat/' + chatId
                };
                return $http(req);
            },
            'joinChat' : function(chatId, latitude, longitude) {
                var req = {
                    'method' : 'PUT',
                    'url' : 'http://localhost:8080/mygeo/api/chat/' + chatId + '/join',
                    'params' : {'latitude': latitude, 'longitude': longitude},
                    'data' : ''
                };
                return $http(req);
            },
            'leaveChat' : function(chatId) {
                var req = {
                    'method' : 'PUT',
                    'url' : 'http://localhost:8080/mygeo/api/chat/' + chatId + '/leave',
                    'data' : ''
                };
                return $http(req);
            },
            'saveChat' : function(chat) {
                var req = {
                    'method' : 'POST',
                    'url' : 'http://localhost:8080/mygeo/api/chat',
                    'data': chat
                };
                return $http(req);
            },
            'getMessages' : function(chatId) {
                var req = {
                    'method' : 'GET',
                    'url' : 'http://localhost:8080/mygeo/api/message/' + chatId + '/messages'
                };
                return $http(req);
            },
            'sendMessage' : function(message) {
                var req = {
                    'method' : 'POST',
                    'url' : 'http://localhost:8080/mygeo/api/message',
                    'data' : message
                };
                return $http(req);
            }
    }}]);
