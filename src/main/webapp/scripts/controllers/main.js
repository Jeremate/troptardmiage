'use strict';

	ttmApp.controller('MainCtrl', ['$scope', '$window', '$state', function($scope, $window, $state){
		$scope.isBackendReady = false;
		
		var CLIENT_ID = "679411653009-62udgm0l3010dqbhon9lrff7pcqldrg9.apps.googleusercontent.com";
		var SCOPES = "https://www.googleapis.com/auth/userinfo.email";
		$scope.signedIn = false;
		/**
	     * Ajout pour fonctionner avec Google Cloud Endpoint
	     * Fonction interceptant l'appel à window.init() effectué dans index.html
	     */
	    $window.init = function() {
	    	console.log("$window.init called");

			var apisToLoad;
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
	}]);