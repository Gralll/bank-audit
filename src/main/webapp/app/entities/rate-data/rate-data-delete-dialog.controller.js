(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .controller('RateDataDeleteController',RateDataDeleteController);

    RateDataDeleteController.$inject = ['$uibModalInstance', 'entity', 'RateData'];

    function RateDataDeleteController($uibModalInstance, entity, RateData) {
        var vm = this;

        vm.rateData = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            RateData.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
