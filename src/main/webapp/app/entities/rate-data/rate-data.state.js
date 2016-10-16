(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('rate-data', {
            parent: 'entity',
            url: '/rate-data',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bankAuditApp.rateData.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/rate-data/rate-data.html',
                    controller: 'RateDataController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('rateData');
                    $translatePartialLoader.addPart('auditProgress');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('rate-data-detail', {
            parent: 'entity',
            url: '/rate-data/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bankAuditApp.rateData.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/rate-data/rate-data-detail.html',
                    controller: 'RateDataDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('rateData');
                    $translatePartialLoader.addPart('auditProgress');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'RateData', function($stateParams, RateData) {
                    return RateData.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'rate-data',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('rate-data-detail.edit', {
            parent: 'rate-data-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rate-data/rate-data-dialog.html',
                    controller: 'RateDataDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RateData', function(RateData) {
                            return RateData.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('rate-data.new', {
            parent: 'rate-data',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rate-data/rate-data-dialog.html',
                    controller: 'RateDataDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                progress: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('rate-data', null, { reload: 'rate-data' });
                }, function() {
                    $state.go('rate-data');
                });
            }]
        })
        .state('rate-data.edit', {
            parent: 'rate-data',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rate-data/rate-data-dialog.html',
                    controller: 'RateDataDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RateData', function(RateData) {
                            return RateData.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('rate-data', null, { reload: 'rate-data' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('rate-data.delete', {
            parent: 'rate-data',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rate-data/rate-data-delete-dialog.html',
                    controller: 'RateDataDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['RateData', function(RateData) {
                            return RateData.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('rate-data', null, { reload: 'rate-data' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
