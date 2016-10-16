(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .factory('RateResultSearch', RateResultSearch);

    RateResultSearch.$inject = ['$resource'];

    function RateResultSearch($resource) {
        var resourceUrl =  'api/_search/rate-results/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
