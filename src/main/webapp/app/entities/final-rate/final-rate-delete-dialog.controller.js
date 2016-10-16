(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .controller('FinalRateDeleteController',FinalRateDeleteController);

    FinalRateDeleteController.$inject = ['$uibModalInstance', 'entity', 'FinalRate'];

    function FinalRateDeleteController($uibModalInstance, entity, FinalRate) {
        var vm = this;

        vm.finalRate = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            FinalRate.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
