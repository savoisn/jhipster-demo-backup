'use strict';

angular.module('startmeupApp')
    .controller('ProjectController', function ($scope, Project, User) {
        $scope.projects = [];
        $scope.users = User.query();
        $scope.loadAll = function() {
            Project.query(function(result) {
               $scope.projects = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Project.get({id: id}, function(result) {
                $scope.project = result;
                $('#saveProjectModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.project.id != null) {
                Project.update($scope.project,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Project.save($scope.project,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Project.get({id: id}, function(result) {
                $scope.project = result;
                $('#deleteProjectConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Project.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteProjectConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveProjectModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.project = {name: null, description: null, mydate: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
