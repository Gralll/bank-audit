(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .factory('GroupRateSearch', GroupRateSearch);

    GroupRateSearch.$inject = ['$resource'];

    function GroupRateSearch($resource) {
        var resourceUrl =  'api/_search/group-rates/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
