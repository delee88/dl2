package gov.eeoc.accountcertification.util;

import gov.eeoc.accountcertification.model.Director;

import java.util.Comparator;

public class DirectorHelper implements Comparator<Director> {

	 @Override
	 public int compare(Director d1, Director d2) {
		 if(d1.getEmail() ==  null || d1.getEmail().isEmpty() )
		 {
			System.out.println("DirectorHelper null or empty value " + d1.getDirectorName());
					 		      return 1;
		 }
		 
		 if(d2.getEmail() ==  null || d2.getEmail().isEmpty())
		 {
				System.out.println("DirectorHelper null or empty value " + d2.getDirectorName());

			 return 1;
		 }
		 return d1.getEmail().trim().toUpperCase()
		 	   .compareTo(d2.getEmail().trim().toUpperCase());
		 
	 }
	
	 
} 


