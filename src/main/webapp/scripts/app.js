'use strict';

var ttmApp = angular.module('troptardmiage', [
    'ui.bootstrap', 'ui.router'
]);

ttmApp.config(function ($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('welcome');

    $stateProvider
    .state('welcome', {
       url: '/',
       views: {
         'welcome': {
            templateUrl: 'views/welcome.html'
         }
       }
    })

    .state('events', {
       url: '/events',
       views: {
            '': {
                templateUrl: 'views/events.html'
            },
            'navbar-user': {
                templateUrl: 'views/navbaruser.html'
            }
        }
    })

    .state('confirm-event', {
       url: '/events/{eventId}/confirm',
       views: {
            '': {
                templateUrl: 'views/confirmevent.html'
            },
            'navbar-back': {
                templateUrl: 'views/navbarback.html'
            }
        }
    });
});