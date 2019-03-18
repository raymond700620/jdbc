myApp.controller('dynamicQueryController' , function ($scope, $http) {
        console.log("dynamic start...!");
       //sample query
        $scope.sql="select candidate_email, exam_code, site_country, exam_date, score, grade from cert_exam_result limit 10 #sample only ";
        $scope.response = [{}]; //clear the cache data

        //2. Search Button Click method
        $scope.searchDynamic = function () {
               console.log("Dynamic Search button pressed!");

               $http({
                       method: 'GET',
                       url: 'cert/getDynamicQueryResult',
                       params: {dsql: $scope.sql}
                    }).then(function successCallback(response){
                                $scope.response = response.data; //response JSON
                                //Need to handle zero record case - check size before next
                                $scope.thdata = response.data[0]; //the first record
                                console.log($scope.thdata);
                       }, function errorCallback(response){
                                console.log("Unable to perform get request");
                      });
        };

        //3. Publish Dynamic
        $scope.publihDynamic = function () {
               console.log("Dynamic Publish button pressed!");

               //var path = "cert/addDynamicTab/MyReport/"+ $scope.sql;

               $http({
                           method: 'GET',
                           url: 'cert/addDynamicTab',
                           params: {tabName: 'My Report', dsql: $scope.sql}
                         })
                           .then(function successCallback(response){
                           $scope.newTabID = response.data; //response JSON
                           console.log($scope.newTabID);
                           if ($scope.newTabID=="-1") {
                              alert("Reach the max no of dynamic report, you must delete old one before publih");
                           }
                           else {
                              $scope.$parent.hidedTabs[$scope.newTabID] = false;
                           }
                     }, function errorCallback(response){
                           console.log("Unable to perform get request");
                     });
        };

});