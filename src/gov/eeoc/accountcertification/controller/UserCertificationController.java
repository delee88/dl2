package gov.eeoc.accountcertification.controller;

import gov.eeoc.accountcertification.CertificationConstants;
import gov.eeoc.accountcertification.service.CertificationService;
import gov.eeoc.accountcertification.util.CertificationUtil;
import gov.eeoc.accountcertification.util.ConvertUserMapToList;
import gov.eeoc.accountcertification.util.DmsEmailMap;
import gov.eeoc.accountcertification.util.EncryptionUtil;
import gov.eeoc.accountcertification.util.HeadQuarterLdapMap;
import gov.eeoc.accountcertification.util.ImsUserMap;
import gov.eeoc.accountcertification.util.LdapPersonHelperEmail;
import gov.eeoc.accountcertification.util.LdapPersonHelperUserId;
import gov.eeoc.accountcertification.util.UserHelper;
import gov.eeoc.accountcertification.util.UserOfficeCodeHelper;
import gov.eeoc.accountcertification.model.AccountCertReport;
import gov.eeoc.accountcertification.model.DMSUsers;
import gov.eeoc.accountcertification.model.Director;
import gov.eeoc.accountcertification.model.Person;
import gov.eeoc.accountcertification.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.SortedSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitHint;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

@ManagedBean(name = "userCertificationBean")
@ViewScoped
public class UserCertificationController implements Serializable {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger
			.getLogger(UserCertificationController.class);

	@EJB
	CertificationService certificationService;

	// @ManagedProperty("#{param.code}")

	private String logo = "EEOC_SEAL_Logo.bmp";

	private StreamedContent image;

	private String[] selectedSystem;

	private String code;

	private String hqdeptcode;

	private String ldapcitycode;

	private String officeCode;

	private List<User> pendingUsers;

	private List<User> filteredPendingUsers;

	private Director selDirector = new Director();

	public Director getSelDirector() {
		return selDirector;
	}

	public void setSelDirector(Director selDirector) {
		this.selDirector = selDirector;
	}

	private Director director = new Director();

	private User selUserForRemoval = new User();

	private HashMap<String, User> listOfIMSRemovedUsers = new HashMap<String, User>();

	public HashMap<String, User> getListOfIMSRemovedUsers() {
		return listOfIMSRemovedUsers;
	}

	public void setListOfIMSRemovedUsers(
			HashMap<String, User> listOfIMSRemovedUsers) {
		this.listOfIMSRemovedUsers = listOfIMSRemovedUsers;
	}

	private long totalCount;

	public List<AccountCertReport> getUserAccntRemove() {
		userAccntRemove = getCurrentRemovedListofUsers();

		// log.info("Test the size of removed list AccountCertReport "
		// +userAccntRemove.size());
		return userAccntRemove;
	}

	public void setUserAccntRemove(ArrayList<AccountCertReport> userRemove) {
		this.userAccntRemove = userRemove;
	}

	private List<AccountCertReport> userAccntRemove = new ArrayList<AccountCertReport>();

	private boolean enabled;

	private int pendingUsersCount;

	private HashMap<String, User> removeMap = new HashMap<String, User>();

	public HashMap<String, User> getRemoveUserMap() {
		return removeMap;
	}

	public void setRemoveUserMap(HashMap<String, User> removeMap) {
		this.removeMap = removeMap;
	}

	private ImsUserMap imsUserMap = new ImsUserMap();

	private List<Person> ldapPersonUserList = new ArrayList<Person>();

	public List<Person> getLdapPersonUserList() {
		return ldapPersonUserList;
	}

	public void setLdapPersonUserList(List<Person> tlist) {
		this.ldapPersonUserList = tlist;
	}

	private List<User> imsUserList = new ArrayList<User>();

	public void setIMSUserList(List<User> ulist) {
		this.imsUserList = ulist;
	}

	public List<User> getIMSUserList() {
		return this.imsUserList;
	}

	private String headQuarterOfficeCode;

	public String getHeadQuarterOfficeCode() {
		return headQuarterOfficeCode;
	}

	public void setHeadQuarterOfficeCode(String headQuarterOfficeCode) {
		this.headQuarterOfficeCode = headQuarterOfficeCode;
	}

	final private static String USERID = "USERID";
	final private static String EMAIL = "EMAIL";

	public String[] getSelectedSystem() {
		return selectedSystem;
	}

	public void setSelectedSystem(String[] selectedSystem) {
		this.selectedSystem = selectedSystem;
	}

	public void createLdapPersonMapByAttrType(List<Person> personUserListLdap,
			String types) {
		log.info("Size of list setPersonUserLdapListAndMapType "
				+ personUserListLdap.size() + " types = " + types);
		this.ldapPersonUserList = personUserListLdap; // personUserListLdap.subList(0,
														// 300);
		if (types.equals(USERID)) {

			for (Person per : this.ldapPersonUserList) {
				if (!ldapPersonUserIdMap.containsValue(per.getUserId().trim()
						.toUpperCase())
						&& per.getUserId().trim().toUpperCase() != null
						&& !per.getUserId().trim().toUpperCase().equals("")) {
					ldapPersonUserIdMap.put(per.getUserId().trim()
							.toUpperCase(), per);
				} else {
					log.info("These are the duplicate keys, nulls, or blanks excluded in the map "
							+ per.getUserId().trim().toUpperCase());
				}
			}
			log.info("Size of Person UserId Map " + ldapPersonUserIdMap.size());
		}

		if (types.equals(EMAIL)) {
			log.info("Size of ldap list before creating Email map  "
					+ personUserListLdap.size());
			for (Person per : personUserListLdap) {
				// log.info("List of emails from person "+ per.getEmail());
				if (!ldapPersonEmailMap.containsValue(per.getEmail().trim()
						.toUpperCase())
						&& per.getEmail().trim().toUpperCase() != null
						&& !per.getEmail().trim().toUpperCase().equals("")) {
					// get user info from email
					String temail = this.certificationService
							.getContactInfoFromEmail(per.getEmail());
					if (!temail.equals(BLANK)) {
						ldapPersonEmailMap.put(temail, per);
					} else {
						ldapPersonEmailMap.put(
								"blankemail." + per.getFirstName() + "."
										+ per.getLastName(), per);
					}

				} else {
					log.info("These are the duplicate keys, nulls, or blanks excluded in the map "
							+ per.getEmail().trim().toUpperCase());
				}
			}
		}

		// log.info("Size of Person Ldap List " + personUserListLdap.size());
		log.info("Size of Person Email Map After " + ldapPersonEmailMap.size());

		// personUserListLdap;
	}

	public void cancelLdapRemove(ActionEvent event) {

		// log.info("cancelLdapRemove Component Id " +
		// event.getComponent().getFamily() );
		log.info("cancelLdapRemove this.selUserForRemoval "
				+ this.selUserForRemoval.getEmail());

		boolean eval = false;
		User temp;
		if (getRemoveUserMap().containsKey(this.selUserForRemoval.getEmail())) {
			temp = getRemoveUserMap().get(this.selUserForRemoval.getEmail());
			if (temp.getAccReport().containsKey("LDAP")) {
				temp.getAccReport().remove("LDAP");
				getRemoveUserMap().put(this.selUserForRemoval.getEmail(), temp);
				eval = true;
			}

		}

		if (imsUserMap.getImsUserMap().containsKey(
				this.selUserForRemoval.getEmail())
				&& eval) {
			User us = imsUserMap.getImsUserMap().get(
					this.selUserForRemoval.getEmail());
			us.setNetremove(false);
			us.setNetremoveVal("Remove Network Account");
			this.selUserForRemoval.setNetremoveVal("Remove Network Account");
			imsUserMap.getImsUserMap().put(this.selUserForRemoval.getEmail(),
					us);
		}

		// org.primefaces.component.datatable.DataTable acctab;
		// acctab =
		// (org.primefaces.component.datatable.DataTable)event.getComponent().findComponent("pendingusers");
		// log.info("This is the event from the table -->  "
		// +acctab.getDefaultEventName());

	}

	public List<AccountCertReport> getCurrentRemovedListofUsers() {

		ArrayList<AccountCertReport> accntCertList = new ArrayList<AccountCertReport>();

		ArrayList<User> usList = new ArrayList<User>();

		for (int x = 0; x < getRemoveUserMap().values().toArray().length; x++) {
			User u1 = (User) getRemoveUserMap().values().toArray()[x];
			usList.add(u1);
		}

		for (int x = 0; x < usList.size(); x++) {
			for (int i = 0; i < usList.get(x).getAccReport().values().toArray().length; i++) {
				AccountCertReport acr = (AccountCertReport) usList.get(x)
						.getAccReport().values().toArray()[i];
				accntCertList.add(acr);
			}
		}

		return accntCertList;

	}

	public void updateLdapRemoveList(ActionEvent event) {

		log.info("  updateLdapRemoveList ");

		// log.info("Check the value of clear " +this.getClearAll()[0] );

		Date d = new Date();
		int ind = 0;
		int indValue = 0;
		if (getRemoveUserMap().containsKey(this.selUserForRemoval.getEmail())) {
			User temp = getRemoveUserMap().get(
					this.selUserForRemoval.getEmail());
			// temp.setNetremove(true);
			ind = this.getDirector().getEmail().indexOf("@");
			if (ind != -1) {
				indValue = ind - 1;
				temp.getAccReport().put(
						"LDAP",
						new AccountCertReport(this.selUserForRemoval
								.getFirstName(), this.selUserForRemoval
								.getLastName(), this.selUserForRemoval
								.getEmail() + "@EEOC.GOV", this.director
								.getEmail().substring(0, ind), d, "Y", "LDAP",
								this.selUserForRemoval.getCommentsldap()));

				getRemoveUserMap().put(this.selUserForRemoval.getEmail(), temp);

				log.info("update the list "
						+ "  "
						+ getRemoveUserMap()
								.get(this.selUserForRemoval.getEmail())
								.getAccReport().get("LDAP").getFirstName());
				log.info("update the list "
						+ "  "
						+ getRemoveUserMap()
								.get(this.selUserForRemoval.getEmail())
								.getAccReport().get("LDAP").getLastName());
				log.info("update the list "
						+ "  "
						+ getRemoveUserMap()
								.get(this.selUserForRemoval.getEmail())
								.getAccReport().get("LDAP").getApplication());
			} else {
				temp.getAccReport().put(
						"LDAP",
						new AccountCertReport(this.selUserForRemoval
								.getFirstName(), this.selUserForRemoval
								.getLastName(), this.selUserForRemoval
								.getEmail() + "@EEOC.GOV", "No Director email",
								d, "Y", "LDAP", this.selUserForRemoval
										.getCommentsldap()));

				getRemoveUserMap().put(this.selUserForRemoval.getEmail(), temp);
				log.info("update the list "
						+ "  "
						+ getRemoveUserMap()
								.get(this.selUserForRemoval.getEmail())
								.getAccReport().get("LDAP").getFirstName());
				log.info("update the list "
						+ "  "
						+ getRemoveUserMap()
								.get(this.selUserForRemoval.getEmail())
								.getAccReport().get("LDAP").getLastName());
				log.info("update the list "
						+ "  "
						+ getRemoveUserMap()
								.get(this.selUserForRemoval.getEmail())
								.getAccReport().get("LDAP").getApplication());
			}

		} else {

			ind = this.getDirector().getEmail().indexOf("@");
			if (ind != -1) {
				indValue = ind - 1;
				this.selUserForRemoval.getAccReport().put(
						"LDAP",
						new AccountCertReport(this.selUserForRemoval
								.getFirstName(), this.selUserForRemoval
								.getLastName(), this.selUserForRemoval
								.getEmail() + "@EEOC.GOV", this.director
								.getEmail().substring(0, ind), d, "Y", "LDAP",
								this.selUserForRemoval.getCommentsldap()));

				// this.selUserForRemoval.setNetremove(true);
				getRemoveUserMap().put(this.selUserForRemoval.getEmail(),
						this.selUserForRemoval);

				log.info("update the list "
						+ "  "
						+ getRemoveUserMap()
								.get(this.selUserForRemoval.getEmail())
								.getAccReport().get("LDAP").getFirstName());
				log.info("update the list "
						+ "  "
						+ getRemoveUserMap()
								.get(this.selUserForRemoval.getEmail())
								.getAccReport().get("LDAP").getLastName());
				log.info("update the list "
						+ "  "
						+ getRemoveUserMap()
								.get(this.selUserForRemoval.getEmail())
								.getAccReport().get("LDAP").getApplication());
			} else {
				this.selUserForRemoval.getAccReport().put(
						"LDAP",
						new AccountCertReport(this.selUserForRemoval
								.getFirstName(), this.selUserForRemoval
								.getLastName(), this.selUserForRemoval
								.getEmail() + "@EEOC.GOV", "No Director email",
								d, "Y", "LDAP", this.selUserForRemoval
										.getCommentsldap()));

				// this.selUserForRemoval.setNetremove(true);
				getRemoveUserMap().put(this.selUserForRemoval.getEmail(),
						this.selUserForRemoval);

				log.info("update the list "
						+ "  "
						+ getRemoveUserMap()
								.get(this.selUserForRemoval.getEmail())
								.getAccReport().get("LDAP").getFirstName());
				log.info("update the list "
						+ "  "
						+ getRemoveUserMap()
								.get(this.selUserForRemoval.getEmail())
								.getAccReport().get("LDAP").getLastName());
				log.info("update the list "
						+ "  "
						+ getRemoveUserMap()
								.get(this.selUserForRemoval.getEmail())
								.getAccReport().get("LDAP").getApplication());

			}
		}

		if (imsUserMap.getImsUserMap().containsKey(
				this.selUserForRemoval.getEmail())) {
			User us = imsUserMap.getImsUserMap().get(
					this.selUserForRemoval.getEmail());
			us.setNetremove(true);
			us.setNetremoveVal("Removed");
			this.selUserForRemoval.setNetremoveVal("NET Removed");
			imsUserMap.getImsUserMap().put(this.selUserForRemoval.getEmail(),
					us);
		}
		log.info("Remove size after removal " + getRemoveUserMap().size()
				+ " Size of AccountCertReport list "
				+ getUserAccntRemove().size());
	}

	public void cancelDmsRemove(ActionEvent event) {

		// log.info("cancelImsRemove Component Id " +
		// event.getComponent().getFamily() );
		log.info("cancelDmsRemove this.selUserForRemoval "
				+ this.selUserForRemoval.getEmail());

		boolean eval = false;
		User temp;
		if (getRemoveUserMap().containsKey(this.selUserForRemoval.getEmail())) {
			temp = getRemoveUserMap().get(this.selUserForRemoval.getEmail());
			if (temp.getAccReport().containsKey("DMS")) {
				temp.getAccReport().remove("DMS");
				getRemoveUserMap().put(this.selUserForRemoval.getEmail(), temp);
				eval = true;
			}

		}

		if (imsUserMap.getImsUserMap().containsKey(
				this.selUserForRemoval.getEmail())
				&& eval) {
			User us = imsUserMap.getImsUserMap().get(
					this.selUserForRemoval.getEmail());
			us.setDmsremove(false);
			us.setDmsremoveVal("Remove DMS Account");
			this.selUserForRemoval.setDmsremoveVal("Remove DMS Account");
			imsUserMap.getImsUserMap().put(this.selUserForRemoval.getEmail(),
					us);
		}

		log.info(" cancelDmsRemove this.selUserForRemoval DMS remove value "
				+ imsUserMap.getImsUserMap()
						.get(this.selUserForRemoval.getEmail()).isDmsremove());

		// org.primefaces.component.datatable.DataTable acctab;
		// acctab =
		// (org.primefaces.component.datatable.DataTable)event.getComponent().findComponent("pendingusers");
		// log.info("This is the event from the table -->  "
		// +acctab.getDefaultEventName());

	}

	public void updateDmsRemoveList(ActionEvent event) {
		Date d = new Date();
		int ind = 0;
		int indValue = 0;
		if (getRemoveUserMap().containsKey(this.selUserForRemoval.getEmail())) {
			User temp = getRemoveUserMap().get(
					this.selUserForRemoval.getEmail());

			// temp.setDmsremove(true);

			ind = this.getDirector().getEmail().indexOf("@");
			if (ind != -1) {
				indValue = ind - 1;
				temp.getAccReport().put(
						"DMS",
						new AccountCertReport(this.selUserForRemoval
								.getFirstName(), this.selUserForRemoval
								.getLastName(), this.selUserForRemoval
								.getEmail() + "@EEOC.GOV", this.getDirector()
								.getEmail().substring(0, ind), d, "Y", "DMS",
								this.selUserForRemoval.getCommentsdms()));
				getRemoveUserMap().put(this.selUserForRemoval.getEmail(), temp);
			} else {
				temp.getAccReport().put(
						"DMS",
						new AccountCertReport(this.selUserForRemoval
								.getFirstName(), this.selUserForRemoval
								.getLastName(), this.selUserForRemoval
								.getEmail() + "@EEOC.GOV",
								"No director Email ", d, "Y", "DMS",
								this.selUserForRemoval.getCommentsdms()));
				getRemoveUserMap().put(this.selUserForRemoval.getEmail(), temp);
			}

		} else {

			ind = this.getDirector().getEmail().indexOf("@");
			if (ind != -1) {
				indValue = ind - 1;
				this.selUserForRemoval.getAccReport().put(
						"DMS",
						new AccountCertReport(this.selUserForRemoval
								.getFirstName(), this.selUserForRemoval
								.getLastName(), this.selUserForRemoval
								.getEmail() + "@EEOC.GOV", this.getDirector()
								.getEmail().substring(0, ind), d, "Y", "DMS",
								this.selUserForRemoval.getCommentsdms()));

				// this.selUserForRemoval.setDmsremove(true);
				getRemoveUserMap().put(this.selUserForRemoval.getEmail(),
						this.selUserForRemoval);

			} else {
				this.selUserForRemoval.getAccReport().put(
						"DMS",
						new AccountCertReport(this.selUserForRemoval
								.getFirstName(), this.selUserForRemoval
								.getLastName(), this.selUserForRemoval
								.getEmail() + "@EEOC.GOV",
								" No Director Email ", d, "Y", "DMS",
								this.selUserForRemoval.getCommentsdms()));

				// this.selUserForRemoval.setDmsremove(true);
				getRemoveUserMap().put(this.selUserForRemoval.getEmail(),
						this.selUserForRemoval);
			}

		}

		if (imsUserMap.getImsUserMap().containsKey(
				this.selUserForRemoval.getEmail())) {
			User us = imsUserMap.getImsUserMap().get(
					this.selUserForRemoval.getEmail());
			us.setDmsremove(true);
			us.setDmsremoveVal("Removed");
			this.selUserForRemoval.setDmsremoveVal("DMS Removed");
			imsUserMap.getImsUserMap().put(this.selUserForRemoval.getEmail(),
					us);
		}
		log.info("updateDmsRemoveList this.selUserForRemoval DMS remove value"
				+ imsUserMap.getImsUserMap()
						.get(this.selUserForRemoval.getEmail()).isDmsremove());

		log.info("update the list "
				+ "  "
				+ getRemoveUserMap().get(this.selUserForRemoval.getEmail())
						.getAccReport().get("DMS").getFirstName());
		log.info("update the list "
				+ "  "
				+ getRemoveUserMap().get(this.selUserForRemoval.getEmail())
						.getAccReport().get("DMS").getLastName());
		log.info("update the list "
				+ "  "
				+ getRemoveUserMap().get(this.selUserForRemoval.getEmail())
						.getAccReport().get("DMS").getApplication());

		log.info("Remove size after removal " + getRemoveUserMap().size()
				+ " Size of AccountCertReport list "
				+ getUserAccntRemove().size());

	}

	public void cancelImsRemove(ActionEvent event) {

		// log.info("cancelImsRemove Component Id " +
		// event.getComponent().getFamily() );
		log.info("cancelImsRemove this.selUserForRemoval "
				+ this.selUserForRemoval.getEmail());

		if (getListOfIMSRemovedUsers().containsKey(
				this.selUserForRemoval.getEmail())) {
			getListOfIMSRemovedUsers()
					.remove(this.selUserForRemoval.getEmail());
		}

		boolean eval = false;
		User temp;
		if (getRemoveUserMap().containsKey(this.selUserForRemoval.getEmail())) {
			temp = getRemoveUserMap().get(this.selUserForRemoval.getEmail());
			if (temp.getAccReport().containsKey("IMS")) {
				temp.getAccReport().remove("IMS");
				getRemoveUserMap().put(this.selUserForRemoval.getEmail(), temp);
				eval = true;
			}

		}

		if (imsUserMap.getImsUserMap().containsKey(
				this.selUserForRemoval.getEmail())
				&& eval) {
			User us = imsUserMap.getImsUserMap().get(
					this.selUserForRemoval.getEmail());
			us.setImsremove(false);
			us.setImsremoveVal("Remove IMS Account");
			this.selUserForRemoval.setImsremoveVal("Remove IMS Account");
			imsUserMap.getImsUserMap().put(this.selUserForRemoval.getEmail(),
					us);
		}

		// org.primefaces.component.datatable.DataTable acctab;
		// acctab =
		// (org.primefaces.component.datatable.DataTable)event.getComponent().findComponent("pendingusers");
		// log.info("This is the event from the table -->  "
		// +acctab.getDefaultEventName());

	}

	public void updateImsRemoveList(ActionEvent event) {
		Date d = new Date();
		int ind = 0;
		int indValue = 0;
		if (getRemoveUserMap().containsKey(this.selUserForRemoval.getEmail())) {
			User temp = getRemoveUserMap().get(
					this.selUserForRemoval.getEmail());
			// temp.setImsremove(true);
			ind = this.getDirector().getEmail().indexOf("@");

			if (ind != -1) {
				indValue = ind - 1;
				temp.getAccReport().put(
						"IMS",
						new AccountCertReport(this.selUserForRemoval
								.getFirstName(), this.selUserForRemoval
								.getLastName(), this.selUserForRemoval
								.getEmail() + "@EEOC.GOV", this.getDirector()
								.getEmail().substring(0, ind), d, "Y", "IMS",
								this.selUserForRemoval.getComments()));
				getRemoveUserMap().put(this.selUserForRemoval.getEmail(), temp);
				getListOfIMSRemovedUsers().put(
						this.selUserForRemoval.getEmail(),
						this.selUserForRemoval);
			} else {
				temp.getAccReport().put(
						"IMS",
						new AccountCertReport(this.selUserForRemoval
								.getFirstName(), this.selUserForRemoval
								.getLastName(), this.selUserForRemoval
								.getEmail() + "@EEOC.GOV",
								"No Director Email ", d, "Y", "IMS",
								this.selUserForRemoval.getComments()));
				getRemoveUserMap().put(this.selUserForRemoval.getEmail(), temp);
				getListOfIMSRemovedUsers().put(
						this.selUserForRemoval.getEmail(),
						this.selUserForRemoval);
			}

		}

		else {
			ind = this.getDirector().getEmail().indexOf("@");
			if (ind != -1) {
				indValue = ind - 1;
				this.selUserForRemoval.getAccReport().put(
						"IMS",
						new AccountCertReport(this.selUserForRemoval
								.getFirstName(), this.selUserForRemoval
								.getLastName(), this.selUserForRemoval
								.getEmail() + "@EEOC.GOV", this.getDirector()
								.getEmail().substring(0, ind), d, "Y", "IMS",
								this.selUserForRemoval.getComments()));
				// this.selUserForRemoval.setImsremove(true);
				getRemoveUserMap().put(this.selUserForRemoval.getEmail(),
						this.selUserForRemoval);

				getListOfIMSRemovedUsers().put(
						this.selUserForRemoval.getEmail(),
						this.selUserForRemoval);
			} else {
				this.selUserForRemoval.getAccReport().put(
						"IMS",
						new AccountCertReport(this.selUserForRemoval
								.getFirstName(), this.selUserForRemoval
								.getLastName(), this.selUserForRemoval
								.getEmail() + "@EEOC.GOV", "No director Email",
								d, "Y", "IMS", this.selUserForRemoval
										.getComments()));
				// this.selUserForRemoval.setImsremove(true);
				getRemoveUserMap().put(this.selUserForRemoval.getEmail(),
						this.selUserForRemoval);
				getListOfIMSRemovedUsers().put(
						this.selUserForRemoval.getEmail(),
						this.selUserForRemoval);
			}

		}

		// update main map to show ims remove flag has changed
		if (imsUserMap.getImsUserMap().containsKey(
				this.selUserForRemoval.getEmail())) {
			User us = imsUserMap.getImsUserMap().get(
					this.selUserForRemoval.getEmail());
			us.setImsremove(true);
			us.setImsremoveVal("Removed");
			this.selUserForRemoval.setImsremoveVal("IMS Removed");
			imsUserMap.getImsUserMap().put(this.selUserForRemoval.getEmail(),
					us);
		}

		log.info("update the list "
				+ "  "
				+ getRemoveUserMap().get(this.selUserForRemoval.getEmail())
						.getAccReport().get("IMS").getFirstName());
		log.info("update the list "
				+ "  "
				+ getRemoveUserMap().get(this.selUserForRemoval.getEmail())
						.getAccReport().get("IMS").getLastName());
		log.info("update the list "
				+ "  "
				+ getRemoveUserMap().get(this.selUserForRemoval.getEmail())
						.getAccReport().get("IMS").getApplication());

		log.info("Remove size after removal " + getRemoveUserMap().size()
				+ " Size of AccountCertReport list "
				+ getUserAccntRemove().size());

	}

	private HashMap<String, Person> ldapPersonUserIdMap = new HashMap<String, Person>(
			new Integer((int) (2600 / 0.75 + 1)).intValue());

	public HashMap<String, Person> getLdapPersonUserMap() {
		return ldapPersonUserIdMap;
	}

	public void setLdapPersonUserMap(HashMap<String, Person> personMap) {
		this.ldapPersonUserIdMap = personMap;
	}

	private HashMap<String, Person> ldapPersonEmailMap = new HashMap<String, Person>(
			new Integer((int) (2600 / 0.75 + 1)).intValue());

	public HashMap<String, Person> getLdapPersonEmailMap() {
		return ldapPersonEmailMap;
	}

	public void setLdapPersonEmailMap(HashMap<String, Person> personEmailMap) {
		this.ldapPersonEmailMap = personEmailMap;
	}

	private HashMap<String, User> imsUserLastNameMap = new HashMap<String, User>();

	public HashMap<String, User> getImsUserLastNameMap() {
		return imsUserLastNameMap;
	}

	public void setImsUserLastNameMap(HashMap<String, User> imsUserLastNameMap) {
		this.imsUserLastNameMap = imsUserLastNameMap;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getHqmapcode() {
		return hqdeptcode;
	}

	public void setHqmapcode(String hqmapcode) {
		this.hqdeptcode = hqmapcode;
	}

	public String getLdapcitycode() {
		return ldapcitycode;
	}

	public void setLdapcitycode(String ldapcitycode) {
		this.ldapcitycode = ldapcitycode;
	}

	public String getOfficeCode() {
		return officeCode;
	}

	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	public List<User> getFilteredPendingUsers() {

		return this.filteredPendingUsers;
	}

	public void setFilteredPendingUsers(List<User> filteredPendingUsers) {

		this.filteredPendingUsers = filteredPendingUsers;
	}

	public Director getDirector() {
		return director;
	}

	public void setDirector(Director director) {
		this.director = director;
	}

	public User getSelUserForRemoval() {
		return selUserForRemoval;
	}

	public void setSelUserForRemoval(User selUserForRemoval) {
		log.info("calling setSelUserForRemoval");
		log.info("selUserForRemoval.getAccReport().size() "
				+ selUserForRemoval.getAccReport().size());
		log.info(" selUserForRemoval.getFirstName()  "
				+ selUserForRemoval.getFirstName() + "  "
				+ selUserForRemoval.getLastName());
		this.selUserForRemoval = selUserForRemoval;
	}

	public List<User> getPendingUsers() {
		return pendingUsers;
	}

	public void setPendingUsers(List<User> pendingUsers) {
		this.pendingUsers = pendingUsers;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getPendingUsersCount() {
		return pendingUsersCount;
	}

	public void setPendingUsersCount(int pendingUsersCount) {
		this.pendingUsersCount = pendingUsersCount;
	}

	public UserCertificationController() {

	}

	public void getReportURL() throws IOException {
		String url = certificationService.getReportUrl(officeCode);
		// String url =
		// "http://10.9.23.44:8080/birtweb/run?__report=ims_access.rptdesign&P_OFFICE_CODE=430&__format=pdf&P_TOKEN=95A2HPBBW2";
		FacesContext.getCurrentInstance().getExternalContext().redirect(url);
	}

	// ArrayList<Account>

	private boolean clearAll;

	public boolean getClearAll() {
		return clearAll;
	}

	public void setClearAll(boolean clearAll) {
		this.clearAll = clearAll;
	}

	private List<String> resources;

	public List<String> getResources() {
		return resources;
	}

	public void createResourceList() {
		resources = new ArrayList<String>();
		resources.add("LDAP");
		resources.add("IMS");
		resources.add("DMS");
	}

	public void setResources(List<String> resources) {
		this.resources = resources;
	}

	/*
	 * public void valueChangeMethod(AjaxBehaviorEvent e){
	 * log.info("The value change event has been fired "); }
	 */
	public void clearUserRecordFromRemoveMap(AjaxBehaviorEvent event) {
		String etemp = this.selUserForRemoval.getEmail();
		log.info(" clearUserRecordFromRemoveMap email " + etemp);

		if (getRemoveUserMap().containsKey(etemp)) {
			getRemoveUserMap().remove(etemp);
		} else {
			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(null, new FacesMessage(
					"The user is not in the removal list"));
		}

	}

	public void clearAccntCertReportList() {

		for (String key : getRemoveUserMap().keySet()) {
			getRemoveUserMap().get(key).getAccReport().clear();
		}

		getRemoveUserMap().clear();

		log.info("  getRemoveUserMap().clear(); size "
				+ getRemoveUserMap().size());
		/*
		 * for(User s : this.pendingUsers){ s.setAccReport(new
		 * HashMap<String,AccountCertReport>()); }
		 */
	}

	public void showTableView(ActionEvent event) {

		log.info("Event from input text " + event.getSource().toString());

	}

	public void createUserAccntRemoveList() {
		/*
		 * Generate removed user list
		 */
		log.info("removeUsers");
		getUserAccntRemove().clear(); // clearing cache list creating list below
										// from map

		boolean check = getRemoveUserMap().isEmpty();

		log.info(" Map is Empty " + check);

		if (!check) {
			List<User> userList = new ArrayList<User>(getRemoveUserMap()
					.values());
			for (User u : userList) {
				List<AccountCertReport> accntRemov = new ArrayList<AccountCertReport>(
						u.getAccReport().values());
				for (AccountCertReport acr : accntRemov) {
					getUserAccntRemove().add(acr);
				}
			}
		}
		if (!check)
			log.info("removeUsers "
					+ getUserAccntRemove().get(0).getApplication()
					+ " Size of list " + getUserAccntRemove().size());
		else {

			log.info("remove map is empty no users to remove");
		}
		// log.info("Removing IMS Users ");
		// remove();
	}

	public String certify() {
		log.info("Certification in progress ");
		boolean success;
		log.info(" Calling AccountCertificationService ");
		log.info(" Calling createUserAccntRemoveList ");

		createUserAccntRemoveList();
		if (getUserAccntRemove().isEmpty())
			log.info("getUserAccntRemove is empty nothing to remove or report");

		if (!getUserAccntRemove().isEmpty()) {
			this.certificationService.removeAllUsers(getUserAccntRemove());
			getUserAccntRemove().clear();
			getRemoveUserMap().clear();
			remove();
		}

		if ((director.getCertificationDate() == null)
				|| CertificationUtil.getNoOfDaysfromLastCertification(director
						.getCertificationDate()) >= 335) {
			java.sql.Date dtTodaysDate = new java.sql.Date(new Date().getTime());
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			String todaysDate = df.format(dtTodaysDate);
			if(officeCode.equals("810")){
				/* This is  a special case these are all the same office 
				 * 810 is representing all of the offices
				 * */
			success = certificationService.certifyUser("810");
			success = certificationService.certifyUser("811");
			success = certificationService.certifyUser("812");
			success = certificationService.certifyUser("814");
			}
			else
             if(officeCode.equals("830")) {
            	 /* This is  a special case these are all the same office 
 				 * 830 is representing all of the offices
 				 * */
            	 success = certificationService.certifyUser("830");
     			success = certificationService.certifyUser("832");
             }
             else
            	 success = certificationService.certifyUser(officeCode);
				if (success) {
				try {
					StringBuffer buf = new StringBuffer();
					buf.append("Dear ");
					buf.append(director.getDirectorName());
					buf.append(",<br/><br/>");
					buf.append("This is to confirm your certification of the Integrated Mission System (IMS) accounts for ");
					buf.append(director.getOfficeDescription());
					buf.append(". The accounts have been certified as of  ");
					buf.append(todaysDate);
					buf.append(".<br/><br/>");
					buf.append("We  will  repeat  this  process  on  an  annual  basis. Thank  you  for  your  cooperation.");
					buf.append("<br/><br/>");
					buf.append("Richard Koplow");
					buf.append("<br/>");
					buf.append("Senior Information Security Officer");
					buf.append("<br/>");
					buf.append("U.S. Equal Employment Opportunity Commission");
					buf.append("<br/><br/>");
					buf.append("<i>This is an autogenerated email. Please do not reply to this email.</i>");
					if (CertificationConstants.OIT.equals(officeCode)) {
					sendEmail(director.getEmail(),
						CertificationConstants.FROM_EMAIL,
						"Your Certification is Completed",
						buf.toString());
					} 
					else {

						List<String> ITSpecialistEmailList = this.certificationService
								.getITSpecialistEmail(officeCode);

						if ((ITSpecialistEmailList == null)
								|| (ITSpecialistEmailList.isEmpty())) {
							List<String> ITAssistantEmailAddressList = this.certificationService
									.getITAssistantEmail(officeCode);
							if ((ITAssistantEmailAddressList == null)
									|| (ITAssistantEmailAddressList.isEmpty())) {
								sendEmail(director.getEmail(),
										CertificationConstants.FROM_EMAIL,
										"Your Certification is Completed",
										buf.toString());
							} else {
								if (ITAssistantEmailAddressList.size() == 1) {
									sendEmail(director.getEmail(),
											ITAssistantEmailAddressList.get(0),
											CertificationConstants.FROM_EMAIL,
											"Your Certification is Completed",
											buf.toString());
								} else {
									sendEmail(director.getEmail(),
											ITAssistantEmailAddressList,
											CertificationConstants.FROM_EMAIL,
											"Your Certification is Completed",
											buf.toString());
								}
							}
						} else {
							if (ITSpecialistEmailList.size() == 1) {
								sendEmail(director.getEmail(),
										ITSpecialistEmailList.get(0),
										CertificationConstants.FROM_EMAIL,
										"Your Certification is Completed",
										buf.toString());
							} else {
								sendEmail(director.getEmail(),
										ITSpecialistEmailList,
										CertificationConstants.FROM_EMAIL,
										"Your Certification is Completed",
										buf.toString());
							}
						}

					}
				} catch (Exception e) {
					log.error("Error while sending email to the director", e);
					log.error(director.getEmail());

				}
				return "Success";
			} else {
				success = false;
				log.error("Error while certifying office= " + officeCode);
				return "errorPage";
			}

		} else {
			return "alreadyDone";
		}

		// return "Success?faces-redirect=true";
		// return "Success";
	}

	public void remove() {

		FacesContext context = FacesContext.getCurrentInstance();

		List<User> list = new ArrayList<User>(getListOfIMSRemovedUsers()
				.values());

		for (User ulist : list) {
			// if (ulist.getComments().trim().equals("")) {
			// context.addMessage(null, new
			// FacesMessage("The removal reason is required for remove accounts."));
			// return (null);

			log.info("Removing user ");
			boolean success;
			success = certificationService.removeUser(ulist);
			if (success) {
				// this.pendingUsersCount=certificationService.getCountofActiveByOfficeCode(officeCode);
				// System.out.println("Pending UsersCount:"+
				// this.pendingUsersCount);
				// this.pendingUsers =
				// certificationService.getListofActiveByOfficeCode(officeCode);

				try {
					log.info("Inside the remove account email sending method for"
							+ ulist.getStaffSeq());
					certificationService.delete_ims_account(
							this.director.getDirectorStaffSeq(),
							ulist.getStaffSeq());
				} catch (Exception e) {
					log.error("Error while executing the stored procedure for removing user ");
					log.error(ulist.getStaffSeq());
				}
			} else {
				log.error("Error while removing the user ");
				log.error(ulist.getStaffSeq());
			}
		}
		// return (null);
		getListOfIMSRemovedUsers().clear();
	}

	public List<User> returnImsUserAccessList(List<User> tuser) {
		/*
		 * for the purpose of this applications An IMS user is defined as having
		 * Shared_Access deleted = A and Shared staff status = 'A'
		 */

		log.info("Check size of IMS list before checking A " + tuser.size());
		ArrayList<User> ruser = new ArrayList<User>();

		for (User puser : tuser) {
			String tmp = puser.getStaffSeqDeletedVal().trim();
			tmp = tmp.toUpperCase();
			if (tmp != null && !tmp.isEmpty() && tmp.equals("A")) {
				ruser.add(puser);
			} else {
				log.info(" returnImsUserAccessList Value Exception "
						+ puser.getFirstName() + "  " + puser.getLastName()
						+ "  deleted value " + puser.getStaffSeqDeletedVal()
						+ "  email  " + puser.getEmail());
			}
		}
		log.info("Check size of IMS list after checking A " + ruser.size());

		return ruser;
	}

	final private static String BLANK = "blank";

	public void createIMSUserKeyMap(List<User> ulist) {
		int counter = 0;
		log.info("Inside createIMSUserKeyMap ulist.size() " + ulist.size());
		for (User us : ulist) {
			counter++;
			log.info(" Name " + us.getFirstName() + "  " + us.getLastName()
					+ "   email " + us.getEmail());
			String tmp = this.certificationService.getContactInfoFromEmail(
					us.getEmail()).trim();
			log.info("CreateIMSUserKeyMap Value returned from getContactInfoFromEmail "
					+ tmp + " counter " + counter);
			if (!tmp.equals(BLANK)) {
				if (!imsUserMap.getImsUserMap().containsValue(tmp)) {
					us.setEmail(tmp);
					imsUserMap.getImsUserMap().put(tmp, us);
				} else {
					log.info("  duplicate " + "  tmp value " + tmp);
				}
			} else {
				log.info(" createIMSUserKeyMap possible blank email "
						+ "  New Key Value " + " blank email."
						+ us.getFirstName() + "." + us.getLastName() + " ");

				String temp = "blankemail." + us.getFirstName() + "."
						+ us.getLastName();
				us.setEmail(temp);
				imsUserMap.getImsUserMap().put(
						"blankemail." + us.getFirstName() + "."
								+ us.getLastName(), us);
			}
		}

		log.info(" createIMSUserKeyMap size  "
				+ imsUserMap.getImsUserMap().keySet().size() + "  Counter val "
				+ counter);
	}

	public List<User> returnSearchedImsAccessListAgaintLdapList(
			List<Person> ldlist) {

		ArrayList<User> newImsUserList = new ArrayList<User>();

		ArrayList<User> addLdapUsers = new ArrayList<User>();

		log.info(" returnSearchedImsAccessListAgaintLdapList ");

		log.info(" Size of list from ldap before search " + ldlist.size());
		log.info("Size of IMS map " + imsUserMap.getImsUserMap().size());

		/*
		 * for(int x = 0; x <
		 * imsUserMap.getImsUserMap().values().toArray().length; x++) { User u1
		 * = (User)imsUserMap.getImsUserMap().values().toArray()[x];
		 * 
		 * 
		 * log.info("u1.email in imsMap before checking ldap email and hashcode  "
		 * +u1.getEmail()+ "   "+ u1.getEmail().hashCode() + " x  " + x); }
		 */
		log.info("Check ldap list consistent IMS Keys");
		/*
		 * for(int x = 0; x < ldlist.size(); x++) {
		 * log.info("Check ldap list consistent "+ldlist.get(x).getEmail() ); }
		 */

		String tinfo = "";
		int counter = 0;
		int invalidEmail = 0;
		int valid = 0;
		int contains = 0;
		for (Person tperson : ldlist) {
			counter++;
			tinfo = tperson.getEmail().trim();
			log.info("Checking email field from Ldap Map loop in returnSearchedImsAccessListAgaintLdapList mail and hashcode "
					+ tinfo + "  hashcode " + tinfo.hashCode());

			if (imsUserMap.getImsUserMap().containsKey(tinfo)) {
				log.info(" Found " + tinfo);
				contains++;
				User utemp = imsUserMap.getImsUserMap().get(tinfo);
				utemp.setNetAccess("Y");
				utemp.setNetutiliy("N/A");
				utemp.setImsAccess("Active");
				// utemp.setDmsstate(false);

				imsUserMap.getImsUserMap().put(tinfo, utemp); // update the map
																// value with
																// the
																// imsNetStatus

				// newImsUserList.add(utemp);
			} else {

				// email =
				// this.certificationService.getContactInfoFromEmail(email);

				// newUser.setImsAccess("no IMS Account");
				// newUser.setUtiliy("none");
				// log.info(" New User "+ email);
				log.info(" Not Found Adding to map " + tinfo);

				imsUserMap.getImsUserMap().put(
						tinfo,
						new User(tperson.getFirstName(), tperson.getLastName(),
								tinfo, "Y"));
			}

			// search ims user list against ldap list
		} // end for loop

		log.info("Size of imsUserMap after checking for net Access and ldap list combination "
				+ imsUserMap.getImsUserMap().size());
		/*
		 * for(int x = 0; x <
		 * imsUserMap.getImsUserMap().values().toArray().length; x++) { User u1
		 * = (User)imsUserMap.getImsUserMap().values().toArray()[x];
		 * newImsUserList.add(u1);
		 * 
		 * log.info(" In returnSearchedImsAccessListAgaintLdapList after mapping "
		 * + " x  " + x + " User " + u1.getEmail() + " hash  " +
		 * u1.getEmail().hashCode() ); log.info( " netaccess " +
		 * u1.getNetAccess()); }
		 */
		log.info("New imsldap user list after conversion to arraylist of users "
				+ newImsUserList.size()
				+ " Size of map "
				+ imsUserMap.getImsUserMap().values().size());

		// log.info(" Size of user list after search" + newImsUserList.size() +
		// "  counter loop size "+
		// counter+ " invalidEmail  "+ invalidEmail + " contains  "+ contains);

		setPendingUsers(newImsUserList);
		// for(User u : getPendingUsers())
		// log.info("Check email key for main list after ims ldap match and merger --- >  "+
		// u.getEmail());
		return getPendingUsers();

	}

	/*
	 * public void createLdapUserListFromIMSTables() {
	 * log.info("Calling ldap All staff xml list ");
	 * createLdapPersonMapByAttrType
	 * (this.certificationService.convertLdapAllStaffXmlToList(),USERID );
	 * 
	 * log.info("Ldap User list Size before sorting " +
	 * getLdapPersonUserList().size());
	 * log.info("Sorting staff list by UserId");
	 * java.util.Collections.sort(getLdapPersonUserList(),new
	 * LdapPersonHelperUserId());
	 * 
	 * log.info("Ldap staff list is sorted by User Id");
	 * //getPersonUserListLdap().
	 * 
	 * }
	 */
	public List<Person> removeWhiteSpaceFromLdapEmailAttr(List<Person> plist) {

		for (int x = 0; x < plist.size(); x++) {
			if (!plist.get(x).getEmail().isEmpty())
				plist.get(x).setEmail(
						plist.get(x).getEmail().replaceAll("\\s", "").trim()
								.toUpperCase());
		}

		return plist;
	}

	public List<Person> makeLdapPersonEmailAttrSearchable(List<Person> per) {
		log.info("Inside makeLdapPersonEmailAttrSearchable");
		for (int x = 0; x < per.size(); x++) {
			// log.info("Checking ldap email "+per.get(x).getEmail());
			String temail = this.certificationService
					.getContactInfoFromEmail(per.get(x).getEmail());

			per.get(x).setEmail(temail);
			// log.info("Checking email in makeLdapPersonEmailAttrSearchable "+
			// per.get(x).getEmail());
		}

		return per;
	}

	public List<Person> getCorrectedLdapFirstAndLastName(List<Person> ldlist) {

		for (int x = 0; x < ldlist.size(); x++) {
			String fstr = "";

			String firstName = ldlist.get(x).getFirstName();
			fstr = firstName.substring(0, 1);
			firstName = fstr
					+ firstName.substring(1, firstName.length()).toLowerCase();
			ldlist.get(x).setFirstName(firstName);

			fstr = "";
			String lastName = ldlist.get(x).getLastName();
			fstr = lastName.substring(0, 1);
			lastName = fstr
					+ lastName.substring(1, lastName.length()).toLowerCase();
			ldlist.get(x).setLastName(lastName);
		}
		return ldlist;
	}

	public List<User> createLdapAndImsUserListFromOfficeCode(String code,
			List<User> imsList) {

		log.info("createLdapAndImsUserListFromOfficeCode");

		log.info("createLdapAndImsUserListFromOfficeCode Size of ims list "
				+ imsList.size());

		log.info(" OfficeCode list -->  " + code);

		HeadQuarterLdapMap hqUitl = new HeadQuarterLdapMap();

		code = code.toUpperCase();

		/* checking office code for head qt values */

		String check_HQT = code.substring(0, 1);
		String check_FEPA = code.substring(2, 3);

		check_FEPA = check_FEPA.toUpperCase();

		log.info("Fepa Check " + check_FEPA);
		boolean val = true;

		ArrayList<Person> templdapArray = new ArrayList<Person>();

		ArrayList<Person> rejectedlistArray = new ArrayList<Person>();

		if (UserOfficeCodeHelper.getIMSOfficeToLdapOffice().containsKey(code)) {
			this.ldapcitycode = UserOfficeCodeHelper.getIMSOfficeToLdapOffice()
					.get(code);

			log.info("EEOC Office code " + code + "  ldap city code " + this.ldapcitycode);
			List<Person> tperson = this.certificationService
					.convertLdapStaffXmlByOfficeCodeToList(this.ldapcitycode);

			log.info("createLdapAndImsUserListFromOfficeCode Size of ldap list "
					+ tperson.size());

			tperson = makeLdapPersonEmailAttrSearchable(tperson);

			log.info("makeLdapPersonEmailAttrSearchable Size of ldap list "
					+ tperson.size());

			log.info("Sorting ldap staff list by Email");

			java.util.Collections.sort(tperson, new LdapPersonHelperEmail());
			tperson = getCorrectedLdapFirstAndLastName(tperson);
			// for(Person per: tperson)
			// log.info(" Checking email values after made searchable -->  " +
			// per.getEmail()+ " Department " + per.getDepartment());

			log.info("Ldap staff list is sorted by Email");

			log.info("Setting ldapPersonuser list  ");

			setLdapPersonUserList(tperson);
			setIMSUserList(imsList);

			// createLdapPersonMapByAttrType(tperson,EMAIL);

			log.info("Ldap User list Size before sorting "
					+ getLdapPersonUserList().size());

			log.info("Create IMS user list where shared_access = A");

			// List<User> tuserMeth = returnImsUserAccessList(imsList); already
			// checking this in database

			// log.info("IMS user list where delete field is A " +
			// imsList.size());

			log.info("Create IMS User key Map in EEOC Check");

			createIMSUserKeyMap(getIMSUserList());

			log.info("Size of IMS User Map "
					+ imsUserMap.getImsUserMap().size());
			// log.info("check email key from IMS list ---> " );
			// for(int x = 0; x <
			// imsUserMap.getImsUserMap().values().toArray().length; x++) {
			// User u1 = (User)imsUserMap.getImsUserMap().values().toArray()[x];
			// log.info("check email key from IMS list ---> " + u1.getEmail());
			// }

			return returnSearchedImsAccessListAgaintLdapList(getLdapPersonUserList());
		} else if (check_HQT.equals("8")
				&& !Pattern.matches("[a-zA-Z]+", check_FEPA)) {

			String ldapDept = (String) hqUitl.getHeadMQMap().get(code);

			this.hqdeptcode = ldapDept;
       log.info("ldapDept department " + this.hqdeptcode);
			log.info("Head quarter Checking for WASDC office code ");
			// code = UserOfficeCodeHelper.getIMSOfficeToLdapOffice().get(code);
			this.ldapcitycode = "WASHDC";
			List<Person> tperson = this.certificationService
					.convertLdapStaffXmlByOfficeCodeToList("WASHDC");

			log.info("createLdapAndImsUserListFromOfficeCode Size of ldap list "
					+ tperson.size());

			tperson = makeLdapPersonEmailAttrSearchable(tperson);

			log.info("makeLdapPersonEmailAttrSearchable Size of ldap list WASHDC "
					+ tperson.size());

			log.info("Sorting ldap staff list by Email");

			java.util.Collections.sort(tperson, new LdapPersonHelperEmail());

			tperson = getCorrectedLdapFirstAndLastName(tperson);

			log.info("List size before resizing " + tperson.size());
			for (int ind = 0; ind < tperson.size(); ind++) {
				// log.info(" Checking email values after made searchable -->  "
				// + tperson.get(ind).getEmail()+ " Department " +
				// tperson.get(ind).getDepartment());
				if (tperson.get(ind).getDepartment().equals(ldapDept)) {
					// log.info("Valid ldap dept "+ "  "+
					// tperson.get(ind).getFirstName() + " "+
					// tperson.get(ind).getFullName());
					templdapArray.add(tperson.get(ind));
				} else {
					// log.info("Invalid ldap dept "+ "  "+
					// tperson.get(ind).getFirstName() + " "+
					// tperson.get(ind).getFullName() + " dept " +
					// tperson.get(ind).getDepartment());
					rejectedlistArray.add(tperson.get(ind));
					// tperson.remove(ind);
				}
			}
			java.util.Collections.sort(templdapArray,
					new LdapPersonHelperEmail());
			log.info("List size after resizing templdapArray "
					+ templdapArray.size());
			log.info("Ldap staff list is sorted by Email");
			log.info("Setting ldapPersonuser list  ");
			/*
			 * for(Person per: rejectedlistArray){
			 * log.info("Excluded ldap person " + per.getDepartment() + "  "+
			 * per.getFirstName() + " "+ per.getFullName() + "  email " +
			 * per.getEmail() + "  hascode " +per.getEmail().hashCode());
			 * 
			 * }
			 */
			for (Person per : templdapArray) {
				log.info("Valid ldap person " + ldapDept + "  "
						+ per.getFirstName() + " " + per.getFullName()
						+ "  email " + per.getEmail() + "  hascode "
						+ per.getEmail().hashCode());

			}

			setLdapPersonUserList(templdapArray);
			setIMSUserList(imsList);

			// createLdapPersonMapByAttrType(tperson,EMAIL);

			log.info("Ldap User list Size after sorting "
					+ getLdapPersonUserList().size());

			log.info("Create IMS user list where shared_access = A");

			// List<User> tuserMeth = returnImsUserAccessList(imsList); already
			// checking this in database

			// log.info("IMS user list where delete field is A " +
			// imsList.size());

			log.info("Create IMS User key Map in headQtr check");

			createIMSUserKeyMap(getIMSUserList());

			log.info("Size of IMS User Map "
					+ imsUserMap.getImsUserMap().size());

			// log.info("check email key from IMS list ---> " );
			// for(int x = 0; x <
			// imsUserMap.getImsUserMap().values().toArray().length; x++) {
			// User u1 = (User)imsUserMap.getImsUserMap().values().toArray()[x];
			// log.info("check email key from IMS list ---> " + u1.getEmail());
			// }

			return returnSearchedImsAccessListAgaintLdapList(getLdapPersonUserList());

		}
		if (Pattern.matches("[a-zA-Z]+", check_FEPA)) {

			/*
			 * The lines that are commented out have to do with fepa not needing
			 * any ldap mapping associations as the previous else
			 * statements....... We only create the ims map then return the list
			 * of pending users
			 */

			log.info("Fepa office code ");
			// code = UserOfficeCodeHelper.getIMSOfficeToLdapOffice().get(code);

			// List<Person> tperson =
			// this.certificationService.convertLdapStaffXmlByOfficeCodeToList("WASHDC");

			// log.info("createLdapAndImsUserListFromOfficeCode Size of ldap list "
			// +tperson.size());

			// tperson = makeLdapPersonEmailAttrSearchable(tperson);

			// log.info("makeLdapPersonEmailAttrSearchable Size of ldap list WASHDC "
			// +tperson.size());

			// log.info("Sorting ldap staff list by Email");

			// java.util.Collections.sort(tperson,new LdapPersonHelperEmail());

			// for(Person per: tperson)
			// log.info(" Checking email values after made searchable -->  " +
			// per.getEmail());

			// log.info("Ldap staff list is sorted by Email");

			// log.info("Setting ldapPersonuser list  ");

			// setLdapPersonUserList(tperson);

			setIMSUserList(imsList);

			// createLdapPersonMapByAttrType(tperson,EMAIL);

			// log.info("Ldap User list Size before sorting " +
			// getLdapPersonUserList().size());

			log.info("Create IMS user list where shared_access = A");

			// List<User> tuserMeth = returnImsUserAccessList(imsList); already
			// checking this in database

			// log.info("IMS user list where delete field is A " +
			// imsList.size());

			log.info("Create IMS User key Map  in Fepa Check");

			createIMSUserKeyMap(getIMSUserList());

			log.info("Size of IMS User Map "
					+ imsUserMap.getImsUserMap().size());
			// log.info("check email key from IMS list ---> " );
			// for(int x = 0; x <
			// imsUserMap.getImsUserMap().values().toArray().length; x++) {
			// User u1 = (User)imsUserMap.getImsUserMap().values().toArray()[x];
			// log.info("check email key from IMS list ---> " + u1.getEmail());
			// }

			/*
			 * Here we are just adding the ims users to the pending list
			 */

			ArrayList<User> newImsUserList = new ArrayList<User>();

			for (int x = 0; x < imsUserMap.getImsUserMap().values().toArray().length; x++) {
				User u1 = (User) imsUserMap.getImsUserMap().values().toArray()[x];
				newImsUserList.add(u1);
				log.info("Other Ims object info  " + u1.getFirstName() + "  "
						+ u1.getLastName() + "  " + u1.getEmail());
				log.info("u1.getImsAccess()  " + u1.getImsAccess() + " x  " + x);
			}

			log.info("New ims Fepa user list after conversion to arraylist of users "
					+ newImsUserList.size());

			// log.info(" Size of user list after search" +
			// newImsUserList.size() + "  counter loop size "+
			// counter+ " invalidEmail  "+ invalidEmail + " contains  "+
			// contains);

			setPendingUsers(newImsUserList);
			// for(User u : getPendingUsers())
			// log.info("Check email key for main list after ims ldap match and merger --- >  "+
			// u.getEmail());
			return getPendingUsers();
		} else {
			return new ArrayList<User>();
		}
		// getPersonUserListLdap().

	}

	public List<User> returnedKeySearchedEmailListAgainstDMSData(
			List<User> user, String city) {
		log.info("call returnedKeySearchedEmailListAgainstDMSData city value "
				+ city);
		// DmsEmailMap tdmsmap = certificationService.generateDMSEmailMap(); //
		// Checking DMS list

		// String hqdept = this.hqdeptcode;
		List<DMSUsers> tdmsmap = certificationService
				.getDMSUserLocationDataByCity(city);

		log.info(" returnedKeySearchedEmailListAgainstDMSData DMS List  size "
				+ tdmsmap.size());
		/*
		 * for(DMSUsers d:tdmsmap) { log.info(" Check for dms values " +
		 * "First name " +d.getFirstName() + " Last name "+ d.getLastName() +
		 * "  Email "+ d.getMailaddress());
		 * 
		 * }
		 */
		ArrayList<User> newImsUserList = new ArrayList<User>();

		ArrayList<User> addDMSUsers = new ArrayList<User>();
		/*
		 * for(int x = 0; x <
		 * imsUserMap.getImsUserMap().values().toArray().length; x++) { User u1
		 * = (User)imsUserMap.getImsUserMap().values().toArray()[x]; log.info(
		 * "returnedKeySearchedEmailListAgainstDMSData before IMS LDAP Emails key search "
		 * + u1.getEmail()); }
		 */
		log.info("returnedKeySearchedEmailListAgainstDMSData Size of list from dms before search "
				+ tdmsmap.size());
		log.info("returnedKeySearchedEmailListAgainstDMSData Size of IMSLDAP map "
				+ imsUserMap.getImsUserMap().size());

		String tinfo = "";
		int counter = 0;
		int invalidEmail = 0;
		int valid = 0;
		int contains = 0;
		for (DMSUsers tperson : tdmsmap) {
			counter++;
			tinfo = this.certificationService.getContactInfoFromEmail(tperson
					.getMailaddress());
			log.info("returnedKeySearchedEmailListAgainstDMSData Checking email field from DMS List "
					+ tinfo + " check for names " + tperson.getFirstName());

			if (imsUserMap.getImsUserMap().containsKey(tinfo)) {
				log.info("Found match in ldap ims list update  map" + tinfo);
				contains++;
				User utemp = imsUserMap.getImsUserMap().get(tinfo);
				utemp.setDmsAccess("Y");
				utemp.setDmsutility("N/A");
				utemp.setDmsstate(true);
				imsUserMap.getImsUserMap().put(tinfo, utemp); // update the map
																// value with
																// the
																// imsNetStatus

				// newImsUserList.add(utemp);
			} else {

				// User newUser = new User();

				// String email = tperson.getMailaddress().toLowerCase();
				// newUser.setFirstName(tperson.getFirstName());
				// newUser.setLastName(tperson.getLastName());
				// newUser.setEmail(tinfo);
				// newUser.setDmsAccess("Yes");
				// newUser.setDmsutility("N/A");
				// newUser.setDmsstate(true);
				// newUser.setImsAccess("no IMS Account");
				// newUser.setUtiliy("none");
				log.info("Not Found " + tinfo);

				log.info("returnedKeySearchedEmailListAgainstDMSData Checking email field from DMS List "
						+ tinfo
						+ " add New DMS to list "
						+ tperson.getFirstName());

				imsUserMap.getImsUserMap().put(
						tinfo,
						new User(tperson.getFirstName(), tperson.getLastName(),
								tinfo, "Y", true));

			}
			// search ims user list against ldap list
		} // end for loop

		log.info("Size of imsUserMap really User Map after checking for DMS Access against ldap and IMS list combination "
				+ imsUserMap.getImsUserMap().size());

		log.info("View new Pending list list after DMS add ");

		// log.info(" Size of user list after search" + newImsUserList.size() +
		// "  counter loop size "+
		// counter+ " invalidEmail  "+ invalidEmail + " contains  "+ contains);

		// for (User u1: getPendingUsers())
		// log.info("Check email from resulting ldap,imsand dms merge from Pending User list --- > "+
		// u1.getEmail());

		for (int x = 0; x < imsUserMap.getImsUserMap().values().toArray().length; x++) {
			User u1 = (User) imsUserMap.getImsUserMap().values().toArray()[x];

			if (u1.getDmsAccess() == null) {
				log.info("DMS is null");
				u1.setDmsAccess("N");
			}
			if (u1.getImsAccess() == null) {
				log.info("Ims is null");
				u1.setImsAccess("N");
			}

			if (u1.getNetAccess() == null) {
				log.info("Net is null");
				u1.setNetAccess("N");
			}
			log.info("  value " + u1.getEmail() + "  Net " + u1.getNetAccess()
					+ " IMS  " + u1.getImsAccess() + "  DMS "
					+ u1.getDmsAccess());

			newImsUserList.add(u1);
		}

		log.info(" check list size " + newImsUserList.size() + "  map size "
				+ imsUserMap.getImsUserMap().values().size());
		setPendingUsers(newImsUserList);

		return getPendingUsers();
	}

	public List<User> returnedKeySearchedEmailListAgainstDMSHQData(
			List<User> user, String city) {
		log.info("call returnedKeySearchedEmailListAgainstHQDMSData city value "
				+ city);
		// DmsEmailMap tdmsmap = certificationService.generateDMSEmailMap(); //
		// Checking DMS list

		String hqdept = this.hqdeptcode;
		List<DMSUsers> tdmsmap = certificationService
				.getDMSUserLocationDataByDept(hqdept);

		log.info(" returnedKeySearchedEmailListAgainstHQDMSData DMS List  size "
				+ tdmsmap.size());

		for (DMSUsers d : tdmsmap) {
			log.info(" Check for dms values " + "First name "
					+ d.getFirstName() + " Last name " + d.getLastName()
					+ "  Email " + d.getMailaddress());

		}
		ArrayList<User> newImsUserList = new ArrayList<User>();

		ArrayList<User> addDMSUsers = new ArrayList<User>();
		/*
		 * for(int x = 0; x <
		 * imsUserMap.getImsUserMap().values().toArray().length; x++) { User u1
		 * = (User)imsUserMap.getImsUserMap().values().toArray()[x]; log.info(
		 * "returnedKeySearchedEmailListAgainstHQDMSData before IMS LDAP Emails key search "
		 * + u1.getEmail()); }
		 */
		log.info("returnedKeySearchedEmailListAgainstHQDMSData Size of list from dms before search "
				+ tdmsmap.size());
		log.info("returnedKeySearchedEmailListAgainstHQDMSData Size of IMSLDAP map "
				+ imsUserMap.getImsUserMap().size());

		String tinfo = "";
		int counter = 0;
		int invalidEmail = 0;
		int valid = 0;
		int contains = 0;
		for (DMSUsers tperson : tdmsmap) {
			counter++;
			tinfo = this.certificationService.getContactInfoFromEmail(tperson
					.getMailaddress());
			log.info("returnedKeySearchedEmailListAgainstDMSHQData Checking email field from DMS List "
					+ tinfo + " check for names " + tperson.getFirstName());

			if (imsUserMap.getImsUserMap().containsKey(tinfo)) {
				log.info("Found match in ldap ims list update  map" + tinfo);
				contains++;
				User utemp = imsUserMap.getImsUserMap().get(tinfo);
				utemp.setDmsAccess("Y");
				utemp.setDmsutility("N/A");
				utemp.setDmsstate(true);
				imsUserMap.getImsUserMap().put(tinfo, utemp); // update the map
																// value with
																// the
																// imsNetStatus

				// newImsUserList.add(utemp);
			} else {

				// User newUser = new User();

				// String email = tperson.getMailaddress().toLowerCase();
				// newUser.setFirstName(tperson.getFirstName());
				// newUser.setLastName(tperson.getLastName());
				// newUser.setEmail(tinfo);
				// newUser.setDmsAccess("Yes");
				// newUser.setDmsutility("N/A");
				// newUser.setDmsstate(true);
				// newUser.setImsAccess("no IMS Account");
				// newUser.setUtiliy("none");
				log.info("Not Found " + tinfo);

				log.info("returnedKeySearchedEmailListAgainstDMSHQData Checking email field from DMS List "
						+ tinfo
						+ " add New DMS to list "
						+ tperson.getFirstName());

				imsUserMap.getImsUserMap().put(
						tinfo,
						new User(tperson.getFirstName(), tperson.getLastName(),
								tinfo, "Y", true));

			}
			// search ims user list against ldap list
		} // end for loop

		log.info("Size of imsUserMap really User Map after checking for DMSHQ Access against ldap and IMS list combination "
				+ imsUserMap.getImsUserMap().size());

		log.info("View new Pending list list after DMS add ");

		// log.info(" Size of user list after search" + newImsUserList.size() +
		// "  counter loop size "+
		// counter+ " invalidEmail  "+ invalidEmail + " contains  "+ contains);

		// for (User u1: getPendingUsers())
		// log.info("Check email from resulting ldap,imsand dms merge from Pending User list --- > "+
		// u1.getEmail());

		for (int x = 0; x < imsUserMap.getImsUserMap().values().toArray().length; x++) {
			User u1 = (User) imsUserMap.getImsUserMap().values().toArray()[x];

			if (u1.getDmsAccess() == null) {
				log.info("DMS is null");
				u1.setDmsAccess("N");
			}
			if (u1.getImsAccess() == null) {
				log.info("Ims is null");
				u1.setImsAccess("N");
			}

			if (u1.getNetAccess() == null) {
				log.info("Net is null");
				u1.setNetAccess("N");
			}
			log.info("  value " + u1.getEmail() + "  Net " + u1.getNetAccess()
					+ " IMS  " + u1.getImsAccess() + "  DMS "
					+ u1.getDmsAccess());

			newImsUserList.add(u1);
		}

		log.info(" check list size " + newImsUserList.size() + "  map size "
				+ imsUserMap.getImsUserMap().values().size());

		setPendingUsers(newImsUserList);

		return getPendingUsers();
	}

	public List<User> returnedKeySearchedEmailListAgainstLdapData(
			List<User> user) {

		log.info("  ");
		log.info(" In returnedKeySearchedEmailListAgainstLdapData Also Size of User List "
				+ user.size());
		return user;
	}

	public List<User> returnedKeySearchedUseridListAgainstLdapData(
			List<User> user) {

		log.info("  ");
		log.info(" In returnedMappedSearchedListAgainstLdapData Also Size of User List "
				+ user.size());

		for (int x = 0; x < user.size(); x++) {
			if (getLdapPersonUserMap().containsKey(user.get(x).getNetAccess())) {
				String str = user.get(x).getNetAccess();
				user.get(x).setNetAccess(str + " UserID Exist");
				log.info(str + " UserID Exist" + "  Counter value in list  "
						+ x);
			} else {
				String str = user.get(x).getNetAccess();
				user.get(x).setNetAccess(str + " UserID Exist does not exist");
				log.info(str + " UserID Exist does not exist "
						+ "  Counter value in list  " + x);
			}
		}

		return user;
	}

	public List<User> returnedBinarySearchedUserIdListAgainstLdapData(
			List<User> user) {

		log.info("  PersonLdapUserListSize " + getLdapPersonUserList().size());

		for (int x = 0; x < user.size(); x++) {
			Person peronKey = new Person();
			peronKey.setUserId(user.get(x).getNetAccess().trim().toUpperCase());
			log.info("Key to be searched " + peronKey.getUserId());
			int index = java.util.Collections.binarySearch(
					getLdapPersonUserList(), peronKey,
					new LdapPersonHelperUserId());
			// .binarySearch(imsUsers,u.getNetAccess(),comparUser);

			log.info("Index of binary search key " + index);
			// binarySearch(imsUsers, u.getNetAccess());
			if (index >= 0) {
				log.info("This key is found in index  " + index + " <--> "
						+ getLdapPersonUserList().get(index).getUserId()
						+ " exist Y");
				user.get(x).setNetAccess(
						getLdapPersonUserList().get(index).getUserId()
								+ " exist Y");
			} else {
				user.get(x).setNetAccess(
						"blank not found " + peronKey.getUserId());

				log.info("This key is not found in index "
						+ peronKey.getUserId() + " <--> " + index);
			}
		}
		log.info("performing binary search completed");
		return user;
	}

	/*
	 * public List<User> returnSearchedListOfIMSUsersInLdapByUserId(List<User>
	 * imsUsers){
	 * log.info("performing binary search size of users imsUsers size "+
	 * imsUsers.size()); return
	 * returnedKeySearchedUseridListAgainstLdapData(imsUsers); //return
	 * returnedBinarySearchedUserIdListAgainstLdapData(imsUsers); }
	 */
	public List<User> generateUserAccessTableListByOfficeCode(String office_code) {

		log.info(" Calling the method generateUserAccessTableListByOfficeCode Office Code Value"
				+ office_code);

		log.info(" Calling the method certificationService.getListofImsUsersActiveByOfficeCode");
                List<String> select810 = new ArrayList<String>();
                select810.add("810");
                select810.add("811");
                select810.add("812");
                select810.add("814");
                
                List<String> select830 = new ArrayList<String>();
                select830.add("830");
                select830.add("832");
                 
		  if(!office_code.equals("810") && !office_code.equals("830"))
		this.pendingUsers = certificationService
				.getListofImsUsersActiveByOfficeCode(office_code);
		  else { 
			  
			  if(office_code.equals("810"))
			  this.pendingUsers = certificationService
					  .getListofImsUsersActiveBySelectedOfficeCode(select810);
			  if(office_code.equals("830"))
				  this.pendingUsers = certificationService
						  .getListofImsUsersActiveBySelectedOfficeCode(select830); 
		  }
		log.info("generateUserAccessTableListByOfficeCode List of users size "
				+ this.pendingUsers.size());

		/* ------------------------------ */
		String check_HQT = office_code.substring(0, 1);

		String check_FEPA = office_code.substring(2, 3);

		check_FEPA = check_FEPA.toUpperCase();

		// log.info("Fepa Check " + check_FEPA +" Office Code "+ office_code);

		log.info(" Calling   this.createLdapAndImsUserListFromOfficeCode(office_code,this.pendingUsers) ");

		this.pendingUsers = this.createLdapAndImsUserListFromOfficeCode(
				office_code, this.pendingUsers);
		log.info("Pending Users Size after  createLdapAndImsUserListFromOfficeCode "
				+ this.pendingUsers.size());
		if (Pattern.matches("[a-zA-Z]+", check_FEPA)) {

			log.info(" Pattern.matches Check Fepa  TRUE return list w/o DMS ");

			return this.pendingUsers;
		}
		if (!check_HQT.equals("8")){
			if(!office_code.equals("570"))
			return returnedKeySearchedEmailListAgainstDMSData(
					this.pendingUsers,
					this.certificationService.getDirectorCity(office_code));
			else{
				return returnedKeySearchedEmailListAgainstDMSData(
						this.pendingUsers,
						"washington-fo");
			}
		}
		else
			return returnedKeySearchedEmailListAgainstDMSHQData(
					this.pendingUsers,
					this.certificationService.getDirectorCity(office_code));

	}

	public boolean checkNetState() {
		if (this.selUserForRemoval.getImsAccess() == null
				|| this.selUserForRemoval.getImsAccess().isEmpty()
				|| this.selUserForRemoval.getImsAccess().length() == 0)
			return false;
		else
			return true;
	}

	private String valueOfInit = "";
	private String agent;

	public void init() {

		try {
			InputStream streamIMG = this.getClass().getResourceAsStream(
					"/EEOC_SEAL_Logo.bmp");
			image = new DefaultStreamedContent(streamIMG, "image/bmp");

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		log.info("Value of code member in UserCertificationBean " + code);
		String str = "";
		// String str = CertificationUtil.getSessionVariableAsString("start");

		FacesContext facesContext = FacesContext.getCurrentInstance();
		String parameter_value = (String) facesContext.getExternalContext()
				.getRequestParameterMap().get("code");
		this.code = parameter_value;
		if (valueOfInit.equals("run")) {
			log.info("Certificationutil Session Variable " + valueOfInit);

		} else {
			log.info("Certificationutil Session  Variable does not exist "
					+ valueOfInit);
		}

		// if(!valueOfInit.equals("run")) {
		CertificationUtil.SetSessionVariable("start", "run");
		log.info("CertificationUtil.SetSessionVariable(start,run)  ");
		if (code != null) {
			String decryptedCode = " ";
			try {
				EncryptionUtil decryptionUtil = new EncryptionUtil();
				decryptedCode = decryptionUtil.decrypt(code);
			} catch (Exception e) {
				e.printStackTrace();
			}
			log.info("about to set session also here is the encrypted office code --> "
					+ code);
			CertificationUtil.SetSessionVariable(
					CertificationConstants.OFFICE_CODE, decryptedCode);
		}
		try {

			agent = CertificationUtil.getUserAgent();
			log.info("User agent " + agent);
			officeCode = CertificationUtil
					.getSessionVariableAsString(CertificationConstants.OFFICE_CODE);
			log.info(" Decrypted office code --> " + officeCode);
			this.director = certificationService.getDirector(officeCode);
			this.selDirector = this.director;
			if ((director.getCertificationDate() == null)
					|| CertificationUtil
							.getNoOfDaysfromLastCertification(director
									.getCertificationDate()) >= 335) {

				createResourceList();
				// createLdapUserListFromIMSTables();

				// this.pendingUsers =
				// certificationService.getListofActiveByOfficeCode(officeCode);
                      log.info(" this is the office code " + officeCode);
				this.pendingUsers = generateUserAccessTableListByOfficeCode(officeCode);
             /*
				for (User us : this.pendingUsers) {

					log.info("This is the final table values "
							+ us.getFirstName() + "  " + us.getLastName()
							+ "  " + us.getEmail());
				}
				*/
				java.util.Collections.sort(pendingUsers, new UserHelper());
				// this.pendingUsersCount =
				// certificationService.getCountofActiveByOfficeCode(officeCode);

				setPendingUsers(this.pendingUsers);
				// this.pendingUsers =
				// returnedKeySearchedEmailListAgainstDMSData(this.pendingUsers);

				this.pendingUsersCount = this.pendingUsers.size();

				log.info("table build complete this.pendingUsersCount "
						+ this.pendingUsers.size());

				log.info("This is the User Map (imsUserMap.getImsUserMap) list size after processing "
						+ imsUserMap.getImsUserMap().size());

				valueOfInit = CertificationUtil
						.getSessionVariableAsString("start");

				// for(User u1 : this.pendingUsers)
				// log.info("Users index email -->   " + u1.getEmail());
				// log.info("Calling datatable id"+ getFooClientId());
			} else {

				this.pendingUsers = null;

				this.pendingUsersCount = 0;

				this.enabled = true;

			}
		} catch (Exception e) {
			log.info("Exception thrown trying to establish a session");
			e.printStackTrace();
		}

		// } // check if init has already been called
		log.info("end");
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public StreamedContent getImage() {
		return image;
	}

	public void setImage(StreamedContent image) {
		this.image = image;
	}

	private void sendEmail(String email, String from, String subject,
			String content) throws Exception {

		//String host = CertificationConstants.SMTP_SERVER;
		String host = this.certificationService.getSMTPSERVER();

		Properties properties = System.getProperties();

		properties.setProperty("mail.smtp.host", host);

		Session session = Session.getDefaultInstance(properties);
		try {

			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(from));

			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					email));

			message.setSubject(subject);

			message.setContent(content, "text/html");

			Transport.send(message);
			log.info("Confirmation email send successfully to director:"
					+ email);
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

	private void sendEmail(String email, String cc, String from,
			String subject, String content) throws Exception {

		//String host = CertificationConstants.SMTP_SERVER;
		String host = this.certificationService.getSMTPSERVER();

		Properties properties = System.getProperties();

		properties.setProperty("mail.smtp.host", host);

		Session session = Session.getDefaultInstance(properties);
		try {

			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(from));

			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					email));

			message.addRecipient(Message.RecipientType.CC, new InternetAddress(
					cc));

			message.setSubject(subject);

			message.setContent(content, "text/html");

			Transport.send(message);
			log.info("Confirmation email send successfully to director:"
					+ email);
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

	private void sendEmail(String email, List ccList, String from,
			String subject, String content) throws Exception {
		String cc;
		//String host = CertificationConstants.SMTP_SERVER;
		String host = this.certificationService.getSMTPSERVER();

		Properties properties = System.getProperties();

		properties.setProperty("mail.smtp.host", host);

		Session session = Session.getDefaultInstance(properties);
		try {

			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(from));

			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					email));

			for (int i = 0; i < ccList.size(); i++) {
				try {
					cc = (String) ccList.get(i);
					message.addRecipient(Message.RecipientType.CC,
							new InternetAddress(cc));
				} catch (Exception e) {
					System.out.println("The email address is empty");
					e.printStackTrace();
				}
			}
			message.setSubject(subject);

			message.setContent(content, "text/html");

			Transport.send(message);

			log.info("Confirmation email send successfully to director:"
					+ email);

		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

	public void keepSessionAlive() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) fc
				.getExternalContext().getRequest();
		request.getSession(false);
	}

	@PostConstruct
	public void initUserCertificationController() {
		init();

	}

	private Pattern pattern;
	private Matcher matcher;

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public boolean validate(final String email) {
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(email);
		return matcher.matches();

	}

	public void testButtonActionEvent(ActionEvent event) {

		// event.getComponent().
	}

	public void testEventPre() {
		log.info("getFooClientId() " + getFooClientId());
	}

	public String getFooClientId(UIComponent uicomp) {
		FacesContext context = FacesContext.getCurrentInstance();
		UIViewRoot root = context.getViewRoot();

		final String componentId = "pendingusers";
		UIComponent c = findComponent(root, componentId);

		return c.getClientId(context);
	}

	public String getFooClientId() {
		FacesContext context = FacesContext.getCurrentInstance();
		UIViewRoot root = context.getViewRoot();

		final String componentId = "pendingusers";
		UIComponent c = findComponent(root, componentId);

		return c.getClientId(context);
	}

	private UIComponent findComponent(UIComponent c, String id) {
		if (id.equals(c.getId())) {
			return c;
		}
		java.util.Iterator<UIComponent> kids = c.getFacetsAndChildren();
		while (kids.hasNext()) {
			UIComponent found = findComponent(kids.next(), id);
			if (found != null) {
				return found;
			}
		}
		return null;
	}

	public String getContactInfoFromEmail(String str) {
		String tmp = "";
		int ind = 0;
		String email_regex = "[A-Z]+[a-zA-Z_]+@\b([a-zA-Z]+.){2}\b?.[a-zA-Z]+";

		if (str != null && !str.isEmpty()) {
			str = str.trim().toUpperCase();
			str = str.replaceAll("\\s", "");

			if (validate(str)) {
				tmp = str;
				ind = tmp.lastIndexOf("@");
				if (ind != -1) {
					return tmp.substring(0, ind);
				} else {
					log.info(" getContactInfoFromEmail No @ sign in email ");
					return BLANK;
				}
			} else {

				log.info("getContactInfoFromEmail Email address not well formed "
						+ str + " String length " + str.length());
				return BLANK;
			}
		} else {
			log.info("getContactInfoFromEmail This email is null or empty "
					+ str);
			return BLANK;
		}

	}

}
