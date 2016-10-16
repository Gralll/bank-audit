(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .controller('RateMatrixDetailController', RateMatrixDetailController);

    RateMatrixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'RateMatrix'];

    function RateMatrixDetailController($scope, $rootScope, $stateParams, previousState, entity, RateMatrix) {
        var vm = this;

        vm.rateMatrix = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bankAuditApp:rateMatrixUpdate', function(event, result) {
            vm.rateMatrix = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
