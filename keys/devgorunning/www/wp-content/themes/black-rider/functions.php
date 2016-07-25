<?php

include_once get_template_directory() . '/functions/inkthemes-functions.php';
$functions_path = get_template_directory() . '/functions/';
define('CLASS_PATH', $functions_path);
/* These files build out the options interface.  Likely won't need to edit these. */
require_once ($functions_path . 'admin-functions.php');  // Custom functions and plugins
require_once ($functions_path . 'admin-interface.php');  // Admin Interfaces 
require_once ($functions_path . 'theme-options.php');   // Options panel settings and custom settings
require_once ($functions_path . 'define_template.php');

/* ----------------------------------------------------------------------------------- */
/* jQuery Enqueue */
/* ----------------------------------------------------------------------------------- */
add_action( 'admin_bar_menu', 'remove_wp_logo', 999 );

function remove_wp_logo( $wp_admin_bar ) {
	$wp_admin_bar->remove_node( 'wp-logo' );
}
function blcr_wp_enqueue_scripts() {
    if (!is_admin()) {
        wp_enqueue_script('jquery');
        wp_enqueue_script('inkthemes-ddsmoothmenu', get_template_directory_uri() . '/js/ddsmoothmenu.js', array('jquery'));
        wp_enqueue_script('flex-slider', get_template_directory_uri() . '/js/jquery.flexslider-min.js', array('jquery'));
        wp_enqueue_script('inkthemes-custom', get_template_directory_uri() . '/js/custom.js', array('jquery'));
        wp_enqueue_script('mobile-menu', get_template_directory_uri() . "/js/mobile-menu.js", array('jquery'), '', true);
    }
}

add_action('wp_enqueue_scripts', 'blcr_wp_enqueue_scripts');


//inkthemes get option
function blcr_get_option($name) {
    $options = get_option('inkthemes_options');
    if (isset($options[$name]))
        return $options[$name];
}

//inkthemes update option
function blcr_update_option($name, $value) {
    $options = get_option('inkthemes_options');
    $options[$name] = $value;
    return update_option('inkthemes_options', $options);
}

//inkthemes delete option
function blcr_delete_option($name) {
    $options = get_option('inkthemes_options');
    unset($options[$name]);
    return update_option('inkthemes_options', $options);
}

//Enqueue comment thread js
function blcr_enqueue_scripts() {
    if (is_singular() and get_site_option('thread_comments')) {
        wp_enqueue_script('comment-reply');
    }
}

add_action('wp_enqueue_scripts', 'blcr_enqueue_scripts');

//Add plugin notification 
//require_once(get_template_directory() . '/functions/plugin-activation.php');
//require_once(get_template_directory() . '/functions/inkthemes-plugin-notify.php');
add_action('tgmpa_register', 'blackrider_plugins_notify');
?>
