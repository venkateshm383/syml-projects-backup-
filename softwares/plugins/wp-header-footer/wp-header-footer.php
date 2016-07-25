<?php
/*
Plugin Name: WP Header Footer
Plugin URI: http://www.codefleet.net/wp-header-footer/
Description: Add a settings page in WP admin where you can paste code for wp_head and wp_footer.
Version: 1.2.0
Author: Nico Amarilla
Author URI: http://www.codefleet.net/
License: GPL-2.0+
License URI: http://www.gnu.org/licenses/gpl-2.0.txt
*/

// Autoloader
function wphf_autoloader($classname) {
    if(false !== strpos($classname, 'Wphf')){
        $plugin_dir = realpath(plugin_dir_path(__FILE__)) . DIRECTORY_SEPARATOR;
        $classname = str_replace('_', DIRECTORY_SEPARATOR, $classname);
        $file = $plugin_dir .'src'.DIRECTORY_SEPARATOR. $classname . '.php';
        require_once $file;
    }
}
spl_autoload_register('wphf_autoloader');

// Hook the plugin
add_action('plugins_loaded', 'wphf_init', 10); // Priority must be less than target action
function wphf_init() {
    
    $plugin = new Wphf_Main();
    
    $plugin['name'] = 'WP Header Footer';
    $plugin['path'] = realpath(plugin_dir_path(__FILE__)) . DIRECTORY_SEPARATOR;
    $plugin['url'] = plugin_dir_url(__FILE__);
    $plugin['debug'] = false;
    $plugin['version'] = '1.2.0';
	$plugin['textdomain'] = 'wphf';
    $plugin['slug'] = 'wp-header-footer/wp-header-footer.php'; 
    $plugin['nonce_name'] = 'wphf_nonce';
    $plugin['nonce_action'] = 'wphf_action';
    
    load_plugin_textdomain( $plugin['textdomain'], false, basename(dirname(__FILE__)).'/languages' ); // Load language files
    
    $plugin['view.view_folder'] = $plugin['path'].'views'.DIRECTORY_SEPARATOR;
    $plugin['view'] = new Wphf_View( $plugin['view.view_folder'] );
    
    $plugin['admin'] = new Wphf_Admin();
    
    $plugin['settings_page'] = new Wphf_SettingsPage();
    $plugin['settings_page.page_title'] =  __('WP Header Footer', $plugin['textdomain']);
    $plugin['settings_page.menu_title'] =  __('WP Header Footer', $plugin['textdomain']);
    $plugin['settings_page.option_group'] = 'wphf_option_group';
    $plugin['settings_page.option_name'] = 'wphf_option_name';
    $plugin['settings_page.parent_slug'] = 'options-general.php';
    $plugin['settings_page.menu_slug'] = 'wphf-settings-sub-page';
    $plugin['settings_page.capability'] = 'manage_options';
    
    $plugin['frontend'] = new Wphf_Frontend();
    
    $plugin->run();
}