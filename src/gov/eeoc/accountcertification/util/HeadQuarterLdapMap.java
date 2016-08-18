package gov.eeoc.accountcertification.util;

import java.util.HashMap;

public class HeadQuarterLdapMap {

	
	
	public HeadQuarterLdapMap() {
		
	  headMQMap.put("810", "OGC");
	  headMQMap.put("811", "OGC");
	  headMQMap.put("812", "OGC");
	  headMQMap.put("814", "OGC");
	  
	  headMQMap.put("820", "OLC");
	  headMQMap.put("821", "OLC");
	  
	  headMQMap.put("830", "OFP");
	  headMQMap.put("832", "OFP");
	  
	  headMQMap.put("834", "OFO");
	  headMQMap.put("835", "OGC");
	  
	  headMQMap.put("840", "CH");
	  headMQMap.put("841", "VCH");
	  headMQMap.put("842", "CM-1");
	  headMQMap.put("843", "CM-2");
	  headMQMap.put("844", "CM-3");
	  headMQMap.put("845", "CM-4");
	  headMQMap.put("866", "OIG");
	  
	  headMQMap.put("871", "ES");
	  headMQMap.put("872", "OEO");
	  
	  headMQMap.put("873", "OCLA");
	  headMQMap.put("874", "OHR");
	  
	  headMQMap.put("875", "ORIP");
	  headMQMap.put("876", "OIT");
	  
	  headMQMap.put("877", "OCFO");
	 
	  
	}
	
	
	private HashMap<String,String> headMQMap = new HashMap<String,String>();


	public HashMap getHeadMQMap() {
		return headMQMap;
	}


	public void setHeadMQMap(HashMap headMQMap) {
		this.headMQMap = headMQMap;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((headMQMap == null) ? 0 : headMQMap.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HeadQuarterLdapMap other = (HeadQuarterLdapMap) obj;
		if (headMQMap == null) {
			if (other.headMQMap != null)
				return false;
		} else if (!headMQMap.equals(other.headMQMap))
			return false;
		return true;
	}
	
}
