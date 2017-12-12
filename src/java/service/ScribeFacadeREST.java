/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Availability;
import entity.Scribe;
import entity.ShiftSchedule;
import java.util.Date;
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
@Path("entity.scribe")
public class ScribeFacadeREST extends AbstractFacade<Scribe> {

    @PersistenceContext(unitName = "ScribeManagementPU")
    private EntityManager em;

    public ScribeFacadeREST() {
        super(Scribe.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Scribe entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Scribe entity) {
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
    public Scribe find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Scribe> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Scribe> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
    public List<Scribe> findQualifiedScribe() {
        return em.createNamedQuery("Scribe.findByGeneralQualified" )
                .setParameter("generalQualified", true)
                .getResultList();    
    }
    
    // get a list of scribe and number of scheduled shifts pair, order by scheduled shifts
    public List<Object[]> getShiftsOfWeek(int year, int week, String contractType) {
        return em.createNamedQuery("Scribe.findShiftsOfWeek")
                .setParameter("year", year)
                .setParameter("week", week)
                .setParameter("contractType", contractType)
                .getResultList();        
    } 
    
    public long getScribeShiftsOfWeek(int year, int week, int scribeId) {
        
        List scribeShiftsList = em.createNamedQuery("Scribe.findScribeShiftsOfWeek")
                .setParameter("year", year)
                .setParameter("week", week)
                .setParameter("scribeId", scribeId)
                .getResultList();
        if (scribeShiftsList.size() == 0) return 0;
        else{
            Object[] scribeShifts = (Object[])(scribeShiftsList.get(0));
            return (long)scribeShifts[1]; 
        }
    }
    
    public List<Availability> getScribeAvailabilityOfDay(Date date, int scribeId) {
        return em.createNamedQuery("Scribe.findScribeAvailabilityOfDay")
                .setParameter("date", date)
                .setParameter("scribeId", scribeId)
                .getResultList();
        
    }
    
    public List<ShiftSchedule> getScribeShiftsOfDay(Date date, int scribeId) {
        List<ShiftSchedule> scribeShifts = em.createNamedQuery("Scribe.findScribeShiftsOfDay")
                .setParameter("date", date)
                .setParameter("scribeId", scribeId)
                .getResultList();
        return scribeShifts;
    }
    
    public List<Scribe> getScribeById(int scribeId){
        return em.createNamedQuery("Scribe.findByScribeId" )
                .setParameter("scribeId", scribeId)
                .getResultList();       
    }
    
}
