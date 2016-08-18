package gov.eeoc.accountcertification.controller;

import gov.eeoc.accountcertification.CertificationConstants;
import gov.eeoc.accountcertification.model.Director;
import gov.eeoc.accountcertification.model.Person;
import gov.eeoc.accountcertification.model.User;
import gov.eeoc.accountcertification.service.CertificationService;
import gov.eeoc.accountcertification.util.DirectorHelper;
import gov.eeoc.accountcertification.util.UserHelper;
import gov.eeoc.accountcertification.util.UserOfficeCodeHelper;
import gov.eeoc.accountcertification.util.LdapXmlHelper;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "userManagedBean")
@SessionScoped
public class UserManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;

	// private static Logger log =
	// Logger.getLogger(UserCertificationController.class);

	private static Logger log = Logger.getLogger(UserManagedBean.class);

	@EJB
	CertificationService certificationService;

	private  int numOfAttempts = 0;

	private String userName;

	private String password;

	private String officeCode;

	private String url;

	private String email;

	private String xmlLdapDirector;

	private boolean loadData = false;;

	private List<Person> personUserListLdap = new ArrayList<Person>();

	public List<Person> getPersonUserListLdap() {
		return personUserListLdap;
	}

	public void setPersonUserListLdap(List<Person> personUserListLdap) {
		this.personUserListLdap = personUserListLdap; // personUserListLdap.subList(0,
														// 300);
		java.util.Collections.sort(this.personUserListLdap);
		// personUserListLdap;
	}

	private Director director = new Director();

	private LdapXmlHelper ldapHelper = new LdapXmlHelper();

	public LdapXmlHelper getLdapHelper() {
		return ldapHelper;
	}

	public void setLdapHelper(LdapXmlHelper ldapHelper) {
		this.ldapHelper = ldapHelper;
	}

	private Director selDirectorEmail = new Director();

	public Director getSelDirectorEmail() {
		return selDirectorEmail;
	}

	public void setSelDirectorEmail(Director selDirectorEmail) {
		this.selDirectorEmail = selDirectorEmail;
	}

	private List<Director> filteredDirectors;

	public List<Director> getFilteredDirectors() {
		return filteredDirectors;
	}

	public void setFilteredDirectors(List<Director> filteredDirectors) {
		this.filteredDirectors = filteredDirectors;
	}

	private ArrayList<Director> directortotallist = new ArrayList<Director>();

	private ArrayList<Director> directoreeoclist = new ArrayList<Director>();

	private ArrayList<Director> directorfepadlist = new ArrayList<Director>();

	private ArrayList<Director> directorHQlist = new ArrayList<Director>();

	public int getNumOfAttempts() {
		return numOfAttempts;
	}

	public void setNumOfAttempts(int numOfAttempts) {
		this.numOfAttempts = numOfAttempts;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOfficeCode() {
		return officeCode;
	}

	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Director getDirector() {
		return director;
	}

	public void setDirector(Director director) {
		this.director = director;
	}

	public String getXmlLdapDirector() {
		return xmlLdapDirector;
	}

	public void setXmlLdapDirector(String xmlLdapDirector) {
		this.xmlLdapDirector = xmlLdapDirector;
	}

	public ArrayList<Director> getDirectoreeoclist() {
		return directoreeoclist;
	}

	public ArrayList<Director> getDirectorfepadlist() {
		return directorfepadlist;
	}

	public ArrayList<Director> getDirectortotallist() {
		return directortotallist;
	}

	public ArrayList<Director> getDirectorHQlist() {
		return directorHQlist;
	}

	public void setDirectorHQlist(ArrayList<Director> directorHQlist) {
		this.directorHQlist = directorHQlist;
	}

	public void init() {
		/*
		 * RequestContext.getCurrentInstance()
		 * .execute("PF('loadDialogWidget').close()");
		 */
		log.info(" clearing filter");
		this.filteredDirectors = null;
	}

	public void sendBulkMail() {
		addMessage("Mail", "Sending Bulk Mail.");
	}

	public void addMessage(String summary, String detail) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void generateBulkEmailNoPageRet() {
		boolean check = doEmailMessage(new Callback() {
			@Override
			public boolean run() {
				// TODO Auto-generated method stub
				return initialEmailProcessEEOC(directortotallist);
			}

		});

	}

	public boolean doEmailMessage(Callback start) {
		boolean test;
		if (start.run()) {
			log.info("Success ");
			FacesContext.getCurrentInstance().addMessage(
					":buttonSubmit:messages",
					new FacesMessage("Successfully sent bulk emails"));
			test = true;
		} else {
			log.info("Failure ");
			FacesContext.getCurrentInstance().addMessage(
					":buttonSubmit:messages",
					new FacesMessage("Bulk emails failed to get sent"));
			test = false;
		}
		return test;

	}

	public String doEmail(Callback start) {

		if (start.run()) {
			log.info("Success ");
			return "EmailConfirmation?faces-redirect=true";
		} else {
			log.info("Failure ");
			return "errorPage?faces-redirect=true";
		}
	}

	public String generateBulkEmail() {

		log.info("Generate Email start");
		String check = doEmail(new Callback() {
			@Override
			public boolean run() {
				// TODO Auto-generated method stub
				return initialEmailProcessEEOC(directortotallist);
			}

		});
		log.info(" Page forward " + check);
		return check;
	}

	public void generateSingleEmail(ActionEvent event) {

		/*
		 * String eve = event.getComponent().getFamily(); if
		 * (event.getComponent() instanceof CommandButton)
		 * log.info("Event that called this called if statement " + eve);
		 * 
		 * log.info("Event that called this " + eve);
		 * 
		 * log.info("generateSingleEmail  ");
		 * log.info("  selDirectorEmail var Dir name" +
		 * selDirectorEmail.getDirectorName());
		 * log.info("  selDirectorEmail var Email " +
		 * selDirectorEmail.getEmail());
		 * log.info("  selDirectorEmail var Office Code " +
		 * selDirectorEmail.getOfficeCode() );
		 * log.info("  selDirectorEmail var Url Link " +
		 * selDirectorEmail.getUrlLink() );
		 * 
		 * log.info("  selDirectorEmail var CertificationDate " +
		 * selDirectorEmail.getCertificationDate() );
		 * 
		 * log.info("  selDirectorEmail var DirectorStaffSeq " +
		 * selDirectorEmail.getDirectorStaffSeq() );
		 */

		log.info("Mail being sent to " + selDirectorEmail.getEmail());
		if (!(selDirectorEmail.getEmail() == null)
				&& !(selDirectorEmail.getEmail().isEmpty())) {
			singleEmailProcess(selDirectorEmail);
			FacesContext.getCurrentInstance().addMessage(
					":buttonSubmit:messages",
					new FacesMessage("Single mail sent to "
							+ selDirectorEmail.getEmail()));
		}

	}

	private ArrayList<String> userLdapIMSList = new ArrayList<String>();

	public ArrayList<String> getUserLdapIMSList() {
		return userLdapIMSList;
	}

	public void setUserLdapIMSList(ArrayList<String> userLdapIMSList) {
		this.userLdapIMSList = userLdapIMSList;
	}

	public void specialHQCodes() {

	}

	public void getDirectorList() {

		List<String> accessList = new ArrayList<String>();
		/*
		 * setPersonUserListLdap
		 * (this.certificationService.convertLdapAllStaffXmlToList() );
		 */
		List<String> diruser = this.certificationService.getAllEEOCDirectors();

		directoreeoclist.clear();
		directorfepadlist.clear();
		directortotallist.clear();
		directorHQlist.clear();

		log.info("*******Total number of EEOC directors:*******"
				+ diruser.size());
		for (int i = 0; i < diruser.size(); i++) {
			officeCode = diruser.get(i);
			log.info("Office Code Before Encryption =" + officeCode);
			email = this.certificationService.getDirectorEmail(officeCode);
			log.info("Director's Email:" + email);
			this.director = certificationService.getDirector(officeCode);
			this.director.setFepaDirectorVal("----");
			url = certificationService
					.getIMSAccountCertificationUrl(officeCode);
			String url_link = "<a href=" + url + ">Click this Link</a>";
			this.director.setUrlLink(url);
			directoreeoclist.add(director);
			log.info("*******EEOC director:******* " + directoreeoclist.size());
		}

		for (Director d1 : directoreeoclist)
			directortotallist.add(d1);

		List<String> diruser2 = this.certificationService.getAllFEPADirectors();

		log.info("*******Total number of FEPA directors:*******"
				+ diruser2.size());
		for (int i = 0; i < diruser2.size(); i++) {
			officeCode = diruser2.get(i);
			log.info("Office Code Before Encryption =" + officeCode);
			email = this.certificationService.getDirectorEmail(officeCode);
			log.info("Director's Email:" + email);
			this.director = certificationService.getDirector(officeCode);
			this.director.setFepaDirectorVal("FEPADirector");
			url = certificationService
					.getIMSAccountCertificationUrl(officeCode);
			this.director.setUrlLink(url);
			directorfepadlist.add(director);
			log.info("*******Total number of FEPA director:*******  "
					+ directorfepadlist.size());
		}

		for (Director d1 : directorfepadlist)
			directortotallist.add(d1);
		log.info("*******Total number of directors:*******"
				+ directortotallist.size());

		List<String> diruser3 = this.certificationService.getAllHQDirectors();
		// officeCode.equals("810") ||
		log.info("*******Total number of HQD directors:*******"
				+ diruser2.size());
		for (int i = 0; i < diruser3.size(); i++) {
			officeCode = diruser3.get(i);
			if (officeCode.equals("811") || officeCode.equals("812")
					|| officeCode.equals("814") || officeCode.equals("832"))
				continue;
			log.info("Office Code Before Encryption =" + officeCode);
			email = this.certificationService.getDirectorEmail(officeCode);
			log.info("Director's Email:" + email);
			this.director = certificationService.getDirector(officeCode);
			// this.director.setFepaDirectorVal("FEPADirector");
			url = certificationService
					.getIMSAccountCertificationUrl(officeCode);
			this.director.setUrlLink(url);
			directorHQlist.add(director);
			log.info("*******Total number of HQD director:*******  "
					+ directorHQlist.size());
		}

		for (Director d1 : directorHQlist)
			directortotallist.add(d1);

		log.info("*******Total number of directors:*******"
				+ directortotallist.size());

		/*
		 * for(Director d2 : directortotallist) {
		 * log.info("What is in the director list");
		 * log.info("What is in the director list " + d2.getDirectorName());
		 * log.info("What is in the director list " + d2.getDirectorTitle());
		 * log.info("What is in the director list " + d2.getEmail()); }
		 */

		/*
		 * Adding Emails to table
		 */

		// for(Person per: getPersonUserListLdap()) {
		// log.info("  Person Info from Ldap " );
		// log.info("   " );
		// log.info("Person info UserId  "+ per.getUserId());
		// log.info("Person info Fname  "+ per.getFullName());
		// log.info("Person info Lname  "+ per.getLastName());
		// log.info("Person info Office Code  "+ per.getOfficeCode() );
		// log.info("   " );
		// log.info("Let's check to see if shared access has ldap id "+
		// per.getUserId());

		// accessList =
		// this.certificationService.getSharedAccessDataByOracleUserId(per.getUserId().toString().toUpperCase());
		// for(String str : accessList)
		// log.info(" User access in ims from ldap  " + str);
		// log.info("   " );

		// }

		// log.info("check the ldap list size " +
		// getPersonUserListLdap().size());

		// ArrayList<Person> temp = new ArrayList<Person>();

		// setPersonUserListLdap(getPersonUserListLdap().subList(0, 20));
		// log.info("After getting sublist " + getPersonUserListLdap().size());

		// log.info("test ldap city ims map " +
		// LdapOfficeCodes.getLdapOfficeCityMap().get("MINNAO"));

		// return null;
		java.util.Collections.sort(directortotallist, new DirectorHelper());
	}

	public String generateDirectorTable() {
		
		 
		log.info("generateDirectorTable function () ");
		String valid = "";
		
		if (numOfAttempts >= CertificationConstants.maxAttempts) {
			 
			FacesMessage msg = new FacesMessage("You have exceeded the number of attempts. Please contact the helpdesk or try login after some time.","");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			invalidateSession();
			numOfAttempts = 0;
			return "login";
		}
		// go to database and validate.
		List<String> validUsers=this.certificationService.getIMSUtiliesAccessForOIT();
		 
		
		log.info("User name " + getUserName().toUpperCase());
		
		if (validUsers.contains(getUserName().toUpperCase())) {
		    log.info("The username is valid.");
		    log.info("Check if the password is valid " );
	 	    valid = certificationService.validateUser(getUserName(), getPassword());
	         log.info("Password is valid " +valid );
	 	
	           if (valid.equals("Y")) {
			     log.info("About to query Director list populate table.");
			     this.filteredDirectors = null;
			     getDirectorList();
			    log.info("Director list  generated.");
			    return "AdminPage?faces-redirect=true";
		        }
	           else {
				numOfAttempts++;
				FacesMessage msg = new FacesMessage("You have entered an invalid account information.", "");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return "login.jsf";
			   }
		}
		else
		{
			numOfAttempts++;
			FacesMessage msg = new FacesMessage("You have entered an invalid account information.", "");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "login.jsf";	
		}
	          
	}

	private void invalidateSession() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();
		HttpSession httpSession = request.getSession(false);
		if (httpSession != null) {
			httpSession.invalidate();
		}

	}

	private void sendEmail(String email, String from, String subject,
			String content) throws Exception {
		// String host = CertificationConstants.SMTP_SERVER;
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
			log.info("Confirmation email send successfully to:" + email);
		} catch (MessagingException mex) {
			mex.printStackTrace();
		} catch (Exception e) {

			log.info("Error while sending email to email address:" + email);
			e.printStackTrace();
		}
	}

	@PostConstruct
	public void initUserCertificationController() {

		init();

	}

	public void initialEmailProcess() {
		try {
			StringBuffer buf = new StringBuffer();
			buf.append("Dear ");
			buf.append(director.getDirectorName());
			buf.append(",<br/><br/>");
			buf.append("EEOC is required to annually recertify that the access levels assigned to users of major information systems continue to be appropriate for ");
			buf.append("their official business duties.  Therefore, we are conducting a mandatory re-certification of user access to EEOC's Integrated Mission System ");
			buf.append("(IMS) / Document Management System and Network.  To complete this review and re-certification, please follow the instructions below:");
			buf.append(".<br/><br/>");
			buf.append("1.	Access our on-line Account Certification application ");
			buf.append("<a href='");
			buf.append(url);
			buf.append("'>Click on this Link</a>");
			buf.append(". If the link doesn't work, please copy and paste this url:");
			buf.append("<br/>");
			buf.append(url);
			buf.append("<br/>");
			buf.append("2.	A list of the users in your office will be displayed.");
			buf.append("<br/>");
			buf.append("3.	Click on the 'IMS User Access Detail Report' link on the screen to see a detailed listing of the access levels assigned to each user.");
			buf.append("<br/>");
			buf.append("4.	If access should be removed for a particular user, please click the 'Remove Account' button by the user's name on the screen and enter a reason for removal. ");
			buf.append("<br/>");
			buf.append("5.	If access should be modified, for ims, please use the Account Certification Utilities Security module within IMS to modify access levels.");
			buf.append("<br/>");
			buf.append("6.	Once all accounts are reviewed and verified, press the Certify button.  This step must be completed by the Office Director and will ");
			buf.append("<br/>");
			buf.append("serve as the official certification for the year.");
			buf.append("<br/>");
			buf.append("7.	Upon completion of the certification, a confirmation email will be sent to the Office Director and IT Specialist.");
			buf.append("<br/><br/>");
			buf.append("Your action on this mandatory re-certification is requested by Friday, Sept 30, 2016.  If you have any questions, please contact the EEOC Helpdesk at");
			buf.append("<br/>");
			buf.append("202.663.4767 or eeoc.helpdesk@eeoc.gov.  Thank you for your compliance with this mandatory security requirement. ");
			buf.append("<br/><br/><br/>");
			buf.append("Pierrette McIntire");
			buf.append("<br/>");
			buf.append("Chief Information Security Officer");
			buf.append("<br/>");
			buf.append("U.S. Equal Employment Opportunity Commission");
			buf.append("<br/><br/>");
			buf.append("<i>This is an autogenerated email. Please do not reply to this email.</i>");

			sendEmail(email, CertificationConstants.FROM_EMAIL,
					"Annual Certification of IMS Accounts", buf.toString());
		} catch (Exception e) {
			log.error("Error while sending email ", e);
			// e.printStackTrace();
		}
	}

	public void singleEmailProcess(Director singleDirector) {

		try {
			StringBuffer buf = new StringBuffer();
			buf.append("Dear ");
			buf.append(singleDirector.getDirectorName());
			buf.append(",<br/><br/>");
			buf.append("EEOC is required to annually recertify that the access levels assigned to users of major information systems continue to be appropriate for ");
			buf.append("their official business duties.  Therefore, we are conducting a mandatory re-certification of user access to EEOC's Integrated Mission System ");
			buf.append("(IMS) / Document Management System (DMS) and  EEOC Network.  To complete this review and re-certification, please follow the instructions below:");
			buf.append(".<br/><br/>");
			buf.append("1.	Access our on-line Account Certification application ");
			buf.append("<a href='");
			buf.append(singleDirector.getUrlLink());
			buf.append("'>Click on this Link</a>");
			buf.append(". If the link doesn't work, please copy and paste this url:");
			buf.append("<br/>");
			buf.append(singleDirector.getUrlLink());
			buf.append("<br/>");
			buf.append("2.	A list of the user accounts in your office will be displayed.");
			buf.append("<br/>");
			buf.append("3. If you wish to see more detail on each IMS user account, you may click on the 'IMS User Access Detail Report' link on the screen to see a detailed listing of the access levels assigned to each IMS user.");
			buf.append("<br/>");
			buf.append("4. If access should be removed for a particular user, please click the 'Remove Account' button on the screen for the particular application and enter a reason for removal. ");
			buf.append("<br/>");
			buf.append("5.	If access should be modified,for IMS, please use the Account Certification Utilities Security module within IMS to modify access levels.");
			buf.append("<br/>");
			buf.append("For DMS and Network access please submit a Service-Now ticket. ");
			buf.append("<br/>");
			buf.append("6.	Once all accounts are reviewed and verified, press the Certify button.  This step must be completed by the Office Director and will ");
			buf.append("<br/>");
			buf.append("serve as the official certification for the year.");
			buf.append("<br/>");
			buf.append("7.	Upon completion of the certification, a confirmation email will be sent to the Office Director and IT Specialist.");
			buf.append("<br/><br/>");
			buf.append("Your action on this mandatory re-certification is requested by Friday, Sept 30, 2016.  If you have any questions, please contact the EEOC Helpdesk at");
			buf.append("<br/>");
			buf.append("202.663.4767 or eeoc.helpdesk@eeoc.gov.  Thank you for your compliance with this mandatory security requirement. ");
			buf.append("<br/><br/><br/>");
			buf.append("Pierrette McIntire");
			buf.append("<br/>");
			buf.append("Chief Information Security Officer");
			buf.append("<br/>");
			buf.append("U.S. Equal Employment Opportunity Commission");
			buf.append("<br/><br/>");
			buf.append("<i>This is an autogenerated email. Please do not reply to this email.</i>");

			sendEmail(singleDirector.getEmail(),
					CertificationConstants.FROM_EMAIL,
					"Annual Certification of User Accounts", buf.toString());

		} catch (Exception e) {
			log.error("Error while sending email ", e);
			// e.printStackTrace();
		}
	}

	public String test() {
		log.info("Test this");
		return "Success";
	}

	public boolean initialEmailProcessEEOC(ArrayList<Director> dirlist) {
		log.debug("initialEmailProcessEEOC list size " + dirlist.size());
		int count = 0;
		boolean checkEmailSuccess = true;
		for (Director dirc : dirlist) {
			if (dirc.getEmail() == null || dirc.getEmail().isEmpty())
				continue;
			count++;
			try {
				StringBuffer buf = new StringBuffer();
				buf.append("Dear ");
				buf.append(dirc.getDirectorName());
				buf.append(",<br/><br/>");
				buf.append("EEOC is required to annually recertify that the access levels assigned to users of major information systems continue to be appropriate for ");
				buf.append("their official business duties.  Therefore, we are conducting a mandatory re-certification of user access to EEOC's Integrated Mission System ");
				buf.append("(IMS) / Document Management System (DMS) and  EEOC Network.  To complete this review and re-certification, please follow the instructions below:");
				buf.append(".<br/><br/>");
				buf.append("1.	Access our on-line Account Certification application ");
				buf.append("<a href='");
				buf.append(dirc.getUrlLink());
				buf.append("'>Click on this Link</a>");
				buf.append(". If the link doesn't work, please copy and paste this url:");
				buf.append("<br/>");
				buf.append(dirc.getUrlLink());
				buf.append("<br/>");
				buf.append("2.	A list of the user accounts in your office will be displayed.");
				buf.append("<br/>");
				buf.append("3. If you wish to see more detail on each IMS user account, you may click on the 'IMS User Access Detail Report' link on the screen to see a detailed listing of the access levels assigned to each IMS user.");
				buf.append("<br/>");
				buf.append("4. If access should be removed for a particular user, please click the 'Remove Account' button on the screen for the particular application and enter a reason for removal. ");
				buf.append("<br/>");
				buf.append("5.	If access should be modified,for IMS, please use the Account Certification Utilities Security module within IMS to modify access levels.");
				buf.append("<br/>");
				buf.append("For DMS and Network access please submit a Service-Now ticket. ");
				buf.append("<br/>");
				buf.append("6.	Once all accounts are reviewed and verified, press the Certify button.  This step must be completed by the Office Director and will ");
				buf.append("<br/>");
				buf.append("serve as the official certification for the year.");
				buf.append("<br/>");
				buf.append("7.	Upon completion of the certification, a confirmation email will be sent to the Office Director and IT Specialist.");
				buf.append("<br/><br/>");
				buf.append("Your action on this mandatory re-certification is requested by Friday, Sept 30, 2016.  If you have any questions, please contact the EEOC Helpdesk at");
				buf.append("<br/>");
				buf.append("202.663.4767 or eeoc.helpdesk@eeoc.gov.  Thank you for your compliance with this mandatory security requirement. ");
				buf.append("<br/><br/><br/>");
				buf.append("Pierrette McIntire");
				buf.append("<br/>");
				buf.append("Chief Information Security Officer");
				buf.append("<br/>");
				buf.append("U.S. Equal Employment Opportunity Commission");
				buf.append("<br/><br/>");
				buf.append("<i>This is an autogenerated email. Please do not reply to this email.</i>");

				sendEmail(dirc.getEmail(), CertificationConstants.FROM_EMAIL,
						"Annual Certification of User Accounts", buf.toString());
				log.debug("Email sent " + count);

			} catch (Exception e) {
				log.error("Error while sending email ", e);
				// e.printStackTrace();
				checkEmailSuccess = false;

			}

		}

		log.debug("initialEmailProcessEEOC complete");
		return checkEmailSuccess;
	}

	public void setLoadDataVal() {
		setLoadData(true);
	}

	public boolean isLoadData() {
		return loadData;
	}

	public void setLoadData(boolean loadData) {
		this.loadData = loadData;
	}

}
