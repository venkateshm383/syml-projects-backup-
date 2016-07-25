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
jimport('joomla.filter.output');
?>
<div id="mortgageapplication-mortgageapplication">
	<?php foreach($this->items as $item){ ?>
		<?php
		if(empty($item->alias)){
			$item->alias = $item->name;
		};
		$item->alias = JFilterOutput::stringURLSafe($item->alias);
		$item->linkURL = JRoute::_('index.php?option=com_mortgageapplication&view=mortgageapplicationedit&id='.$item->id.':'.$item->alias);
		?>
		<p><strong>Name</strong>: <a href="<?php echo $item->linkURL; ?>"><?php echo $item->name; ?></a></p>
		<p><strong>Username</strong>: <?php echo $item->username; ?></p>
		<p><strong>Password</strong>: <?php echo $item->password; ?></p>
		<p><strong>Created_date</strong>: <?php echo $item->createddate; ?></p>
		<p><strong>Updated_date</strong>: <?php echo $item->updateddate; ?></p>
		<p><strong>Status</strong>: <?php echo $item->status; ?></p>
		<p><strong>Link URL</strong>: <a href="<?php echo $item->linkURL; ?>">Go to page</a> - <?php echo $item->linkURL; ?></p>
		<br /><br />
	<?php }; ?>
</div>
