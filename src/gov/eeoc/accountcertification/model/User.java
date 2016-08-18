package gov.eeoc.accountcertification.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import gov.eeoc.accountcertification.util.AccessUtil;

public class User {

	private Long directorStaffSeq;
	private Long staffSeq;
	private String firstName;
	private String lastName;
	private String email;
	private String appealsRole;
	private String privateRole;
	private String federalRole;
	private String litigationRole;
	private String outreachRole;
	private String imsAccess = "";
	private String netAccess;
	private String dmsAccess;
	private String utiliy = "0";
	private String netutiliy;
	private String dmsutility;
	private String comments;
	private String commentsldap;
	private String commentsdms;
	private boolean netstate;
	private boolean netremove;
	private boolean imsstate;
	private boolean imsremove;
	private boolean dmsstate;
	private boolean dmsremove;

	private String netremoveVal = "Remove Network Account";
	private String imsremoveVal = "Remove IMS Account";
	private String dmsremoveVal = "Remove DMS Account";

	private Director direct;

	public boolean isNetstate() {
		if (this.netAccess == null || this.netAccess.isEmpty()
				|| this.netAccess.length() == 0 || this.netAccess.equals("N"))
			return false;
		else
			return true;
	}

	public void setNetstate(boolean netstate) {
		this.netstate = netstate;
	}

	public boolean isImsstate() {
		if (this.imsAccess == null || this.imsAccess.isEmpty()
				|| this.imsAccess.length() == 0 || this.imsAccess.equals("N"))
			return false;
		else
			return true;
	}

	public void setImsstate(boolean imsstate) {
		this.imsstate = imsstate;
	}

	public boolean isDmsstate() {
		// System.out.println("isDmsstate "+ this.dmsAccess.contains("Yes"));
		if (this.dmsAccess == null || this.dmsAccess.isEmpty()
				|| this.dmsAccess.length() == 0 || this.dmsAccess.contains("N"))
			return false;
		else
			return true;
	}

	public void setDmsstate(boolean dmsstate) {
		this.dmsstate = dmsstate;
	}

	private String staffSeqDeletedVal;

	private HashMap<String, AccountCertReport> accReport = new HashMap<String, AccountCertReport>();

	private UserAccessAttributes userAccessAtrr = new UserAccessAttributes();

	public class UserAccessAttributes {

		public UserAccessAttributes() {

		}

		public UserAccessAttributes(String utility, String basic) {
			super();
			this.utility = utility;
			this.basic = basic;
		}

		public String getUtility() {
			return utility;
		}

		public void setUtility(String utility) {
			this.utility = utility;
		}

		public String getBasic() {
			return basic;
		}

		public void setBasic(String basic) {
			this.basic = basic;
		}

		private String utility;
		private String basic;

	}

	public UserAccessAttributes getUserAccessAtrr() {
		return userAccessAtrr;
	}

	public void setUserAccessAtrr(UserAccessAttributes userAccessAtrr) {
		this.userAccessAtrr = userAccessAtrr;
	}

	public User(Long staffSeq, String firstName, String lastName,
			String appealsRole, String utiliy) {
		this.staffSeq = staffSeq;
		this.firstName = firstName;
		this.lastName = lastName;
		this.appealsRole = appealsRole;
		this.utiliy = utiliy;
	}

	public User(Long staffSeq, String firstName, String lastName) {
		this.staffSeq = staffSeq;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public User(String firstName, String lastName, String email, String ldap) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.netAccess = ldap;
	}

	public User(String firstName, String lastName, String email,
			String netaccess, String netutil, boolean dmsstate) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.netAccess = netaccess;
		this.netutiliy = netutil;
		this.dmsstate = dmsstate;
	}

	public User(String firstName, String lastName, String email,
			String dmsaccess, boolean Dmsste) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.dmsAccess = dmsaccess;
		this.dmsstate = Dmsste;
	}

	public User() {

	}

	public String getCommentsldap() {
		return commentsldap;
	}

	public void setCommentsldap(String commentsldap) {
		this.commentsldap = commentsldap;
	}

	public String getCommentsdms() {
		return commentsdms;
	}

	public void setCommentsdms(String commentsdms) {
		this.commentsdms = commentsdms;
	}

	public Long getDirectorStaffSeq() {
		return directorStaffSeq;
	}

	public void setDirectorStaffSeq(Long directorStaffSeq) {
		this.directorStaffSeq = directorStaffSeq;
	}

	public Long getStaffSeq() {
		return staffSeq;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setStaffSeq(Long staffSeq) {
		this.staffSeq = staffSeq;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
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

	public String getAppealsRole() {
		return appealsRole;
	}

	public String getUtiliy() {
		return AccessUtil.getFormattedUtility(utiliy);
	}

	public void setAppealsRole(String appealsRole) {
		this.appealsRole = appealsRole;
	}

	public void setUtiliy(String utiliy) {
		this.utiliy = utiliy;
	}

	public String getPrivateRole() {
		return privateRole;
	}

	public String getFederalRole() {
		return federalRole;
	}

	public String getLitigationRole() {
		return litigationRole;
	}

	public String getOutreachRole() {
		return outreachRole;
	}

	public void setPrivateRole(String privateRole) {
		this.privateRole = privateRole;
	}

	public void setFederalRole(String federalRole) {
		this.federalRole = federalRole;
	}

	public void setLitigationRole(String litigationRole) {
		this.litigationRole = litigationRole;
	}

	public void setOutreachRole(String outreachRole) {
		this.outreachRole = outreachRole;
	}

	public String getImsAccess() {
		return AccessUtil.getTotalImsAccess(privateRole, federalRole,
				litigationRole, outreachRole, appealsRole);
	}

	public void setImsAccess(String imsAccess) {
		this.imsstate = this.isImsstate();
		this.imsAccess = imsAccess;

	}

	public String getNetAccess() {
		return netAccess;
	}

	public void setNetAccess(String netAccess) {
		this.netAccess = netAccess;
		this.netstate = this.isNetstate();
	}

	public String getNetutiliy() {
		return netutiliy;
	}

	public void setNetutiliy(String netutiliy) {
		this.netutiliy = netutiliy;
	}

	public String getDmsAccess() {
		return dmsAccess;
	}

	public void setDmsAccess(String dmsAccess) {
		// System.out.println("Chekcing dms access "+ dmsAccess);

		this.dmsAccess = dmsAccess;

		this.dmsstate = this.isDmsstate();
	}

	public String getDmsutility() {
		return dmsutility;
	}

	public void setDmsutility(String dmsutility) {
		this.dmsutility = dmsutility;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getStaffSeqDeletedVal() {
		return staffSeqDeletedVal;
	}

	public void setStaffSeqDeletedVal(String staffSeqDeletedVal) {
		this.staffSeqDeletedVal = staffSeqDeletedVal;
	}

	public HashMap<String, AccountCertReport> getAccReport() {
		return accReport;
	}

	public void setAccReport(HashMap<String, AccountCertReport> accReport) {
		this.accReport = accReport;
	}

	public Director getDirect() {
		return direct;
	}

	public void setDirect(Director direct) {
		this.direct = direct;
	}

	public boolean isImsremove() {
		return imsremove;
	}

	public void setImsremove(boolean imsremove) {
		this.imsremove = imsremove;
	}

	public boolean isNetremove() {
		return netremove;
	}

	public void setNetremove(boolean netremove) {
		this.netremove = netremove;
	}

	public boolean isDmsremove() {
		return dmsremove;
	}

	public void setDmsremove(boolean dmsremove) {
		this.dmsremove = dmsremove;
	}

	public String getNetremoveVal() {
		return netremoveVal;
	}

	public void setNetremoveVal(String netremoveVal) {
		this.netremoveVal = netremoveVal;
	}

	public String getImsremoveVal() {
		return imsremoveVal;
	}

	public void setImsremoveVal(String imsremoveVal) {
		this.imsremoveVal = imsremoveVal;
	}

	public String getDmsremoveVal() {
		return dmsremoveVal;
	}

	public void setDmsremoveVal(String dmsremoveVal) {
		this.dmsremoveVal = dmsremoveVal;
	}

	@Override
	public int hashCode() {
		System.out.println(" hashcode called");
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		System.out.println(" equals called " + obj.toString());

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

}
