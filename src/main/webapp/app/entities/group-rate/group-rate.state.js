(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('group-rate', {
            parent: 'entity',
            url: '/group-rate',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bankAuditApp.groupRate.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/group-rate/group-rates.html',
                    controller: 'GroupRateController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('groupRate');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('group-rate-detail', {
            parent: 'entity',
            url: '/group-rate/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bankAuditApp.groupRate.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/group-rate/group-rate-detail.html',
                    controller: 'GroupRateDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('groupRate');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'GroupRate', function($stateParams, GroupRate) {
                    return GroupRate.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'group-rate',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('group-rate-detail.edit', {
            parent: 'group-rate-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/group-rate/group-rate-dialog.html',
                    controller: 'GroupRateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['GroupRate', function(GroupRate) {
                            return GroupRate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('group-rate.new', {
            parent: 'group-rate',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/group-rate/group-rate-dialog.html',
                    controller: 'GroupRateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                indexRate: null,
                                rate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('group-rate', null, { reload: 'group-rate' });
                }, function() {
                    $state.go('group-rate');
                });
            }]
        })
        .state('group-rate.edit', {
            parent: 'group-rate',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/group-rate/group-rate-dialog.html',
                    controller: 'GroupRateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['GroupRate', function(GroupRate) {
                            return GroupRate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('group-rate', null, { reload: 'group-rate' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('group-rate.delete', {
            parent: 'group-rate',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/group-rate/group-rate-delete-dialog.html',
                    controller: 'GroupRateDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['GroupRate', function(GroupRate) {
                            return GroupRate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('group-rate', null, { reload: 'group-rate' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
