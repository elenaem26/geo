angular.module('myApp')
    .controller("myChatsCtrl", ['$scope', 'chatsService', 'NgMap', 'authService', '$timeout', 'mapService',
        function($scope, chatsService, NgMap, authService, $timeout, mapService) {
        $scope.chats = [];

        var selectedChat = null;
        $scope.latitude = 51.656919;
        $scope.longitude = 39.186008;
        authService.checkExpiration();
        angular.element(document).ready(function () {
            //$loading.start($scope.dwLoader);
            /*NgMap.getMap().then(function(map) { //Create the instance of map
                $scope.map = map;
                var markers = $scope.map.markers; //?
            });*/
            //hideMarkers();
            getChats();
            //$loading.finish($scope.dwLoader);
            /*NgMap.getMap().then(function(map) {
             console.log(map.getCenter());
             console.log('markers', map.markers);
             console.log('shapes', map.shapes);
             });*/
        });

        //TODO failure callback
        // function getChats() {
        //     if (navigator.geolocation) {
        //         navigator.geolocation.getCurrentPosition(showPosition);
        //     } else {
        //         console.log("Geolocation is not supported by this browser.");
        //     }
        // }
        // function showPosition(position) {
        //     $scope.latitude = 51.656919; //position.coords.latitude;
        //     $scope.longitude = 39.186008; //position.coords.longitude;
        //     chatsService.findChats($scope.latitude, $scope.longitude).then(function (response) {
        //         $scope.chats = response.data;
        //     });
        // }

        function getChats() {
            chatsService.getMyChats().then(function (response) {
                $scope.chats = response.data;
                generateChatMarkers();
            });
        }
        /*for (var i=0; i<8 ; i++) {
         markers[i] = new google.maps.Marker({
         title: "Hi marker " + i
         })
         }

         $scope.GenerateMapMarkers = function() {

         var numMarkers = Math.floor(Math.random() * 4) + 4;  // betwween 4 & 8 of them
         for (i = 0; i < numMarkers; i++) {
         var lat =   1.280095 + (Math.random()/100);
         var lng = 103.850949 + (Math.random()/100);
         // You need to set markers according to google api instruction
         // you don't need to learn ngMap, but you need to learn google map api v3
         // https://developers.google.com/maps/documentation/javascript/marker
         var latlng = new google.maps.LatLng(lat, lng);
         markers[i].setPosition(latlng);
         markers[i].setMap($scope.map)
         }
         };*/

        function generateChatMarkers() {
            mapService.hideAll();
            for (i = 0; i < $scope.chats.length; i++) {
                var item = $scope.chats[i];
                item.markerId = mapService.addMarkerAndCircle(item);
            }
        }

        $scope.showCircle = function(chat) {
            if (selectedChat !== null && selectedChat.markerId !== null) {
                mapService.hideMarkerAndCircle(selectedChat.markerId);
            }
            mapService.showCircle(chat.markerId, $scope.map);
            selectedChat = chat;
        };
    }]);