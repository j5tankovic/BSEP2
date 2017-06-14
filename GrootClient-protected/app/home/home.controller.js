(function(angular){
    'use strict';

    angular
        .module('groot')
        .controller('HomeCtrlAs', HomeCtrlAs);

    HomeCtrlAs.$inject = ['$state', 'userService', 'sessionService'];

    function HomeCtrlAs($state, userService, sessionService) {
        var homeVm = this;

        homeVm.userCourses = [];
        homeVm.user = {};

        homeVm.toCourse = toCourse;

        activate();

        function activate() {
            var user = sessionService.getUser();
            if (user) {
                var id = user.id;
                findUser(id)
                    .then(findCourses)
            }
        }

        function findUser(id) {      
            return userService.findOne(id)
                .then(function(response) {
                    homeVm.user = response.data;
                })
        }

        function findCourses() {
            userService.findCourses(homeVm.user.id)
                .then(function(response) {
                    homeVm.userCourses = response.data;
                })
        }

        function toCourse(course) {
            debugger;
            $state.go('main.course', {id: course.id});
        }
    }
}(angular));