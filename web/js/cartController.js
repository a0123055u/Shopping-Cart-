/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var cartApp= angular.module('cart',[]);
cartApp.controller('cartController',['$scope','$http','$window','$rootScope','prodList',function($scope,$http,$window,$rootScope,prodList){
    $scope.products=[];
     $scope.cart=[];
     $scope.cartQuantity=0;
    $http.get('product').success(function(data){        
        console.log(data);
          $scope.records=[];
        $scope.products=data;
         angular.forEach($scope.products, function(value, key){
                var val = value;
                console.log("var"+val)
                if(key!=="error")
                    $scope.records.push({'name':key,'price':value,'action':false,'qty':0});
                else
                    $window.location.href = value;
                    
                
                console.log("record"+$scope.records)
                $scope.products=$scope.records;
   });
      
        
    }).error(function(data){
        console.log(data);
        $window.location.href=data;
    })
  
    
    $scope.addTotal= function(qty,price,product){    
       
        for(var i=0;i<qty;i++){
             $scope.cartQuantity++;
        }
          
            product.qty=qty;          
        $scope.total += price*qty; 
        product.action=true;
         var name=product.name;        
         $scope.cart.push({name:product.name,price:product.price,qty:product.qty});
         $scope.prod=$scope.cart;     
       
    }
     $scope.clearSelection= function(sum,product,index){
         if($scope.total-sum>=0){
              $scope.total -= sum; 
//              $scope.cart-=product;

            for(var i=0;i<product.qty;i++){
             $scope.cartQuantity--;
             
             }
            var spliceIndex;
            var spliceKey;
            angular.forEach($scope.prod,function(value,key){
            console.log(value);
            console.log(key);
     if(value.name==product.name)
     {
         spliceIndex=key;
          console.log("Before"+$scope.cart);
         $scope.cart.splice(spliceIndex,1); 
         console.log($scope.cart);
            return;
     }
})
               
         }           
        else
            $scope.total=0;        
         $scope.subTotal="";
         product.action=false;
     }
     
     $scope.checkOut = function(product){
         console.log("Entered"+product);
         var prod = $scope.prod;
         angular.forEach(product,function(value,key){
             console.log("name "+value.name+" "+"qty "+value.qty+" "+"Price "+value.price);
             console.log(key);
             console.log(value);
         });
      
         // $http.get('product').success(function(data){        
         $http.post('payment',JSON.stringify({"prd":[product],"totalNet":$scope.total,"qtys":$scope.cartQuantity})).success(function(data){
                  if(data!=null)
                  {
                       $scope.$broadcast('productList',product);
                      //$rootScope.$emit('productList',product);
                      prodList.setProd(product);
                       $window.location.href=data;
                       
                  }
                     
                        //console.log(data.Success+" "+data.url);
//             angular.forEach(data, function(value, key){
//                 console.log("Entered success");
//                 console.log("Key "+key+ " "+ "Value "+value)
//                 if(data.url==="url")
//                     $window.location.href=value;             
            
             
                //});     
        }).error(function(data){
           
                 console.log("Error in Payment"+data);
             });
     } 
}]);
cartApp.service('prodList',function(){
  var prod=[];
    return{
        getProd:function(){
            return prod;
        },
        setProd:function(data){
            prod=data;
        }
    };
});

//cartApp.controller('paymentController',['$scope','$http','$window','$rootScope','prodList','$controller',function($scope,$http,$window,$rootScope,prodList,$controller){
//        $controller('cartController', {$scope: $scope});
//        
//        console.log($scope.cart);
//        console.log("cartCont");
//        
//       $scope.$on('productList',function(event,data){
//        console.log("In the paymentController"+data);
//    });
//     $scope.xxx=[];
//    $scope.$watch(function () { return prodList.getProd(); }, function (newValue, oldValue) {
//        if (newValue != null) {
//            //update Controller2's xxx value
//            $scope.xxx= newValue;
//        }
//    }, true);
//    
//    console.log("Entered PAyment Controller"+$scope.product+" $scope.xxx"+ $scope.xxx);
//    $scope.close=function(){
//        $window.location.href="index.html"
//    }
//   
//    
//}])


