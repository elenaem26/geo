(function () {
    'use strict';
    var app = angular.module('myApp');
    app.service('stompHelper', ['$q', function ($q) {
        var _stompClient;
        var _socket;
        var _subscriptions = [];
        var _connectionState = 0; //0 - not connected, 1 - connected, 2 - connecting
        var _timer;
        var _login;
        var _pass;
        var _url;
        var _reconnectAttempts = 0;
        var _deferred;

        this.subscribe = function (subscribeEndpoint, userFunc, errorCallback, id, shouldLeaveAlive) {
            createSubcription(subscribeEndpoint, userFunc, errorCallback, id, shouldLeaveAlive);
        };

        function createSubcription(subscribeEndpoint, userFunc, errorCallback, id, shouldLeaveAlive) {
            if (_stompClient) {
                if (_connectionState != 1) {
                    var self = this;
                    //wait for connection
                    setTimeout(function(){createSubcription(subscribeEndpoint, userFunc, errorCallback, id, shouldLeaveAlive)}, 100);
                } else {
                    if (id && _subscriptions && _subscriptions.length > 0) {
                        var i = 0;
                        while (i < _subscriptions.length && _subscriptions[i].id != id) {
                            i++
                        }
                        if (i <  _subscriptions.length) {
                            return;
                        }
                    }
                    var subscription = _stompClient.subscribe(subscribeEndpoint, userFunc, { id: id });
                    if (shouldLeaveAlive) {
                        subscription.shouldLeaveAlive = true;
                    }
                    subscription.endpoint = subscribeEndpoint;
                    subscription.userFunc = userFunc;
                    _subscriptions.push(subscription);
                }
            }
        }

        this.connect = function(url, login, pass) {
            if (_connectionState == 2) {
                //already connecting to server
                var defer = $q.defer();
                defer.resolve();
                return defer.promise;
            }
            if (_stompClient && _connectionState == 1) {
                _stompClient.disconnect();
                _subscriptions = [];
                _connectionState = 0;
            }
            if (url) {
                _url = url;
            }
            if (login) {
                _login = login;
            }
            if (pass) {
                _pass = pass;
            }
            return initConnection();
        };

        this.unsubscribe = function() {
            if (_subscriptions && _subscriptions.length > 0) {
                for (var i = _subscriptions.length - 1; i >= 0 ; i--) {
                    var subscription = _subscriptions[i];
                    if (!subscription.shouldLeaveAlive) {
                        subscription.unsubscribe();
                        _subscriptions.splice(i, 1);
                    }
                }
            }
        };

        this.forceUnsubscribe = function(subscriptionId) {
            if (_subscriptions) {
                for (var i = _subscriptions.length - 1; i >= 0 ; i--) {
                    var subscription = _subscriptions[i];
                    if (subscription.id && subscription.id == subscriptionId) {
                        subscription.unsubscribe();
                        _subscriptions.splice(i, 1);
                    }
                }
            }
        };

        this.disconnect = function() {
            if (_stompClient && _connectionState == 1) {
                _stompClient.disconnect();
                _subscriptions = [];
                _connectionState = 0;
            }
        };

        this.send = function (endpoint, params, data) {
            _stompClient.send(endpoint, params, data);
        };

        function connectCallback() {
            _connectionState = 1;
            _reconnectAttempts = 0;
            restoreSubscriptions();
            _deferred.resolve();
        }

        function restoreSubscriptions() {
            if (_subscriptions && _subscriptions.length > 0) {
                for (var i = _subscriptions.length - 1; i >= 0 ; i--) {
                    var sub = _subscriptions[i];
                    createSubcription(sub.endpoint, sub.userFunc, null, sub.id, sub.shouldLeaveAlive);
                }
            }
        };

        function connectFailureCallback() {
            _connectionState = 2;
            _stompClient = null;
            if (_reconnectAttempts < 3) {
                setTimeout(function() {
                    _reconnectAttempts++;
                    initConnection();
                },  _reconnectAttempts * 100);
            } else {
                _connectionState = 0;
                _deferred.reject();
            }
        }

        function initConnection() {
            if (_url && _login && _pass) {
                _deferred = $q.defer();
                _connectionState = 2;
                _socket = new SockJS(_url);
                _stompClient = Stomp.over(_socket);
                _stompClient.connect(_login, _pass, connectCallback, connectFailureCallback);
                return _deferred.promise;
            }
        }
    }]);

})();