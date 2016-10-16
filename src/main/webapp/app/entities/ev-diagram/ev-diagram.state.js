(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('ev-diagram', {
            parent: 'entity',
            url: '/ev-diagram',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bankAuditApp.evDiagram.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ev-diagram/ev-diagrams.html',
                    controller: 'EvDiagramController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('evDiagram');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('ev-diagram-detail', {
            parent: 'entity',
            url: '/ev-diagram/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bankAuditApp.evDiagram.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ev-diagram/ev-diagram-detail.html',
                    controller: 'EvDiagramDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('evDiagram');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'EvDiagram', function($stateParams, EvDiagram) {
                    return EvDiagram.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'ev-diagram',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('ev-diagram-detail.edit', {
            parent: 'ev-diagram-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ev-diagram/ev-diagram-dialog.html',
                    controller: 'EvDiagramDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EvDiagram', function(EvDiagram) {
                            return EvDiagram.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ev-diagram.new', {
            parent: 'ev-diagram',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ev-diagram/ev-diagram-dialog.html',
                    controller: 'EvDiagramDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                ev1: null,
                                ev2: null,
                                ev3: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('ev-diagram', null, { reload: 'ev-diagram' });
                }, function() {
                    $state.go('ev-diagram');
                });
            }]
        })
        .state('ev-diagram.edit', {
            parent: 'ev-diagram',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ev-diagram/ev-diagram-dialog.html',
                    controller: 'EvDiagramDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EvDiagram', function(EvDiagram) {
                            return EvDiagram.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ev-diagram', null, { reload: 'ev-diagram' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ev-diagram.delete', {
            parent: 'ev-diagram',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ev-diagram/ev-diagram-delete-dialog.html',
                    controller: 'EvDiagramDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['EvDiagram', function(EvDiagram) {
                            return EvDiagram.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ev-diagram', null, { reload: 'ev-diagram' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
