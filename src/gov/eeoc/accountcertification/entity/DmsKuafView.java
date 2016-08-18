
package gov.eeoc.accountcertification.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Table(name = "DMS_USERS")
@NamedQueries({
    @NamedQuery(name = "dmsUser.findAll", query = "SELECT l FROM DmsKuafView l")
})
public class DmsKuafView implements Serializable {
	
	
private static final long serialVersionUID = 1L;
	
  
    @Basic(optional = true)
    @Column(name = "LASTNAME")
    private String lastname;
    
    @Basic(optional = true)
    @Column(name = "FIRSTNAME")
    private String firstname;
    
    @Basic(optional = true)
    @Column(name = "LOGONID")
    private String logonid;
    
    @Basic(optional = true)
    @Column(name = "OFFICELOCATION")
    private String officelocation;
    
    @Basic(optional = true)
    @Column(name = "MAILADDRESS")
    private String mailaddress;
    
        
    public DmsKuafView() {
    	
    }


	public DmsKuafView(String lastname, String firstname, String logonid,String mailaddress,
			String officelocation, String name) {
		super();
		this.lastname = lastname;
		this.firstname = firstname;
		this.mailaddress = mailaddress;
		this.officelocation = officelocation;
		this.logonid = logonid;
	}


	@Override
	public String toString() {
		return "eeoc.gov.ims.webservice.entity.DmsKuafView [name=" + this.getClass() + "]";
	}


	public String getLastname() {
		return lastname;
	}


	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	public String getFirstname() {
		return firstname;
	}


	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}


	public String getLogonid() {
		return logonid;
	}


	public void setLogonid(String logonid) {
		this.logonid = logonid;
	}


	public String getOfficelocation() {
		return officelocation;
	}


	public void setOfficelocation(String officelocation) {
		this.officelocation = officelocation;
	}

	
	public String getMailaddress() {
		return mailaddress;
	}


	public void setMailaddress(String mailaddress) {
		this.mailaddress = mailaddress;
	}
	
	
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
		DmsKuafView other = (DmsKuafView) obj;
		if (mailaddress == null) {
			if (other.mailaddress != null)
				return false;
		} else if (!mailaddress.equals(other.mailaddress))
			return false;
		return true;
	}

}
