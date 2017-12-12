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
@Table(name = "physician")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Physician.findAll", query = "SELECT p FROM Physician p")
    , @NamedQuery(name = "Physician.findByPhysicianId", query = "SELECT p FROM Physician p WHERE p.physicianId = :physicianId")
    , @NamedQuery(name = "Physician.findByPhysicianFname", query = "SELECT p FROM Physician p WHERE p.physicianFname = :physicianFname")
    , @NamedQuery(name = "Physician.findByPhysicianLname", query = "SELECT p FROM Physician p WHERE p.physicianLname = :physicianLname")
    , @NamedQuery(name = "Physician.findByPhysicianPhone", query = "SELECT p FROM Physician p WHERE p.physicianPhone = :physicianPhone")
    , @NamedQuery(name = "Physician.findByPhysicianAddress", query = "SELECT p FROM Physician p WHERE p.physicianAddress = :physicianAddress")
    , @NamedQuery(name = "Physician.findByPhysicianEmail", query = "SELECT p FROM Physician p WHERE p.physicianEmail = :physicianEmail")
    , @NamedQuery(name = "Physician.findByClientDate", query = "SELECT p FROM Physician p WHERE p.clientDate = :clientDate")
    , @NamedQuery(name = "Physician.findByContractStartDate", query = "SELECT p FROM Physician p WHERE p.contractStartDate = :contractStartDate")
    , @NamedQuery(name = "Physician.findByContractEndDate", query = "SELECT p FROM Physician p WHERE p.contractEndDate = :contractEndDate")})
public class Physician implements Serializable {

    @Lob
    @Column(name = "physician_photo")
    private byte[] physicianPhoto;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "physician_id")
    private Integer physicianId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "physician_fname")
    private String physicianFname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "physician_lname")
    private String physicianLname;
    @Size(max = 20)
    @Column(name = "physician_phone")
    private String physicianPhone;
    @Size(max = 60)
    @Column(name = "physician_address")
    private String physicianAddress;
    @Size(max = 40)
    @Column(name = "physician_email")
    private String physicianEmail;
    @Column(name = "client_date")
    @Temporal(TemporalType.DATE)
    private Date clientDate;
    @Column(name = "contract_start_date")
    @Temporal(TemporalType.DATE)
    private Date contractStartDate;
    @Column(name = "contract_end_date")
    @Temporal(TemporalType.DATE)
    private Date contractEndDate;
    @JoinTable(name = "md_specializaiton", joinColumns = {
        @JoinColumn(name = "physician", referencedColumnName = "physician_id")}, inverseJoinColumns = {
        @JoinColumn(name = "specialization", referencedColumnName = "specialization_id")})
    @ManyToMany
    private Collection<Specialization> specializationCollection;
    @JoinTable(name = "md_location", joinColumns = {
        @JoinColumn(name = "physician", referencedColumnName = "physician_id")}, inverseJoinColumns = {
        @JoinColumn(name = "location", referencedColumnName = "location_id")})
    @ManyToMany
    private Collection<Location> locationCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "physician1")
    private Collection<Preference> preferenceCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "physician")
    private Collection<ShiftSchedule> shiftScheduleCollection;

    public Physician() {
    }

    public Physician(Integer physicianId) {
        this.physicianId = physicianId;
    }

    public Physician(Integer physicianId, String physicianFname, String physicianLname) {
        this.physicianId = physicianId;
        this.physicianFname = physicianFname;
        this.physicianLname = physicianLname;
    }

    public Integer getPhysicianId() {
        return physicianId;
    }

    public void setPhysicianId(Integer physicianId) {
        this.physicianId = physicianId;
    }

    public String getPhysicianFname() {
        return physicianFname;
    }

    public void setPhysicianFname(String physicianFname) {
        this.physicianFname = physicianFname;
    }

    public String getPhysicianLname() {
        return physicianLname;
    }

    public void setPhysicianLname(String physicianLname) {
        this.physicianLname = physicianLname;
    }

    public String getPhysicianPhone() {
        return physicianPhone;
    }

    public void setPhysicianPhone(String physicianPhone) {
        this.physicianPhone = physicianPhone;
    }

    public String getPhysicianAddress() {
        return physicianAddress;
    }

    public void setPhysicianAddress(String physicianAddress) {
        this.physicianAddress = physicianAddress;
    }

    public String getPhysicianEmail() {
        return physicianEmail;
    }

    public void setPhysicianEmail(String physicianEmail) {
        this.physicianEmail = physicianEmail;
    }

    public byte[] getPhysicianPhoto() {
        return physicianPhoto;
    }

    public void setPhysicianPhoto(byte[] physicianPhoto) {
        this.physicianPhoto = physicianPhoto;
    }

    public Date getClientDate() {
        return clientDate;
    }

    public void setClientDate(Date clientDate) {
        this.clientDate = clientDate;
    }

    public Date getContractStartDate() {
        return contractStartDate;
    }

    public void setContractStartDate(Date contractStartDate) {
        this.contractStartDate = contractStartDate;
    }

    public Date getContractEndDate() {
        return contractEndDate;
    }

    public void setContractEndDate(Date contractEndDate) {
        this.contractEndDate = contractEndDate;
    }

    @XmlTransient
    public Collection<Specialization> getSpecializationCollection() {
        return specializationCollection;
    }

    public void setSpecializationCollection(Collection<Specialization> specializationCollection) {
        this.specializationCollection = specializationCollection;
    }

    @XmlTransient
    public Collection<Location> getLocationCollection() {
        return locationCollection;
    }

    public void setLocationCollection(Collection<Location> locationCollection) {
        this.locationCollection = locationCollection;
    }

    @XmlTransient
    public Collection<Preference> getPreferenceCollection() {
        return preferenceCollection;
    }

    public void setPreferenceCollection(Collection<Preference> preferenceCollection) {
        this.preferenceCollection = preferenceCollection;
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
        hash += (physicianId != null ? physicianId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Physician)) {
            return false;
        }
        Physician other = (Physician) object;
        if ((this.physicianId == null && other.physicianId != null) || (this.physicianId != null && !this.physicianId.equals(other.physicianId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Physician[ physicianId=" + physicianId + " ]";
    }
    
}
