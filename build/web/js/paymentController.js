/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var paymentApp = angular.module('cart',[])
paymentApp.controller('paymentController',['$scope','$http','$window','$rootScope','prodList',function($scope,$http,$window,$rootScope,prodList){
    
//    $scope.$on('productList',function(event,data){
//        console.log("In the paymentController"+data);
//    });
     $scope.xxx=[];
    $scope.$watch(function () { return prodList.getProd(); }, function (newValue, oldValue) {
        if (newValue != null) {
            //update Controller2's xxx value
            $scope.xxx= newValue;
        }
    }, true);
    
    console.log("Entered PAyment Controller"+$scope.product+" $scope.xxx"+ $scope.xxx);
    $scope.close=function(){
        $window.location.href="index.html"
    }
   
    
}])

