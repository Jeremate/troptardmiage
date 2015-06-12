'use strict';
//TODO: use callback

ttmApp.factory('ttmStorageApi', ['$http', function($http){
	var NOT_READY_MESSAGE = "troptardmiage storage api is not ready"

	return {
		isBackendReady: false,

		losers: function() {
			if (!this.isBackendReady) {
				console.log(NOT_READY_MESSAGE);
				return;
			}
			return gapi.client.troptardmiage.users.losers();
		},

		getUser: function() {
			if (!this.isBackendReady) {
				console.log(NOT_READY_MESSAGE);
				return;
			}
			return gapi.client.troptardmiage.users.get();
		},

		createUser: function() {
			if (!this.isBackendReady) {
				console.log(NOT_READY_MESSAGE);
				return;
			}
			return gapi.client.troptardmiage.users.create();
		},

		getUserThemes: function() {
			if (!this.isBackendReady) {
				console.log(NOT_READY_MESSAGE);
				return;
			}
			return gapi.client.troptardmiage.users.themes();
		}

	};
}])