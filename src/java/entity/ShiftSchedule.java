/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Marie
 */
@Entity
@Table(name = "shift_schedule")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ShiftSchedule.findAll", query = "SELECT s FROM ShiftSchedule s")
    , @NamedQuery(name = "ShiftSchedule.findByShiftScheduleId", query = "SELECT s FROM ShiftSchedule s WHERE s.shiftScheduleId = :shiftScheduleId")
    , @NamedQuery(name = "ShiftSchedule.findByDateToFill", query = "SELECT s FROM ShiftSchedule s WHERE s.dateToFill = :dateToFill")
    , @NamedQuery(name = "ShiftSchedule.findByTimeIn", query = "SELECT s FROM ShiftSchedule s WHERE s.timeIn = :timeIn")
    , @NamedQuery(name = "ShiftSchedule.findByTimeOut", query = "SELECT s FROM ShiftSchedule s WHERE s.timeOut = :timeOut")
    , @NamedQuery(name = "ShiftSchedule.findByPatientsSeen", query = "SELECT s FROM ShiftSchedule s WHERE s.patientsSeen = :patientsSeen")
    , @NamedQuery(name = "ShiftSchedule.findByHighVolume", query = "SELECT s FROM ShiftSchedule s WHERE s.highVolume = :highVolume")
    , @NamedQuery(name = "ShiftSchedule.findByNote", query = "SELECT s FROM ShiftSchedule s WHERE s.note = :note")
    , @NamedQuery(name = "ShiftSchedule.findByWeek", query = "SELECT s FROM ShiftSchedule s WHERE FUNCTION('year', s.dateToFill) = :year AND FUNCTION('weekofyear', s.dateToFill) = :week AND s.scribe = null")   
})
public class ShiftSchedule implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "shift_schedule_id")
    private Integer shiftScheduleId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_to_fill")
    @Temporal(TemporalType.DATE)
    private Date dateToFill;
    @Column(name = "time_in")
    @Temporal(TemporalType.TIME)
    private Date timeIn;
    @Column(name = "time_out")
    @Temporal(TemporalType.TIME)
    private Date timeOut;
    @Column(name = "patients_seen")
    private Integer patientsSeen;
    @Column(name = "high_volume")
    private Boolean highVolume;
    @Size(max = 2000)
    @Column(name = "note")
    private String note;
    @JoinColumn(name = "physician", referencedColumnName = "physician_id")
    @ManyToOne(optional = false)
    private Physician physician;
    @JoinColumn(name = "scribe", referencedColumnName = "scribe_id")
    @ManyToOne
    private Scribe scribe;
    @JoinColumn(name = "location", referencedColumnName = "location_id")
    @ManyToOne(optional = false)
    private Location location;
    @JoinColumn(name = "specialization", referencedColumnName = "specialization_id")
    @ManyToOne(optional = false)
    private Specialization specialization;
    @JoinColumn(name = "shift", referencedColumnName = "shift_id")
    @ManyToOne(optional = false)
    private Shift shift;

    public ShiftSchedule() {
    }

    public ShiftSchedule(Integer shiftScheduleId) {
        this.shiftScheduleId = shiftScheduleId;
    }

    public ShiftSchedule(Integer shiftScheduleId, Date dateToFill) {
        this.shiftScheduleId = shiftScheduleId;
        this.dateToFill = dateToFill;
    }

    public Integer getShiftScheduleId() {
        return shiftScheduleId;
    }

    public void setShiftScheduleId(Integer shiftScheduleId) {
        this.shiftScheduleId = shiftScheduleId;
    }

    public Date getDateToFill() {
        return dateToFill;
    }

    public void setDateToFill(Date dateToFill) {
        this.dateToFill = dateToFill;
    }

    public Date getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(Date timeIn) {
        this.timeIn = timeIn;
    }

    public Date getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Date timeOut) {
        this.timeOut = timeOut;
    }

    public Integer getPatientsSeen() {
        return patientsSeen;
    }

    public void setPatientsSeen(Integer patientsSeen) {
        this.patientsSeen = patientsSeen;
    }

    public Boolean getHighVolume() {
        return highVolume;
    }

    public void setHighVolume(Boolean highVolume) {
        this.highVolume = highVolume;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Physician getPhysician() {
        return physician;
    }

    public void setPhysician(Physician physician) {
        this.physician = physician;
    }

    public Scribe getScribe() {
        return scribe;
    }

    public void setScribe(Scribe scribe) {
        this.scribe = scribe;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (shiftScheduleId != null ? shiftScheduleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ShiftSchedule)) {
            return false;
        }
        ShiftSchedule other = (ShiftSchedule) object;
        if ((this.shiftScheduleId == null && other.shiftScheduleId != null) || (this.shiftScheduleId != null && !this.shiftScheduleId.equals(other.shiftScheduleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ShiftSchedule[ shiftScheduleId=" + shiftScheduleId + " ]";
    }
    
}
