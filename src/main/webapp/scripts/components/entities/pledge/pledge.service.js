'use strict';

angular.module('startmeupApp')
    .factory('Pledge', function ($resource, DateUtils) {
        return $resource('api/pledges/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
