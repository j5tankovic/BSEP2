(function (angular) {
    'use strict';

    angular.module('groot')
        .config(function ($stateProvider) {
            $stateProvider
                .state('login', {
                    url: '/login',
                    templateUrl: 'app/auth/login/login.html',
                    controller: 'LoginCtrlAs',
                    controllerAs: 'vm'
                })
        });

})(angular);
