package gov.eeoc.accountcertification.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@NamedQueries({
    @NamedQuery(name = "LiveLinkKuaf.findAll", query = "SELECT l FROM LiveLinkKuaf l"
    		)  		  
})
@Entity
@Table(name = "LIVELINKKUAF")
public class LiveLinkKuaf implements Serializable {
	
	
private static final long serialVersionUID = 1L;
	
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    
    @Basic(optional = false)
    @Column(name = "OWNERID")
    private Long ownerid;
    
    @Basic(optional = false)
    @Column(name = "TYPE")
    private Long type;
    
    @Basic(optional = false)
    @Column(name = "SPACEID")
    private Long spaceid;
    
    @Basic(optional = false)
    @Column(name = "NAME")
    private String name;
    
    @Basic(optional = true)
    @Column(name = "USERDATA")
    private String userdata;
    
    @Basic(optional = true)
    @Column(name = "LEADERID")
    private Long leaderid;
    
    @Basic(optional = true)
    @Column(name = "DELETED")
    private Long deleted;
    
    @Basic(optional = true)
    @Column(name = "USERPWD")
    private String userid;
    
    @Basic(optional = true)
    @Column(name = "GROUPID")
    private Long groupid;
    
    @Basic(optional = true)
    @Column(name = "USERPRIVILEGES")
    private Long userprivileges;
    
    @Basic(optional = true)
    @Column(name = "LASTNAME")
    private String lastname;
    
    @Basic(optional = true)
    @Column(name = "MIDDLENAME")
    private String middlename;
    
    @Basic(optional = true)
    @Column(name = "FIRSTNAME")
    private String firstname;
    
    @Basic(optional = true)
    @Column(name = "MAILADDRESS")
    private String mailaddress;
    
    @Basic(optional = true)
    @Column(name = "CONTACT")
    private String contact;
    
    @Basic(optional = true)
    @Column(name = "TITLE")
    private String title;
    
    @Basic(optional = true)
    @Column(name = "PWDEXPIREDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pwdexpiredate;
    
    @Basic(optional = true)
    @Column(name = "PWDEXPIREMODE")
    private Long  pwdexpiremode;
    
    @Basic(optional = true)
    @Column(name = "SETTINGSNUM")
    private Long  settingsnum;
    
    @Basic(optional = true)
    @Column(name = "FAX")
    private String fax;
    
    @Basic(optional = true)
    @Column(name = "OFFICELOCATION")
    private String officelocation;
    
    @Basic(optional = true)
    @Column(name = "TIMEZONE")
    private Long  timezone;
    
    @Basic(optional = true)
    @Column(name = "PHOTOID")
    private Long  photoid;
    
    @Basic(optional = true)
    @Column(name = "GENDER")
    private Long  gender;
    
    @Basic(optional = true)
    @Column(name = "BIRTHDAY")
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthday;
    
    @Basic(optional = true)
    @Column(name = "PERSONALEMAIL")
    private String personalemail;
    
    @Basic(optional = true)
    @Column(name = "HOMEADDRESS1")
    private String homeaddress1;
    
    @Basic(optional = true)
    @Column(name = "HOMEADDRESS2")
    private String homeaddress2;
    
    @Basic(optional = true)
    @Column(name = "HOMEPHONE")
    private String homephone;
    
    @Basic(optional = true)
    @Column(name = "HOMEFAX")
    private String homefax;
    
    @Basic(optional = true)
    @Column(name = "CELLULARPHONE")
    private String cellularphone;
    
    @Basic(optional = true)
    @Column(name = "PAGER")
    private String pager;
    
    @Basic(optional = true)
    @Column(name = "HOMEPAGE")
    private String homepage;
    
    @Basic(optional = true)
    @Column(name = "FAVORITES1")
    private String favorites1;
    
    @Basic(optional = true)
    @Column(name = "FAVORITES2")
    private String favorites2;
    
    @Basic(optional = true)
    @Column(name = "FAVORITES3")
    private String favorites3;
    
    @Basic(optional = true)
    @Column(name = "INTERESTS")
    private String interests;
    
    
    public LiveLinkKuaf() {
    }

    public LiveLinkKuaf(Long id) {
        this.id = id;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		LiveLinkKuaf other = (LiveLinkKuaf) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "eeoc.gov.ims.webservice.entity.LiveLinkKuaf [id=" + id + "]";
	}

	public LiveLinkKuaf(Long id, Long ownerid, Long type, Long spaceid,
			String name, String userdata, Long leaderid, Long deleted,
			String userid, Long groupid, Long userprivileges, String lastname,
			String middlename, String firstname, String mailaddress,
			String contact, String title, Date pwdexpiredate,
			Long pwdexpiremode, Long settingsnum, String fax,
			String officelocation, Long timezone, Long photoid, Long gender,
			Date birthday, String personalemail, String homeaddress1,
			String homeaddress2, String homephone, String homefax,
			String cellularphone, String pager, String homepage,
			String favorites1, String favorites2, String favorites3,
			String interests) {
		super();
		this.id = id;this.ownerid = ownerid;this.type = type;this.spaceid = spaceid;
		this.name = name; this.userdata = userdata; this.leaderid = leaderid; this.deleted = deleted;
		this.userid = userid; this.groupid = groupid; this.userprivileges = userprivileges;
		this.lastname = lastname; this.middlename = middlename; this.firstname = firstname;
		this.mailaddress = mailaddress; this.contact = contact; this.title = title; this.pwdexpiredate = pwdexpiredate;
		this.pwdexpiremode = pwdexpiremode; this.settingsnum = settingsnum; this.fax = fax;
		this.officelocation = officelocation; this.timezone = timezone; this.photoid = photoid;
		this.gender = gender; this.birthday = birthday; this.personalemail = personalemail;
		this.homeaddress1 = homeaddress1; this.homeaddress2 = homeaddress2; this.homephone = homephone;
		this.homefax = homefax; this.cellularphone = cellularphone; this.pager = pager;
		this.homepage = homepage; this.favorites1 = favorites1; this.favorites2 = favorites2;
		this.favorites3 = favorites3; this.interests = interests;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOwnerid() {
		return ownerid;
	}

	public void setOwnerid(Long ownerid) {
		this.ownerid = ownerid;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Long getSpaceid() {
		return spaceid;
	}

	public void setSpaceid(Long spaceid) {
		this.spaceid = spaceid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserdata() {
		return userdata;
	}

	public void setUserdata(String userdata) {
		this.userdata = userdata;
	}

	public Long getLeaderid() {
		return leaderid;
	}

	public void setLeaderid(Long leaderid) {
		this.leaderid = leaderid;
	}

	public Long getDeleted() {
		return deleted;
	}

	public void setDeleted(Long deleted) {
		this.deleted = deleted;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Long getGroupid() {
		return groupid;
	}

	public void setGroupid(Long groupid) {
		this.groupid = groupid;
	}

	public Long getUserprivileges() {
		return userprivileges;
	}

	public void setUserprivileges(Long userprivileges) {
		this.userprivileges = userprivileges;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getMailaddress() {
		return mailaddress;
	}

	public void setMailaddress(String mailaddress) {
		this.mailaddress = mailaddress;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getPwdexpiredate() {
		return pwdexpiredate;
	}

	public void setPwdexpiredate(Date pwdexpiredate) {
		this.pwdexpiredate = pwdexpiredate;
	}

	public Long getPwdexpiremode() {
		return pwdexpiremode;
	}

	public void setPwdexpiremode(Long pwdexpiremode) {
		this.pwdexpiremode = pwdexpiremode;
	}

	public Long getSettingsnum() {
		return settingsnum;
	}

	public void setSettingsnum(Long settingsnum) {
		this.settingsnum = settingsnum;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getOfficelocation() {
		return officelocation;
	}

	public void setOfficelocation(String officelocation) {
		this.officelocation = officelocation;
	}

	public Long getTimezone() {
		return timezone;
	}

	public void setTimezone(Long timezone) {
		this.timezone = timezone;
	}

	public Long getPhotoid() {
		return photoid;
	}

	public void setPhotoid(Long photoid) {
		this.photoid = photoid;
	}

	public Long getGender() {
		return gender;
	}

	public void setGender(Long gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getPersonalemail() {
		return personalemail;
	}

	public void setPersonalemail(String personalemail) {
		this.personalemail = personalemail;
	}

	public String getHomeaddress1() {
		return homeaddress1;
	}

	public void setHomeaddress1(String homeaddress1) {
		this.homeaddress1 = homeaddress1;
	}

	public String getHomeaddress2() {
		return homeaddress2;
	}

	public void setHomeaddress2(String homeaddress2) {
		this.homeaddress2 = homeaddress2;
	}

	public String getHomephone() {
		return homephone;
	}

	public void setHomephone(String homephone) {
		this.homephone = homephone;
	}

	public String getHomefax() {
		return homefax;
	}

	public void setHomefax(String homefax) {
		this.homefax = homefax;
	}

	public String getCellularphone() {
		return cellularphone;
	}

	public void setCellularphone(String cellularphone) {
		this.cellularphone = cellularphone;
	}

	public String getPager() {
		return pager;
	}

	public void setPager(String pager) {
		this.pager = pager;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getFavorites1() {
		return favorites1;
	}

	public void setFavorites1(String favorites1) {
		this.favorites1 = favorites1;
	}

	public String getFavorites2() {
		return favorites2;
	}

	public void setFavorites2(String favorites2) {
		this.favorites2 = favorites2;
	}

	public String getFavorites3() {
		return favorites3;
	}

	public void setFavorites3(String favorites3) {
		this.favorites3 = favorites3;
	}

	public String getInterests() {
		return interests;
	}

	public void setInterests(String interests) {
		this.interests = interests;
	}
	
    
    

}
