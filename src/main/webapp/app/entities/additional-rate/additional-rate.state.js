(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('additional-rate', {
            parent: 'entity',
            url: '/additional-rate',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bankAuditApp.additionalRate.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/additional-rate/additional-rates.html',
                    controller: 'AdditionalRateController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('additionalRate');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('additional-rate-detail', {
            parent: 'entity',
            url: '/additional-rate/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bankAuditApp.additionalRate.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/additional-rate/additional-rate-detail.html',
                    controller: 'AdditionalRateDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('additionalRate');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'AdditionalRate', function($stateParams, AdditionalRate) {
                    return AdditionalRate.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'additional-rate',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('additional-rate-detail.edit', {
            parent: 'additional-rate-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/additional-rate/additional-rate-dialog.html',
                    controller: 'AdditionalRateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AdditionalRate', function(AdditionalRate) {
                            return AdditionalRate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('additional-rate.new', {
            parent: 'additional-rate',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/additional-rate/additional-rate-dialog.html',
                    controller: 'AdditionalRateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                ev1: null,
                                ev2: null,
                                ev3: null,
                                evbptp: null,
                                evbitp: null,
                                ev1Ozpd: null,
                                ev2Ozpd: null,
                                evoopd: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('additional-rate', null, { reload: 'additional-rate' });
                }, function() {
                    $state.go('additional-rate');
                });
            }]
        })
        .state('additional-rate.edit', {
            parent: 'additional-rate',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/additional-rate/additional-rate-dialog.html',
                    controller: 'AdditionalRateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AdditionalRate', function(AdditionalRate) {
                            return AdditionalRate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('additional-rate', null, { reload: 'additional-rate' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('additional-rate.delete', {
            parent: 'additional-rate',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/additional-rate/additional-rate-delete-dialog.html',
                    controller: 'AdditionalRateDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['AdditionalRate', function(AdditionalRate) {
                            return AdditionalRate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('additional-rate', null, { reload: 'additional-rate' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
