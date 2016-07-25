<?php
function blcr_setup() {
    /* ----------------------------------------------------------------------------------- */
    /* Theme Support
      /*----------------------------------------------------------------------------------- */
    add_theme_support('post-thumbnails');
    add_image_size('post-thumbnails', 216, 168, true);
    add_theme_support( 'title-tag' );
    //custom background support
    add_theme_support('custom-background', array(
        // Background color default
        'default-color' => 'fff',
            // Background image default
            //'default-image' => get_template_directory_uri() . '/images/body-bg.png'
    ));
    $args = array(
        'width' => 1920,
        'height' => 654,
        'flex-height' => true,
        'flex-width' => true,
        'header-text' => false,
        'default-image' => get_template_directory_uri() . '/images/slider-1.jpg',
    );
    //custom header support
    add_theme_support('custom-header', $args);
    /* ----------------------------------------------------------------------------------- */
    /* Auto Feed Links Support
      /*----------------------------------------------------------------------------------- */
    add_theme_support('automatic-feed-links');
    //Load languages file
    load_theme_textdomain('black-rider', get_template_directory() . '/languages');
	// This theme styles the visual editor with editor-style.css to match the theme style.
    add_editor_style();
	// activate support for thumbnails
    // added in 2.9
    add_theme_support('menus');
    set_post_thumbnail_size(216, 168, false);
}
add_action('after_setup_theme', 'blcr_setup');
/* ----------------------------------------------------------------------------------- */
/* Custom Menus Function
  /*----------------------------------------------------------------------------------- */
// Add CLASS attributes to the first <ul> occurence in wp_page_menu
function blcr_add_menuclass($ulclass) {
    return preg_replace('/<ul>/', '<ul class="ddsmoothmenu">', $ulclass, 1);
}
add_filter('wp_page_menu', 'blcr_add_menuclass');
add_action('after_setup_theme', 'blcr_register_custom_menu');
function blcr_register_custom_menu() {
    register_nav_menu('custom_menu', __('Main Menu', 'black-rider'));
}
function blcr_nav() {
    if (function_exists('wp_nav_menu'))
        wp_nav_menu(array('theme_location' => 'custom_menu', 'container_id' => 'menu', 'menu_class' => 'ddsmoothmenu', 'fallback_cb' => 'blcr_nav_fallback'));
    else
        blcr_nav_fallback();
}
function blcr_nav_fallback() {
    ?>
    <div id="menu">
        <ul class="ddsmoothmenu">
            <?php
            wp_list_pages('title_li=&show_home=1&sort_column=menu_order');
            ?>
        </ul>
    </div>
    <?php
}
function blcr_nav_menu_items($items) {
    if (is_home()) {
        $homelink = '<li class="current_page_item">' . '<a href="' . home_url('/') . '">' . __('Home', 'black-rider') . '</a></li>';
    } else {
        $homelink = '<li>' . '<a href="' . home_url('/') . '">' . __('Home', 'black-rider') . '</a></li>';
    }
    $items = $homelink . $items;
    return $items;
}
add_filter('wp_list_pages', 'blcr_nav_menu_items');
/* ----------------------------------------------------------------------------------- */
/* Breadcrumbs Plugin
  /*----------------------------------------------------------------------------------- */
function blcr_breadcrumbs() {
    $delimiter = '&raquo;';
    $home = 'Home'; // text for the 'Home' link
    $before = '<span class="current">'; // tag before the current crumb
    $after = '</span>'; // tag after the current crumb
    echo '<div id="crumbs">';
    global $post;
    $homeLink = home_url();
    echo '<a href="' . $homeLink . '">' . $home . '</a> ' . $delimiter . ' ';
    if (is_category()) {
        global $wp_query;
        $cat_obj = $wp_query->get_queried_object();
        $thisCat = $cat_obj->term_id;
        $thisCat = get_category($thisCat);
        $parentCat = get_category($thisCat->parent);
        if ($thisCat->parent != 0)
            echo(get_category_parents($parentCat, TRUE, ' ' . $delimiter . ' '));
        echo $before . 'Archive by category "' . single_cat_title('', false) . '"' . $after;
    }
    elseif (is_day()) {
        echo '<a href="' . get_year_link(get_the_time('Y')) . '">' . get_the_time('Y') . '</a> ' . $delimiter . ' ';
        echo '<a href="' . get_month_link(get_the_time('Y'), get_the_time('m')) . '">' . get_the_time('F') . '</a> ' . $delimiter . ' ';
        echo $before . get_the_time('d') . $after;
    } elseif (is_month()) {
        echo '<a href="' . get_year_link(get_the_time('Y')) . '">' . get_the_time('Y') . '</a> ' . $delimiter . ' ';
        echo $before . get_the_time('F') . $after;
    } elseif (is_year()) {
        echo $before . get_the_time('Y') . $after;
    } elseif (is_single() && !is_attachment()) {
        if (get_post_type() != 'post') {
            $post_type = get_post_type_object(get_post_type());
            $slug = $post_type->rewrite;
            echo '<a href="' . $homeLink . '/' . $slug['slug'] . '/">' . $post_type->labels->singular_name . '</a> ' . $delimiter . ' ';
            echo $before . get_the_title() . $after;
        } else {
            $cat = get_the_category();
            $cat = $cat[0];
            echo get_category_parents($cat, TRUE, ' ' . $delimiter . ' ');
            echo $before . get_the_title() . $after;
        }
    } elseif (!is_single() && !is_page() && get_post_type() != 'post') {
        $post_type = get_post_type_object(get_post_type());
        echo $before . $post_type->labels->singular_name . $after;
    } elseif (is_attachment()) {
        $parent = get_post($post->post_parent);
        $cat = get_the_category($parent->ID);
        $cat = $cat[0];
        echo get_category_parents($cat, TRUE, ' ' . $delimiter . ' ');
        echo '<a href="' . get_permalink($parent) . '">' . $parent->post_title . '</a> ' . $delimiter . ' ';
        echo $before . get_the_title() . $after;
    } elseif (is_page() && !$post->post_parent) {
        echo $before . get_the_title() . $after;
    } elseif (is_page() && $post->post_parent) {
        $parent_id = $post->post_parent;
        $breadcrumbs = array();
        while ($parent_id) {
            $page = get_page($parent_id);
            $breadcrumbs[] = '<a href="' . get_permalink($page->ID) . '">' . get_the_title($page->ID) . '</a>';
            $parent_id = $page->post_parent;
        }
        $breadcrumbs = array_reverse($breadcrumbs);
        foreach ($breadcrumbs as $crumb)
            echo $crumb . ' ' . $delimiter . ' ';
        echo $before . get_the_title() . $after;
    } elseif (is_search()) {
        echo $before . 'Search results for "' . get_search_query() . '"' . $after;
    } elseif (is_tag()) {
        echo $before . 'Posts tagged "' . single_tag_title('', false) . '"' . $after;
    } elseif (is_author()) {
        global $author;
        $userdata = get_userdata($author);
        echo $before . 'Articles posted by ' . $userdata->display_name . $after;
    } elseif (is_404()) {
        echo $before . 'Error 404' . $after;
    }
    if (get_query_var('paged')) {
        if (is_category() || is_day() || is_month() || is_year() || is_search() || is_tag() || is_author())
            echo ' (';
        echo __('Page', 'black-rider') . ' ' . get_query_var('paged');
        if (is_category() || is_day() || is_month() || is_year() || is_search() || is_tag() || is_author())
            echo ')';
    }
    echo '</div>';
}
/**
 * This function gets image width and height and
 * Prints attached images from the post        
 */
function blcr_get_image($width, $height) {
    $w = $width;
    $h = $height;
    global $post, $posts;
//This is required to set to Null
    $img_source = '';
    $id = '';
    $permalink = get_permalink();
    ob_start();
    ob_end_clean();
    $output = preg_match_all('/<img.+src=[\'"]([^\'"]+)[\'"].*>/i', $post->post_content, $matches);
    if (isset($matches [1] [0])) {
        $img_source = $matches [1] [0];
    }
    if ($img_source) {
        print "<div class='post-image'><a href='$permalink'><span></span></a><img height='$h' width='$w' src='$img_source' class='postimg' alt='Post Image'/></div>";
    }
}
/* ----------------------------------------------------------------------------------- */
/* Attachment Page Design
  /*----------------------------------------------------------------------------------- */
//For Attachment Page
/**
 * Prints HTML with meta information for the current post (category, tags and permalink).
 *
 */
function blcr_posted_in() {
// Retrieves tag list of current post, separated by commas.
    $tag_list = get_the_tag_list('', ', ');
    if ($tag_list) {
        $posted_in = POSTED_ENTRY;
    } elseif (is_object_in_taxonomy(get_post_type(), 'category')) {
        $posted_in = POSTED_BOOKMARK;
    } else {
        $posted_in = BOOKMARK;
    }
// Prints the string, replacing the placeholders.
    printf(
            $posted_in, get_the_category_list(', '), $tag_list, get_permalink(), the_title_attribute('echo=0')
    );
}
/**
 * Set the content width based on the theme's design and stylesheet.
 *
 * Used to set the width of images and content. Should be equal to the width the theme
 * is designed for, generally via the style.css stylesheet.
 */
if (!isset($content_width))
    $content_width = 590;
/**
 * Register widgetized areas, including two sidebars and four widget-ready columns in the footer.
 *
 * To override twentyten_widgets_init() in a child theme, remove the action hook and add your own
 * function tied to the init hook.
 *
 * @uses register_sidebar
 */
function blcr_widgets_init() {
// Area 1, located at the top of the sidebar.
    register_sidebar(array(
        'name' => PRIMARY_WIDGET,
        'id' => 'primary-widget-area',
        'description' => THE_PRIMARY_WIDGET,
        'before_widget' => '',
        'after_widget' => '',
        'before_title' => '<h3>',
        'after_title' => '</h3>',
    ));
// Area 2, located below the Primary Widget Area in the sidebar. Empty by default.
    register_sidebar(array(
        'name' => SECONDRY_WIDGET,
        'id' => 'secondary-widget-area',
        'description' => THE_SECONDRY_WIDGET,
        'before_widget' => '',
        'after_widget' => '',
        'before_title' => '<h3>',
        'after_title' => '</h3>',
    ));
// Area 2, located below the Primary Widget Area in the sidebar. Empty by default.
    register_sidebar(array(
        'name' => RIGHT_WIDGET,
        'id' => 'home-page-widget-area',
        'description' => THE_RIGHT_WIDGET,
        'before_widget' => '',
        'after_widget' => '',
        'before_title' => '<h3>',
        'after_title' => '</h3>',
    ));
    // Area 3, located in the footer. Empty by default.
    register_sidebar(array(
        'name' => FIRST_FOOTER_WIDGET,
        'id' => 'first-footer-widget-area',
        'description' => THE_FIRST_FOOTER_WIDGET,
        'before_widget' => '',
        'after_widget' => '',
        'before_title' => '<h3>',
        'after_title' => '</h3>',
    ));
    // Area 4, located in the footer. Empty by default.
    register_sidebar(array(
        'name' => SECONDRY_FOOTER_WIDGET,
        'id' => 'second-footer-widget-area',
        'description' => THE_SECONDRY_FOOTER_WIDGET,
        'before_widget' => '',
        'after_widget' => '',
        'before_title' => '<h3>',
        'after_title' => '</h3>',
    ));
    // Area 5, located in the footer. Empty by default.
    register_sidebar(array(
        'name' => THIRD_FOOTER_WIDGET,
        'id' => 'third-footer-widget-area',
        'description' => THE_THIRD_FOOTER_WIDGET,
        'before_widget' => '',
        'after_widget' => '',
        'before_title' => '<h3>',
        'after_title' => '</h3>',
    ));
    // Area 6, located in the footer. Empty by default.
    register_sidebar(array(
        'name' => FOURTH_FOOTER_WIDGET,
        'id' => 'fourth-footer-widget-area',
        'description' => THE_FOURTH_FOOTER_WIDGET,
        'before_widget' => '',
        'after_widget' => '',
        'before_title' => '<h3>',
        'after_title' => '</h3>',
    ));
}
/** Register sidebars by running inkthemes_widgets_init() on the widgets_init hook. */
add_action('widgets_init', 'blcr_widgets_init');
/**
 * Pagination
 *
 */
function blcr_pagination($pages = '', $range = 2) {
    $showitems = ($range * 2) + 1;
    global $paged;
    if (empty($paged))
        $paged = 1;
    if ($pages == '') {
        global $wp_query;
        $pages = $wp_query->max_num_pages;
        if (!$pages) {
            $pages = 1;
        }
    }
    if (1 != $pages) {
        echo "<ul class='paging'>";
        if ($paged > 2 && $paged > $range + 1 && $showitems < $pages)
            echo "<li><a href='" . get_pagenum_link(1) . "'>&laquo;</a></li>";
        if ($paged > 1 && $showitems < $pages)
            echo "<li><a href='" . get_pagenum_link($paged - 1) . "'>&lsaquo;</a></li>";
        for ($i = 1; $i <= $pages; $i++) {
            if (1 != $pages && (!($i >= $paged + $range + 1 || $i <= $paged - $range - 1) || $pages <= $showitems )) {
                echo ($paged == $i) ? "<li><a href='" . get_pagenum_link($i) . "' class='current' >" . $i . "</a></li>" : "<li><a href='" . get_pagenum_link($i) . "' class='inactive' >" . $i . "</a></li>";
            }
        }
        if ($paged < $pages && $showitems < $pages)
            echo "<li><a href='" . get_pagenum_link($paged + 1) . "'>&rsaquo;</a></li>";
        if ($paged < $pages - 1 && $paged + $range - 1 < $pages && $showitems < $pages)
            echo "<li><a href='" . get_pagenum_link($pages) . "'>&raquo;</a></li>";
        echo "</ul>\n";
    }
}
/////////Theme Options
/* ----------------------------------------------------------------------------------- */
/* Add Favicon
  /*----------------------------------------------------------------------------------- */
function blcr_childtheme_favicon() {
    if (blcr_get_option('inkthemes_favicon') != '') {
        echo '<link rel="shortcut icon" href="' . blcr_get_option('inkthemes_favicon') . '"/>' . "\n";
    } 
}
add_action('wp_head', 'blcr_childtheme_favicon');
/* ----------------------------------------------------------------------------------- */
/* Custom CSS Styles */
/* ----------------------------------------------------------------------------------- */
function blcr_of_head_css() {
    $output = '';
    $custom_css = blcr_get_option('inkthemes_customcss');
    if ($custom_css <> '') {
        $output .= $custom_css . "\n";
    }
// Output styles
    if ($output <> '') {
        $output = "<!-- Custom Styling -->\n<style type=\"text/css\">\n" . $output . "</style>\n";
        echo $output;
    }
}
add_action('wp_head', 'blcr_of_head_css');
// activate support for thumbnails
function blcr_get_category_id($cat_name) {
    $term = get_term_by('name', $cat_name, 'category');
    return $term->term_id;
}
//Trm excerpt
function blcr_trim_excerpt($length) {
    global $post;
    $explicit_excerpt = $post->post_excerpt;
    if ('' == $explicit_excerpt) {
        $text = get_the_content('');
        $text = apply_filters('the_content', $text);
        $text = str_replace(']]>', ']]>', $text);
    } else {
        $text = apply_filters('the_content', $explicit_excerpt);
    }
    $text = strip_shortcodes($text); // optional
    $text = strip_tags($text);
    $excerpt_length = $length;
    $words = explode(' ', $text, $excerpt_length + 1);
    if (count($words) > $excerpt_length) {
        array_pop($words);
        array_push($words, '[&hellip;]');
        $text = implode(' ', $words);
        $text = apply_filters('the_excerpt', $text);
    }
    return $text;
}
function blcr_image_post($post_id) {
    add_post_meta($post_id, 'img_key', 'on');
}
//Trm post title
function blcr_titlesmall($before = '', $after = '', $echo = true, $length = false) {
    $title = get_the_title();
    if ($length && is_numeric($length)) {
        $title = substr($title, 0, $length);
    }
    if (strlen($title) > 0) {
        $title = apply_filters('inkthemes_titlesmall', $before . $title . $after, $before, $after);
        if ($echo)
            echo $title;
        else
            return $title;
    }
}
add_filter('wp_title', 'blcr_wp_title');
/**
 * Filters the page title appropriately depending on the current page
 *
 * This function is attached to the 'wp_title' fiilter hook.
 *
 * @uses	get_bloginfo()
 * @uses	is_home()
 * @uses	is_front_page()
 */
function blcr_wp_title($title) {
    global $page, $paged;
    if (is_feed())
        return $title;
    $site_description = get_bloginfo('description');
    $filtered_title = $title . get_bloginfo('name');
    $filtered_title .= (!empty($site_description) && ( is_home() || is_front_page() ) ) ? ' | ' . $site_description : '';
    $filtered_title .= ( 2 <= $paged || 2 <= $page ) ? ' | ' . sprintf(__('Page %s', 'black-rider'), max($paged, $page)) : '';
    return $filtered_title;
}
function blcr_custom_text_color() {
    $text_color = blcr_get_option('inkthemes_text_color');
    $style = '<style type="text/css">';
    $style .= 'body,h1, h2, h3, h4, h5, h6,.page_info_wrapper p,.feature_inner_box h6,.post .post_content p,.post .post_title a,.page_heading_container .page_heading_content,.contact-page p {color:' . $text_color . ';}';
    $style .="</style>";
    if ($text_color)
        return $style;
    else
        return;
}
function blcr_text_color() {
    $text_color = apply_filters('blcr_text_color', blcr_custom_text_color());
    echo $text_color;
}
add_action('wp_head', 'blcr_text_color');
//Remove galery default style
function blcr_remove_gallery_css( $css ) {
	return preg_replace( "#<style type='text/css'>(.*?)</style>#s", '', $css );
}
add_filter('gallery_style','blcr_remove_gallery_css');
?>