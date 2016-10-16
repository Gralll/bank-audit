(function() {
    'use strict';
    angular
        .module('bankAuditApp')
        .factory('RateResult', RateResult);

    RateResult.$inject = ['$resource'];

    function RateResult ($resource) {
        var resourceUrl =  'api/rate-results/:id';

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
