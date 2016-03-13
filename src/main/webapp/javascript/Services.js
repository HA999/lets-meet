/* global letsmeetApp */

letsmeetApp.provider('dynamicStatesService', function($stateProvider) {
    this.$get = function() {
        return {
            addState: function(name, state) {
                $stateProvider.state(name, state);
            }
        };
    };
});


//Define user resource factory
letsmeetApp.factory('LoginService', function($resource) {
    return $resource('v1/:username', {username: '@username', password: '@password'});
});


//Define registration resource factory
letsmeetApp.factory('SignupService', function($resource) {
    return $resource('v1/signup', {newUser: '@newUser'});
});

letsmeetApp.factory('UniqueUsernameService', function($resource) {
    return $resource('v1/signup/:username', {username: '@username'});
});

letsmeetApp.factory('CountryNameService', function($resource) {
    return $resource('v1/countries');
});

letsmeetApp.factory('CityNameService', function($resource) {
    return $resource('v1/cities/:country', {country: '@country'});
});



//Define Categories resource factory
letsmeetApp.factory('CategoriesService', function($resource) {
    return $resource('v1/categories');
});

letsmeetApp.factory('SubCategoriesService', function($resource) {
    return $resource('v1/categories/subcategories/:category', {category: '@category'});
});

//Define Search resource factory
letsmeetApp.factory('SearchActivitiesService', function($resource) {
    return $resource('v1/search', {category: '@category', 
        subcategory: '@subcategory', city: '@city'});
});