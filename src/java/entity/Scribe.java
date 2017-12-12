/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Marie
 */
@Entity
@Table(name = "scribe")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Scribe.findAll", query = "SELECT s FROM Scribe s")
    , @NamedQuery(name = "Scribe.findByScribeId", query = "SELECT s FROM Scribe s WHERE s.scribeId = :scribeId")
    , @NamedQuery(name = "Scribe.findByScribeFname", query = "SELECT s FROM Scribe s WHERE s.scribeFname = :scribeFname")
    , @NamedQuery(name = "Scribe.findByScribeLname", query = "SELECT s FROM Scribe s WHERE s.scribeLname = :scribeLname")
    , @NamedQuery(name = "Scribe.findByScribePhone", query = "SELECT s FROM Scribe s WHERE s.scribePhone = :scribePhone")
    , @NamedQuery(name = "Scribe.findByScribeAddress", query = "SELECT s FROM Scribe s WHERE s.scribeAddress = :scribeAddress")
    , @NamedQuery(name = "Scribe.findByScribeEmail", query = "SELECT s FROM Scribe s WHERE s.scribeEmail = :scribeEmail")
    , @NamedQuery(name = "Scribe.findByContractType", query = "SELECT s FROM Scribe s WHERE s.contractType = :contractType")
    , @NamedQuery(name = "Scribe.findByHiringDate", query = "SELECT s FROM Scribe s WHERE s.hiringDate = :hiringDate")
    , @NamedQuery(name = "Scribe.findByExpectedEndDate", query = "SELECT s FROM Scribe s WHERE s.expectedEndDate = :expectedEndDate")
    , @NamedQuery(name = "Scribe.findByEndDate", query = "SELECT s FROM Scribe s WHERE s.endDate = :endDate")
    , @NamedQuery(name = "Scribe.findByHoursWorked", query = "SELECT s FROM Scribe s WHERE s.hoursWorked = :hoursWorked")
    , @NamedQuery(name = "Scribe.findByCamelotProgress", query = "SELECT s FROM Scribe s WHERE s.camelotProgress = :camelotProgress")
    , @NamedQuery(name = "Scribe.findByCamelotScore", query = "SELECT s FROM Scribe s WHERE s.camelotScore = :camelotScore")
    , @NamedQuery(name = "Scribe.findByTextbookReadings", query = "SELECT s FROM Scribe s WHERE s.textbookReadings = :textbookReadings")
    , @NamedQuery(name = "Scribe.findByOnlineResources", query = "SELECT s FROM Scribe s WHERE s.onlineResources = :onlineResources")
    , @NamedQuery(name = "Scribe.findByInClassTraining", query = "SELECT s FROM Scribe s WHERE s.inClassTraining = :inClassTraining")
    , @NamedQuery(name = "Scribe.findByMscPaperwork", query = "SELECT s FROM Scribe s WHERE s.mscPaperwork = :mscPaperwork")
    , @NamedQuery(name = "Scribe.findByGeneralQualified", query = "SELECT s FROM Scribe s WHERE s.generalQualified = :generalQualified")
    , @NamedQuery(name = "Scribe.findShiftsOfWeek", query = "SELECT sc, "
            + "CAST(SUM( CASE WHEN FUNC('year', s.dateToFill) = :year AND FUNC('weekofyear', s.dateToFill) = :week THEN 1 ELSE 0 END) AS SIGNED) AS Result "
            + "FROM Scribe sc LEFT JOIN sc.shiftScheduleCollection s "
            + "WHERE sc.generalQualified = TRUE AND sc.contractType = :contractType "
            + "GROUP BY sc ORDER BY Result")
    , @NamedQuery(name = "Scribe.findScribeShiftsOfWeek", query = "SELECT sc, COUNT(s.shiftScheduleId) FROM Scribe sc LEFT JOIN sc.shiftScheduleCollection s "
            + "WHERE ( FUNC('year', s.dateToFill) = :year AND FUNC('weekofyear', s.dateToFill) = :week) AND sc.scribeId = :scribeId GROUP BY sc")
    , @NamedQuery(name = "Scribe.findScribeAvailabilityOfDay", query = "SELECT a FROM Scribe sc LEFT JOIN sc.availabilityCollection a "
            + "WHERE ( a.availableDate = :date) AND sc.scribeId = :scribeId")
    , @NamedQuery(name = "Scribe.findScribeShiftsOfDay", query = "SELECT s FROM Scribe sc LEFT JOIN sc.shiftScheduleCollection s "
            + "WHERE s.dateToFill = :date AND sc.scribeId = :scribeId")
})
public class Scribe implements Serializable {

    @Lob
    @Column(name = "scribe_photo")
    private byte[] scribePhoto;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "scribe_id")
    private Integer scribeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "scribe_fname")
    private String scribeFname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "scribe_lname")
    private String scribeLname;
    @Size(max = 20)
    @Column(name = "scribe_phone")
    private String scribePhone;
    @Size(max = 60)
    @Column(name = "scribe_address")
    private String scribeAddress;
    @Size(max = 40)
    @Column(name = "scribe_email")
    private String scribeEmail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "contract_type")
    private String contractType;
    @Column(name = "hiring_date")
    @Temporal(TemporalType.DATE)
    private Date hiringDate;
    @Column(name = "expected_end_date")
    @Temporal(TemporalType.DATE)
    private Date expectedEndDate;
    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Column(name = "hours_worked")
    private Short hoursWorked;
    @Column(name = "camelot_progress")
    private Short camelotProgress;
    @Column(name = "camelot_score")
    private Integer camelotScore;
    @Column(name = "textbook_readings")
    private Boolean textbookReadings;
    @Column(name = "online_resources")
    private Boolean onlineResources;
    @Column(name = "in_class_training")
    private Boolean inClassTraining;
    @Column(name = "msc_paperwork")
    private Boolean mscPaperwork;
    @Basic(optional = false)
    @NotNull
    @Column(name = "general_qualified")
    private boolean generalQualified;
    @JoinTable(name = "history_location", joinColumns = {
        @JoinColumn(name = "scribe", referencedColumnName = "scribe_id")}, inverseJoinColumns = {
        @JoinColumn(name = "location", referencedColumnName = "location_id")})
    @ManyToMany
    private Collection<Location> locationCollection;
    @JoinTable(name = "mentor_mentee", joinColumns = {
        @JoinColumn(name = "mentor", referencedColumnName = "scribe_id")}, inverseJoinColumns = {
        @JoinColumn(name = "mentee", referencedColumnName = "scribe_id")})
    @ManyToMany
    private Collection<Scribe> scribeCollection;
    @ManyToMany(mappedBy = "scribeCollection")
    private Collection<Scribe> scribeCollection1;
    @JoinTable(name = "loc_training", joinColumns = {
        @JoinColumn(name = "scribe", referencedColumnName = "scribe_id")}, inverseJoinColumns = {
        @JoinColumn(name = "location", referencedColumnName = "location_id")})
    @ManyToMany
    private Collection<Location> locationCollection1;
    @JoinTable(name = "active_location", joinColumns = {
        @JoinColumn(name = "scribe", referencedColumnName = "scribe_id")}, inverseJoinColumns = {
        @JoinColumn(name = "location", referencedColumnName = "location_id")})
    @ManyToMany
    private Collection<Location> locationCollection2;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "scribe1")
    private Collection<Preference> preferenceCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "scribe1")
    private Collection<ScribeSpecializaiton> scribeSpecializaitonCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "scribe")
    private Collection<Availability> availabilityCollection;
    @OneToMany(mappedBy = "scribe")
    private Collection<ShiftSchedule> shiftScheduleCollection;

    public Scribe() {
    }

    public Scribe(Integer scribeId) {
        this.scribeId = scribeId;
    }

    public Scribe(Integer scribeId, String scribeFname, String scribeLname, String contractType, boolean generalQualified) {
        this.scribeId = scribeId;
        this.scribeFname = scribeFname;
        this.scribeLname = scribeLname;
        this.contractType = contractType;
        this.generalQualified = generalQualified;
    }

    public Integer getScribeId() {
        return scribeId;
    }

    public void setScribeId(Integer scribeId) {
        this.scribeId = scribeId;
    }

    public String getScribeFname() {
        return scribeFname;
    }

    public void setScribeFname(String scribeFname) {
        this.scribeFname = scribeFname;
    }

    public String getScribeLname() {
        return scribeLname;
    }

    public void setScribeLname(String scribeLname) {
        this.scribeLname = scribeLname;
    }

    public String getScribePhone() {
        return scribePhone;
    }

    public void setScribePhone(String scribePhone) {
        this.scribePhone = scribePhone;
    }

    public String getScribeAddress() {
        return scribeAddress;
    }

    public void setScribeAddress(String scribeAddress) {
        this.scribeAddress = scribeAddress;
    }

    public String getScribeEmail() {
        return scribeEmail;
    }

    public void setScribeEmail(String scribeEmail) {
        this.scribeEmail = scribeEmail;
    }

    public byte[] getScribePhoto() {
        return scribePhoto;
    }

    public void setScribePhoto(byte[] scribePhoto) {
        this.scribePhoto = scribePhoto;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public Date getHiringDate() {
        return hiringDate;
    }

    public void setHiringDate(Date hiringDate) {
        this.hiringDate = hiringDate;
    }

    public Date getExpectedEndDate() {
        return expectedEndDate;
    }

    public void setExpectedEndDate(Date expectedEndDate) {
        this.expectedEndDate = expectedEndDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Short getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(Short hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public Short getCamelotProgress() {
        return camelotProgress;
    }

    public void setCamelotProgress(Short camelotProgress) {
        this.camelotProgress = camelotProgress;
    }

    public Integer getCamelotScore() {
        return camelotScore;
    }

    public void setCamelotScore(Integer camelotScore) {
        this.camelotScore = camelotScore;
    }

    public Boolean getTextbookReadings() {
        return textbookReadings;
    }

    public void setTextbookReadings(Boolean textbookReadings) {
        this.textbookReadings = textbookReadings;
    }

    public Boolean getOnlineResources() {
        return onlineResources;
    }

    public void setOnlineResources(Boolean onlineResources) {
        this.onlineResources = onlineResources;
    }

    public Boolean getInClassTraining() {
        return inClassTraining;
    }

    public void setInClassTraining(Boolean inClassTraining) {
        this.inClassTraining = inClassTraining;
    }

    public Boolean getMscPaperwork() {
        return mscPaperwork;
    }

    public void setMscPaperwork(Boolean mscPaperwork) {
        this.mscPaperwork = mscPaperwork;
    }

    public boolean getGeneralQualified() {
        return generalQualified;
    }

    public void setGeneralQualified(boolean generalQualified) {
        this.generalQualified = generalQualified;
    }

    @XmlTransient
    public Collection<Location> getLocationCollection() {
        return locationCollection;
    }

    public void setLocationCollection(Collection<Location> locationCollection) {
        this.locationCollection = locationCollection;
    }

    @XmlTransient
    public Collection<Scribe> getScribeCollection() {
        return scribeCollection;
    }

    public void setScribeCollection(Collection<Scribe> scribeCollection) {
        this.scribeCollection = scribeCollection;
    }

    @XmlTransient
    public Collection<Scribe> getScribeCollection1() {
        return scribeCollection1;
    }

    public void setScribeCollection1(Collection<Scribe> scribeCollection1) {
        this.scribeCollection1 = scribeCollection1;
    }

    @XmlTransient
    public Collection<Location> getLocationCollection2() {
        return locationCollection1;
    }

    public void setLocationCollection2(Collection<Location> locationCollection2) {
        this.locationCollection1 = locationCollection2;
    }

    @XmlTransient
    public Collection<Location> getActiveLocationCollection() {
        return locationCollection2;
    }

    public void setActiveLocationCollection(Collection<Location> activeLocationCollection) {
        this.locationCollection2 = activeLocationCollection;
    }

    @XmlTransient
    public Collection<Preference> getPreferenceCollection() {
        return preferenceCollection;
    }

    public void setPreferenceCollection(Collection<Preference> preferenceCollection) {
        this.preferenceCollection = preferenceCollection;
    }

    @XmlTransient
    public Collection<ScribeSpecializaiton> getScribeSpecializaitonCollection() {
        return scribeSpecializaitonCollection;
    }

    public void setScribeSpecializaitonCollection(Collection<ScribeSpecializaiton> scribeSpecializaitonCollection) {
        this.scribeSpecializaitonCollection = scribeSpecializaitonCollection;
    }

    @XmlTransient
    public Collection<Availability> getAvailabilityCollection() {
        return availabilityCollection;
    }

    public void setAvailabilityCollection(Collection<Availability> availabilityCollection) {
        this.availabilityCollection = availabilityCollection;
    }

    @XmlTransient
    public Collection<ShiftSchedule> getShiftScheduleCollection() {
        return shiftScheduleCollection;
    }

    public void setShiftScheduleCollection(Collection<ShiftSchedule> shiftScheduleCollection) {
        this.shiftScheduleCollection = shiftScheduleCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (scribeId != null ? scribeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Scribe)) {
            return false;
        }
        Scribe other = (Scribe) object;
        if ((this.scribeId == null && other.scribeId != null) || (this.scribeId != null && !this.scribeId.equals(other.scribeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Scribe[ scribeId=" + scribeId + " ]";
    }

}
