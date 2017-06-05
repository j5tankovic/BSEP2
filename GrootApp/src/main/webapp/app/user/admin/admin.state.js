(function(angular){
    'use strict';

    angular.module('groot')
        .config(function ($stateProvider) {
            $stateProvider
                .state('main.admin', {
                    url: '/admin',
                    templateUrl: 'app/user/admin/admin.html',
                    controller: 'AdminCtrlAs',
                    controllerAs: 'vm'
                })
                .state('main.admin.users', {
                    url: '/users',
                    templateUrl: 'app/user/admin/adminPanelUsers.html',
                    controller: 'AdminCtrlAs',
                    controllerAs: 'vm'
                })
                .state('main.admin.courses', {
                    url: '/courses',
                    templateUrl: 'app/user/admin/adminPanelCourses.html',
                    controller: 'AdminCtrlAs',
                    controllerAs: 'vm'
                })
                .state('main.admin.course', {
                    url: '/courses/:courseId',
                    templateUrl: 'app/user/admin/adminPanelCourse.html',
                    controller: 'AdminCtrlAs',
                    controllerAs: 'vm'
                })
        });

}(angular));