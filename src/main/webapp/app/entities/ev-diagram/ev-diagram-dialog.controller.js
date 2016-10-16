(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .controller('EvDiagramDialogController', EvDiagramDialogController);

    EvDiagramDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'EvDiagram'];

    function EvDiagramDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, EvDiagram) {
        var vm = this;

        vm.evDiagram = entity;
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
            if (vm.evDiagram.id !== null) {
                EvDiagram.update(vm.evDiagram, onSaveSuccess, onSaveError);
            } else {
                EvDiagram.save(vm.evDiagram, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bankAuditApp:evDiagramUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
