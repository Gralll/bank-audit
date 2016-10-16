(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .controller('AuditDialogController', AuditDialogController);

    AuditDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Audit', 'RateData', 'RateResult'];

    function AuditDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Audit, RateData, RateResult) {
        var vm = this;

        vm.audit = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.ratedata = RateData.query({filter: 'audit-is-null'});

        $q.all([vm.audit.$promise, vm.ratedata.$promise]).then(function() {
            if (!vm.audit.rateData || !vm.audit.rateData.id) {
                return $q.reject();
            }
            return RateData.get({id : vm.audit.rateData.id}).$promise;
        }).then(function(rateData) {
            vm.ratedata.push(rateData);
        });
        vm.results = RateResult.query({filter: 'audit-is-null'});
        $q.all([vm.audit.$promise, vm.results.$promise]).then(function() {
            if (!vm.audit.result || !vm.audit.result.id) {
                return $q.reject();
            }
            return RateResult.get({id : vm.audit.result.id}).$promise;
        }).then(function(result) {
            vm.results.push(result);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.audit.id !== null) {
                Audit.update(vm.audit, onSaveSuccess, onSaveError);
            } else {
                Audit.save(vm.audit, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bankAuditApp:auditUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.startDate = false;
        vm.datePickerOpenStatus.endDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
