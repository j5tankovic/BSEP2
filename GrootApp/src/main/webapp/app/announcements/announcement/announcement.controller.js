(function(angular) {
    'use strict';

    angular
        .module('groot')
        .controller('AnnouncementCtrlAs', AnnouncementCtrlAs);

    AnnouncementCtrlAs.$inject = ['$state', '$stateParams', 'courseService', 'sessionService'];

    function AnnouncementCtrlAs($state, $stateParams, courseService, sessionService) {

        var announcementVm = this;
        var courseId = $stateParams.id;
        var announcementId = $stateParams.announcementId;

        announcementVm.announcement = {};
        announcementVm.loggedUser = {};
        announcementVm.isAnnouncementFormVisible = false;

        announcementVm.toggleFormVisibility = toggleFormVisibility;
        announcementVm.edit = edit;
        announcementVm.delete = deleteOne;

        activate();

        function activate() {
            announcementVm.loggedUser = sessionService.getUser();
            courseService.findAnnouncement(courseId, announcementId)
                .then(function(response){
                    announcementVm.announcement = response.data;
                }).catch(function(error){
                    console.log(error);
            })
        }

        function toggleFormVisibility(){
            announcementVm.isAnnouncementFormVisible = !announcementVm.isAnnouncementFormVisible;
        }

        function edit() {
            courseService.updateAnnouncement(courseId, announcementVm.announcement)
                .then(function(response){
                    announcementVm.announcement = response.data;
                    toggleFormVisibility();
                }).catch(function(error){
                    console.log('Failed');
            })
        }

        function deleteOne() {
            courseService.deleteAnnouncement(courseId, announcementVm.announcement.id)
                .then(function(response) {
                    $state.go('main.course.announcements');
                }).catch(function(error) {
                console.log("Failed")
            });
        }
    }
}(angular));