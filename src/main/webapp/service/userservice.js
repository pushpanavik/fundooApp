app.factory('userservice', function($http,$window) {
     
	var serviceobj =[];
	
	serviceobj.registerModel = function(user,url) {
		
		return $http({
			method : "POST",
			url : url,
			data : user
		})
	}
		
	serviceobj.loginModel=function(user,url){
		return $http({
			method: "POST",
			url: url,
			data:user
		})
	}
	
	serviceobj.forgotModel=function(user,url){
		return  $http({
			method:"POST",
			url:url,
			data:angular.toJson(user)
		})
	}
	
	serviceobj.resetModel=function(user,token,url){
		return $http({
			method: "POST",
			url:url,
			headers: {
				   'token':token
				 },
			data:angular.toJson(user),
			
		})
	}
	return serviceobj;
	
});