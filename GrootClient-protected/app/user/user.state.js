(function(angular){
    'use strict';

    angular.module('groot')
        .config(function ($stateProvider) {
            $stateProvider
                .state('main.course.people', {
                    url: '/people',
                    templateUrl: 'app/user/coursePeople.html',
                    controller: 'CourseCtrlAs',
                    controllerAs: 'vm'
                })
                .state('main.profile', {
                    url: '/profile',
                    templateUrl: 'app/user/profile.html',
                    controller: 'UserCtrlAs',
                    controllerAs: 'userVm'
                })
        });

}(angular));