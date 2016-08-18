package gov.eeoc.accountcertification.util;

import gov.eeoc.accountcertification.model.User;

import java.util.HashMap;

public class ImsUserMap {

	
	private String name;
	
	private User us;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private User users;

	public User getUsers() {
		return users;
	}

	public void setUsers(User users) {
		this.users = users;
	}
	
	private HashMap<String,User> imsUserMap = new HashMap<String,User>();

	public synchronized HashMap<String, User> getImsUserMap() {
		return imsUserMap;
	}

	public void setImsUserMap(HashMap<String, User> imsUserMap) {
		this.imsUserMap = imsUserMap;
	}

	 

	
}
