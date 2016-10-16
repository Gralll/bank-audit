(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('local-rate', {
            parent: 'entity',
            url: '/local-rate',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bankAuditApp.localRate.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/local-rate/local-rates.html',
                    controller: 'LocalRateController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('localRate');
                    $translatePartialLoader.addPart('checkCategory');
                    $translatePartialLoader.addPart('documentation');
                    $translatePartialLoader.addPart('execution');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('local-rate-detail', {
            parent: 'entity',
            url: '/local-rate/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bankAuditApp.localRate.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/local-rate/local-rate-detail.html',
                    controller: 'LocalRateDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('localRate');
                    $translatePartialLoader.addPart('checkCategory');
                    $translatePartialLoader.addPart('documentation');
                    $translatePartialLoader.addPart('execution');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'LocalRate', function($stateParams, LocalRate) {
                    return LocalRate.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'local-rate',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('local-rate-detail.edit', {
            parent: 'local-rate-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/local-rate/local-rate-dialog.html',
                    controller: 'LocalRateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LocalRate', function(LocalRate) {
                            return LocalRate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('local-rate.new', {
            parent: 'local-rate',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/local-rate/local-rate-dialog.html',
                    controller: 'LocalRateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                indexRate: null,
                                question: null,
                                necessary: false,
                                category: null,
                                documentation: null,
                                execution: null,
                                notRated: null,
                                rate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('local-rate', null, { reload: 'local-rate' });
                }, function() {
                    $state.go('local-rate');
                });
            }]
        })
        .state('local-rate.edit', {
            parent: 'local-rate',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/local-rate/local-rate-dialog.html',
                    controller: 'LocalRateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LocalRate', function(LocalRate) {
                            return LocalRate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('local-rate', null, { reload: 'local-rate' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('local-rate.delete', {
            parent: 'local-rate',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/local-rate/local-rate-delete-dialog.html',
                    controller: 'LocalRateDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LocalRate', function(LocalRate) {
                            return LocalRate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('local-rate', null, { reload: 'local-rate' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
