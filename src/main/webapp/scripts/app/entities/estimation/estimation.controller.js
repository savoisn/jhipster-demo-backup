'use strict';

angular.module('startmeupApp')
    .controller('EstimationController', function ($scope, Estimation, User, Project, Pledge) {
        $scope.estimations = [];
        $scope.users = User.query();
        $scope.projects = Project.query();
        $scope.pledges = Pledge.query();
        $scope.loadAll = function() {
            Estimation.query(function(result) {
               $scope.estimations = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Estimation.get({id: id}, function(result) {
                $scope.estimation = result;
                $('#saveEstimationModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.estimation.id != null) {
                Estimation.update($scope.estimation,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Estimation.save($scope.estimation,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Estimation.get({id: id}, function(result) {
                $scope.estimation = result;
                $('#deleteEstimationConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Estimation.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteEstimationConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveEstimationModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.estimation = {name: null, description: null, creadate: null, pledged: null, cost: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
