app
.controller('userCtrl',function (userservice,$scope,$state, $stateParams,$location,$window) {
	var baseUrl="http://localhost:8080/fundoo/user/";

	$scope.registerModel=function(){
	var user={
		    firstname: $scope.fname,
		    lastname: $scope.lname,
		    emailId: $scope.emailId,
		    password: $scope.password,
		    address: $scope.address,
		    phoneNumber:$scope.phoneNumber
		  };
      localStorage.register="registerModel";
      console.log(localStorage.register);
      localStorage.setItem("emailId",$scope.emailId)
      localStorage.setItem("password",$scope.password)

			var url="baseUrl" + "registerUser";
		  userservice.registerModel(user,url)
			.then(function successCallback(response) {
				console.log(response)
				$window.alert("Check your mail to activate your account ");
			}, function errorCallback(response) {
				console.log(response);
			});
	}
		
	$scope.loginModel=function(){
		var user={
				emailId: $scope.emailId,
				password: $scope.password
		};
		var url=baseUrl +"login";
		userservice.loginModel(user,url)
		.then (function successCallback(response){
			console.log(response.data);
			console.log("successfully login");
			$state.go('home');
			
		},function errorCallback(response){
			console.log(response)
		});
		
	}
	
	$scope.forgotModel=function(){
		var user={
				emailId: $scope.emailId	
		};
		var url=baseUrl +"forgotPassword";
		userservice.forgotModel(user,url)
		.then (function successCallback(response){
			console.log(response);
							
			$window.alert("check your email for login");
		},function errorCallback(response){
			console.log(response.data)
		});
		console.log($scope.forgotModel);
		console.log($scope.emailId)
		
	}
	$scope.resetModel=function(){
		var searchObject = $location.search();
		console.log(searchObject.token)
		var user={
				newpassword: $scope.newpassword
		};
		var url=baseUrl+"resetPassword";
		userservice.resetModel(user,searchObject.token,url)
		.then (function successCallback(response){
			console.log(response.data);
			$state.go('Login')
			
		},function errorCallback(response){
			console.log(response.data)
		});
		console.log($scope.newpassword);
	}
	
	});

