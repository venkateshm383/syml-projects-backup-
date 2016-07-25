<?php
/**
 * @version     1.0.0
 * @package     com_social_networks
 * @copyright   Copyright (C) 2014. All rights reserved.
 * @license     GNU General Public License version 2 or later; see LICENSE.txt
 * @author      nehal jadhav <nehal.erpincloud@gmail.com> - http://
 */

// No direct access
defined('_JEXEC') or die;

/**
 * Social_networks helper.
 */
class Social_networksHelper
{
	/**
	 * Configure the Linkbar.
	 */
	public static function addSubmenu($vName = '')
	{
		JHtmlSidebar::addEntry(
			JText::_('COM_SOCIAL_NETWORKS_TITLE_SOCIALNETWORKSS'),
			'index.php?option=com_social_networks&view=socialnetworkss',
			$vName == 'socialnetworkss'
		);
		JHtmlSidebar::addEntry(
			JText::_('COM_SOCIAL_NETWORKS_TITLE_SOCIALNETWORKINGS'),
			'index.php?option=com_social_networks&view=socialnetworkings',
			$vName == 'socialnetworkings'
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

		$assetName = 'com_social_networks';

		$actions = array(
			'core.admin', 'core.manage', 'core.create', 'core.edit', 'core.edit.own', 'core.edit.state', 'core.delete'
		);

		foreach ($actions as $action) {
			$result->set($action, $user->authorise($action, $assetName));
		}

		return $result;
	}
}
