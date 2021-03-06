(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .directive('back', ['$window', function($window) {
            return {
                restrict: 'A',
                link: function (scope, elem, attrs) {
                    elem.bind('click', function () {
                        $window.history.back();
                    });
                }
            };
        }]);
})();

