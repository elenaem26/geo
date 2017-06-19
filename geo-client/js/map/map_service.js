angular.module('myApp')
    .service('mapService', function () {
        var markers = [];
        var circles = [];

        return {
            addMarkerAndCircle: function (item) {
                var i = markers.length;
                markers[i] = new google.maps.Marker({
                    title: item.name
                });
                var location = new google.maps.LatLng(item.latitude, item.longitude);
                markers[i].setPosition(location);
                markers[i].setShape({
                    coords: [item.latitude, item.longitude, item.radius],
                    type: "circle"
                });
                markers[i].setMap(null);
                circles[i] = new google.maps.Circle({
                    strokeColor: '#FF0000',
                    strokeOpacity: 0.1,
                    strokeWeight: 2,
                    fillColor: '#FF0000',
                    fillOpacity: 0.35,
                    map: null,
                    center: location,
                    radius: 300
                });
                return i;
            },
            hideMarkerAndCircle: function (i) {
                if (markers[i] !== null) {
                    markers[i].setMap(null);
                }
                if (circles[i] !== null) {
                    circles[i].setMap(null);
                }
            },
            showCircle: function(i, map) {
                if (markers[i] !== null) {
                    markers[i].setMap(map);
                }
                if (circles[i] !== null) {
                    circles[i].setMap(map);
                }
            },
            hideAll: function() {
                for (var i =  markers.length - 1 ; i >= 0; i--) {
                    markers[i].setMap(null);
                    markers.splice(i, 1);
                }
                for (var i =  circles.length - 1 ; i >= 0; i--) {
                    circles[i].setMap(null);
                    circles.splice(i, 1);
                }
            }
        };
    });
