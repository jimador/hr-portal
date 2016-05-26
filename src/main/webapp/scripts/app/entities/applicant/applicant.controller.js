'use strict';

angular.module('hrportalApp')
    .controller('ApplicantController', function ($scope, $state, Applicant, ParseLinks) {

        $scope.applicants = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Applicant.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.applicants = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.applicant = {
                title: null,
                firstName: null,
                lastName: null,
                middleName: null,
                contactInfo: null,
                position: null,
                offerDate: null,
                suffix: null,
                id: null,
                zip: null,
                contract: null,
                project: null,
                manager: null
            };
        };
    });
