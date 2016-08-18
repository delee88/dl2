package gov.eeoc.accountcertification.model;

public class MappedLdapAndDBUserList {

	
	public MappedLdapAndDBUserList () {
		
	}

	
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
    private String dbCity;
    private String dbOfficeCode;
    
    
	public MappedLdapAndDBUserList(String firstName, String lastName,
			String fullName, String workPhone, String mobile, String email,
			String officeCode, String department, String roomNumber,
			String title, String description, String userId, String employee,
			String dbCity, String dbOfficeCode) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.fullName = fullName;
		this.workPhone = workPhone;
		this.mobile = mobile;
		this.email = email;
		this.officeCode = officeCode;
		this.department = department;
		this.roomNumber = roomNumber;
		this.title = title;
		this.description = description;
		this.userId = userId;
		this.employee = employee;
		this.dbCity = dbCity;
		this.dbOfficeCode = dbOfficeCode;
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
	public String getDbCity() {
		return dbCity;
	}
	public void setDbCity(String dbCity) {
		this.dbCity = dbCity;
	}
	public String getDbOfficeCode() {
		return dbOfficeCode;
	}
	public void setDbOfficeCode(String dbOfficeCode) {
		this.dbOfficeCode = dbOfficeCode;
	}
	public String getWorkPhone() {
		return workPhone;
	}
	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}
}
