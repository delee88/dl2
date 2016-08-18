package gov.eeoc.accountcertification.util;

import gov.eeoc.accountcertification.model.Person;

import java.util.Comparator;

public class LdapPersonHelperEmail implements Comparator<Person> {
	 
	 @Override
	 public int compare(Person u1, Person u2) {
		  
		 String e1 = u1.getEmail().trim().toUpperCase();
		 String e2 = u2.getEmail().trim().toUpperCase();
		 e1 = e1.replaceAll("\\s", "");
		 e2 = e2.replaceAll("\\s", "");
		 return e1
		 	   .compareTo(e2);
		 
	 }
	

}
