 'use strict';

angular.module('hrportalApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-hrportalApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-hrportalApp-params')});
                }
                return response;
            }
        };
    });
