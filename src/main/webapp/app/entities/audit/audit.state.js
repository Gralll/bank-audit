(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('audit', {
            parent: 'entity',
            url: '/audit',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bankAuditApp.audit.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/audit/audits.html',
                    controller: 'AuditController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('audit');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('audit-detail', {
            parent: 'entity',
            url: '/audit/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bankAuditApp.audit.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/audit/audit-detail.html',
                    controller: 'AuditDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('audit');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Audit', function($stateParams, Audit) {
                    return Audit.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'audit',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('audit-detail.edit', {
            parent: 'audit-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/audit/audit-dialog.html',
                    controller: 'AuditDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Audit', function(Audit) {
                            return Audit.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('audit.new', {
            parent: 'audit',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/audit/audit-dialog.html',
                    controller: 'AuditDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                bank: null,
                                startDate: null,
                                endDate: null,
                                startLevel: null,
                                endLevel: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('audit', null, { reload: 'audit' });
                }, function() {
                    $state.go('audit');
                });
            }]
        })
        .state('audit.edit', {
            parent: 'audit',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/audit/audit-dialog.html',
                    controller: 'AuditDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Audit', function(Audit) {
                            return Audit.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('audit', null, { reload: 'audit' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('audit.delete', {
            parent: 'audit',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/audit/audit-delete-dialog.html',
                    controller: 'AuditDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Audit', function(Audit) {
                            return Audit.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('audit', null, { reload: 'audit' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
