package gov.eeoc.accountcertification.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "collection")  
public class Collection {

	@XmlElement(name = "person", type = Person.class)
	private List<Person> personList = new ArrayList<Person>();

	public Collection () {
		
	}

	public List<Person> getPersonList() {
		return personList;
	}

	public void setPersonList(List<Person> persons) {
		this.personList = persons;
	}
	
	 
}
