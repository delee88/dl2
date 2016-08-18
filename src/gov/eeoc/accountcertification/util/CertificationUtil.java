package gov.eeoc.accountcertification.util;

import java.util.Date;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class CertificationUtil {

	public static int getNoOfDaysfromLastCertification(Date certificationDate) {
		final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
		int diffInDays;
		Date todaysDate = new Date();
		diffInDays = (int) ((todaysDate.getTime() - certificationDate.getTime()) / DAY_IN_MILLIS);
		return diffInDays;
	}

	public static void SetSessionVariable(String key, Object value) {
		FacesContext context = FacesContext.getCurrentInstance();
		
		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();
		                
		HttpSession httpSession = request.getSession(true);
		httpSession.setAttribute(key, value);
	}

	public static Object getSessionVariable(String key) {
 
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();
		HttpSession httpSession = request.getSession(false);
		
		   Object obj = "obj";
		  if(httpSession.getAttribute(key) == null){
			  obj = "null";
			 System.out.println(" null httpSession.getAttribute(key) " + obj);
		  }
		  else { 
			  obj = httpSession.getAttribute(key);
		      System.out.println(" httpSession.getAttribute(key) " + obj);
		  }
		return obj;
	}
 /*
  *   if Session does not exist return null string
  * *  if you do not a null value gets returned and this could crash the server
  */
	 
	@SuppressWarnings("finally")
	public static String getSessionVariableAsString(String key) {
		
		System.out.println("getSessionVariableAsString " + key);
		
		FacesContext context = FacesContext.getCurrentInstance();
		
		System.out.println(" FacesContext.getCurrentInstance() ");
		
		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();
		
		System.out.println(" HttpServletRequest request ");

		HttpSession httpSession = request.getSession(false);

		System.out.println(" request.getSession(false) ");
		
		//String str = (String) httpSession.getAttribute(key);
		String  str = "";
		try {
		  str = (String)httpSession.getAttribute(key);
		 // System.out.println(" httpSession.getAttribute(key) " + str);
		}
		catch(Exception ex )
		{
			 str = "null";
			 System.out.println(" Catch httpSession.getAttribute(key) " + str);
		}
		finally {
			 
			return str;
		}
		 
	}
	
	public static String getUserAgent() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();
		String userAgent = request.getHeader("user-agent");
		
		return userAgent;
		
	}
	
	
}