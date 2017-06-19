angular.module('myApp')
    .factory('authService', ['$http', '$rootScope', '$state', Service]);

    function Service($http, $rootScope, $state) {
        var service = {};

        service.login = login;
        service.logout = logout;
        service.isAuthenticated = isAuthenticated;
        service.checkExpiration = checkExpiration;
        service.getCurrentUser = getCurrentUser;

        return service;

        function login(username, password, callback) {
            return $http.post('http://localhost:8080/mygeo/login', { username: username, password: password })
                .then(function (response) {
                        // login successful if there's a token in the response
                        if (response.headers('Authorization')) {
                            // add jwt token to auth header for all requests made by the $http service
                            var token = response.headers('Authorization');
                            token = token.replace(/Bearer\s+/, '');

                            // store username and token in local storage to keep user logged in between page refreshes
                            localStorage.currentUser = JSON.stringify({ username: username, token: token});
                            $rootScope.currentUser = { username: username, token: token};

                            // execute callback with true to indicate successful login
                            //callback(true);
                        } else {
                            // execute callback with false to indicate failed login
                            //callback(false);
                        }
                    })
                .catch(function() {
                    localStorage.currentUser = null;
                })
        }

        function isAuthenticated() {
            if (!$rootScope.currentUser) {
                if (localStorage.currentUser) {
                    $rootScope.currentUser = JSON.parse(localStorage.currentUser);
                }
            }
            return !!$rootScope.currentUser;
        }

        function getCurrentUser() {
            /*            if (!$rootScope.currentUser) {
             if (localStorage.currentUser) {
             $rootScope.currentUser = JSON.parse(localStorage.currentUser);
             }
             }*/
            return $rootScope.currentUser;
        }

        function checkExpiration() {
            if (!isAuthenticated()) {
                $state.go("login");
            }
        }

        function logout() {
            // remove user from local storage and clear http auth header
            delete localStorage.currentUser;
            //$http.defaults.headers.common.Authorization = '';
        }
    }