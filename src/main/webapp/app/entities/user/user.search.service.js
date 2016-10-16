(function() {
    'use strict';

    angular
        .module('bankAuditApp')
        .factory('UserSearch', UserSearch);

    UserSearch.$inject = ['$resource'];

    function UserSearch($resource) {
        var resourceUrl =  'api/_search/users/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
