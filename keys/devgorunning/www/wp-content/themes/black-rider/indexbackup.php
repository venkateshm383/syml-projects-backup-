<?php
/**
 * The main template file.
 *
 * This is the most generic template file in a WordPress theme
 * and one of the two required files for a theme (the other being style.css).
 * It is used to display a page when nothing more specific matches a query. 
 * E.g., it puts together the home page when no home.php file exists.
 * Learn more: http://codex.wordpress.org/Template_Hierarchy
 *
 */
?>
<?php get_header(); ?>
<div class="slider-wrapper">
    <span class="top-shadow"></span>
    <div class="flexslider">
        <ul class="slides">
            <li>  
                <img src="<?php header_image(); ?>"  alt="" />                
            </li>           
        </ul>			  
    </div>
</div>         
<div class="clear"></div>
<div class="home_container">
    <div class="container_24">
        <div class="grid_24">
            <div class="home-content">
                <div class="page_info_wrapper">

                </div>
                <div class="grid_16 alpha">
                    <div class="blog_feature">
                        <?php                        
                        $limit = get_option('posts_per_page');
                        $paged = (get_query_var('paged')) ? get_query_var('paged') : 1;
                        $wp_query = new WP_Query();
                        $wp_query->query('showposts=' . $limit . '&paged=' . $paged);
                        ?>
                        <?php
                        $i = 0;
                        if ($wp_query->have_posts()) : while ($wp_query->have_posts()) : $wp_query->the_post();
                                $i++;
                            endwhile;
                        endif;
                        ?>	
                        <?php
                        $count = 0;
                        if ($wp_query->have_posts()) : while ($wp_query->have_posts()) : $wp_query->the_post();
                                $count++;
                                ?>
                                <!--Start post-->
                                <div class="post <?php
                                if ($count == $i) {
                                    echo 'last-post';
                                }
                                ?>">
                                         <?php if (has_post_thumbnail()) { ?>
                                        <div class='post-image'><a href="<?php the_permalink(); ?>">
                                                <?php the_post_thumbnail('post-thumbnails', array('class' => 'postimg')); ?>
                                            </a></div>
                                    <?php } else { ?>
                                        <?php blcr_get_image(216, 168); ?> 
                                        <?php
                                    }
                                    ?>
                                    <h1 class="post_title"><a href="<?php the_permalink() ?>" rel="bookmark" title="Permanent Link to <?php the_title_attribute(); ?>"><?php the_title(); ?></a></h1>
                                    <div class="post_content">                
                                        <ul class="post_meta">
                                            <li class="posted_by"><?php the_author_posts_link(); ?></li>
                                            <li class="post_date"><?php echo get_the_time('M-d-Y') ?></a></li>
                                            <li class="post_category"><?php the_category(', '); ?></li>
                                            <li class="postc_comment">&nbsp;<?php comments_popup_link('0 Comments.', '1 Comments.', '% Comments.'); ?></li>
                                        </ul>
                                        <?php echo blcr_trim_excerpt(40); ?>
                                        <a class="read-more" href="<?php the_permalink() ?>"><?php echo READ_MORE; ?></a>
                                    </div>              
                                </div>
                                <?php
                            endwhile;
                            ?>
                            <div class="clear"></div>
                            <nav id="nav-single"> 
                                <span class="nav-next">
                                    <?php previous_posts_link(NEWER_POSTS); ?>
                                </span>
                                <span class="nav-previous">
                                    <?php next_posts_link(OLDER_POSTS); ?>
                                </span>  </nav>
                            <?php
                        endif;
                        ?>
                        <?php wp_reset_postdata(); ?>
                    </div>
                </div>
                <div class="grid_8 omega">
                    <div class="sidebar">
                    </div>
                </div>
                <div class=" grid_8 omega">
                    <div class="sidebar home">
                        <?php if (is_active_sidebar('home-page-widget-area')) : ?>
                            <?php dynamic_sidebar('home-page-widget-area'); ?>                       
                        <?php endif; ?>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="clear"></div>
<?php get_footer(); ?>
