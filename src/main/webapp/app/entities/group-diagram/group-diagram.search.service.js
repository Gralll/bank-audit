(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .factory('GroupDiagramSearch', GroupDiagramSearch);

    GroupDiagramSearch.$inject = ['$resource'];

    function GroupDiagramSearch($resource) {
        var resourceUrl =  'api/_search/group-diagrams/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
