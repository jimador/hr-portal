'use strict';

angular.module('hrportalApp')
	.controller('ManagerDeleteController', function($scope, $uibModalInstance, entity, Manager) {

        $scope.manager = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Manager.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
