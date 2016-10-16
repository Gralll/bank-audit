(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .controller('LocalRateDeleteController',LocalRateDeleteController);

    LocalRateDeleteController.$inject = ['$uibModalInstance', 'entity', 'LocalRate'];

    function LocalRateDeleteController($uibModalInstance, entity, LocalRate) {
        var vm = this;

        vm.localRate = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LocalRate.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
