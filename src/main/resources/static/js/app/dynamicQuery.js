myApp.controller('dynamicQueryController' , function ($scope, $http) {
        console.log("dynamic start...!");
        $scope.firstname="Raymond";
        //sample query
        $scope.sql="select site_country, exam_code, "
        + " count(case when grade='pass' then grade end) as pass, "
        + " count(grade) as total "
        + " from cert_exam_result "
        + " group by site_country, exam_code "
        + " order by site_country, exam_code ";

        //2. Search Button Click method
        $scope.searchDynamic = function () {
               console.log("Dynamic Search button pressed!");

               var path = "cert/getDynamicQueryResult/"+ $scope.sql;

               $http.get(path)
                      .then(function successCallback(response){
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

               var path = "cert/addDynamicTab/DTAB1/MyReport/"+ $scope.sql;

               $http.get(path)
                           .then(function successCallback(response){
                           $scope.response = response.data; //response JSON
                           console.log($scope.data);
                           //TODO: show the Tab
                     }, function errorCallback(response){
                           console.log("Unable to perform get request");
                     });
        };

});