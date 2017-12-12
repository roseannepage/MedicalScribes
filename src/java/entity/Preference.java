/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Marie
 */
@Entity
@Table(name = "preference")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Preference.findAll", query = "SELECT p FROM Preference p")
    , @NamedQuery(name = "Preference.findByPhysician", query = "SELECT p FROM Preference p WHERE p.preferencePK.physician = :physician")
    , @NamedQuery(name = "Preference.findByScribe", query = "SELECT p FROM Preference p WHERE p.preferencePK.scribe = :scribe")
    , @NamedQuery(name = "Preference.findByPreference", query = "SELECT p FROM Preference p WHERE p.preference = :preference")
})
public class Preference implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PreferencePK preferencePK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "preference")
    private boolean preference;
    @JoinColumn(name = "scribe", referencedColumnName = "scribe_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Scribe scribe1;
    @JoinColumn(name = "physician", referencedColumnName = "physician_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Physician physician1;

    public Preference() {
    }

    public Preference(PreferencePK preferencePK) {
        this.preferencePK = preferencePK;
    }

    public Preference(PreferencePK preferencePK, boolean preference) {
        this.preferencePK = preferencePK;
        this.preference = preference;
    }

    public Preference(int physician, int scribe) {
        this.preferencePK = new PreferencePK(physician, scribe);
    }

    public PreferencePK getPreferencePK() {
        return preferencePK;
    }

    public void setPreferencePK(PreferencePK preferencePK) {
        this.preferencePK = preferencePK;
    }

    public boolean getPreference() {
        return preference;
    }

    public void setPreference(boolean preference) {
        this.preference = preference;
    }

    public Scribe getScribe1() {
        return scribe1;
    }

    public void setScribe1(Scribe scribe1) {
        this.scribe1 = scribe1;
    }

    public Physician getPhysician1() {
        return physician1;
    }

    public void setPhysician1(Physician physician1) {
        this.physician1 = physician1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (preferencePK != null ? preferencePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Preference)) {
            return false;
        }
        Preference other = (Preference) object;
        if ((this.preferencePK == null && other.preferencePK != null) || (this.preferencePK != null && !this.preferencePK.equals(other.preferencePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Preference[ preferencePK=" + preferencePK + " ]";
    }
    
}
