(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .controller('GroupRateDialogController', GroupRateDialogController);

    GroupRateDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'GroupRate', 'RateData', 'LocalRate'];

    function GroupRateDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, GroupRate, RateData, LocalRate) {
        var vm = this;

        vm.groupRate = entity;
        vm.clear = clear;
        vm.save = save;
        vm.ratedata = RateData.query();
        vm.localrates = LocalRate.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.groupRate.id !== null) {
                GroupRate.update(vm.groupRate, onSaveSuccess, onSaveError);
            } else {
                GroupRate.save(vm.groupRate, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bankAuditApp:groupRateUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
