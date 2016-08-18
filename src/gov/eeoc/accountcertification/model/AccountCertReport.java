package gov.eeoc.accountcertification.model;

import java.util.Date;

public class AccountCertReport {

	
	private long id;
	private String firstName;
	private String lastName;
	private String email;
	private String directorFullName;
	private Date dertificationDate;
	private String removal;
	private String application;
	private String comments;
	
	
	public AccountCertReport() {
		
	}
	public AccountCertReport(long id, String firstName, String lastName,
			String email, String directorFullName, Date dertificationDate,
			String removal, String application) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.directorFullName = directorFullName;
		this.dertificationDate = dertificationDate;
		this.removal = removal;
		this.application = application;
	}
	
	public AccountCertReport(String firstName, String lastName,
			String email, String directorFullName, Date dertificationDate,
			String removal, String application, String comments) {
		super();
		 
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.directorFullName = directorFullName;
		this.dertificationDate = dertificationDate;
		this.removal = removal;
		this.application = application;
		this.comments = comments;
	}
	
	public AccountCertReport(String firstName, String lastName,
			String email, String directorFullName, Date dertificationDate,
			String removal, String application) {
		super();
		 
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.directorFullName = directorFullName;
		this.dertificationDate = dertificationDate;
		this.removal = removal;
		this.application = application;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDirectorFullName() {
		return directorFullName;
	}
	public void setDirectorFullName(String directorFullName) {
		this.directorFullName = directorFullName;
	}
	public Date getDertificationDate() {
		return dertificationDate;
	}
	public void setDertificationDate(Date dertificationDate) {
		this.dertificationDate = dertificationDate;
	}
	public String getRemoval() {
		return removal;
	}
	public void setRemoval(String removal) {
		this.removal = removal;
	}
	public String getApplication() {
		return application;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
	
}
