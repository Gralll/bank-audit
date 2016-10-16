(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .factory('FinalRateSearch', FinalRateSearch);

    FinalRateSearch.$inject = ['$resource'];

    function FinalRateSearch($resource) {
        var resourceUrl =  'api/_search/final-rates/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
