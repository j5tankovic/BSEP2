(function(angular) {
    'use strict';

    angular
        .module('groot')
        .service('courseService', courseService);

    courseService.$inject = ['$http'];

    function courseService($http) {
        var BASE_URL = "http://localhost:8080/api/courses";

        function pathWithId(id) {
            return BASE_URL + `/${id}`;
        }

        return {
            findAll: findAll,
            findOne: findOne,
            addCourse: addCourse,
            deleteCourse: deleteCourse,
            findAnnouncement: findAnnouncement,
            addAnnouncement: addAnnouncement,
            updateAnnouncement: updateAnnouncement,
            deleteAnnouncement: deleteAnnouncement,
            addUser: addUser,
        };

        function findAll() {
            return $http.get(BASE_URL);
        }

        function findOne(id) {
            return $http.get(pathWithId(id));
        }

        function addCourse(course) {
            return $http.post(BASE_URL, course);
        }

        function deleteCourse(courseId) {
            return $http.delete(pathWithId(courseId));
        }

        function findAnnouncement(courseId, announcementId) {
            return $http.get(pathWithId(courseId) + '/announcements/' + announcementId);
        }

        function addAnnouncement(courseId, newAnnouncement) {
            return $http.post(pathWithId(courseId) + '/announcements', newAnnouncement);
        }

        function updateAnnouncement(courseId, announcement) {
            return $http.put(pathWithId(courseId) + `/announcements/${announcement.id}`, announcement);
        }

        function deleteAnnouncement(courseId, announcement) {
            return $http.delete(pathWithId(courseId) + `/announcements/${announcement.id}`);
        }

        function addUser(courseId, userToCourse) {
            return $http.post(pathWithId(courseId) + '/users', userToCourse);
        }
    }
})(angular);
