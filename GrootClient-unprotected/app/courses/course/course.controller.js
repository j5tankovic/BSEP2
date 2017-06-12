(function(angular) {
    'use strict';

    angular
        .module('groot')
        .controller('CourseCtrlAs', CourseCtrlAs);

    CourseCtrlAs.$inject=['$stateParams', '$state', 'courseService', 'sessionService'];

    function CourseCtrlAs($stateParams, $state, courseService, sessionService) {

        var courseVm = this;
        var courseId = $stateParams.id;

        courseVm.announcement = {};
        courseVm.announcements = [];
        courseVm.course = {};
        courseVm.formAnnouncement = {};
        courseVm.isAnnouncementFormVisible = false;
        courseVm.loggedUser = {};
        courseVm.people = [];


        courseVm.addAnnouncement = addAnnouncement;
        courseVm.toAnnouncements = toAnnouncements;
        courseVm.toPeople = toPeople;
        courseVm.toAnnouncement = toAnnouncement;
        courseVm.toggleFormVisibility = toggleFormVisibility;

        activate();

        function activate() {
            var courseId = $stateParams.id;
            courseVm.loggedUser = sessionService.getUser();
            courseService.findOne(courseId)
                .then(function(response) {
                    courseVm.course.name = response.data.name;
                    courseVm.announcements = response.data.announcements;
                    courseVm.people = response.data.users;
                });
        }

        function toAnnouncements(){
            $state.go('main.course.announcements')
        }

        function toPeople() {
            $state.go('main.course.people')
        }

        function toAnnouncement(announcement) {
            $state.go('main.course.announcement', {announcementId: announcement.id})
        }

        function toggleFormVisibility() {
            courseVm.isAnnouncementFormVisible = !courseVm.isAnnouncementFormVisible;
        }

        function addAnnouncement(){
            courseService.addAnnouncement(courseId, courseVm.formAnnouncement)
                .then(function(response) {
                    activate();
                    toggleFormVisibility();
                }).catch(function(error) {
                    //TODO: change error function
                    console.log("Failed");
            })
        }
    }
}(angular));