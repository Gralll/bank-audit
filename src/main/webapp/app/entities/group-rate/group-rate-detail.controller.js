(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .controller('GroupRateDetailController', GroupRateDetailController);

    GroupRateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'GroupRate', 'RateData', 'LocalRate'];

    function GroupRateDetailController($scope, $rootScope, $stateParams, previousState, entity, GroupRate) {
        var vm = this;

        vm.groupRate = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bankAuditApp:groupRateUpdate', function(event, result) {
            vm.groupRate = result;
        });
        $scope.$on('$destroy', unsubscribe);

        vm.save = function save () {
            vm.isSaving = true;
            if (vm.groupRate.id !== null) {
                GroupRate.update(vm.groupRate, onSaveSuccess, onSaveError);
            } else {
                GroupRate.save(vm.groupRate, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bankAuditApp:groupRateUpdate', result);
            //$uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.calcLocalRate = function(localRate, notRated) {
            if (notRated == true) {
                localRate.rate = null;
            } else {
                if (localRate.category == 'FIRST') {
                    if (localRate.documentation == 'YES') {
                        if (localRate.execution == 'YES') {
                            localRate.rate = 1.0;
                        } else if (localRate.execution == 'ALMOST') {
                            localRate.rate = 0.75;
                        } else if (localRate.execution == 'PARTIALLY') {
                            localRate.rate = 0.5;
                        } else if (localRate.execution == 'NO') {
                            localRate.rate = 0.25;
                        } else {
                            localRate.rate = null;
                        }
                    } else if (localRate.documentation == 'NO') {
                        localRate.rate = 0.0;
                    } else {
                        localRate.rate = null;
                    }
                }
                if (localRate.category == 'SECOND') {
                    if (localRate.documentation == 'YES') {
                        localRate.rate = 1.0;
                    } else if (localRate.documentation == 'NO') {
                        localRate.rate = 0.0;
                    } else {
                        localRate.rate = null;
                    }
                }
                if (localRate.category == 'THIRD') {
                    if (localRate.execution == 'YES') {
                        localRate.rate = 1.0;
                    } else if (localRate.execution == 'PARTIALLY') {
                        localRate.rate = 0.5;
                    } else if (localRate.execution == 'NO') {
                        localRate.rate = 0.0;
                    } else {
                        localRate.rate = null;
                    }
                }
                if (localRate.category == 'FOURTH') {
                    if (localRate.documentation == 'YES') {
                        localRate.rate = 1.0;
                    } else if (localRate.documentation == 'NO') {
                        localRate.rate = 0.0;
                    } else {
                        localRate.rate = null;
                    }
                }
            }

            return localRate.rate;
        };
    }
})();
