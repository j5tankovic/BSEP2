(function(angular){
    'use strict';

    angular
        .module('groot')
        .controller('MainCtrlAs',MainCtrlAs);

    MainCtrlAs.$inject = ['$state', 'userService', 'sessionService'];

    function MainCtrlAs($state, userService, sessionService){

        var mainVm = this;

        mainVm.user = {};

        mainVm.logout = logout;
        mainVm.toProfile = toProfile;

        activate();

        function activate() {
            var user = sessionService.getUser();
            if (user) {
                var userId = user.id;
                userService.findOne(userId)
                    .then(function(response){
                        mainVm.user.name = response.data.name;
                    }).catch(function(error){

                });
            }
        }

        function logout() {
            sessionService.removeUser();
            $state.go('login');
        }

        function toProfile() {
            $state.go('main.profile');
        }
    }
}(angular));