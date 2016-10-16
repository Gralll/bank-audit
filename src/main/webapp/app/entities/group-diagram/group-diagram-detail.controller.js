(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .controller('GroupDiagramDetailController', GroupDiagramDetailController);

    GroupDiagramDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'GroupDiagram'];

    function GroupDiagramDetailController($scope, $rootScope, $stateParams, previousState, entity, GroupDiagram) {
        var vm = this;

        vm.groupDiagram = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bankAuditApp:groupDiagramUpdate', function(event, result) {
            vm.groupDiagram = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
