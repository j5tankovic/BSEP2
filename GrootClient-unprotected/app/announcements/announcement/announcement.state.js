(function(angular){
    'use strict';

    angular.module('groot')
        .config(function ($stateProvider) {
            $stateProvider
                .state('main.course.announcements', {
                    url: '/announcements',
                    templateUrl: 'app/announcements/announcements.html',
                    controller: 'CourseCtrlAs',
                    controllerAs: 'courseVm',
                })
                .state('main.course.announcement', {
                    url: '/announcements/:announcementId',
                    templateUrl: 'app/announcements/announcement/announcement.html',
                    controller: 'AnnouncementCtrlAs',
                    controllerAs: 'announcementVm'
                })
        });
}(angular));