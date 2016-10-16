(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .controller('AdditionalRateDialogController', AdditionalRateDialogController);

    AdditionalRateDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'AdditionalRate'];

    function AdditionalRateDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, AdditionalRate) {
        var vm = this;

        vm.additionalRate = entity;
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
            if (vm.additionalRate.id !== null) {
                AdditionalRate.update(vm.additionalRate, onSaveSuccess, onSaveError);
            } else {
                AdditionalRate.save(vm.additionalRate, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bankAuditApp:additionalRateUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
