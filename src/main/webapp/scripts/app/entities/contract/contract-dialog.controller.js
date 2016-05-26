'use strict';

angular.module('hrportalApp').controller('ContractDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Contract',
        function($scope, $stateParams, $uibModalInstance, entity, Contract) {

        $scope.contract = entity;
        $scope.load = function(id) {
            Contract.get({id : id}, function(result) {
                $scope.contract = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hrportalApp:contractUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.contract.id != null) {
                Contract.update($scope.contract, onSaveSuccess, onSaveError);
            } else {
                Contract.save($scope.contract, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
