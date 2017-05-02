angular.module('myApp')
    .factory('chatsService',['$http',  function($http) {
        return {
            'findChats' : function(latitude, longitude) {
                var req = {
                    'method' : 'GET',
                    'url' : 'http://localhost:8080/mygeo/api/chat',
                    'params' : {'latitude': latitude, 'longitude': longitude}
                };
                return $http(req);
        }
    }}]);
