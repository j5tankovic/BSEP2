(function (angular) {
    'use strict';

    angular
        .module('groot')
        .controller('AdminCtrlAs', AdminCtrlAs)

    AdminCtrlAs.$inject = ['$state', 'courseService', 'userService'];

    function AdminCtrlAs($state, courseService, userService) {

        var vm = this;

        vm.courses = [];
        vm.users = [];

        vm.newUser = {};
        vm.newCourse = {};

        vm.isUserFormVisible = false;
        vm.isCourseFormVisible = false;

        vm.addCourse = addCourse;
        vm.updateCourse = updateCourse;
        vm.deleteCourse  = deleteCourse;

        vm.addUser = addUser;
        vm.deleteUser = deleteUser;
        vm.addUserToCourse = addUserToCourse;

        vm.toUsers = toUsers;
        vm.toCourses = toCourses;
        vm.toCourse = toCourse;

        vm.toggleUserFormVisibility = toggleUserFormVisibility;
        vm.toggleCourseFormVisibility = toggleCourseFormVisibility;

        activate();

        function activate() {
            //TODO: change function activate - admin
            userService.findAll()
                .then(function(response) {
                    vm.users = response.data;
                }).catch(function(error) {
                    console.log("Failed due to", error);
            });

            courseService.findAll()
                .then(function(response) {
                    vm.courses = response.data;
                }).catch(function(error){
                    console.log("Failed due to", error);
            });
        }

        function addCourse() {
            courseService.addCourse(vm.newCourse)
                .then(function(response){
                    toggleCourseFormVisibility();
                    activate();
                }).catch(function(error) {

            });
        }

        function updateCourse(course) {
            courseService.updateCourse(course)
                .then(function(response){
                    //TODO
                }).catch(function(error) {
                    //TODO
            })
        }

        function deleteCourse(courseId) {
            courseService.deleteCourse(courseId)
                .then(function(response){
                    //TODO
                }).catch(function(error){
                    //TODO
            })
        }

        function addUser() {
            userService.addUser(vm.newUser)
                .then(function(response){
                   vm.toggleUserFormVisibility();
                   activate();
                }).catch(function(error) {
                    //TODO: change error function - addUser
                    console.log("Failed due to", error);
            })
        }

        function deleteUser() {

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
            vm.isUserFormVisible = !vm.isUserFormVisible;
        }

        function toggleCourseFormVisibility() {
            vm.isCourseFormVisible = !vm.isCourseFormVisible;
        }

    }
}(angular));