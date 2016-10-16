(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .controller('GroupDiagramDeleteController',GroupDiagramDeleteController);

    GroupDiagramDeleteController.$inject = ['$uibModalInstance', 'entity', 'GroupDiagram'];

    function GroupDiagramDeleteController($uibModalInstance, entity, GroupDiagram) {
        var vm = this;

        vm.groupDiagram = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            GroupDiagram.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
