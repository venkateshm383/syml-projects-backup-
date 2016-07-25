<?php
/*
 * Plugin Name: BNE Flyout
 * Version: 1.2.9
 * Plugin URI: http://www.bnecreative.com/products/off-canvas-sidebar-content-for-wordpress/
 * Description:  Adds hidden sidebars or off-canvas content (left, right, top, and bottom), including custom menus, to all pages of your website that are triggered from a floating image, button or inline element.
 * Author: Kerry Kline, BNE Creative
 * Author URI: http://www.bnecreative.com
 * Requires at least: 3.8
 * License: GPL2

    Copyright 2015  BNE Creative

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License version 2,
    as published by the Free Software Foundation.

    You may NOT assume that you can use any other version of the GPL.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    The license for this software can likely be found here:
    http://www.gnu.org/licenses/gpl-2.0.html

*/


// Exit if accessed directly
if ( !defined( 'ABSPATH' ) ) exit;

// Check WP version on Activation
global $wp_version;
if ( version_compare( $wp_version,"3.8","<" ) ) {
	exit( _e( 'Sorry, but BNE Flyouts requires WordPress 3.8+', 'bne-flyout' ) );
}


/* ===========================================================
 *	Plugin Constants
 * ======================================================== */

define( 'BNE_FLYOUT_VERSION', '1.2.9' );
define( 'BNE_FLYOUT_DIR', dirname( __FILE__ ) );
define( 'BNE_FLYOUT_URI', plugins_url( '', __FILE__ ) );
define( 'BNE_FLYOUT_BASENAME', plugin_basename( __FILE__ ) );



/*
 * 	Load plugin textdomain for localization
 *
 * 	@since 1.2.4
*/
function bne_flyout_load_textdomain() {
  load_plugin_textdomain( 'bne-flyout', false, dirname( plugin_basename( __FILE__ ) ) . '/languages' );
}
add_action( 'plugins_loaded', 'bne_flyout_load_textdomain' );



/* ===========================================================
 *	Plugin Includes
 * ======================================================== */


// Register Custom Post Type
include_once( BNE_FLYOUT_DIR . '/includes/cpt.php' );

// ACF Setup and Custom Fields
include_once( BNE_FLYOUT_DIR . '/includes/acf/acf-setup.php' );

// Flyout Output to frontend
include_once( BNE_FLYOUT_DIR . '/includes/flyout-styles.php' );
include_once( BNE_FLYOUT_DIR . '/includes/flyout-output.php' );


/*
 * Load and Activate Plugin Updater Class.
 * @since 1.0
 * @updated v1.2.3
*/
require_once( BNE_FLYOUT_DIR . '/includes/wp-updates-plugin.php' );
new WPUpdatesPluginUpdater_819( 'http://wp-updates.com/api/2/plugin', plugin_basename(__FILE__));




/* ===========================================================
 *	Plugin Scripts and Styles
 * ======================================================== */

/*
 *	Register Main Plugin CSS and JS
 *	@since v1.0
*/
function bne_flyout_register_enqueue_scripts_styles() {

	// Register the CSS
	wp_register_style( 'bne-flyout-styles', BNE_FLYOUT_URI . '/assets/css/bne-flyout.css', '', BNE_FLYOUT_VERSION, 'all' );

	// Dev version
	//wp_register_script( 'sidr', BNE_FLYOUT_URI . '/assets/js/jquery.sidr.dev.js', array('jquery'), BNE_FLYOUT_VERSION, true );

	// Register Sidr JS
	wp_register_script( 'sidr', BNE_FLYOUT_URI . '/assets/js/jquery.sidr.min.js', array('jquery'), BNE_FLYOUT_VERSION, true );

	// Enqueue the plugin CSS and JS
	wp_enqueue_style( 'bne-flyout-styles' );
	wp_enqueue_script( 'sidr' );

}
add_action( 'wp_enqueue_scripts', 'bne_flyout_register_enqueue_scripts_styles', 99 );