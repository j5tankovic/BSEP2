(function (angular) {
    'use strict';

    angular.module('groot')
        .controller('LoginCtrlAs', LoginCtrlAs);

    LoginCtrlAs.$inject = ['$state', 'authorizationService', 'sessionService', 'roleService'];

    function LoginCtrlAs($state, authorizationService, sessionService, roleService) {

        var vm = this;

        vm.user = {};
        vm.login = login;

        function login() {
            authorizationService.login(vm.user)
                .then(function (response) {
                    sessionService.setUser(response.data);
                    redirectToPath();
                }).catch(function(error) {
                    console.log(error);
            });
        }

        function redirectToPath() {
            var loggedUser = sessionService.getUser();
            if (roleService.isAdmin(loggedUser)) {
                $state.go('main.admin', {id: loggedUser.id});
            } else {
                $state.go('main.home', {id: loggedUser.id});
            }
        }

    }

})(angular);