'use strict';

angular.module('hrportalApp')
    .controller('PositionDetailController', function ($scope, $rootScope, $stateParams, entity, Position) {
        $scope.position = entity;
        $scope.load = function (id) {
            Position.get({id: id}, function(result) {
                $scope.position = result;
            });
        };
        var unsubscribe = $rootScope.$on('hrportalApp:positionUpdate', function(event, result) {
            $scope.position = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
