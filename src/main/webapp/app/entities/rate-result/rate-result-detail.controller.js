(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .controller('RateResultDetailController', RateResultDetailController);

    RateResultDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'RateResult', 'RateMatrix', 'AdditionalRate', 'FinalRate', 'EvDiagram', 'GroupDiagram'];

    function RateResultDetailController($scope, $rootScope, $stateParams, previousState, entity, RateResult, RateMatrix, AdditionalRate, FinalRate, EvDiagram, GroupDiagram) {
        var vm = this;

        vm.rateResult = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bankAuditApp:rateResultUpdate', function(event, result) {
            vm.rateResult = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
