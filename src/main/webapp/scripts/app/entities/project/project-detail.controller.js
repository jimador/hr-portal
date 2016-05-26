'use strict';

angular.module('hrportalApp')
    .controller('ProjectDetailController', function ($scope, $rootScope, $stateParams, entity, Project) {
        $scope.project = entity;
        $scope.load = function (id) {
            Project.get({id: id}, function(result) {
                $scope.project = result;
            });
        };
        var unsubscribe = $rootScope.$on('hrportalApp:projectUpdate', function(event, result) {
            $scope.project = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
