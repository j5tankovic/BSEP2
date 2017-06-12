(function (angular) {
    'use strict';

    angular
        .module('groot')
        .service('roleService', roleService);

    roleService.$inject = [];

    function roleService() {

        var adminRoles = ["ADMIN"];
        var teacherRoles = ["TEACHER"];
        var studentRoles = ["STUDENT"];

        return {
            isAdmin: isAdmin,
            isTeacher: isTeacher,
            isStudent: isStudent,
        }

        function isAdmin(loggedUser) {
            if (loggedUser) {
                return _.includes(adminRoles, loggedUser.role);
            }
            return false;
        }

        function isTeacher(loggedUser) {
            return loggedUser ? _.includes(teacherRoles, loggedUser.role) : false;
        }

        function isStudent(loggedUser) {
            return loggedUser ? _.includes(studentRoles, loggedUser.role) : false;
        }
    }
})(angular);
