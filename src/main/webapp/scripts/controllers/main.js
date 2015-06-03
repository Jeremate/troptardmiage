'use strict';

	ttmApp.controller('MainCtrl', [
		'$scope', '$window', '$state', '$http', 'openDataApi', function($scope, $window, $state, $http, openDataApi){
		$scope.isBackendReady = false;
		$scope.signedIn = false;

		// Constantes pour GAPI
		var CLIENT_ID = "679411653009-62udgm0l3010dqbhon9lrff7pcqldrg9.apps.googleusercontent.com";
		var SCOPES = "https://www.googleapis.com/auth/userinfo.email";
		// var CODE = "4/Torf9JEYnZN3mtQnTHuBttCm1tzr312ttWe1tE-8c4A.0pQxAkvoZ4geWmFiZwPfH00LLHlxmwI";
		
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
		    	
		    	var rootApi = $window.location.origin + '/_ah/api';//TODO : vérifier si en prod ok sinon changer vers troptardmiage.appspot.com
		        gapi.client.load('troptardmiage', 'v1', function() {
		            console.log("troptardmiage api loaded");
		            //le flag isBackendReady permet d'éviter d'appeler gapi avant qu'elle soit chargée
		            $scope.isBackendReady = true;
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
				  // User is signed in, redirect to events
				  	$state.go("events");
				} else {
					//User is not signed in, redirect to main
					console.log("User not signed in");
					$state.go("main");
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
	    $scope.loadODCategories = function() {
	    	console.log("loading categories");
	    	openDataApi.categories().success(function(data) {
		    		openDataApi.events(data[0].id, 1).success(function(data2) {
		    			console.log(data2);
		    		});
		    	});
	    }

	}]);