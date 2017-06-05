(function (angular) {
    'use strict';
    angular
        .module('groot', ['ngRoute', 'ui.router', 'ngMaterial', 'ngAlertify'])
        .config(function ($stateProvider, $urlRouterProvider, $httpProvider, $mdThemingProvider) {
            $mdThemingProvider
                .theme('default')
                .primaryPalette('indigo')
                .accentPalette('blue-grey');


            $httpProvider.interceptors.push('authInterceptor');
            $urlRouterProvider.otherwise("/home");
            $stateProvider
                .state('main', {
                    url: '',
                    abstract: true,
                    controller: 'MainCtrlAs',
                    controllerAs: 'mainVm',
                    templateUrl: 'app/main.html'
                });
        })
        .run(function ($rootScope, $location, sessionService, roleService) {

            var publicRoutes = ["/login"];
            var restrictedRoutesForLoggedUser = ["/login"];
            var allowedRoutesForAdmin = ["/admin", "admin/courses", "admin/people"];
            var adminRoutes = ["/admin"];

            var routeIsIn = function (routes, currentRoute) {
                return _.find(routes, function (route) {
                    return _.startsWith(currentRoute, route);
                });
            };

            $rootScope.$on('$stateChangeSuccess', function (ev, to, toParams, from, fromParams) {
                var loggedUser = sessionService.getUser();

                if (routeIsIn(restrictedRoutesForLoggedUser, $location.url()) && loggedUser) {
                    if (fromParams){
                        from.url = from.url.replace(/:id/gm,fromParams.id);
                    }
                    $location.path(from.url);
                }

                if (!routeIsIn(publicRoutes, $location.url()) && !loggedUser) {
                    $location.path('/login');
                }
                else if (routeIsIn(adminRoutes, $location.url()) && !roleService.isAdmin(loggedUser)) {
                    $location.path(from.url);
                }
                else if (!routeIsIn(allowedRoutesForAdmin, $location.url()) && roleService.isAdmin(loggedUser)) {
                    $location.path("/admin");
                }
            });
        });



}(angular));