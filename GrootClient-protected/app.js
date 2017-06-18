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
            var allowedRoutesForAdmin = ["/admin", "/courses", "/users", "/userToCourse"];

            var routeIsIn = function (routes, currentRoute) {
                return _.find(routes, function (route) {
                    return currentRoute === route;
                });
            };

            $rootScope.$on('$stateChangeStart', function (ev, to, toParams, from, fromParams) {
                var loggedUser = sessionService.getUser();

                if (routeIsIn(restrictedRoutesForLoggedUser, to.url) && loggedUser) {
                    $location.path(from.url).replace();
                }

                else if (!routeIsIn(publicRoutes, to.url) && !loggedUser) {
                    $location.path('/login');
                }
                else if (routeIsIn(allowedRoutesForAdmin, to.url) && !roleService.isAdmin(loggedUser)) {
                    $location.path("/unauthorized").replace();
                }
                else if (!routeIsIn(allowedRoutesForAdmin, to.url) && roleService.isAdmin(loggedUser)) {
                    $location.path("/unauthorized").replace();
                }


            });
        });



}(angular));