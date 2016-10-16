(function() {
    'use strict';
    angular
        .module('bankAuditApp')
        .factory('GroupRate', GroupRate);

    GroupRate.$inject = ['$resource'];

    function GroupRate ($resource) {
        var resourceUrl =  'api/group-rates/:id';

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
