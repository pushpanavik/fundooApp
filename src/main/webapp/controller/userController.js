app
.controller('userCtrl',function (userservice,$scope,$state) {
	$scope.registerModel=function(){
	var user={
		    firstname: $scope.fname,
		    lastname: $scope.lname,
		    emailId: $scope.emailId,
		    password: $scope.password,
		    address: $scope.address,
		    phoneNumber:$scope.phoneNumber
		  };

		  userservice.registerModel(user)
		  console.log(user);
	}
		
	$scope.loginModel=function(){
		var user={
				emailId: $scope.emailId,
				password: $scope.password
		};
		userservice.loginModel(user)
		console.log(user);
	}
	
	$scope.forgotModel=function(){
		var user={
				emailId: $scope.emailId	
		};
		userservice.forgotModel(user)
		console.log($scope.forgotModel);
		console.log($scope.emailId)
		
	}
	$scope.resetModel=function(){
		var user={
				newpassword: $scope.newpassword
		};
		userservice.resetModel(user)
		console.log($scope.newpassword);
	}
	
	});

