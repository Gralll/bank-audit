(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .controller('EvDiagramDetailController', EvDiagramDetailController);

    EvDiagramDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'EvDiagram'];

    function EvDiagramDetailController($scope, $rootScope, $stateParams, previousState, entity, EvDiagram) {
        var vm = this;

        vm.evDiagram = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bankAuditApp:evDiagramUpdate', function(event, result) {
            vm.evDiagram = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
