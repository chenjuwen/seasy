package com.seasy.commons.freemarker;

import java.util.Locale;

import freemarker.template.Configuration;
import freemarker.template.Version;

public class DefaultConfiguration {
	public static final String TEMPLATE_CLASSPATH = "/template";
	public static final String ENCODING = "UTF-8";
	public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String NUMBER_FORMAT = "####";
	public static final Locale LOCALE = Locale.CHINA;
	public static final Version VERSION = Configuration.VERSION_2_3_21;
	
}
