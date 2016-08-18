package gov.eeoc.accountcertification.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "SHARED_OFFICE_INFO")
@XmlRootElement
public class SharedOfficeInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name="SHARED_OFFICE_INFO_OFFICESEQID_GENERATOR", sequenceName="OFFICESEQID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SHARED_OFFICE_INFO_OFFICESEQID_GENERATOR")
	
	@Basic(optional = false)
	@Column(name = "OFFICE_CODE")
	private String officeCode;
	@Column(name = "ADDRESS_LINE_1")
	private String addressLine1;
	@Column(name = "ADDRESS_LINE_2")
	private String addressLine2;
	@Column(name = "SPECIAL_MAILING_ADDRESS")
	private String specialMailingAddress;
	@Basic(optional = false)
	@Column(name = "CITY")
	private String city;
	@Basic(optional = false)
	@Column(name = "STATE")
	private String state;
	@Basic(optional = false)
	@Column(name = "ZIP_CODE")
	private String zipCode;
	@Column(name = "MAIN_OFFICE_VOICE_TELEPHONE")
	private String mainOfficeVoiceTelephone;
	@Column(name = "OFFICE_DIRECTOR_VOICE_TELEPHON")
	private String officeDirectorVoiceTelephon;
	@Basic(optional = false)
	@Column(name = "OFFICE_SEQ")
	private long officeSeq;
	@Column(name = "FAX")
	private String fax;
	@Column(name ="OFFICE_DIRECTOR_TITLE")
	private String officeDirectorTitle;
	@Column(name = "TTY")
	private String tty;
	@Column(name = "OFFICE_HOURS")
	private String officeHours;
	@Basic(optional = false)
	@Column(name = "DISTRICT_OFFICE")
	private String districtOffice;
	@Column(name = "DIRECTOR_STAFF_SEQ")
	private long directorStaffSeq;
	@Column(name = "ADR_STAFF_SEQ")
	private Long adrStaffSeq;
	@Column(name = "RA_STAFF_SEQ")
	private Long raStaffSeq;
	@Column(name = "ADR_FAX")
	private String adrFax;
	@Column(name = "FRM_WP_TYPE")
	private String frmWpType;
	@Column(name = "FRM_WP_EXECUTABLE")
	private String frmWpExecutable;
	@Column(name = "FRM_WP_MACRO")
	private String frmWpMacro;
	@Column(name = "FRM_TEMPLATE_DIR")
	private String frmTemplateDir;
	@Column(name = "FRM_WP_EXTENSION")
	private String frmWpExtension;
	@Column(name ="TOLL_FREE_PHONE")
	private String tollFreePhone;
	@Column(name = "HEARINGS_UNIT")
	private String hearingsUnit;
	@Column(name = "STATE_AGENCY")
	private String stateAgency;
	@Column(name = "CERTIFICATION_DATE")
	private Date certificationDate;
	
	public String getOfficeCode() {
		return officeCode;
	}
	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public String getSpecialMailingAddress() {
		return specialMailingAddress;
	}
	public void setSpecialMailingAddress(String specialMailingAddress) {
		this.specialMailingAddress = specialMailingAddress;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getMainOfficeVoiceTelephone() {
		return mainOfficeVoiceTelephone;
	}
	public void setMainOfficeVoiceTelephone(String mainOfficeVoiceTelephone) {
		this.mainOfficeVoiceTelephone = mainOfficeVoiceTelephone;
	}
	public String getOfficeDirectorVoiceTelephon() {
		return officeDirectorVoiceTelephon;
	}
	public void setOfficeDirectorVoiceTelephon(String officeDirectorVoiceTelephon) {
		this.officeDirectorVoiceTelephon = officeDirectorVoiceTelephon;
	}
	public long getOfficeSeq() {
		return officeSeq;
	}
	public void setOfficeSeq(long officeSeq) {
		this.officeSeq = officeSeq;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getOfficeDirectorTitle() {
		return officeDirectorTitle;
	}
	public void setOfficeDirectorTitle(String officeDirectorTitle) {
		this.officeDirectorTitle = officeDirectorTitle;
	}
	public String getTty() {
		return tty;
	}
	public void setTty(String tty) {
		this.tty = tty;
	}
	public String getOfficeHours() {
		return officeHours;
	}
	public void setOfficeHours(String officeHours) {
		this.officeHours = officeHours;
	}
	public String getDistrictOffice() {
		return districtOffice;
	}
	public void setDistrictOffice(String districtOffice) {
		this.districtOffice = districtOffice;
	}
	public long getDirectorStaffSeq() {
		return directorStaffSeq;
	}
	public void setDirectorStaffSeq(long directorStaffSeq) {
		this.directorStaffSeq = directorStaffSeq;
	}
	public Long getAdrStaffSeq() {
		return adrStaffSeq;
	}
	public void setAdrStaffSeq(Long adrStaffSeq) {
		this.adrStaffSeq = adrStaffSeq;
	}
	public Long getRaStaffSeq() {
		return raStaffSeq;
	}
	public void setRaStaffSeq(Long raStaffSeq) {
		this.raStaffSeq = raStaffSeq;
	}
	public String getAdrFax() {
		return adrFax;
	}
	public void setAdrFax(String adrFax) {
		this.adrFax = adrFax;
	}
	public String getFrmWpType() {
		return frmWpType;
	}
	public void setFrmWpType(String frmWpType) {
		this.frmWpType = frmWpType;
	}
	public String getFrmWpExecutable() {
		return frmWpExecutable;
	}
	public void setFrmWpExecutable(String frmWpExecutable) {
		this.frmWpExecutable = frmWpExecutable;
	}
	public String getFrmWpMacro() {
		return frmWpMacro;
	}
	public void setFrmWpMacro(String frmWpMacro) {
		this.frmWpMacro = frmWpMacro;
	}
	public String getFrmTemplateDir() {
		return frmTemplateDir;
	}
	public void setFrmTemplateDir(String frmTemplateDir) {
		this.frmTemplateDir = frmTemplateDir;
	}
	public String getFrmWpExtension() {
		return frmWpExtension;
	}
	public void setFrmWpExtension(String frmWpExtension) {
		this.frmWpExtension = frmWpExtension;
	}
	public String getTollFreePhone() {
		return tollFreePhone;
	}
	public void setTollFreePhone(String tollFreePhone) {
		this.tollFreePhone = tollFreePhone;
	}
	public String getHearingsUnit() {
		return hearingsUnit;
	}
	public void setHearingsUnit(String hearingsUnit) {
		this.hearingsUnit = hearingsUnit;
	}
	public String getStateAgency() {
		return stateAgency;
	}
	public void setStateAgency(String stateAgency) {
		this.stateAgency = stateAgency;
	}
	public Date getCertificationDate() {
		return certificationDate;
	}
	public void setCertificationDate(Date certificationDate) {
		this.certificationDate = certificationDate;
	}
}
	