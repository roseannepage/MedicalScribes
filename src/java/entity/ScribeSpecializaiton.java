/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement; 

/**
 *
 * @author Marie
 */
@Entity
@Table(name = "scribe_specializaiton")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ScribeSpecializaiton.findAll", query = "SELECT s FROM ScribeSpecializaiton s")
    , @NamedQuery(name = "ScribeSpecializaiton.findBySpecialization", query = "SELECT s FROM ScribeSpecializaiton s WHERE s.scribeSpecializaitonPK.specialization = :specialization")
    , @NamedQuery(name = "ScribeSpecializaiton.findByScribe", query = "SELECT s FROM ScribeSpecializaiton s WHERE s.scribeSpecializaitonPK.scribe = :scribe")
    , @NamedQuery(name = "ScribeSpecializaiton.findByLocation", query = "SELECT s FROM ScribeSpecializaiton s WHERE s.scribeSpecializaitonPK.location = :location")})
public class ScribeSpecializaiton implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ScribeSpecializaitonPK scribeSpecializaitonPK;
    @JoinColumn(name = "scribe", referencedColumnName = "scribe_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Scribe scribe1;
    @JoinColumn(name = "specialization", referencedColumnName = "specialization_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Specialization specialization1;
    @JoinColumn(name = "location", referencedColumnName = "location_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Location location1;

    public ScribeSpecializaiton() {
    }

    public ScribeSpecializaiton(ScribeSpecializaitonPK scribeSpecializaitonPK) {
        this.scribeSpecializaitonPK = scribeSpecializaitonPK;
    }

    public ScribeSpecializaiton(int specialization, int scribe, int location) {
        this.scribeSpecializaitonPK = new ScribeSpecializaitonPK(specialization, scribe, location);
    }

    public ScribeSpecializaitonPK getScribeSpecializaitonPK() {
        return scribeSpecializaitonPK;
    }

    public void setScribeSpecializaitonPK(ScribeSpecializaitonPK scribeSpecializaitonPK) {
        this.scribeSpecializaitonPK = scribeSpecializaitonPK;
    }

    public Scribe getScribe1() {
        return scribe1;
    }

    public void setScribe1(Scribe scribe1) {
        this.scribe1 = scribe1;
    }

    public Specialization getSpecialization1() {
        return specialization1;
    }

    public void setSpecialization1(Specialization specialization1) {
        this.specialization1 = specialization1;
    }

    public Location getLocation1() {
        return location1;
    }

    public void setLocation1(Location location1) {
        this.location1 = location1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (scribeSpecializaitonPK != null ? scribeSpecializaitonPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ScribeSpecializaiton)) {
            return false;
        }
        ScribeSpecializaiton other = (ScribeSpecializaiton) object;
        if ((this.scribeSpecializaitonPK == null && other.scribeSpecializaitonPK != null) || (this.scribeSpecializaitonPK != null && !this.scribeSpecializaitonPK.equals(other.scribeSpecializaitonPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ScribeSpecializaiton[ scribeSpecializaitonPK=" + scribeSpecializaitonPK + " ]";
    }
    
}
