'use strict';

angular.module('hrportalApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


