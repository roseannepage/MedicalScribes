/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Location;
import java.util.Arrays;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Marie
 */
@Stateless
@Path("entity.location")
public class LocationFacadeREST extends AbstractFacade<Location> {

    @PersistenceContext(unitName = "ScribeManagementPU")
    private EntityManager em;

    public LocationFacadeREST() {
        super(Location.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Location entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Location entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Location find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Location> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Location> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public String[] getProvinces() {
        Object[] objectArray = em.createNamedQuery("Location.findProvinces")
                .getResultList().toArray();
        if (objectArray.length != 0 && objectArray[0] != null) {
            return Arrays.asList(objectArray).toArray(new String[objectArray.length]);
        } else {
            return null;
        }
    }

    public String[] getCities(String province) {
        Object[] objectArray = em.createNamedQuery("Location.findCities")
                .setParameter("province", province)
                .getResultList().toArray();
        if (objectArray.length != 0 && objectArray[0] != null) {
            return Arrays.asList(objectArray).toArray(new String[objectArray.length]);
        } else {
            return null;
        }
    }

    public String[] getSites(String city) {
        Object[] objectArray = em.createNamedQuery("Location.findSites")
                .setParameter("city", city)
                .getResultList().toArray();
        if (objectArray.length != 0 && objectArray[0] != null) {
            return Arrays.asList(objectArray).toArray(new String[objectArray.length]);
        } else {
            return null;
        }
    }

    public String[] getDepartments(String site) {
        Object[] objectArray = em.createNamedQuery("Location.findDepartments")
                .setParameter("site", site)
                .getResultList().toArray();
        if (objectArray.length != 0 && objectArray[0] != null) {
            return Arrays.asList(objectArray).toArray(new String[objectArray.length]);
        } else {
            return null;
        }
    }

    public String[] getAreas(String department) {
        Object[] objectArray = em.createNamedQuery("Location.findAreas")
                .setParameter("department", department)
                .getResultList().toArray();
        if (objectArray.length != 0 && objectArray[0] != null) {
            return Arrays.asList(objectArray).toArray(new String[objectArray.length]);
        } else {
            return null;
        }
    }
}
