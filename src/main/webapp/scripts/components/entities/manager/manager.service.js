'use strict';

angular.module('hrportalApp')
    .factory('Manager', function ($resource, DateUtils) {
        return $resource('api/managers/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
