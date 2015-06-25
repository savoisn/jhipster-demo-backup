'use strict';

angular.module('startmeupApp')
    .controller('PledgeController', function ($scope, Pledge, User, Estimation) {
        $scope.pledges = [];
        $scope.users = User.query();
        $scope.estimations = Estimation.query();
        $scope.loadAll = function() {
            Pledge.query(function(result) {
               $scope.pledges = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Pledge.get({id: id}, function(result) {
                $scope.pledge = result;
                $('#savePledgeModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.pledge.id != null) {
                Pledge.update($scope.pledge,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Pledge.save($scope.pledge,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Pledge.get({id: id}, function(result) {
                $scope.pledge = result;
                $('#deletePledgeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Pledge.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePledgeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#savePledgeModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.pledge = {amount: null, comment: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
