(function (angular) {
    'use strict';

    angular.module('groot')
        .config(function ($stateProvider) {
            $stateProvider
                .state('unauthorized', {
                    url: '/unauthorized',
                    templateUrl: 'app/auth/unauthorized/unauthorized.html',
                    controller: 'UnauthorizedCtrlAs',
                    controllerAs: 'unauthorizedVm'
                });
        });

})(angular);
