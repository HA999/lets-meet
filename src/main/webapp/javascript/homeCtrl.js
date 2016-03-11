//Define user resource factory
letsmeetApp.factory('LoginService', function($resource) {
    return $resource('v1/:username', {username: '@username', password: '@password'});
});
	
letsmeetApp.controller('homeController', function($scope, $state, LoginService) {
    
    
});







