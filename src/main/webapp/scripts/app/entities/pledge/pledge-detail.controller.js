'use strict';

angular.module('startmeupApp')
    .controller('PledgeDetailController', function ($scope, $stateParams, Pledge, User, Estimation) {
        $scope.pledge = {};
        $scope.load = function (id) {
            Pledge.get({id: id}, function(result) {
              $scope.pledge = result;
            });
        };
        $scope.load($stateParams.id);
    });
