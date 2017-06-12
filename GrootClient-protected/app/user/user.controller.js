(function(angular){
    'use strict';

    angular
        .module('groot')
        .controller('UserCtrlAs', UserCtrlAs);

    UserCtrlAs.$inject = ['userService', 'sessionService'];

    function UserCtrlAs(userService, sessionService) {

        var userVm = this;

        userVm.user = {};
        userVm.userEdit = {};
        userVm.isEditFormVisible = false;

        userVm.editProfile = editProfile;
        userVm.toggleFormVisibility = toggleFormVisibility;

        activate();

        function activate() {
            var loggedUser = sessionService.getUser();
            var userId = loggedUser.id;
            userService.findOne(userId)
                .then(function(response) {
                    userVm.user.id = response.data.id;
                    userVm.user.name = response.data.name;
                    userVm.user.surname = response.data.surname;
                }).catch(function(error) {
                    //TODO: change error function - findOne
            });
        }

        function editProfile() {
            userService.editProfile(userVm.user.id, userVm.userEdit)
                .then(function(response) {
                   userVm.user = response.data;
                   userVm.toggleFormVisibility();
                }).catch(function(error) {
                console.log("Failed");
            });
        }

        function toggleFormVisibility() {
            userVm.isEditFormVisible = !userVm.isEditFormVisible;
            userVm.userEdit = {};
        }
    }
}(angular));