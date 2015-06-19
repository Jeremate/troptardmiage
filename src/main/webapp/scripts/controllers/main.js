'use strict';

ttmApp.controller('MainCtrl', [
	'$scope', '$window', '$state', 'openDataApi', 'ttmStorageApi', function($scope, $window, $state, openDataApi, ttmStorageApi) {

		//variables
		ttmStorageApi.isBackendReady = false;
		$scope.user = {};// the user connected
		$scope.signedIn = false;
		$scope.themes = []; // themes from the API Loire Atlantique
		$scope.selectedThemes = []; // selected themes = preferences
		$scope.previousSelectedThemes = [];// themes that were selected before new change in preferences
		$scope.events = []; // events from the API Loire Altantique
		$scope.losers = []; // top losers

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
					$scope.userpicture = resp.picture;
					$scope.myUser();
					$scope.loadODThemes();
					$scope.loadLosers();
				  // User is signed in, redirect to events
				  	$state.go("events");
				  	// $scope.$apply();
				} else {
					//User is not signed in
					console.log("User not signed in");
					$state.go("welcome");
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


	    // ---------------------------------------------------------
	    // --------------- Part on events --------------------------
	    // ---------------------------------------------------------

		/**
	     * Load Open Data events with detail for the region Loire Altlantique
	     */
	    $scope.loadODEvents = function(themeId) {
	    	console.log("loading open data events");
	    	openDataApi.events(themeId, 1, function(data) {
	    		// console.log(data);
	    		angular.forEach(data.data, function(event, key){
	    			openDataApi.eventDetailed(event.eventId, function(data) {
	    				// console.log(data);
	    				$scope.events.push(data);
	    				if($scope.isEventSubscribed(data.eventId)) {
	    					// console.log(data);
	    					data.subscribed = true;
	    				}
	    			});
	    		});
	    	});
	    }

	    $scope.subscribe = function(event) {
	    	console.log("subscribe");
	    	// need a hack to select the right category/theme
	    	chooseRightTheme(event);
	    	ttmStorageApi.subscribe(event, function(res) {
	    		if(!res.code) {
	    			$scope.user = res.result;
	    		} else {
	    			console.log(res);
	    		}
	    	});
	    	event.subscribed = true;
	    }

	    $scope.unsubscribe = function(event) {
	    	console.log("unsubscribe");
	    	ttmStorageApi.unsubscribe(event, function(res) {
	    		if(!res.code) {
	    			$scope.user = res.result;
	    		} else {
	    			console.log(res);
	    		}
	    	});
	    	event.subscribed = false;
	    }

	    $scope.isEventSubscribed = function(eventId) {
	    	var match = false;
	    	if($scope.user.hasOwnProperty("subscriptions")) {
	    		var userEventIds = Object.keys($scope.user.subscriptions);
	    		if($.inArray(eventId, userEventIds, 0) >= 0) {
	    			match = true;
	    		}
	    	}
	    	return match;
	    }

	    $scope.preSelectEvents = function() {
	    	if($scope.user.hasOwnProperty("subscriptions")) {
	    		console.log("yes");
	    		var userEventIds = Object.keys($scope.user.subscriptions);
	    		angular.forEach($scope.events, function(event, key) {
	    			if($.inArray(event.eventId, userEventIds, 0)) {
	    				console.log(event);
	    				event.subscribed = true;
	    			}
	    		});
	    	}
	    }

	    $scope.confirmEvent = function(event) {
	    	console.log("confirmEvent");
	    	ttmStorageApi.confirmEvent(event, function(res) {
	    		if(!res.code) {
	    			console.log("event confirmed");
	    			$scope.user = res.result;
	    		} else {
					console.log(res);
	    		}
	    	});
	    }

	    var chooseRightTheme = function(event) {
	    	angular.forEach(event.category, function(theme, key){
	    		//same order in user.themes than selectedThemes
	    		var index = $.inArray(theme.id, $scope.user.themes, 0);
	    		if(index >= 0) {
	    			//should always be >= 0
	    			event.category.push($scope.selectedThemes[index]);
	    		}
	    	});
	    }


	    // ---------------------------------------------------------
	    // --------------- Part on themes --------------------------
	    // ---------------------------------------------------------

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
	    	theme.selected = !theme.selected;
	    }

	    $scope.cancelSelectedThemes = function() {
	    	console.log("cancelSelectedThemes");
	    	angular.forEach($scope.selectedThemes, function(value, key){
	    		removeUserTheme(value);
	    	});
	    	angular.forEach($scope.previousSelectedThemes, function(value, key){
	    		addUserTheme(value);
	    	});
	    	$scope.preSelectThemes();
	    }

	    $scope.validateSelectedThemes = function() {
	    	console.log("validateSelectedThemes");
	    	// console.log($scope.user);
	    	$scope.events = [];
	    	$scope.previousSelectedThemes = [];
	    	angular.forEach($scope.selectedThemes, function(value, key){
	    		$scope.previousSelectedThemes.push(value);
	    		$scope.loadODEvents(value.id);
	    	});
	    }

	    $scope.preSelectThemes = function() {
	    	$scope.selectedThemes = [];
	    	if($scope.user.hasOwnProperty("themes")) {
		    	angular.forEach($scope.themes, function(themeMere, key){
		    		angular.forEach(themeMere.children, function(theme, key){
		    			angular.forEach($scope.user.themes, function(userThemeId, key){
			    			// console.log(userThemeId+" == "+theme.id+" : ");
			    			// console.log(userThemeId == theme.id);
			    			if(userThemeId == theme.id) {
			    				theme.selected = true;
			    				$scope.selectedThemes.push(theme);
			    			}
			    		});
		    		});
		    	});
		    }
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

	    // ---------------------------------------------------------
	    // --------------- Part on losers --------------------------
	    // ---------------------------------------------------------

	    $scope.loadLosers = function() {
	    	console.log("loadLosers");
	    	$scope.losers = [];
	    	ttmStorageApi.losers(function(res) {
	    		if(!res.code) {
	    			console.log("getting losers");
	    			// console.log(res);
	    			angular.forEach(res.result, function(value, key){
	    				if(angular.isNumber(value)) {
	    					$scope.losers.push({
	    						name: key,
	    						percentage: value
	    					});
	    				}
	    			});
	    			// $scope.losers = res.result;
	    			// $scope.$apply();
	    		} else {
	    			console.log(res);
	    		}
	    	});
	    }

	}]);
