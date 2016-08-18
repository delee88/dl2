package gov.eeoc.accountcertification.util;

import gov.eeoc.accountcertification.model.AccountCertReport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConvertUserMapToList {

	
public ConvertUserMapToList () {
		
}

public static  List<AccountCertReport> getAccntReportFromMap(HashMap<String,AccountCertReport> accnMap) {
	List<AccountCertReport> list = new ArrayList<AccountCertReport>(accnMap.values());
	return list;
}

 

}
