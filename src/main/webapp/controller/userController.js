app.controller('UserCtrl',function($scope,userservice)
{
$scope.registerModel=
	{
     firstname:"",
     lastname:"",
     emailId:"",
     password:"",
     address:"",
     phoneNumber:""
	}
$scope.register=function(registerModel)
{
 console.log(angular.toJson(registerModel));	
 userservice.getData(registerModel);
 
}

 });
