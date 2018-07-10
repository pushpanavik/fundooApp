app.factory('UserFactory',function($http)
	{
	return {
		getData:function(){
			return hhtp.Post('/registerUser').then (function successCallBack(response){
				$scope.user=response.data;
			},
			function errorCallBack(response){
				var result =response.data;
			});
		}
	}
		})




	
