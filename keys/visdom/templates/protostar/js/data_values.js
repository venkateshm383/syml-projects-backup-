function GetFieldType(field) {
	if (field.hasClass('type_select'))
		return 'select';
	if (field.hasClass('type_textarea'))
		return 'textarea';
	if (field.hasClass('type_text'))
		return 'text';
	if (field.hasClass('type_money'))
		return 'money';
	if (field.hasClass('type_percent'))
		return 'percent';
	if (field.hasClass('type_yesno'))
		return 'yesno';
	if (field.hasClass('type_timespan_months'))
		return 'timespan_months';
	if (field.hasClass('type_date'))
		return 'date';
	if (field.hasClass('type_year'))
		return 'year';
	if (field.hasClass('type_email'))
		return 'email';
	if (field.hasClass('type_phone_areacode'))
		return 'phone_areacode';
	if (field.hasClass('type_phone_number'))
		return 'phone_number';
	if (field.hasClass('type_phone_extension'))
		return 'phone_extension';
	if (field.hasClass('type_sqft'))
		return 'sqft';
	if (field.hasClass('type_sin'))
		return 'sin';
	if (field.hasClass('type_integer'))
		return 'integer';
	if (field.hasClass('type_integeryear'))
		return 'integeryear';
	if (field.hasClass('type_postalcode'))
		return 'postalcode';
	if (field.hasClass('type_interest'))
		return 'interest';
}

function SetupField(field) {
	var parser = /^([^_]*)_([^_]*)_(.*)$/;

	field.entity = {};
	entity = field.entity;

	entity.type = field.name.replace(parser, '$1');
	entity.id = field.name.replace(parser, '$2');
	entity.field = field.name.replace(parser, '$3');
	entity.field_type = GetFieldType(field);
	entity.saved_value = field.value;

	field.addEvent('change', EFieldPossiblyChanged);

	if (field.type == "text")
		field.addEvent('keypress', EFieldKeyPress);
}

function ScanApiFields() {
	$$('.api_field').each(function(field) {
		SetupField(field);
	});
}

function EFieldPossiblyChanged(e) {
	ScrubField(this);
	ValidateField(this);
}

function ScrubField(field) {
	switch (field.entity.field_type) {
		case 'postalcode':
			field.value = field.value.toUpperCase();
			break;
		// case 'money':
			// if (field.value.match(/[kK]$/))
				// var k = true;
			// if (field.value.match(/[mM]$/))
				// var m = true;

			// var value = field.value.replace(/[^0-9.]/g, '');
			// if (value == '')
				// field.value = '';
			// else {
				// if (k)
					// value = Number(value.replace(/[kK]$/,''))*1000;
				// if (m)
					// field.value = Number(value.replace(/[mM]$/,''))*1000000;
				// if (Number(value) > 9999999999999.99)
					// value = 9999999999999.99;
					
				// if (!isNaN(Number(value).toFixed(2)))
					// field.value = addCommas(Number(value).toFixed(2));
				// else
					// field.value = '';
			// }
			// break;
		case 'money':
			if (field.value.match(/[kK]$/))
				var k = true;
			if (field.value.match(/[mM]$/))
				var m = true;

			var value = field.value.replace(/[^0-9.]/g, '');
			if (value == '')
				field.value = '';
			else {
				if (k)
					value = Number(value.replace(/[kK]$/,''))*1000;
				if (m)
					field.value = Number(value.replace(/[mM]$/,''))*1000000;
				if (Number(value) > 9999999999999.99)
					value = 9999999999999.99;
					
				if (!isNaN(Number(value).toFixed(3)))
					field.value = addCommas(Number(value).toFixed(3));
				else
					field.value = '';
			}
			break;
	}

	switch (field.entity.field) {
		case 'address1':
		case 'city':
			field.value = field.value.toUpperCase();
	}
}

var field_validate_matrix = {
	money: /^([0-9,]+(\.[0-9]{2})?|[0-9]+(\.[0-9]+)?[kKmM])$/,
	percent: /^[0-9]+(\.[0-9]{1,3})?$/,
	email: /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-z]+$/,
	phone_areacode: /^[0-9]+$/,
	phone_number: /^[0-9]{3}[. -]?[0-9]{4}$/,
	phone_extension: /^[0-9]+$/,
	sqft: /^[0-9]+$/,
	sin: /^([0-9]{3}[ .-]?){2}[0-9]{3}$/,
	integer: /^[0-9]+$/,                  
	postalcode: /^[a-zA-Z][0-9][a-zA-Z][ .-]?[0-9][a-zA-Z][0-9]$/
};
function ValidateField(field) { 
	var invalid = false;
	var validate = field_validate_matrix[field.entity.field_type];
	 if (validate && field.value && !field.value.match(validate))
		invalid = true;  

	if (field.entity.field_type == 'sin')
		if (!validate_sin(field.value))
		{
			alert("Sin number is invalid");
			invalid = true;
		}
		
	if (invalid && !field.hasClass('ApiInvalid'))
		field.addClass('ApiInvalid');   
	if (!invalid && field.hasClass('ApiInvalid'))
		field.removeClass('ApiInvalid');
}

function validate_sin(number) {
	number = number.replace(/[^0-9]/g, '');
	
	var sum = 0;
	var multipliers = [1,2,1, 2,1,2, 1,2,1];
	for (var i = 0; i < number.length; i++) {
		var num = Number(number.charAt(i)) * multipliers[i];
		sum += num > 9 ? num - 9 : num;
	}
	
	return sum%10 == 0;
}

var key_filter_matrix = {
	money: /[kKmM0-9,.]/,
	percent: /[0-9.]/,
	year: /[0-9]/,
	email: /[a-zA-Z0-9.@_-]/,
	phone_areacode: /[0-9]/,
	phone_number: /[0-9-]/,
	phone_extension: /[0-9]/,
	sqft: /[0-9]/,
	sin: /[0-9- ]/,
	integer: /[0-9]/,  
	postalcode: /[0-9.]/
};

function EFieldKeyPress(e) {
	e = new Event(e);
	
	if (e.control || e.alt)
		return true;
   
	for (specialKey in Event.keys)
		if (e.key == specialKey)
			return true;

	if (e.key == '#' || e.key == '$') // # and $ are home and end. Nothing I can do about it. :(
		return true;

	var filter = key_filter_matrix[this.entity.field_type];
	if (filter && !e.key.match(filter))
		e.stop();
}

// Entrypoint
window.addEvent('domready', function() {
	
	setTimeout('SetupDataBinding();', 1);
});
function SetupDataBinding() {
	ScanApiFields();
	SetupTimespanLoading();
}












// from http://www.mredkj.com/javascript/numberFormat.html
function addCommas(nStr)
{
	nStr += '';
	x = nStr.split('.');
	x1 = x[0];
	x2 = x.length > 1 ? '.' + x[1] : '';
	var rgx = /(\d+)(\d{3})/;
	while (rgx.test(x1)) {
		x1 = x1.replace(rgx, '$1' + ',' + '$2');
	}
	return x1 + x2;
}
















function SetupTimespanLoading() {
	var nodes = $$('.monthsPD');
	nodes.extend($$('.yearsPD'));

	nodes.each(function(pd){
		pd.addEvent('change', ETimespanMonthsChange);
	});
}

function ETimespanMonthsChange(e) {
	var parser = /(y|m)_(.*)$/;
	var identifier = this.id.replace(parser, '$2');
	var field = document.getElementsByName(identifier)[0];

	if ($('m_'+identifier).value === '' && $('y_'+identifier).value === '')
		field.value = '';
	else {
		var months = Number($('m_'+identifier).value);
		var years = Number($('y_'+identifier).value);
		field.value = years*12 + months;
	}
}








// Date Buttons

function EDateButtonClicked(e) {
	for (var field = $(this).getPrevious(); field && !$(field).hasClass('type_date'); field = field.getPrevious());

	var calendar = new Calendar(0, null, EDateButtonCalendarSelect, EDateButtonCalendarClose);

	calendar.showsTime = false;
	calendar.showsOtherMonths = false;
	calendar.setRange(1900,2100);
	calendar.weekNumbers = false;
	calendar.create();
	calendar.setDateFormat('%Y-%m-%d');
	calendar.parseDate(field.value);

	var field_pos = $(field).getPosition();
	var field_size = $(field).getSize();
	var cal_size = $(calendar.table).getSize();

	var new_x = field_pos.x + field_size.size.x - cal_size.size.x;
	var new_y = field_pos.y - cal_size.size.y;

	calendar.showAt(new_x, new_y);

	calendar.parent = field;
	calendar.field = field;
}

window.addEvent('domready', function(){
	$$('.type_date').each(function(el){
		el.addEvent('mouseover', EDateHovered);
		el.addEvent('mouseout', EDateUnHovered);
	});
});
function EDateButtonCalendarSelect(cal,date) {
	if (cal.dateClicked)
		cal.callCloseHandler();
}
function EDateButtonCalendarClose(cal) {
	this.field.value = cal.date.print(cal.dateFormat);
	cal.hide();
}

function EDateHovered(e) {

	if (this.no_tooltip)
		return;
	this.no_tooltip = true;

	if (this.value) {
		var d = Date.parseDate(this.value, '%Y-%m-%d');
		var prettyDate = d.prettyPrint('%A %B %e, %Y');
	} else
		return;
		
	
	
	var el = new Element('div')
		.setStyles({
			position: 'absolute',
			background: 'white',
			color: 'black',
			border: "1px solid black",
			padding: "5px",
			"z-index": 9000
		});
	el.innerHTML = prettyDate;
	this.tooltip = el;
	el.injectAfter(this);
	
	var pos = this.getPosition();
	var size = el.getSize();
	
	el.setStyle('top', pos.y - size.size.y - 5);
	el.setStyle('left', pos.x + 10);
	el.setStyle('opacity', 0);
	
	var fx = new Fx.Styles(el, {duration: 750, transition: Fx.Transitions.Cubic.easeOut})
	fx.start({opacity: [0, 1]});
}
function EDateUnHovered(e) {
	if (this.tooltip)
		this.tooltip.remove();
	this.no_tooltip = false;
	this.tooltip = null;
}
