
myApp.controller('summaryController' , function ($scope, $http) {
        //console.log("I've been pressed3!");

        //1. Data initialisation for drop down
        $scope.years = [];
        $scope.startYear = 2010;
        var current_year = new Date().getFullYear();
        $scope.endYear = current_year;
        for(var i = $scope.startYear; i <= current_year; i++){
            $scope.years.push(i);
        }
        $scope.regions = ['ALL','America','APAC','EMEA'];
        $scope.region = 'ALL';

        $scope.charts = ["PIE","BAR"];
        $scope.chart = "PIE";
        $scope.hidePie = false;
        $scope.hideBar = true;

        //2. Search Button Click method
        $scope.searchCertSummary = function () {
              console.log("I've been pressed1!");

              var path = "cert/getCertSummaryByRegion/";
              if ($scope.region==='ALL') {
                path = "cert/getCertSummary/"+ $scope.startYear + "/" + $scope.endYear;
              }
              else {
                path = path + $scope.startYear + "/" + $scope.endYear+ "/" +$scope.region;
              }
              $http.get(path)
                   .then(function successCallback(response){
                        $scope.response = response.data;
                        $scope.getTotal();
                        $scope.getPieData("All courses",null);
                        console.log($scope.response);
                   }, function errorCallback(response){
                        console.log("Unable to perform get request");
                   });
        };

        //3. Calculation of total method
        $scope.getTotal = function () {
            //The last row in table --> overall information
                    //console.log("getTotal");
                    $scope.overallPass = 0;
                    $scope.overallFail = 0;
                    $scope.overallUnknown = 0;

                    console.log($scope.response);
                    $scope.response.forEach((element) => {
                        $scope.overallPass += element.passCount;
                        $scope.overallFail += element.failCount;
                        $scope.overallUnknown += element.unknownCount;
                    });


        }

        //4. Chart data method
        $scope.getPieData = function (course, row) {
                    $scope.labels = ["Pass", "Fail", "Unknown"];
                    $scope.chartTitle = course;
                    $scope.options = {
                            legend: { display: true },
                            responsive: true
                    }
                    if (course==='All courses') {
                        $scope.data = [$scope.overallPass, $scope.overallFail, $scope.overallUnknown];
                    }
                    else {
                        //$scope.data = [100,100,100]
                        $scope.data = [row.passCount,row.failCount,row.unknownCount];
                        console.log(row.pivotalExamCode);

                    }
        }

        //5. Chart switch
        $scope.switchChart = function () {
             if ($scope.chart == "PIE") {
                $scope.hidePie = false;
                $scope.hideBar = true;
             }
             else {
                $scope.hidePie = true;
                $scope.hideBar = false;
             }
        }


        //6. Initial Function
        $scope.searchCertSummary();

});