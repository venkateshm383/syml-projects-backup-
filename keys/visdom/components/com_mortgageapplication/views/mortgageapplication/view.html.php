<?php
/*------------------------------------------------------------------------
# view.html.php - Mortgage Application Form  Component
# ------------------------------------------------------------------------
# author    Rameez
# copyright Copyright (C) 2014. All Rights Reserved
# license   GNU/GPL Version 2 or later - http://www.gnu.org/licenses/gpl-2.0.html
# website   bistasolutions.com
-------------------------------------------------------------------------*/

// No direct access to this file
defined('_JEXEC') or die('Restricted access');
// import Joomla view library
jimport('joomla.application.component.view');
/**
 * HTML Mortgageapplication View class for the Mortgage Application Form  Component
 */
class MortgageapplicationViewmortgageapplication extends JViewLegacy
{
	// Overwriting JView display method
	function display($tpl = null)
	{
		//JHTML::_('behavior.calendar');
		//JHTML::_('behavior.tooltip');
		// Assign data to the view
		$this->items = $this->get('Items');
		// Check for errors.
		if (count($errors = $this->get('Errors'))){
			JError::raiseError(500, implode('<br />', $errors));
			return false;
		};
		// Display the view
		parent::display($tpl);
	}
}
?>