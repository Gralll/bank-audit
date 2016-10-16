(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .factory('AuditSearch', AuditSearch);

    AuditSearch.$inject = ['$resource'];

    function AuditSearch($resource) {
        var resourceUrl =  'api/_search/audits/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
