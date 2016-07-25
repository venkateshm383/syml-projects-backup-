<?php
/*
 *	Custom Fields
 *	Advanced Custom Fields Pro
 *  http://www.advancedcustomfields.com/
 *
 * 	@author		Kerry Kline
 * 	@copyright	Copyright (c) 2015, Kerry Kline
 * 	@link		http://www.bnecreative.com
 *	@package	BNE Flyout
 *
 *	@ACF Version	5.2.3
 *	@updated		July 18, 2015
 *
*/



/* ===========================================================
 *	Register the Custom Fields for our Plugin
 * ======================================================== */

$flyout_metabox_title = __( 'Flyout Design', 'bne-flyout');

if(function_exists("register_field_group"))
{
	register_field_group(array (
		'id' => 'acf_bne-flyout-cpt',
		'title' => $flyout_metabox_title,
		'fields' => array (
			array (
				'key' => 'field_5376922effce8',
				'label' => __('Flyout Content', 'bne-flyout'),
				'name' => '',
				'type' => 'tab',
			),
			array (
				'key' => 'field_53768ba8577c5',
				'label' => __('Flyout Content', 'bne-flyout'),
				'name' => 'bne_flyout_content',
				'type' => 'wysiwyg',
				'instructions' => __('You can include any text, images from the media library, basic HTML, and most shortcodes.', 'bne-flyout'),
				'default_value' => '',
				'toolbar' => 'full',
				'media_upload' => 'yes',
			),
			array (
				'key' => 'field_54651c995e88b',
				'label' => __('Custom Menu', 'bne-flyout'),
				'name' => 'bne_flyout_custom_menu',
				'type' => 'nav_menu',
				'instructions' => __('Add an available custom menu (Appearance > menus) to this Flyout. This will be displayed below what you have added in the content editor above.', 'bne-flyout'),
				'save_format' => 'menu',
				'container' => 0,
				'allow_null' => 1,
			),

			array (
				'key' => 'field_55aab1d2ce485',
				'label' => __('Flyout Content Order', 'bne-flyout'),
				'name' => 'bne_flyout_content_order',
				'type' => 'select',
				'instructions' => __('If using a custom menu and content, which order should they appear?', 'bne-flyout'),
				'choices' => array (
					1 => __('Content then Menu', 'bne-flyout'),
					2 => __('Menu then Content', 'bne-flyout'),
				),
				'default_value' => 1,
				'allow_null' => 0,
				'multiple' => 0,
			),


			array (
				'key' => 'field_537ed83192781',
				'label' => __('Flyout Styles', 'bne-flyout'),
				'name' => '',
				'type' => 'tab',
			),
			array (
				'key' => 'field_537ea373f7d0f',
				'label' => __('General Font Color', 'bne-flyout'),
				'name' => 'bne_flyout_content_font_color',
				'type' => 'color_picker',
				'instructions' => __('Sets a general font color within the Flyout container.', 'bne-flyout'),
				'default_value' => '#ffffff',
			),
			array (
				'key' => 'field_537ea3ccf7d11',
				'label' => __('Link Color', 'bne-flyout'),
				'name' => 'bne_flyout_content_font_link_color',
				'type' => 'color_picker',
				'instructions' => __('Sets the color for links within the Flyout container.', 'bne-flyout'),
				'default_value' => '#EEEEEE',
			),
			array (
				'key' => 'field_537ea3b8f7d10',
				'label' => __('Header Font Color', 'bne-flyout'),
				'name' => 'bne_flyout_content_font_header_color',
				'type' => 'color_picker',
				'instructions' => __('Sets the color for headers (h1-h6) within the Flyout container.', 'bne-flyout'),
				'default_value' => '#ffffff',
			),
			array (
				'key' => 'field_5377c1a3c91d6',
				'label' => __('Background Color', 'bne-flyout'),
				'name' => 'bne_flyout_background_color',
				'type' => 'color_picker',
				'instructions' => __('Sets a background color to the Flyout container.', 'bne-flyout'),
				'default_value' => '#333333',
			),
			array (
				'key' => 'field_5377c1c9c91d7',
				'label' => __('Background Image', 'bne-flyout'),
				'name' => 'bne_flyout_background_image',
				'type' => 'image',
				'instructions' => __('Sets a background image to the Flyout container.', 'bne-flyout'),
				'save_format' => 'url',
				'preview_size' => 'thumbnail',
				'library' => 'all',
			),
			array (
				'key' => 'field_5377c1f1c91d8',
				'label' => __('Background Image Repeat', 'bne-flyout'),
				'name' => 'bne_flyout_background_image_repeat',
				'type' => 'select',
				'choices' => array (
					'no-repeat' => __('No Repeat', 'bne-flyout'),
					'repeat-x' => __('Repeat Horizontally', 'bne-flyout'),
					'repeat-y' => __('Repeat Vertically', 'bne-flyout'),
					'repeat' => __('Repeat All', 'bne-flyout'),
				),
				'default_value' => '',
				'allow_null' => 0,
				'multiple' => 0,
			),
			array (
				'key' => 'field_5377c21ec91d9',
				'label' => __('Background Image Position', 'bne-flyout'),
				'name' => 'bne_flyout_background_image_position',
				'type' => 'select',
				'choices' => array (
					'left top' => __('Left Top', 'bne-flyout'),
					'left center' => __('Left Center', 'bne-flyout'),
					'left bottom' => __('Left Bottom', 'bne-flyout'),
					'right top' => __('Right Top', 'bne-flyout'),
					'right center' => __('Right Center', 'bne-flyout'),
					'right bottom' => __('Right Bottom', 'bne-flyout'),
					'center top' => __('Center Top', 'bne-flyout'),
					'center center' => __('Center Center', 'bne-flyout'),
					'center bottom' => __('Center Bottom', 'bne-flyout'),
				),
				'default_value' => '',
				'allow_null' => 0,
				'multiple' => 0,
			),
			array (
				'key' => 'field_5377be0c64fa2',
				'label' => __('Flyout Settings', 'bne-flyout'),
				'name' => '',
				'type' => 'tab',
			),
			array (
				'key' => 'field_53768bc7577c6',
				'label' => __('Flyout Location', 'bne-flyout'),
				'name' => 'bne_flyout_location',
				'type' => 'select',
				'instructions' => __('Choose the window side to "flyout" your content from.', 'bne-flyout'),
				'choices' => array (
					'Left Side' => __('Left Side', 'bne-flyout'),
					'Right Side' => __('Right Side', 'bne-flyout'),
					'Top Side' => __('Top Side', 'bne-flyout'),
					'Bottom Side' => __('Bottom Side', 'bne-flyout'),
				),
				'default_value' => '',
				'allow_null' => 0,
				'multiple' => 0,
			),
			array (
				'key' => 'field_537ed61cb0e94',
				'label' => __('Flyout Size', 'bne-flyout'),
				'name' => 'bne_flyout_size',
				'type' => 'number',
				'instructions' => __('Sets the size of the Flyout container in pixels (px). This will be the width for left and right Flyout locations, or the height for top and bottom Flyout locations.', 'bne-flyout'),
				'default_value' => 300,
				'placeholder' => '',
				'prepend' => '',
				'append' => 'px',
				'min' => 1,
				'max' => '',
				'step' => 1,
			),
			array (
				'key' => 'field_5395ea83298b9',
				'label' => __('Flyout Displacement', 'bne-flyout'),
				'name' => 'bne_flyout_displacement',
				'type' => 'select',
				'instructions' => __('Specify how the page content reacts to the Flyout appearing. You can either "Push" the page content out of the way, or you can "Slide" above the content.', 'bne-flyout'),
				'choices' => array (
					'true' => __('Push Content (default)', 'bne-flyout'),
					'false' => __('Slide Above Content', 'bne-flyout'),
				),
				'default_value' => 'true',
				'allow_null' => 0,
				'multiple' => 0,
			),
			array (
				'key' => 'field_54c5bfcda1d8e',
				'label' => __('Flyout Restrictions', 'bne-flyout'),
				'name' => 'bne_flyout_restrictions',
				'type' => 'post_object',
				'instructions' => __('Restrict this Flyout to <strong><u>only display</strong></u> on certain pages within your website.', 'bne-flyout'),
				'post_type' => array (
					0 => 'page',
					1 => 'post',
				),
				'taxonomy' => array (
					0 => 'all',
				),
				'allow_null' => 1,
				'multiple' => 1,
			),
			array (
				'key' => 'field_5377d5c3dc838',
				'label' => __('Trigger Settings', 'bne-flyout'),
				'name' => '',
				'type' => 'tab',
			),
			array (
				'key' => 'field_5377d5cfdc839',
				'label' => __('Trigger Type', 'bne-flyout'),
				'name' => 'bne_flyout_trigger_type',
				'type' => 'select',
				'instructions' => __('By default, the only way to expose a Flyout is to click on a trigger button or image used on the side of the browser window. However, you can control this: just add the trigger class name (shown to the right and on the Flyout admin post list) for this Flyout to any element on your page (link, button, image). Then that element becomes the trigger for this flyout.<br><br><strong>Note:</strong> Choosing "Hide" will not show the trigger on the side of the browser window at all.', 'bne-flyout'),
				'choices' => array (
					'Button' => __('Button', 'bne-flyout'),
					'Image' => __('Image', 'bne-flyout'),
					'Hide' => __('Hide', 'bne-flyout'),
				),
				'default_value' => '',
				'allow_null' => 0,
				'multiple' => 0,
			),
			array (
				'key' => 'field_5377d72ddc83a',
				'label' => __('Trigger Location', 'bne-flyout'),
				'name' => 'bne_flyout_trigger_location',
				'type' => 'select',
				'instructions' => __('Set the location of the Flyout trigger.<br><br><strong>Note:</strong> It\'s usually best, for the user, for this to match the same side as the Flyout itself.', 'bne-flyout'),
				'conditional_logic' => array (
					'status' => 1,
					'rules' => array (
						array (
							'field' => 'field_5377d5cfdc839',
							'operator' => '!=',
							'value' => 'Hide',
						),
					),
					'allorany' => 'all',
				),
				'choices' => array (
					'Left Side' => __('Left Side', 'bne-flyout'),
					'Right Side' => __('Right Side', 'bne-flyout'),
					'Top Side' => __('Top Side', 'bne-flyout'),
					'Bottom Side' => __('Bottom Side', 'bne-flyout'),
				),
				'default_value' => '',
				'allow_null' => 0,
				'multiple' => 0,
			),
			array (
				'key' => 'field_5377eb481412d',
				'label' => __('Trigger Position', 'bne-flyout'),
				'name' => 'bne_flyout_trigger_position_top',
				'type' => 'text',
				'instructions' => __('Based on location. Note: Since v1.2.7, you can now use px, em, or % units. If no unit is specified, it will default to %.<br><br>- <strong>Left/Right Trigger Location:</strong> If the trigger is located on either the left or right side, this will be the value from the <strong><u>top</u></strong> of the window.<br> - <strong>Top/bottom Trigger Location:</strong> If the trigger is located on the top or bottom side, this will be the value from the <strong><u>left</u></strong> of the browser window.', 'bne-flyout'),
				'conditional_logic' => array (
					'status' => 1,
					'rules' => array (
						array (
							'field' => 'field_5377d5cfdc839',
							'operator' => '!=',
							'value' => 'Hide',
						),
					),
					'allorany' => 'any',
				),
				'default_value' => 40,
				'placeholder' => '',
				'prepend' => '',
				'append' => '',
				'formatting' => 'none',
				'maxlength' => 5,
			),
			array (
				'key' => 'field_5377f166cde6d',
				'label' => __('Button Label', 'bne-flyout'),
				'name' => 'bne_flyout_trigger_button_label',
				'type' => 'text',
				'instructions' => __('This is the text used for the button label.', 'bne-flyout'),
				'conditional_logic' => array (
					'status' => 1,
					'rules' => array (
						array (
							'field' => 'field_5377d5cfdc839',
							'operator' => '==',
							'value' => 'Button',
						),
					),
					'allorany' => 'all',
				),
				'default_value' => 'Click Here!',
				'placeholder' => '',
				'prepend' => '',
				'append' => '',
				'formatting' => 'none',
				'maxlength' => '',
			),
			array (
				'key' => 'field_5377ea67cfdb6',
				'label' => __('Background Color', 'bne-flyout'),
				'name' => 'bne_flyout_trigger_background_color',
				'type' => 'color_picker',
				'instructions' => __('The background color for the button.', 'bne-flyout'),
				'conditional_logic' => array (
					'status' => 1,
					'rules' => array (
						array (
							'field' => 'field_5377d5cfdc839',
							'operator' => '==',
							'value' => 'Button',
						),
					),
					'allorany' => 'any',
				),
				'default_value' => '#333333',
			),
			array (
				'key' => 'field_5377f0c2cf777',
				'label' => __('Trigger Image', 'bne-flyout'),
				'name' => 'bne_flyout_trigger_image',
				'type' => 'image',
				'instructions' => __('Choose an image to represent the flyout trigger. For best results, use an image that is already sized for your needs. Left and Right image trigger locations have a max-width of 200px to help prevent covering too much of your content.', 'bne-flyout'),
				'conditional_logic' => array (
					'status' => 1,
					'rules' => array (
						array (
							'field' => 'field_5377d5cfdc839',
							'operator' => '==',
							'value' => 'Image',
						),
					),
					'allorany' => 'all',
				),
				'save_format' => 'object',
				'preview_size' => 'full',
				'library' => 'all',
			),
			array (
				'key' => 'field_537a3e12ceffd',
				'label' => __('Trigger Responsive Visibility', 'bne-flyout'),
				'name' => 'bne_flyout_trigger_visibility',
				'type' => 'checkbox',
				'instructions' => __('Select any resolution type you\'d like to hide this trigger on. This is optional, but can be utilized to deliver different Flyouts to different devices. Also useful to simply hide the trigger for small screens so that they don\'t take up as much space.', 'bne-flyout'),
				'conditional_logic' => array (
					'status' => 1,
					'rules' => array (
						array (
							'field' => 'field_5377d5cfdc839',
							'operator' => '!=',
							'value' => 'Hide',
						),
					),
					'allorany' => 'all',
				),
				'choices' => array (
					'hide_on_standard' => __('Hide on Standard Resolutions ( > 900px)', 'bne-flyout'),
					'hide_on_tablet' => __('Hide on Tablets ( 601px to 899px )', 'bne-flyout'),
					'hide_on_mobile' => __('Hide on Mobile Devices ( 0 < 600px )', 'bne-flyout'),
				),
				'default_value' => '',
				'layout' => 'vertical',
			),
		),
		'location' => array (
			array (
				array (
					'param' => 'post_type',
					'operator' => '==',
					'value' => 'bne_flyout',
					'order_no' => 0,
					'group_no' => 0,
				),
			),
		),
		'options' => array (
			'position' => 'normal',
			'layout' => 'default',
			'hide_on_screen' => array (
			),
		),
		'menu_order' => 0,
	));
}