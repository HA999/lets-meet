/* global letsmeetApp */



letsmeetApp.controller('registrationController', function($scope, SignupService, CountryNameService, CityNameService,
        UniqueUsernameService, $rootScope, $filter, $state, dynamicStatesService) {
	
        $scope.cities;
        $scope.signUpUser;
        $scope.isUserNameTaken = false;
        
        //Get country list
        $scope.countries = CountryNameService.query();
        
              
        $scope.getCities = function(country){
		//Get cities by country
		$scope.cities = CityNameService.query({country: country});
	};
        
	$scope.register = function(){
            var newUser = $scope.signUpUser;
            
            newUser.dateofbirth = $filter('date')($scope.signUpUser.dateofbirth, 'yyyy-MM-dd');
            
            var uniqueUsernameQuary = UniqueUsernameService.get({username: newUser.username});
            uniqueUsernameQuary.$promise.then(function signup() {
                console.log("Success: ");
                var signupQuery = SignupService.save(newUser);
            
                signupQuery.$promise.then(function() {
                    $scope.isUserNameTaken = false;
                    $rootScope.currentUser = newUser.username; 
                    dynamicStatesService.addState(newUser.username, {
                        url: '/' + newUser.username,
                        templateUrl: 'partials/search.html',
                        controller: 'searchController',
                        data: {
                            requireLogin: true
                        }
                    });
                $scope.isNotLoggedInObj.value = false;
                $state.go(newUser.username);

                }, function cantSignup(response) {//TODO: Write error to user
                    console.log("Error: " + response.data);
                });
            }, function userNameTaken(response) {
                //TODO: Write error to user - "Username is taken"
                $scope.isUserNameTaken = true;
                console.log("Error: " + response.data);
            });
	};
});

//letsmeetApp.controller('registrationController', function($scope) {
    //$scope.user = { username: "Olya", password: "Poko"};
//		
//	$scope.signUpUser;
//
//	$scope.register = function(){
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
//	}
//		
    // $scope.signup = function() {
		// //go to signup page
	// };
//});


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

