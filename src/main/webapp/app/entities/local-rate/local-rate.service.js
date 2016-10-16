(function() {
    'use strict';
    angular
        .module('bankAuditApp')
        .factory('LocalRate', LocalRate);

    LocalRate.$inject = ['$resource'];

    function LocalRate ($resource) {
        var resourceUrl =  'api/local-rates/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
