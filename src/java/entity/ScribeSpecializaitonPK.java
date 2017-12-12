/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;


/**
 *
 * @author Marie
 */
@Embeddable
public class ScribeSpecializaitonPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "specialization")
    private int specialization;
    @Basic(optional = false)
    @NotNull
    @Column(name = "scribe")
    private int scribe;
    @Basic(optional = false)
    @NotNull
    @Column(name = "location")
    private int location;

    public ScribeSpecializaitonPK() {
    }

    public ScribeSpecializaitonPK(int specialization, int scribe, int location) {
        this.specialization = specialization;
        this.scribe = scribe;
        this.location = location;
    }

    public int getSpecialization() {
        return specialization;
    }

    public void setSpecialization(int specialization) {
        this.specialization = specialization;
    }

    public int getScribe() {
        return scribe;
    }

    public void setScribe(int scribe) {
        this.scribe = scribe;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) specialization;
        hash += (int) scribe;
        hash += (int) location;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ScribeSpecializaitonPK)) {
            return false;
        }
        ScribeSpecializaitonPK other = (ScribeSpecializaitonPK) object;
        if (this.specialization != other.specialization) {
            return false;
        }
        if (this.scribe != other.scribe) {
            return false;
        }
        if (this.location != other.location) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ScribeSpecializaitonPK[ specialization=" + specialization + ", scribe=" + scribe + ", location=" + location + " ]";
    }
    
}
