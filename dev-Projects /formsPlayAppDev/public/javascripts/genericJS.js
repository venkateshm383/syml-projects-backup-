/**
 *  This js file is for the Generic mortgage Application..............
 */

/*For restricting symbol and character and other character */
    function isNumber(evt) {
        evt = (evt) ? evt : window.event;
        var charCode = (evt.which) ? evt.which : evt.keyCode;
        if (charCode > 31 && (charCode < 48 || charCode > 57) || (charCode == 44)) {
        	// alert("Please enter a number (no comma or dash)");
            return false;
        }
        return true;
    }
    
   /*This for birthday datepicker of jquery*/
    $(function() {
      $( "#datepicker" ).datepicker({
      	yearRange: "-100:-18",
          maxDate: "-18Y", 
          minDate: "-100Y",
          changeMonth: true,
          changeYear: true,
          defaultDate: new Date().getDate() - 365*30+7	
        
      });
    });
   
     