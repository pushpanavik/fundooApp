app.factory('userservice', function($http) {
     
	var serviceobj =[];
	
	serviceobj.registerModel = function(user) {
		
		return $http({
			method : "POST",
			url : "http://localhost:8080/fundoo/user/registerUser",
			data : user
		}).then(function successCallback(response) {
			console.log(response.data)
			console.log("Check your mail to activate your account ");
			

		}, function errorCallback(response) {
			console.log(response);
		});
	}
		
	serviceobj.loginModel=function(user){
		return $http({
			method: "POST",
			url: "http://localhost:8080/fundoo/user/login",
			data:user
		}).then (function successCallback(response){
			console.log(response.data);
			console.log("successfully login");
		},function errorCallback(response){
			console.log(response.data)
		});
	}
	
	
	serviceobj.forgotModel=function(user){
		return $http({
			method:"POST",
			url:"http://localhost:8080/fundoo/user/forgotPassword",
			data:user
		}).then (function successCallback(response){
			console.log(response.data);
			console.log("check your email for login");
		},function errorCallback(response){
			console.log(response.data)
		});
	}
	return serviceobj;
	
});