(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .controller('GroupDiagramDialogController', GroupDiagramDialogController);

    GroupDiagramDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'GroupDiagram'];

    function GroupDiagramDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, GroupDiagram) {
        var vm = this;

        vm.groupDiagram = entity;
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
            if (vm.groupDiagram.id !== null) {
                GroupDiagram.update(vm.groupDiagram, onSaveSuccess, onSaveError);
            } else {
                GroupDiagram.save(vm.groupDiagram, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bankAuditApp:groupDiagramUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
