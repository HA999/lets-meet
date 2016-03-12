//Define an angular module for our app
var letsmeetApp = angular.module('letsmeetApp', ['ngResource', 'ui.router', 'ui.bootstrap']);


letsmeetApp.run(function ($rootScope, $state, $window) {
   
    $rootScope.$on('$stateChangeStart', function (event, toState, toParams) {
        var requireLogin = toState.data.requireLogin;
        
        if (requireLogin && typeof $rootScope.currentUser === 'undefined') {
            event.preventDefault();
            $window.alert("You must login first");
            $state.go('login');
        }
    });
});

//Define Routing for the application
letsmeetApp.config(['$stateProvider', '$urlRouterProvider', '$httpProvider',
    function ($stateProvider, $urlRouterProvider) {
        $stateProvider.
            state('welcome', {
                url: '/welcome',
                templateUrl: 'partials/search.html',
                controller: 'searchController',
                data: {
                    requireLogin: false
                }
            }).
            state('login', {
                url: '/login',
                templateUrl: 'partials/login.html',
                controller: 'loginController',
                data: {
                    requireLogin: false
                }
            }).
            state('signup', {
                url: '/signup',
                templateUrl: 'partials/signup.html',
                controller: 'registrationController',
                data: {
                    requireLogin: false
                }
            }).
            state('home', {
                url: '/home',
                templateUrl: 'partials/search.html',
                controller: 'homeController',
                data: {
                    requireLogin: true
                }
            }).
            state('home.activities', {
                url: '/activities',
                templateUrl: 'partials/activities.html',
                controller: 'activitiesController',
                data: {
                    requireLogin: true
                }
            }).
            state('home.logout', {
                url: '/logout',
                templateUrl: 'partials/logout.html',
                controller: 'homeController',
                data: {
                    requireLogin: true
                }
            });
            
            $urlRouterProvider.otherwise('/welcome');
    }]);

letsmeetApp.provider('dynamicStatesService', function($stateProvider) {
    this.$get = function() {
        return {
            addState: function(name, state) {
                $stateProvider.state(name, state);
            }
        };
    };
});

letsmeetApp.controller('navController', function ($scope) {
    $scope.isCollapsed = false;

    $scope.isNotLoggedInObj = {value: true };
});



letsmeetApp.controller('activitiesController', function($scope) {
	
    $scope.myActivities = [];	

    $scope.myActivities = [
            {
                "actId" : 2,
                "name" : "Movie Night",
                "userId" : 1,
                "subCatId" : 12,
                "createdTime" : null,
                "dateTime" : "2016/02/11 20:00",
                "country" : "Israel",
                "city" : "Tel Aviv",
                "about" : "Do you want to watch a movie with me??",
                "photo" : "C://Users//leppa//Desktop//10945526_10152665553430897_3459750655375000406_n.JPG",
                "category" : "dogs",
                "subCategory" : "walking"
            },
            {
                "actId" : 4,
                "name" : "Lets Dance",
                "userId" : 2,
                "subCatId" : 22,
                "createdTime" : null,
                "dateTime" : null,
                "country" : "Israel",
                "city" : "Jerusalem",
                "about" : "Music all the way",
                "photo" : "http://s3.amazonaws.com/libapps/accounts/12194/images/music_notes11.jpg",
                "category" : "dogs",
                "subCategory" : "walking"
            },
            {
                "actId" : 1,
                "name" : "Walking the dogs",
                "userId" : 2,
                "subCatId" : 41,
                "createdTime" : null,
                "dateTime" : null,
                "country" : "USA",
                "city" : "New York",
                "about" : "lets take a walk in the park",
                "photo" : "http://healthypetcheckup.org/assets/pets-fcb53b73523cd42be71be807ca0d6aaf.jpg",
                "category" : "dogs",
                "subCategory" : "walking"
            }              
        ];                                                  	
    //$scope.activities = function(){
            //call to the server for all my activities
    // }
    $scope.deleteActivity = function(){

    };
});


// letsmeetApp.controller('activitiesController', function($scope) {});
 


//do not forget to change "isNotLoggedIn" to false after login or signup
