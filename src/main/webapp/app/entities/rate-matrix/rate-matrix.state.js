(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('rate-matrix', {
            parent: 'entity',
            url: '/rate-matrix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bankAuditApp.rateMatrix.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/rate-matrix/rate-matrices.html',
                    controller: 'RateMatrixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('rateMatrix');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('rate-matrix-detail', {
            parent: 'entity',
            url: '/rate-matrix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bankAuditApp.rateMatrix.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/rate-matrix/rate-matrix-detail.html',
                    controller: 'RateMatrixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('rateMatrix');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'RateMatrix', function($stateParams, RateMatrix) {
                    return RateMatrix.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'rate-matrix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('rate-matrix-detail.edit', {
            parent: 'rate-matrix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rate-matrix/rate-matrix-dialog.html',
                    controller: 'RateMatrixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RateMatrix', function(RateMatrix) {
                            return RateMatrix.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('rate-matrix.new', {
            parent: 'rate-matrix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rate-matrix/rate-matrix-dialog.html',
                    controller: 'RateMatrixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                indexRate: null,
                                rate: null,
                                zero: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('rate-matrix', null, { reload: 'rate-matrix' });
                }, function() {
                    $state.go('rate-matrix');
                });
            }]
        })
        .state('rate-matrix.edit', {
            parent: 'rate-matrix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rate-matrix/rate-matrix-dialog.html',
                    controller: 'RateMatrixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RateMatrix', function(RateMatrix) {
                            return RateMatrix.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('rate-matrix', null, { reload: 'rate-matrix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('rate-matrix.delete', {
            parent: 'rate-matrix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rate-matrix/rate-matrix-delete-dialog.html',
                    controller: 'RateMatrixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['RateMatrix', function(RateMatrix) {
                            return RateMatrix.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('rate-matrix', null, { reload: 'rate-matrix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
