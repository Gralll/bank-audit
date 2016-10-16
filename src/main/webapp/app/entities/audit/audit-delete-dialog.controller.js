(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .controller('AuditDeleteController',AuditDeleteController);

    AuditDeleteController.$inject = ['$uibModalInstance', 'entity', 'Audit'];

    function AuditDeleteController($uibModalInstance, entity, Audit) {
        var vm = this;

        vm.audit = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Audit.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
