package gov.eeoc.accountcertification.util;

import gov.eeoc.accountcertification.CertificationConstants;

public class AccessUtil {
public static String getFormattedUtility(String utility){
	if(CertificationConstants.NoUtilityAccess.equals(utility))
	{
		return "N";
	}else{
		return "Y";
	}
}

public static String getTotalImsAccess(String privateRole,
		String federalRole,String litigationRole,
		String outreachRole,
		String appealsRole){
	
	if(CertificationConstants.ImsAccess.equals(privateRole)||
			CertificationConstants.ImsAccess.equals(federalRole)||
			CertificationConstants.ImsAccess.equals(litigationRole)||
			CertificationConstants.ImsAccess.equals(outreachRole)||
			CertificationConstants.ImsAccess.equals(appealsRole))
			   {
		   return "Y";
	   }else{
		   return "N";
	   }
}
}
