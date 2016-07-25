<?php
/*
 *	Flyout output to wp_footer()
 *
 * 	@author		Kerry Kline
 * 	@copyright	Copyright (c) 2015, Kerry Kline
 * 	@link		http://www.bnecreative.com
 *	@package	BNE Flyouts
 *
 *
*/


/*
 *	Loop through available Flyouts and sent to frontend.
 *	Output to wp_footer()
 *	@since v1.0
 *	@updated v1.2.8
 *
*/
function bne_flyout_footer_output() {


	// Get Current Page ID
	$page_id = get_the_ID();

	// CPT Query args
	$query_args = array(
		'post_type' 		=> 	'bne_flyout',	// CPT
		'posts_per_page'	=> 	-1				// Number of Flyouts
	);


	// Setup the Query
	$bne_flyout = new WP_Query( $query_args );
	if( $bne_flyout->have_posts() ) { ?>

		<!-- BNE Flyouts -->
		<div class="bne-flyout-wrapper">

			<div class="flyout-overlay"></div>

			<?php while ( $bne_flyout->have_posts() ) : $bne_flyout->the_post();

				// Flyout ID
				$flyout_id = get_the_ID();

				// Pull in the Restricted Pages as a post object array
				$restricted_pages = get_field('bne_flyout_restrictions');

				// Restricted Check
				if( $restricted_pages ) {

					// Build Restricted Array
					$restricted_pages_array = array();

					// Loop through each post object and get the assigned ID
				    foreach( $restricted_pages as $restricted_page ) {
					    // Add the ID's to the restricted array
					    $restricted_pages_array[] = $restricted_page->ID;
				    }

				    // Cross check the current page ID with the Restricted Array
					if( !in_array( $page_id , $restricted_pages_array) ) {

						continue;
					}

				} // END Restricted Check


				// Flyout Location
				$flyout_location = get_field( 'bne_flyout_location' );
				if( 'Left Side' == $flyout_location ) {
					$flyout_location = 'left';
				} elseif( 'Right Side' == $flyout_location ) {
					$flyout_location = 'right';
				} elseif( 'Top Side' == $flyout_location ) {
					$flyout_location = 'top';
				} elseif( 'Bottom Side' == $flyout_location ) {
					$flyout_location = 'bottom';
				}

				// Flyout Displacement (true = push, false = slide)
				$displacement = get_field( 'bne_flyout_displacement' );
				if ( !$displacement || 'true' == $displacement ) {
					$displacement = 'true';
					$displacement_class = 'push';
				} else {
					$displacement_class = 'slide';
				}

				// Trigger Location
				$trigger_location = get_field( 'bne_flyout_trigger_location' );
				if( 'Right Side' == $trigger_location ) {
					$trigger_location_class = 'trigger-right';
				} elseif( 'Left Side' == $trigger_location ) {
					$trigger_location_class = 'trigger-left';
				} elseif( 'Top Side' == $trigger_location ) {
					$trigger_location_class = 'trigger-top';
				} elseif( 'Bottom Side' == $trigger_location ) {
					$trigger_location_class = 'trigger-bottom';
				}

				// Trigger Type
				$trigger_type = get_field( 'bne_flyout_trigger_type' );
				if( 'Hide' != $trigger_type ) {

					// Trigger Button
					$trigger_bg_color = esc_html( get_field( 'bne_flyout_trigger_background_color' ) );
					$trigger_position = sanitize_text_field( get_field( 'bne_flyout_trigger_position_top' ) );
					$trigger_button_label = wp_kses(
						get_field( 'bne_flyout_trigger_button_label' ),
						// Allow certain HTML tags for the label
						array(
							'span' 	=> 	array( 'style' => array(), 'class' => array() ),
							'i' 	=> 	array( 'class' => array() ),
							'br' 	=> 	array(),
							'em'	=> 	array()
						)
					);

					// Trigger Image
					$trigger_image = get_field( 'bne_flyout_trigger_image' );

					// Open Trigger Styles
					$trigger_styles = '';

					// Trigger Styles - Button Color
					if ( $trigger_bg_color && 'Button' == $trigger_type ) {
						$trigger_styles .= 'background-color: '.$trigger_bg_color.'; ';
					}

					// Trigger Styles - Position

					// @legacy prior to v1.2.7 - If the position is "only" a number
					// and does not include %, px, em, etc... add the "%" after it.
					// This field is now a text field and not a numeric field to allow unit choices.
					if( is_numeric( $trigger_position ) ) {
						$trigger_position = $trigger_position.'%';
					}

					// Determine the css placement based on location.
					if( 'Left Side' == $trigger_location ||  'Right Side' == $trigger_location ) {
						$trigger_styles .= 'top: '.$trigger_position.'; ';
					} else {
						$trigger_styles .= 'left: '.$trigger_position.'; ';
					}


					// Trigger Visiblity
					$trigger_visiblities_check = get_field( 'bne_flyout_trigger_visibility' );
					if( $trigger_visiblities_check ) {
						$trigger_visiblities = get_field_object( 'bne_flyout_trigger_visibility' );
						$trigger_visiblity_class = implode( ' ', $trigger_visiblities['value'] );
					} else {
						$trigger_visiblity_class = '';
					}

					// Trigger Final Classes
					$trigger_classes = 'flyout-trigger-id-'.$flyout_id.' flyout-trigger '.$trigger_location_class.' '.$trigger_visiblity_class;

				}
				?>

				<div id="flyout-container-id-<?php echo $flyout_id; ?>">

					<?php if( 'Button' == $trigger_type ) { ?>
						<!-- Flyout Button Trigger -->
						<div class="<?php echo $trigger_classes; ?> trigger-button" style="<?php echo $trigger_styles; ?>"><?php echo $trigger_button_label; ?></div>
					<?php } ?>

					<?php if( 'Image' == $trigger_type ) { ?>
						<!-- Flyout Image Trigger -->
						<div class="<?php echo $trigger_classes; ?> trigger-image" style="<?php echo $trigger_styles; ?>"><img src="<?php echo esc_url( $trigger_image['url'] ); ?>" alt="<?php echo esc_html( $trigger_image['alt'] ); ?>"/></div>
					<?php } ?>


					<!-- Flyout JS -->
					<script type="text/javascript">
						jQuery(document).ready(function($) {

							// Call Flyout
						    $('.flyout-trigger-id-<?php echo $flyout_id; ?>').sidr({	// Trigger classname
								name: 'flyout-content-id-<?php echo $flyout_id; ?>',	// Matches Flyout ID
								side: '<?php echo $flyout_location; ?>',				// Position
								displace: <?php echo $displacement; ?>,					// true = push, false = slide

								// Flyout Open Event
								onOpen: function(name) {

									// Add overlay on body
									$('.flyout-overlay').fadeIn(300).toggleClass('active');

									// Toggle flyout open class
									$('#flyout-content-id-<?php echo $flyout_id; ?>').toggleClass('flyout-open');

									// Close Flyout by clicking close button or clicking on overlay
								    $('.flyout-overlay, #flyout-close-id-<?php echo $flyout_id; ?>').click(function(){
										$.sidr('close', 'flyout-content-id-<?php echo $flyout_id; ?>');	// Matches Trigger ID
									});
								},

								// Flyout Closing Event
								onClose: function(name) {
									// Close Shade
									$('.flyout-overlay').fadeOut(300).toggleClass('active');

									// Toggle flyout open class
									$('#flyout-content-id-<?php echo $flyout_id; ?>').toggleClass('flyout-open');
								}
						    });
						});
					</script>

					<!-- Build Flyout Content -->
					<div id="flyout-content-id-<?php echo $flyout_id; ?>" class="sidr">
						<div class="flyout-content <?php echo $displacement_class; ?>">

							<!-- Flyout Header Buttons -->
							<div class="flyout-header-buttons">
								<a id="flyout-close-id-<?php echo $flyout_id; ?>" class="flyout-close-button"><i class="bne-icon-cancel"></i></a>
								<?php if( current_user_can( 'edit_pages' ) ) { ?>
									<a class="flyout-edit-button" href="<?php echo get_edit_post_link( $flyout_id ); ?>" target="_blank"><i class="bne-icon-pencil"></i></a>
								<?php } ?>
							</div>

							<?php
								
								// Dev - Allow Adding custom content before Flyout Content
								do_action( 'bne_flyout_content_before' );
								
								// Setup the Content Order
								// @since v1.2.8
								$flyout_content_order = get_field( 'bne_flyout_content_order', $flyout_id );
							
							
								// Content then Menu (default)
								if( !$flyout_content_order || $flyout_content_order == 1 ) {
																
									if( get_field( 'bne_flyout_content', $flyout_id ) ) {
									
										echo '<div class="flyout-content-body">';
											echo get_field( 'bne_flyout_content', $flyout_id );
										echo '</div>';
									}
	
									if( get_field( 'bne_flyout_custom_menu', $flyout_id ) ) {
										echo '<div class="flyout-menu">';
											echo get_field( 'bne_flyout_custom_menu', $flyout_id );
										echo '</div>';
									}


								// Menu Then Content
								} else {
																
									if( get_field( 'bne_flyout_custom_menu', $flyout_id ) ) {
										echo '<div class="flyout-menu">';
											echo get_field( 'bne_flyout_custom_menu', $flyout_id );
										echo '</div>';
									}

									if( get_field( 'bne_flyout_content', $flyout_id ) ) {
									
										echo '<div class="flyout-content-body">';
											echo get_field( 'bne_flyout_content', $flyout_id );
										echo '</div>';
									}
	

								} // End Content Order


								// Dev - Allow Adding custom content AFTER Flyout Content
								do_action( 'bne_flyout_content_after' );
								
							?>

							<div class="clear"></div>
						</div><!-- .flyout-content (end) -->
					</div><!-- #flyout-content-ID.sidr (end) -->
				</div><!-- .flyout-container-ID (end) -->

			<?php endwhile; ?>

		</div><!-- .bne-flyout-wrapper (end) -->

	<?php } // END QUERY

	// Reset Post Query
	wp_reset_postdata();

}
add_action( 'wp_footer', 'bne_flyout_footer_output' );