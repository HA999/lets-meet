/* global letsmeetApp */


	
letsmeetApp.controller('loginController', function($rootScope, $scope, $state, LoginService, dynamicStatesService) {
    
    $scope.login = function () {
        var user = {
            username: $scope.userToLogin.username, 
            password: $scope.userToLogin.password
    };        
        
    //Post login
    var query = LoginService.save({username: user.username, 
        password: user.password});

    query.$promise.then(function () {
        $rootScope.currentUser = user.username;
        dynamicStatesService.addState(user.username, {
            url: '/' + user.username,
            templateUrl: 'partials/search.html',
            controller: 'searchController',
            data: {
                requireLogin: true
            }
        });
        $scope.isNotLoggedInObj.value = false;
        $state.go(user.username);
        
        }, function (response) {//TODO: Write error to user
            console.log("Error " + response.data);
        });
    };
});



////﻿var letsmeetApp = angular.module('letsmeetApp', ['ngRoute', 'ui.bootstrap']);
//
//letsmeetApp.controller('loginController', function($scope) {
//	
//	// var loggedInUser = this;
//	$scope.loggedInUser;
//	
//	$scope.login = function() {
//		// AuthenticationService.Login(vm.username, vm.password, function (response) {
//			// if (response.success) {
//				// AuthenticationService.SetCredentials(vm.username, vm.password);
//				// $location.path('/');
//			// } else {
//				// FlashService.Error(response.message);
//				// vm.dataLoading = false;
//			// }
//		// });
//		
//		// loggedInUser.user_Id = מה שתחזיר הפונקציה אם הצליחה
//	};
//});



