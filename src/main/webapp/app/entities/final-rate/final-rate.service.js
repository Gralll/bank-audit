(function() {
    'use strict';
    angular
        .module('bankAuditApp')
        .factory('FinalRate', FinalRate);

    FinalRate.$inject = ['$resource'];

    function FinalRate ($resource) {
        var resourceUrl =  'api/final-rates/:id';

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
