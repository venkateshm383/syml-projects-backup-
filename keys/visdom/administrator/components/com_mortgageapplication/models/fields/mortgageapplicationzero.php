<?php
/*------------------------------------------------------------------------
# mortgageapplicationzero.php - Mortgage Application Form  Component
# ------------------------------------------------------------------------
# author    Rameez
# copyright Copyright (C) 2014. All Rights Reserved
# license   GNU/GPL Version 2 or later - http://www.gnu.org/licenses/gpl-2.0.html
# website   bistasolutions.com
-------------------------------------------------------------------------*/

// No direct access to this file
defined('_JEXEC') or die('Restricted access');

// import the list field type
jimport('joomla.form.helper');
JFormHelper::loadFieldClass('list');

/**
 * name Form Field class for the Mortgageapplication component
 */
class JFormFieldmortgageapplicationzero extends JFormFieldList
{
	/**
	 * The name field type.
	 *
	 * @var		string
	 */
	protected $type = 'mortgageapplicationzero';

	/**
	 * Method to get a list of options for a list input.
	 *
	 * @return	array		An array of JHtml options.
	 */
	protected function getOptions()
	{
		$db = JFactory::getDBO();
		$query = $db->getQuery(true);
		$query->select('#__mortgageapplication_mortgageapplicationedit.id as id, #__mortgageapplication_mortgageapplicationedit.name as name');
		$query->from('#__mortgageapplication_mortgageapplicationedit');
		$db->setQuery((string)$query);
		$items = $db->loadObjectList();
		$options = array();
		if($items){
			foreach($items as $item){
				$options[] = JHtml::_('select.option', $item->id, ucwords($item->name));
			};
		};
		$options = array_merge(parent::getOptions(), $options);

		return $options;
	}
}
?>