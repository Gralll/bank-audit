(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .factory('LocalRateSearch', LocalRateSearch);

    LocalRateSearch.$inject = ['$resource'];

    function LocalRateSearch($resource) {
        var resourceUrl =  'api/_search/local-rates/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
