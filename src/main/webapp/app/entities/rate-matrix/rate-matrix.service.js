(function() {
    'use strict';
    angular
        .module('bankAuditApp')
        .factory('RateMatrix', RateMatrix);

    RateMatrix.$inject = ['$resource'];

    function RateMatrix ($resource) {
        var resourceUrl =  'api/rate-matrices/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
