(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('final-rate', {
            parent: 'entity',
            url: '/final-rate',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bankAuditApp.finalRate.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/final-rate/final-rates.html',
                    controller: 'FinalRateController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('finalRate');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('final-rate-detail', {
            parent: 'entity',
            url: '/final-rate/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bankAuditApp.finalRate.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/final-rate/final-rate-detail.html',
                    controller: 'FinalRateDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('finalRate');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'FinalRate', function($stateParams, FinalRate) {
                    return FinalRate.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'final-rate',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('final-rate-detail.edit', {
            parent: 'final-rate-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/final-rate/final-rate-dialog.html',
                    controller: 'FinalRateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FinalRate', function(FinalRate) {
                            return FinalRate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('final-rate.new', {
            parent: 'final-rate',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/final-rate/final-rate-dialog.html',
                    controller: 'FinalRateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                evoopd: null,
                                ev1Ozpd: null,
                                evmb: null,
                                r: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('final-rate', null, { reload: 'final-rate' });
                }, function() {
                    $state.go('final-rate');
                });
            }]
        })
        .state('final-rate.edit', {
            parent: 'final-rate',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/final-rate/final-rate-dialog.html',
                    controller: 'FinalRateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FinalRate', function(FinalRate) {
                            return FinalRate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('final-rate', null, { reload: 'final-rate' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('final-rate.delete', {
            parent: 'final-rate',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/final-rate/final-rate-delete-dialog.html',
                    controller: 'FinalRateDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['FinalRate', function(FinalRate) {
                            return FinalRate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('final-rate', null, { reload: 'final-rate' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
