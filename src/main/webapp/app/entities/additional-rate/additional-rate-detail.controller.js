(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .controller('AdditionalRateDetailController', AdditionalRateDetailController);

    AdditionalRateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'AdditionalRate'];

    function AdditionalRateDetailController($scope, $rootScope, $stateParams, previousState, entity, AdditionalRate) {
        var vm = this;

        vm.additionalRate = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bankAuditApp:additionalRateUpdate', function(event, result) {
            vm.additionalRate = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
