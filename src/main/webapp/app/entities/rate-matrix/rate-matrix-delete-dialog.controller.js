(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .controller('RateMatrixDeleteController',RateMatrixDeleteController);

    RateMatrixDeleteController.$inject = ['$uibModalInstance', 'entity', 'RateMatrix'];

    function RateMatrixDeleteController($uibModalInstance, entity, RateMatrix) {
        var vm = this;

        vm.rateMatrix = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            RateMatrix.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
