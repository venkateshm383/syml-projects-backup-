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
 * mortgageapplication View
 */
class MortgageapplicationViewmortgageapplication extends JViewLegacy
{
	/**
	 * Mortgageapplication view display method
	 * @return void
	 */
	function display($tpl = null) 
	{
		// Include helper submenu
		MortgageapplicationHelper::addSubmenu('mortgageapplication');

		// Get data from the model
		$items = $this->get('Items');
		$pagination = $this->get('Pagination');

		// Check for errors.
		if (count($errors = $this->get('Errors'))){
			JError::raiseError(500, implode('<br />', $errors));
			return false;
		};

		// Assign data to the view
		$this->items = $items;
		$this->pagination = $pagination;

		// Set the toolbar
		$this->addToolBar();
		// Show sidebar
		$this->sidebar = JHtmlSidebar::render();

		// Display the template
		parent::display($tpl);

		// Set the document
		$this->setDocument();
	}

	/**
	 * Setting the toolbar
	 */
	protected function addToolBar() 
	{
		$canDo = MortgageapplicationHelper::getActions();
		JToolBarHelper::title(JText::_('Mortgageapplication Manager'), 'mortgageapplication');
		if($canDo->get('core.create')){
			JToolBarHelper::addNew('mortgageapplicationedit.add', 'JTOOLBAR_NEW');
		};
		if($canDo->get('core.edit')){
			JToolBarHelper::editList('mortgageapplicationedit.edit', 'JTOOLBAR_EDIT');
		};
		if($canDo->get('core.delete')){
			JToolBarHelper::deleteList('', 'mortgageapplication.delete', 'JTOOLBAR_DELETE');
		};
		if($canDo->get('core.admin')){
			JToolBarHelper::divider();
			JToolBarHelper::preferences('com_mortgageapplication');
		};
	}

	/**
	 * Method to set up the document properties
	 *
	 *
	 * @return void
	 */
	protected function setDocument() 
	{
		$document = JFactory::getDocument();
		$document->setTitle(JText::_('Mortgageapplication Manager - Administrator'));
	}
}
?>