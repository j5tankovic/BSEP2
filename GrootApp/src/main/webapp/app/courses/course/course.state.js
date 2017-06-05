(function(angular){
    'use strict';

    angular.module('groot')
        .config(function ($stateProvider) {
            $stateProvider
                .state('main.course', {
                    url: '/courses/:id',
                    templateUrl: 'app/courses/course/course.html',
                    controller: 'CourseCtrlAs',
                    controllerAs: 'courseVm'
                });
        });
}(angular));