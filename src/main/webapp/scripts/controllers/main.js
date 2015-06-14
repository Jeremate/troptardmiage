'use strict';

ttmApp.controller('MainCtrl', [
	'$scope', '$window', '$state', 'openDataApi', 'ttmStorageApi', function($scope, $window, $state, openDataApi, ttmStorageApi){

		//variables
		ttmStorageApi.isBackendReady = false;
		$scope.user = {};
		$scope.signedIn = false;
		$scope.themes = [];
		$scope.selectedThemes = [];
		$scope.events = [];
		$scope.losers = [];

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
					$scope.myUser();
					$scope.loadODThemes();
					$scope.loadLosers();
					//TODO : ajouter les thèmes du user au $scope.selectedThemes
				  // User is signed in, redirect to events
				  	$state.go("events");
				} else {
					//User is not signed in
					console.log("User not signed in");
				}
			});
	    }

	    $scope.auth = function() {
	    	if(!$scope.signedIn) {
	    		signin(false, userAuthed);
	    	} else {
	    		$scope.signedIn = false;
	    		$state.go("welcome");
	    	}
	    }

	    $scope.myUser = function() {
	    	console.log("myUser");
	    	ttmStorageApi.createUser(function(res) {
	    		if(!res.code) {
	    			$scope.user = res.result;
	    		} else {
	    			console.log(res);
	    		}
	    	});
	    }

	    // --------------- end authentication part ---------------

	    /**
	     * Load Open Data categories for the region Loire Altlantique
	     */
	    $scope.loadODThemes = function() {
	    	if($scope.themes.length == 0) {
		    	console.log("loading open data themes");
		    	openDataApi.themes(function(data) {
		    		// console.log(data);
		    		$scope.themes = data;
		    	});
		    }
	    }

	    $scope.loadODEvents = function(themeId) {
	    	console.log("loading open data events");
	    	openDataApi.events(themeId, 1, function(data) {
	    		console.log(data);
	    		$scope.events = $scope.events.concat(data.data);
	    	});
	    }

	    $scope.subscribe = function(event) {
	    	console.log("subscribe");
	    	ttmStorageApi.subscribe(event, function(res) {
	    		if(!res.code) {
	    			$scope.user = res.result;
	    		} else {
	    			console.log(res);
	    		}
	    	})
	    }

	    $scope.unsubscribe = function(event) {
	    	console.log("unsubscribe");
	    	ttmStorageApi.unsubscribe(event, function(res) {
	    		console.log(res);
	    		if(!res.code) {}
	    		$scope.user = res.result;
	    	})
	    }

	    $scope.loadLosers = function() {
	    	console.log("loadLosers");
	    	ttmStorageApi.losers(function(res) {
	    		if(!res.code) {
	    			console.log("getting losers");
	    			// $scope.losers = res.items;
	    			$scope.losers = res.result;
	    		} else {
	    			console.log(res);
	    		}
	    	});
	    }


	    // ---------------------------------------------------------
	    // --------------- Part on selected themes -----------------
	    // ---------------------------------------------------------

	    $scope.manageSelectedThemes = function(theme) {
	    	var index = $scope.selectedThemes.indexOf(theme);
	    	if(index != -1) {
	    		console.log("removing selected theme");
	    		$scope.selectedThemes.splice(index, 1);
	    		removeUserTheme(theme);
	    	} else {
	    		console.log("adding selected theme");
	    		$scope.selectedThemes.push(theme);
	    		addUserTheme(theme);
	    	}
	    }

	    $scope.cancelSelectedThemes = function() {
	    	console.log("cancelSelectedThemes");
	    	console.log($scope.user);
	    	console.log($scope.user.hasOwnProperty("themes"));
	    	if($scope.user.hasOwnProperty("themes")) {
	    		$scope.selectedThemes = $scope.user.themes;
	    	} else {
	    		$scope.selectedThemes = [];
	    	}
	    }

	    $scope.validateSelectedThemes = function() {
	    	console.log("validateSelectedThemes");
	    	// console.log($scope.user);
	    	$scope.events = [];
	    	angular.forEach($scope.selectedThemes, function(value, key){
	    		$scope.loadODEvents(value.id);
	    	});
	    }

	    var addUserTheme = function(theme) {
	    	console.log("addUserTheme");
	    	ttmStorageApi.addUserTheme(theme, function(res) {
	    		if(!res.code) {
	    			$scope.user = res.result;
	    			// $scope.selectedThemes = $scope.user.themes;
	    		} else {
	    			console.log(res);
	    		}
	    	});
	    }

	    var removeUserTheme = function(theme) {
	    	console.log("removeUserTheme");
	    	ttmStorageApi.removeUserTheme(theme, function(res) {
	    		if(!res.code) {
	    			$scope.user = res.result;
	    			// $scope.selectedThemes = $scope.user.themes;
	    		} else {
	    			console.log(res);
	    		}
	    	});
	    }
	}]);