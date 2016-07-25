=== BNE Flyout ===
Author URI: http://www.bnecreative.com
Contributors: bluenotes
Tags: sidebar, flyout, off-canvas, hidden sidebars, sidebar content, hidden content
Requires at least: 3.8
Tested up to: 4.3
Stable tag: 1.2.9
License: GPLv2 or later
License URI: http://www.gnu.org/licenses/gpl-2.0.html


Adds unlimited off-canvas content to your WordPress website triggered with floating buttons, images, or inline links.


== Description ==

Adds hidden sidebars (left, right, top, and bottom) to all pages of your website that are triggered (shown) from a clicked action. These sidebars will "flyout" from the edge of the browser window pushing your content out of the way or sliding on top. Each Flyout includes an optional button or image trigger that is floated on the left/right/top/bottom side of the browser window. In addition, any HTML element on your page can trigger the Flyout including links, images, buttons, or menu items, using the Flyout's classname.

== Installation ==

1. Upload "bne-flyout" folder to the "/wp-content/plugins/" directory
2. Activate the plugin through the "Plugins" menu in WordPress
3. A new menu item will be added called "Flyouts."


== Frequently Asked Questions ==




== Changelog ==

= 1.2.9 ( Aug 19, 2015 ) = 
* Fix: WordPress v4.3 support and compatibility.




= 1.2.8 ( July 21, 2015 ) = 
* Fix: Image Triggers will now include alt meta information.
* Fix: In certain cases, inline CSS styles were being removed from the HTML tag when a flyout closes.
* New: If using a custom menu and content within a Flyout, you can now specify the order they appear.



= 1.2.7.1 ( April 23, 2015 ) =
* Security - Add additional sanitization checks on output. No threats were reported. This is just a precaution due to recent events with other plugins/themes.
* Update ACF to 5.2.3




= 1.2.7 ( April 19, 2015 ) =
* Fix/Enhancement: Removed forced "display:none" on the flyout and visibility check within sidr.js and css. This allows iframes and Google Maps to display properly after the Flyout opens. This is still experimental.
* New: Allow trigger positions to use em, px, or % units. If no unit type is specified it will default to %. This field is also a textfield and no longer numeric. Max characters allowed is 5, Therefore, you can now specify: 40px, 300px, 2%, 4em, etc.



= 1.2.6 ( March 21, 2015 ) =
* Fix: Flyouts not opening on Windows 8 devices that are touch enabled which can also use a mouse.



= 1.2.5 ( March 12, 2015 ) =
* Fix: Match trigger image output max-width to 200px as it is defined in the trigger settings tab.
* Fix: Compatibility fix with ACF v5.2.1
* Tweak: Changed Step value for trigger position from 10 to 1 in the trigger settings tab. Some users reported not being able to save with non-10 step values.
* Note: Update branding from Bluenotes Entertainment to BNE Creative ( Why? http://www.bnecreative.com/blog/introducing-bne-creative/ )



= 1.2.4 ( January 25, 2015 ) =
* New: Add option to restrict which pages the Flyout will be displayed on. This option is found under the Flyout Settings tab for each one.
* New: Now Localization Ready!
* Fix: CSS Bug with Chrome on Windows where the trigger buttons would jump around randomly while scrolling.



= 1.2.3 ( December 13, 2014 ) =
* Replaced auto-update script from AutoHosted to WP-Updates



= 1.2.2 ( December 9, 2014 ) =
* Fix: Adjust CSS output to account the size of the flyout while off screen. This should fix the slight delay between moving the page body and the flyout appearing for some users.
* Update to ACF 5.1.4



= 1.2.1 ( November 27, 2014 ) =
* Fix: Adjust the logic for browser scrolling when a Flyout is active and not. This also helps correct the issue with certain themes that use animations on elements.
* New: When a Flyout is opening, Triggers will fade out.



= 1.2 ( November 21, 2014 ) =
* Fix: Removed duplicated CSS class ID for trigger images.
* New: Add overlay to body when Flyout is open giving a shading effect.
* New: Allow Flyouts to be closed by clicking anywhere on the body, hence the added overlay.
* New: Added new locations for Flyouts and Triggers. Now they can be positioned on all four sides! Top, Right, Bottom, and Left.
* New: Added option to include a custom menu within the Flyout. This menu will be displayed below the Flyout content if also added.
* New: Added two action hooks for developers: 1) bne_flyout_content_before() - Adds custom content to the top of the flyout. 2) bne_flyout_content_after() - Adds custom content to the bottom of the flyout.
* Tweak: Moved the Flyout admin menu icon into register_post_type() -- no need to further add additional CSS to the admin head.
* Tweak: Adjusted the Flyout width setting. Reworded to also apply to height for top and bottom locations.
* Tweak: Close and Edit button css.
* Tweak: Cleaned and further optimized CSS.
* Tweak: Cleaned and further optimized JS.
* Tweak: Defined the break points for the trigger visibility options on the edit screen.
* Updated to ACF5.



= 1.1.2 ( August 18, 2014 ) =
* Fix: If using the slide displacement option, the utility classes: "sidr-open" and "flyout-content-id-{...}-open" were not being added to the body.



= 1.1.1 ( August 7, 2014 ) =
* Removed a conflict if another plugin is also using the font family "fontello".
* Added compatibility for the BNE WordPress Theme Framework v1.0
* Moved loading ACF to be called with the "after_setup_theme" hook. This allows the plugin to be activated if a theme already has ACF internally included such as the new ACFPro (v5).



= 1.1 (June 13, 2014) =
* Added new option under "Flyout Settings" to either have the Flyout "Slide" above the page content or "Push" the page content as normal.



= 1.0.1 (June 5, 2014) =
* Enhancement: Changed body position to fixed from absolute within jquery.sidr.min.js while flyout is active to prevent scrolling on the pushed body.
* FIX: Added "width:100%" to default trigger images for IE8.



= 1.0 (May 24, 2014) =
* First public relase