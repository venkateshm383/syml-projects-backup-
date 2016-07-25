<?php
/**
 * @version     1.0.0
 * @package     com_openerp
 * @copyright   Copyright (C) 2014. All rights reserved.
 * @license     GNU General Public License version 2 or later; see LICENSE.txt
 * @author      nehal jadhav <nehal.erpincloud@gmail.com> - http://
 */

// No direct access
defined('_JEXEC') or die;

/**
 * Openerp helper.
 */
class OpenerpHelper
{
	/**
	 * Configure the Linkbar.
	 */
	public static function addSubmenu($vName = '')
	{
		JHtmlSidebar::addEntry(
			JText::_('COM_OPENERP_TITLE_OPENERPCONFIGS'),
			'index.php?option=com_openerp&view=openerpconfigs',
			$vName == 'openerpconfigs'
		);
		JHtmlSidebar::addEntry(
			JText::_('COM_OPENERP_TITLE_OPENERPCONFIGURATIONS'),
			'index.php?option=com_openerp&view=openerpconfigurations',
			$vName == 'openerpconfigurations'
		);

	}

	/**
	 * Gets a list of the actions that can be performed.
	 *
	 * @return	JObject
	 * @since	1.6
	 */
	public static function getActions()
	{
		$user	= JFactory::getUser();
		$result	= new JObject;

		$assetName = 'com_openerp';

		$actions = array(
			'core.admin', 'core.manage', 'core.create', 'core.edit', 'core.edit.own', 'core.edit.state', 'core.delete'
		);

		foreach ($actions as $action) {
			$result->set($action, $user->authorise($action, $assetName));
		}

		return $result;
	}
}
