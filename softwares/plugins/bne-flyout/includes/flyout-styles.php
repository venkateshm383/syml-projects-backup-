<?php
/*
 *	Flyout styles output to wp_head()
 *
 * 	@author		Kerry Kline
 * 	@copyright	Copyright (c) 2015, Kerry Kline
 * 	@link		http://www.bnecreative.com
 *	@package	BNE Flyouts
 *
*/



/*
 *	Per Flyout Custom CSS
 *	Loops through each published Flyout and grabs only the custom CSS styles
 *	then adds it into wp_head.
 *
 *	@since v1.0
 *	@updated v1.2.7.1
 *
*/
function bne_flyout_custom_css_wp_head() {

	// Loop through all Flyouts
	$bne_flyout = new WP_Query( array( 'post_type' => 'bne_flyout', 'posts_per_page' => -1 ) );
	if( $bne_flyout->have_posts() ) { ?>

<!-- Flyout Custom CSS -->
<style type="text/css">
<?php while( $bne_flyout->have_posts() ) : $bne_flyout->the_post();

	// Get Flyout ID
	$flyout_id = get_the_ID();

	// Flyout Size @since v1.2
	$flyout_size = esc_html( get_field( 'bne_flyout_size' ) );

	// @since v1.2, bne_flyout_container_width has changed to bne_flyout_size
	// Legacy... Use the old size for those that have not updated their individual Flyouts
	if( !$flyout_size && get_field( 'bne_flyout_container_width' ) ) {
		$flyout_size = esc_html( get_field( 'bne_flyout_container_width' ) );
	}

	// Set size CSS based on Flyout location
	// Note: Width is re-added here for top/bottom to satisfy google maps.
	if( 'Left Side' == get_field( 'bne_flyout_location' ) ) {
		$flyout_size = 'width:'.$flyout_size.'px; left:-'.$flyout_size.'px;';
	} elseif( 'Right Side' == get_field( 'bne_flyout_location' ) ) {
		$flyout_size = 'width:'.$flyout_size.'px; right:-'.$flyout_size.'px;';
	} elseif( 'Top Side' == get_field( 'bne_flyout_location' ) ) {
		$flyout_size = 'height:'.$flyout_size.'px; top:-'.$flyout_size.'px; width: 100vw;';
	} elseif( 'Bottom Side' == get_field( 'bne_flyout_location' ) ) {
		$flyout_size = 'height:'.$flyout_size.'px; bottom:-'.$flyout_size.'px; width: 100vw;';
	}

	// Flyout Styles
	$flyout_bg_color 		= esc_html( get_field( 'bne_flyout_background_color' ) );
	$flyout_bg_image 		= esc_url( get_field( 'bne_flyout_background_image' ) );
	$flyout_bg_repeat 		= esc_html( get_field( 'bne_flyout_background_image_repeat' ) );
	$flyout_bg_position 	= esc_html( get_field( 'bne_flyout_background_image_position' ) );
	$body_color 			= esc_html( get_field( 'bne_flyout_content_font_color' ) );
	$link_color 			= esc_html( get_field( 'bne_flyout_content_font_link_color' ) );
	$header_color 			= esc_html( get_field( 'bne_flyout_content_font_header_color' ) );

	?>

	#flyout-content-id-<?php echo $flyout_id; ?> { <?php echo $flyout_size; ?> }
	#flyout-content-id-<?php echo $flyout_id; ?> .flyout-content { color: <?php echo $body_color; ?>; }
	#flyout-content-id-<?php echo $flyout_id; ?> .flyout-content a,
	#flyout-content-id-<?php echo $flyout_id; ?> .flyout-content a:hover,
	#flyout-content-id-<?php echo $flyout_id; ?> .flyout-content a:visited { color: <?php echo $link_color; ?>; }
	#flyout-content-id-<?php echo $flyout_id; ?> .flyout-content h1,
	#flyout-content-id-<?php echo $flyout_id; ?> .flyout-content h2,
	#flyout-content-id-<?php echo $flyout_id; ?> .flyout-content h3,
	#flyout-content-id-<?php echo $flyout_id; ?> .flyout-content h4,
	#flyout-content-id-<?php echo $flyout_id; ?> .flyout-content h5,
	#flyout-content-id-<?php echo $flyout_id; ?> .flyout-content h6 { color: <?php echo $header_color; ?>; }
	<?php if( $flyout_bg_color && !$flyout_bg_image ) { ?>
	#flyout-content-id-<?php echo $flyout_id; ?> .flyout-content {
		background-color: <?php echo $flyout_bg_color; ?>;
	}
	<?php } elseif( $flyout_bg_image && $flyout_bg_repeat == 'no-repeat' ) { ?>
	#flyout-content-id-<?php echo $flyout_id; ?> .flyout-content {
		background-color: <?php echo $flyout_bg_color; ?>;
		background-image: url('<?php echo $flyout_bg_image; ?>') !important;
		background-repeat: <?php echo $flyout_bg_repeat; ?>;
		background-position: <?php echo $flyout_bg_position; ?>;
		background-attachment: scroll;
		-webkit-background-size: cover;
		-moz-background-size: cover;
		-o-background-size: cover;
		background-size: cover;
	}
	<?php } elseif( $flyout_bg_image ) { ?>
	#flyout-content-id-<?php echo $flyout_id; ?> .flyout-content {
		background-color: <?php echo $flyout_bg_color; ?>;
		background-image: url('<?php echo $flyout_bg_image; ?>') !important;
		background-repeat: <?php echo $flyout_bg_repeat; ?>;
		background-position: <?php echo $flyout_bg_position; ?>;
		background-attachment: scroll;
	}
	<?php } ?>

<?php endwhile; ?>
</style>
<!-- END Flyout Custom CSS -->
	<?php }

	// Restore original Post Data
	wp_reset_postdata();

}
add_action( 'wp_head', 'bne_flyout_custom_css_wp_head' );