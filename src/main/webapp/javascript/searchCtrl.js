/* global letsmeetApp */


letsmeetApp.controller('searchController', function($scope, $rootScope, 
        CategoriesService, SubCategoriesService, CountryNameService, 
        CityNameService, SearchActivitiesService) {
    
    $scope.oneAtATime = true;
    
    $scope.isNotLoggedIn = (typeof $rootScope.currentUser === 'undefined');
    
    $scope.categories = CategoriesService.query();
    
    $scope.getSubCat = function(category) {
        $scope.subCategories = SubCategoriesService.query({category: category});
    };
    
    $scope.countries = CountryNameService.query();;
    
    $scope.getCities = function(country) {
        $scope.cities = CityNameService.query({country: country});  
    };
    
    $scope.search = function(category, subCategory, country, city) {
        $scope.activities = SearchActivitiesService.query({category: category, 
            subcategory: subCategory, city: city});
    };
    
//    $scope.selectedSubCategory = null;
//    $scope.selectedCity = null;
//    $scope.activities = [];




//$scope.categories = ["music", "movies", "pets", "sport"];
//$scope.subCategories = ["cats", "dogs", "books"];
//$scope.countries = ["Israel", "USA"];
//$scope.cities = ["Tel Aviv", "Jerusalem"];
//
//$scope.search = function(selectedCategory, selectedSubCategory, selectedCountry, selectedCity){
//        //   שולח שאילתא עם כל הפרטים 
//    $scope.activities = [
//            {
//                "actId" : 2,
//                "name" : "Movie Night",
//                "userId" : 1,
//                "subCatId" : 12,
//                "createdTime" : null,
//                "dateTime" : "2016/02/11 20:00",
//                "country" : "Israel",
//                "city" : "Tel Aviv",
//                "about" : "Do you want to watch a movie with me??",
//                "photo" : "C://Users//leppa//Desktop//10945526_10152665553430897_3459750655375000406_n.JPG"
//            },
//            {
//                "actId" : 4,
//                "name" : "Lets Dance",
//                "userId" : 2,
//                "subCatId" : 22,
//                "createdTime" : null,
//                "dateTime" : null,
//                "country" : "Israel",
//                "city" : "Jerusalem",
//                "about" : "Music all the way",
//                "photo" : "http://s3.amazonaws.com/libapps/accounts/12194/images/music_notes11.jpg"
//            },
//            {
//                "actId" : 1,
//                "name" : "Walking the dogs",
//                "userId" : 2,
//                "subCatId" : 41,
//                "createdTime" : null,
//                "dateTime" : null,
//                "country" : "USA",
//                "city" : "New York",
//                "about" : "lets take a walk in the park",
//                "photo" : "http://healthypetcheckup.org/assets/pets-fcb53b73523cd42be71be807ca0d6aaf.jpg"
//            }
//        ];		
//    };
        
//        $http.get("url of get categories").then(function(response) {
//        $scope.categories = response.data;
//    });
//	$scope.getSubCat = function() {
//        //do get for sub categories
//    };
});
