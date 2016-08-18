package gov.eeoc.accountcertification.model;

public class DMSUsers {

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((mailaddress == null) ? 0 : mailaddress.hashCode());
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
		DMSUsers other = (DMSUsers) obj;
		if (mailaddress == null) {
			if (other.mailaddress != null)
				return false;
		} else if (!mailaddress.equals(other.mailaddress))
			return false;
		return true;
	}
	private String name;
	private String firstName;
	private String lastName;
	private String loginid;
	private String mailaddress;
	private String officelocation;
	
	
	public DMSUsers(String name, String firstName, String lastName,
			String mailaddress, String officelocation) {
		super();
		this.name = name;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mailaddress = mailaddress;
		this.officelocation = officelocation;
	}
	
	public DMSUsers() {
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public String getMailaddress() {
		return mailaddress;
	}
	public void setMailaddress(String mailaddress) {
		this.mailaddress = mailaddress;
	}
	public String getOfficelocation() {
		return officelocation;
	}
	public void setOfficelocation(String officelocation) {
		this.officelocation = officelocation;
	}
	
	
}
