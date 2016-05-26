'use strict';

angular.module('hrportalApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('applicant', {
                parent: 'entity',
                url: '/applicants',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Applicants'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/applicant/applicants.html',
                        controller: 'ApplicantController'
                    }
                },
                resolve: {
                }
            })
            .state('applicant.detail', {
                parent: 'entity',
                url: '/applicant/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Applicant'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/applicant/applicant-detail.html',
                        controller: 'ApplicantDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Applicant', function($stateParams, Applicant) {
                        return Applicant.get({id : $stateParams.id});
                    }]
                }
            })
            .state('applicant.new', {
                parent: 'applicant',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/applicant/applicant-dialog.html',
                        controller: 'ApplicantDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
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
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('applicant', null, { reload: true });
                    }, function() {
                        $state.go('applicant');
                    })
                }]
            })
            .state('applicant.edit', {
                parent: 'applicant',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/applicant/applicant-dialog.html',
                        controller: 'ApplicantDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Applicant', function(Applicant) {
                                return Applicant.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('applicant', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('applicant.delete', {
                parent: 'applicant',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/applicant/applicant-delete-dialog.html',
                        controller: 'ApplicantDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Applicant', function(Applicant) {
                                return Applicant.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('applicant', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
