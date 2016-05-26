'use strict';

angular.module('hrportalApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('settings', {
                parent: 'account',
                url: '/settings',
                data: {
                    authorities: ['ROLE_USER', 'ROLE_SYS_ADMIN'],
                    pageTitle: 'Settings'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/account/settings/settings.html',
                        controller: 'SettingsController'
                    }
                },
                resolve: {

                }
            });
    });
