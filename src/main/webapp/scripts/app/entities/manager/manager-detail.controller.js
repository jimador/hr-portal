'use strict';

angular.module('hrportalApp')
    .controller('ManagerDetailController', function ($scope, $rootScope, $stateParams, entity, Manager) {
        $scope.manager = entity;
        $scope.load = function (id) {
            Manager.get({id: id}, function(result) {
                $scope.manager = result;
            });
        };
        var unsubscribe = $rootScope.$on('hrportalApp:managerUpdate', function(event, result) {
            $scope.manager = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
