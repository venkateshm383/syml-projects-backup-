<?php
abstract class Wphf_Base {
    protected $plugin;
    
    final public function run( $plugin ) {
        $this->plugin = $plugin;
    }
}