<?php if(!defined('ABSPATH')) die('Direct access denied.'); ?>

<div class="wrap">
	<?php echo $screen_icon; ?>
	<h2><?php echo $page_title; ?></h2>
	<div class="intro">
		<p><?php _e('Place the code that you want to appear when wp_head and wp_footer actions are called by WordPress. Make sure that the current active theme uses wp_head() and wp_footer() for this plugin to work. Example usage: Google Analytics tracking code, inserting script tags, or style tags.', $textdomain); ?></p>
	</div>
	<?php echo $debug; ?>
	<form method="post" action="options.php">
		<?php
		echo $settings_fields;
		?>
		<table class="form-table">
			<tr>
				<th><label for="wphf-wp_head"><?php _e('Add this in wp_head():', $textdomain); ?></label></th>
				<td>
					<textarea rows="10" class="widefat" id="wphf-wp_head" name="<?php echo esc_attr( $option_name."[wp_head]" ); ?>"><?php echo esc_textarea( $settings_data['wp_head'] ); ?></textarea>
					<em><?php _e('Accepts raw text.', $textdomain); ?></em>
				</td>
			</tr>
			<tr>
				<th><label for="wphf-wp_footer"><?php _e('Add this in wp_footer():', $textdomain); ?></label></th>
				<td>
					<textarea rows="10" class="widefat" id="wphf-wp_footer" name="<?php echo esc_attr( $option_name."[wp_footer]" ); ?>"><?php echo esc_textarea( $settings_data['wp_footer'] ); ?></textarea>
					<em><?php _e('Accepts raw text.', $textdomain); ?></em>
				</td>
			</tr>
		</table>
		<br /><br />
		<?php submit_button( __('Clear All', $textdomain), 'secondary', 'reset', false) ?> &nbsp;
		<?php submit_button( __('Save', $textdomain), 'primary', 'submit', false) ?>
	</form>
	
</div>