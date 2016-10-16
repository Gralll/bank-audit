(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .factory('EvDiagramSearch', EvDiagramSearch);

    EvDiagramSearch.$inject = ['$resource'];

    function EvDiagramSearch($resource) {
        var resourceUrl =  'api/_search/ev-diagrams/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
