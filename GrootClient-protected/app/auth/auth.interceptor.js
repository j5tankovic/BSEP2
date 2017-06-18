(function (angular) {
    'use strict';

    angular.module('groot')
        .service('authInterceptor', authInterceptor);

    authInterceptor.$inject = ['$q', 'sessionService'];

    function authInterceptor($q, sessionService) {

        return {
            request: request,
            responseError: responseError
        };

        function request(config) {
            var currentUser = sessionService.getUser();
            if (currentUser) {
                var authToken = currentUser.token;
                if (authToken) {
                    config.headers["Authorization"] = authToken;
                }
            }
            return config;
        }

        function responseError(response) {
            return $q.reject(response);
        }
    }
})(angular);
