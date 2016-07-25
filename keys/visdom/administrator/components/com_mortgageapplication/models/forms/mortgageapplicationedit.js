/**
 *	mortgageapplicationedit : Validate
 *	Filename : mortgageapplicationedit.js
 *
 *	Author : Rameez
 *	Component : Mortgage Application Form 
 *
 *	Copyright : Copyright (C) 2014. All Rights Reserved
 *	License : GNU/GPL Version 2 or later - http://www.gnu.org/licenses/gpl-2.0.html
 *
 **/
window.addEvent('domready', function() {
	document.formvalidator.setHandler('name',
		function (value) {
			regex=/^[^_]+$/;
			return regex.test(value);
	});
	document.formvalidator.setHandler('username',
		function (value) {
			regex=/^[^_]+$/;
			return regex.test(value);
	});
	document.formvalidator.setHandler('password',
		function (value) {
			regex=/^[^_]+$/;
			return regex.test(value);
	});
	document.formvalidator.setHandler('createddate',
		function (value) {
			regex=/^[^_]+$/;
			return regex.test(value);
	});
	document.formvalidator.setHandler('updateddate',
		function (value) {
			regex=/^[^_]+$/;
			return regex.test(value);
	});
	document.formvalidator.setHandler('status',
		function (value) {
			regex=/^[^_]+$/;
			return regex.test(value);
	});
});