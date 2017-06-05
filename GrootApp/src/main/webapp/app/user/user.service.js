(function(angular) {
    'use strict';

    angular
        .module('groot')
        .service('userService', userService);

    userService.$inject = ['$http'];

    function userService($http) {
        var BASE_URL = '/api/users';

        function pathWithId(id) {
            return BASE_URL + '/' + id;
        }

        return {
            findAll: findAll,
            findOne: findOne,
            findOneByToken: findOneByToken,
            addUser: addUser,
            findCourses: findCourses,
            editProfile: editProfile,
        };

        function findAll() {
            return $http.get(BASE_URL);
        }

        function findOne(id) {
            return $http.get(pathWithId(id));
        }

        function findCourses(id) {
            var coursesPath = pathWithId(id) + "/courses";
            return $http.get(coursesPath);
        }
        
        function findOneByToken(token) {
            return $http.get(BASE_URL + "/" + token);
        }

        function addUser(user) {
            return $http.post(BASE_URL, user);
        }

        function editProfile(id, userToEdit) {
            return $http.put(pathWithId(id), userToEdit);
        }
    }
}(angular));