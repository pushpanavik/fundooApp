app.factory('userservice',function($http,$state)
	{
	return {
		getData:function(){
			return http.Post('/user/registerUser').then (function successCallBack(response){
				$scope.user=response.data;
				console.log(response);
				$scope.status=response.status;
				$scope.headers=response.headers();					
			},
			function errorCallBack(response){
				var result =response.data;
				console.log("could not add user");
			});
		}
	}
	return {
		
	}
		})




	
