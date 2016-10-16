(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .controller('LocalRateDetailController', LocalRateDetailController);

    LocalRateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LocalRate', 'GroupRate'];

    function LocalRateDetailController($scope, $rootScope, $stateParams, previousState, entity, LocalRate, GroupRate) {
        var vm = this;

        vm.localRate = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bankAuditApp:localRateUpdate', function(event, result) {
            vm.localRate = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
