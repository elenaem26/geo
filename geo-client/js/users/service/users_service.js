angular.module('myApp')
    .factory('usersService',['$http',  function($http) {
        return {
            'getAllUsers' : function() {
                var req = {
                    'method' : 'GET',
                    'url' : 'http://localhost:8080/mygeo/admin/allusers',
                };
                return $http(req);
            }
        }}]);
