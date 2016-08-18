package gov.eeoc.accountcertification.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "SHARED_ACCESS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SharedAccess.findAll", query = "SELECT s FROM SharedAccess s"),
    @NamedQuery(name = "SharedAccess.findBySsStaffSeq", query = "SELECT s FROM SharedAccess s WHERE s.ssStaffSeq = :ssStaffSeq"),
    @NamedQuery(name = "SharedAccess.findByOracleUserId", query = "SELECT s FROM SharedAccess s WHERE s.oracleUserId = :oracleUserId"),
  })
public class SharedAccess implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "SS_STAFF_SEQ")
    private Long ssStaffSeq;
    @Basic(optional = false)
    @Column(name = "ORACLE_USER_ID")
    private String oracleUserId;
    @Column(name = "PASSWORD_EXPIRATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date passwordExpirationDate;
    @Basic(optional = false)
    @Column(name = "UTILITY")
    private String utility;
    @Basic(optional = false)
    @Column(name = "P_READ_LEVEL")
    private short pReadLevel;
    @Basic(optional = false)
    @Column(name = "P_WRITE_LEVEL")
    private short pWriteLevel;
    @Column(name = "P_RESTRICTED_ACTION")
    private String pRestrictedAction;
    @Basic(optional = false)
    @Column(name = "P_MODIFY_ACTION")
    private String pModifyAction;
    @Basic(optional = false)
    @Column(name = "P_MEDIATION_BENEFITS")
    private String pMediationBenefits;
    @Basic(optional = false)
    @Column(name = "P_STATE_LOCAL_READ")
    private short pStateLocalRead;
    @Basic(optional = false)
    @Column(name = "P_STATE_LOCAL_WRITE")
    private String pStateLocalWrite;
    @Basic(optional = false)
    @Column(name = "P_WKLD_MGMT_SUPV")
    private String pWkldMgmtSupv;
    @Column(name = "P_ACTING")
    private String pActing;
    @Basic(optional = false)
    @Column(name = "P_WKLD_MGMT_UNASSIGNED")
    private String pWkldMgmtUnassigned;
    @Basic(optional = false)
    @Column(name = "F_READ_LEVEL")
    private short fReadLevel;
    @Basic(optional = false)
    @Column(name = "F_WRITE_LEVEL")
    private short fWriteLevel;
    @Column(name = "F_RESTRICTED_ACTION")
    private String fRestrictedAction;
    @Basic(optional = false)
    @Column(name = "F_MODIFY_ACTION")
    private String fModifyAction;
    @Basic(optional = false)
    @Column(name = "F_WKLD_MGMT_SUPV")
    private String fWkldMgmtSupv;
    @Column(name = "F_ACTING")
    private String fActing;
    @Basic(optional = false)
    @Column(name = "F_WKLD_MGMT_UNASSIGNED")
    private String fWkldMgmtUnassigned;
    @Basic(optional = false)
    @Column(name = "L_READ_LEVEL")
    private short lReadLevel;
    @Basic(optional = false)
    @Column(name = "L_WRITE_LEVEL")
    private short lWriteLevel;
    @Column(name = "L_RESTRICTED_ACTION")
    private String lRestrictedAction;
    @Basic(optional = false)
    @Column(name = "L_MODIFY_ACTION")
    private String lModifyAction;
    @Basic(optional = false)
    @Column(name = "L_WKLD_MGMT_SUPV")
    private String lWkldMgmtSupv;
    @Column(name = "L_ACTING")
    private String lActing;
    @Basic(optional = false)
    @Column(name = "L_WKLD_MGMT_UNASSIGNED")
    private String lWkldMgmtUnassigned;
    @Basic(optional = false)
    @Column(name = "A_EVENT_READ_LEVEL")
    private short aEventReadLevel;
    @Basic(optional = false)
    @Column(name = "A_EVENT_WRITE_LEVEL")
    private short aEventWriteLevel;
    @Basic(optional = false)
    @Column(name = "A_INITIATIVE_READ_LEVEL")
    private short aInitiativeReadLevel;
    @Basic(optional = false)
    @Column(name = "A_INITIATIVE_WRITE_LEVEL")
    private short aInitiativeWriteLevel;
    @Column(name = "PRIVATE_ROLE")
    private String privateRole;
    @Column(name = "FEDERAL_ROLE")
    private String federalRole;
    @Column(name = "LITIGATION_ROLE")
    private String litigationRole;
    @Column(name = "OUTREACH_ROLE")
    private String outreachRole;
    @Column(name = "DELETED")
    private String deleted;
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
    @Column(name = "ARCHIVE_396_ACCESS")
    private String archive396Access;
    @Basic(optional = false)
    @Column(name = "PRI_RPT_SCOPE")
    private short priRptScope;
    @Basic(optional = false)
    @Column(name = "FED_RPT_SCOPE")
    private short fedRptScope;
    @Basic(optional = false)
    @Column(name = "LIT_RPT_SCOPE")
    private short litRptScope;
    @Basic(optional = false)
    @Column(name = "OUT_RPT_SCOPE")
    private short outRptScope;
    @Column(name = "ARCHIVE_396_DELETE")
    private String archive396Delete;
    @Basic(optional = false)
    @Column(name = "FRM_WP_TYPE")
    private String frmWpType;
    @Basic(optional = false)
    @Column(name = "FRM_WP_EXECUTABLE")
    private String frmWpExecutable;
    @Basic(optional = false)
    @Column(name = "FRM_WP_MACRO")
    private String frmWpMacro;
    @Basic(optional = false)
    @Column(name = "FRM_TEMPLATE_DIR")
    private String frmTemplateDir;
    @Basic(optional = false)
    @Column(name = "FRM_WP_EXTENSION")
    private String frmWpExtension;
    @Basic(optional = false)
    @Column(name = "FRM_WP_SERVICE")
    private String frmWpService;
    @Column(name = "ROB_ACCEPT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date robAcceptDate;
    @Column(name = "HYPERION_ACCESS")
    private String hyperionAccess;
    @Column(name = "P_SYSTEMIC")
    private String pSystemic;
    @Column(name = "P_LOCAL_LIST")
    private String pLocalList;
    @Column(name = "F_LOCAL_LIST")
    private String fLocalList;
    @Column(name = "FA_LOCAL_LIST")
    private String faLocalList;
    @Column(name = "FA_FAIR")
    private String faFair;
    @Column(name = "FA_SHOW_CAUSE")
    private String faShowCause;
    @Column(name = "FA_DELETE")
    private String faDelete;
    @Column(name = "FA_CLOSURE")
    private String faClosure;
    @Column(name = "FA_READ_LEVEL")
    private Short faReadLevel;
    @Column(name = "FA_WRITE_LEVEL")
    private Short faWriteLevel;
    @Column(name = "APPEALS_ROLE")
    private String appealsRole;
    @Column(name = "FA_RPT_SCOPE")
    private Short faRptScope;
    @Column(name = "FA_ADD_CASE_TYPE")
    private String faAddCaseType;
    @Column(name = "FA_WKLD_MGMT")
    private String faWkldMgmt;
    @Column(name = "FA_USER_DEFAULT")
    private String faUserDefault;
    @Column(name = "F_DECONSOLIDATE")
    private String fDeconsolidate;
    @Column(name = "P_AUDIT_LOG")
    private String pAuditLog;
    @Column(name = "EMAIL_VERIFY_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date emailVerifyDate;
    @Column(name = "LA_READ_LEVEL")
    private String laReadLevel;
    @Column(name = "LA_WRITE_LEVEL")
    private String laWriteLevel;
    @Column(name = "P_FILE_DESTRUCT")
    private String pFileDestruct;
    @Column(name = "REMOVAL_REASON")
    private String removalReason;
    @Column(name = "CERTIFICATION_DATE")
    private Date certificationDate;
    
    @JoinColumn(name = "SS_STAFF_SEQ", referencedColumnName = "STAFF_SEQ", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private SharedStaff sharedStaff;

    public SharedAccess() {
    }

    public SharedAccess(Long ssStaffSeq) {
        this.ssStaffSeq = ssStaffSeq;
    }

    public SharedAccess(Long ssStaffSeq, String oracleUserId, String utility, short pReadLevel, short pWriteLevel, String pModifyAction, String pMediationBenefits, short pStateLocalRead, String pStateLocalWrite, String pWkldMgmtSupv, String pWkldMgmtUnassigned, short fReadLevel, short fWriteLevel, String fModifyAction, String fWkldMgmtSupv, String fWkldMgmtUnassigned, short lReadLevel, short lWriteLevel, String lModifyAction, String lWkldMgmtSupv, String lWkldMgmtUnassigned, short aEventReadLevel, short aEventWriteLevel, short aInitiativeReadLevel, short aInitiativeWriteLevel, String createdBy, Date createdDate, String maintainedBy, Date maintainedDate, short priRptScope, short fedRptScope, short litRptScope, short outRptScope, String frmWpType, String frmWpExecutable, String frmWpMacro, String frmTemplateDir, String frmWpExtension, String frmWpService) {
        this.ssStaffSeq = ssStaffSeq;
        this.oracleUserId = oracleUserId;
        this.utility = utility;
        this.pReadLevel = pReadLevel;
        this.pWriteLevel = pWriteLevel;
        this.pModifyAction = pModifyAction;
        this.pMediationBenefits = pMediationBenefits;
        this.pStateLocalRead = pStateLocalRead;
        this.pStateLocalWrite = pStateLocalWrite;
        this.pWkldMgmtSupv = pWkldMgmtSupv;
        this.pWkldMgmtUnassigned = pWkldMgmtUnassigned;
        this.fReadLevel = fReadLevel;
        this.fWriteLevel = fWriteLevel;
        this.fModifyAction = fModifyAction;
        this.fWkldMgmtSupv = fWkldMgmtSupv;
        this.fWkldMgmtUnassigned = fWkldMgmtUnassigned;
        this.lReadLevel = lReadLevel;
        this.lWriteLevel = lWriteLevel;
        this.lModifyAction = lModifyAction;
        this.lWkldMgmtSupv = lWkldMgmtSupv;
        this.lWkldMgmtUnassigned = lWkldMgmtUnassigned;
        this.aEventReadLevel = aEventReadLevel;
        this.aEventWriteLevel = aEventWriteLevel;
        this.aInitiativeReadLevel = aInitiativeReadLevel;
        this.aInitiativeWriteLevel = aInitiativeWriteLevel;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.maintainedBy = maintainedBy;
        this.maintainedDate = maintainedDate;
        this.priRptScope = priRptScope;
        this.fedRptScope = fedRptScope;
        this.litRptScope = litRptScope;
        this.outRptScope = outRptScope;
        this.frmWpType = frmWpType;
        this.frmWpExecutable = frmWpExecutable;
        this.frmWpMacro = frmWpMacro;
        this.frmTemplateDir = frmTemplateDir;
        this.frmWpExtension = frmWpExtension;
        this.frmWpService = frmWpService;
    }

    public Long getSsStaffSeq() {
        return ssStaffSeq;
    }

    public void setSsStaffSeq(Long ssStaffSeq) {
        this.ssStaffSeq = ssStaffSeq;
    }

    public String getOracleUserId() {
        return oracleUserId;
    }

    public void setOracleUserId(String oracleUserId) {
        this.oracleUserId = oracleUserId;
    }

    public Date getPasswordExpirationDate() {
        return passwordExpirationDate;
    }

    public void setPasswordExpirationDate(Date passwordExpirationDate) {
        this.passwordExpirationDate = passwordExpirationDate;
    }

    public String getUtility() {
        return utility;
    }

    public void setUtility(String utility) {
        this.utility = utility;
    }

    public short getPReadLevel() {
        return pReadLevel;
    }

    public void setPReadLevel(short pReadLevel) {
        this.pReadLevel = pReadLevel;
    }

    public short getPWriteLevel() {
        return pWriteLevel;
    }

    public void setPWriteLevel(short pWriteLevel) {
        this.pWriteLevel = pWriteLevel;
    }

    public String getPRestrictedAction() {
        return pRestrictedAction;
    }

    public void setPRestrictedAction(String pRestrictedAction) {
        this.pRestrictedAction = pRestrictedAction;
    }

    public String getPModifyAction() {
        return pModifyAction;
    }

    public void setPModifyAction(String pModifyAction) {
        this.pModifyAction = pModifyAction;
    }

    public String getPMediationBenefits() {
        return pMediationBenefits;
    }

    public void setPMediationBenefits(String pMediationBenefits) {
        this.pMediationBenefits = pMediationBenefits;
    }

    public short getPStateLocalRead() {
        return pStateLocalRead;
    }

    public void setPStateLocalRead(short pStateLocalRead) {
        this.pStateLocalRead = pStateLocalRead;
    }

    public String getPStateLocalWrite() {
        return pStateLocalWrite;
    }

    public void setPStateLocalWrite(String pStateLocalWrite) {
        this.pStateLocalWrite = pStateLocalWrite;
    }

    public String getPWkldMgmtSupv() {
        return pWkldMgmtSupv;
    }

    public void setPWkldMgmtSupv(String pWkldMgmtSupv) {
        this.pWkldMgmtSupv = pWkldMgmtSupv;
    }

    public String getPActing() {
        return pActing;
    }

    public void setPActing(String pActing) {
        this.pActing = pActing;
    }

    public String getPWkldMgmtUnassigned() {
        return pWkldMgmtUnassigned;
    }

    public void setPWkldMgmtUnassigned(String pWkldMgmtUnassigned) {
        this.pWkldMgmtUnassigned = pWkldMgmtUnassigned;
    }

    public short getFReadLevel() {
        return fReadLevel;
    }

    public void setFReadLevel(short fReadLevel) {
        this.fReadLevel = fReadLevel;
    }

    public short getFWriteLevel() {
        return fWriteLevel;
    }

    public void setFWriteLevel(short fWriteLevel) {
        this.fWriteLevel = fWriteLevel;
    }

    public String getFRestrictedAction() {
        return fRestrictedAction;
    }

    public void setFRestrictedAction(String fRestrictedAction) {
        this.fRestrictedAction = fRestrictedAction;
    }

    public String getFModifyAction() {
        return fModifyAction;
    }

    public void setFModifyAction(String fModifyAction) {
        this.fModifyAction = fModifyAction;
    }

    public String getFWkldMgmtSupv() {
        return fWkldMgmtSupv;
    }

    public void setFWkldMgmtSupv(String fWkldMgmtSupv) {
        this.fWkldMgmtSupv = fWkldMgmtSupv;
    }

    public String getFActing() {
        return fActing;
    }

    public void setFActing(String fActing) {
        this.fActing = fActing;
    }

    public String getFWkldMgmtUnassigned() {
        return fWkldMgmtUnassigned;
    }

    public void setFWkldMgmtUnassigned(String fWkldMgmtUnassigned) {
        this.fWkldMgmtUnassigned = fWkldMgmtUnassigned;
    }

    public short getLReadLevel() {
        return lReadLevel;
    }

    public void setLReadLevel(short lReadLevel) {
        this.lReadLevel = lReadLevel;
    }

    public short getLWriteLevel() {
        return lWriteLevel;
    }

    public void setLWriteLevel(short lWriteLevel) {
        this.lWriteLevel = lWriteLevel;
    }

    public String getLRestrictedAction() {
        return lRestrictedAction;
    }

    public void setLRestrictedAction(String lRestrictedAction) {
        this.lRestrictedAction = lRestrictedAction;
    }

    public String getLModifyAction() {
        return lModifyAction;
    }

    public void setLModifyAction(String lModifyAction) {
        this.lModifyAction = lModifyAction;
    }

    public String getLWkldMgmtSupv() {
        return lWkldMgmtSupv;
    }

    public void setLWkldMgmtSupv(String lWkldMgmtSupv) {
        this.lWkldMgmtSupv = lWkldMgmtSupv;
    }

    public String getLActing() {
        return lActing;
    }

    public void setLActing(String lActing) {
        this.lActing = lActing;
    }

    public String getLWkldMgmtUnassigned() {
        return lWkldMgmtUnassigned;
    }

    public void setLWkldMgmtUnassigned(String lWkldMgmtUnassigned) {
        this.lWkldMgmtUnassigned = lWkldMgmtUnassigned;
    }

    public short getAEventReadLevel() {
        return aEventReadLevel;
    }

    public void setAEventReadLevel(short aEventReadLevel) {
        this.aEventReadLevel = aEventReadLevel;
    }

    public short getAEventWriteLevel() {
        return aEventWriteLevel;
    }

    public void setAEventWriteLevel(short aEventWriteLevel) {
        this.aEventWriteLevel = aEventWriteLevel;
    }

    public short getAInitiativeReadLevel() {
        return aInitiativeReadLevel;
    }

    public void setAInitiativeReadLevel(short aInitiativeReadLevel) {
        this.aInitiativeReadLevel = aInitiativeReadLevel;
    }

    public short getAInitiativeWriteLevel() {
        return aInitiativeWriteLevel;
    }

    public void setAInitiativeWriteLevel(short aInitiativeWriteLevel) {
        this.aInitiativeWriteLevel = aInitiativeWriteLevel;
    }

    public String getPrivateRole() {
        return privateRole;
    }

    public void setPrivateRole(String privateRole) {
        this.privateRole = privateRole;
    }

    public String getFederalRole() {
        return federalRole;
    }

    public void setFederalRole(String federalRole) {
        this.federalRole = federalRole;
    }

    public String getLitigationRole() {
        return litigationRole;
    }

    public void setLitigationRole(String litigationRole) {
        this.litigationRole = litigationRole;
    }

    public String getOutreachRole() {
        return outreachRole;
    }

    public void setOutreachRole(String outreachRole) {
        this.outreachRole = outreachRole;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getMaintainedBy() {
        return maintainedBy;
    }

    public void setMaintainedBy(String maintainedBy) {
        this.maintainedBy = maintainedBy;
    }

    public Date getMaintainedDate() {
        return maintainedDate;
    }

    public void setMaintainedDate(Date maintainedDate) {
        this.maintainedDate = maintainedDate;
    }

    public String getArchive396Access() {
        return archive396Access;
    }

    public void setArchive396Access(String archive396Access) {
        this.archive396Access = archive396Access;
    }

    public short getPriRptScope() {
        return priRptScope;
    }

    public void setPriRptScope(short priRptScope) {
        this.priRptScope = priRptScope;
    }

    public short getFedRptScope() {
        return fedRptScope;
    }

    public void setFedRptScope(short fedRptScope) {
        this.fedRptScope = fedRptScope;
    }

    public short getLitRptScope() {
        return litRptScope;
    }

    public void setLitRptScope(short litRptScope) {
        this.litRptScope = litRptScope;
    }

    public short getOutRptScope() {
        return outRptScope;
    }

    public void setOutRptScope(short outRptScope) {
        this.outRptScope = outRptScope;
    }

    public String getArchive396Delete() {
        return archive396Delete;
    }

    public void setArchive396Delete(String archive396Delete) {
        this.archive396Delete = archive396Delete;
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

    public String getFrmWpService() {
        return frmWpService;
    }

    public void setFrmWpService(String frmWpService) {
        this.frmWpService = frmWpService;
    }

    public Date getRobAcceptDate() {
        return robAcceptDate;
    }

    public void setRobAcceptDate(Date robAcceptDate) {
        this.robAcceptDate = robAcceptDate;
    }

    public String getHyperionAccess() {
        return hyperionAccess;
    }

    public void setHyperionAccess(String hyperionAccess) {
        this.hyperionAccess = hyperionAccess;
    }

    public String getPSystemic() {
        return pSystemic;
    }

    public void setPSystemic(String pSystemic) {
        this.pSystemic = pSystemic;
    }

    public String getPLocalList() {
        return pLocalList;
    }

    public void setPLocalList(String pLocalList) {
        this.pLocalList = pLocalList;
    }

    public String getFLocalList() {
        return fLocalList;
    }

    public void setFLocalList(String fLocalList) {
        this.fLocalList = fLocalList;
    }

    public String getFaLocalList() {
        return faLocalList;
    }

    public void setFaLocalList(String faLocalList) {
        this.faLocalList = faLocalList;
    }

    public String getFaFair() {
        return faFair;
    }

    public void setFaFair(String faFair) {
        this.faFair = faFair;
    }

    public String getFaShowCause() {
        return faShowCause;
    }

    public void setFaShowCause(String faShowCause) {
        this.faShowCause = faShowCause;
    }

    public String getFaDelete() {
        return faDelete;
    }

    public void setFaDelete(String faDelete) {
        this.faDelete = faDelete;
    }

    public String getFaClosure() {
        return faClosure;
    }

    public void setFaClosure(String faClosure) {
        this.faClosure = faClosure;
    }

    public Short getFaReadLevel() {
        return faReadLevel;
    }

    public void setFaReadLevel(Short faReadLevel) {
        this.faReadLevel = faReadLevel;
    }

    public Short getFaWriteLevel() {
        return faWriteLevel;
    }

    public void setFaWriteLevel(Short faWriteLevel) {
        this.faWriteLevel = faWriteLevel;
    }

    public String getAppealsRole() {
        return appealsRole;
    }

    public void setAppealsRole(String appealsRole) {
        this.appealsRole = appealsRole;
    }

    public Short getFaRptScope() {
        return faRptScope;
    }

    public void setFaRptScope(Short faRptScope) {
        this.faRptScope = faRptScope;
    }

    public String getFaAddCaseType() {
        return faAddCaseType;
    }

    public void setFaAddCaseType(String faAddCaseType) {
        this.faAddCaseType = faAddCaseType;
    }

    public String getFaWkldMgmt() {
        return faWkldMgmt;
    }

    public void setFaWkldMgmt(String faWkldMgmt) {
        this.faWkldMgmt = faWkldMgmt;
    }

    public String getFaUserDefault() {
        return faUserDefault;
    }

    public void setFaUserDefault(String faUserDefault) {
        this.faUserDefault = faUserDefault;
    }

    public String getFDeconsolidate() {
        return fDeconsolidate;
    }

    public void setFDeconsolidate(String fDeconsolidate) {
        this.fDeconsolidate = fDeconsolidate;
    }

    public String getPAuditLog() {
        return pAuditLog;
    }

    public void setPAuditLog(String pAuditLog) {
        this.pAuditLog = pAuditLog;
    }

    public Date getEmailVerifyDate() {
        return emailVerifyDate;
    }

    public void setEmailVerifyDate(Date emailVerifyDate) {
        this.emailVerifyDate = emailVerifyDate;
    }

    public String getLaReadLevel() {
        return laReadLevel;
    }

    public void setLaReadLevel(String laReadLevel) {
        this.laReadLevel = laReadLevel;
    }

    public String getLaWriteLevel() {
        return laWriteLevel;
    }

    public void setLaWriteLevel(String laWriteLevel) {
        this.laWriteLevel = laWriteLevel;
    }

    public SharedStaff getSharedStaff() {
        return sharedStaff;
    }

    public void setSharedStaff(SharedStaff sharedStaff) {
        this.sharedStaff = sharedStaff;
    }

    public String getpFileDestruct() {
		return pFileDestruct;
	}

	public void setpFileDestruct(String pFileDestruct) {
		this.pFileDestruct = pFileDestruct;
	}

	public String getRemovalReason() {
		return removalReason;
	}

	public void setRemovalReason(String removalReason) {
		this.removalReason = removalReason;
	}

	public Date getCertificationDate() {
		return certificationDate;
	}

	public void setCertificationDate(Date certificationDate) {
		this.certificationDate = certificationDate;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (ssStaffSeq != null ? ssStaffSeq.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SharedAccess)) {
            return false;
        }
        SharedAccess other = (SharedAccess) object;
        if ((this.ssStaffSeq == null && other.ssStaffSeq != null) || (this.ssStaffSeq != null && !this.ssStaffSeq.equals(other.ssStaffSeq))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.eeoc.accountcertification.entity.SharedAccess[ ssStaffSeq=" + ssStaffSeq + " ]";
    }
    
}

