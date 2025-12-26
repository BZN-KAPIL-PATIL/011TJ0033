package com.byzan.utility;

import java.util.ResourceBundle;

public interface PathConstant {

	static ResourceBundle rbBundle = ResourceBundle.getBundle("Configuration");

	static String INPUT = rbBundle.getString("INPUT").trim();
	static String OUTPUT = rbBundle.getString("OUTPUT").trim();
	static int Row = Integer.parseInt(rbBundle.getString("Row").trim());

	static String SUCCESS = rbBundle.getString("SUCCESS").trim();

	static String FAILURE = rbBundle.getString("FAILURE").trim();

	static String AUDIT_LOG = rbBundle.getString("AUDIT_LOG").trim();

	static String Validationfilepath = rbBundle.getString("Validationfilepath").trim();

	static  String InputFileDateFormat = rbBundle.getString("InputFileDateFormat");

}
