<?php
/**
 * The Header for our theme.
 *
 * Displays all of the <head> section and everything up till <div id="main">
 *
 */
?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html <?php language_attributes(); ?>>
    <head>
        <meta charset="<?php bloginfo('charset'); ?>" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
        <link rel="profile" href="http://gmpg.org/xfn/11" />
        <link rel="pingback" href="<?php bloginfo('pingback_url'); ?>" />
        <link rel="stylesheet" type="text/css" media="all" href="<?php bloginfo('stylesheet_url'); ?>" /> 
        <?php wp_head(); ?>
    </head>
    <body <?php body_class(); ?>>
        <div class="header_container">
            <div class="container_24">
                <div class="grid_24">
                    <div class="header">
                        <div class="grid_16 alpha">
                            <div class="logo">
                                <?php if (blcr_get_option('inkthemes_logo') != '') { ?>
                                <a href="<?php echo esc_url(home_url('/')); ?>"><img src="<?php if (blcr_get_option('inkthemes_logo') != '') {  echo blcr_get_option('inkthemes_logo'); } ?>" alt="<?php bloginfo('name'); ?>" /></a>
                                <?php } else { ?>
                                <hgroup>
                                    <h1 id="site-title"><span><a href="<?php echo esc_url(home_url('/')); ?>" title="<?php echo esc_attr(get_bloginfo('name', 'display')); ?>" rel="home"><?php bloginfo('name'); ?></a></span></h1>
                                    <h2 id="site-description"><?php bloginfo('description'); ?></h2>
                                </hgroup>
                                <?php } ?>
                            </div>
                        </div>
                        <div class="grid_8 omega">            
                            <div class="call-us">
                                <?php if (blcr_get_option('inkthemes_topright') != '') { ?>
                                    <p> <?php echo stripslashes(blcr_get_option('inkthemes_topright')); ?></p>
                                    <a class="btn" href="tel:<?php echo stripslashes(blcr_get_option('inkthemes_contact_number')); ?>">
                                    </a>
                                <?php } else {
                                    ?>
                                    <p><?php _e('Call US - 214 - 2547 - 142', 'black-rider'); ?></p>
                                    <a class="btn" href="tel:5551234567"></a>
                                <?php } ?>
                            </div>
                        </div>        
                    </div>
                </div>
            </div>
            <div class="clear"></div>
        </div>
        <div class="menu-container">
            <div class="container_24">
                <div class="grid_24">
                    <div class="grid_18">
                        <div class="menu-wrapper">
                            <div id="MainNav">                                  
                                <?php if (blcr_get_option('inkthemes_nav') != '') { ?><a href="#" class="mobile_nav closed"><?php echo stripslashes(blcr_get_option('inkthemes_nav')); ?><span></span></a>
                                <?php } else { ?> <a href="#" class="mobile_nav closed"><?php _e('Pages Navigation Menu', 'black-rider'); ?><span></span></a>
                                <?php } ?>
                                <?php blcr_nav(); ?> 
                            </div>  
                        </div>
                    </div>
                </div>
                <div class="clear"></div>
            </div>
        </div>