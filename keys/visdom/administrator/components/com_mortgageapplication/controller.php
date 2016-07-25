<?php
/*------------------------------------------------------------------------
# controller.php - Mortgage Application Form  Component
# ------------------------------------------------------------------------
# author    Rameez
# copyright Copyright (C) 2014. All Rights Reserved
# license   GNU/GPL Version 2 or later - http://www.gnu.org/licenses/gpl-2.0.html
# website   bistasolutions.com
-------------------------------------------------------------------------*/

// No direct access to this file
defined('_JEXEC') or die('Restricted access');

// import Joomla controller library
jimport('joomla.application.component.controller');

/**
 * General Controller of Mortgageapplication component
 */
class MortgageapplicationController extends JControllerLegacy
{
	/**
	 * display task
	 *
	 * @return void
	 */
	function display($cachable = false, $urlparams = false)
	{
		// set default view if not set
		JRequest::setVar('view', JRequest::getCmd('view', 'Mortgageapplication'));

		// call parent behavior
		parent::display($cachable);

		// set view
		$view = strtolower(JRequest::getVar('view'));

		// Set the submenu
		MortgageapplicationHelper::addSubmenu($view);
	}
}
?>