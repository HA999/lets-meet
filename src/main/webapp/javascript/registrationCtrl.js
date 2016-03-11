
var letsmeetApp = angular.module('letsmeetApp', ['ngRoute', 'ui.bootstrap']);

letsmeetApp.controller('registrationController', function($scope) {
    //$scope.user = { username: "Olya", password: "Poko"};
		
	$scope.signUpUser;

	$scope.register = function(){
		// UserService.Create(vm.user)
			// .then(function (response) {
				// if (response.success) {
					// FlashService.Success('Registration successful', true);
					// $location.path('/login');
				// } else {
					// FlashService.Error(response.message);
					// vm.dataLoading = false;
				// }
			// });
	}
		
    // $scope.signup = function() {
		// //go to signup page
	// };
});


// var app = angular.module('myApp', []);
// app.controller('myCtrl', function($scope, $http) {
    // $http.get("wrongfilename.htm")
    // .then(function(response) {
        // //First function handles success
        // $scope.content = response.data;
    // }, function(response) {
        // //Second function handles error
        // $scope.content = "Something went wrong";
    // });
// });

