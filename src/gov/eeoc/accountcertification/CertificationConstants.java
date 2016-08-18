package gov.eeoc.accountcertification;

import javax.inject.Named;


@Named
public interface CertificationConstants {
	String agency="Agency";
	String ImsAccess="Y";
	String NoUtilityAccess="0";
	int maxAttempts=3;
	String OIT="876";
	String ENCRYPTION_KEY="AccountCertification_Key";
	
	String FROM="email.from";
	String FROM_EMAIL="certificationadmin@eeoc.gov";
	String TO="email.to";
	String SUBJECT="email.subject";
	String CONTENT="email.content";
	String CC="email.cc";
	String BCC="email.bcc";
	String SMTP_SERVER="10.9.24.141";
	String EMAIL="email";
	String USER="user";
	String SELECTED_AGENCY="selectedAgency";
	String OFFICE_CODE="OfficeCode";
			
}
