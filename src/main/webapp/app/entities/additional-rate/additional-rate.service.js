(function() {
    'use strict';
    angular
        .module('bankAuditApp')
        .factory('AdditionalRate', AdditionalRate);

    AdditionalRate.$inject = ['$resource'];

    function AdditionalRate ($resource) {
        var resourceUrl =  'api/additional-rates/:id';

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
