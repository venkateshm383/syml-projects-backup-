<?php
/**
 * Class for admin area
 */
class Wphf_Admin extends Wphf_Base {
	
	public function bootstrap(){
		
		// Add link in WP plugins screen
        add_filter( 'plugin_action_links', array( $this, 'settings_link' ), 10, 2 );
		
	}
	
	/**
    * Add a "Settings" link on the plugins page 
    */
    public function settings_link( $links, $file ) {

        if ( $this->plugin['slug'] == $file ) {
            $links[] = '<a href="' . admin_url( 'options-general.php?page='.$this->plugin['settings_page.menu_slug'] ) . '">' . __( 'Settings', $this->plugin['textdomain'] ) . '</a>';
        }

        return $links;
    }
}