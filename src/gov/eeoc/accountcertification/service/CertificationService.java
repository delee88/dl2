package gov.eeoc.accountcertification.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import gov.eeoc.accountcertification.model.Collection;
import gov.eeoc.accountcertification.entity.AccountCertificationReport;
import gov.eeoc.accountcertification.entity.DmsKuafView;
import gov.eeoc.accountcertification.entity.LiveLinkKuaf;
import gov.eeoc.accountcertification.entity.SharedAccess;
import gov.eeoc.accountcertification.entity.SharedStaff;
import gov.eeoc.accountcertification.entity.SharedOfficeInfo;
import gov.eeoc.accountcertification.model.AccountCertReport;
import gov.eeoc.accountcertification.model.DMSUsers;
import gov.eeoc.accountcertification.model.Director;
import gov.eeoc.accountcertification.model.MappedLdapAndDBUserList;
import gov.eeoc.accountcertification.model.Person;
import gov.eeoc.accountcertification.model.User;
import gov.eeoc.accountcertification.util.DmsEmailMap;
import gov.eeoc.accountcertification.util.SystemUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
@Stateless
public class CertificationService {

	@PersistenceContext(unitName = "CertificationUnit")
	private EntityManager em;
	private static Logger log = Logger.getLogger(CertificationService.class);
	@SuppressWarnings("unchecked")
	public long getTotalCount() {
		Query query = em.createQuery("SELECT COUNT(c) FROM SharedStaff c");
		long OfficeCount = (Long) query.getSingleResult();
		return OfficeCount;
	}
	
	private URL url;
	
	public void getLdapWebServiceEeocUser(String str) {
		try {
		 url = new URL("http://10.9.23.42:8080/ldapws/rest/access/search/staff?word="+str);
		 log.info("Rest connection successfull");
		 HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		 log.info("Establish httpUrlConnection");

		 conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/xml");
			if (conn.getResponseCode() != 200) {
				 log.info("Failed : HTTP error code " + conn.getResponseCode());

				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
			 log.info("Reading Input Stream ");

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
			 log.info("Successfulluy read data ");
			conn.disconnect();
			 log.info("Disconnect from webservice ");
			
		 log.info(url.getContent());
		}
		catch(Exception ex) {
			 ex.printStackTrace();
			
		}
		 
	}
	
	
	public List<Person> getLdapUserList(String xmlList) {
		InputStream isList = new ByteArrayInputStream(xmlList.getBytes());
		 List<Person> per = null;
		try {
			
		JAXBContext jaxbContext = JAXBContext.newInstance(Collection.class); 
		System.out.println(" JAXBContext.newInstance(Collection.class); ");
		
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		System.out.println(" jaxbContext.createUnmarshaller(); ");
		
		Collection collection = (Collection) jaxbUnmarshaller.unmarshal(isList);
		isList.close();
		System.out.println(" (collection) jaxbUnmarshaller.unmarshal(file); ");
		
		System.out.println(" Test collection ");
		System.out.println("Size if collection " + collection.getPersonList().size());
		
		per = collection.getPersonList();
		 
			System.out.println(" Complete getLdapUserList");
		
	} catch (JAXBException e) {
		e.printStackTrace();
	  } catch (IOException e) {
		  
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return per;
		
	}
	
	
	 public String getLdapWebServiceAllStaff() throws IOException {
		 StringBuilder sb = null;
		 String temp  = "";
		 BufferedReader br = null;
		 HttpURLConnection conn = null;
		 String error = "Error";
		try {   
			 
		    url = new URL("http://10.9.23.42:8080/ldapws/rest/access/get/staff/all");
		    log.info("Making URL Rest Connection http://localhost:8080/ldapws/rest/access/get/staff/all");
		    
		    if( url != null) 
		    conn = (HttpURLConnection) url.openConnection();
		    else
		    {
		    	log.info("Url failed http://10.9.23.42:8080/ldapws/rest/access/get/staff/all");
				log.info("Rest service may not be functioning");
		    }
		    if(conn != null) {
		    log.info("Establish httpUrlConnection http://10.9.23.42:8080/ldapws/rest/access/get/staff/all");
		     conn.setRequestMethod("GET");
			 conn.setRequestProperty("Accept", "application/xml");
		     	if (conn.getResponseCode() != 200) {
			     	 System.out.println("Failed : HTTP error code " + conn.getResponseCode());
			     	throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			    }
			     br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
			        log.info("Reading Input Stream ");
			
			      String xmlLine;
			      int index;
			      int counter = 1;
			      sb = new StringBuilder();
			      while ((xmlLine = br.readLine()) != null) {
			    	  sb.append(xmlLine);
			      		}
     			 log.info("Successfully read data ");
	     		  br.close();
	     		 log.info("Close Buffered Stream");
		    	 conn.disconnect();
		    	 log.info("Close Connection");
		         log.info("Disconnect from webservice ");
		    }
		    else {
		    	 log.info("HttpUrlConnection http://10.9.23.42:8080/ldapws/rest/access/get/staff/all failed Check ldap webservice is running ");  
		    }
		}
		catch(IOException io ){
			io.printStackTrace();
		}
		catch(Exception ex) {
			 ex.printStackTrace();
		}
		finally {
		        if(sb != null) {
				 br.close();
			     log.info("Close Buffered Stream");
				// TODO Auto-generated catch block		 
			     conn.disconnect();
			     log.info("Close Connection");
		        }
		}
		//System.out.println(sb.toString());
		log.info("Return ldap xml");
		
		if(sb != null)
		 return sb.toString();
		else
			return "blank";
	}
	 
	 
	 public String getLdapWebServiceStaffByOfficeCode(String code) throws IOException {
		 StringBuilder sb = null;
		 String temp  = "";
		 BufferedReader br = null;
		 HttpURLConnection conn = null;
		 String error = "Error";
		    String webServiceUrl = getLdapWebServiceUrl()+"/get/staff?officecode="+code;

		try {   
			 
		  //  url = new URL("http://10.9.23.42:8080/ldapws/rest/access/get/staff?officecode="+code);
 			 url = new URL(webServiceUrl);
			log.info("Making URL Rest Connection   " + webServiceUrl);
		    
		    if( url != null) 
		    conn = (HttpURLConnection) url.openConnection();
		    else
		    {
		    	log.info(webServiceUrl);
				log.info(webServiceUrl);
		    }
		    if(conn != null) {
		    log.info("Establish httpUrlConnection " + webServiceUrl);
		     conn.setRequestMethod("GET");
			 conn.setRequestProperty("Accept", "application/xml");
		     	if (conn.getResponseCode() != 200) {
			     	 System.out.println("Failed : HTTP error code " + conn.getResponseCode());
			     	throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			    }
			     br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
			        log.info("Reading Input Stream ");
			
			      String xmlLine;
			      int index;
			      int counter = 1;
			      sb = new StringBuilder();
			      while ((xmlLine = br.readLine()) != null) {
			    	  sb.append(xmlLine);
			      		}
     			 log.info("Successfully read data ");
	     		  br.close();
	     		 log.info("Close Buffered Stream");
		    	 conn.disconnect();
		    	 log.info("Close Connection");
		         log.info("Disconnect from webservice ");
		    }
		    else {
		    	 log.info("HttpUrlConnection " +  webServiceUrl + " failed Check ldap webservice is running ");  
		    }
		}
		catch(IOException io ){
			io.printStackTrace();
		}
		catch(Exception ex) {
			 ex.printStackTrace();
		}
		finally {
		        if(sb != null) {
				 br.close();
			     log.info("Close Buffered Stream");
				// TODO Auto-generated catch block		 
			     conn.disconnect();
			     log.info("Close Connection");
		        }
		}
		//System.out.println(sb.toString());
		log.info("Return ldap xml");
		
		if(sb != null)
		 return sb.toString();
		else
			return "blank";
	}
	
 public List<Person> convertLdapStaffXmlByOfficeCodeToList(String code) {
		 
		 String  xml = "";
		try {
	           xml = getLdapWebServiceStaffByOfficeCode(code);
			}
			catch(IOException ex){
				ex.printStackTrace();
			}
			 if(!xml.equals("blank")) { 
              return getLdapUserList(xml.trim());
			 }
			 else
			 {
		    log.info(" certificationService.convertLdapStaffXmlByOfficeCodeToList did not generate xml list");
		    log.info("return empty list");
		    return new ArrayList<Person>();
			 }
	 }
	 
	 public List<Person> convertLdapAllStaffXmlToList() {
		 
		 String  xml = "";
		try {
	           xml = getLdapWebServiceAllStaff();
			}
			catch(IOException ex){
				ex.printStackTrace();
			}
			 if(!xml.equals("blank")) { 
              return getLdapUserList(xml.trim());
			 }
			 else
			 {
		    log.info(" certificationService.convertLdapAllStaffXmlToList did not generate xml list");
		    log.info("return empty list");
		    return new ArrayList<Person>();
			 }
	 }
		 
	 
	 List<MappedLdapAndDBUserList> createLdapAndIMSOfficeCodeMap(String officeCode){
		 
		 return null;
		 
	 }
	 

	 public String getDirectorCity(String officeCode) {
			if (officeCode == null)
				return null;
			String str = "select s.city from SharedOfficeInfo s where "
					+ "s.officeCode=:arg1";
			Query query = em.createQuery(str);
			query.setParameter("arg1", officeCode);
			String city = (String) query.getSingleResult();
			return city;
		}
	 
	
	public String getOfficeDescription(String officeCode) {
		if (officeCode == null)
			return "";
		String str = "select s.description from SharedCode s where "
				+ "s.code=:arg1 and " + "s.domain =:arg2";
		Query query = em.createQuery(str);
		query.setParameter("arg1", officeCode);
		query.setParameter("arg2", "ACO");
		String OfficeDescription = (String) query.getSingleResult();
		return OfficeDescription;
	}

	public Date getCertificationDate(String officeCode) {
		if (officeCode == null)
			return null;
		String str = "select s.certificationDate from SharedOfficeInfo s where "
				+ "s.officeCode=:arg1";
		Query query = em.createQuery(str);
		query.setParameter("arg1", officeCode);
		Date certificationDate = (Date) query.getSingleResult();
		return certificationDate;
	}

	public String getDirectorName(String officeCode) {
		if (officeCode == null)
			return "";
		String str = "select s.firstName||' '||s.lastName "
				+ "from SharedStaff s ,SharedOfficeInfo p "
				+ "where p.directorStaffSeq = s.staffSeq and "
				+ "p.officeCode=:arg1";
		Query query = em.createQuery(str);
		query.setParameter("arg1", officeCode);
		String DirectorName = (String) query.getSingleResult();
		return DirectorName;
	}

	public String getDirectorEmail(String officeCode) {
		if (officeCode == null)
			return "";
		String str = "select s.emailAddress "
				+ "from SharedStaff s ,SharedOfficeInfo p "
				+ "where p.directorStaffSeq = s.staffSeq and "
				+ "p.officeCode=:arg1";
		Query query = em.createQuery(str);
		query.setParameter("arg1", officeCode);
		String directorEmail = (String) query.getSingleResult();
		return directorEmail;
	}

	public String getDirectorTitle(String officeCode) {
		if (officeCode == null)
			return "";
		String str = "select s.title "
				+ "from SharedStaff s ,SharedOfficeInfo p "
				+ "where p.directorStaffSeq = s.staffSeq and "
				+ "p.officeCode=:arg1";
		Query query = em.createQuery(str);
		query.setParameter("arg1", officeCode);
		String directorTitleCode = (String) query.getSingleResult();
		String str1 = "select s.description " + "from SharedCode s  "
				+ "where s.code = :arg2 and " + "s.domain= :arg3 ";
		Query query1 = em.createQuery(str1);
		query1.setParameter("arg2", directorTitleCode);
		query1.setParameter("arg3", "STF");
		List<String> directorTitleList =  query1.getResultList();
		if( directorTitleList.isEmpty()){
			return "";
		}else
		return directorTitleList.get(0);
	}

	public Long getDirectorStaffSeq(String officeCode) {
		log.info(" Certification Service getDirectorStaffSeq officeCode " +officeCode);
		if (officeCode == null)
			return 0L;
		String str = "select p.directorStaffSeq "
				+ "from SharedStaff s ,SharedOfficeInfo p "
				+ "where p.directorStaffSeq = s.staffSeq and "
				+ "p.officeCode=:arg1";
		
		log.info(" Certification Service getDirectorStaffSeq query string  " + str);

		Query query = em.createQuery(str);
		query.setParameter("arg1", officeCode);
		Long DirectorStaffSeq = (Long) query.getSingleResult();
		log.info(" Certification Service getDirectorStaffSeq officeCode Complete " );

		return DirectorStaffSeq;
	}

	
	
	private ArrayList<DMSUsers> dmsList = new ArrayList<DMSUsers>();	
	
	public ArrayList<DMSUsers> getDmsList() {
		return dmsList;
	}
	public void setDmsList(ArrayList<DMSUsers> dmsList) {
		this.dmsList = dmsList;
	}
 
	public List<DMSUsers> getDMSLiveLinkData1() {
		log.info(" getDMSLiveLinkData ");
		List<String> dmsUsers = new ArrayList<String>(1300);
		// use entity manager to retrieve named query
				TypedQuery<LiveLinkKuaf> dmsResults = em.createNamedQuery(
						"LiveLinkKuaf.findAll", LiveLinkKuaf.class);
				
				 				  log.info(" getFirstResult  "+ dmsResults.getFirstResult() );
				 				 
				  List<LiveLinkKuaf> dmsLiveLink = dmsResults.getResultList();
				
				  log.info(" dmsLiveLink.size()  "+ dmsLiveLink.size() );
				  
				for (Iterator iterator = dmsLiveLink.iterator(); iterator.hasNext();)  {
					LiveLinkKuaf ll = (LiveLinkKuaf) iterator.next();
					if(ll == null)
						continue;
				if(  ll.getMailaddress() == null ) 
				{
					log.info(" Some null value returned ");
					continue;
				}
				else
				{
				if (  ! (  ll.getMailaddress().isEmpty() )   ) 
				{
				//	log.info("email address list size  " + ll.getMailaddress().toUpperCase() + "  " + dmsList.size());
					DMSUsers  tdms = new 
					DMSUsers(  " ", 
					 " ", " ",
					ll.getMailaddress().toUpperCase() , " " );
					dmsList.add(tdms);   
				    }
				else
				{
				continue;	
				}
		   } 
		}  // for loop
				 log.info(" LiveLinkKuaf.findAll  returning dms list size  " + dmsList.size());
				return dmsList;
	}
	
	
	
	public List<DMSUsers> getDMSUserLocationDataByDept(String location) {
		log.info(" getDMSUserData1 ");
		List<String> dmsUsers = new ArrayList<String>(1300);
		log.info(" getDMSUserData2 ");
		// use entity manager to retrieve named query
		
		/*
				TypedQuery<DmsKuafView> dmsResults = em.createNamedQuery(
						"dmsUser.findAll", DmsKuafView.class);
				
				 				  log.info(" getFirstResult  "+ dmsResults.getFirstResult() );
				 				 
				  List<DmsKuafView> dmsuse = dmsResults.getResultList();
				*/
		  if(location == null){
			log.info("DMS city code is null return empty list ");
			  return new ArrayList<DMSUsers>();
		  }
		ArrayList<DMSUsers> dmArr = new ArrayList<DMSUsers>();
		log.info(" getDMSUserData3 ");
		String loc = location.trim();
		   Query query = em.createNativeQuery("select * from dms_users where officelocation like '"+"%"+loc+"%"+"'" );
		   log.info(" getDMSUserData4 ");
		   if(query == null || query.getResultList().isEmpty())
         	  return new ArrayList<DMSUsers>();
		   
		   List<Object[]> dmsuse = query.getResultList();
		                  
		                  
				  log.info(" dms_users.size()  "+ query.getResultList().size() );
				  
				  for(int x = 0; x < dmsuse.size(); x++) {
					    
					//  log.info("count " + x);
					  
				     DMSUsers us = new DMSUsers();
				     
					if(dmsuse.get(x) == null)  {
					log.info("dmsuse.get(x) null");
						continue;
					
					}
					else {
						 
				     Object[] dmsArr = dmsuse.get(x);
					
					if(dmsArr[0] == null || dmsArr[0].toString().isEmpty() || dmsArr[0].toString().length() == 0) 
					{
						log.info("dmsArr[0] null");
						continue;
					}
						else
					{
						us.setLastName(dmsArr[0].toString());
			//			log.info("dmsArr[0] " +dmsArr[0].toString());
					}
					if(dmsArr[1] == null || dmsArr[1].toString().isEmpty() || dmsArr[1].toString().length() == 0) 
					{ log.info("dmsArr[1] null");
						continue;
					}
						else
					{
						us.setFirstName(dmsArr[1].toString());
				//		log.info("dmsArr[1] " +dmsArr[1].toString());
					}
					if(dmsArr[2] == null || dmsArr[2].toString().isEmpty() || dmsArr[2].toString().length() == 0) 
					{
						log.info("dmsArr[2] null");
						continue;
					}
						else
					{
						us.setLoginid(dmsArr[2].toString());
				//		log.info("dmsArr[2] " +dmsArr[2].toString());
					}
					
					if(dmsArr[3] == null || dmsArr[3].toString().isEmpty() || dmsArr[3].toString().length() == 0) 
					{
						log.info("dmsArr[3] null");
						continue;
					}
						else
					{
						us.setOfficelocation(dmsArr[3].toString());
				//		log.info("dmsArr[3] " +dmsArr[3].toString());
					}
					
					if(dmsArr[4] == null || dmsArr[4].toString().isEmpty() || dmsArr[4].toString().length() == 0) 
					{
						log.info("dmsArr[4] null");
						continue;
					}
					else
					{
						us.setMailaddress(dmsArr[4].toString());
				//		log.info("dmsArr[4] " +dmsArr[4].toString());
					}
					 	 // log.info("About to add to dms list");
					dmArr.add(us);
				//	 log.info("Added to dms list");
					}
				 }
		
				  
				 
				  List<DMSUsers> retDMS = dmArr;
				  log.info("Size of returned dms list " + retDMS.size());
				  
 				  return retDMS;
	}
	
	
	public List<DMSUsers> getDMSUserLocationDataByCity(String location) {
		log.info(" getDMSUserData1 ");
		List<String> dmsUsers = new ArrayList<String>(1300);
		
		log.info(" getDMSUserData1 1");
		// use entity manager to retrieve named query
		
		/*
				TypedQuery<DmsKuafView> dmsResults = em.createNamedQuery(
						"dmsUser.findAll", DmsKuafView.class);
				
				 				  log.info(" getFirstResult  "+ dmsResults.getFirstResult() );
				 				 
				  List<DmsKuafView> dmsuse = dmsResults.getResultList();
				*/
		ArrayList<DMSUsers> dmArr = new ArrayList<DMSUsers>();
		log.info(" getDMSUserData1 2");
		String loc = location.toLowerCase().trim();
		log.info(" getDMSUserData1 3");
		log.info(" getDMSUserLocationDataByCity City value in " + loc);
		log.info(" getDMSUserData1 4");
		   Query query = em.createNativeQuery("select * from dms_users where lower(officelocation) like '"+"%"+loc+"%"+"'" );
		     List<Object[]> dmsuse = query.getResultList();
		
				  log.info(" dms_users.size()  "+ query.getResultList().size() );
				  
				  for(int x = 0; x < dmsuse.size(); x++) {
					    
					//  log.info("count " + x);
					  
				     DMSUsers us = new DMSUsers();
				     
					if(dmsuse.get(x) == null)  {
					log.info("dmsuse.get(x) null");
						continue;
					
					}
					else {
						 
				     Object[] dmsArr = dmsuse.get(x);
					
					if(dmsArr[0] == null || dmsArr[0].toString().isEmpty() || dmsArr[0].toString().length() == 0) 
					{
						log.info("dmsArr[0] null");
						continue;
					}
						else
					{
						us.setLastName(dmsArr[0].toString());
			//			log.info("dmsArr[0] " +dmsArr[0].toString());
					}
					if(dmsArr[1] == null || dmsArr[1].toString().isEmpty() || dmsArr[1].toString().length() == 0) 
					{ log.info("dmsArr[1] null");
						continue;
					}
						else
					{
						us.setFirstName(dmsArr[1].toString());
				//		log.info("dmsArr[1] " +dmsArr[1].toString());
					}
					if(dmsArr[2] == null || dmsArr[2].toString().isEmpty() || dmsArr[2].toString().length() == 0) 
					{
						log.info("dmsArr[2] null");
						continue;
					}
						else
					{
						us.setLoginid(dmsArr[2].toString());
				//		log.info("dmsArr[2] " +dmsArr[2].toString());
					}
					
					if(dmsArr[3] == null || dmsArr[3].toString().isEmpty() || dmsArr[3].toString().length() == 0) 
					{
						log.info("dmsArr[3] null");
						continue;
					}
						else
					{
						us.setOfficelocation(dmsArr[3].toString());
				//		log.info("dmsArr[3] " +dmsArr[3].toString());
					}
					
					if(dmsArr[4] == null || dmsArr[4].toString().isEmpty() || dmsArr[4].toString().length() == 0) 
					{
						log.info("dmsArr[4] null");
						continue;
					}
					else
					{
						us.setMailaddress(dmsArr[4].toString());
				//		log.info("dmsArr[4] " +dmsArr[4].toString());
					}
					 	 // log.info("About to add to dms list");
					dmArr.add(us);
				//	 log.info("Added to dms list");
					}
				 }
		
				  
				 
				  List<DMSUsers> retDMS = dmArr;
				  log.info("Size of returned dms list " + retDMS.size());
				  
 				  return retDMS;
	}
	
	
	public List<DMSUsers> getDMSUserData1() {
		log.info(" getDMSUserData1 ");
		List<String> dmsUsers = new ArrayList<String>(1300);
		// use entity manager to retrieve named query
		
		/*
				TypedQuery<DmsKuafView> dmsResults = em.createNamedQuery(
						"dmsUser.findAll", DmsKuafView.class);
				
				 				  log.info(" getFirstResult  "+ dmsResults.getFirstResult() );
				 				 
				  List<DmsKuafView> dmsuse = dmsResults.getResultList();
				*/
		ArrayList<DMSUsers> dmArr = new ArrayList<DMSUsers>();
		
		   Query query = em.createNativeQuery("select * from dms_users" );
		     List<Object[]> dmsuse = query.getResultList();
		
				  log.info(" dms_users.size()  "+ query.getResultList().size() );
				  
				  for(int x = 0; x < dmsuse.size(); x++) {
					    
					//  log.info("count " + x);
					  
				     DMSUsers us = new DMSUsers();
				     
					if(dmsuse.get(x) == null)  {
					log.info("dmsuse.get(x) null");
						continue;
					
					}
					else {
						 
				     Object[] dmsArr = dmsuse.get(x);
					
					if(dmsArr[0] == null || dmsArr[0].toString().isEmpty() || dmsArr[0].toString().length() == 0) 
					{
						log.info("dmsArr[0] null");
						continue;
					}
						else
					{
						us.setLastName(dmsArr[0].toString());
			//			log.info("dmsArr[0] " +dmsArr[0].toString());
					}
					if(dmsArr[1] == null || dmsArr[1].toString().isEmpty() || dmsArr[1].toString().length() == 0) 
					{ log.info("dmsArr[1] null");
						continue;
					}
						else
					{
						us.setFirstName(dmsArr[1].toString());
				//		log.info("dmsArr[1] " +dmsArr[1].toString());
					}
					if(dmsArr[2] == null || dmsArr[2].toString().isEmpty() || dmsArr[2].toString().length() == 0) 
					{
						log.info("dmsArr[2] null");
						continue;
					}
						else
					{
						us.setLoginid(dmsArr[2].toString());
				//		log.info("dmsArr[2] " +dmsArr[2].toString());
					}
					
					if(dmsArr[3] == null || dmsArr[3].toString().isEmpty() || dmsArr[3].toString().length() == 0) 
					{
						log.info("dmsArr[3] null");
						continue;
					}
						else
					{
						us.setOfficelocation(dmsArr[3].toString());
				//		log.info("dmsArr[3] " +dmsArr[3].toString());
					}
					
					if(dmsArr[4] == null || dmsArr[4].toString().isEmpty() || dmsArr[4].toString().length() == 0) 
					{
						log.info("dmsArr[4] null");
						continue;
					}
					else
					{
						us.setMailaddress(dmsArr[4].toString().toUpperCase());
				//		log.info("dmsArr[4] " +dmsArr[4].toString());
					}
					 	 // log.info("About to add to dms list");
					dmArr.add(us);
				//	 log.info("Added to dms list");
					}
				 }
		
				  
				  /*
					if(ll == null)
						continue;
					
				if(  ll == null ) 
				{
					log.info(" Some null value returned ");
					continue;
				}
				else
				{
				if (  ! (  (String)ll[0].isEmpty() )   ) 
				{
				//	log.info("email address list size  " + ll.getMailaddress().toUpperCase() + "  " + dmsList.size());
					DMSUsers  tdms = new 
					DMSUsers(  " ", 
					 " ", " ",
					ll.getMailaddress().toUpperCase() , " " );
					dmsList.add(tdms);   
				    }
				else
				{
				continue;	
				}
		 */
				  List<DMSUsers> retDMS = dmArr;
				  log.info("Size of returned dms list " + retDMS.size());
				  
 				  return retDMS;
	}
	
	public List<DMSUsers> getTestingEntityMapByView(String loc) {
		log.info(" getDMSLiveLinkData ");
		List<String> dmsUsers = new ArrayList<String>(1300);
		// use entity manager to retrieve named query
		
	                 loc = loc.toLowerCase().trim();
				TypedQuery<DmsKuafView> dmsResultsQueryByLocation = em.createNamedQuery(
						"dmsUser.findBylocation", DmsKuafView.class);
				
				dmsResultsQueryByLocation.setParameter("officelocation","%"+ loc+"%");
				 
				// execute query and get results
				 
				
				 				  log.info(" getDMSUserLocationData1  "+ dmsResultsQueryByLocation.getFirstResult() );
				 				 
				  List<DmsKuafView> dmsuse = dmsResultsQueryByLocation.getResultList();
				
				  log.info(" getDMSUserLocationData1.size()  "+ dmsuse.size() );
				  
				for (Iterator iterator = dmsuse.iterator(); iterator.hasNext();)  {
					DmsKuafView ll = (DmsKuafView) iterator.next();
					if(ll == null)
						continue;
				if(  ll.getMailaddress() == null ) 
				{
					log.info(" Some null value returned ");
					continue;
				}
				else
				{
				if (  ! (  ll.getMailaddress().isEmpty() )   ) 
				{
				//	log.info("email address list size  " + ll.getMailaddress().toUpperCase() + "  " + dmsList.size());
					DMSUsers  tdms = new 
					DMSUsers(  " ", 
					 " ", " ",
					ll.getMailaddress().toUpperCase() , " " );
					dmsList.add(tdms);   
				    }
				else
				{
				continue;	
				}
		   } 
		}  // for loop
				 log.info(" dmsUser.findBylocation  returning dmsUser list size  " + dmsList.size());
				return dmsList;
	}
	
	
	public DmsEmailMap generateDMSEmailMap() {
		log.info("call generateDMSEmailMap ");
		List<DMSUsers> tdmsusers =   getDMSUserData1(); //getDMSLiveLinkData1();	   
		log.info("Size of list to be mapped " + tdmsusers.size());
		  DmsEmailMap tmap = new DmsEmailMap();
	       for (DMSUsers tdms : tdmsusers ) {
	    	//   String temail = getContactInfoFromEmail(tdms.getMailaddress());
	    	   String temail = tdms.getMailaddress();
	    	 //  log.info(" getContactInfoFromEmail " + temail);
 			    	     if (!temail.equals("blank") && !tmap.getDmsmap().containsKey(temail) ) {
 			    	    	 temail = temail.toUpperCase();
 			    	    	// log.info("DMS Email key for map " + temail);
 			    	    	 tmap.getDmsmap().put(temail, tdms);
 				             }
 			    	     else {
 			    	  //  	log.info("temail is blank ");
 			    	    	 continue;
 			    	     }
 		      }
 				
	       log.info("Size of the map " +tmap.getDmsmap().size());
				return tmap;
	}
	
	
public DmsEmailMap generateDMSEmailLocationMap(String location) {
		
		List<DMSUsers> tdmsusers =   getDMSUserLocationDataByCity(location); //getDMSLiveLinkData1();	   
		  DmsEmailMap tmap = new DmsEmailMap();
	       for (DMSUsers tdms : tdmsusers ) {
	    	  String temail = getContactInfoFromEmail(tdms.getMailaddress());
	    	 //  String temail = tdms.getMailaddress();
	    	   log.info("generateDMSEmailLocationMap  "+ temail);   
 			    	     if (!temail.equals("blank") && !tmap.getDmsmap().containsKey(temail) ) {
 			    	    	 temail = temail.toUpperCase();
 			    	    	// log.info("DMS Email key for map " + temail);
 			    	    	 tmap.getDmsmap().put(temail, tdms);
 				             }
 			    	     else
 			    	    	 continue;
 		      }
 				
	       log.info("Size of the map " +tmap.getDmsmap().size());
				return tmap;
	}

	
	public List<String> getSharedAccessDataByOracleUserId(String userId) {

		List<String> users = new ArrayList<String>();

		// use entity manager to retrieve named query
		TypedQuery<SharedAccess> queryStaffByOfficeCode = em.createNamedQuery(
				"SharedAccess.findByOracleUserId", SharedAccess.class);
		// set dynamic data for query
		queryStaffByOfficeCode.setParameter("oracleUserId", userId);
		 
		// execute query and get results
		List<SharedAccess> staff = queryStaffByOfficeCode.getResultList();
		//log.info("shared_access  size "+ staff.size() );
		for (Iterator iterator = staff.iterator(); iterator.hasNext();) {
			
			SharedAccess sa = (SharedAccess) iterator.next();
		   // log.info(" Oracle User id " + sa.getOracleUserId());
		                   users.add(sa.getOracleUserId());
		} 
                return users;
	}
	
	// used by application to query ims users
	public List<User> getListofImsUsersActiveByOfficeCode(String officeCode) {
   log.info(" Begin CertificationService getListofImsUsersActiveByOfficeCode  ");
		List<User> users = new ArrayList<User>();
		// use entity manager to retrieve named query
		//TypedQuery<SharedStaff> queryStaffByOfficeCode = em.createNamedQuery(
			//	"SharedStaff.findActiveByOfficeCode", SharedStaff.class);
		
		TypedQuery<SharedStaff> queryStaffByOfficeCode = em.createNamedQuery(
					"SharedStaff.findByOfficeCode", SharedStaff.class);
		// set dynamic data for query
		queryStaffByOfficeCode.setParameter("officeCode", officeCode);
		
		 
		//queryStaffByOfficeCode.setParameter("status", "A");  no longer need this, we are matching by deleted column in shared_access 
		// execute query and get results
		List<SharedStaff> staff = queryStaffByOfficeCode.getResultList();
		log.info(" CertificationService getListofImsUsersActiveByOfficeCode  size " +staff.size() );
		 int counter = 0;
		for (Iterator iterator = staff.iterator(); iterator.hasNext();)  {
			SharedStaff ss = (SharedStaff) iterator.next();
			SharedAccess sa = getStaffAccessByStaffSeq(ss.getStaffSeq());
			if (sa == null)
				continue;
			   
			   else {
				   
	           if (sa.getDeleted().equals("A"))   { 			   
	    	    Long directorStaffSeq = getDirectorStaffSeq(officeCode);
			    User user = new User(ss.getStaffSeq(), ss.getFirstName(),
			    ss.getLastName());
			    String temp = "";
			    user.setStaffSeqDeletedVal(sa.getDeleted());	
			    user.setImsAccess("A");
			      if(ss.getEmailAddress() != null && !ss.getEmailAddress().isEmpty() ) { 
			        user.setEmail(ss.getEmailAddress().toUpperCase());
			      }
			      else
			      {
				   log.info(" getListofImsUsersActiveByOfficeCode " + " bad email address  " + ss.getEmailAddress() + " <-- Email address ");
				   //user.setEmail("NULL");
			      }
			  user.setStaffSeq(ss.getStaffSeq());
 			//  user.setNetAccess(sa.getOracleUserId().trim().toUpperCase());
			  
 			  user.setPrivateRole(sa.getPrivateRole());
			  user.setFederalRole(sa.getFederalRole());
			  user.setLitigationRole(sa.getLitigationRole());
			  user.setOutreachRole(sa.getOutreachRole());
			  user.setAppealsRole(sa.getAppealsRole());
			  user.setUtiliy(sa.getUtility());
			  user.setDirectorStaffSeq(directorStaffSeq); 
			  users.add(user);
			     // log.info("head Quarter User " + users.get(counter).getFirstName() );
			      // log.info("head Quarter User " + users.get(counter).getEmail() );
	                                 counter++;
	           }
	        
			}
			
	     }
		
		log.info("Size of user list returned from getListofImsUsersActiveByOfficeCode and Status " + users.size());
		return users;

	}
	
	
	public List<User> getListofImsUsersActiveBySelectedOfficeCode(List<String> officeCode) {
		   log.info(" Begin CertificationService getListofImsUsersActiveByOfficeCode  ");
				List<User> users = new ArrayList<User>();
				// use entity manager to retrieve named query
				//TypedQuery<SharedStaff> queryStaffByOfficeCode = em.createNamedQuery(
					//	"SharedStaff.findActiveByOfficeCode", SharedStaff.class);
				
				
				for(String str :officeCode) {
					System.out.println("These are the Special  office codes " + str);
				}
				
				TypedQuery<SharedStaff> queryStaffByOfficeCode = em.createNamedQuery(
						"SharedStaff.findBySelectedOfficeCode", SharedStaff.class);
				// set dynamic data for query
				queryStaffByOfficeCode.setParameter("officeCodeList", officeCode);
				
				
			//	TypedQuery<SharedStaff> queryStaffByOfficeCode = (TypedQuery<SharedStaff>) em.createNativeQuery("SELECT s FROM SharedStaff s WHERE s.officeCode in :officeCodeList", SharedStaff.class);
				//queryStaffByOfficeCode.setParameter("officeCodeList", officeCode);
				
				 
				//queryStaffByOfficeCode.setParameter("status", "A");  no longer need this, we are matching by deleted column in shared_access 
				// execute query and get results
				List<SharedStaff> staff = queryStaffByOfficeCode.getResultList();
				log.info(" CertificationService getListofImsUsersActiveByOfficeCode  size " +staff.size() );
				 int counter = 0;
				for (Iterator iterator = staff.iterator(); iterator.hasNext();)  {
					SharedStaff ss = (SharedStaff) iterator.next();
					SharedAccess sa = getStaffAccessByStaffSeq(ss.getStaffSeq());
					if (sa == null)
						continue;
					   
					   else {
						   
			           if (sa.getDeleted().equals("A"))   { 			   
			    	    Long directorStaffSeq = getDirectorStaffSeq(officeCode.get(0));
					    User user = new User(ss.getStaffSeq(), ss.getFirstName(),
					    ss.getLastName());
					    String temp = "";
					    user.setStaffSeqDeletedVal(sa.getDeleted());	
					    user.setImsAccess("A");
					      if(ss.getEmailAddress() != null && !ss.getEmailAddress().isEmpty() ) { 
					        user.setEmail(ss.getEmailAddress().toUpperCase());
					      }
					      else
					      {
						   log.info(" getListofImsUsersActiveByOfficeCode " + " bad email address  " + ss.getEmailAddress() + " <-- Email address ");
						   //user.setEmail("NULL");
					      }
					  user.setStaffSeq(ss.getStaffSeq());
		 			//  user.setNetAccess(sa.getOracleUserId().trim().toUpperCase());
					  
		 			  user.setPrivateRole(sa.getPrivateRole());
					  user.setFederalRole(sa.getFederalRole());
					  user.setLitigationRole(sa.getLitigationRole());
					  user.setOutreachRole(sa.getOutreachRole());
					  user.setAppealsRole(sa.getAppealsRole());
					  user.setUtiliy(sa.getUtility());
					  user.setDirectorStaffSeq(directorStaffSeq); 
					  users.add(user);
					     // log.info("head Quarter User " + users.get(counter).getFirstName() );
					      // log.info("head Quarter User " + users.get(counter).getEmail() );
			                                 counter++;
			           }
			        
					}
					
			     }
				
				log.info("Size of user list returned from getListofImsUsersActiveByOfficeCode and Status " + users.size());
				return users;

			}
			
	
	
	
	
	public List<User> getListofActiveByOfficeCode(String officeCode) {

		List<User> users = new ArrayList<User>();
		// use entity manager to retrieve named query
		TypedQuery<SharedStaff> queryStaffByOfficeCode = em.createNamedQuery(
				"SharedStaff.findActiveByOfficeCode", SharedStaff.class);
		// set dynamic data for query
		queryStaffByOfficeCode.setParameter("officeCode", officeCode);
		queryStaffByOfficeCode.setParameter("status", "A");
		// execute query and get results
		List<SharedStaff> staff = queryStaffByOfficeCode.getResultList();
		for (Iterator iterator = staff.iterator(); iterator.hasNext();) {
			SharedStaff ss = (SharedStaff) iterator.next();
			SharedAccess sa = getStaffAccessByStaffSeq(ss.getStaffSeq());
			if (sa == null)
				continue;
			Long directorStaffSeq = getDirectorStaffSeq(officeCode);
			User user = new User(ss.getStaffSeq(), ss.getFirstName(),
					ss.getLastName());
			user.setPrivateRole(sa.getPrivateRole());
			user.setFederalRole(sa.getFederalRole());
			user.setLitigationRole(sa.getLitigationRole());
			user.setOutreachRole(sa.getOutreachRole());
			user.setAppealsRole(sa.getAppealsRole());
			user.setUtiliy(sa.getUtility());
			user.setDirectorStaffSeq(directorStaffSeq);

			users.add(user);
		}
		return users;

	}

	public int getCountofActiveByOfficeCode(String officeCode) {

		if (officeCode == null)
			return 0;
		int count=this.getListofActiveByOfficeCode(officeCode).size();
		return count;
	}
	public Director getDirector(String officeCode) {
		
		log.info(" CertificationService getDirector");
		Director director = new Director();
		log.info(" CertificationService getDirector get directorStaffSeq");

		Long directorStaffSeq = getDirectorStaffSeq(officeCode);
		log.info(" CertificationService getDirector get directorStaffSeq retrieved " + directorStaffSeq);

		String directorName = getDirectorName(officeCode);
		String officeDescription = getOfficeDescription(officeCode);
		String directorTitle = getDirectorTitle(officeCode);
		String directorEmail = getDirectorEmail(officeCode);
		Date directorCertificationDate = getCertificationDate(officeCode);

		director.setDirectorStaffSeq(directorStaffSeq);
		director.setOfficeCode(officeCode);
		director.setDirectorName(directorName);
		director.setOfficeDescription(officeDescription);
		director.setDirectorTitle(directorTitle);
		director.setEmail(directorEmail);
		director.setCertificationDate(directorCertificationDate);
        log.info("Certification Service getDirector Complete");
		return director;
	}
	
	public List<String> getAllEEOCDirectors() {
		String str = "select p.officeCode "
				+ "from SharedStaff s ,SharedOfficeInfo p "
				+ "where p.directorStaffSeq = s.staffSeq and "
				+"p.officeCode between '410' and '570'and "
				+ "s.status=:arg1 and "
	        	+"SUBSTR(p.officeCode,3,1) BETWEEN '0' AND '9'";

		Query query = em.createQuery(str);
		query.setParameter("arg1", "A");
		List<String> list = query.getResultList();
		return list;

	}
	
	public List<String> getAllHQDirectors() {
		String str = "select p.officeCode "
				+ "from SharedStaff s ,SharedOfficeInfo p "
				+ "where p.directorStaffSeq = s.staffSeq and "
				+"p.officeCode between '810' and '890'and "
				+ "s.status=:arg1 and "
		        +"SUBSTR(p.officeCode,3,1) BETWEEN '0' AND '9'";

		Query query = em.createQuery(str);
		query.setParameter("arg1", "A");
		List<String> list = query.getResultList();
		return list;

	}
	
	public List<String> getAllFEPADirectors() {
		String str = "select p.officeCode "
				+ "from SharedStaff s ,SharedOfficeInfo p "
				+ "where p.directorStaffSeq = s.staffSeq and "
				+ "s.status=:arg1 and "
				+"SUBSTR(p.officeCode,3,1) BETWEEN 'A' AND 'Z'";
				

		Query query = em.createQuery(str);
		query.setParameter("arg1", "A");
		List<String> list = query.getResultList();
		return list;

	}
	
	public List<String> getITSpecialistEmail(String officeCode) {
		if (officeCode == null)
			return null;
		List<String> ITSpecialistList=null;
		try{
			String str = "select s.emailAddress from SharedStaff s where "
					+ "s.title=:arg1 and " + "s.status =:arg2 and " 
					+ "s.officeCode=:arg3" ;
			Query query = em.createQuery(str);
			query.setParameter("arg1", "T2");
			query.setParameter("arg2", "A");
			query.setParameter("arg3", officeCode);
			// ITSpecialist = (String) query.getSingleResult();
			ITSpecialistList = query.getResultList();
			}catch(NoResultException nre){
				log.error("There is no IT Specialist for the office code"+ officeCode);		
			}
		
		return ITSpecialistList;

	}
	
	public List<String> getITAssistantEmail(String officeCode) {
		if (officeCode == null)
			return null;
		List<String> ITAssistant = null;
		try{
		String str = "select s.emailAddress from SharedStaff s where "
				+ "s.title=:arg1 and " + "s.status =:arg2 and " 
				+ "s.officeCode=:arg3" ;
		Query query = em.createQuery(str);
		query.setParameter("arg1", "T7");
		query.setParameter("arg2", "A");
		query.setParameter("arg3", officeCode);
		 ITAssistant = query.getResultList();
		}catch(NoResultException nre){
			log.error("There is no IT Specialist for the office code"+ officeCode);		
		}
		return ITAssistant;
		
	}
	public String validateUser(String userName, String password) {

		Query query = em
				.createNativeQuery("SELECT shared_logon_util.verify_password(?,?) FROM dual");
		query.setParameter(1, userName);
		query.setParameter(2, password);
		return (String) query.getResultList().get(0);

	}
	
	public List<String> getIMSUtiliesAccessForOIT(){
		String str = "select a.oracleUserId "
				+ "from SharedStaff s, SharedAccess a  "
				+ "where s.staffSeq = a.ssStaffSeq and "
				+ "s.officeCode =:arg1 and "
				+ "a.utility =:arg2 and "
				+ "a.deleted =:arg3 "
				+ "order by  a.oracleUserId";
				
		Query query = em.createQuery(str);
		query.setParameter("arg1", "876");
		query.setParameter("arg2", "4");
		query.setParameter("arg3", "A");
		List<String> list = query.getResultList();
		return list;

	}

	public BigDecimal passwordReset(String email) {

		Query query = em
				.createNativeQuery("SELECT shared_logon_pref.util1(?) FROM dual");
		query.setParameter(1, email);
		return (BigDecimal) query.getResultList().get(0);
	}

	private SharedAccess getStaffAccessByStaffSeq(long ssStaffSeq) {
		String str = "SELECT s FROM SharedAccess s WHERE s.ssStaffSeq = :ssStaffSeq";
		Query q = em.createQuery(str);
		q.setParameter("ssStaffSeq", ssStaffSeq);

		List<SharedAccess> access = q.getResultList();
		if (access == null || access.isEmpty())
			return null;
		return access.get(0);
	}
	
	private AccountCertificationReport getAccountCertificationReport() {
		TypedQuery<AccountCertificationReport> queryAllReports = em.createNamedQuery(
				"ACCOUNT_CERTIFICATION_REPORT.findAll", AccountCertificationReport.class);
		List<AccountCertificationReport> report = queryAllReports.getResultList();
		  
		if (report == null || report.isEmpty())
			return null;
		log.info(" getAccountCertificationReport size "+ report.size());
		return report.get(0);
	}
	
	public boolean removeAllUsers(List<AccountCertReport> list) {
		//AccountCertificationReport
	//	report1 = this.getAccountCertificationReport();
	
		AccountCertificationReport report = this.getAccountCertificationReport();
		
		log.info("Checking entity " );
		for(AccountCertReport acr: list){
			AccountCertificationReport acr2 = new AccountCertificationReport();
			acr2.setFirstName(acr.getFirstName());
			acr2.setLastName(acr.getLastName());
			acr2.setEmail(acr.getEmail());
			acr2.setDirectorFullName(acr.getDirectorFullName());
			acr2.setCertificationDate(acr.getDertificationDate());
			acr2.setRemoval(acr.getRemoval());
			acr2.setApplication(acr.getApplication());
			acr2.setComments(acr.getComments());
			
			 
			em.merge(acr2);
		 
			log.info("add entity " );
		}
		
		return true;
	}

	public boolean removeUser(User user) {
		boolean success = true;
		java.sql.Date dtTodaysDate = new java.sql.Date(new Date().getTime());

		SharedAccess access = this.getStaffAccessByStaffSeq(user.getStaffSeq());
		access.setRemovalReason(user.getComments());
		access.setDeleted("D");
		access.setMaintainedBy("CERT");
		access.setMaintainedDate(dtTodaysDate);
		
		em.merge(access);

		SharedStaff staff = this.getStaffByStaffSeq(user.getStaffSeq());
		staff.setStatus("I");
		staff.setMaintainedBy("CERT");
		staff.setMaintainedDate(dtTodaysDate);
		em.merge(staff);
		return success;
	}

	public void delete_ims_account(long directorStaffSeq, long ssStaffSeq) {
		String str = "call dba_prod_request.delete_ims_account(:requester,:to_be_deleted)";
		Query query = em.createNativeQuery(str);
		query.setParameter("requester", directorStaffSeq);
		query.setParameter("to_be_deleted", ssStaffSeq);
		int res = query.executeUpdate();
		//System.out.println("### directorStaffSeq: " + directorStaffSeq);
		//System.out.println("### ssStaffSeq: " + ssStaffSeq);
	}

	
	public boolean certifyUser(String officeCode) {
		boolean success = true;
		java.sql.Date dtTodaysDate = new java.sql.Date(new Date().getTime());

		SharedOfficeInfo officeinfo = getOfficeByOfficeCode(officeCode);
		officeinfo.setCertificationDate(dtTodaysDate);
		em.merge(officeinfo);

		TypedQuery<SharedStaff> queryStaffByOfficeCode = em.createNamedQuery(
				"SharedStaff.findActiveByOfficeCode", SharedStaff.class);
		queryStaffByOfficeCode.setParameter("officeCode", officeCode);
		queryStaffByOfficeCode.setParameter("status", "A");
		List<SharedStaff> staff = queryStaffByOfficeCode.getResultList();
		for (Iterator iterator = staff.iterator(); iterator.hasNext();) {
			SharedStaff ss = (SharedStaff) iterator.next();

			SharedAccess sa = this.getStaffAccessByStaffSeq(ss.getStaffSeq());
			if (sa == null)
				continue;
			sa.setCertificationDate(dtTodaysDate);
			em.merge(sa);
		}
		return success;
	}

	private SharedStaff getStaffByStaffSeq(long staffSeq) {
		String str = "SELECT s FROM SharedStaff s WHERE s.staffSeq = :staffSeq";
		Query q = em.createQuery(str);
		q.setParameter("staffSeq", staffSeq);

		List<SharedStaff> staff = q.getResultList();
		if (staff == null || staff.isEmpty())
			return null;
		return staff.get(0);
	}

	public String getSMTPSERVER() {
			Query query = em
					.createNativeQuery("SELECT VALUE FROM SHARED_APPLICATION_PARAMETER WHERE NAME = 'SMTP_SERVER'");
			return (String) query.getSingleResult();
		}
	
	private SharedOfficeInfo getOfficeByOfficeCode(String officeCode) {
		String str = "SELECT s FROM SharedOfficeInfo s WHERE s.officeCode = :officeCode";
		Query q = em.createQuery(str);
		q.setParameter("officeCode", officeCode);

		List<SharedOfficeInfo> officeInfo = q.getResultList();
		if (officeInfo == null || officeInfo.isEmpty())
			return null;
		return officeInfo.get(0);
	}

	public String getReportUrl(String officeCode) {
		SystemUtil systemUtil = new SystemUtil();
		String reportServer = getReportServer();
		String format = "pdf";
		Integer reportNumber = 111;

		String partialReportUrl = systemUtil.buildPartialReportServerUrl(
				reportServer, officeCode);
		Long directorStaffSeq = getDirectorStaffSeq(officeCode);
		String token = getTokenForBirtReport(directorStaffSeq, reportNumber,
				partialReportUrl);
		return systemUtil.buildReportServerUrl(reportServer, officeCode,
				format, token);

	}

	private String getReportServer() {
		Query query = em
				.createNativeQuery("SELECT VALUE FROM SHARED_APPLICATION_PARAMETER WHERE NAME = 'BIRT_URL'");
		return (String) query.getSingleResult();
	}

	private String getTokenForBirtReport(Long directorStaffSeq,
			Integer reportNumber, String reportServer) {
		/* Code for Test and further */
		Query query = em
				.createNativeQuery("select birt_security.get_token(?,?,?,null) from dual");
		query.setParameter(1, directorStaffSeq);
		query.setParameter(2, reportNumber);
		query.setParameter(3, reportServer);

		/* Code for DEVL */
		/*
		 * Query query =
		 * em.createNativeQuery("select birt_security.get_token(?,?,?,?) from dual"
		 * ); query.setParameter(1, directorStaffSeq); query.setParameter(2,
		 * reportNumber); query.setParameter(3, reportServer);
		 * query.setParameter(4, "RPT");
		 */
		return (String) query.getResultList().get(0);

	}

	public String getIMSAccountCertificationUrl(String officeCode) {
		SystemUtil systemUtil = new SystemUtil();
		String webServer = null;
		//if(System.getProperty("developer") != null) {
		//	webServer = "localhost:8080";
		//} else {
		   webServer = getWebServer();
		   
		   System.out.println("webServer " + webServer);
		//} original setting will set it to localhost for now
       // String webServer = "http://10.9.44.171:8080";
		/*
		 * This is for Local String webServer="http://localhost:8080";
		 */
		   
		   //webServer = "http://10.9.44.171:8080";
		return systemUtil
				.buildIMSAccountCertificationUrl(webServer, officeCode);

	}
	
	private String getLdapWebServiceUrl() {
		/*
		 * http://10.9.23.42:8080/ldapws/rest/access
		 * LDAP_WEB_SERVICE_URL
		Query query = em
				.createNativeQuery("SELECT VALUE FROM SHARED_APPLICATION_PARAMETER WHERE NAME = 'CERT_URL'");
		*
		*
		*/
		//return "http://10.9.23.42:8080/ldapws/rest/access";
	    //	return (String) query.getSingleResult();
		Query query = em
				.createNativeQuery("SELECT VALUE FROM SHARED_APPLICATION_PARAMETER WHERE NAME = 'LDAP_WEB_SERVICE_URL'");
		return (String) query.getSingleResult();
	
	}
	

	private String getWebServer() {
		Query query = em
				.createNativeQuery("SELECT VALUE FROM SHARED_APPLICATION_PARAMETER WHERE NAME = 'CERT_URL'");
		// return "http://localhost:8080"; 
		   return (String) query.getSingleResult();
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
	public String getContactInfoFromEmail(String str) {
		String tmp = "";
		int ind = 0;
		String email_regex = "[A-Z]+[a-zA-Z_]+@\b([a-zA-Z]+.){2}\b?.[a-zA-Z]+";
		 
		 
		if(str != null && !str.isEmpty() ) {
		 str = str.trim().toUpperCase();
		 str = str.replaceAll("\\s", "");
		 if(validate(str))  {
		   tmp =  str;
		   ind = tmp.lastIndexOf("@");
		      if(ind != -1) {
		       tmp = tmp.substring(0, ind);
		        return tmp.toUpperCase();
		           }
		             else 
		          {
		          return "blank";	
		          }
		 }
		 else
		   {
			 return "blank";
		    }
	  }
		else
		{
			return "blank";
		}
	}
}
