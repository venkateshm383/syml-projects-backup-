
	function validateForm() {
		alert("employee ");

		try{
		var employee = document.getElementById("employee").value;
		var selfEmployed =  document.getElementById("slefemployee").value;
		var retVal = true;

		/*if (employee == ""
				) {
			document.getElementById('emp').innerHTML = "<span style='color:red'>Please Select any one Option  </span>";
			retVal=false;
		} else {
			document.getElementById("emp").innerHTML = "";
		}
		alert("selfEmployed "+selfEmployed);
 */
		/* if (selfEmployed == "") {
		document.getElementById('slefemp').innerHTML = "<span style='color:red'>Please Select any one Option</span>";
		retVal=false;
		} else {
	document.getElementById("slefemp").innerHTML = "";
	} */
		
		if (selfEmployed == "" && selfEmployed == "") {
			document.getElementById('totalEmprequired').innerHTML = "<span style='color:red'>You must fill in 3 years history (36 months). If you don't have 3 years work history, please let us know what else you were doing for the full 3 year period</span>";
			retVal=false;
			} else {
		document.getElementById("totalEmprequired").innerHTML = "";
		}
		
	var totalExp=0;
		

		if (employee == "yes") {
			var totalEmployCount=document.getElementById("totalEmp").value;

			var empBusinessName1 = document.getElementById("empBusinessName1").value;
			if (!empBusinessName1) {
				document.getElementById('empBusinessName1').style.borderColor= "red";
				retVal=false;
			} else {
				document.getElementById("empBusinessName1").style.borderColor = "";
			}

			var empStartMonth1 = document.getElementById("empStartMonth1").value;
			if (!empStartMonth1) {
				document.getElementById('empStartMonth1').style.borderColor = "red";
				retVal=false;
			}else if (	isNaN(empStartMonth1)   ) {
				document.getElementById('empStartMonth1').style.borderColor = "red";
				retVal=false;
			}else if (	empStartMonth1==0   ) {
				document.getElementById('empStartMonth1').style.borderColor = "red";
				retVal=false;
			}
			else {
				totalExp=Math.floor(totalExp)+Math.floor(empStartMonth1);
				document.getElementById("empStartMonth1").style.borderColor = "";
			}

			var empPosition1 = document.getElementById("empPosition1").value;
			if (!empPosition1) {
				document.getElementById('empPosition1').style.borderColor = "red";
				retVal=false;
			} else {
				document.getElementById("empPosition1").style.borderColor = "";
			}
			
			if(totalEmployCount>=1){
			var empBusinessName1 = document.getElementById("empBusinessName2").value;
			if (!empBusinessName1) {
				document.getElementById('empBusinessName2').style.borderColor= "red";
				retVal=false;
			} else {
				document.getElementById("empBusinessName2").style.borderColor = "";
			}

			var empStartMonth1 = document.getElementById("empStartMonth2").value;
			if (!empStartMonth1) {
				document.getElementById('empStartMonth2').style.borderColor = "red";
				retVal=false;
			}else if (	isNaN(empStartMonth1)   ) {
				document.getElementById('empStartMonth2').style.borderColor = "red";
				retVal=false;
			}else if (	empStartMonth1==0   ) {
				document.getElementById('empStartMonth2').style.borderColor = "red";
				retVal=false;
			}
			else {
				totalExp=Math.floor(totalExp)+Math.floor(empStartMonth1);
				document.getElementById("empStartMonth2").style.borderColor = "";
			}

			var empPosition1 = document.getElementById("empPosition2").value;
			if (!empPosition1) {
				document.getElementById('empPosition2').style.borderColor = "red";
				retVal=false;
			} else {
				document.getElementById("empPosition2").style.borderColor = "";
			}
			}
			
			if(totalEmployCount>=2){
				var empBusinessName1 = document.getElementById("empBusinessName3").value;
				if (!empBusinessName1) {
					document.getElementById('empBusinessName3').style.borderColor= "red";
					retVal=false;
				} else {
					document.getElementById("empBusinessName3").style.borderColor = "";
				}

				var empStartMonth1 = document.getElementById("empStartMonth3").value;
				if (!empStartMonth1) {
					document.getElementById('empStartMonth3').style.borderColor = "red";
					retVal=false;
				}else if (	isNaN(empStartMonth1)   ) {
					document.getElementById('empStartMonth3').style.borderColor = "red";
					retVal=false;
				}else if (	empStartMonth1==0   ) {
					document.getElementById('empStartMonth3').style.borderColor = "red";
					retVal=false;
				}
				else {
					totalExp=Math.floor(totalExp)+Math.floor(empStartMonth1);
					document.getElementById("empStartMonth3").style.borderColor = "";
				}

				var empPosition1 = document.getElementById("empPosition3").value;
				if (!empPosition1) {
					document.getElementById('empPosition3').style.borderColor = "red";
					retVal=false;
				} else {
					document.getElementById("empPosition3").style.borderColor = "";
				}
				}
			if(totalEmployCount>2){
				var empBusinessName1 = document.getElementById("empBusinessName4").value;
				if (!empBusinessName1) {
					document.getElementById('empBusinessName4').style.borderColor= "red";
					retVal=false;
				} else {
					document.getElementById("empBusinessName4").style.borderColor = "";
				}

				var empStartMonth1 = document.getElementById("empStartMonth4").value;
				if (!empStartMonth1) {
					document.getElementById('empStartMonth4').style.borderColor = "red";
					retVal=false;
				}else if (	isNaN(empStartMonth1)   ) {
					document.getElementById('empStartMonth4').style.borderColor = "red";
					retVal=false;
				}else if (	empStartMonth1==0   ) {
					document.getElementById('empStartMonth4').style.borderColor = "red";
					retVal=false;
				}
				else {
					totalExp=Math.floor(totalExp)+Math.floor(empStartMonth1);
					document.getElementById("empStartMonth4").style.borderColor = "";
				}

				var empPosition1 = document.getElementById("empPosition4").value;
				if (!empPosition1) {
					document.getElementById('empPosition4').style.borderColor = "red";
					retVal=false;
				} else {
					document.getElementById("empPosition4").style.borderColor = "";
				}
				}
			
			if(totalEmployCount>3){
				var empBusinessName1 = document.getElementById("empBusinessName5").value;
				if (!empBusinessName1) {
					document.getElementById('empBusinessName5').style.borderColor= "red";
					retVal=false;
				} else {
					document.getElementById("empBusinessName5").style.borderColor = "";
				}

				var empStartMonth1 = document.getElementById("empStartMonth5").value;
				if (!empStartMonth1) {
					document.getElementById('empStartMonth5').style.borderColor = "red";
					retVal=false;
				}else if (	isNaN(empStartMonth1)   ) {
					document.getElementById('empStartMonth5').style.borderColor = "red";
					retVal=false;
				}else if (	empStartMonth1==0   ) {
					document.getElementById('empStartMonth5').style.borderColor = "red";
					retVal=false;
				}
				else {
					totalExp=Math.floor(totalExp)+Math.floor(empStartMonth1);
					document.getElementById("empStartMonth5").style.borderColor = "";
				}

				var empPosition1 = document.getElementById("empPosition5").value;
				if (!empPosition1) {
					document.getElementById('empPosition5').style.borderColor = "red";
					retVal=false;
				} else {
					document.getElementById("empPosition5").style.borderColor = "";
				}
				}
			
			if(totalEmployCount>4){
				var empBusinessName1 = document.getElementById("empBusinessName6").value;
				if (!empBusinessName1) {
					document.getElementById('empBusinessName6').style.borderColor= "red";
					retVal=false;
				} else {
					document.getElementById("empBusinessName6").style.borderColor = "";
				}

				var empStartMonth1 = document.getElementById("empStartMonth6").value;
				if (!empStartMonth1) {
					document.getElementById('empStartMonth6').style.borderColor = "red";
					retVal=false;
				}else if (	isNaN(empStartMonth1)   ) {
					document.getElementById('empStartMonth6').style.borderColor = "red";
					retVal=false;
				}else if (	empStartMonth1==0   ) {
					document.getElementById('empStartMonth6').style.borderColor = "red";
					retVal=false;
				}
				else {
					totalExp=Math.floor(totalExp)+Math.floor(empStartMonth1);
					document.getElementById("empStartMonth6").style.borderColor = "";
				}

				var empPosition1 = document.getElementById("empPosition6").value;
				if (!empPosition1) {
					document.getElementById('empPosition6').style.borderColor = "red";
					retVal=false;
				} else {
					document.getElementById("empPosition6").style.borderColor = "";
				}
				}
			
			if(totalEmployCount>5){
				var empBusinessName1 = document.getElementById("empBusinessName7").value;
				if (!empBusinessName1) {
					document.getElementById('empBusinessName7').style.borderColor= "red";
					retVal=false;
				} else {
					document.getElementById("empBusinessName7").style.borderColor = "";
				}

				var empStartMonth1 = document.getElementById("empStartMonth7").value;
				if (!empStartMonth1) {
					document.getElementById('empStartMonth7').style.borderColor = "red";
					retVal=false;
				}else if (	isNaN(empStartMonth1)   ) {
					document.getElementById('empStartMonth7').style.borderColor = "red";
					retVal=false;
				}else if (	empStartMonth1==0   ) {
					document.getElementById('empStartMonth7').style.borderColor = "red";
					retVal=false;
				}
				else {
					totalExp=Math.floor(totalExp)+Math.floor(empStartMonth1);
					document.getElementById("empStartMonth7").style.borderColor = "";
				}

				var empPosition1 = document.getElementById("empPosition7").value;
				if (!empPosition1) {
					document.getElementById('empPosition7').style.borderColor = "red";
					retVal=false;
				} else {
					document.getElementById("empPosition7").style.borderColor = "";
				}
				}
			
			if(totalEmployCount>6){
				var empBusinessName1 = document.getElementById("empBusinessName8").value;
				if (!empBusinessName1) {
					document.getElementById('empBusinessName8').style.borderColor= "red";
					retVal=false;
				} else {
					document.getElementById("empBusinessName8").style.borderColor = "";
				}

				var empStartMonth1 = document.getElementById("empStartMonth8").value;
				if (!empStartMonth1) {
					document.getElementById('empStartMonth8').style.borderColor = "red";
					retVal=false;
				}else if (	isNaN(empStartMonth1)   ) {
					document.getElementById('empStartMonth8').style.borderColor = "red";
					retVal=false;
				}else if (	empStartMonth1==0   ) {
					document.getElementById('empStartMonth8').style.borderColor = "red";
					retVal=false;
				}
				else {
					totalExp=Math.floor(totalExp)+Math.floor(empStartMonth1);
					document.getElementById("empStartMonth8").style.borderColor = "";
				}

				var empPosition1 = document.getElementById("empPosition8").value;
				if (!empPosition1) {
					document.getElementById('empPosition8').style.borderColor = "red";
					retVal=false;
				} else {
					document.getElementById("empPosition8").style.borderColor = "";
				}
				}
		}
	
		
		if(selfEmployed=='yes'){
			var totalSlefEmp=document.getElementById("totalSlefEmp").value;

			alert("total self Employe "+totalSlefEmp);
			
		var empBusinessName1 = document.getElementById("selfBusinessName1").value;
		if (!empBusinessName1) {
			document.getElementById('selfBusinessName1').style.borderColor= "red";
			retVal=false;
		} else {
			document.getElementById("selfBusinessName1").style.borderColor = "";
		}

		var empStartMonth1 = document.getElementById("selfStartMon1").value;
		if (!empStartMonth1) {
			document.getElementById('selfStartMon1').style.borderColor = "red";
			retVal=false;
		}else if (	isNaN(empStartMonth1)   ) {
			document.getElementById('selfStartMon1').style.borderColor = "red";
			retVal=false;
		}else if (	empStartMonth1==0   ) {
			document.getElementById('selfStartMon1').style.borderColor = "red";
			retVal=false;
		}
		else {
			totalExp=Math.floor(totalExp)+Math.floor(empStartMonth1);
			document.getElementById("selfStartMon1").style.borderColor = "";
		}

		var empPosition1 = document.getElementById("selfPosition1").value;
		if (!empPosition1) {
			document.getElementById('selfPosition1').style.borderColor = "red";
			retVal=false;
		} else {
			document.getElementById("selfPosition1").style.borderColor = "";
		}
		
		var empBusinessName1 = document.getElementById("selfBusinessName1").value;
		if (!empBusinessName1) {
			document.getElementById('selfBusinessName1').style.borderColor= "red";
			retVal=false;
		} else {
			document.getElementById("selfBusinessName1").style.borderColor = "";
		}

		var empStartMonth1 = document.getElementById("selfStartMon1").value;
		if (!empStartMonth1) {
			document.getElementById('selfStartMon1').style.borderColor = "red";
			retVal=false;
		}else if (	isNaN(empStartMonth1)   ) {
			document.getElementById('selfStartMon1').style.borderColor = "red";
			retVal=false;
		}else if (	empStartMonth1==0   ) {
			document.getElementById('selfStartMon1').style.borderColor = "red";
			retVal=false;
		}
		else {
			totalExp=Math.floor(totalExp)+Math.floor(empStartMonth1);
			document.getElementById("selfStartMon1").style.borderColor = "";
		}

		var empPosition1 = document.getElementById("selfPosition1").value;
		if (!empPosition1) {
			document.getElementById('selfPosition1').style.borderColor = "red";
			retVal=false;
		} else {
			document.getElementById("selfPosition1").style.borderColor = "";
		}
		if(totalSlefEmp>=1){
			var empBusinessName1 = document.getElementById("selfBusinessName2").value;
			if (!empBusinessName1) {
				document.getElementById('selfBusinessName2').style.borderColor= "red";
				retVal=false;
			} else {
				document.getElementById("selfBusinessName2").style.borderColor = "";
			}

			var empStartMonth1 = document.getElementById("selfStartMon2").value;
			if (!empStartMonth1) {
				document.getElementById('selfStartMon2').style.borderColor = "red";
				retVal=false;
			}else if (	isNaN(empStartMonth1)   ) {
				document.getElementById('selfStartMon2').style.borderColor = "red";
				retVal=false;
			}else if (	empStartMonth1==0   ) {
				document.getElementById('selfStartMon2').style.borderColor = "red";
				retVal=false;
			}
			else {
				totalExp=Math.floor(totalExp)+Math.floor(empStartMonth1);
				document.getElementById("selfStartMon2").style.borderColor = "";
			}

			var empPosition1 = document.getElementById("selfPosition2").value;
			if (!empPosition1) {
				document.getElementById('selfPosition2').style.borderColor = "red";
				retVal=false;
			} else {
				document.getElementById("selfPosition2").style.borderColor = "";
			}
			
			var empBusinessName1 = document.getElementById("selfBusinessName2").value;
			if (!empBusinessName1) {
				document.getElementById('selfBusinessName2').style.borderColor= "red";
				retVal=false;
			} else {
				document.getElementById("selfBusinessName2").style.borderColor = "";
			}

			var empStartMonth1 = document.getElementById("selfStartMon2").value;
			if (!empStartMonth1) {
				document.getElementById('selfStartMon2').style.borderColor = "red";
				retVal=false;
			}else if (	isNaN(empStartMonth1)   ) {
				document.getElementById('selfStartMon2').style.borderColor = "red";
				retVal=false;
			}else if (	empStartMonth1==0   ) {
				document.getElementById('selfStartMon2').style.borderColor = "red";
				retVal=false;
			}
			else {
				totalExp=Math.floor(totalExp)+Math.floor(empStartMonth1);
				document.getElementById("selfStartMon2").style.borderColor = "";
			}

			var empPosition1 = document.getElementById("selfPosition2").value;
			if (!empPosition1) {
				document.getElementById('selfPosition2').style.borderColor = "red";
				retVal=false;
			} else {
				document.getElementById("selfPosition2").style.borderColor = "";
			}
			
		}
		
		if(totalSlefEmp>=2){
			var empBusinessName1 = document.getElementById("selfBusinessName3").value;
			if (!empBusinessName1) {
				document.getElementById('selfBusinessName3').style.borderColor= "red";
				retVal=false;
			} else {
				document.getElementById("selfBusinessName3").style.borderColor = "";
			}

			var empStartMonth1 = document.getElementById("selfStartMon3").value;
			if (!empStartMonth1) {
				document.getElementById('selfStartMon3').style.borderColor = "red";
				retVal=false;
			}else if (	isNaN(empStartMonth1)   ) {
				document.getElementById('selfStartMon3').style.borderColor = "red";
				retVal=false;
			}else if (	empStartMonth1==0   ) {
				document.getElementById('selfStartMon3').style.borderColor = "red";
				retVal=false;
			}
			else {
				totalExp=Math.floor(totalExp)+Math.floor(empStartMonth1);
				document.getElementById("selfStartMon3").style.borderColor = "";
			}

			var empPosition1 = document.getElementById("selfPosition3").value;
			if (!empPosition1) {
				document.getElementById('selfPosition3').style.borderColor = "red";
				retVal=false;
			} else {
				document.getElementById("selfPosition3").style.borderColor = "";
			}
			
			var empBusinessName1 = document.getElementById("selfBusinessName3").value;
			if (!empBusinessName1) {
				document.getElementById('selfBusinessName3').style.borderColor= "red";
				retVal=false;
			} else {
				document.getElementById("selfBusinessName3").style.borderColor = "";
			}

			var empStartMonth1 = document.getElementById("selfStartMon3").value;
			if (!empStartMonth1) {
				document.getElementById('selfStartMon3').style.borderColor = "red";
				retVal=false;
			}else if (	isNaN(empStartMonth1)   ) {
				document.getElementById('selfStartMon3').style.borderColor = "red";
				retVal=false;
			}else if (	empStartMonth1==0   ) {
				document.getElementById('selfStartMon3').style.borderColor = "red";
				retVal=false;
			}
			else {
				totalExp=Math.floor(totalExp)+Math.floor(empStartMonth1);
				document.getElementById("selfStartMon3").style.borderColor = "";
			}

			var empPosition1 = document.getElementById("selfPosition3").value;
			if (!empPosition1) {
				document.getElementById('selfPosition3').style.borderColor = "red";
				retVal=false;
			} else {
				document.getElementById("selfPosition3").style.borderColor = "";
			}
			
		}

		if(totalSlefEmp>=3){
			var empBusinessName1 = document.getElementById("selfBusinessName4").value;
			if (!empBusinessName1) {
				document.getElementById('selfBusinessName4').style.borderColor= "red";
				retVal=false;
			} else {
				document.getElementById("selfBusinessName4").style.borderColor = "";
			}

			var empStartMonth1 = document.getElementById("selfStartMon4").value;
			if (!empStartMonth1) {
				document.getElementById('selfStartMon4').style.borderColor = "red";
				retVal=false;
			}else if (	isNaN(empStartMonth1)   ) {
				document.getElementById('selfStartMon4').style.borderColor = "red";
				retVal=false;
			}else if (	empStartMonth1==0   ) {
				document.getElementById('selfStartMon4').style.borderColor = "red";
				retVal=false;
			}
			else {
				totalExp=Math.floor(totalExp)+Math.floor(empStartMonth1);
				document.getElementById("selfStartMon4").style.borderColor = "";
			}

			var empPosition1 = document.getElementById("selfPosition4").value;
			if (!empPosition1) {
				document.getElementById('selfPosition4').style.borderColor = "red";
				retVal=false;
			} else {
				document.getElementById("selfPosition4").style.borderColor = "";
			}
			
			var empBusinessName1 = document.getElementById("selfBusinessName4").value;
			if (!empBusinessName1) {
				document.getElementById('selfBusinessName4').style.borderColor= "red";
				retVal=false;
			} else {
				document.getElementById("selfBusinessName4").style.borderColor = "";
			}

			var empStartMonth1 = document.getElementById("selfStartMon4").value;
			if (!empStartMonth1) {
				document.getElementById('selfStartMon4').style.borderColor = "red";
				retVal=false;
			}else if (	isNaN(empStartMonth1)   ) {
				document.getElementById('selfStartMon4').style.borderColor = "red";
				retVal=false;
			}else if (	empStartMonth1==0   ) {
				document.getElementById('selfStartMon4').style.borderColor = "red";
				retVal=false;
			}
			else {
				totalExp=Math.floor(totalExp)+Math.floor(empStartMonth1);
				document.getElementById("selfStartMon4").style.borderColor = "";
			}

			var empPosition1 = document.getElementById("selfPosition4").value;
			if (!empPosition1) {
				document.getElementById('selfPosition4').style.borderColor = "red";
				retVal=false;
			} else {
				document.getElementById("selfPosition4").style.borderColor = "";
			}
			
		}
		}
		
		
		if(totalExp<36){
			document.getElementById('totalEmprequired').innerHTML = "<span style='color:red'>You must fill in 3 years history (36 months). If you don't have 3 years work history, please let us know what else you were doing for the full 3 year period</span>";
			retVal=false;
			} else {
		document.getElementById("totalEmprequired").innerHTML = "";
		}
		
		}catch(err){
			alert(err);
		}

		return 		retVal;

	}
	