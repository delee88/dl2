package gov.eeoc.accountcertification.entity;

import java.io.Serializable;
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
@Table(name = "SHARED_CODE")
@XmlRootElement
public class SharedCode implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name="SHARED_CODE_CODEID_GENERATOR", sequenceName="CODEID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SHARED_CODE_CODEID_GENERATOR")
	 
	@Basic(optional = false)
	 @Column(name = "DOMAIN")
	private String domain;
	 @Basic(optional = false)
	 @Column(name = "CODE")
	private String code;
	 @Column(name = "DESCRIPTION")
	private String description;
	 @Column(name = "OBSOLETE")
	private String obsolete;
	 @Column(name = "SHORT_NAME")
	private String shortName;
	 @Column(name = "DISPLAY_ORDER")
	private long displayOrder;

	public String getDomain() {
		return domain;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public String getObsolete() {
		return obsolete;
	}

	public String getShortName() {
		return shortName;
	}

	public long getDisplayOrder() {
		return displayOrder;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setObsolete(String obsolete) {
		this.obsolete = obsolete;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public void setDisplayOrder(long displayOrder) {
		this.displayOrder = displayOrder;
	}

}
