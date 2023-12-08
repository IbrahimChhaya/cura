using System;
using System.Collections.Generic;
using System.Linq;
using MHC_API.Data;
using MHC_API.Model;
using Microsoft.AspNetCore.Mvc;

namespace MHC_API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class PatientController : ControllerBase
    {
        private MHCDatabaseDBContext db;

        public PatientController(MHCDatabaseDBContext context)
        {
            db = context;
        }

        //get a list of patients (users) linked to a psychologist
        [HttpGet("GetPsychologistPatients/{psychId}")]
        public List<User> GetPsychologistPatients(int psychId)
        {
            //get list of pair ids linked to pysch from link table
            var psychLinks = (from l in db.Link
                              where l.PsychID.Equals(psychId) && l.Status.Equals("Y")
                              select l.PairID);

            List<int> links = new List<int>();

            if (psychLinks.Any())
            {
                foreach (int id in psychLinks)
                {
                    //get pair child id
                    var childId = (from p in db.Pair
                                   where p.PairID.Equals(id)
                                   select p.ChildID).FirstOrDefault();

                    if (childId != 0)
                    {
                        links.Add(childId);
                    }
                }

                return getPatients(links);
            }
            else
            {
                return null;    //psych has no patients
            }

        }

        //utility method to get patients given a list of their ids
        private List<User> getPatients(List<int> ids)
        {
            List<User> patients = new List<User>();

            foreach (int id in ids)
            {
                var tempChild = getUser(id);

                if (tempChild != null)
                {
                    patients.Add(tempChild);
                }
            }

            return patients;
        }

        //get a user given their id
        private User getUser(int id)
        {
            var user = (from u in db.User
                        where u.UserID.Equals(id)
                        select u).FirstOrDefault();

            if (user != null)
            {
                return user;
            }
            else
            {
                return null;
            }
        }

        //returns patient, guardian, next appointment, and last appointment
        [HttpGet("getPsychPatients/{psychID}")]
        public List<Patient> getPsychPatients(int psychID)
        {
            UserController userController = new UserController(db);
            BookingsController bkgController = new BookingsController(db);


            List<Patient> patients = new List<Patient>();

            //get children
            var children = GetPsychologistPatients(psychID);

            if (children != null)
            {
                foreach (User child in children)
                {
                    Patient tempPatient = new Patient();

                    tempPatient.patientInfo = child;

                    User parent = userController.GetGuardian(child.UserID); // get child's guardian

                    if (parent != null)
                    {
                        tempPatient.guardian = parent;  //
                    }
                    else
                    {
                        // no parent found
                    }

                    //get previous appointment date
                    var lastAppointment = bkgController.GetLastAppointmentDate(child.UserID);
                    tempPatient.lastAppointment = lastAppointment;

                    //get next appointment date
                    var nextAppointment = bkgController.GetNextBooking(child.UserID);
                    tempPatient.nextAppointment = nextAppointment.Date;

                    //get focus points
                    HubController hubController = new HubController(db);
                    List<RHubProblems> points = hubController.fetchChildFocusPoints(child.UserID);

                    if (points != null)
                    {
                        List<String> pointNames = new List<String>();
                        foreach (RHubProblems point in points)
                        {
                            pointNames.Add(point.Problem);
                        }

                        tempPatient.focusPoints = pointNames;
                    }

                    //add the child to the list of patients
                    patients.Add(tempPatient);
                }

                return patients;
            }
            else
            {
                return null; //no patients found
            }


        }

        //check if a user is a patient, given their pair id
        [HttpGet("isPatient/{pairId}")]
        public Boolean isPatient(int pairId)
        {
            //check if pair id appears in link table
            //var link = db.Link.Where(l => l.PairID.Equals(pairId)).FirstOrDefault();

            var link = (from l in db.Link
                        where l.PairID.Equals(pairId)
                        select l).FirstOrDefault();

            if (link != null)
                return true;
            else
                return false;
        }

        //function to find pairs that have bookings with a psychologist, but no link
        [HttpGet("findPotentialPatients/{psychId}")]
        public List<Patient> findPotentialPatients(int psychId)
        {
            List<Patient> potentialPatients = new List<Patient>();

            var bookingsRecruits = getPossiblePatientsFromBookings(psychId);

            var linksRecruits = getPossiblePatientsFromLinks(psychId);

            if (bookingsRecruits != null && linksRecruits != null)
            {
                potentialPatients = bookingsRecruits.Concat(linksRecruits).ToList();
            }
            else if (bookingsRecruits == null)
            {
                potentialPatients = linksRecruits;
            }
            else if (linksRecruits == null)
            {
                potentialPatients = bookingsRecruits;
            }

            return potentialPatients;
        }

        //utility method: find potential patients using inactive links (status = "N")
        private List<Patient> getPossiblePatientsFromLinks(int psychId)
        {
            List<Patient> potentialPatients = new List<Patient>();

            var linkPatients = (from l in db.Link
                                where l.PsychID.Equals(psychId) && l.Status.Equals("N")
                                select l.PairID);

            if (linkPatients.Any())
            {
                foreach (int pI in linkPatients)
                {
                    var tempPatient = createPatient(pI); //create patient using pairId

                    if (tempPatient != null)
                        potentialPatients.Add(tempPatient); //add patient to list
                }
            }

            return potentialPatients;
        }

        //utility method: find potential patients using bookings table
        private List<Patient> getPossiblePatientsFromBookings(int psychId)
        {
            List<Patient> potentialPatients = new List<Patient>();
            BookingsController bkgController = new BookingsController(db);

            //find all psych calendars
            List<PsychCalendar> psychCalendars = bkgController.getPsychologistCalendars(psychId);

            if (psychCalendars != null)
            {

                foreach (PsychCalendar c in psychCalendars)
                {
                    //for each calendar, extract pair ids linked to the calendar
                    var calendarPairs = (from b in db.Bookings
                                         where b.CalendarID.Equals(c.PsychCalendarID)
                                         select b.PairID);
                    if (calendarPairs.Any())
                    {
                        foreach (int p in calendarPairs)
                        {
                            //check if pair id is in the link table, if not, add it to potential patients list
                            var isLinked = isPatient(p);

                            if (isLinked == false)
                            {
                                var tempPatient = createPatient(p); //create a patient using the pairId

                                if (tempPatient != null)
                                {
                                    Patient result = potentialPatients.Find(
                                    delegate (Patient pt)
                                    {
                                        return pt.patientInfo.UserID == tempPatient.patientInfo.UserID;
                                    }
                                    );

                                    if (result == null)
                                    {
                                        potentialPatients.Add(tempPatient); //add patient to potential patients list
                                    }
                                }

                            }
                        }
                    }
                    //else
                    //{
                    //    return null; //no pairs linked to any of the calendars
                    //}

                }
            }
            else
            {
                //no calendars linked to the psychologist
                return null;
            }

            return potentialPatients;
        }

        //utility method to create a patient, given a pairId
        private Patient createPatient(int pairId)
        {
            Patient tempPatient = new Patient();

            UserController userController = new UserController(db);
            BookingsController bkgController = new BookingsController(db);

            //get pair child
            var child = userController.GetPairChild(pairId);

            if (child != null)
            {
                tempPatient.patientInfo = child;

                //get pair guardian
                var parent = userController.GetGuardian(child.UserID);
                tempPatient.guardian = parent;

                //get last appointment date
                var lastAppointment = bkgController.GetLastAppointmentDate(child.UserID);
                tempPatient.lastAppointment = lastAppointment;

                //get next appointment date
                var nextBooking = bkgController.GetNextBooking(child.UserID);
                tempPatient.nextAppointment = nextBooking.Date;

                //get focus points
                HubController hubController = new HubController(db);
                List<RHubProblems> points = hubController.fetchChildFocusPoints(child.UserID);

                if (points != null)
                {
                    List<String> pointNames = new List<String>();
                    foreach (RHubProblems point in points)
                    {
                        pointNames.Add(point.Problem);
                    }

                    tempPatient.focusPoints = pointNames;
                }

            }
            else
            {
                //could not find child
                return new Patient();
            }

            return tempPatient;

        }

        //function to add a patient (create link record), given a pair
        [HttpPost("addPatient")]
        public Link addPatient(AddPatientInfo info)
        {
            //get pair id from pair table
            var pairID = (from p in db.Pair
                          where p.ChildID.Equals(info.childID) && p.ParentID.Equals(info.guardianID)
                          select p.PairID).FirstOrDefault();

            if (pairID != 0)
            {
                Link newLink = null;
                //check if link already exists but status is just "N"
                newLink = (from l in db.Link
                           where l.PairID.Equals(pairID) && l.PsychID.Equals(info.psychID)
                           select l).FirstOrDefault();

                if (newLink != null)
                {
                    newLink.Status = "Y";
                }
                else
                {
                    newLink = new Link
                    {
                        PairID = pairID,
                        PsychID = info.psychID,
                        Status = "Y",
                        Rating = 5,
                        DateLinked = DateTime.Now
                    };


                    db.Link.Add(newLink);
                }

                //save changes
                try
                {
                    db.SaveChanges();
                    return new Link
                    {
                        LinkID = newLink.LinkID,
                        PairID = newLink.PairID,
                        PsychID = newLink.PsychID,
                        Status = newLink.Status,
                        Rating = newLink.Rating
                    };
                }
                catch (Exception ex)
                {
                    ex.GetBaseException();
                    return new Link { LinkID = -1 }; //falied to create new link
                }
            }
            else
            {
                return new Link { LinkID = 0 }; //could not find pair id
            }
        }

        //Function to remove a patient (set link staus = "N")
        [HttpPost("removePatient")]
        public Link removePatient(AddPatientInfo info)
        {
            //get pair id from pair table
            var pairID = (from p in db.Pair
                          where p.ChildID.Equals(info.childID) && p.ParentID.Equals(info.guardianID)
                          select p.PairID).FirstOrDefault();

            if (pairID != 0)
            {
                //check if link already exists but status is just "N"
                var newLink = (from l in db.Link
                               where l.PairID.Equals(pairID) && l.PsychID.Equals(info.psychID)
                               select l).FirstOrDefault();

                if (newLink != null)
                {
                    newLink.Status = "N";

                    //save changes
                    try
                    {
                        db.SaveChanges();
                        return new Link
                        {
                            LinkID = newLink.LinkID,
                            PairID = newLink.PairID,
                            PsychID = newLink.PsychID,
                            Status = newLink.Status,
                            Rating = newLink.Rating
                        };
                    }
                    catch (Exception ex)
                    {
                        ex.GetBaseException();
                        return new Link { LinkID = -1 }; //falied to change link status
                    }
                }
                else
                {
                    return new Link { LinkID = -2 }; //pair found is not a patient of the psychologist
                }

            }
            else
            {
                //pairID not found
                return new Link { LinkID = 0 };
            }
        }
    }
}