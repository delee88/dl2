package gov.eeoc.accountcertification.util;

import java.util.HashMap;

public class UserOfficeCodeHelper {

	
	
     /* 
	      ALBUDO, ATLADO, BALTDO, BIRMDO, BOSTAO, BUFFLO, CHARDO, CHICDO, 
	      CINCAO, CLEVDO, DALDO, DENVDO, DETRDO, ELPAAO, FRESLO, GRNBLO,
	      GRNVLO, HONOLO, HOUSDO, INDIDO, JACKAO, KANSAO, LASVLO, LITTAO,
	      LOSADO, LOUIAO, MEMPDO, MIAMDO, MILWDO, MINNAO, MOBILO, NASHAO, 
	      NEWAAO, NEWODO, NEWYDO, NORFAO, OAKLLO, OKLAAO, PHILDO, PHOEDO, 
	      PITTAO, PUERAO, RALIAO, RICHAO, SANADO, SANDAO, SANFDO, SANJLO, 
	      SAVALO, SEATDO, STLODO, TAMPAO, WASHDC
      */
	
	   
	
	 private static HashMap<String,String> IMSOfficeToLdapOffice
	  = new HashMap<String,String>();
	
     public static HashMap<String, String> getIMSOfficeToLdapOffice() {
		return IMSOfficeToLdapOffice;
	}

	private static HashMap<String,CityCode> ldapOfficeToIMSOffice
	  = new HashMap<String,CityCode>();
	
    public static HashMap<String, CityCode> getldapOfficeToIMSOffice() {
		return ldapOfficeToIMSOffice;
	 }

static {
	    ldapOfficeToIMSOffice.put("ALBUDO", new CityCode("Albuquerque","543"));
	    ldapOfficeToIMSOffice.put("ATLADO", new CityCode("Atlanta","410") );
	    ldapOfficeToIMSOffice.put("BALTDO", new CityCode("Baltimore","531"));
	  // ldapOfficeToIMSOffice.put("BIRMDO", ""); // ask josh about this code
	    ldapOfficeToIMSOffice.put("BOSTAO", new CityCode("Boston","523"));
	    ldapOfficeToIMSOffice.put("BUFFLO", new CityCode("Buffalo","525"));
	    ldapOfficeToIMSOffice.put("CHARDO", new CityCode("Charlotte","430"));
	    ldapOfficeToIMSOffice.put("CHICDO", new CityCode("Chicago","440"));
	    ldapOfficeToIMSOffice.put("CINCAO", new CityCode("Cincinnati","473"));
	    ldapOfficeToIMSOffice.put("CLEVDO", new CityCode("Cleveland","532") );
	    ldapOfficeToIMSOffice.put("DALDO", new CityCode("Dallas","450"));
	    ldapOfficeToIMSOffice.put("DENVDO", new CityCode("Denver","541"));
	    ldapOfficeToIMSOffice.put("DETRDO", new CityCode("Detroit","471"));
	    ldapOfficeToIMSOffice.put("ELPAAO", new CityCode("El Paso","453"));
	    ldapOfficeToIMSOffice.put("FRESLO", new CityCode("Fresno","485"));
	    ldapOfficeToIMSOffice.put("GRNBLO", new CityCode("Greensboro","435"));
	    ldapOfficeToIMSOffice.put("GRNVLO", new CityCode("Greenville","436"));
	    ldapOfficeToIMSOffice.put("HONOLO", new CityCode("Honolulu","486"));
	    ldapOfficeToIMSOffice.put("HOUSDO", new CityCode("Houston","460") );
	    ldapOfficeToIMSOffice.put("INDIDO", new CityCode("Indianapolis","470"));
	    ldapOfficeToIMSOffice.put("JACKAO", new CityCode("Jackson","423") );
	    ldapOfficeToIMSOffice.put("KANSAO", new CityCode("Kansas City","563"));
	    ldapOfficeToIMSOffice.put("LASVLO",  new CityCode("Las Vegas","487") );
	    ldapOfficeToIMSOffice.put("LITTAO",  new CityCode("Little Rock","493") );
	    ldapOfficeToIMSOffice.put("LOSADO", new CityCode("Los Angeles","480"));
	    ldapOfficeToIMSOffice.put("LOUIAO", new CityCode("Louisville","474") );
	    ldapOfficeToIMSOffice.put("MEMPDO", new CityCode("Memphis","490") );
	    ldapOfficeToIMSOffice.put("MIAMDO", new CityCode("Miami","510") );
	    ldapOfficeToIMSOffice.put("MILWDO", new CityCode("Milwaukee","443") );
	    ldapOfficeToIMSOffice.put("MINNAO", new CityCode("Minneapolis","444"));
	    ldapOfficeToIMSOffice.put("MOBILO", new CityCode("Mobile","425"));
	    ldapOfficeToIMSOffice.put("NASHAO", new CityCode("Nashville","494"));
	    ldapOfficeToIMSOffice.put("NEWAAO",  new CityCode("Newark","524"));
	    ldapOfficeToIMSOffice.put("NEWODO", new CityCode("New Orleans","461"));
	    ldapOfficeToIMSOffice.put("NEWYDO", new CityCode("New York","520"));
	  // ldapOfficeToIMSOffice.put("NORFAO", "");  // ask josh about this code area code 757
	    ldapOfficeToIMSOffice.put("OAKLLO", new CityCode("Oakland","555"));
	    ldapOfficeToIMSOffice.put("OKLAAO", new CityCode("Oklahoma City","564"));
	    ldapOfficeToIMSOffice.put("PHILDO", new CityCode("Philadelphia","530"));
	    ldapOfficeToIMSOffice.put("PHOEDO", new CityCode("Phoenix","540"));
	    ldapOfficeToIMSOffice.put("PITTAO", new CityCode("Pittsburgh","533"));
	 //  ldapOfficeToIMSOffice.put("PUERAO", ""); // ask josh about this code
	 //  ldapOfficeToIMSOffice.put("RALIAO", ""); // ask josh about this code
	 //  ldapOfficeToIMSOffice.put("RICHAO", ""); // ask josh about this code
	 //  ldapOfficeToIMSOffice.put("SANADO", ""); // ask josh about this code
	 //  ldapOfficeToIMSOffice.put("SANDAO", ""); // ask josh about this code
	 //  ldapOfficeToIMSOffice.put("SANFDO", ""); // ask josh about this code
	 //  ldapOfficeToIMSOffice.put("SANJLO", ""); // ask josh about this code
	 //  ldapOfficeToIMSOffice.put("SAVALO", ""); // ask josh about this code
	     ldapOfficeToIMSOffice.put("SEATDO", new CityCode("Seattle","551") ); // ask josh about this code
	     ldapOfficeToIMSOffice.put("STLODO", new CityCode("Saint Louis","560"));
	     ldapOfficeToIMSOffice.put("TAMPAO", new CityCode("Tampa","511"));
	     ldapOfficeToIMSOffice.put("WASHFO", new CityCode("Washington","570"));
	  
	     
	    /* -------------------------  EEOC MAP  ----------------------------- */
	     
	     IMSOfficeToLdapOffice.put("543", "ALBUDO");
	     IMSOfficeToLdapOffice.put("410", "ATLADO");
	     IMSOfficeToLdapOffice.put("531", "BALTDO");
		 IMSOfficeToLdapOffice.put("420", "BIRMDO"); 
	     IMSOfficeToLdapOffice.put("523", "BOSTAO");
	     IMSOfficeToLdapOffice.put("525", "BUFFLO");
	     IMSOfficeToLdapOffice.put("430", "CHARDO");
	     IMSOfficeToLdapOffice.put("440", "CHICDO");
	     IMSOfficeToLdapOffice.put("473", "CINCAO");
	     IMSOfficeToLdapOffice.put("532", "CLEVDO");
	     IMSOfficeToLdapOffice.put("450", "DALDO");
	     IMSOfficeToLdapOffice.put("541", "DENVDO");
	     IMSOfficeToLdapOffice.put("471", "DETRDO");
	     IMSOfficeToLdapOffice.put("453", "ELPAAO");
	     IMSOfficeToLdapOffice.put("485", "FRESLO");
	     IMSOfficeToLdapOffice.put("435", "GRNBLO");
	     IMSOfficeToLdapOffice.put("436", "GRNVLO");
	     IMSOfficeToLdapOffice.put("486", "HONOLO");
	     IMSOfficeToLdapOffice.put("460", "HOUSDO");
	     IMSOfficeToLdapOffice.put("470", "INDIDO");
	     IMSOfficeToLdapOffice.put("423", "JACKAO");
	     IMSOfficeToLdapOffice.put("563", "KANSAO");
	     IMSOfficeToLdapOffice.put("487", "LASVLO");
	     IMSOfficeToLdapOffice.put("493", "LITTAO");
	     IMSOfficeToLdapOffice.put("480", "LOSADO");
	     IMSOfficeToLdapOffice.put("474", "LOUIAO");
	     IMSOfficeToLdapOffice.put("490", "MEMPDO");
	     IMSOfficeToLdapOffice.put("510", "MIAMDO");
	     IMSOfficeToLdapOffice.put("443", "MILWDO");
	     IMSOfficeToLdapOffice.put("444", "MINNAO");
	     IMSOfficeToLdapOffice.put("425", "MOBILO");
	     IMSOfficeToLdapOffice.put("494", "NASHAO");
	     IMSOfficeToLdapOffice.put("524", "NEWAAO");
	     IMSOfficeToLdapOffice.put("461", "NEWODO");
	     IMSOfficeToLdapOffice.put("520", "NEWYDO");
	     IMSOfficeToLdapOffice.put("437", "NORFAO");
	     IMSOfficeToLdapOffice.put("555", "OAKLLO");
	     IMSOfficeToLdapOffice.put("564", "OKLAAO");
	     IMSOfficeToLdapOffice.put("530", "PHILDO"); 
	     IMSOfficeToLdapOffice.put("540", "PHOEDO");
	     IMSOfficeToLdapOffice.put("533", "PITTAO");
	     
	     IMSOfficeToLdapOffice.put("515", "PUERAO");
	     IMSOfficeToLdapOffice.put("433", "RALIAO");
	     IMSOfficeToLdapOffice.put("433", "RALIAO");
	     IMSOfficeToLdapOffice.put("438", "RICHAO");
	     IMSOfficeToLdapOffice.put("438", "RICHAO");
	     IMSOfficeToLdapOffice.put("451", "SANADO");
	     IMSOfficeToLdapOffice.put("488", "SANDAO");
	     IMSOfficeToLdapOffice.put("550", "SANFDO");
	     IMSOfficeToLdapOffice.put("556", "SANJLO");
	     IMSOfficeToLdapOffice.put("415", "SAVALO"); 
	     IMSOfficeToLdapOffice.put("551", "SEATDO");
	     IMSOfficeToLdapOffice.put("560", "STLODO");
	     IMSOfficeToLdapOffice.put("511", "TAMPAO");
	     IMSOfficeToLdapOffice.put("570", "WASHFO");
	     
	     
	     
	     
   }
					
			  
					
}
