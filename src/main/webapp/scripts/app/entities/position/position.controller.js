'use strict';

angular.module('hrportalApp')
    .controller('PositionController', function ($scope, $state, Position, ParseLinks) {

        $scope.positions = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 0;
        $scope.loadAll = function() {
            Position.query({page: $scope.page, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.positions.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 0;
            $scope.positions = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.reset();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.position = {
                laborCategory: null,
                salary: null,
                id: null,
                positionName: null
            };
        };
    });
