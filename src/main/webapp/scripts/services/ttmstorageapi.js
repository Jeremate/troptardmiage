'use strict';
//TODO: use callback

ttmApp.factory('ttmStorageApi', ['$http', function($http){
	var NOT_READY_MESSAGE = "troptardmiage storage api is not ready";

	return {
		isBackendReady: false,

		losers: function(callback) {
			if (!this.isBackendReady) {
				console.log(NOT_READY_MESSAGE);
				return;
			}
			gapi.client.troptardmiage.users.losers().execute(callback);
		},

		getUser: function(userId, callback) {
			if (!this.isBackendReady) {
				console.log(NOT_READY_MESSAGE);
				return;
			}
			gapi.client.troptardmiage.users.get({"userId": userId}).execute(callback);
		},

		createUser: function(callback) {
			if (!this.isBackendReady) {
				console.log(NOT_READY_MESSAGE);
				return;
			}
			gapi.client.troptardmiage.users.create().execute(callback);
		},

		getUserThemes: function(callback) {
			if (!this.isBackendReady) {
				console.log(NOT_READY_MESSAGE);
				return;
			}
			gapi.client.troptardmiage.users.themes().execute(callback);
		},

		addUserTheme: function(theme, callback) {
			if (!this.isBackendReady) {
				console.log(NOT_READY_MESSAGE);
				return;
			}
			gapi.client.troptardmiage.users.addTheme(theme).execute(callback);
		},

		subscribe: function(event, callback) {
			if (!this.isBackendReady) {
				console.log(NOT_READY_MESSAGE);
				return;
			}
			event.cityName = event.city.title;
			event.themeId = event.category[0].id;
			gapi.client.troptardmiage.users.subscribe(event).execute(callback);
		},

		unsubscribe: function(event, callback) {
			if (!this.isBackendReady) {
				console.log(NOT_READY_MESSAGE);
				return;
			}
			gapi.client.troptardmiage.users.unsubscribe(event).execute(callback);
		}

		confirmEvent: function(event, callback) {
			if (!this.isBackendReady) {
				console.log(NOT_READY_MESSAGE);
				return;
			}
			gapi.client.troptardmiage.users.confirmEvent(event).execute(callback);
		}

	};
}])