package gov.eeoc.accountcertification.util;

import gov.eeoc.accountcertification.model.DMSUsers;

import java.util.HashMap;

public class DmsEmailMap {

	
	 private String dms;

	public String getDms() {
		return dms;
	}


	public void setDms(String dms) {
		this.dms = dms;
	}


	public DmsEmailMap() {
		
	}
	
	private HashMap<String,DMSUsers> dmsmap = new HashMap<String,DMSUsers>(2500);


	public HashMap<String, DMSUsers> getDmsmap() {
		return dmsmap;
	}


	public void setDmsmap(HashMap<String, DMSUsers> dmsmap) {
		this.dmsmap = dmsmap;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dms == null) ? 0 : dms.hashCode());
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
		DmsEmailMap other = (DmsEmailMap) obj;
		if (dms == null) {
			if (other.dms != null)
				return false;
		} else if (!dms.equals(other.dms))
			return false;
		return true;
	}


	 
	
}
