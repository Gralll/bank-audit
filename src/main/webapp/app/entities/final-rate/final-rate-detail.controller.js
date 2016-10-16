(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .controller('FinalRateDetailController', FinalRateDetailController);

    FinalRateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'FinalRate'];

    function FinalRateDetailController($scope, $rootScope, $stateParams, previousState, entity, FinalRate) {
        var vm = this;

        vm.finalRate = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bankAuditApp:finalRateUpdate', function(event, result) {
            vm.finalRate = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
