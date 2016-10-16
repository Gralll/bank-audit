(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .controller('AuditDetailController', AuditDetailController);

    AuditDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Audit', 'RateData', 'RateResult'];

    function AuditDetailController($scope, $rootScope, $stateParams, previousState, entity, Audit, RateData, RateResult) {
        var vm = this;
        vm.isVisibleRates = true;
        vm.isVisibleResults = false;
        vm.isVisibleRatesTU = false;
        vm.isVisibleRatesMI = false;
        vm.isVisibleRatesPP = false;

        vm.audit = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bankAuditApp:auditUpdate', function(event, result) {
            vm.audit = result;
        });
        $scope.$on('$destroy', unsubscribe);


        this.getRates = function() {
            vm.isVisibleRates = true;
            vm.isVisibleResults = false;
        };

        this.getResults = function () {
            vm.isVisibleRates = false;
            vm.isVisibleResults = true;
        };

        this.getGroupRateProgress = function(groupRate) {
            var countRated = 0;
            var countAllRated = 0;
            var sumRates = 0;
            for (var i = 0; i < groupRate.localRates.length; i++) {
                if (!groupRate.localRates[i].notRated) {
                    if (groupRate.localRates[i].rate != null) {
                        countRated++;
                        sumRates += groupRate.localRates[i].rate;
                    }
                    countAllRated++;
                }
            }
            groupRate.rate = sumRates/countAllRated;
            return (countRated / countAllRated * 100) + "%";
        }

    }
})();
