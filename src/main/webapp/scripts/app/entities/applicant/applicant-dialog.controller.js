'use strict';

angular.module('hrportalApp').controller('ApplicantDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Applicant', 'Position', 'Manager', 'Contract', 'Project',
        function ($scope, $stateParams, $uibModalInstance, entity, Applicant, Position, Manager, Contract, Project) {

            $scope.applicant = entity;
            $scope.positions = [];
            $scope.managers = [];
            $scope.contracts = [];
            $scope.projects = [];

            $scope.load = function (id) {
                Applicant.get({id: id}, function (result) {
                    $scope.applicant = result;
                });
            };

            var loadDataSources = function (){
                Position.query({},function(result) {
                    for (var i = 0; i < result.length; i++) {
                        $scope.positions.push(result[i]);
                    }
                });

                Contract.query({},function(result) {
                    for (var i = 0; i < result.length; i++) {
                        $scope.contracts.push(result[i]);
                    }
                });

                Manager.query({},function(result) {
                    for (var i = 0; i < result.length; i++) {
                        $scope.managers.push(result[i]);
                    }
                });

                Project.query({},function(result) {
                    for (var i = 0; i < result.length; i++) {
                        $scope.projects.push(result[i]);
                    }
                });
            };

            loadDataSources();

            var onSaveSuccess = function (result) {
                $scope.$emit('hrportalApp:applicantUpdate', result);
                $uibModalInstance.close(result);
                $scope.isSaving = false;
            };

            var onSaveError = function (result) {
                $scope.isSaving = false;
            };

            $scope.save = function () {
                $scope.isSaving = true;
                if ($scope.applicant.id != null) {
                    Applicant.update($scope.applicant, onSaveSuccess, onSaveError);
                } else {
                    Applicant.save($scope.applicant, onSaveSuccess, onSaveError);
                }
            };

            $scope.clear = function () {
                $uibModalInstance.dismiss('cancel');
            };
            $scope.datePickerForOfferDate = {};

            $scope.datePickerForOfferDate.status = {
                opened: false
            };

            $scope.datePickerForOfferDateOpen = function ($event) {
                $scope.datePickerForOfferDate.status.opened = true;
            };
        }]);
