'use strict';

angular.module('hrportalApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('metrics', {
                parent: 'admin',
                url: '/metrics',
                data: {
                    authorities: ['ROLE_SYS_ADMIN'],
                    pageTitle: 'Application Metrics'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/admin/metrics/metrics.html',
                        controller: 'MetricsController'
                    }
                },
                resolve: {

                }
            });
    });
