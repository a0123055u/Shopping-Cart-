

var nameApp = angular.module("login",[]).controller("loginController",function($scope,$http,$window){
   // $scope.userName;
  //  $scope.passWord;
  $scope.sSignUp= false;
  $scope.login=true;
  
    
    
    $scope.Login = function(){
         $scope.error=false;
         $scope.msg=[];
        if(!$scope.userName){
             $scope.error=true;
             $scope.msg.push("User Name empty");
            
        }
           
        if(!$scope.passWord){
            $scope.error=true;
             $scope.msg.push("password empty");
        }
           
       
        console.log($scope.userName);
        if(!$scope.error){
             $http.post('login',JSON.stringify({'userName':$scope.userName,'passWord':$scope.passWord})
                ).success(function(data){
                    console.log(data);
                    if(data!=0)
                        $window.location.href=data;
                    else
                        alert("Wrong user Name or Password");
                }).error(function(data){
                    console.log(data);
                    if(data===0){
                        alert("Wrong User Name or Password")
                    }
//                    alert("Wrong User Name or Password");
                })    
        }
                   
//            url:'login',
//            data:JSON.stringify({'userName':$scope.userName, 'passWord':$scope.passWord}),
//            headers:{'Content-Type':'application/json'},
//            dataType:'json'//            
//        }).success(function (data){
//            $window.location.href=data;
//            //$location.path('/shopping ');
//        })
//
    }
    
    
    $scope.signUp = function(){
         $scope.login=false;
        $scope.sSignUp=true;
       
         $scope.msg="";
         $scope.SIGNUP=true;
    }
    $scope.sSignUpSubmit = function(){
//        $scope.login=true;
             $scope.sError=false;
             $scope.msg=[];
             $scope.sMsg=[];
        if(!$scope.sUserName){
             $scope.sError=true;
             $scope.sMsg.push("User Name empty");
            
        }
           
        if(!$scope.sPassWord){
            $scope.sError=true;
             $scope.sMsg.push("password empty");
        }
        if(!$scope.sEmailId){
            $scope.sError=true;
            $scope.sMsg.push("Email Id is Empty")
        }
        if(!$scope.sError){
            $http.post('signup',JSON.stringify({'sUserName':$scope.sUserName,'sPassWord':$scope.sPassWord,
                'sEmailId':$scope.sEmailId})).success(function(data){
                console.log("------------"+data);
                if(data!=0){
//                    $window.location.href=data;
                    $scope.login=true;
                    $scope.error=true;
                    $scope.msg.push("Created User Successfully");
                     $scope.sSignUp=false;
                }
                    
                }).error(function(data){
                    console.log(data);
                })
        }
        //need to implement this as it is after the inser function for sign up is success and upon showing confirmation.
//        $scope.sSignUp=$scope.login?false:true  
    }
    $scope.cancel=function(){
        $scope.userName="";
        $scope.passWord="";
         $scope.msg="";
    }
    
});



