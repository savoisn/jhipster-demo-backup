'use strict';

angular.module('startmeupApp')
    .controller('EstimationDetailController', function ($scope, $stateParams, Estimation, User, Project, Pledge) {
        $scope.estimation = {};
        $scope.load = function (id) {
            Estimation.get({id: id}, function(result) {
              $scope.estimation = result;
            });
        };
        $scope.load($stateParams.id);
    });
