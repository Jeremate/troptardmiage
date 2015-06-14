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
       url: '/confirm-event',
       views: {
            '': {
                templateUrl: 'views/confirm-event.html'
            },
            'navbar-user': {
                templateUrl: 'views/navbaruser.html'
            }
        }
    });
});