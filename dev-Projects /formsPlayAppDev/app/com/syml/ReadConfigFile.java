package com.syml;

import java.util.Properties;

import play.Logger;


public class ReadConfigFile {
	public static Properties readConfigfile() {

		Properties prop = new Properties();
		try {
			prop.load(ReadConfigFile.class.getClassLoader().getResourceAsStream(
					Constants.CONFIG_PROPERTIES_FILE));
		} catch (Exception e) {
			Logger.error("error in Reading config.properties file" + e);
		}
		return prop;
	}
}
