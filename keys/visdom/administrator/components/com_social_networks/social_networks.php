<?php
/**
 * @version     1.0.0
 * @package     com_social_networks
 * @copyright   Copyright (C) 2014. All rights reserved.
 * @license     GNU General Public License version 2 or later; see LICENSE.txt
 * @author      nehal jadhav <nehal.erpincloud@gmail.com> - http://
 */


// no direct access
defined('_JEXEC') or die;

// Access check.
if (!JFactory::getUser()->authorise('core.manage', 'com_social_networks')) 
{
	throw new Exception(JText::_('JERROR_ALERTNOAUTHOR'));
}

// Include dependancies
jimport('joomla.application.component.controller');

$controller	= JControllerLegacy::getInstance('Social_networks');
$controller->execute(JFactory::getApplication()->input->get('task'));
$controller->redirect();
