'use strict';

angular.module('hrportalApp').controller('ManagerDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Manager',
        function($scope, $stateParams, $uibModalInstance, entity, Manager) {

        $scope.manager = entity;
        $scope.load = function(id) {
            Manager.get({id : id}, function(result) {
                $scope.manager = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hrportalApp:managerUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.manager.id != null) {
                Manager.update($scope.manager, onSaveSuccess, onSaveError);
            } else {
                Manager.save($scope.manager, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
