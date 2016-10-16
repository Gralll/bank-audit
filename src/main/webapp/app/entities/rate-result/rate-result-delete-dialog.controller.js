(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .controller('RateResultDeleteController',RateResultDeleteController);

    RateResultDeleteController.$inject = ['$uibModalInstance', 'entity', 'RateResult'];

    function RateResultDeleteController($uibModalInstance, entity, RateResult) {
        var vm = this;

        vm.rateResult = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            RateResult.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
