var app = angular.module("myApp", ['ui.router', 'darthwade.loading', 'ngMap', 'config', 'toastr']);

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
        }).state('create_chat', {
            url: '/create_chat',
            parent: 'root',
            data: {
                pageTitle: 'Создание чата'
            },
            views: {
                'content@': {
                    templateUrl: 'partial/chats/create_chat.html',
                    controller: 'createChatCtrl'
                }
            }
        }).state('my_chats', {
            url: '/my_chats',
            parent: 'root',
            data: {
                pageTitle: 'Мои чаты'
            },
            views: {
                'content@': {
                    templateUrl: 'partial/chats/my_chats.html',
                    controller: 'myChatsCtrl'
                }
            }
        }).state('users', {
            url: '/users',
            parent: 'root',
            data: {
                pageTitle: 'Все пользователи'
            },
            views: {
                'content@': {
                    templateUrl: 'partial/users/users.html',
                    controller: 'usersCtrl'
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

        $provide.factory('httpRequestGeneralInterceptor',['$rootScope', function ($rootScope) {
            return {
                request: function (config) {
                    var user = $rootScope.currentUser;
                    if (user && user != "null" && user.token) {
                        config.headers['Authorization'] = 'Bearer ' + user.token;
                    }
                    return config;
                }
            };
        }]);

        $httpProvider.interceptors.push('httpRequestGeneralInterceptor');
        $httpProvider.defaults.headers.common['Content-Type'] = 'application/json';
    }]);

app.run(['CONFIG', '$http', 'stompHelper', '$rootScope', '$state', 'toastr',
    function (CONFIG, $http, stompHelper, $rootScope, $state, toastr) {

        initSocket();

        function initSocket() {
            var url = CONFIG.apiUrl + "ws";
            return stompHelper.connect(url, "guest", "guest");
        }

        $rootScope.$on('$stateChangeSuccess', function () {
            document.title = $state.current.data.pageTitle;
        });
    }]);