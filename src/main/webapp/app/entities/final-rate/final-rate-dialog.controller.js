(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .controller('FinalRateDialogController', FinalRateDialogController);

    FinalRateDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'FinalRate'];

    function FinalRateDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, FinalRate) {
        var vm = this;

        vm.finalRate = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.finalRate.id !== null) {
                FinalRate.update(vm.finalRate, onSaveSuccess, onSaveError);
            } else {
                FinalRate.save(vm.finalRate, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bankAuditApp:finalRateUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
