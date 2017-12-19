angular.module('myApp')
    .controller('MenuCtrl', ['$state', '$scope', 'authService',
        function ($state, $scope, authService) {

            $scope.isPersonActive = true;

            $scope.isUnauthorized = function() {
                return !authService.isAuthenticated();
            };
            angular.element(document).ready(function () {
                //$loading.start($scope.dwLoader);
                $scope.isAdmin = authService.isAdmin();
                $scope.$apply();
                //$loading.finish($scope.dwLoader);
                /*NgMap.getMap().then(function(map) {
                 console.log(map.getCenter());
                 console.log('markers', map.markers);
                 console.log('shapes', map.shapes);
                 });*/
            });

            $scope.logout = function() {
                authService.logout();
                $state.go('login');
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