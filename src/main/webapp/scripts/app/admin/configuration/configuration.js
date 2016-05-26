'use strict';

angular.module('hrportalApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('configuration', {
                parent: 'admin',
                url: '/configuration',
                data: {
                    authorities: ['ROLE_SYS_ADMIN'],
                    pageTitle: 'Configuration'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/admin/configuration/configuration.html',
                        controller: 'ConfigurationController'
                    }
                },
                resolve: {

                }
            });
    });
