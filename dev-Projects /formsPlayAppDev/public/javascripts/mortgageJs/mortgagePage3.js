/**
 * MortgagePage2Ref js file
 */

/**
 * for load the page if quit alert before quit the page.
 */
window.onbeforeunload = function() {
	return "Are you sure?";
};

$(document).ready(function() {
	$('form').submit(function() {
		window.onbeforeunload = null;
	});
});

/**
 * for select function
 */

window.onload = function() {
	SelectElement();
};
function SelectElement() {   
    var buildRendered = document.forms["myForm"]["buildingType1"].value;  
    document.forms["myForm"]["buildingType"].value=buildRendered;
    
    var buildingStyle = document.forms["myForm"]["propertystyle1"].value;  
    document.forms["myForm"]["propertyStyle"].value = buildingStyle;
    
    var propertyheated  = document.forms["myForm"]["propertyheated1"].value;  
    document.forms["myForm"]["propertyHeat"].value = propertyheated;
    
    var getwater  = document.forms["myForm"]["getwater1"].value;  
    document.forms["myForm"]["propertyGetWater"].value = getwater;
    
    var propertydispose  = document.forms["myForm"]["propertydispose1"].value;  
    document.forms["myForm"]["propertyDisposeWater"].value = propertydispose;
    
    var garagetype  = document.forms["myForm"]["garagetype1"].value;  
    
    document.forms["myForm"]["typeGarage"].value = garagetype;
    
    var garageSize  = document.forms["myForm"]["garageSize1"].value;  
    console.log("garageSize --- "+garageSize);
    document.forms["myForm"]["garageSize"].value = garageSize;
}
/**
 * data-toggle
 */
$(document).ready(function(){
    $('[data-toggle="tooltip"]').tooltip();   
});

/**
 * angular code here
 */
var app = angular.module("demoApp", ['ui.bootstrap']);

app.controller('DemoController', function ($scope) {
    init();
    function init() {
        $scope.newItemType = '';
        $scope.company="";
        $scope.change = function () {
            console.log($scope.newItemType)
        };
        
        
        
        
    }
    
    
    var garagetype1 = document.forms["myForm"]["garagetype1"].value;
    if(garagetype1 != null && garagetype1 !="")
    	$scope.type = garagetype1;
   console.log("value of type "+$scope.type);
    
});

/**
 * for validating the entire  page3
 */
function validateForm() {
	
	var buildingType = document.forms["myForm"]["buildingType"].value;
	if (!buildingType) {
		document.getElementById('widget_settings_44').innerHTML = "<span style='color:red'>*This field is Required.</span>";
		document.getElementById('widget_settings_44').focus();
		return false;
	} else {
		document.getElementById("widget_settings_44").innerHTML = "";
	}		
	
	var propertyStyle = document.forms["myForm"]["propertyStyle"].value;
	if (!propertyStyle) {
		document.getElementById('widget_settings_65').innerHTML = "<span style='color:red'>*This field is Required.</span>";
		document.getElementById('widget_settings_65').focus();
		return false;
	} else {
		document.getElementById("widget_settings_65").innerHTML = "";
	}
	
	var squareFt = document.forms["myForm"]["squareFt"].value;
	if (!squareFt) {
		document.getElementById('input_24').innerHTML = "<span style='color:red'>*This field is Required.</span>";
		document.getElementById('squareFtID').focus();
			return false;
	} else {
		document.getElementById("input_24").innerHTML = "";
	}
	
	var propertyHeat = document.forms["myForm"]["propertyHeat"].value;
	if (!propertyHeat) {
		document.getElementById('widget_settings_58').innerHTML = "<span style='color:red'>*This field is Required.</span>";
		document.getElementById('widget_settings_58').focus();
		return false;
	} else {
		document.getElementById("widget_settings_58").innerHTML = "";
	}
	
	var propertyGetWater = document.forms["myForm"]["propertyGetWater"].value;
	if (!propertyGetWater) {
		document.getElementById('widget_settings_59').innerHTML = "<span style='color:red'>*This field is Required.</span>";
		document.getElementById('widget_settings_59').focus();
		return false;
	} else {
		document.getElementById("widget_settings_59").innerHTML = "";
	}
	
	var propertyDisposeWater = document.forms["myForm"]["propertyDisposeWater"].value;
	if (!propertyDisposeWater) {
		document.getElementById('widget_settings_60').innerHTML = "<span style='color:red'>*This field is Required.</span>";
		document.getElementById('widget_settings_60').focus();
		return false;
	} else {
		document.getElementById("widget_settings_60").innerHTML = "";
	}
	
	var typeGarage = document.forms["myForm"]["typeGarage"].value;
	if (!typeGarage) {
		document.getElementById('widget_settings_61').innerHTML = "<span style='color:red'>*This field is Required.</span>";
		document.getElementById('widget_settings_61').focus();
		return false;
	} else {
		document.getElementById("widget_settings_61").innerHTML = "";
	}
	
	// if(typeGarage == "typeGarages" && typeGarage == "None" ){
		var garageSize = document.forms["myForm"]["garageSize"].value;
		if (garageSize == "garageSizes") {
			document.getElementById('widget_settings_64').innerHTML = "<span style='color:red'>*This field is Required.</span>";
			document.getElementById('widget_settings_64').focus();
			return false;
		} else {
			document.getElementById("widget_settings_64").innerHTML = "";
		}	
	//} 
		
	//return true;
}
/**
 * on change  background color of select drop down field
 */
function changeBackgroundOfSelectedField() {
	var buildingType = document.forms["myForm"]["buildingType"].value;
	if (!buildingType) {
		document.getElementById('propType').style.backgroundColor = "#ffffff";
	}else{
		document.getElementById('propType').style.backgroundColor = "#33cc33";
	}
	
	var propertyStyle = document.forms["myForm"]["propertyStyle"].value;
	if (!propertyStyle) {
		document.getElementById('propStyles').style.backgroundColor = "#ffffff";
	} else {
		document.getElementById('propStyles').style.backgroundColor = "#33cc33";
	}
	
	var propertyHeat = document.forms["myForm"]["propertyHeat"].value;
	if (!propertyHeat) {
		document.getElementById('propHeated').style.backgroundColor = "#ffffff";
	} else {
		document.getElementById('propHeated').style.backgroundColor = "#33cc33";
	}
	
	var propertyGetWater = document.forms["myForm"]["propertyGetWater"].value;
	if (!propertyGetWater) {
		document.getElementById('propGetWater').style.backgroundColor = "#ffffff";
	} else {
		document.getElementById('propGetWater').style.backgroundColor = "#33cc33";
	}
	
	var propertyDisposeWater = document.forms["myForm"]["propertyDisposeWater"].value;
	if (!propertyDisposeWater) {
		document.getElementById('propDispWater').style.backgroundColor = "#ffffff";
	} else {
		document.getElementById('propDispWater').style.backgroundColor = "#33cc33";
	}
	
	var typeGarage = document.forms["myForm"]["typeGarage"].value;
	if (!typeGarage) {
		document.getElementById('typeOfGarage').style.backgroundColor = "#ffffff";
	} else {
		document.getElementById('typeOfGarage').style.backgroundColor = "#33cc33";
	}
	
	var garageSize = document.forms["myForm"]["garageSize"].value;
	if (garageSize == "garageSizes") {
		document.getElementById('sizeOfGarage').style.backgroundColor = "#ffffff";
	} else {
		document.getElementById('sizeOfGarage').style.backgroundColor = "#33cc33";
	}	
	
}

/**
 * for Number event 
 */

function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
    	// alert("Please enter a number (no comma or dash)");
        return false;
    }
    return true;
}
