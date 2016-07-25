<!-- Start the Loop. -->
<?php if (have_posts()) : while (have_posts()) : the_post(); ?>
        <!--Start post-->
        <div id="post-<?php the_ID(); ?>" <?php post_class(); ?>>
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
                <div class="clear"></div>
                <a class="read-more" href="<?php the_permalink() ?>"><?php echo READ_MORE; ?></a>
            </div>              
        </div>
        <?php
    endwhile;
else:
    ?>
    <div class="post">
        <p>
            <?php echo SORRY_NO_POST_MATCHED; ?>
        </p>
    </div>
<?php endif; ?>
<div class="clear"></div>
<!--End post-->