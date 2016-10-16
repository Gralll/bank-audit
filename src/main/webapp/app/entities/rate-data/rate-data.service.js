(function() {
    'use strict';
    angular
        .module('bankAuditApp')
        .factory('RateData', RateData);

    RateData.$inject = ['$resource'];

    function RateData ($resource) {
        var resourceUrl =  'api/rate-data/:id';

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
