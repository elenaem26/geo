var app = angular.module("myApp", ['ui.router', 'darthwade.loading', 'ngMap', 'config']);

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
                    controller: 'chatsCtrl'
                }
            }
        }).state('chat', {
            url: '/chat/:id',
            data: {
                pageTitle: 'Chat'
            },
            views: {
                'content@': {
                    templateUrl: 'partial/chats/chat.html',
                    controller: 'chatCtrl'
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
                    return config;
                }
            };
        });

        $httpProvider.interceptors.push('httpRequestGeneralInterceptor');
    }]);

app.run(['CONFIG', '$http', 'stompHelper',
    function (CONFIG, $http, stompHelper) {

        initSocket();

        function initSocket() {
            var url = CONFIG.apiUrl + "ws";
            return stompHelper.connect(url, "guest", "guest");
        }
    }]);