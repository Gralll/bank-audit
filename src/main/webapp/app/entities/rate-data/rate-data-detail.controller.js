(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .controller('RateDataDetailController', RateDataDetailController);

    RateDataDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'RateData', 'GroupRate'];

    function RateDataDetailController($scope, $rootScope, $stateParams, previousState, entity, RateData, GroupRate) {
        var vm = this;

        vm.rateData = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bankAuditApp:rateDataUpdate', function(event, result) {
            vm.rateData = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
