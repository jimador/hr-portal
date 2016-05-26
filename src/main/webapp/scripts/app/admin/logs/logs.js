'use strict';

angular.module('hrportalApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('logs', {
                parent: 'admin',
                url: '/logs',
                data: {
                    authorities: ['ROLE_SYS_ADMIN'],
                    pageTitle: 'Logs'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/admin/logs/logs.html',
                        controller: 'LogsController'
                    }
                },
                resolve: {

                }
            });
    });
