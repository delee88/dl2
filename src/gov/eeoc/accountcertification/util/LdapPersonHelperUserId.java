package gov.eeoc.accountcertification.util;

import gov.eeoc.accountcertification.model.Person;

import java.util.Comparator;

public class LdapPersonHelperUserId implements Comparator<Person> {
 
	 @Override
	 public int compare(Person u1, Person u2) {
		 return u1.getUserId().trim().toUpperCase()
		 	   .compareTo(u2.getUserId().trim().toUpperCase());
		 
	 }
	
	/* 
    
    @Override
    public int compare(Person p1, Person p2) {
    	int val = p1.getUserId().compareTo(p2.getUserId());
           if(val > 0 )   {
            return 1;
        } else {
            return -1;
        }
    }
    
    */
} 


