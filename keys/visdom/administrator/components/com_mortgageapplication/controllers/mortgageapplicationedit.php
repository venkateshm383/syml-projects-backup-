<?php
/*------------------------------------------------------------------------
# mortgageapplicationedit.php - Mortgage Application Form  Component
# ------------------------------------------------------------------------
# author    Rameez
# copyright Copyright (C) 2014. All Rights Reserved
# license   GNU/GPL Version 2 or later - http://www.gnu.org/licenses/gpl-2.0.html
# website   bistasolutions.com
-------------------------------------------------------------------------*/

// No direct access to this file
defined('_JEXEC') or die('Restricted access');

// import Joomla controllerform library
jimport('joomla.application.component.controllerform');

/**
 * Mortgageapplication Controller Mortgageapplicationedit
 */
class MortgageapplicationControllermortgageapplicationedit extends JControllerForm
{
	public function __construct($config = array())
	{
		$this->view_list = 'mortgageapplication'; // safeguard for setting the return view listing to the main view.
		parent::__construct($config);
	}

	/**
	 * Function that allows child controller access to model data
	 * after the data has been saved.
	 * 
	 * @param   JModel  &$model     The data model object.
	 * @param   array   $validData  The validated data.
	 * 
	 * @return  void
	 * 
	 * @since   11.1
	 */
	protected function postSaveHook(JModelLegacy &$model, $validData = array())
	{
		// Get a handle to the Joomla! application object
		$application = JFactory::getApplication();

		$model->save($data);

	}

}
?>