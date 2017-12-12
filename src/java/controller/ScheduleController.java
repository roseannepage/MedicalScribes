/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author Jianing (Marie) Zhang, use PrimeFaces library
 */
import entity.Availability;
import entity.Location;
import entity.Preference;
import entity.PreferencePK;
import entity.Scribe;
import entity.ScribeSpecializaitonPK;
import entity.Shift;
import entity.ShiftSchedule;
import helper.ScheduleTool;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import service.AvailabilityFacadeREST;
import service.PreferenceFacadeREST;
import service.ScribeFacadeREST;
import service.ScribeSpecializaitonFacadeREST;
import service.ShiftFacadeREST;
import service.ShiftScheduleFacadeREST;

@ManagedBean
@SessionScoped
public class ScheduleController implements Serializable {

    public static final String FULLTIME = "Full-time";
    public static final String PARTTIME = "Part-time";
    public static final int MAX_SHIFTS_PER_WEEK = 5;
    public static final int MIN_FULLTIME_SHIFTS = 3;

    // EJB
    @EJB
    private ScribeFacadeREST ejbScribe;
    @EJB
    private ShiftScheduleFacadeREST ejbShiftSchedule;
    @EJB
    private ScribeSpecializaitonFacadeREST ebjScribeSpecialization;
    @EJB
    private ShiftFacadeREST ejbShift;
    @EJB
    private AvailabilityFacadeREST ejbAvailability;
    @EJB
    private PreferenceFacadeREST ejbPreference;

    // others
    private List<Object[]> fullTimeScribes;
    private List<Object[]> partTimeScribes;
    private int availableScribes;
    private List<ShiftSchedule> requests;

    // for view
    private Date selectDate;
    private int employeeId;
    private ScheduleModel eventModel = new DefaultScheduleModel();;

    private ScheduleModel lazyEventModel;

    private ScheduleEvent event = new DefaultScheduleEvent();

    /**
     * Get the value of employeeId
     *
     * @return the value of employeeId
     */
    public int getEmployeeId() {
        return employeeId;
    }

    /**
     * Set the value of employeeId
     *
     * @param employeeId new value of employeeId
     */
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * Get the value of selectDate
     *
     * @return the value of selectDate
     */
    public Date getSelectDate() {
        return selectDate;
    }

    /**
     * Set the value of selectDate
     *
     * @param selectDate new value of selectDate
     */
    public void setSelectDate(Date selectDate) {
        this.selectDate = selectDate;
    }  

    public String getSchedulePage() {

        Calendar calendar = Calendar.getInstance();
        if (selectDate != null) {
            calendar.setTime(selectDate);
            schedule(calendar.get(Calendar.YEAR), calendar.get(Calendar.WEEK_OF_YEAR));
        }
        
        return getSupervisorPage();

    }
    
    public String getEmployeePage(){
        
        eventModel = new DefaultScheduleModel();
        
        if (employeeId != 0) {
            List<ShiftSchedule> schedules = (List<ShiftSchedule>) ejbScribe.getScribeById(employeeId).get(0).getShiftScheduleCollection();
            addToCalendar(schedules);
        }
        
        return "schedule";       
    }
    
    public String getSupervisorPage(){
        eventModel = new DefaultScheduleModel();

        List<ShiftSchedule> schedules = ejbShiftSchedule.findAll();

        addToCalendar(schedules);
        
        return "schedule";
    }
    
    private void addToCalendar(List<ShiftSchedule> schedules){
        
        if (schedules == null) 
            return;
        
        for (ShiftSchedule s : schedules) {
            if (s.getScribe() != null) {
                String event = s.getScribe().getScribeFname();

                Date date = s.getDateToFill();
                Date startTime = s.getShift().getStartTime();
                Date endTime = s.getShift().getEndTime();

                Date start = new Date(date.getYear(), date.getMonth(), date.getDate(), startTime.getHours(), startTime.getMinutes());
                Date end = new Date(date.getYear(), date.getMonth(), date.getDate());

                if (s.getShift().getOvernight()) {
                    end = ScheduleTool.plusOneDay(s.getDateToFill());

                } else {
                    end = s.getDateToFill();
                }
                end.setHours(endTime.getHours());
                end.setMinutes(endTime.getMinutes());

                eventModel.addEvent(new DefaultScheduleEvent(event, start, end));
            }
        }

    }

    public void schedule(int year, int week) {
        
        // ------------------------temperary code for testing-------------------------


       
        // ------------------------temperary code for testing-------------------------
        
        
        
        
        
        
        // requests by year and week, only get the schedule has not been filled
        requests = ejbShiftSchedule.getWeeklySchedule(year, week);

        // schedule each request
        for (ShiftSchedule request : requests) {
            
            // if reqeust has already been scheduled, go to next
            if (request.getScribe() != null) {
                continue;
            }
            
            // check preferred scribe first
            Collection<Preference> preferences = request.getPhysician().getPreferenceCollection();
            for (Preference preference : preferences) {
                Scribe scribe = preference.getScribe1();
                // preferred, not overtime, condition qualified
                if (preference.getPreference() && ejbScribe.getScribeShiftsOfWeek(year, week, scribe.getScribeId()) < MAX_SHIFTS_PER_WEEK && shedulePreferredScribe(request, scribe)) {
                    break;
                }
            }

            // next check full-time, part-time based on business rule
            if (request.getScribe() == null) {

                // full-time and part-time general qualified scribe list ordered by total shifts already scheduled in the week
                // the query result is list of Object[], each Object[] contains two elements only, scribe and the scheduled shifts of the scribe 
                fullTimeScribes = ejbScribe.getShiftsOfWeek(year, week, FULLTIME);
                partTimeScribes = ejbScribe.getShiftsOfWeek(year, week, PARTTIME);

                // index of scribe in the list, represent scribe that going to be checked
                int fulltimeIndex = 0, partimeIndex = 0;
                // the scheduled shifts of scribe represent by fulltimeIndex/partimeIndex
                long scheduledShiftsFT, scheduledShiftsPT;

                while (fulltimeIndex < fullTimeScribes.size() && partimeIndex < partTimeScribes.size()) {

                    // extract scribe and scheduled shifts of the scribe 
                    Object[] scribeShiftsPairFull = fullTimeScribes.get(fulltimeIndex);
                    Scribe scribeFullTime = (Scribe) scribeShiftsPairFull[0];
                    scheduledShiftsFT = (long)scribeShiftsPairFull[1];

                    // if already hit maxmum shifts limit, since the list is orderd by qty of scheduled shifts, 
                    // no need to check the rest, all the rest will hit maxmum as well
                    if (scheduledShiftsFT == MAX_SHIFTS_PER_WEEK) {
                        fulltimeIndex = fullTimeScribes.size();
                        break;
                    }

                    Object[] scribeShiftsPairPart = partTimeScribes.get(partimeIndex);
                    Scribe scribePartTime = (Scribe) scribeShiftsPairPart[0];
                    scheduledShiftsPT = (long) scribeShiftsPairPart[1];
                    if (scheduledShiftsPT == MAX_SHIFTS_PER_WEEK) {
                        partimeIndex = partTimeScribes.size();
                        break;
                    }

                    // schedule based on business rule
                    if (scheduledShiftsFT - scheduledShiftsPT < MIN_FULLTIME_SHIFTS) {
                        if (sheduleNormalScribe(request, scribeFullTime)) {
                            break;
                        } else {
                            fulltimeIndex++;
                        }
                    } else {
                        if (sheduleNormalScribe(request, scribePartTime)) {
                            break;
                        } else {
                            partimeIndex++;
                        }
                    }
                }

                // if one of the list already finish screen, check all the rest of the other one
                if (request.getScribe() == null && partimeIndex < partTimeScribes.size()) {
                    for (int i = partimeIndex; i < partTimeScribes.size(); i++) {
                        Object[] scribeShiftsPairPart = partTimeScribes.get(partimeIndex);
                        Scribe scribePartTime = (Scribe) scribeShiftsPairPart[0];
                        scheduledShiftsPT = (long) scribeShiftsPairPart[1];
                        if (scheduledShiftsPT == MAX_SHIFTS_PER_WEEK) {
                            break;
                        }
                        if (sheduleNormalScribe(request, scribePartTime)) {
                            break;
                        }
                    }
                }

                // if one of the list already finish screen, check all the rest of the other one
                if (request.getScribe() == null && fulltimeIndex < fullTimeScribes.size()) {
                    for (int i = fulltimeIndex; i < fullTimeScribes.size(); i++) {
                        Object[] scribeShiftsPairFull = fullTimeScribes.get(fulltimeIndex);
                        Scribe scribeFullTime = (Scribe) scribeShiftsPairFull[0];
                        scheduledShiftsFT = (long) scribeShiftsPairFull[1];
                        if (scheduledShiftsFT == MAX_SHIFTS_PER_WEEK) {
                            break;
                        }
                        if (sheduleNormalScribe(request, scribeFullTime)) {
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * try to schedule a normal scribe, and write result to database if schedule successfully
     * 
     * @param request
     * @param scribe
     * @return true if schedule successfully, otherwise false;
     */
    private boolean sheduleNormalScribe(ShiftSchedule request, Scribe scribe) {

        // verify scribe is not avoided by physician
        PreferencePK key = new PreferencePK(request.getPhysician().getPhysicianId(), scribe.getScribeId());
        Preference preference = ejbPreference.find(key);
        if(preference != null && !preference.getPreference()) return false;
//         for (Preference preference : preferences) {
//             if(preference.getScribe1().getScribeId() == scribe.getScribeId() && !preference.getPreference())
//                 return false;
//         }
        
        // then check all condition same as preferred scribe
        return shedulePreferredScribe(request, scribe);
    }
    
    /**
     * try to schedule a preferred scribe, and write result to database if schedule successfully
     * @param request
     * @param scribe
     * @return true if schedule successfully, otherwise false;
     */
    private boolean shedulePreferredScribe(ShiftSchedule request, Scribe scribe) {
        
        // active location
        Location location = null;
        for (Location loc : scribe.getActiveLocationCollection()) {
            if (Objects.equals(loc.getLocationId(), request.getLocation().getLocationId())) {
                location = loc;
                break;
            }
        }
        if (location == null) 
            return false;
        
        // specialization training
        ScribeSpecializaitonPK key = new ScribeSpecializaitonPK(request.getSpecialization().getSpecializationId(),
                scribe.getScribeId(),
                location.getLocationId());
        if (ebjScribeSpecialization.find(key) == null) {
            return false;
        }

        // availability       
        // get the shift that the request specified, which include the date and time slot information
        Shift shift = ejbShift.find(request.getShift().getShiftId());

        // get the availabilities of the scribe on that same day
        List<Availability> availabilities = ejbScribe.getScribeAvailabilityOfDay(request.getDateToFill(), scribe.getScribeId());

        for (Availability availability : availabilities) {

            // available date
            Date availableDate = availability.getAvailableDate();

            // if this availability has been booked, or not same day or time slot does not match, then schedule unsuccessfully
            if (availability.getBooked()
                    || !request.getDateToFill().equals(availableDate) 
                    && isTimeMatched(shift.getStartTime(), shift.getEndTime(), shift.getOvernight(),
                            availability.getStartTime(), availability.getEndTime(), availability.getOvernight()))
                return false;
            
            // check scribe previous day and one day after schedule, make sure there is 8 hours between scheduled shifts
            Date beforeDate = ScheduleTool.minusOneDay(availableDate);
            List<ShiftSchedule> preDaySchedule = ejbScribe.getScribeShiftsOfDay(beforeDate, scribe.getScribeId());
            for (ShiftSchedule schedule : preDaySchedule) {
                if (!hasEightHoursDiff(schedule.getShift().getEndTime(), request.getShift().getStartTime(), schedule.getShift().getOvernight())) {
                    return false;
                }
            }
            Date afterDate = ScheduleTool.plusOneDay(availableDate);
            List<ShiftSchedule> afterDaySchedule = ejbScribe.getScribeShiftsOfDay(afterDate, scribe.getScribeId());
            for (ShiftSchedule schedule : afterDaySchedule){
                if (!hasEightHoursDiff(availability.getEndTime(), schedule.getShift().getStartTime(), availability.getOvernight()))
                    return false;
            }
            
            // Also check the same day schedule in case a manually scheduled shift already been arranged
            List<ShiftSchedule> todaySchedule = ejbScribe.getScribeShiftsOfDay(availableDate, scribe.getScribeId());
            if (todaySchedule.size() > 0) {
                return false;
            }

            // the scribe meet all requirement, set the scribe for the schedule, and booked scribe's all availabilities on that day
            // this is to meet the requirement "cannot schedule a scribe for a double shift"
            request.setScribe(scribe);
            ejbShiftSchedule.edit(request);
            availabilities.forEach((eachAvailability) -> {
                eachAvailability.setBooked(true);
                ejbAvailability.edit(eachAvailability);
            });
            return true;

        }

        return false;
    }
       
    
    private boolean isTimeMatched(Date shiftStartTime, Date shiftEndTime, boolean shiftOvernight, Date scribeStartTime, Date scribeEndTime, boolean scribeOvernight){
        Date newShiftEndTime = shiftEndTime;
        Date newScribeEndTime = scribeEndTime;
        
        // adjust available end time if it is overnight
        if (shiftOvernight) {
            newShiftEndTime = ScheduleTool.plusOneDay(shiftEndTime);
        }      
        if (scribeOvernight){
            newScribeEndTime = ScheduleTool.plusOneDay(scribeEndTime);
        }
        
        return !shiftStartTime.before(scribeStartTime) // scribe start time no later than shift start time
                    && !newScribeEndTime.before(newShiftEndTime); // scribe end time no early than shift end time
    }

    private boolean hasEightHoursDiff(Date endTime, Date startTime, boolean endTimeOvernight){
        if(endTimeOvernight) {
            endTime = ScheduleTool.plusOneDay(endTime);
            return startTime.getHours() - endTime.getHours() > 8 || 
                    startTime.getHours() - endTime.getHours() == 8 && startTime.getMinutes() - endTime.getMinutes() >= 0;
        }
        else{
            return 24 - endTime.getHours() + startTime.getHours() > 8 ||
                    24 - endTime.getHours() + startTime.getHours() == 8 && startTime.getMinutes() - endTime.getMinutes() >= 0;
        }
    }

//    @PostConstruct
//    public void init() {
//
//    }

    public Date getRandomDate(Date base) {
        Calendar date = Calendar.getInstance();
        date.setTime(base);
        date.add(Calendar.DATE, ((int) (Math.random() * 30)) + 1);    //set random day of month

        return date.getTime();
    }

    public Date getInitialDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), Calendar.FEBRUARY, calendar.get(Calendar.DATE), 0, 0, 0);

        return calendar.getTime();
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public ScheduleModel getLazyEventModel() {
        return lazyEventModel;
    }

    private Calendar today() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);

        return calendar;
    }

    private Date previousDay8Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) - 1);
        t.set(Calendar.HOUR, 8);

        return t.getTime();
    }

    private Date previousDay11Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) - 1);
        t.set(Calendar.HOUR, 11);

        return t.getTime();
    }

    private Date today1Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.HOUR, 1);

        return t.getTime();
    }

    private Date theDayAfter3Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.DATE, t.get(Calendar.DATE) + 2);
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.HOUR, 3);

        return t.getTime();
    }

    private Date today6Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.HOUR, 6);

        return t.getTime();
    }

    private Date nextDay9Am() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.AM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) + 1);
        t.set(Calendar.HOUR, 9);

        return t.getTime();
    }

    private Date nextDay11Am() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.AM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) + 1);
        t.set(Calendar.HOUR, 11);

        return t.getTime();
    }

    private Date fourDaysLater3pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) + 4);
        t.set(Calendar.HOUR, 3);

        return t.getTime();
    }

    public ScheduleEvent getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }

    public void addEvent(ActionEvent actionEvent) {
        if (event.getId() == null) {
            eventModel.addEvent(event);
        } else {
            eventModel.updateEvent(event);
        }

        event = new DefaultScheduleEvent();
    }

    public void onEventSelect(SelectEvent selectEvent) {
        event = (ScheduleEvent) selectEvent.getObject();
    }

    public void onDateSelect(SelectEvent selectEvent) {
        event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
    }

    public void onEventMove(ScheduleEntryMoveEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());

        addMessage(message);
    }

    public void onEventResize(ScheduleEntryResizeEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());

        addMessage(message);
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

}
