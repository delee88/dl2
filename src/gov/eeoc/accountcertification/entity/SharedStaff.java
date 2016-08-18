package gov.eeoc.accountcertification.entity;

import java.io.Serializable;
import java.util.Set;
import java.util.Date;
import java.util.Set;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author VPATEL
 */
@Entity
@Table(name = "SHARED_STAFF")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SharedStaff.findAll", query = "SELECT s FROM SharedStaff s"),
    @NamedQuery(name = "SharedStaff.findByStaffSeq", query = "SELECT s FROM SharedStaff s WHERE s.staffSeq = :staffSeq"),
    @NamedQuery(name = "SharedStaff.findByEmailAddress", query = "SELECT s FROM SharedStaff s WHERE s.emailAddress = :emailAddress"),
    @NamedQuery(name = "SharedStaff.findByOfficeCode", query = "SELECT s FROM SharedStaff s WHERE s.officeCode = :officeCode"),
    @NamedQuery(name = "SharedStaff.findBySelectedOfficeCode", query = "SELECT s FROM SharedStaff s WHERE s.officeCode in :officeCodeList"),
    @NamedQuery(name = "SharedStaff.findActiveByOfficeCode", query = "SELECT s FROM SharedStaff s WHERE s.officeCode = :officeCode and s.status = :status order by s.lastName,s.firstName"),
    @NamedQuery(name = "SharedStaff.findByFunction", query = "SELECT s FROM SharedStaff s WHERE s.function = :function"),
    })
public class SharedStaff implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "STAFF_SEQ")
    private Long staffSeq;
    @Column(name = "ASSIGNED_INITIALS")
    private String assignedInitials;
    @Column(name = "SOCIAL_SECURITY_NUMBER")
    private String socialSecurityNumber;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Basic(optional = false)
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "MIDDLE_INITIAL")
    private String middleInitial;
    @Column(name = "NAME_PREFIX")
    private String namePrefix;
    @Column(name = "NAME_SUFFIX")
    private String nameSuffix;
    @Column(name = "WORK_PHONE")
    private String workPhone;
    @Column(name = "EXTENSION")
    private String extension;
    @Column(name = "EMAIL_ADDRESS")
    private String emailAddress;
    @Basic(optional = false)
    @Column(name = "OFFICE_CODE")
    private String officeCode;
    @Column(name = "FUNCTION")
    private String function;
    @Column(name = "UNIT")
    private String unit;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "ACTING")
    private String acting;
    @Basic(optional = false)
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Basic(optional = false)
    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Basic(optional = false)
    @Column(name = "MAINTAINED_BY")
    private String maintainedBy;
    @Basic(optional = false)
    @Column(name = "MAINTAINED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date maintainedDate;
    @Column(name = "SUPERVISOR")
    private String supervisor;
    @Column(name = "FAX")
    private String fax;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "LOCAL_OFFICE")
    private String localOffice;
    @Column(name = "FA_FU")
    private String faFu;
    @Column(name = "HIGH_CONTRAST")
    private String highContrast;
    @OneToMany(mappedBy = "supervisorSeq", fetch = FetchType.EAGER)
    private Set<SharedStaff> sharedStaffSet;
    @JoinColumn(name = "SUPERVISOR_SEQ", referencedColumnName = "STAFF_SEQ")
    @ManyToOne(fetch = FetchType.EAGER)
    private SharedStaff supervisorSeq;

    public SharedStaff() {
    }

    public SharedStaff(Long staffSeq) {
        this.staffSeq = staffSeq;
    }

    public SharedStaff(Long staffSeq, String lastName, String officeCode, String createdBy, Date createdDate, String maintainedBy, Date maintainedDate) {
        this.staffSeq = staffSeq;
        this.lastName = lastName;
        this.officeCode = officeCode;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.maintainedBy = maintainedBy;
        this.maintainedDate = maintainedDate;
    }
    @XmlTransient
    public Long getStaffSeq() {
        return staffSeq;
    }

    public void setStaffSeq(Long staffSeq) {
        this.staffSeq = staffSeq;
    }
    @XmlTransient
    public String getAssignedInitials() {
        return assignedInitials == null ? "" : assignedInitials;
    }

    public void setAssignedInitials(String assignedInitials) {
        this.assignedInitials = assignedInitials;
    }
    @XmlTransient
    public String getSocialSecurityNumber() {
        return socialSecurityNumber == null ? "" : socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public String getFirstName() {
        return firstName == null ? "" : firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName == null ? "" : lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleInitial() {
        return middleInitial == null ? "" : middleInitial;
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    public String getNamePrefix() {
        return namePrefix == null ? "" : namePrefix;
    }

    public void setNamePrefix(String namePrefix) {
        this.namePrefix = namePrefix;
    }

    public String getNameSuffix() {
        return nameSuffix == null ? "" : nameSuffix;
    }

    public void setNameSuffix(String nameSuffix) {
        this.nameSuffix = nameSuffix;
    }

    public String getWorkPhone() {
        return workPhone == null ? "" : workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getExtension() {
        return extension == null ? "" : extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getEmailAddress() {
        return emailAddress == null ? "" : emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getOfficeCode() {
        return officeCode == null ? "" : officeCode;
    }

    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode;
    }

    public String getFunction() {
        return function == null ? "" : function;
    }

    public void setFunction(String function) {
        this.function = function;
    }
    @XmlTransient
    public String getUnit() {
        return unit == null ? "" : unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
    @XmlTransient
    public String getStatus() {
        return status == null ? "" : status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    @XmlTransient
    public String getActing() {
        return acting == null ? "" : acting;
    }

    public void setActing(String acting) {
        this.acting = acting;
    }
    @XmlTransient
    public String getCreatedBy() {
        return createdBy == null ? "" : createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    @XmlTransient
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    @XmlTransient
    public String getMaintainedBy() {
        return maintainedBy == null ? "" : maintainedBy;
    }

    public void setMaintainedBy(String maintainedBy) {
        this.maintainedBy = maintainedBy;
    }
    @XmlTransient
    public Date getMaintainedDate() {
        return maintainedDate;
    }

    public void setMaintainedDate(Date maintainedDate) {
        this.maintainedDate = maintainedDate;
    }
    @XmlTransient
    public String getSupervisor() {
        return supervisor == null ? "" : supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getFax() {
        return fax == null ? "" : fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    @XmlTransient
    public String getLocalOffice() {
        return localOffice == null ? "" : localOffice;
    }

    public void setLocalOffice(String localOffice) {
        this.localOffice = localOffice;
    }
    @XmlTransient
    public String getFaFu() {
        return faFu == null ? "" : faFu;
    }

    public void setFaFu(String faFu) {
        this.faFu = faFu;
    }
    
    public String getHighContrast() {
		return highContrast;
	}

	public void setHighContrast(String highContrast) {
		this.highContrast = highContrast;
	}

	@XmlTransient
    public Set<SharedStaff> getSharedStaffSet() {
        return sharedStaffSet;
    }

    public void setSharedStaffSet(Set<SharedStaff> sharedStaffSet) {
        this.sharedStaffSet = sharedStaffSet;
    }

    public SharedStaff getSupervisorInfo() {
        return supervisorSeq;
    }

    public void setSupervisorInfo(SharedStaff supervisorSeq) {
        this.supervisorSeq = supervisorSeq;
    }
/*
    @XmlTransient
    public Set<ImsChargeInquiry> getImsChargeInquirySet() {
        return imsChargeInquirySet;
    }

    public void setImsChargeInquirySet(Set<ImsChargeInquiry> imsChargeInquirySet) {
        this.imsChargeInquirySet = imsChargeInquirySet;
    }

    @XmlTransient
    public Set<ImsChargeInquiry> getImsChargeInquirySet1() {
        return imsChargeInquirySet1;
    }

    public void setImsChargeInquirySet1(Set<ImsChargeInquiry> imsChargeInquirySet1) {
        this.imsChargeInquirySet1 = imsChargeInquirySet1;
    }

    @XmlTransient
    public Set<ImsChargeInquiry> getImsChargeInquirySet2() {
        return imsChargeInquirySet2;
    }

    public void setImsChargeInquirySet2(Set<ImsChargeInquiry> imsChargeInquirySet2) {
        this.imsChargeInquirySet2 = imsChargeInquirySet2;
    }
*/
    /*
    @XmlTransient
    public Set<LegAction> getLegActionSet() {
        return legActionSet;
    }

    public void setLegActionSet(Set<LegAction> legActionSet) {
        this.legActionSet = legActionSet;
    }

    @XmlTransient
    public Set<LegcaseShrstfRltnshp> getLegcaseShrstfRltnshpSet() {
        return legcaseShrstfRltnshpSet;
    }

    public void setLegcaseShrstfRltnshpSet(Set<LegcaseShrstfRltnshp> legcaseShrstfRltnshpSet) {
        this.legcaseShrstfRltnshpSet = legcaseShrstfRltnshpSet;
    }
*/
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (staffSeq != null ? staffSeq.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SharedStaff)) {
            return false;
        }
        SharedStaff other = (SharedStaff) object;
        if ((this.staffSeq == null && other.staffSeq != null) || (this.staffSeq != null && !this.staffSeq.equals(other.staffSeq))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.eeoc.accountcertification.entity.SharedStaff[ staffSeq=" + staffSeq + " ]";
    }
    
}
