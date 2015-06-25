'use strict';

angular.module('startmeupApp')
    .controller('ProjectDetailController', function ($scope, $stateParams, Project, User) {
        $scope.project = {};
        $scope.load = function (id) {
            Project.get({id: id}, function(result) {
              $scope.project = result;
            });
        };
        $scope.load($stateParams.id);
    });
