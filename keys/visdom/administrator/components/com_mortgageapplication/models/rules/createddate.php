<?php
/*------------------------------------------------------------------------
# createddate.php - Mortgage Application Form  Component
# ------------------------------------------------------------------------
# author    Rameez
# copyright Copyright (C) 2014. All Rights Reserved
# license   GNU/GPL Version 2 or later - http://www.gnu.org/licenses/gpl-2.0.html
# website   bistasolutions.com
-------------------------------------------------------------------------*/

// No direct access to this file
defined('_JEXEC') or die('Restricted access');

// import Joomla formrule library
jimport('joomla.form.formrule');
/**
 * Form Rule class for the Joomla Framework.
 */
class JFormRulecreateddate extends JFormRule
{
	/**
	 * The regular expression.
	 *
	 * @access	protected
	 * @var		string
	 * @since	2.5
	 */
	protected $regex = '^[^_]+$';
}
?>