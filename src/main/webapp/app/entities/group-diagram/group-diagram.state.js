(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('group-diagram', {
            parent: 'entity',
            url: '/group-diagram',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bankAuditApp.groupDiagram.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/group-diagram/group-diagrams.html',
                    controller: 'GroupDiagramController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('groupDiagram');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('group-diagram-detail', {
            parent: 'entity',
            url: '/group-diagram/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bankAuditApp.groupDiagram.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/group-diagram/group-diagram-detail.html',
                    controller: 'GroupDiagramDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('groupDiagram');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'GroupDiagram', function($stateParams, GroupDiagram) {
                    return GroupDiagram.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'group-diagram',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('group-diagram-detail.edit', {
            parent: 'group-diagram-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/group-diagram/group-diagram-dialog.html',
                    controller: 'GroupDiagramDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['GroupDiagram', function(GroupDiagram) {
                            return GroupDiagram.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('group-diagram.new', {
            parent: 'group-diagram',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/group-diagram/group-diagram-dialog.html',
                    controller: 'GroupDiagramDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                indexRate: null,
                                value: null,
                                level: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('group-diagram', null, { reload: 'group-diagram' });
                }, function() {
                    $state.go('group-diagram');
                });
            }]
        })
        .state('group-diagram.edit', {
            parent: 'group-diagram',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/group-diagram/group-diagram-dialog.html',
                    controller: 'GroupDiagramDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['GroupDiagram', function(GroupDiagram) {
                            return GroupDiagram.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('group-diagram', null, { reload: 'group-diagram' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('group-diagram.delete', {
            parent: 'group-diagram',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/group-diagram/group-diagram-delete-dialog.html',
                    controller: 'GroupDiagramDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['GroupDiagram', function(GroupDiagram) {
                            return GroupDiagram.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('group-diagram', null, { reload: 'group-diagram' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
