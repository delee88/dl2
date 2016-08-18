package gov.eeoc.accountcertification.util;

import java.io.File;

public class LdapXmlHelper {

	
	public LdapXmlHelper() {
		super();
 	}
	
	
	public LdapXmlHelper(String userXml) {
		super();
		this.userXml = userXml;
	}

	private String userXml;

	private File xmlFile;
	
	

	public String getUserXml() {
		return userXml;
	}

	public void setUserXml(String userXml) {
		this.userXml = userXml;
	}

	public File getXmlFile() {
		return xmlFile;
	}

	public void setXmlFile(File xmlFile) {
		this.xmlFile = xmlFile;
	}
	
	
}
