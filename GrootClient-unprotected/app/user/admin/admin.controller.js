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
        adminVm.selectedCourse = {};
        adminVm.userToCourse = {};
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
        adminVm.toUserToCourse = toUserToCourse;
        adminVm.toggleUserFormVisibility = toggleUserFormVisibility;
        adminVm.toggleCourseFormVisibility = toggleCourseFormVisibility;
        adminVm.selectCourse = selectCourse;

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
                    console.log("Failed due to", error);
            });
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
            courseService.addUser(adminVm.selectedCourse.id, adminVm.userToCourse)
                .then(function(response) {
                    console.log("User added to course");
                    adminVm.selectedCourse = {};
                    adminVm.userToCourse = {};
                }).catch(function(error) {
                    console.log("Failed due to", error);
            });
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

        function toUserToCourse(course) {
            $state.go('main.admin.userToCourse');
        }

        function toggleUserFormVisibility() {
            adminVm.isUserFormVisible = !adminVm.isUserFormVisible;
            adminVm.newUser = {};
        }

        function toggleCourseFormVisibility() {
            adminVm.isCourseFormVisible = !adminVm.isCourseFormVisible;
            adminVm.newCourse = {};
        }

        function selectCourse(course) {
            adminVm.selectedCourse = course;
        }

    }
}(angular));