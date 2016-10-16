(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .controller('RateResultDialogController', RateResultDialogController);

    RateResultDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'RateResult', 'RateMatrix', 'AdditionalRate', 'FinalRate', 'EvDiagram', 'GroupDiagram'];

    function RateResultDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, RateResult, RateMatrix, AdditionalRate, FinalRate, EvDiagram, GroupDiagram) {
        var vm = this;

        vm.rateResult = entity;
        vm.clear = clear;
        vm.save = save;
        vm.ratematrices = RateMatrix.query({filter: 'rateresult-is-null'});
        $q.all([vm.rateResult.$promise, vm.ratematrices.$promise]).then(function() {
            if (!vm.rateResult.rateMatrix || !vm.rateResult.rateMatrix.id) {
                return $q.reject();
            }
            return RateMatrix.get({id : vm.rateResult.rateMatrix.id}).$promise;
        }).then(function(rateMatrix) {
            vm.ratematrices.push(rateMatrix);
        });
        vm.additionalrates = AdditionalRate.query({filter: 'rateresult-is-null'});
        $q.all([vm.rateResult.$promise, vm.additionalrates.$promise]).then(function() {
            if (!vm.rateResult.additionalRate || !vm.rateResult.additionalRate.id) {
                return $q.reject();
            }
            return AdditionalRate.get({id : vm.rateResult.additionalRate.id}).$promise;
        }).then(function(additionalRate) {
            vm.additionalrates.push(additionalRate);
        });
        vm.finalrates = FinalRate.query({filter: 'rateresult-is-null'});
        $q.all([vm.rateResult.$promise, vm.finalrates.$promise]).then(function() {
            if (!vm.rateResult.finalRate || !vm.rateResult.finalRate.id) {
                return $q.reject();
            }
            return FinalRate.get({id : vm.rateResult.finalRate.id}).$promise;
        }).then(function(finalRate) {
            vm.finalrates.push(finalRate);
        });
        vm.evdiagrams = EvDiagram.query({filter: 'rateresult-is-null'});
        $q.all([vm.rateResult.$promise, vm.evdiagrams.$promise]).then(function() {
            if (!vm.rateResult.evDiagram || !vm.rateResult.evDiagram.id) {
                return $q.reject();
            }
            return EvDiagram.get({id : vm.rateResult.evDiagram.id}).$promise;
        }).then(function(evDiagram) {
            vm.evdiagrams.push(evDiagram);
        });
        vm.groupdiagrams = GroupDiagram.query({filter: 'rateresult-is-null'});
        $q.all([vm.rateResult.$promise, vm.groupdiagrams.$promise]).then(function() {
            if (!vm.rateResult.groupDiagram || !vm.rateResult.groupDiagram.id) {
                return $q.reject();
            }
            return GroupDiagram.get({id : vm.rateResult.groupDiagram.id}).$promise;
        }).then(function(groupDiagram) {
            vm.groupdiagrams.push(groupDiagram);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.rateResult.id !== null) {
                RateResult.update(vm.rateResult, onSaveSuccess, onSaveError);
            } else {
                RateResult.save(vm.rateResult, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bankAuditApp:rateResultUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
