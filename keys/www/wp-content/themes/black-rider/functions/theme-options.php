<?php

add_action('init', 'inkthemes_options');
if (!function_exists('inkthemes_options')) {

    function inkthemes_options() {
        // VARIABLES
        $themename = 'Black Rider Theme';
        $shortname = "of";
        // Populate OptionsFramework option in array for use in theme
        global $of_options;
        $of_options = blcr_get_option('of_options');
        //Front page on/off
        $file_rename = array("on" => "On", "off" => "Off");
        $home_page_blog_content = array("on" => "On", "off" => "Off");
        // Background Defaults
        $background_defaults = array('color' => '', 'image' => '', 'repeat' => 'repeat', 'position' => 'top center', 'attachment' => 'scroll');
        //Stylesheet Reader
        $alt_stylesheets = array("orange" => "orange", "green" => "green", "teal-green" => "teal-green", "yellow" => "yellow", "red" => "red", "black" => "black", "pink" => "pink", "blue" => "blue");
        $lan_stylesheets = array("Default" => "Default", "rtl" => "rtl");
        // Pull all the categories into an array
        $options_categories = array();
        $options_categories_obj = get_categories();
        foreach ($options_categories_obj as $category) {
            $options_categories[$category->cat_ID] = $category->cat_name;
        }
        // Populate OptionsFramework option in array for use in theme
        $contact_option = array("on" => "On", "off" => "Off");
        $captcha_option = array("on" => "On", "off" => "Off");
        // Pull all the pages into an array
        $options_pages = array();
        $options_pages_obj = get_pages('sort_column=post_parent,menu_order');
        $options_pages[''] = 'Select a page:';
        foreach ($options_pages_obj as $page) {
            $options_pages[$page->ID] = $page->post_title;
        }
        // If using image radio buttons, define a directory path
        $imagepath = get_template_directory_uri() . '/images/';

        $options = array(
            array("name" => "General Settings",
                "type" => "heading"),
            array("name" => "Custom Logo",
                "desc" => "Upload a logo for your Website. The recommended size for the logo is 200px width x 50px height.",
                "id" => "inkthemes_logo",
                "type" => "upload"),
            array("name" => "Custom Favicon",
                "desc" => "Here you can upload a Favicon for your Website. Specified size is 16px x 16px.",
                "id" => "inkthemes_favicon",
                "type" => "upload"),
            array("name" => "Custom Text Color",
                "desc" => "Choose your theme text color.",
                "id" => "inkthemes_text_color",
                "type" => "color"),
            array("name" => "Mobile Navigation Menu",
                "desc" => "Enter your mobile navigation menu text",
                "id" => "inkthemes_nav",
                "std" => "",
                "type" => "textarea"),
            array("name" => "Top Right Contact Details",
                "desc" => "Mention the contact details here which will be displayed on the top right corner of Website.",
                "id" => "inkthemes_topright",
                "std" => "",
                "type" => "textarea"),
            array("name" => "Contact Number For Tap To Call Feature",
                "desc" => "Mention your contact number here through which users can interact to you directly. This feature is called tap to call and this will work when the user will access your website through mobile phones or iPhone.",
                "id" => "inkthemes_contact_number",
                "std" => "",
                "type" => "text"),                 
            //Homepage Feature Area
            array("name" => "Homepage Feature Area",
                "type" => "heading"),
            array("name" => "Home Page Main Heading Comes Here",
                "desc" => "Mention the punch line for your business here.",
                "id" => "inkthemes_page_main_heading",
                "std" => "",
                "type" => "textarea"),
            array("name" => "Home Page Sub Heading",
                "desc" => "Mention the tagline for your business here that will complement the punch line.",
                "id" => "inkthemes_page_sub_heading",
                "std" => "",
                "type" => "textarea"),
            array("name" => "Home Page Feature Section Starts From Here.",
                "type" => "saperate",
                "class" => "saperator"),
            array("name" => "First Feature Image",
                "desc" => "Choose image for your first Feature area. Optimal size 170px x 170px",
                "id" => "inkthemes_fimg1",
                "std" => "",
                "type" => "upload"),
            array("name" => "First Feature Heading",
                "desc" => "Enter your text for first col heading.",
                "id" => "inkthemes_firsthead",
                "std" => "",
                "type" => "textarea"),
            array("name" => "First Feature Description",
                "desc" => "Enter your text for first col description.",
                "id" => "inkthemes_firstdesc",
                "std" => "",
                "type" => "textarea"),
            array("name" => "First feature Link",
                "desc" => "Enter your text for First feature Link.",
                "id" => "inkthemes_feature_link1",
                "std" => "",
                "type" => "text"),
            array("name" => "Second Feature Starts From Here.",
                "type" => "saperate",
                "class" => "saperator"),
            //Second Feature Separetor
            array("name" => "Second Feature Image",
                "desc" => "Choose image for your second Feature area. Optimal size 170px x 170px",
                "id" => "inkthemes_fimg2",
                "std" => "",
                "type" => "upload"),
            array("name" => "Second Feature Heading",
                "desc" => "Enter your heading for second Feature area",
                "id" => "inkthemes_headline2",
                "std" => "",
                "type" => "textarea"),
            array("name" => "Second Col Description",
                "desc" => "Enter your text for second col description.",
                "id" => "inkthemes_seconddesc",
                "std" => "",
                "type" => "textarea"),
            array("name" => "Second feature Link",
                "desc" => "Enter your text for Second feature Link.",
                "id" => "inkthemes_feature_link2",
                "std" => "",
                "type" => "text"),
            array("name" => "Third Feature Starts From Here.",
                "type" => "saperate",
                "class" => "saperator"),
            //Third Feature Separetor
            array("name" => "Third Feature Image",
                "desc" => "Choose image for your thrid Feature. Optimal size 170px x 170px",
                "id" => "inkthemes_fimg3",
                "std" => "",
                "type" => "upload"),
            array("name" => "Third Feature Heading",
                "desc" => "Enter your heading for third Feature area",
                "id" => "inkthemes_headline3",
                "std" => "",
                "type" => "textarea"),
            array("name" => "Third Feature Description",
                "desc" => "Enter your text for Third Feature description.",
                "id" => "inkthemes_thirddesc",
                "std" => "",
                "type" => "textarea"),
            array("name" => "Third feature Link",
                "desc" => "Enter your text for Second feature Link.",
                "id" => "inkthemes_feature_link3",
                "std" => "",
                "type" => "text"),
            array("name" => "Fourth Feature Starts From Here.",
                "type" => "saperate",
                "class" => "saperator"),
            //Fourth Feature Separetor
            array("name" => "Fourth Feature Image",
                "desc" => "Choose image for your Fourth Feature. Optimal size 170px x 170px",
                "id" => "inkthemes_fimg4",
                "std" => "",
                "type" => "upload"),
            array("name" => "Fourth Feature Heading",
                "desc" => "Enter your heading for Fourth Feature area",
                "id" => "inkthemes_headline4",
                "std" => "",
                "type" => "textarea"),
            array("name" => "Fourth Feature Description",
                "desc" => "Enter your text for Fourth Feature description.",
                "id" => "inkthemes_fourthdesc",
                "std" => "",
                "type" => "textarea"),
            array("name" => "Fourth feature Link",
                "desc" => "Enter your text for Fourth feature Link.",
                "id" => "inkthemes_feature_link4",
                "std" => "",
                "type" => "text"),
            array("name" => "Home Page Testimonial Section Starts From Here",
                "type" => "saperate",
                "class" => "saperator"),
            array("name" => "Home Page Blog Heading",
                "desc" => "Enter your text for home Page blog heading section",
                "id" => "inkthemes_blog_heading",
                "std" => "",
                "type" => "text"),          
//****=============================================================================****//
//****-----------This code is used for creating color styleshteet options----------****//							
//****=============================================================================****//				
            array("name" => "Styling Options",
                "type" => "heading"),           
            array("name" => "Custom CSS",
                "desc" => "Quickly add your custom CSS code to your theme by writing the code in this block.",
                "id" => "inkthemes_customcss",
                "std" => "",
                "type" => "textarea"),
//****=============================================================================****//
//****-------------This code is used for creating Bottom Footer Setting options-------------****//					
//****=============================================================================****//			
            array("name" => "Footer Settings",
                "type" => "heading"),
            array("name" => "Footer Text",
                "desc" => "Write the text here that will be displayed on the footer i.e. at the bottom of the Website.",
                "id" => "inkthemes_footertext",
                "std" => "",
                "type" => "textarea"));

        blcr_update_option('of_template', $options);
        blcr_update_option('of_themename', $themename);
        blcr_update_option('of_shortname', $shortname);
    }

}
?>
