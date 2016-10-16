(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .factory('RateDataSearch', RateDataSearch);

    RateDataSearch.$inject = ['$resource'];

    function RateDataSearch($resource) {
        var resourceUrl =  'api/_search/rate-data/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
