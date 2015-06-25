'use strict';

angular.module('startmeupApp')
    .factory('Estimation', function ($resource, DateUtils) {
        return $resource('api/estimations/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.creadate = DateUtils.convertLocaleDateFromServer(data.creadate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.creadate = DateUtils.convertLocaleDateToServer(data.creadate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.creadate = DateUtils.convertLocaleDateToServer(data.creadate);
                    return angular.toJson(data);
                }
            }
        });
    });
