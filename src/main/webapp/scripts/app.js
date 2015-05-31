'use strict';

var ttmApp = angular.module('troptardmiage', [
	'ui.bootstrap', 'ui.router'
	]);

ttmApp.config(function ($stateProvider, $urlRouterProvider) {
	$urlRouterProvider.otherwise('main');

	$stateProvider
		.state('main', {
			url: '/',
			views: {
				'': {
					templateUrl: 'views/main.html'
				},
				'navbar-main@': {
					templateUrl: 'views/navbarmain.html'
				}
			}
		})

		.state('events', {
			url: '/events',
			views: {
				'': { 
					templateUrl: 'views/events.html' 
				},
				'navbar-main@': {
					templateUrl: 'views/navbarmain.html'
				},
				'navbar-user@events': {
					templateUrl: 'views/navbaruser.html'
				}
			}
		});
});