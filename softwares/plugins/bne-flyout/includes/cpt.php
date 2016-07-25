<?php
/*
 *	Setup Custom Post Type
 *
 * 	@author		Kerry Kline
 * 	@copyright	Copyright (c) 2015, Kerry Kline
 * 	@link		http://www.bnecreative.com
 *	@package	BNE Flyout
 *
 *	@updated	January 25, 2015
 *
*/



/*
 *	Register Custom Post Type
 *
 *	@since v1.0
 *	@updated v1.2
 *
*/
function bne_flyout_register_post_type() {

	$cpt_slug		= 'bne_flyout';
	$dashicon_icon	= 'dashicons-share-alt2';


	// Custom Post Type Labels
	$labels = array(
		'name'					=> __( 'Flyouts', 'bne-flyout' ),
		'singular_name'			=> __( 'Flyout', 'bne-flyout' ),
		'add_new'            	=> __( 'Add new Flyout', 'bne-flyout' ),
		'add_new_item'       	=> __( 'Add new Flyout', 'bne-flyout' ),
		'edit_item'          	=> __( 'Edit Flyout', 'bne-flyout' ),
		'new_item'           	=> __( 'New Flyout', 'bne-flyout' ),
		'all_items'          	=> __( 'All Flyouts', 'bne-flyout' ),
		'view_item'          	=> __( 'View Flyout', 'bne-flyout' ),
		'search_items'       	=> __( 'Search Flyouts', 'bne-flyout' ),
		'not_found'          	=> __( 'No Flyouts found', 'bne-flyout' ),
		'not_found_in_trash' 	=> __( 'No Flyouts found in trash', 'bne-flyout' ),
		'parent_item_colon'  	=> __( 'Parent Flyout', 'bne-flyout' ),
		'menu_name'          	=> __( 'Flyouts', 'bne-flyout' )
	);

	// Custom Post Type Supports
	$supports = array('title');

	// Custom Post Type Arguments
	$args = array(
	    'labels'				=> $labels,
	    'hierarchical'       	=> false,
	    'description'        	=> '',
	    'public'             	=> false,
	    'publicly_queryable' 	=> false,
	    'show_ui'            	=> true,
	    'show_in_menu'       	=> true,
	    'show_in_nav_menus'  	=> false,
	    'show_in_admin_bar'  	=> false,
	    'exclude_from_search'	=> true,
	    'query_var'          	=> true,
	    'rewrite'            	=> false,
	    'can_export'         	=> true,
	    'has_archive'        	=> false,
		'menu_icon' 			=> $dashicon_icon,
	    'supports'           	=> $supports,
	    'capability_type'    	=> 'post',
	);

	register_post_type( $cpt_slug, $args );

}
add_action( 'init', 'bne_flyout_register_post_type' );



/*
 *	CPT Update Messages when creating/editing a Flyout on the
 *	post edit screen.
 *	@since v1.0
 *	@updated v1.2
 *
*/
function bne_flyout_updated_messages( $messages ) {
	global $post, $post_ID;

    $screen = get_current_screen();
    if ( 'bne_flyout' == $screen->post_type ) {

		$messages["post"][1] 	= __( 'Flyout updated!', 'bne-flyout' );
		$messages["post"][6] 	= __( 'Flyout published!', 'bne-flyout' );
		$messages["post"][10] 	= __( 'Flyout draft updated!', 'bne-flyout' );

		return $messages;
	}

	return $messages;
}
add_filter( 'post_updated_messages', 'bne_flyout_updated_messages' );



/*
 *	Setup Columns on Post List Admin Screen
 * 	@since v1.0
 *	@updated v1.2
 *
*/
if ( function_exists( 'add_theme_support' ) ) {
    add_filter( 'manage_edit-bne_flyout_columns', 'bne_flyout_posts_columns', 5 );
    add_action( 'manage_posts_custom_column', 'bne_flyout_posts_custom_columns', 10, 2 );
}



/*
 *	Add/Remove Columns on Post List Admin Screen
 * 	@since v1.0
 *	@updated v1.2
 *
*/
function bne_flyout_posts_columns( $columns ) {

	unset ( $columns['date'] );
	unset ( $columns['author'] );

    $columns['bne_flyout_location'] 		= __( 'Flyout Location', 'bne-flyout' );
    $columns['bne_flyout_trigger_type'] 	= __( 'Trigger Type', 'bne-flyout' );
    $columns['bne_flyout_trigger_location'] = __( 'Trigger Location', 'bne-flyout' );
    $columns['bne_flyout_classname'] 		= __( 'Classname', 'bne-flyout' );

    return $columns;
}



/*
 *	Add Content to Columns on Post List Admin Screen
 * 	@since v1.0
 *	@updated v1.2
 *
*/
function bne_flyout_posts_custom_columns( $column_name, $post_id ) {

	$flyout_location = get_field( 'bne_flyout_location' );
	$trigger_type = get_field( 'bne_flyout_trigger_type' );
	$trigger_location = get_field( 'bne_flyout_trigger_location' );

	// Flyout Location
	if( $column_name === 'bne_flyout_location') {
		echo $flyout_location;
	}

	// Trigger Type
	if( $column_name === 'bne_flyout_trigger_type' ) {
		echo $trigger_type;
	}

	// Trigger Location
	if( $column_name === 'bne_flyout_trigger_location' ) {
		echo $trigger_location;
	}

	// Trigger Classname
	if( $column_name === 'bne_flyout_classname' ) {
		echo 'flyout-trigger-id-'.$post_id;
	}

} // END Columns



/*
 *	Add Custom Meta Box for our CPT
 *	@since v1.0
 *	@updated v1.2
 *
*/
function bne_flyout_admin_meta_boxes() {

    // Add Classname Help Box
    add_meta_box( 'singleshortcodediv', __( 'Flyout Classname', 'bne-flyout' ), 'bne_flyout_classname_box', 'bne_flyout', 'side', 'default');

}
add_action( 'do_meta_boxes', 'bne_flyout_admin_meta_boxes' );



/*
 *	Class Name Meta Box Content
 *	@since v1.0
 *	@updated v1.2
 *
*/
function bne_flyout_classname_box( $post ) {

		echo '<p>'.__( 'This class name allows you use any html element (link, image, menu item, etc) on your page to trigger this flyout.', 'bne-flyout' ).'</p>';
		echo '<p><strong>'.__('Classname', 'bne-flyout' ).':</strong> flyout-trigger-id-'.get_the_ID().'</p>';
		echo '<p><strong>'.__('Example', 'bne-flyout' ).'</strong>:</p>';
		echo '&lt;a href="#" class="flyout-trigger-id-'.get_the_ID().'">Click Me!&lt;/a>';
}