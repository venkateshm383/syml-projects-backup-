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
define('DB_NAME', 'devwordpressnirmata');

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
define('AUTH_KEY',         'bE163dEeionrKkVn7ng2oz2x6bv581UG0ecjoSHtbDz1gkiZig3NWg7LGr8BFBTh2');
define('SECURE_AUTH_KEY',  'Q8CK28PaQ15hPMqo35VW7du2OvghN76Zh2YnkpLr2Wtq9xWzWDqGDl1bdNkon9GhO');
define('LOGGED_IN_KEY',    'jwm0ZOslGltJECDChj5brKCOlCfPaDvKsSwLZv7VbZRY1esfmiQZxElL7dZTT3gjZ');
define('NONCE_KEY',        'NcmkzVPr0XzXOwMFjBsivgOiyUK8ZMYB2VaM4hc7QQ1GPMuTveKy0nROcmgyXuLQn');
define('AUTH_SALT',        '30BmaNCzuUL2F0cG1S1reSddnSFi2BtI9al2pRBqn9RFK1xIOJOyDDuBnMeVJXBGy');
define('SECURE_AUTH_SALT', 'UuIRGoIZCZK23hnwMVhpwTTLEaXtRuzPKVhQeMF6GoUTh1OU0Wnvql88Mzf7w4UKJ');
define('LOGGED_IN_SALT',   'RkbvKM9nF6m4BMJ2SinNyXMbQRs0sTkqyDKVZtNZijoYhbeG07ie4URWPiWINsFKc');
define('NONCE_SALT',       'Ea7MqI25aA16NuNNzhSL0LkyIN8I9GealCqUtdYUKZ2kbT3QYKskHTzETIPSqcvgj');

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
