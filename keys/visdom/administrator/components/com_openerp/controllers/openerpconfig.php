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

jimport('joomla.application.component.controllerform');

/**
 * Openerpconfig controller class.
 */
class OpenerpControllerOpenerpconfig extends JControllerForm
{

    function __construct() {
        $this->view_list = 'openerpconfigs';
        parent::__construct();
    }

}