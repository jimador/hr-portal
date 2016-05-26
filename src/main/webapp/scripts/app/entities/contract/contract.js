'use strict';

angular.module('hrportalApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('contract', {
                parent: 'entity',
                url: '/contracts',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Contracts'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/contract/contracts.html',
                        controller: 'ContractController'
                    }
                },
                resolve: {
                }
            })
            .state('contract.detail', {
                parent: 'entity',
                url: '/contract/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Contract'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/contract/contract-detail.html',
                        controller: 'ContractDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Contract', function($stateParams, Contract) {
                        return Contract.get({id : $stateParams.id});
                    }]
                }
            })
            .state('contract.new', {
                parent: 'contract',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/contract/contract-dialog.html',
                        controller: 'ContractDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    contractNumber: null,
                                    contractLaborCategory: null,
                                    contractName: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('contract', null, { reload: true });
                    }, function() {
                        $state.go('contract');
                    })
                }]
            })
            .state('contract.edit', {
                parent: 'contract',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/contract/contract-dialog.html',
                        controller: 'ContractDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Contract', function(Contract) {
                                return Contract.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('contract', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('contract.delete', {
                parent: 'contract',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/contract/contract-delete-dialog.html',
                        controller: 'ContractDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Contract', function(Contract) {
                                return Contract.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('contract', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
