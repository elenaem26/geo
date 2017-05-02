var bggisComponent = {
	defaultOptions: {
		'centerColors': ['#ff0000', '#ffffff'],
		'colorMap': {}
	},
	init: function (divId, baseUrl, authKey, readyFunc) {
		var timer;
		var callCount = 0;

		function initBgGis() {
			var gis = new window.Bggis(divId, {
				serverBaseUrl: baseUrl,
				authKey: authKey
			});
			gis.ready(function () {
				window.api = gis.api;
				gis.api.stopRequestSending();
				if (readyFunc) {
					readyFunc();
				}
			}.bind(window));
		}

		function loading() {
			if (window.hasOwnProperty("Bggis")) {
				if (timer) {
					clearTimeout(timer);
				}
				initBgGis();
			} else {
				//Если ждем больше 5 сек, прекращаем загрузку
				if (callCount >= 5000) {
					clearTimeout(timer);
					return;
				}
				callCount += 250;
				timer = setTimeout(loading, 250)
			}
		}

		loading();
	},
	getApi: function () {
		return window.api;
	},
	removeAllMarkers: function () {
		if (!window.api) {
			return;
		}
		window.api.removeAllMapObjects();
	},
	removeMarker: function (markerId) {
		if (!window.api) {
			return;
		}
		window.api.removeMapObject(markerId);
	},
	removeMarkerGroup: function (groupId) {
		if (!window.api) {
			return;
		}
		window.api.removeMapObject(groupId);
	},
	setMarkerIcon: function (markerId, dataURL, iconSizeX, iconSizeY) {
		if (!window.api) {
			return;
		}

		iconSizeX = iconSizeX || 42;
		iconSizeY = iconSizeY || 57;
		window.api.setMarkerIcon(markerId, dataURL, iconSizeX, iconSizeY);
	},
	setMapCenter: function (lat, lon) {
		if (!window.api) {
			return;
		}
		window.api.setMapCenter(lat, lon);
	},
	addCustomMarker: function (lat, lng, dataURL, status, iconSizeX, iconSizeY) {
		if (!window.api) {
			return;
		}

		iconSizeX = iconSizeX || 42;
		iconSizeY = iconSizeY || 57;
		var markerId = window.api.createMarker(lat, lng);

		/**
		/* Метод setMarkerIcon ожидает в качестве параметра URL относительно корня приложения "БГ ГИС",
		/* а не клиентского приложения. Самое простое решение: преобразовать нужную картинку в Base64 и
		/* передать полученную строку в качестве URL.
		*/
		window.api.setMarkerIcon(markerId, dataURL, iconSizeX, iconSizeY);
		window.api.setMarkerStatus(markerId, status);
		return markerId;
	},
	showMarker: function (markerId, lat, lng, goToMarker) {
		if (!window.api) {
			return;
		}
		window.api.showMapObject(markerId);
		if (goToMarker) {
			window.api.setMapCenter(lat, lng);
		}
	},
	enableCoordinateSelectionMode: function (funcXY) {
		if (!window.api) {
			return;
		}
		window.api.enableCoordinateSelectionMode(funcXY);
	},
	disableCoordinateSelectionMode: function () {
		if (!window.api) {
			return;
		}
		window.api.disableCoordinateSelectionMode();
	},
	getLocationByCoordinates: function (lat, lon, func) {
		if (!window.api) {
			return;
		}
		window.api.getLocationByCoordinates(lat, lon, func);
	},
	getCoordinatesByLocation: function (address, func) {
		if (!window.api) {
			return;
		}
		window.api.getCoordinatesByLocation(address, func);
	},
	addMarkerPopUp: function (markerId, htmlTemplate) {
		if (!window.api || !window.api.addPopup) {
			return;
		}
		window.api.addPopup(markerId, function (markerId) { return htmlTemplate });
	},
	createMarkerGroup: function(markers, options) {
		if (!window.api) {
			return;
		}
		if (!markers || markers.length == 0) {
			return;
		}
		options = options || this.defaultOptions;
		return window.api.createMarkerGroup(markers, options);
	},
	zoomTo: function(markerId) {
		if (!window.api) {
			return;
		}
		return window.api.zoomTo(markerId);
	},
	markAsSelected: function(markerId, selected) { 
		if (!window.api) {
			return;
		}
		return window.api.markAsSelected(markerId, selected);
	},
	refreshMarkerGroup: function(markerGroupId) {
		if (!window.api) {
			return;
		}
		return window.api.refreshMarkerCluster(markerGroupId);
	},
	changeMarkerSelection: function(markerId, selected, iconPath) {
		if (!window.api) {
			return;
		}
		this.setMarkerIcon(markerId, iconPath);
		if (selected) {
			this.zoomTo(markerId);
		}
		this.markAsSelected(markerId, selected);
	}
};