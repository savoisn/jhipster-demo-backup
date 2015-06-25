'use strict';

angular.module('startmeupApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('estimation', {
                parent: 'entity',
                url: '/estimation',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'startmeupApp.estimation.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/estimation/estimations.html',
                        controller: 'EstimationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('estimation');
                        return $translate.refresh();
                    }]
                }
            })
            .state('estimationDetail', {
                parent: 'entity',
                url: '/estimation/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'startmeupApp.estimation.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/estimation/estimation-detail.html',
                        controller: 'EstimationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('estimation');
                        return $translate.refresh();
                    }]
                }
            });
    });
