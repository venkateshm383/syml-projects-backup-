<?php
/**
 * The Template for displaying all single posts.
 *
 */
?>
<?php get_header(); ?>
<div class="page_heading_container">
    <div class="container_24">
        <div class="grid_24">
            <div class="page_heading_content">
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
                        <!-- Start the Loop. -->
                        <?php if (have_posts()) : while (have_posts()) : the_post(); ?>
                                <!--Start post-->
                                <div id="post-<?php the_ID(); ?>" <?php post_class(); ?>>
                                    <h1 class="post_title"><a href="<?php the_permalink() ?>" rel="bookmark" title="Permanent Link to <?php the_title_attribute(); ?>"><?php the_title(); ?></a></h1>
                                   
                                    <div class="post_content">                
                                        <ul class="post_meta">
                                            <li class="posted_by"><?php the_author_posts_link(); ?></li>
                                            <li class="post_date"><?php echo get_the_time('M, d, Y') ?></a></li>
                                            <li class="post_category"><?php the_category(', '); ?></li>
                                            <li class="postc_comment">&nbsp;<?php comments_popup_link('No Comments.', '1 Comment.', '% Comments.'); ?></li>
                                        </ul>
                                    </div>  
                                     <?php if (has_post_thumbnail()) { ?>                                      
                                            <?php the_post_thumbnail('post-thumbnails', array('class' => 'postimg')); ?>                                     
                                    <?php } ?>
                                    <?php the_content(); ?>                                              
                                    <?php if (has_tag()) { ?>
                                        <div class="tag">
                                            <?php the_tags(__('Post Tagged with', 'black-rider'), ','); ?>
                                        </div>
                                    <?php } ?>
                                    <?php wp_link_pages(array('before' => '<div class="page-link"><span>' . __('Pages:', 'black-rider') . '</span>', 'after' => '</div>')); ?>
                                </div>
                                <?php
                            endwhile;
                        else:
                            ?>
                            <div class="post">
                                <p>
                                    <?php _e('Sorry, no posts matched your criteria.', 'black-rider'); ?>
                                </p>
                            </div>
                        <?php endif; ?>
                        <!--End post-->
                        <div class="clear"></div>
                        <nav id="nav-single"> <span class="nav-previous">
                                <?php previous_post_link('%link', '<span class="meta-nav">' . PREV_POST . '</span>'); ?>
                            </span> <span class="nav-next">
                                <?php next_post_link('%link', '<span class="meta-nav">' . NEXT_POST . '</span>'); ?>
                            </span> </nav>
                        <!--Start Comment box-->
                        <?php comments_template(); ?>
                        <!--End Comment box-->
                    </div>
                </div>
                <div class="grid_8 omega">
                    <!--Start Sidebar-->
                    <?php get_sidebar(); ?>
                    <!--End Sidebar-->
                </div>
            </div>
        </div>
        <div class="clear"></div>
    </div>
</div>
<?php get_footer(); ?>