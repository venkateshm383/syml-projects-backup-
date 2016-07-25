<?php
/*------------------------------------------------------------------------
# router.php - Mortgage Application Form  Component
# ------------------------------------------------------------------------
# author    Rameez
# copyright Copyright (C) 2014. All Rights Reserved
# license   GNU/GPL Version 2 or later - http://www.gnu.org/licenses/gpl-2.0.html
# website   bistasolutions.com
-------------------------------------------------------------------------*/

// No direct access to this file
defined('_JEXEC') or die('Restricted access');

function MortgageapplicationBuildRoute(&$query)
{
	$segments = array();

	if(isset($query['view'])){
		$segments[] = $query['view'];
		unset($query['view']);
	};

	if(isset($query['id'])){
		$segments[] = $query['id'];
		unset($query['id']);
	};

	return $segments;
}

function MortgageapplicationParseRoute($segments)
{
	$vars = array();
	// Count segments
	$count = count($segments);
	//Handle View and Identifier
	switch($segments[0])
	{
		case 'mortgageapplication':
			$id = explode(':', $segments[$count-1]);
			$vars['id'] = (int) $id[0];
			$vars['view'] = 'mortgageapplication';
			break;

		case 'mortgageapplicationedit':
			$id = explode(':', $segments[$count-1]);
			$vars['id'] = (int) $id[0];
			$vars['view'] = 'mortgageapplicationedit';
			break;
	}

	return $vars;
}
?>