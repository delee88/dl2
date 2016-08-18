package gov.eeoc.accountcertification.util;

import gov.eeoc.accountcertification.model.Person;
import gov.eeoc.accountcertification.model.User;

import java.util.Comparator;
import java.util.HashMap;

public class ImsUserHelperEmail implements Comparator<User>{

	@Override
	 public int compare(User u1, User u2) {
		 return u1.getEmail().trim().toUpperCase()
		 	   .compareTo(u2.getEmail().trim().toUpperCase());
		 
	 }
	
	 
}
