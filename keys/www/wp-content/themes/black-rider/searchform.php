<form role="search" method="get" class="searchform" action="<?php echo esc_url(home_url('/')); ?>">
    <div>
        <input type="text" onfocus="if (this.value == '<?php _e('Search','black-rider'); ?>') {this.value = '';}" onblur="if (this.value == '') {this.value = '<?php _e('Search','black-rider'); ?>';}"  value="<?php _e('Search','black-rider'); ?>" name="s" id="s" />
        <input type="submit" id="searchsubmit" value="" />
    </div>
</form>
<div class="clear"></div>
