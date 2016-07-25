<?php
/**
 * Class for front end
 */
class Wphf_Frontend extends Wphf_Base {
	
	public function bootstrap(){
		
        // Hook to wp_head
        add_action('wp_head', array( $this, 'wp_head' ));
        
        // Hook to wp_footer
        add_action('wp_footer', array( $this, 'wp_footer' ));
		
	}
	
	public function wp_head() {
        $settings_data = $this->plugin['settings_page']->get_settings_data();
        if('' !== $settings_data['wp_head']){
            echo $settings_data['wp_head'];
        }
    }
    
    public function wp_footer() {
        $settings_data = $this->plugin['settings_page']->get_settings_data();
        if('' !== $settings_data['wp_footer']){
            echo $settings_data['wp_footer'];
        }
    }
}