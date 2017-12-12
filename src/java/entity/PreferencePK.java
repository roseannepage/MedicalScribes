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
public class PreferencePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "physician")
    private int physician;
    @Basic(optional = false)
    @NotNull
    @Column(name = "scribe")
    private int scribe;

    public PreferencePK() {
    }

    public PreferencePK(int physician, int scribe) {
        this.physician = physician;
        this.scribe = scribe;
    }

    public int getPhysician() {
        return physician;
    }

    public void setPhysician(int physician) {
        this.physician = physician;
    }

    public int getScribe() {
        return scribe;
    }

    public void setScribe(int scribe) {
        this.scribe = scribe;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) physician;
        hash += (int) scribe;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PreferencePK)) {
            return false;
        }
        PreferencePK other = (PreferencePK) object;
        if (this.physician != other.physician) {
            return false;
        }
        if (this.scribe != other.scribe) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PreferencePK[ physician=" + physician + ", scribe=" + scribe + " ]";
    }
    
}
