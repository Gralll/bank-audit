(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .controller('EvDiagramDeleteController',EvDiagramDeleteController);

    EvDiagramDeleteController.$inject = ['$uibModalInstance', 'entity', 'EvDiagram'];

    function EvDiagramDeleteController($uibModalInstance, entity, EvDiagram) {
        var vm = this;

        vm.evDiagram = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            EvDiagram.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
