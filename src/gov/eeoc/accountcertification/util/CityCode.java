package gov.eeoc.accountcertification.util;

class CityCode {
	  private String code;
	  private String city;
	  
	  CityCode (String str1, String str2) {
		  this.city = str1;
		  this.code = str2;
	  }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	    
}