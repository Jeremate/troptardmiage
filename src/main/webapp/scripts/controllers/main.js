'use strict';

angular.module('troptardmiage', [])
	.controller('MainCtrl', ['$scope', '$window', function($scope, $window){
		$scope.is_backend_ready = false;
		/**
	     * Ajout pour fonctionner avec Google Cloud Endpoint
	     * Fonction interceptant l'appel à window.init() effectué dans index.html
	     */
	    $window.init = function() {
	    	console.log("$window.init called");
	    	$scope.$apply($scope.load_gapi_lib);
	    };
	    /**
	     * Charge l'api todos
	     */
	    $scope.load_gapi_lib = function() {
	    	console.log("load_gapi_lib called");
	    	var rootApi = $window.location.origin + '_ah/api';//TODO : vérifier si en prod ok sinon changer vers troptardmiage.appspot.com
	        gapi.client.load('troptardmiage', 'v1', function() {
	            console.log("troptardmiage api loaded");
	            //le flag is_backend_ready permet d'éviter d'appeler gapi avant qu'elle soit chargée
	            $scope.is_backend_ready = true;
	            console.log("backend : " + $scope.is_backend_ready);//FAIL : doesn't log
	        }, rootApi);
	    };
	}]);