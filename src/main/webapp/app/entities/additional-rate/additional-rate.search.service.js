(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .factory('AdditionalRateSearch', AdditionalRateSearch);

    AdditionalRateSearch.$inject = ['$resource'];

    function AdditionalRateSearch($resource) {
        var resourceUrl =  'api/_search/additional-rates/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
