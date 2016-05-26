'use strict';

angular.module('hrportalApp')
    .controller('ContractDetailController', function ($scope, $rootScope, $stateParams, entity, Contract) {
        $scope.contract = entity;
        $scope.load = function (id) {
            Contract.get({id: id}, function(result) {
                $scope.contract = result;
            });
        };
        var unsubscribe = $rootScope.$on('hrportalApp:contractUpdate', function(event, result) {
            $scope.contract = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
