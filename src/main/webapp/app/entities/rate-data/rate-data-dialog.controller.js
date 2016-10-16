(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .controller('RateDataDialogController', RateDataDialogController);

    RateDataDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'RateData', 'GroupRate'];

    function RateDataDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, RateData, GroupRate) {
        var vm = this;

        vm.rateData = entity;
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
            if (vm.rateData.id !== null) {
                RateData.update(vm.rateData, onSaveSuccess, onSaveError);
            } else {
                RateData.save(vm.rateData, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bankAuditApp:rateDataUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
