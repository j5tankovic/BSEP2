(function(angular) {
    'use strict';

    angular
        .module('groot')
        .service('userService', userService);

    userService.$inject = ['$http'];

    function userService($http) {
        var BASE_URL = 'http://localhost:8080/api/users';

        function pathWithId(id) {
            return BASE_URL + '/' + id;
        }

        return {
            findAll: findAll,
            findOne: findOne,
            findOneByToken: findOneByToken,
            add: add,
            deleteOne: deleteOne,
            findCourses: findCourses,
            editProfile: editProfile
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

        function add(user) {
            return $http.post(BASE_URL, user);
        }

        function deleteOne(userId){
            return $http.delete(pathWithId(userId));
        }

        function editProfile(id, userToEdit) {
            return $http.put(pathWithId(id), userToEdit);
        }
    }
}(angular));