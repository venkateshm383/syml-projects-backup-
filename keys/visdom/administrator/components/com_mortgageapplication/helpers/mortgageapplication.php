<?php
/*------------------------------------------------------------------------
# mortgageapplication.php - Mortgage Application Form  Component
# ------------------------------------------------------------------------
# author    Rameez
# copyright Copyright (C) 2014. All Rights Reserved
# license   GNU/GPL Version 2 or later - http://www.gnu.org/licenses/gpl-2.0.html
# website   bistasolutions.com
-------------------------------------------------------------------------*/

// No direct access to this file
defined('_JEXEC') or die('Restricted access');

/**
 * Mortgageapplication component helper.
 */
abstract class MortgageapplicationHelper
{
	/**
	 *	Configure the Linkbar.
	 */
	public static function addSubmenu($submenu) 
	{
		JHtmlSidebar::addEntry(JText::_('Mortgageapplication'), 'index.php?option=com_mortgageapplication&view=mortgageapplication', $submenu == 'mortgageapplication');
		JHtmlSidebar::addEntry(JText::_('Categories'), 'index.php?option=com_categories&view=categories&extension=com_mortgageapplication', $submenu == 'categories');

		// set some global property
		$document = JFactory::getDocument();
		if ($submenu == 'categories'){
			$document->setTitle(JText::_('Categories - Mortgageapplication'));
		};
	}

	/**
	 *	Get the actions
	 */
	public static function getActions($Id = 0)
	{
		jimport('joomla.access.access');

		$user	= JFactory::getUser();
		$result	= new JObject;

		if (empty($Id)){
			$assetName = 'com_mortgageapplication';
		} else {
			$assetName = 'com_mortgageapplication.message.'.(int) $Id;
		};

		$actions = JAccess::getActions('com_mortgageapplication', 'component');

		foreach ($actions as $action){
			$result->set($action->name, $user->authorise($action->name, $assetName));
		};

		return $result;
	}
}
?>