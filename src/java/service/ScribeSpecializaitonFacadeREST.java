/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.ScribeSpecializaiton;
import entity.ScribeSpecializaitonPK;
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
import javax.ws.rs.core.PathSegment;

/**
 *
 * @author Marie
 */
@Stateless
@Path("entity.scribespecializaiton")
public class ScribeSpecializaitonFacadeREST extends AbstractFacade<ScribeSpecializaiton> {

    @PersistenceContext(unitName = "ScribeManagementPU")
    private EntityManager em;

    private ScribeSpecializaitonPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;specialization=specializationValue;scribe=scribeValue;location=locationValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        entity.ScribeSpecializaitonPK key = new entity.ScribeSpecializaitonPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> specialization = map.get("specialization");
        if (specialization != null && !specialization.isEmpty()) {
            key.setSpecialization(new java.lang.Integer(specialization.get(0)));
        }
        java.util.List<String> scribe = map.get("scribe");
        if (scribe != null && !scribe.isEmpty()) {
            key.setScribe(new java.lang.Integer(scribe.get(0)));
        }
        java.util.List<String> location = map.get("location");
        if (location != null && !location.isEmpty()) {
            key.setLocation(new java.lang.Integer(location.get(0)));
        }
        return key;
    }

    public ScribeSpecializaitonFacadeREST() {
        super(ScribeSpecializaiton.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(ScribeSpecializaiton entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") PathSegment id, ScribeSpecializaiton entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        entity.ScribeSpecializaitonPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public ScribeSpecializaiton find(@PathParam("id") PathSegment id) {
        entity.ScribeSpecializaitonPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<ScribeSpecializaiton> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<ScribeSpecializaiton> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
}
