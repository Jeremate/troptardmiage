'use strict';

ttmApp.controller('MainCtrl', [
	'$scope', '$window', '$state', 'openDataApi', 'ttmStorageApi', function($scope, $window, $state, openDataApi, ttmStorageApi){
		ttmStorageApi.isBackendReady = false;
		$scope.signedIn = false;

		// Constantes pour GAPI
		var CLIENT_ID = "679411653009-62udgm0l3010dqbhon9lrff7pcqldrg9.apps.googleusercontent.com";
		var SCOPES = "https://www.googleapis.com/auth/userinfo.email";

		/**
	     * Ajout pour fonctionner avec Google Cloud Endpoint
	     * Fonction interceptant l'appel à window.init() effectué dans index.html
	     */
	    $window.init = function() {
	    	console.log("$window.init called");

			var apisToLoad;

			/**
			 * Déclenche la fonction pour s'authentifier automatiquement après chargement de toutes les APIs Google
			 */
			var loadCallback = function() {
				if (--apisToLoad == 0) {
                    signin(true, userAuthed);
				}
			};
			/**
		     * Charge l'api troptardmiage
		     */
			var load_gapi_lib = function() {
		    	console.log("load_gapi_lib called");

		    	var rootApi = $window.location.origin + '/_ah/api';
                gapi.client.load('troptardmiage', 'v1', function() {
                    console.log("troptardmiage api loaded");
                    //le flag isBackendReady permet d'éviter d'appeler gapi avant qu'elle soit chargée
                    ttmStorageApi.isBackendReady = true;
                    loadCallback();
                }, rootApi);
		    };

			apisToLoad = 2; // must match number of calls to gapi.client.load()

	    	$scope.$apply(load_gapi_lib);
			gapi.client.load('oauth2', 'v2', loadCallback);
		};

		function signin(mode, authorizeCallback) {
			gapi.auth.authorize(
				{
					client_id: CLIENT_ID,
					scope: SCOPES,
					immediate: mode
				},
				authorizeCallback);
		}

		function userAuthed() {
			var request = gapi.client.oauth2.userinfo.get().execute(function(resp) {
				console.log(resp);
				if (!resp.code) {
					console.log("authentification réussie");
					$scope.signedIn = true;
					$scope.newUser();
				  // User is signed in, redirect to events
				  	$state.go("events");
				} else {
					//User is not signed in, redirect to main
					console.log("User not signed in");
					// $state.go("main");
				}
			});
	    }

	    $scope.auth = function() {
	    	if(!$scope.signedIn) {
	    		signin(false, userAuthed);
	    	} else {
	    		$scope.signedIn = false;
	    		$state.go("main");
	    	}
	    }

	    /**
	     * Load Open Data categories for the region Loire Altlantique
	     */
	    $scope.loadODThemes = function() {
	    	console.log("loading open data themes");
	    	openDataApi.themes(function(data) {
	    		console.log(data);
	    		$scope.themes = data;
	    	});
	    }

	    $scope.loadODEvents = function(themeId) {
	    	console.log("loading open data events");
	    	openDataApi.events(themeId, 1, function(data) {
	    		console.log(data);
	    		$scope.events = data.data;
	    	});
	    }

	    $scope.loadLosers = function() {
	    	console.log("loadLosers");
	    	ttmStorageApi.losers(function(res) {
	    		console.log(res);
	    		if(!res.code) {
	    			console.log("getting losers");
	    			$scope.losers = res;
	    		}
	    	});
	    }

	    $scope.myUser = function() {
	    	console.log("myUser");
	    	ttmStorageApi.getUser("0", function(res) {
	    		console.log(res);
	    	});
	    }

	    $scope.newUser = function() {
	    	console.log("newUser");
	    	ttmStorageApi.createUser(function(res) {
	    		console.log(res);
	    		if(!res.code) {
	    			$scope.user = res.result;
	    		}
	    	});
	    }

	    $scope.myUserThemes = function() {
	    	console.log("myUserThemes");
	    	ttmStorageApi.getUserThemes(function(res) {
	    		console.log(res);
	    		if(!res.code) {}
	    	});
	    }

	    $scope.addUserTheme = function(theme) {
	    	console.log("addUserTheme");
	    	ttmStorageApi.addUserTheme(theme, function(res) {
	    		console.log(res);
	    		if(!res.code) {}
	    		// $scope.user = res.result;
	    	});
	    }

	    $scope.subscribe = function(event) {
	    	console.log("subscribe");
	    	ttmStorageApi.subscribe(event, function(res) {
	    		console.log(res);
	    		if(!res.code) {}
	    		// $scope.user = res.result;
	    	})
	    }

	    $scope.unsubscribe = function(event) {
	    	console.log("unsubscribe");
	    	ttmStorageApi.unsubscribe(event, function(res) {
	    		console.log(res);
	    		if(!res.code) {}
	    		// $scope.user = res.result;
	    	})
	    }
	}]);