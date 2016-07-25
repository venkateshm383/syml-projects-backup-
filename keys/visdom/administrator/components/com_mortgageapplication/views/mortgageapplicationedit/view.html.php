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
 * Mortgageapplicationedit View
 */
class MortgageapplicationViewmortgageapplicationedit extends JViewLegacy
{
	/**
	 * display method of Mortgageapplicationedit view
	 * @return void
	 */
	public function display($tpl = null)
	{
		// get the Data
		$form = $this->get('Form');
		$item = $this->get('Item');
		$script = $this->get('Script');

		// Check for errors.
		if (count($errors = $this->get('Errors'))){
			JError::raiseError(500, implode('<br />', $errors));
			return false;
		};

		// Assign the variables
		$this->form = $form;
		$this->item = $item;
		$this->script = $script;

		// Set the toolbar
		$this->addToolBar();

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
		JFactory::getApplication()->input->set('hidemainmenu', true);
		$user = JFactory::getUser();
		$userId	= $user->id;
		$isNew = $this->item->id == 0;
		$canDo = MortgageapplicationHelper::getActions($this->item->id);
		JToolBarHelper::title($isNew ? JText::_('Mortgageapplicationedit :: New') : JText::_('Mortgageapplicationedit :: Edit'), 'mortgageapplicationedit');
		// Built the actions for new and existing records.
		if ($isNew){
			// For new records, check the create permission.
			if ($canDo->get('core.create')){
				JToolBarHelper::apply('mortgageapplicationedit.apply', 'JTOOLBAR_APPLY');
				JToolBarHelper::save('mortgageapplicationedit.save', 'JTOOLBAR_SAVE');
				JToolBarHelper::custom('mortgageapplicationedit.save2new', 'save-new.png', 'save-new_f2.png', 'JTOOLBAR_SAVE_AND_NEW', false);
			};
			JToolBarHelper::cancel('mortgageapplicationedit.cancel', 'JTOOLBAR_CANCEL');
		} else {
			if ($canDo->get('core.edit')){
				// We can save the new record
				JToolBarHelper::apply('mortgageapplicationedit.apply', 'JTOOLBAR_APPLY');
				JToolBarHelper::save('mortgageapplicationedit.save', 'JTOOLBAR_SAVE');
				// We can save this record, but check the create permission to see
				// if we can return to make a new one.
				if ($canDo->get('core.create')){
					JToolBarHelper::custom('mortgageapplicationedit.save2new', 'save-new.png', 'save-new_f2.png', 'JTOOLBAR_SAVE_AND_NEW', false);
				};
			};
			if ($canDo->get('core.create')){
				JToolBarHelper::custom('mortgageapplicationedit.save2copy', 'save-copy.png', 'save-copy_f2.png', 'JTOOLBAR_SAVE_AS_COPY', false);
			};
			JToolBarHelper::cancel('mortgageapplicationedit.cancel', 'JTOOLBAR_CLOSE');
		};
	}

	/**
	 * Method to set up the document properties
	 *
	 * @return void
	 */
	protected function setDocument()
	{
		$isNew = ($this->item->id < 1);
		$document = JFactory::getDocument();
		$document->setTitle($isNew ? JText::_('Mortgageapplicationedit :: New :: Administrator') : JText::_('Mortgageapplicationedit :: Edit :: Administrator'));
		$document->addScript(JURI::root() . $this->script);
		$document->addScript(JURI::root() . "administrator/components/com_mortgageapplication/views/mortgageapplicationedit/submitbutton.js");
		JText::script('mortgageapplicationedit not acceptable. Error');
	}
}
?>