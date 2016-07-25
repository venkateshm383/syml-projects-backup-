/**
 * angular module for address and calculate for years.
 */
var app = angular.module("demoApp", [ 'ui.bootstrap' ]);
		app.controller('DemoController',function($scope) {
				
						console.log("inside the angular controller ");
						
							var empCount=document.getElementById("empCount").value;
							var slefEmpCount=document.getElementById("slefCount").value;

							$scope.employeecount = 0;
							$scope.slefemployeecount=0;
						
							
							$scope.addemploye = function($scope) {
								if (this.employeecount <9) {
									this.employeecount = this.employeecount + 1;
									
									console.log("employeeCount : "+this.employeecount );
								}

							}
							$scope.removeemploye = function($scope) {
								if (this.employeecount >0) {
									this.employeecount = this.employeecount - 1;
									
									console.log("Remove employeeCount : "+this.employeecount );
								}

							}
							$scope.addslefemploye = function() {
							
								if (this.slefemployeecount  < 9) {
									this.slefemployeecount = this.slefemployeecount + 1;
									console.log("self employeeCount : "+this.slefemployeecount );
								}

							}
							
							$scope.removeslefemploye = function() {
								if (this.slefemployeecount >0) {
									this.slefemployeecount = this.slefemployeecount - 1;
									console.log("Remove self employeeCount : "+this.slefemployeecount );
								}
							}
							
						});


/**
 * validate the form on submit
 * 
 * @returns {Boolean}
 */

function validateForm() {
	console.log("(.) inside the  validateForm() ");
	
	try {
		var employee = document.getElementById("employee").value;
		var selfEmployed = document.getElementById("slefemployee").value;
		var retVal = true;

		/*
		 * if (employee == "" ) { document.getElementById('emp').innerHTML = "<span
		 * style='color:red'>Please Select any one Option </span>";
		 * retVal=false; } else { document.getElementById("emp").innerHTML = ""; }
		 * alert("selfEmployed "+selfEmployed);
		 */
		/*
		 * if (selfEmployed == "") {
		 * document.getElementById('slefemp').innerHTML = "<span
		 * style='color:red'>Please Select any one Option</span>";
		 * retVal=false; } else { document.getElementById("slefemp").innerHTML =
		 * ""; }
		 */

		if (employee == "" || selfEmployed == "") {
			document.getElementById('totalEmprequired').innerHTML = "<span style='color:red'>You must fill in 3 years history (36 months). If you don't have 3 years work history,Please click check box to continue</span>";
			retVal = false;
		} else {
			document.getElementById("totalEmprequired").innerHTML = "";
		}

		var totalExp = 0;

		if (employee == "yes") {
			var totalEmployCount = document.getElementById("totalEmp").value;

			var empBusinessName1 = document.getElementById("empBusinessName1").value;
			if (!empBusinessName1) {
				document.getElementById('empBusinessName1').style.borderColor = "red";
				retVal = false;
			} else {
				document.getElementById("empBusinessName1").style.borderColor = "";
			}

			var empStartMonth1 = document.getElementById("empStartMonth1").value;
			if (!empStartMonth1) {
				document.getElementById('empStartMonth1').style.borderColor = "red";
				retVal = false;
			} else if (isNaN(empStartMonth1)) {
				document.getElementById('empStartMonth1').style.borderColor = "red";
				retVal = false;
			} else if (empStartMonth1 == 0) {
				document.getElementById('empStartMonth1').style.borderColor = "red";
				retVal = false;
			} else {
				totalExp = Math.floor(totalExp) + Math.floor(empStartMonth1);
				document.getElementById("empStartMonth1").style.borderColor = "";
			}

			var empPosition1 = document.getElementById("empPosition1").value;
			if (!empPosition1) {
				document.getElementById('empPosition1').style.borderColor = "red";
				retVal = false;
			} else {
				document.getElementById("empPosition1").style.borderColor = "";
			}

			if (totalEmployCount >= 1) {
				var empBusinessName1 = document
						.getElementById("empBusinessName2").value;
				if (!empBusinessName1) {
					document.getElementById('empBusinessName2').style.borderColor = "red";
					retVal = false;
				} else {
					document.getElementById("empBusinessName2").style.borderColor = "";
				}

				var empStartMonth1 = document.getElementById("empStartMonth2").value;
				if (!empStartMonth1) {
					document.getElementById('empStartMonth2').style.borderColor = "red";
					retVal = false;
				} else if (isNaN(empStartMonth1)) {
					document.getElementById('empStartMonth2').style.borderColor = "red";
					retVal = false;
				} else if (empStartMonth1 == 0) {
					document.getElementById('empStartMonth2').style.borderColor = "red";
					retVal = false;
				} else {
					totalExp = Math.floor(totalExp)
							+ Math.floor(empStartMonth1);
					document.getElementById("empStartMonth2").style.borderColor = "";
				}

				var empPosition1 = document.getElementById("empPosition2").value;
				if (!empPosition1) {
					document.getElementById('empPosition2').style.borderColor = "red";
					retVal = false;
				} else {
					document.getElementById("empPosition2").style.borderColor = "";
				}
			}

			if (totalEmployCount >= 2) {
				var empBusinessName1 = document
						.getElementById("empBusinessName3").value;
				if (!empBusinessName1) {
					document.getElementById('empBusinessName3').style.borderColor = "red";
					retVal = false;
				} else {
					document.getElementById("empBusinessName3").style.borderColor = "";
				}

				var empStartMonth1 = document.getElementById("empStartMonth3").value;
				if (!empStartMonth1) {
					document.getElementById('empStartMonth3').style.borderColor = "red";
					retVal = false;
				} else if (isNaN(empStartMonth1)) {
					document.getElementById('empStartMonth3').style.borderColor = "red";
					retVal = false;
				} else if (empStartMonth1 == 0) {
					document.getElementById('empStartMonth3').style.borderColor = "red";
					retVal = false;
				} else {
					totalExp = Math.floor(totalExp)
							+ Math.floor(empStartMonth1);
					document.getElementById("empStartMonth3").style.borderColor = "";
				}

				var empPosition1 = document.getElementById("empPosition3").value;
				if (!empPosition1) {
					document.getElementById('empPosition3').style.borderColor = "red";
					retVal = false;
				} else {
					document.getElementById("empPosition3").style.borderColor = "";
				}
			}
			if (totalEmployCount > 2) {
				var empBusinessName1 = document
						.getElementById("empBusinessName4").value;
				if (!empBusinessName1) {
					document.getElementById('empBusinessName4').style.borderColor = "red";
					retVal = false;
				} else {
					document.getElementById("empBusinessName4").style.borderColor = "";
				}

				var empStartMonth1 = document.getElementById("empStartMonth4").value;
				if (!empStartMonth1) {
					document.getElementById('empStartMonth4').style.borderColor = "red";
					retVal = false;
				} else if (isNaN(empStartMonth1)) {
					document.getElementById('empStartMonth4').style.borderColor = "red";
					retVal = false;
				} else if (empStartMonth1 == 0) {
					document.getElementById('empStartMonth4').style.borderColor = "red";
					retVal = false;
				} else {
					totalExp = Math.floor(totalExp)
							+ Math.floor(empStartMonth1);
					document.getElementById("empStartMonth4").style.borderColor = "";
				}

				var empPosition1 = document.getElementById("empPosition4").value;
				if (!empPosition1) {
					document.getElementById('empPosition4').style.borderColor = "red";
					retVal = false;
				} else {
					document.getElementById("empPosition4").style.borderColor = "";
				}
			}

			if (totalEmployCount > 3) {
				var empBusinessName1 = document
						.getElementById("empBusinessName5").value;
				if (!empBusinessName1) {
					document.getElementById('empBusinessName5').style.borderColor = "red";
					retVal = false;
				} else {
					document.getElementById("empBusinessName5").style.borderColor = "";
				}

				var empStartMonth1 = document.getElementById("empStartMonth5").value;
				if (!empStartMonth1) {
					document.getElementById('empStartMonth5').style.borderColor = "red";
					retVal = false;
				} else if (isNaN(empStartMonth1)) {
					document.getElementById('empStartMonth5').style.borderColor = "red";
					retVal = false;
				} else if (empStartMonth1 == 0) {
					document.getElementById('empStartMonth5').style.borderColor = "red";
					retVal = false;
				} else {
					totalExp = Math.floor(totalExp)
							+ Math.floor(empStartMonth1);
					document.getElementById("empStartMonth5").style.borderColor = "";
				}

				var empPosition1 = document.getElementById("empPosition5").value;
				if (!empPosition1) {
					document.getElementById('empPosition5').style.borderColor = "red";
					retVal = false;
				} else {
					document.getElementById("empPosition5").style.borderColor = "";
				}
			}

			if (totalEmployCount > 4) {
				var empBusinessName1 = document
						.getElementById("empBusinessName6").value;
				if (!empBusinessName1) {
					document.getElementById('empBusinessName6').style.borderColor = "red";
					retVal = false;
				} else {
					document.getElementById("empBusinessName6").style.borderColor = "";
				}

				var empStartMonth1 = document.getElementById("empStartMonth6").value;
				if (!empStartMonth1) {
					document.getElementById('empStartMonth6').style.borderColor = "red";
					retVal = false;
				} else if (isNaN(empStartMonth1)) {
					document.getElementById('empStartMonth6').style.borderColor = "red";
					retVal = false;
				} else if (empStartMonth1 == 0) {
					document.getElementById('empStartMonth6').style.borderColor = "red";
					retVal = false;
				} else {
					totalExp = Math.floor(totalExp)
							+ Math.floor(empStartMonth1);
					document.getElementById("empStartMonth6").style.borderColor = "";
				}

				var empPosition1 = document.getElementById("empPosition6").value;
				if (!empPosition1) {
					document.getElementById('empPosition6').style.borderColor = "red";
					retVal = false;
				} else {
					document.getElementById("empPosition6").style.borderColor = "";
				}
			}

			if (totalEmployCount > 5) {
				var empBusinessName1 = document
						.getElementById("empBusinessName7").value;
				if (!empBusinessName1) {
					document.getElementById('empBusinessName7').style.borderColor = "red";
					retVal = false;
				} else {
					document.getElementById("empBusinessName7").style.borderColor = "";
				}

				var empStartMonth1 = document.getElementById("empStartMonth7").value;
				if (!empStartMonth1) {
					document.getElementById('empStartMonth7').style.borderColor = "red";
					retVal = false;
				} else if (isNaN(empStartMonth1)) {
					document.getElementById('empStartMonth7').style.borderColor = "red";
					retVal = false;
				} else if (empStartMonth1 == 0) {
					document.getElementById('empStartMonth7').style.borderColor = "red";
					retVal = false;
				} else {
					totalExp = Math.floor(totalExp)
							+ Math.floor(empStartMonth1);
					document.getElementById("empStartMonth7").style.borderColor = "";
				}

				var empPosition1 = document.getElementById("empPosition7").value;
				if (!empPosition1) {
					document.getElementById('empPosition7').style.borderColor = "red";
					retVal = false;
				} else {
					document.getElementById("empPosition7").style.borderColor = "";
				}
			}

			if (totalEmployCount > 6) {
				var empBusinessName1 = document
						.getElementById("empBusinessName8").value;
				if (!empBusinessName1) {
					document.getElementById('empBusinessName8').style.borderColor = "red";
					retVal = false;
				} else {
					document.getElementById("empBusinessName8").style.borderColor = "";
				}

				var empStartMonth1 = document.getElementById("empStartMonth8").value;
				if (!empStartMonth1) {
					document.getElementById('empStartMonth8').style.borderColor = "red";
					retVal = false;
				} else if (isNaN(empStartMonth1)) {
					document.getElementById('empStartMonth8').style.borderColor = "red";
					retVal = false;
				} else if (empStartMonth1 == 0) {
					document.getElementById('empStartMonth8').style.borderColor = "red";
					retVal = false;
				} else {
					totalExp = Math.floor(totalExp)
							+ Math.floor(empStartMonth1);
					document.getElementById("empStartMonth8").style.borderColor = "";
				}

				var empPosition1 = document.getElementById("empPosition8").value;
				if (!empPosition1) {
					document.getElementById('empPosition8').style.borderColor = "red";
					retVal = false;
				} else {
					document.getElementById("empPosition8").style.borderColor = "";
				}
			}
		}

		
		console.log("The value of totalExp : "+totalExp);
		
		
		
			
			
		if (selfEmployed == 'yes') {
			var totalSlefEmp = document.getElementById("totalSlefEmp").value;

			// alert("total self Employe "+totalSlefEmp);

			var empBusinessName1 = document.getElementById("selfBusinessName1").value;
			if (!empBusinessName1) {
				document.getElementById('selfBusinessName1').style.borderColor = "red";
				retVal = false;
			} else {
				document.getElementById("selfBusinessName1").style.borderColor = "";
			}

			var empStartMonth1 = document.getElementById("selfStartMon1").value;
			if (!empStartMonth1) {
				document.getElementById('selfStartMon1').style.borderColor = "red";
				retVal = false;
			} else if (isNaN(empStartMonth1)) {
				document.getElementById('selfStartMon1').style.borderColor = "red";
				retVal = false;
			} else if (empStartMonth1 == 0) {
				document.getElementById('selfStartMon1').style.borderColor = "red";
				retVal = false;
			} else {
				totalExp = Math.floor(totalExp) + Math.floor(empStartMonth1);
				document.getElementById("selfStartMon1").style.borderColor = "";
			}

			var empPosition1 = document.getElementById("selfPosition1").value;
			if (!empPosition1) {
				document.getElementById('selfPosition1').style.borderColor = "red";
				retVal = false;
			} else {
				document.getElementById("selfPosition1").style.borderColor = "";
			}

			if (totalSlefEmp >= 1) {
				var empBusinessName1 = document
						.getElementById("selfBusinessName2").value;
				if (!empBusinessName1) {
					document.getElementById('selfBusinessName2').style.borderColor = "red";
					retVal = false;
				} else {
					document.getElementById("selfBusinessName2").style.borderColor = "";
				}

				var empStartMonth1 = document.getElementById("selfStartMon2").value;
				if (!empStartMonth1) {
					document.getElementById('selfStartMon2').style.borderColor = "red";
					retVal = false;
				} else if (isNaN(empStartMonth1)) {
					document.getElementById('selfStartMon2').style.borderColor = "red";
					retVal = false;
				} else if (empStartMonth1 == 0) {
					document.getElementById('selfStartMon2').style.borderColor = "red";
					retVal = false;
				} else {
					totalExp = Math.floor(totalExp)
							+ Math.floor(empStartMonth1);
					document.getElementById("selfStartMon2").style.borderColor = "";
				}

				var empPosition1 = document.getElementById("selfPosition2").value;
				if (!empPosition1) {
					document.getElementById('selfPosition2').style.borderColor = "red";
					retVal = false;
				} else {
					document.getElementById("selfPosition2").style.borderColor = "";
				}

			}

			if (totalSlefEmp > 1) {
				var empBusinessName1 = document
						.getElementById("selfBusinessName3").value;
				if (!empBusinessName1) {
					document.getElementById('selfBusinessName3').style.borderColor = "red";
					retVal = false;
				} else {
					document.getElementById("selfBusinessName3").style.borderColor = "";
				}

				var empStartMonth1 = document.getElementById("selfStartMon3").value;
				if (!empStartMonth1) {
					document.getElementById('selfStartMon3').style.borderColor = "red";
					retVal = false;
				} else if (isNaN(empStartMonth1)) {
					document.getElementById('selfStartMon3').style.borderColor = "red";
					retVal = false;
				} else if (empStartMonth1 == 0) {
					document.getElementById('selfStartMon3').style.borderColor = "red";
					retVal = false;
				} else {
					totalExp = Math.floor(totalExp)
							+ Math.floor(empStartMonth1);
					document.getElementById("selfStartMon3").style.borderColor = "";
				}

				var empPosition1 = document.getElementById("selfPosition3").value;
				if (!empPosition1) {
					document.getElementById('selfPosition3').style.borderColor = "red";
					retVal = false;
				} else {
					document.getElementById("selfPosition3").style.borderColor = "";
				}

			}

			if (totalSlefEmp > 2) {
				var empBusinessName1 = document
						.getElementById("selfBusinessName4").value;
				if (!empBusinessName1) {
					document.getElementById('selfBusinessName4').style.borderColor = "red";
					retVal = false;
				} else {
					document.getElementById("selfBusinessName4").style.borderColor = "";
				}

				var empStartMonth1 = document.getElementById("selfStartMon4").value;
				if (!empStartMonth1) {
					document.getElementById('selfStartMon4').style.borderColor = "red";
					retVal = false;
				} else if (isNaN(empStartMonth1)) {
					document.getElementById('selfStartMon4').style.borderColor = "red";
					retVal = false;
				} else if (empStartMonth1 == 0) {
					document.getElementById('selfStartMon4').style.borderColor = "red";
					retVal = false;
				} else {
					totalExp = Math.floor(totalExp)
							+ Math.floor(empStartMonth1);
					document.getElementById("selfStartMon4").style.borderColor = "";
				}

				var empPosition1 = document.getElementById("selfPosition4").value;
				if (!empPosition1) {
					document.getElementById('selfPosition4').style.borderColor = "red";
					retVal = false;
				} else {
					document.getElementById("selfPosition4").style.borderColor = "";
				}

			}

			if (totalSlefEmp > 3) {
				var empBusinessName1 = document
						.getElementById("selfBusinessName5").value;
				if (!empBusinessName1) {
					document.getElementById('selfBusinessName5').style.borderColor = "red";
					retVal = false;
				} else {
					document.getElementById("selfBusinessName5").style.borderColor = "";
				}

				var empStartMonth1 = document.getElementById("selfStartMon5").value;
				if (!empStartMonth1) {
					document.getElementById('selfStartMon5').style.borderColor = "red";
					retVal = false;
				} else if (isNaN(empStartMonth1)) {
					document.getElementById('selfStartMon5').style.borderColor = "red";
					retVal = false;
				} else if (empStartMonth1 == 0) {
					document.getElementById('selfStartMon5').style.borderColor = "red";
					retVal = false;
				} else {
					totalExp = Math.floor(totalExp)
							+ Math.floor(empStartMonth1);
					document.getElementById("selfStartMon5").style.borderColor = "";
				}

				var empPosition1 = document.getElementById("selfPosition5").value;
				if (!empPosition1) {
					document.getElementById('selfPosition5').style.borderColor = "red";
					retVal = false;
				} else {
					document.getElementById("selfPosition5").style.borderColor = "";
				}

			}
			if (totalSlefEmp > 4) {
				var empBusinessName1 = document
						.getElementById("selfBusinessName6").value;
				if (!empBusinessName1) {
					document.getElementById('selfBusinessName6').style.borderColor = "red";
					retVal = false;
				} else {
					document.getElementById("selfBusinessName6").style.borderColor = "";
				}

				var empStartMonth1 = document.getElementById("selfStartMon6").value;
				if (!empStartMonth1) {
					document.getElementById('selfStartMon6').style.borderColor = "red";
					retVal = false;
				} else if (isNaN(empStartMonth1)) {
					document.getElementById('selfStartMon6').style.borderColor = "red";
					retVal = false;
				} else if (empStartMonth1 == 0) {
					document.getElementById('selfStartMon6').style.borderColor = "red";
					retVal = false;
				} else {
					totalExp = Math.floor(totalExp)
							+ Math.floor(empStartMonth1);
					document.getElementById("selfStartMon6").style.borderColor = "";
				}

				var empPosition1 = document.getElementById("selfPosition6").value;
				if (!empPosition1) {
					document.getElementById('selfPosition6').style.borderColor = "red";
					retVal = false;
				} else {
					document.getElementById("selfPosition6").style.borderColor = "";
				}

			}
			if (totalSlefEmp > 5) {
				var empBusinessName1 = document
						.getElementById("selfBusinessName7").value;
				if (!empBusinessName1) {
					document.getElementById('selfBusinessName7').style.borderColor = "red";
					retVal = false;
				} else {
					document.getElementById("selfBusinessName7").style.borderColor = "";
				}

				var empStartMonth1 = document.getElementById("selfStartMon7").value;
				if (!empStartMonth1) {
					document.getElementById('selfStartMon7').style.borderColor = "red";
					retVal = false;
				} else if (isNaN(empStartMonth1)) {
					document.getElementById('selfStartMon7').style.borderColor = "red";
					retVal = false;
				} else if (empStartMonth1 == 0) {
					document.getElementById('selfStartMon7').style.borderColor = "red";
					retVal = false;
				} else {
					totalExp = Math.floor(totalExp)
							+ Math.floor(empStartMonth1);
					document.getElementById("selfStartMon7").style.borderColor = "";
				}

				var empPosition1 = document.getElementById("selfPosition7").value;
				if (!empPosition1) {
					document.getElementById('selfPosition7').style.borderColor = "red";
					retVal = false;
				} else {
					document.getElementById("selfPosition7").style.borderColor = "";
				}

			}

			if (totalSlefEmp > 6) {
				var empBusinessName1 = document
						.getElementById("selfBusinessName8").value;
				if (!empBusinessName1) {
					document.getElementById('selfBusinessName8').style.borderColor = "red";
					retVal = false;
				} else {
					document.getElementById("selfBusinessName8").style.borderColor = "";
				}

				var empStartMonth1 = document.getElementById("selfStartMon8").value;
				if (!empStartMonth1) {
					document.getElementById('selfStartMon8').style.borderColor = "red";
					retVal = false;
				} else if (isNaN(empStartMonth1)) {
					document.getElementById('selfStartMon8').style.borderColor = "red";
					retVal = false;
				} else if (empStartMonth1 == 0) {
					document.getElementById('selfStartMon8').style.borderColor = "red";
					retVal = false;
				} else {
					totalExp = Math.floor(totalExp)
							+ Math.floor(empStartMonth1);
					document.getElementById("selfStartMon8").style.borderColor = "";
				}

				var empPosition1 = document.getElementById("selfPosition8").value;
				if (!empPosition1) {
					document.getElementById('selfPosition8').style.borderColor = "red";
					retVal = false;
				} else {
					document.getElementById("selfPosition8").style.borderColor = "";
				}

			}

		}
		
		// alert("total Experince "+totalExp)
		console.log("The value of totalExp : "+totalExp);
		if (totalExp < 36) {
			document.getElementById('totalEmprequired').innerHTML = "<span style='color:red'>You must fill in 3 years history (36 months). If you don't have 3 years work history,Please click check box to continue</span>";
			retVal = false;
		} /*else if (retVal == false) {
			document.getElementById('totalEmprequired').innerHTML = "<span style='color:red'>Please fill all details </span>";

		}*/ else {
			retVal=true;
			document.getElementById("totalEmprequired").innerHTML = "";
		}
		// alert("retval : "+retVal);
	} catch (err) {
		// alert(err);
	}

	if (document.getElementById("agree").checked) {
		retVal = true;
		document.getElementById('totalEmprequired').innerHTML = "";
	} else {

	}

	return retVal;

}


/**
 * 
 */

$(document).ready(function() {
	console.log("inside for ready function  for  tool-tip ");
$('[data-toggle="tooltip"]').tooltip();
});

