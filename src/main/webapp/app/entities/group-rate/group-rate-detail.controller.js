(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .controller('GroupRateDetailController', GroupRateDetailController);

    GroupRateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'GroupRate', 'RateData', 'LocalRate'];

    function GroupRateDetailController($scope, $rootScope, $stateParams, previousState, entity, GroupRate, RateData, LocalRate) {
        var vm = this;

        vm.groupRate = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bankAuditApp:groupRateUpdate', function(event, result) {
            vm.groupRate = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
