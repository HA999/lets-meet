
/* global letsmeetApp */

letsmeetApp.controller('activitiesController', function($scope) {
	
    $scope.isCollapsed = true;
    $scope.oneAtATime = true;
    $scope.activityRequests = [];

    $scope.myActivities = function(){
            //call to the server for all my activities
            return null;
    };

    $scope.deleteActivity = function(myActivity){
            //delete in the server and update the users activities 
    };

    $scope.updateActivity = function(myActivity){

    };

    $scope.getRequests = function(actId){
            $scope.isCollapsed = !$scope.isCollapsed;

            //get requests for the actId given
            //activityRequests = get();
    };

    $scope.updateRequest = function(request){
            request.accepted = !request.accepted;

            //update in server by sending the request virable
    };
});
