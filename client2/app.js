var app = angular.module("myApp", ['ui.router', 'darthwade.loading']);

app.config(['$stateProvider', '$urlRouterProvider', '$provide', '$httpProvider',
    function ($stateProvider, $urlRouterProvider, $provide, $httpProvider) {
        $urlRouterProvider.when('', '/');
        $stateProvider.state('home', {
            url: '/',
            /*data: {
             pageTitle: 'CURRENT_SITUATION'
             },
             views: {
             'content@': {
             templateUrl: 'partial/current_situation/current_situation.html',
             controller: 'CurrentSituationCtrl'
             }
             },*/
            views: {
                'content@': {
                    templateUrl: 'partial/chats/chats.html',
                    controller: 'myCtrl'
                }
            }
        }).state('login', {
            url: '/login',
            data: {
                pageTitle: 'LOG_IN_SYSTEM'
            },
            views: {
                'content@': {
                    templateUrl: 'partial/authentication/login.html',
                    controller: 'LoginCtrl'
                }
            }
        });

        $provide.factory('httpRequestGeneralInterceptor', function () {
            return {
                //TODO service for localStorage
                request: function (config) {
                    if (localStorage.currentUser && localStorage.currentUser != "null") {
                        var currentUser = JSON.parse(localStorage.currentUser);
                        if (currentUser && currentUser.token) {
                            config.headers['Authorization'] = 'Bearer ' + currentUser.token;
                        }
                    }
                    config.headers['Accept'] = 'application/json';
                    return config;
                }
            };
        });

        $httpProvider.interceptors.push('httpRequestGeneralInterceptor');
    }]);