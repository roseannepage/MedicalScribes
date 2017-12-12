/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Marie
 */
@Entity
@Table(name = "specialization")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Specialization.findAll", query = "SELECT s FROM Specialization s")
    , @NamedQuery(name = "Specialization.findBySpecializationId", query = "SELECT s FROM Specialization s WHERE s.specializationId = :specializationId")
    , @NamedQuery(name = "Specialization.findBySpecializationName", query = "SELECT s FROM Specialization s WHERE s.specializationName = :specializationName")})
public class Specialization implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "specialization_id")
    private Integer specializationId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "specialization_name")
    private String specializationName;
    @ManyToMany(mappedBy = "specializationCollection")
    private Collection<Physician> physicianCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "specialization1")
    private Collection<ScribeSpecializaiton> scribeSpecializaitonCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "specialization")
    private Collection<ShiftSchedule> shiftScheduleCollection;

    public Specialization() {
    }

    public Specialization(Integer specializationId) {
        this.specializationId = specializationId;
    }

    public Specialization(Integer specializationId, String specializationName) {
        this.specializationId = specializationId;
        this.specializationName = specializationName;
    }

    public Integer getSpecializationId() {
        return specializationId;
    }

    public void setSpecializationId(Integer specializationId) {
        this.specializationId = specializationId;
    }

    public String getSpecializationName() {
        return specializationName;
    }

    public void setSpecializationName(String specializationName) {
        this.specializationName = specializationName;
    }

    @XmlTransient
    public Collection<Physician> getPhysicianCollection() {
        return physicianCollection;
    }

    public void setPhysicianCollection(Collection<Physician> physicianCollection) {
        this.physicianCollection = physicianCollection;
    }

    @XmlTransient
    public Collection<ScribeSpecializaiton> getScribeSpecializaitonCollection() {
        return scribeSpecializaitonCollection;
    }

    public void setScribeSpecializaitonCollection(Collection<ScribeSpecializaiton> scribeSpecializaitonCollection) {
        this.scribeSpecializaitonCollection = scribeSpecializaitonCollection;
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
        hash += (specializationId != null ? specializationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Specialization)) {
            return false;
        }
        Specialization other = (Specialization) object;
        if ((this.specializationId == null && other.specializationId != null) || (this.specializationId != null && !this.specializationId.equals(other.specializationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Specialization[ specializationId=" + specializationId + " ]";
    }
    
}
