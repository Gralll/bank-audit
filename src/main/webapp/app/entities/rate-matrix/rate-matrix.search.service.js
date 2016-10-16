(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .factory('RateMatrixSearch', RateMatrixSearch);

    RateMatrixSearch.$inject = ['$resource'];

    function RateMatrixSearch($resource) {
        var resourceUrl =  'api/_search/rate-matrices/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
