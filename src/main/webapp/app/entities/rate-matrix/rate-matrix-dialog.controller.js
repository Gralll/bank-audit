(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .controller('RateMatrixDialogController', RateMatrixDialogController);

    RateMatrixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'RateMatrix'];

    function RateMatrixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, RateMatrix) {
        var vm = this;

        vm.rateMatrix = entity;
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
            if (vm.rateMatrix.id !== null) {
                RateMatrix.update(vm.rateMatrix, onSaveSuccess, onSaveError);
            } else {
                RateMatrix.save(vm.rateMatrix, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bankAuditApp:rateMatrixUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
