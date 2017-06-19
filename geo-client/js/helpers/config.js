(function (angular) {

    angular.module('config', []);
    var baseUrl =  window.location.protocol + '//' + window.location.host + '/mygeo/';
    angular.module('config').constant('CONFIG', {
        'apiUrl': baseUrl
    });
})(angular);