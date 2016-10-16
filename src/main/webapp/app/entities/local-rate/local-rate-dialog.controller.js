(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .controller('LocalRateDialogController', LocalRateDialogController);

    LocalRateDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LocalRate', 'GroupRate'];

    function LocalRateDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, LocalRate, GroupRate) {
        var vm = this;

        vm.localRate = entity;
        vm.clear = clear;
        vm.save = save;
        vm.grouprates = GroupRate.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.localRate.id !== null) {
                LocalRate.update(vm.localRate, onSaveSuccess, onSaveError);
            } else {
                LocalRate.save(vm.localRate, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bankAuditApp:localRateUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
