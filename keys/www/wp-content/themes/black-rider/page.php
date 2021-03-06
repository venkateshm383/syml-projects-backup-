<?php
/**
 * The template for displaying all pages.
 *
 * This is the template that displays all pages by default.
 * Please note that this is the wordpress construct of pages
 * and that other 'pages' on your wordpress site will use a
 * different template.
 *
 */
?>
<?php get_header(); ?>  
<div class="page_heading_container">
    <div class="container_24">
        <div class="grid_24">
            <div class="page_heading_content">
                <span class="bred-tip"></span>
                <?php blcr_breadcrumbs(); ?>
            </div>
        </div>
        <div class="clear"></div>
    </div>
</div>
<div class="page-content-container">
    <div class="container_24">
        <div class="grid_24">
            <div class="page-content">
                <div class="grid_16 alpha">
                    <div class="content-bar">  	
                        <h1 class="page-title"><?php the_title(); ?></h1>
                        <?php if (have_posts()) : the_post(); ?>
                            <?php the_content(); ?>	
                        <?php endif; ?>
                        <div class="clear"></div>
                        <!--Start Comment box-->
                        <?php comments_template(); ?>
                        <!--End Comment box-->
                    </div>
                </div>
            </div>
        </div>
        <div class="clear"></div>
    </div>
</div>
<?php get_footer(); ?>
