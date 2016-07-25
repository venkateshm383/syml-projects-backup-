<?php
/*
 *	ACF Core Setup
 *	Advanced Custom Fields Pro
 *  http://www.advancedcustomfields.com/
 *
 * 	@author		Kerry Kline
 * 	@copyright	Copyright (c) 2015, Kerry Kline
 * 	@link		http://www.bnecreative.com
 *	@package	BNE Flyout
 *
 *	@ACF Version	5.2.3
 *	@updated		April 23, 2015
 *
*/



/* ===========================================================
 *	Load ACF Core
 * ======================================================== */

function bne_flyout_acf_load() {

	if( ! class_exists( 'acf' ) ) {

		// load ACF 5 if not already loaded
		include_once( BNE_FLYOUT_DIR . '/includes/acf/acfpro/acf.php' );

		// Remove the ACF admin menu
		add_filter( 'acf/settings/show_admin', '__return_false' );

		// Set ACF 5 Settings Directory
		if( ! function_exists( 'bne_acf_settings_dir' ) ) {
			function bne_acf_settings_dir( $dir ) {
				$dir = BNE_FLYOUT_URI . '/includes/acf/acfpro/';
				return $dir;
			}
			add_filter( 'acf/settings/dir', 'bne_acf_settings_dir' );
		}

		// Set ACF 5 Settings Path
		if( ! function_exists( 'bne_acf_settings_path' ) ) {
			function bne_acf_settings_path( $path ) {
				$path = BNE_FLYOUT_DIR . '/includes/acf/acfpro/';
				return $path;
			}
			add_filter( 'acf/settings/path', 'bne_acf_settings_path' );
		}


	}

	// Include ACF Custom Fields for our Plugin
	include_once( BNE_FLYOUT_DIR . '/includes/acf/acf-custom-fields.php' );

}
add_action( 'after_setup_theme', 'bne_flyout_acf_load' );


// ACF Menu Field Add-On
// https://wordpress.org/plugins/advanced-custom-fields-nav-menu-field/
if( ! class_exists( 'ACF_Nav_Menu_Field_Plugin' ) ) {
	include_once( BNE_FLYOUT_DIR . '/includes/acf/acf-menu/fz-acf-nav-menu.php' );
}