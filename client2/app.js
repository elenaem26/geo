var app = angular.module("myApp", ['ui.router', 'darthwade.loading', 'ngMap', 'config']);

app.config(['$stateProvider', '$urlRouterProvider', '$provide', '$httpProvider',
    function ($stateProvider, $urlRouterProvider, $provide, $httpProvider) {
        $urlRouterProvider.when('', '/');
        $stateProvider.state('root', {
            url: '',
            'abstract': true,
            views: {
                'menu': {
                    templateUrl: 'partial/menu/menu.html',
                    controller: 'MenuCtrl'
                }/*,
                'footer': {
                    templateUrl: 'partial/footer/footer.html',
                    controller: 'FooterCtrl'
                }*/
            }
        }).state('home', {
            url: '/',
            parent: 'root',
            data: {
                pageTitle: 'Чаты поблизости'
            },
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
            parent: 'root',
            data: {
                pageTitle: 'Чат'
            },
            views: {
                'content@': {
                    templateUrl: 'partial/chats/chat.html',
                    controller: 'chatCtrl'
                }
            }
        }).state('login', {
            url: '/login',
            parent: 'root',
            data: {
                pageTitle: 'Войти'
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

app.run(['CONFIG', '$http', 'stompHelper', '$rootScope', '$state',
    function (CONFIG, $http, stompHelper, $rootScope, $state) {

        initSocket();

        function initSocket() {
            var url = CONFIG.apiUrl + "ws";
            return stompHelper.connect(url, "guest", "guest");
        }

        $rootScope.$on('$stateChangeSuccess', function () {
            document.title = $state.current.data.pageTitle;
        });
    }]);