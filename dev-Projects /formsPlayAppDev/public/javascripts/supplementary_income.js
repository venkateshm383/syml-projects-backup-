/**
 * This is for supplementary_income page js file
 */
var app = angular.module("demoApp", ['ui.bootstrap']);
    app.controller('DemoController', function ($scope) {
        init();
        function init() {
            $scope.change = function () {
               
            };
        }
        
        $scope.singleModel = 1;
		
		  $scope.supplIncomeSource = {
			LivingAllow:false,
			Bonus:false,
			VehicleAllow:false,
			Commission:false
		  };
			
		  $scope.supplementaryIncomeSource = [];

		  $scope.$watchCollection('supplIncomeSource', function () {
		    $scope.supplementaryIncomeSource = [];
		    angular.forEach($scope.supplIncomeSource, function (value, key) {
		      if (value) {
		        $scope.supplementaryIncomeSource.push(key);
		      }
		    });
		  });
        
        
        $scope.singleModel = 1;
		
		  $scope.additionalSource = {
			Investments:false,
			Pension:false,
			Maternity:false,
			Other:false
		  };
			
		  $scope.additionalSources = [];

		  $scope.$watchCollection('additionalSource', function () {
		    $scope.additionalSources = [];
		    angular.forEach($scope.additionalSource, function (value, key) {
		      if (value) {
		        $scope.additionalSources.push(key);
		      }
		    });
		  });
		  
		  $scope.singleModel = 1;
			
		  $scope.abt = {
			Investments:false,
			Pension:false,
			Maternity:false,
			Other:false
		  };
			
		  $scope.aboutIncomeSources = [];

		  $scope.$watchCollection('abt', function () {
		    $scope.aboutIncomeSources = [];
		    angular.forEach($scope.abt, function (value, key) {
		      if (value) {
		        $scope.aboutIncomeSources.push(key);
		      }
		    });
		  });
		  
    });