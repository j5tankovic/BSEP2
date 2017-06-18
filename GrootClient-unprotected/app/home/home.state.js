(function (angular) {
    'use strict';

    angular.module('groot')
        .config(function ($stateProvider) {
            $stateProvider
                .state('main.home', {
                    url: '/home',
                    templateUrl: 'app/home/home.html',
                    controller: 'HomeCtrlAs',
                    controllerAs: 'homeVm'
                })
        });

})(angular);
