'use strict';

angular.module('startmeupApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('pledge', {
                parent: 'entity',
                url: '/pledge',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'startmeupApp.pledge.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/pledge/pledges.html',
                        controller: 'PledgeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pledge');
                        return $translate.refresh();
                    }]
                }
            })
            .state('pledgeDetail', {
                parent: 'entity',
                url: '/pledge/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'startmeupApp.pledge.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/pledge/pledge-detail.html',
                        controller: 'PledgeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pledge');
                        return $translate.refresh();
                    }]
                }
            });
    });
