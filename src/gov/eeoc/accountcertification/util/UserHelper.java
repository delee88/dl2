 


package gov.eeoc.accountcertification.util;

import gov.eeoc.accountcertification.model.User;

import java.util.Comparator;

public class UserHelper implements Comparator<User> {
 
	 @Override
	 public int compare(User u1, User u2) {
		 return u1.getLastName().trim().toUpperCase()
		 	   .compareTo(u2.getLastName().trim().toUpperCase());
		 
	 }
	
	 
} 


