<?php
/*------------------------------------------------------------------------
# default.php - Mortgage Application Form  Component
# ------------------------------------------------------------------------
# author    Rameez
# copyright Copyright (C) 2014. All Rights Reserved
# license   GNU/GPL Version 2 or later - http://www.gnu.org/licenses/gpl-2.0.html
# website   bistasolutions.com
-------------------------------------------------------------------------*/

// No direct access to this file
defined('_JEXEC') or die('Restricted access');

?>
<div id="mortgageapplication-content">
	<p><strong>Name</strong>: <?php echo $this->item->name; ?></p>
	<p><strong>Username</strong>: <?php echo $this->item->username; ?></p>
	<p><strong>Password</strong>: <?php echo $this->item->password; ?></p>
	<p><strong>Created_date</strong>: <?php echo $this->item->createddate; ?></p>
	<p><strong>Updated_date</strong>: <?php echo $this->item->updateddate; ?></p>
	<p><strong>Status</strong>: <?php echo $this->item->status; ?></p>
</div>