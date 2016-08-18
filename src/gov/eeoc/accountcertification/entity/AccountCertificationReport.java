package gov.eeoc.accountcertification.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

//@Table(name="ACCOUNT_CERTIFICATION_REPORT" ,schema="IMS")  for testing on your local host

@Entity
@Table(name="ACCOUNT_CERTIFICATION_REPORT" , schema="IMS" ) 
@NamedQueries({
    @NamedQuery(name = "ACCOUNT_CERTIFICATION_REPORT.findAll", query = "SELECT l FROM AccountCertificationReport l"
    		)
})

public class AccountCertificationReport implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@GeneratedValue(strategy=GenerationType.SEQUENCE , generator="ACCOUNT_REPORT_SEQ" )
	@SequenceGenerator(name="ACCOUNT_REPORT_SEQ", sequenceName="ACCOUNT_REPORT_SEQ", allocationSize=1)
	@Id
    @Basic(optional = false)
    @Column(name = "ACR_ID")
	private long id;
	
	@Basic(optional = true)
    @Column(name = "FIRST_NAME")
	private String firstName;
	
	@Basic(optional = true)
    @Column(name = "LAST_NAME")
	private String lastName;
	
	@Basic(optional = true)
    @Column(name = "EMAIL")
	private String email;
	
	@Basic(optional = true)
    @Column(name = "DIRECTOR_FULL_NAME")
	private String directorFullName;
	
	@Basic(optional = true)
    @Column(name = "CERTIFICATION_DATE")
	private Date certificationDate;
	
	@Basic(optional = true)
    @Column(name = "REMOVAL_YN")
	private String removal;
	
	@Basic(optional = true)
    @Column(name = "APPLICATION")
	private String application;
	
	@Basic(optional = true)
    @Column(name = "COMMENTS")
	private String comments;
	

	public AccountCertificationReport(long id) {
		super();
		this.id = id;
	}

	public AccountCertificationReport(long id, String firstName,
			String lastName, String email, String directorFullName,
			Date certificationDate, String removal, String application) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.directorFullName = directorFullName;
		this.certificationDate = certificationDate;
		this.removal = removal;
		this.application = application;
	}
	
	public AccountCertificationReport() {
		
	}
	
	@Override
	public String toString() {
		return "gov.eeoc.accountcertification.entity.AccountCertificationReport [id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		AccountCertificationReport other = (AccountCertificationReport) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
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

	public Date getCertificationDate() {
		return certificationDate;
	}

	public void setCertificationDate(Date certificationDate) {
		this.certificationDate = certificationDate;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	  

	
}
