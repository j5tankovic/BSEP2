(function (angular) {
    'use strict';
    angular
        .module('groot')
        .service('authorizationService', authorizationService);

    authorizationService.$inject = ['$http'];

    function authorizationService($http) {
        var BASE_URL = "api/auth";

        function pathWithId(id) {
            return BASE_URL + '/' + id;
        }

        return {
            login: login
        };

        function login(user) {
            return $http.post(BASE_URL + "/login", user);
        }
    }
}(angular));
