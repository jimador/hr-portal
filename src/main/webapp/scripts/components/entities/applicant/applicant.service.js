'use strict';

angular.module('hrportalApp')
    .factory('Applicant', function ($resource, DateUtils) {
        return $resource('api/applicants/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.offerDate = DateUtils.convertDateTimeFromServer(data.offerDate);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
