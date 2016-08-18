package gov.eeoc.accountcertification.model;
//Ldap Pojo convert to jaxb xml

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;  
import javax.xml.bind.annotation.XmlAccessorType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "person")  
public class Person implements Comparable<Person> {


	private String firstName;
	private String lastName;
	private String fullName;
	private String workPhone;
	private String mobile;
	private String email;
	private String officeCode;
	private String department;
	private String roomNumber;
	private String title;
	private String description;
	private String userId;
	private String employee;
	
	
	public Person () {
		
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
	public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public String getWorkPhone() {
		return workPhone;
	}
	 
	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}
	public String getMobile() {
		return mobile;
	}
	 
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
 
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOfficeCode() {
		return officeCode;
	}
	 
	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}
	public String getDepartment() {
		return department;
	}
	 
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}
	
	@Override
	public int hashCode() {
		System.out.println("hashCode being used from Person Class");
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		System.out.println("Equals being used from Person Class");

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}


	@Override
    public String toString() {
        return "Person [firstname=" + this.firstName + ", lastname=" + this.lastName + ", officeCode="
                + this.officeCode + ", UserId=" + this.userId  + "]";
    }


	@Override
	public int compareTo(Person o) {
		// TODO Auto-generated method stub
		return this.getUserId().compareTo(o.getUserId());
	}   
}
