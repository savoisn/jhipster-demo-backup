'use strict';

angular.module('startmeupApp')
    .factory('Project', function ($resource, DateUtils) {
        return $resource('api/projects/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.mydate = DateUtils.convertLocaleDateFromServer(data.mydate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.mydate = DateUtils.convertLocaleDateToServer(data.mydate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.mydate = DateUtils.convertLocaleDateToServer(data.mydate);
                    return angular.toJson(data);
                }
            }
        });
    });
