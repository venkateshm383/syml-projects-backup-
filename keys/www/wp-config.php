<?php
/**
 * The base configurations of the WordPress.
 *
 * This file has the following configurations: MySQL settings, Table Prefix,
 * Secret Keys, and ABSPATH. You can find more information by visiting
 * {@link https://codex.wordpress.org/Editing_wp-config.php Editing wp-config.php}
 * Codex page. You can get the MySQL settings from your web host.
 *
 * This file is used by the wp-config.php creation script during the
 * installation. You don't have to use the web site, you can just copy this file
 * to "wp-config.php" and fill in the values.
 *
 * @package WordPress
 */

// ** MySQL settings - You can get this info from your web host ** //
/** The name of the database for WordPress */
define('WP_SITEURL', 'https://go.visdom.ca');
define('WP_HOME', 'https://go.visdom.ca');
define('DB_NAME', 'wordpress');

/** MySQL database username */
define('DB_USER', 'syml');

/** MySQL database password */
define('DB_PASSWORD', 'wordpresssyml');

/** MySQL hostname */
define('DB_HOST', 'wordpress-mysql.cxyc85z2tb8q.us-west-2.rds.amazonaws.com');

/** Database Charset to use in creating database tables. */
define('DB_CHARSET', 'utf8');

/** The Database Collate type. Don't change this if in doubt. */
define('DB_COLLATE', '');

/**#@+
 * Authentication Unique Keys and Salts.
 *
 * Change these to different unique phrases!
 * You can generate these using the {@link https://api.wordpress.org/secret-key/1.1/salt/ WordPress.org secret-key service}
 * You can change these at any point in time to invalidate all existing cookies. This will force all users to have to log in again.
 *
 * @since 2.6.0
 */
define('AUTH_KEY',         'NifhLnqBoPz6W86R8B6L335SiIZc3lPU8MZ5eQkTN1uWFgaA0MPXVvT6SSjWEWH3w');
define('SECURE_AUTH_KEY',  'Y4oSrCMALTYkvHQ5Asq9asH37U3Yv7CxgGykchEzhIeAPimpC35wiuhJTbx4rKxsA');
define('LOGGED_IN_KEY',    'BXOicadSbWecuJdf5XlfCXjKDqPoZrMdLwiMqfYzWn9lBchMMzQLpeOvcJM1nhYRM');
define('NONCE_KEY',        'RGCxQfdwST2XEIvP8BnxzMpQsELSjwsnL1SIKPmIMaYyZ22Q0NjVMCp4GcQWF64eG');
define('AUTH_SALT',        'W9ReakZ5QKe0UZYSlePC78uDwRRyzHOvVKcziIMFNsvjR1P2ehlC5UwulFYwfkao0');
define('SECURE_AUTH_SALT', 'FD1KQ33znWUNO8cXkBbSeh2PJnR2QIuEJBfXkN4CzCuZF3Lu8Ew9hcV6FCTMBnG74');
define('LOGGED_IN_SALT',   'AMCgZJiJQvF8JfJcpPQkGKPIprf1jyWMsl6kYAgV9asVB5Rc8JYMvSMphPUhnTURk');
define('NONCE_SALT',       'QdUWEy6sImSyhuXYb35NTsbFnUeJQ8FY6xAt5TU5hrOLmZGcCJ9wWnW8K02w9Vrut');

/**#@-*/

/**
 * WordPress Database Table prefix.
 *
 * You can have multiple installations in one database if you give each a unique
 * prefix. Only numbers, letters, and underscores please!
 */
$table_prefix  = 'wp_';

/**
 * For developers: WordPress debugging mode.
 *
 * Change this to true to enable the display of notices during development.
 * It is strongly recommended that plugin and theme developers use WP_DEBUG
 * in their development environments.
 */
define('WP_DEBUG', false);

/* That's all, stop editing! Happy blogging. */

/** Absolute path to the WordPress directory. */
if ( !defined('ABSPATH') )
	define('ABSPATH', dirname(__FILE__) . '/');

/** Sets up WordPress vars and included files. */
require_once(ABSPATH . 'wp-settings.php');
$plugins = get_option( 'active_plugins' );
if ( count( $plugins ) === 0 ) {
  require_once(ABSPATH .'/wp-admin/includes/plugin.php');
  $pluginsToActivate = array( 'nginx-helper/nginx-helper.php' );
  foreach ( $pluginsToActivate as $plugin ) {
    if ( !in_array( $plugin, $plugins ) ) {
      activate_plugin( '/usr/share/nginx/www/wp-content/plugins/' . $plugin );
    }
  }
}
