package gov.eeoc.accountcertification.util;

import gov.eeoc.accountcertification.util.EncryptionUtil;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.apache.log4j.Logger;

public class SystemUtil {
	public final static String CONFIG_PROPERTY_FILE = "config.properties";

	private final static String PARTIALREPORT_URL_PROP = "partialreport.url";

	private final static String REPORT_URL_PROP = "report.url";

	private final static String ACCOUNTCERT_URL_PROP = "accountcert.url";

	private static Logger log = Logger.getLogger(SystemUtil.class);

	/*
	 * Map contains "Case Name" as the key and a list containing charge/inquiry
	 * number
	 */

	public Properties getPropertiesFromClasspath(String fileName)
			throws RuntimeException {
		if (fileName == null || fileName.isEmpty())
			return null;
		Properties props = new Properties();
		InputStream inputStream;
		inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
		try {
			props.load(inputStream);
			if (inputStream == null) {
				throw new FileNotFoundException("property file '" + fileName
						+ "' not found in the classpath");
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return props;
	}

	public String buildPartialReportServerUrl(String webServer,
			String officeCode) {
		if (webServer == null || officeCode == null)
			return null;
		String url = null;
		Properties prop;
		try {
			prop = getPropertiesFromClasspath(CONFIG_PROPERTY_FILE);
			String partialUrl = prop.getProperty(PARTIALREPORT_URL_PROP);

			Map<String, String> partialvaluesMap = new HashMap<String, String>();
			partialvaluesMap.put("webserver", webServer);
			partialvaluesMap.put("officeCode", officeCode);
			StrSubstitutor sub = new StrSubstitutor(partialvaluesMap);
			url = sub.replace(partialUrl);
		} catch (Exception e) {

			log.error(
					"error while building  partial url for token creation for officeCode ="
							+ officeCode, e);
		}
		return url;
	}

	public String buildReportServerUrl(String webServer, String officeCode,
			String format, String token) {
		if (webServer == null || officeCode == null || format == null
				|| token == null)
			return null;
		String url = null;
		Properties prop;
		try {
			prop = getPropertiesFromClasspath(CONFIG_PROPERTY_FILE);
			String partialUrl = prop.getProperty(REPORT_URL_PROP);

			Map<String, String> valuesMap = new HashMap<String, String>();
			valuesMap.put("webserver", webServer);
			valuesMap.put("officeCode", officeCode);
			valuesMap.put("format", format);
			valuesMap.put("token", token);
			StrSubstitutor sub = new StrSubstitutor(valuesMap);
			url = sub.replace(partialUrl);
		} catch (Exception e) {
			log.error("error while building report url for officeCode ="
					+ officeCode, e);

		}
		return url;
	}

	public String buildIMSAccountCertificationUrl(String webServer,
			String officeCode) {

		if (webServer == null || officeCode == null)
			return null;
		String encryptedOfficeCode = " ";
		try {
			EncryptionUtil encryptionUtil = new EncryptionUtil();
			encryptedOfficeCode = encryptionUtil.encrypt(officeCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String url = null;
		Properties prop;
		try {
			prop = getPropertiesFromClasspath(CONFIG_PROPERTY_FILE);
			String partialUrl = prop.getProperty(ACCOUNTCERT_URL_PROP);

			Map<String, String> valuesMap = new HashMap<String, String>();
			valuesMap.put("webserver", webServer);
			valuesMap.put("encryptedOfficeCode", encryptedOfficeCode);
			StrSubstitutor sub = new StrSubstitutor(valuesMap);
			url = sub.replace(partialUrl);
		} catch (Exception e) {
			log.error("error while building report url for officeCode ="
					+ officeCode, e);

		}
		return url;
	}

}
