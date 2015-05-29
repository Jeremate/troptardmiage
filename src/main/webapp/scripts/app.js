'use strict';

angular.module('troptardmiage', [
	'ngRoute'
	])
	.config(function ($routeProvider) {
		$routeProvider
			.when('/main', {
				templateUrl: '../views/main.html',
				controller: 'MainCtrl'
			})
			.otherwise({
				redirectTo: '/main'
			});
	});