(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .controller('AdditionalRateDeleteController',AdditionalRateDeleteController);

    AdditionalRateDeleteController.$inject = ['$uibModalInstance', 'entity', 'AdditionalRate'];

    function AdditionalRateDeleteController($uibModalInstance, entity, AdditionalRate) {
        var vm = this;

        vm.additionalRate = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            AdditionalRate.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
