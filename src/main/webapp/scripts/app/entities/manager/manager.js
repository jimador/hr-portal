'use strict';

angular.module('hrportalApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('manager', {
                parent: 'entity',
                url: '/managers',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Managers'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/manager/managers.html',
                        controller: 'ManagerController'
                    }
                },
                resolve: {
                }
            })
            .state('manager.detail', {
                parent: 'entity',
                url: '/manager/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Manager'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/manager/manager-detail.html',
                        controller: 'ManagerDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Manager', function($stateParams, Manager) {
                        return Manager.get({id : $stateParams.id});
                    }]
                }
            })
            .state('manager.new', {
                parent: 'manager',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/manager/manager-dialog.html',
                        controller: 'ManagerDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    firstName: null,
                                    lastName: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('manager', null, { reload: true });
                    }, function() {
                        $state.go('manager');
                    })
                }]
            })
            .state('manager.edit', {
                parent: 'manager',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/manager/manager-dialog.html',
                        controller: 'ManagerDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Manager', function(Manager) {
                                return Manager.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('manager', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('manager.delete', {
                parent: 'manager',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/manager/manager-delete-dialog.html',
                        controller: 'ManagerDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Manager', function(Manager) {
                                return Manager.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('manager', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
