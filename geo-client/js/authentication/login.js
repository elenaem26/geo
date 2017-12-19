angular.module('myApp')
    .controller('LoginCtrl', ['$state', '$scope', '$loading', 'authService',
        function ($state, $scope, $loading, authService) {
            $scope.authFailed = false;
            $scope.userType = null;
            $scope.dwLoader = "dwLoader";
            $scope.isBtnDisabled = false;

            $scope.submit = function () {
                $scope.isBtnDisabled = true;
                $loading.start($scope.dwLoader);
                authService.login($scope.username, $scope.password).then(function (data) {
                    if (!authService.isAuthenticated()) {
                        $scope.authFailed = true;
                    } else {
                        $state.go('home', {}, {reload: true});
                    }
                    $scope.isBtnDisabled = false;
                    $loading.finish($scope.dwLoader);
                });
            };

            $scope.clearErrors = function () {
                $scope.authFailed = false;
            };

        }]);