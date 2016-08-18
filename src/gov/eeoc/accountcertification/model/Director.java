package gov.eeoc.accountcertification.model;

import java.util.Date;

public class Director {
	private Long directorStaffSeq;
	private String directorName;
	private String officeCode;
	private String officeDescription;
	private String directorTitle;
	private String email;
	private Date certificationDate;
	private String password;
    private String fepaDirectorVal;
    private String urlLink;
	public Director(Long directorStaffSeq, String directorName,
			String officeCode, String officeDescription) {
		this.directorStaffSeq = directorStaffSeq;
		this.directorName = directorName;
		this.officeCode = officeCode;
		this.officeDescription = officeDescription;
	}

	public Director(String officeCode, String email) {
		this.officeCode = officeCode;
		this.email = email;
	}

	public Director() {

	}

	public Long getDirectorStaffSeq() {
		return directorStaffSeq;
	}

	public String getDirectorName() {
		return directorName;
	}

	public String getOfficeCode() {
		return officeCode;
	}

	public String getOfficeDescription() {
		return officeDescription;
	}

	public void setDirectorStaffSeq(Long directorStaffSeq) {
		this.directorStaffSeq = directorStaffSeq;
	}

	public void setDirectorName(String directorName) {
		this.directorName = directorName;
	}

	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	public void setOfficeDescription(String officeDescription) {
		this.officeDescription = officeDescription;
	}

	public String getDirectorTitle() {
		return directorTitle;
	}

	public void setDirectorTitle(String directorTitle) {
		this.directorTitle = directorTitle;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCertificationDate() {
		return certificationDate;
	}

	public void setCertificationDate(Date certificationDate) {
		this.certificationDate = certificationDate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFepaDirectorVal() {
		return fepaDirectorVal;
	}

	public void setFepaDirectorVal(String fepaDirector) {
		this.fepaDirectorVal = fepaDirector;
	}

	public String getUrlLink() {
		return urlLink;
	}

	public void setUrlLink(String urlLink) {
		this.urlLink = urlLink;
	}

}