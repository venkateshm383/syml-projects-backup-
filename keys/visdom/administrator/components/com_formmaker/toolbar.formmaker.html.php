<?php 
  
 /**
 * @package Form Maker Lite
 * @author Web-Dorado
 * @copyright (C) 2011 Web-Dorado. All rights reserved.
 * @license GNU/GPLv3 http://www.gnu.org/licenses/gpl-3.0.html
 **/

defined('_JEXEC') or die('Restricted access');

function cancel_secondary()
{
	JToolBarHelper :: custom( 'cancel_secondary', 'cancel.png', '', 'Cancel', '', '' );
}

function edit_submit_text()
{
	JToolBarHelper :: custom( 'edit_my_submit_text', 'edit.png', '', 'Actions after submission', '', '' );
}

function remove_submit()
{
	JToolBarHelper :: custom( 'remove_submit', 'delete.png', '', 'Delete', '', '' );
}

function edit_submit()
{
	JToolBarHelper :: custom( 'edit_submit', 'edit.png', '', 'Edit', '', '' );
}

function undo()
{
	JToolBarHelper :: custom( 'undo', 'back.png', '', 'Undo', '', '' );
}

function redo()
{
	JToolBarHelper :: custom( 'redo', 'forward.png', '', 'Redo', '', '' );
}

class TOOLBAR_formmaker {

	public static function _THEMES() {
		$document = JFactory::getDocument();
		$document->addStyleSheet('components/com_formmaker/FormMakerLogo.css');
		JToolBarHelper::title( 'Form Maker: Themes');		
		JToolBarHelper::addNew('add_themes');
		JToolBarHelper::editList('edit_themes');
		JToolBarHelper::makeDefault();
		JToolBarHelper::deleteList('Are you sure you want to delete? ', 'remove_themes');

	}

	public static function _NEW_Form_options() 
	{		
		JToolBarHelper::title( 'Form Maker: Form Options');		
		JToolBarHelper::save('save_form_options');
		JToolBarHelper::apply('apply_form_options');
		cancel_secondary();
	}

	public static function _NEW_Form_form_layout() 
	{		
		$document =JFactory::getDocument();
		$document->addStyleSheet('components/com_formmaker/FormMakerLogo.css');
		JToolBarHelper::title( 'Form Maker', 'FormMakerLogo' );		
		JToolBarHelper::save('save_form_layout');
		JToolBarHelper::apply('apply_form_layout');
		cancel_secondary();
	}
	
	public static function _NEW_THEMES() {
		JToolBarHelper::title( 'Form Maker: Add Theme');		
		JToolBarHelper::apply('apply_themes');
		JToolBarHelper::save('save_themes');
		JToolBarHelper::cancel('cancel_themes');		
	}
	
	public static function EDIT_SUBMITS()
	{
		JToolBarHelper::title( 'Form Maker: Edit Submission');		
		JToolBarHelper::apply('apply_submit');
		JToolBarHelper::save('save_submit');
		JToolBarHelper::cancel('cancel_submit');		
	}
	
	public static function _SUBMITS() 
	{
		$document = JFactory::getDocument();
		$document->addStyleSheet('components/com_formmaker/FormMakerLogo.css');
		JToolBarHelper::title( 'Form Maker: Submissions' );
		remove_submit();
		JToolBarHelper::editList('edit_submit');
	}

	public static function _NEW() 
	{
		$cid 	= JRequest::getVar('cid', array(0), '', 'array');
		JArrayHelper::toInteger($cid, array(0));
		$id 	= $cid[0];
		$row =JTable::getInstance('formmaker', 'Table');
		$row->load( $id);
		JToolBarHelper::title( 'Form Maker: Add New Form');		
		JToolBarHelper::apply();
		JToolBarHelper::save();
		JToolBarHelper :: custom( 'save_and_new', 'save-new.png', '', 'Save & New', '', '' );
		JToolBarHelper :: custom( 'save_as_copy', 'copy.png', '', 'Save as Copy', '', '' );
		JToolBarHelper :: spacer(5);
		JToolBarHelper :: divider();
		JToolBarHelper :: spacer(5);
		JToolBarHelper :: custom( 'form_options_temp', 'cog.png', '', 'Form Options', '', '' );	
		if(!isset($row->form) || (isset($row->form) && $row->form==''))	
		JToolBarHelper :: custom( 'form_layout_temp', 'file-2.png', '', 'Form Layout', '', '' );	
		JToolBarHelper :: spacer(5);
		JToolBarHelper :: divider();
		JToolBarHelper :: spacer(5);
		JToolBarHelper::cancel();		
	}

	public static function _DEFAULT() {
		//$document = JFactory::getDocument();
		//$document->addStyleSheet('components/com_formmaker/FormMakerLogo.css');
		JToolBarHelper::title( 'Form Maker');		
		JToolBarHelper::addNew();
		JToolBarHelper::editList();
		JToolBarHelper::custom( 'copy', 'copy.png', 'copy_f2.png', 'Copy' );
		JToolBarHelper::deleteList();

	}

}

?>