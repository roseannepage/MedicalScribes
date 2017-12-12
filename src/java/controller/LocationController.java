/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Location;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import service.LocationFacadeREST;

/**
 *
 * @author Jianing (Marie) Zhang
 */
@ManagedBean(name = "LocationController")
@ViewScoped
public class LocationController {

    // EJB
    @EJB
    private LocationFacadeREST ejbLocation;
    
    // property
    private Location newLocation;
    private String[] provinces;
    private String[] cities;    
    private String[] sites;    
    private String[] departments;
    private String[] areas;

    /**
     * Get the value of areas
     *
     * @return the value of areas
     */
    public String[] getAreas() {
        return areas;
    }

    /**
     * Get the value of departments
     *
     * @return the value of departments
     */
    public String[] getDepartments() {
        return departments;
    }

    /**
     * Get the value of sites
     *
     * @return the value of sites
     */
    public String[] getSites() {
        return sites;
    }   

    /**
     * Get the value of provinces
     *
     * @return the value of provinces
     */
    public String[] getProvinces() {
        return ejbLocation.getProvinces();
    }

    /**
     * Get the value of cities
     *
     * @return the value of cities
     */
    public String[] getCities() {
        return cities;
    }
        
    /**
     * Set the value of newLocation
     *
     * @param newLocation new value of newLocation
     */
    public void setNewLocation(Location newLocation) {
        this.newLocation = newLocation;
    }

    public Location getNewLocation() {
        return newLocation;
    }

    public LocationController() {
        this.newLocation = new Location();
    }   
    
    public String getLocationPage(){
        return "location";
    }

    public void provinceSelectListener(AjaxBehaviorEvent event) {
        cities = ejbLocation.getCities(newLocation.getProvince());
       
    }

    public void citySelectListener(AjaxBehaviorEvent event) {
        sites = ejbLocation.getSites(newLocation.getCity());
    }
    
    public void siteSelectListener(AjaxBehaviorEvent event) {
        departments = ejbLocation.getDepartments(newLocation.getSite());
    }
    
    public void departmentSelectListener(AjaxBehaviorEvent event) {
        areas = ejbLocation.getAreas(newLocation.getDepartment());
    }
    
    public void addNewLocation(){
        newLocation.setCountry("Canada");
        ejbLocation.create(newLocation);
        newLocation = new Location();
        clear();
    }    
    
    public void resetNewLocation(){
        newLocation = new Location();
        clear();
    }
    
    // helper
    private void clear(){
        provinces = null;
        cities = null;
        sites = null;
        departments = null;
        areas = null;
    }
}
