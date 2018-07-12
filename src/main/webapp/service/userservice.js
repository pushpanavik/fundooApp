app.factory('userservice', function($http,$window) {
     
	var serviceobj =[];
	
	serviceobj.registerModel = function(user,token) {
		
		return $http({
			method : "POST",
			url : "http://localhost:8080/fundoo/user/registerUser",
			data : user
		}).then(function successCallback(response) {
			console.log(response.data)
			localStorage.setItem("token",token);
			$window.alert("Check your mail to activate your account ");
			

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
			$window.alert("successfully login");
			
		},function errorCallback(response){
			console.log(response.data)
		});
	}
	
	
	serviceobj.forgotModel=function(user){
		return  $http({
			method:"POST",
			url:"http://localhost:8080/fundoo/user/forgotPassword",
			data:angular.toJson(user)
		}).then (function successCallback(response){
			console.log(response.data.message);
		
			console.log(token);
			
			$window.alert("check your email for login");
		},function errorCallback(response){
			console.log(response.data)
		});
	}
	
	serviceobj.resetModel=function(user){
		return $http({
			method: "POST",
			url:"http://localhost:8080/fundoo/user/resetPassword",
			data:angular.toJson(user)
		}).then (function successCallback(response){
			console.log(response.data);
			
			$window.alert("your password successfully updated");
		},function errorCallback(response){
			console.log(response.data)
		});
	}
	return serviceobj;
	
});