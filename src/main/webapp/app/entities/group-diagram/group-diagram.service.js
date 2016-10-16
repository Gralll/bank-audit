(function() {
    'use strict';
    angular
        .module('bankAuditApp')
        .factory('GroupDiagram', GroupDiagram);

    GroupDiagram.$inject = ['$resource'];

    function GroupDiagram ($resource) {
        var resourceUrl =  'api/group-diagrams/:id';

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
