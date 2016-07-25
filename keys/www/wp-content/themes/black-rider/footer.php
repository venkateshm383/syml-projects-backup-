<div class="footer_container">
    <div class="footer_container_wrapper">
        <div class="container_24">
            <div class="grid_24">
                <div class="footer">
                    <?php
                    /* A sidebar in the footer? Yep. You can can customize
                     * your footer with four columns of widgets.
                     */
                    get_sidebar('footer');
                    ?>
                </div>
            </div>
            <div class="clear"></div>
        </div>
    </div>
</div>
<div class="bottom_footer_container">
    <div class="container_24">
        <div class="grid_24">
                <div class="copyrightinfo">
                    <?php if (blcr_get_option('inkthemes_footertext') != '') { ?>
                        <p class="copyright"><?php echo blcr_get_option('inkthemes_footertext'); ?></p> 
                    <?php } else { ?>
                    <?php } ?>
                </div>
            </div>
        </div>
    </div>
    <div class="clear"></div>
</div>
</div>
<?php wp_footer(); ?>
</body>
</html>
