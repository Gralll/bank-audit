(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .controller('GroupRateDeleteController',GroupRateDeleteController);

    GroupRateDeleteController.$inject = ['$uibModalInstance', 'entity', 'GroupRate'];

    function GroupRateDeleteController($uibModalInstance, entity, GroupRate) {
        var vm = this;

        vm.groupRate = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            GroupRate.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
