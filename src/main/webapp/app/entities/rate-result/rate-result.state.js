(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('rate-result', {
            parent: 'entity',
            url: '/rate-result',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bankAuditApp.rateResult.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/rate-result/rate-results.html',
                    controller: 'RateResultController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('rateResult');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('rate-result-detail', {
            parent: 'entity',
            url: '/rate-result/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bankAuditApp.rateResult.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/rate-result/rate-result-detail.html',
                    controller: 'RateResultDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('rateResult');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'RateResult', function($stateParams, RateResult) {
                    return RateResult.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'rate-result',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('rate-result-detail.edit', {
            parent: 'rate-result-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rate-result/rate-result-dialog.html',
                    controller: 'RateResultDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RateResult', function(RateResult) {
                            return RateResult.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('rate-result.new', {
            parent: 'rate-result',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rate-result/rate-result-dialog.html',
                    controller: 'RateResultDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('rate-result', null, { reload: 'rate-result' });
                }, function() {
                    $state.go('rate-result');
                });
            }]
        })
        .state('rate-result.edit', {
            parent: 'rate-result',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rate-result/rate-result-dialog.html',
                    controller: 'RateResultDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RateResult', function(RateResult) {
                            return RateResult.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('rate-result', null, { reload: 'rate-result' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('rate-result.delete', {
            parent: 'rate-result',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rate-result/rate-result-delete-dialog.html',
                    controller: 'RateResultDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['RateResult', function(RateResult) {
                            return RateResult.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('rate-result', null, { reload: 'rate-result' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
