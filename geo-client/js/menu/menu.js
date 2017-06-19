angular.module('myApp')
    .controller('MenuCtrl', ['$state', '$scope', 'authService',
        function ($state, $scope, authService) {

            $scope.isPersonActive = true;


            $scope.isUnauthorized = function() {
                return !authService.isAuthenticated();
            };

            $scope.logout = function() {
                authService.logout().then(function (res) {
                    $state.go('login');
                });
            };

            $scope.getUrl = function(){
                return $scope.isUnauthorized() ? "#" + $state.get("login").url : $state.get("home").url;
            };

           /* function getUrlByRole(){
                var result = "#";
                if($scope.isCurrentUserAdmin()){
                    result += $state.get("pages_list").url;
                }
                if($scope.isCurrentUserCitizen()){
                    result += $state.get("user_messages").url;
                }
                if($scope.isCurrentUserCityService()){
                    result += $state.get("task_list").url;
                }
                return result;
            }*/
        }]);