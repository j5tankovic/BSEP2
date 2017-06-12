(function (angular) {
    'use strict';

    angular
        .module('groot')
        .controller('AdminCtrlAs', AdminCtrlAs);

    AdminCtrlAs.$inject = ['$state', 'courseService', 'userService'];

    function AdminCtrlAs($state, courseService, userService) {

        var adminVm = this;

        adminVm.courses = [];
        adminVm.users = [];

        adminVm.newUser = {};
        adminVm.newCourse = {};

        adminVm.isUserFormVisible = false;
        adminVm.isCourseFormVisible = false;

        adminVm.addCourse = addCourse;
        adminVm.deleteCourse  = deleteCourse;

        adminVm.addUser = addUser;
        adminVm.deleteUser = deleteUser;

        adminVm.addUserToCourse = addUserToCourse;

        adminVm.toUsers = toUsers;
        adminVm.toCourses = toCourses;
        adminVm.toCourse = toCourse;

        adminVm.toggleUserFormVisibility = toggleUserFormVisibility;
        adminVm.toggleCourseFormVisibility = toggleCourseFormVisibility;

        activate();

        function activate() {
            userService.findAll()
                .then(function(response) {
                    adminVm.users = response.data;
                }).catch(function(error) {
                    console.log("Failed due to", error);
            });

            courseService.findAll()
                .then(function(response) {
                    adminVm.courses = response.data;
                }).catch(function(error){
                    console.log("Failed due to", error);
            });
        }

        function addCourse() {
            courseService.addCourse(adminVm.newCourse)
                .then(function(response){
                    toggleCourseFormVisibility();
                    activate();
                }).catch(function(error) {

            });
        }

        function deleteCourse(course) {
            courseService.deleteCourse(course.id)
                .then(function(response){
                    activate();
                }).catch(function(error){
                    //TODO
            })
        }

        function addUser() {
            userService.add(adminVm.newUser)
                .then(function(response){
                   toggleUserFormVisibility();
                   activate();
                }).catch(function(error) {
                    console.log("Failed due to", error);
            })
        }

        function deleteUser(user) {
            userService.deleteOne(user.id)
                .then(function(response) {
                    activate();
                }).catch(function(error) {
                console.log("Failed due to", error);
            });
        }

        function addUserToCourse() {

        }

        function toUsers() {
            $state.go('main.admin.users');
        }

        function toCourses(){
            $state.go('main.admin.courses');
        }

        function toCourse(course) {
            $state.go('main.admin.course', {courseId: course.id});
        }


        function toggleUserFormVisibility() {
            adminVm.isUserFormVisible = !adminVm.isUserFormVisible;
            adminVm.newUser = {};
        }

        function toggleCourseFormVisibility() {
            adminVm.isCourseFormVisible = !adminVm.isCourseFormVisible;
            adminVm.newCourse = {};
        }

    }
}(angular));