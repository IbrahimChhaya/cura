using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using MHC_API.Data;
using MHC_API.Model;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace MHC_API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class BookingsController : ControllerBase
    {
        private MHCDatabaseDBContext db;

        public BookingsController(MHCDatabaseDBContext context)
        {
            db = context;
        }

        //get appointment by appointmentID
        [HttpGet("getAppointmentByID/{bookingID}")]
        public Bookings getAppointmentByID(int bookingID)
        {
            var booking = (from b in db.Bookings
                           where b.BookingID.Equals(bookingID)
                           select b).FirstOrDefault();
            
            if(booking != null)
            {
                return booking;
            }
            else
            {
                return null;
            }
        }

        //get child bookings Function
        [HttpGet("GetUserBookings/{userId}")]
        public List<Bookings> GetUserBookings(int userId)
        {
            List<Bookings> userBookings = new List<Bookings>();

            var userType = (from u in db.User
                        where u.UserID.Equals(userId)
                        select u.UserType).FirstOrDefault();

            int pairId = 0;

            if(userType != null)
            {
                if(userType.Equals("Child"))
                {
                    pairId = (from u in db.Pair
                              where u.ChildID.Equals(userId)
                              select u.PairID).FirstOrDefault();
                }
                else if(userType.Equals("Parent"))
                {
                    pairId = (from u in db.Pair
                              where u.ParentID.Equals(userId)
                              select u.PairID).FirstOrDefault();
                }
            }

            
            if (pairId != 0)
            {
                var bookings = (from bk in db.Bookings
                                    where bk.PairID == pairId
                                    select bk);

                if (bookings.Any())
                {
                    foreach (Bookings bkg in bookings)
                    {
                        var tempBkg = new Bookings
                        {
                            BookingID = bkg.BookingID,
                            Date = bkg.Date,
                            Time = bkg.Time,
                            Cancelled = bkg.Cancelled,
                            Type = bkg.Type,
                            CalendarID = bkg.CalendarID,
                            PairID = bkg.PairID
                        };
                        userBookings.Add(tempBkg);


                    }
                    userBookings.Sort((x, y) => DateTime.Compare(x.Date + x.Time, y.Date + y.Time));
                    return userBookings;
                }
                else
                {
                    //paired bu has no bookings
                    return null;
                }

                
            }
            else
            {
                return null;
            }

        }

        //Function for getting a user's bookings with a specific psychologist
        [HttpPost("getUserBookingsWithPsych")]
        public List<Bookings> getUserBookingsWithPsych(PsychPatientPair psyPat)
        {
            //get all the psychologist's bookings
            List<Bookings> allPsychBookings = getPsychBookings(psyPat.PsychID);

            UserController userController = new UserController(db);

            //get pair id from the user id passed
            int pairId = userController.getPairId(psyPat.ChildID);

            if(pairId != 0)
            {
                if (allPsychBookings != null)
                {
                    //find all the bookings with the same pair id as the user passed in the body
                    List<Bookings> userPsychBookings = allPsychBookings.FindAll(
                        delegate (Bookings bkg)
                        {
                            return bkg.PairID == pairId;
    
                        });

                    if (userPsychBookings.Any())
                    {
                        //sort the bookings by date (descending order)
                        userPsychBookings.Sort((x, y) => DateTime.Compare(y.Date + y.Time, x.Date + x.Time));
                        return userPsychBookings;
                    }
                    else
                        return null;
                }
                else
                {
                    return null;    //no bookings - this will return a 204 response 
                }
            }
            else
            {
                //the user is not paired, so they have no bookings
                return null;
            }

            
        }

        //method for getting booking in format needed for mobile app
        [HttpGet("GetUserBookingsForApp/{userId}")]
        public List<Appointment> GetUserBookingsForApp(int userId)
        {
            List<Appointment> appointments = new List<Appointment>();

            List<Bookings> userBookings = new List<Bookings>();

            var userType = (from u in db.User
                            where u.UserID.Equals(userId)
                            select u.UserType).FirstOrDefault();

            List<int> pairId = new List<int>();

            if (userType != null)
            {
                if (userType.Equals("Child"))
                {
                    var pairIds = (from u in db.Pair
                              where u.ChildID.Equals(userId)
                              select u.PairID);

                    foreach(int id in pairIds)
                    {
                        pairId.Add(id);
                    }
                }
                else if (userType.Equals("Parent"))
                {
                    var pairIds = (from u in db.Pair
                              where u.ParentID.Equals(userId)
                              select u.PairID);

                    foreach (int id in pairIds)
                    {
                        pairId.Add(id);
                    }

                }
            }

            if (pairId != null)
            {
                foreach (int id in pairId)
                {
                    dynamic bookings = (from bk in db.Bookings
                                        where bk.PairID == id
                                        select bk);

                    foreach (Bookings bkg in bookings)
                    {
                        Appointment tempAppointment = new Appointment();

                        var tempBkg = new Bookings
                        {
                            BookingID = bkg.BookingID,
                            Date = bkg.Date,
                            Time = bkg.Time,
                            Cancelled = bkg.Cancelled,
                            Type = bkg.Type,
                            CalendarID = bkg.CalendarID,
                            PairID = bkg.PairID,
                            ProblemID = bkg.ProblemID,
                            NoteID = bkg.NoteID,
                            MeetingID = bkg.MeetingID,
                            MeetingPassword = bkg.MeetingPassword
                        };
                        tempAppointment.bookingInfo = tempBkg;

                        //get booking child
                        UserController usrController = new UserController(db);

                        User child = usrController.GetPairChild(bkg.PairID);
                        tempAppointment.patientName = child.Name;

                        //add temp appointment to list
                        appointments.Add(tempAppointment);
                    }
                }
                return appointments;
            }
            else
            {
                return null; //no pair ids therefore no bookings
            }
        }

        //method to get all calendar events linked to a psychologist
        [HttpGet("GetPsychCalendar/{psychId}")]
        public List<CalendarEvent> GetPsychCalendar(int psychId)
        {
            List<Bookings> psychBookings = getPsychBookings(psychId);
            List<CalendarEvent> events = new List<CalendarEvent>();

            if(psychBookings != null)
            {
                foreach (Bookings b in psychBookings)
                {
                    //get child from pairId
                    var childId = (from p in db.Pair
                                   where p.PairID.Equals(b.PairID)
                                   select p.ChildID).FirstOrDefault();

                    //get child's name
                    var childName = (from u in db.User
                                     where u.UserID.Equals(childId)
                                     select u.Name).FirstOrDefault();

                    //create and add event to list
                    var tempEvent = new CalendarEvent
                    {
                        PatientName = childName,
                        DayTime = b.Date + b.Time,
                        pairId = b.PairID,
                        type = b.Type,
                        Cancelled = b.Cancelled
                    };

                    events.Add(tempEvent);
                }

                return events;
            }
            else
            {
                return null;
            }
            
        }

        //Get a child's next booking
        [HttpGet("GetNextBooking/{childId}")]
        public Bookings GetNextBooking(int childId)
        {
            //get all bookings linked to a psychologist
            List<Bookings> bookings = getChildBookings(childId);

            //find the booking with the nearest date and time
            return getEarliestBooking(bookings);
        }

        //Utility method: get calendars linked to a psychologist
        private List<int> getPsychCalendars(int psychId)
        {
            var psychCalendars = (from c in db.PsychCalendar
                                  where c.PsychID == psychId
                                  select c.PsychCalendarID);
            List<int> calendars = new List<int>();


            if (psychCalendars.Any())
            {
                foreach (int c in psychCalendars)
                {
                    calendars.Add(c);
                }

                return calendars;
            }
            else
            { 
                return null; 
            }
            

        }

        private List<Bookings> getChildBookings(int childId)
        {
            //get child's pairId
            var cPairId = (from p in db.Pair
                           where p.ChildID.Equals(childId)
                           select p.PairID).FirstOrDefault();

            if(cPairId != 0)
            {
                var cBookings = (from b in db.Bookings
                                 where b.PairID.Equals(cPairId)
                                 select b);

                List<Bookings> bookings = new List<Bookings>();

                if (cBookings.Any())
                {
                    foreach (Bookings bkg in cBookings)
                    {
                        bookings.Add(bkg);
                    }

                    return bookings;
                }
                else
                {
                    return null;
                }
            }
            else
            {
                return null;
            }
           
            
        }

        //get all bookings linked to a psychologist
        [HttpGet("getPsychBookings/{psychId}")]
        public List<Bookings> getPsychBookings(int psychId)
        {
            //get the calendarIDs linked to the given psychologist
            dynamic psychCalendars = getPsychCalendars(psychId);

            List<Bookings> bookings = new List<Bookings>();

            if(psychCalendars != null)
            {
                //get all bookings linked to the psychologist
                foreach (int c in psychCalendars)
                {
                    dynamic cBookings = getCalendarBookings(c);

                    if(cBookings != null)
                    {
                        //add each of the given bookings to bookings list
                        foreach (Bookings bkg in cBookings)
                        {
                            bookings.Add(bkg);
                        }
                    }
                    else
                    {
                        continue;
                    }

                    
                }

                return bookings;
            }
            else
            {
                return null;
            }
            
        }

        //Function to get a list list of 4 upcoming bookings for a psychologist
        [HttpGet("GetUpcomingBookings/{psychId}")]
        public List<Bookings> GetUpcomingBookings(int psychId)
        {
            List<Bookings> bookings = getPsychBookings(psychId);

            if (bookings != null)
            {
                bookings.Sort((x, y) => DateTime.Compare(x.Date + x.Time, y.Date + y.Time));

                List<Bookings> upcomingBookings = bookings.FindAll(
                    delegate(Bookings bkg)
                    {
                        DateTime currentDate = DateTime.Now;
                        DateTime appDate = bkg.Date + bkg.Time;
                        return appDate >= currentDate && bkg.Cancelled == "Future";
                    }
                    );

                if (upcomingBookings.Any())
                    return upcomingBookings.ToList();
                else 
                    return null;    //no upcoming bookings that are not cancelled
            }
            else
            {
                return null; //no psych bookings
            }
        }

        //function to get a list of upcoming bookings for a child
        [HttpGet("getAllUpcomingBookings/{userId}")]
        public List<Appointment> getAllUpcomingBookings(int userId)
        {
            //get a list of all bookings linked to the child
            var cBookings = GetUserBookingsForApp(userId);

            if (cBookings != null)
            {
                //sort bookings according to date
                cBookings.Sort((x, y) => DateTime.Compare(x.bookingInfo.Date + x.bookingInfo.Time, y.bookingInfo.Date + y.bookingInfo.Time));

                List<Appointment> nextAppointments = cBookings.FindAll(
                    delegate (Appointment app)
                    {
                        DateTime currentDate = DateTime.Now;
                        DateTime appDate = app.bookingInfo.Date + app.bookingInfo.Time;
                        return appDate >= currentDate && app.bookingInfo.Cancelled == "Future";
                    }
                    );

                if (nextAppointments.Any())
                {
                    return nextAppointments;
                }
                else
                {
                    return null;    //no past bookings
                }

            }
            else
                return null;    //user has no bookings

        }

        //Function to get a list of previous bookings for a child
        [HttpGet("getAllPreviousBookings/{userID}")]
        public List<Appointment> getAllPreviousBookings(int userID)
        {
            List<Bookings> previousBookings = new List<Bookings>();

            //get all bookings linked to child
            var bookings = GetUserBookingsForApp(userID);

            if(bookings != null)
            {
                //sort bookings according to date
                bookings.Sort((x, y) => DateTime.Compare(x.bookingInfo.Date + x.bookingInfo.Time, y.bookingInfo.Date + y.bookingInfo.Time));

                List<Appointment> prevAppointments = bookings.FindAll(
                    delegate(Appointment app)
                    {
                        DateTime currentDate = DateTime.Now;
                        DateTime appDate = app.bookingInfo.Date + app.bookingInfo.Time;
                        return currentDate > appDate && app.bookingInfo.Cancelled != "Cancelled";
                    }
                    );

                if (prevAppointments.Any())
                {
                    return prevAppointments;
                }
                else
                {
                    return null;    //no past bookings
                }

            }
            else
                return null;    //user has no bookings


        }

        //Function to get all cancelled bookings given a user id
        [HttpGet("getAllCancelledBookings/{userId}")]
        public List<Appointment> getAllCancelledBookings(int userId)
        {
            List<Appointment> cancelledBookings = new List<Appointment>();

            //get all bookings linked to the user
            var bookings = GetUserBookingsForApp(userId);

            if(bookings != null)
            {
                //sort the bookings according to date - descending order
                bookings.Sort((x, y) => DateTime.Compare(y.bookingInfo.Date + y.bookingInfo.Time, x.bookingInfo.Date + x.bookingInfo.Time));

                //Look for all cancelled bookings
                List<Appointment> cancelledBkgs = bookings.FindAll(
                    delegate (Appointment bkg)
                    {
                        return bkg.bookingInfo.Cancelled == "Cancelled";
                    }
                    );

                if (cancelledBkgs.Any())
                    return cancelledBkgs;
                else
                    return null;    //no bookings were cancelled
            }
            else
            {
                //user has no bookings
                return null;
            }
        }

        //function to get number of past bookings made (child/guardian)
        [HttpGet("getNumberOfPastBookings/{userID}")]
        public int getNumberOfPastBookings(int userId)
        {
            //get all previous bookings
            var prevBookings = getAllPreviousBookings(userId);

            if (prevBookings != null)
                return prevBookings.Count;
            else
                return 0;
        }

        //function to get number of upcoming bookings made (child/guardian)
        [HttpGet("getNumberOfUpcomingBookings/{userID}")]
        public int getNumberOfUpcomingBookings(int userId)
        {
            //get all previous bookings
            var upcomingBookings = getAllUpcomingBookings(userId);

            if (upcomingBookings != null)
                return upcomingBookings.Count;
            else
                return 0;
        }

        //function that given a list of bookings, returns the list of patients associated with those bookings
        [HttpPost("getBookingsPatients")]
        public List<User> getBookingsPatients(List<Bookings> bookings)
        {
            UserController userController = new UserController(db);

            List<User> patients = new List<User>();

            foreach(Bookings bkg in bookings)
            {
                User tempUser = userController.GetPairChild(bkg.PairID);

                if (tempUser.UserID != 0)
                    patients.Add(tempUser);
            }

            return patients;
            
        }

        //utility method: given a list of bookings, find the index of the immediate upcoming meeting
        private int getNextBookingIndex(List<Bookings> bookings)
        {
            var currentDateTime = DateTime.Now;

            //Loop through bookings to find next booking
            Bookings nextBooking = new Bookings();
            int nextBookingIndex = -1;
            double minTimeDiff = 0;

            if(bookings != null)
            {
                //make sure that the smallest time difference is positive
                for (int i = 0; i < bookings.Count; ++i)
                {
                    DateTime firstBookingDate = bookings[i].Date + bookings[0].Time;
                    minTimeDiff = firstBookingDate.Subtract(currentDateTime).TotalDays;

                    if (minTimeDiff > 0)
                    {
                        nextBookingIndex = i;
                        break;
                    }
                }
            }
            

            return nextBookingIndex;
        }


        //utility method: given a list of bookings, return the earliest upcoming booking
        private Bookings getEarliestBooking(List<Bookings> bookings)
        {
            var currentDateTime = DateTime.Now;

            //Loop through bookings to find next booking
            Bookings nextBooking = new Bookings();
            int nextBookingIndex = getNextBookingIndex(bookings);

            if(bookings != null)
            {
                if (nextBookingIndex < 0)
                    return bookings[bookings.Count - 1];

                DateTime cNextBookingDate = bookings[nextBookingIndex].Date + bookings[nextBookingIndex].Time;
                double minTimeDiff = cNextBookingDate.Subtract(currentDateTime).TotalDays;

                nextBooking = bookings[nextBookingIndex];

                for (int i = nextBookingIndex; i < bookings.Count; ++i)
                {
                    DateTime appointDateTime = bookings[i].Date + bookings[i].Time;

                    var dateDiff = appointDateTime.Subtract(currentDateTime).TotalDays;
                    if (dateDiff <= minTimeDiff && dateDiff >= 0)
                    {
                        minTimeDiff = dateDiff;
                        nextBooking = bookings[i];
                    }
                }
            }
           

            return nextBooking;
        }

        //utility method for getting all bookings associated with a specfic calendar
        private List<Bookings> getCalendarBookings(int calendarId)
        {
            List<Bookings> bookings = new List<Bookings>();
            var temp = (from b in db.Bookings
                        where b.CalendarID.Equals(calendarId)
                        select b);

            if (temp.Any())
            {
                foreach (Bookings b in temp)
                {
                    bookings.Add(b);
                }

                return bookings;
            }
            else
            {
                return null;
            }
            
        }

        [HttpGet("GetLastAppointmentDate/{childId}")]
        public DateTime GetLastAppointmentDate(int childId)
        {
            var pairId = (from p in db.Pair
                          where p.ChildID.Equals(childId)
                          select p.PairID).FirstOrDefault();

            List<Bookings> userBookings = new List<Bookings>();
            Bookings prevBooking = new Bookings();
            DateTime currentDate = DateTime.Now;

            if(pairId != 0)
            {
                var bookings = (from b in db.Bookings
                                where b.PairID.Equals(pairId)
                                select b);


                if (bookings.Any())
                {
                    foreach (Bookings bkg in bookings)
                    {
                        userBookings.Add(bkg);
                    }
                }
                
            }
            
            int prevBookingIndex = getPrevBookingIndex(userBookings);

            if(prevBookingIndex != -1)
            {
                prevBooking = userBookings[prevBookingIndex];
                DateTime prevBookDate = userBookings[prevBookingIndex].Date + userBookings[prevBookingIndex].Time;
                double maxDateDiff = currentDate.Subtract(prevBookDate).TotalDays;

                for (int i = prevBookingIndex; i < userBookings.Count; i++)
                {
                    DateTime tempDate = userBookings[i].Date + userBookings[i].Time;
                    double dateDiff = currentDate.Subtract(tempDate).TotalDays;

                    if (dateDiff < maxDateDiff && dateDiff > 0)
                    {
                        maxDateDiff = dateDiff;
                        prevBookDate = tempDate;
                    }
                }

                return prevBookDate;
            }
            else
            {
                return new DateTime(); //no previous booking found 
            }
            
        }

        private int getPrevBookingIndex(List<Bookings> bookings)
        {
            var currentTime = DateTime.Now;
            var prevBookingIndex = -1;

            if (bookings.Any())
            {
                DateTime firstDate = bookings[0].Date + bookings[0].Time;
                double maxTimeDiff = currentTime.Subtract(firstDate).TotalDays;

                for (int i = 0; i < bookings.Count; ++i)
                {
                    DateTime tempDate = bookings[i].Date + bookings[i].Time;
                    double dateDiff = currentTime.Subtract(tempDate).TotalDays;

                    if (dateDiff < maxTimeDiff && dateDiff > 0)
                    {
                        prevBookingIndex = i;
                        return prevBookingIndex;
                    }
                }

               
            }

            return prevBookingIndex;
        }

        //method to add a booking 
            /* this is a very basic method for d3 - needs improvement please*/
            
        [HttpPost("AddBooking/")]
        public int AddBooking(BookingInfo bookingInfo)
        {
            int pairId = (from p in db.Pair
                          where p.ChildID.Equals(bookingInfo.userId)
                          select p.PairID).FirstOrDefault();

            if(pairId != 0)
            {
                //check if the user already has bookings
                Bookings bookings = (from b in db.Bookings
                                where b.PairID.Equals(pairId)
                                select b).FirstOrDefault();

                //checking if the user is linked
                UserController us = new UserController(db);
                UserPsych userPsych = new UserPsych
                {
                    UserID = bookingInfo.userId,
                    PsychID = bookingInfo.PsychID
                };
                var linked = us.isLinked(userPsych);

                string bType = null;

                if (linked==0)
                {
                    bType = "Single";    //user does not have bookings

                    Bookings newBooking = new Bookings
                    {
                        Date = bookingInfo.date,
                        Time = bookingInfo.date.TimeOfDay,
                        Type = bType,
                        CalendarID = bookingInfo.calendarID,
                        Cancelled = "Future",
                        PairID = pairId

                    };

                    db.Bookings.Add(newBooking);
                }
                    
                else if (linked > 0)
                {
                    bType = "Repeat";   //user has bookings

                    //get the four dates needed to make the bookings for 4 weeks
                    List<DateTime> bookingDates = getBookingDatesForNextFourWeeks(bookingInfo.date);

                    if(bookingDates != null)
                    {
                        //add the four bookings
                        foreach(DateTime date in bookingDates)
                        {
                            Bookings newBooking = new Bookings
                            {
                                Date = date,
                                Time = bookingInfo.date.TimeOfDay,
                                Type = bType,
                                CalendarID = bookingInfo.calendarID,
                                Cancelled = "Future",
                                PairID = pairId

                            };

                            db.Bookings.Add(newBooking);
                        }
                    }
                }

                try
                {
                    db.SaveChanges();
                    return 0; //great success
                }
                catch (Exception ex)
                {
                    ex.GetBaseException();
                    return -1;  //failed to save booking/s
                }

            }
            else
            {
                return -2;    //userId does not have a pair
            }

            
        }

        //Function used to cancel a booking
        [HttpPost("cancelBooking")]
        public int cancelBooking(Bookings bkg)
        {
            //find the booking by the id
            var booking = db.Bookings.Where(b => b.BookingID.Equals(bkg.BookingID)).FirstOrDefault();

            if(booking != null)
            {
                booking.Cancelled = "Cancelled";

                try
                {
                    db.SaveChanges();
                    return 0;   //successfully changed booking
                }
                catch(Exception ex)
                {
                    ex.GetBaseException();
                    return -1;      //Couldn't save change
                }
            }
            else
            {
                //booking id was invalid
                return -2;
            }
        }

        [HttpGet("getPsychologistCalendars/{psychId}")]
        public List<PsychCalendar> getPsychologistCalendars(int psychId)
        {
            List<PsychCalendar> pCalendars = new List<PsychCalendar>();

            var calendars = db.PsychCalendar.Where(x => x.PsychID.Equals(psychId));

            if (calendars.Any())
            {
                foreach (PsychCalendar c in calendars)
                {
                    pCalendars.Add(c);
                }

                return pCalendars;
            }
            else
            {
                return null;
            }
            
        }

        //Utility Function: get a list of dates for specific day of week for the month (given date)
        private List<DateTime> getBookingDatesForNextFourWeeks(DateTime startDate)
        {
            List<DateTime> nextFourDates = new List<DateTime>();
            nextFourDates.Add(startDate); //add the first date to the list

            DateTime firstDate = startDate;

            for(int i = 0; i < 3; i++)
            {
                DateTime nextDate = firstDate.AddDays(7);   //add seven days to current date to get next week's date
                nextFourDates.Add(nextDate);

                firstDate = nextDate;
            }

            return nextFourDates;
        }

        //Function to get booking patient and psychologist - given booking ID 
        [HttpGet("getBookingPatientAndPsych/{bookingID}")]
        public BookingParties getBookingPatientAndPsych(int bookingID)
        {
            BookingParties partiesInfo = new BookingParties();

            //check if valid booking id
            var booking = db.Bookings.Where(b => b.BookingID.Equals(bookingID)).FirstOrDefault();

            if(booking != null)
            {
                //get child from pair id associated with booking
                UserController userController = new UserController(db);
                User child = userController.GetPairChild(booking.PairID);

                if (child != null)
                {
                    partiesInfo.patient = new User
                    {
                        UserID = child.UserID,
                        Name = child.Name,
                        Email = child.Email,
                        DOB = child.DOB,
                        UserType = child.UserType,
                        Grade = child.Grade,
                        ProfilePicture = child.ProfilePicture,
                        DateRegistered = child.DateRegistered,
                        Status = child.Status
                    };
                }

                //get psychologist from calendar id
                PsychologistInfo psychInfo = getPsychByCalendarID(booking.CalendarID);

                if (psychInfo.UserID > 0)
                {
                    partiesInfo.psychologist = psychInfo;
                }

                return partiesInfo;

            }
            else
            {
                //invalid booking id
                User invalidBkgUser = new User
                {
                    UserID = -2
                };
                return new BookingParties { patient = invalidBkgUser };
            }
        }

        //Utility function: get psychologist given a calendar id
        [HttpGet("getPsychByCalendarID/{calendarID}")]
        public PsychologistInfo getPsychByCalendarID(int calendarID)
        {
            //check if calendar exists
            var calendar = db.PsychCalendar.Where(c => c.PsychCalendarID.Equals(calendarID)).FirstOrDefault();

            if(calendar != null)
            {
                UserController userController = new UserController(db);
                PsychologistInfo psychInfo = userController.fetchPsychInfo(calendar.PsychID);

                return psychInfo;   //psychInfo.UserID == 0 if psych not found
            }
            else
            {
                return new PsychologistInfo { UserID = -1 }; //calendar not found
            }
        }


        //Function to get a list of previous bookings for a psych
        [HttpGet("getAllPsychPreviousBookings/{psychID}")]
        public List<Bookings> getAllPsychPreviousBookings(int psychID)
        {
            //get all bookings linked to psych
            var bookings = getPsychBookings(psychID);

            if (bookings != null)
            {
                //sort bookings according to date
                bookings.Sort((x, y) => DateTime.Compare(x.Date + x.Time, y.Date + y.Time));

                List<Bookings> prevAppointments = bookings.FindAll(
                    delegate (Bookings app)
                    {
                        DateTime currentDate = DateTime.Now;
                        DateTime appDate = app.Date + app.Time;
                        return currentDate > appDate && app.Cancelled != "Cancelled";
                    }
                );

                if (prevAppointments.Any())
                {
                    return prevAppointments;
                }
                else
                {
                    return null;    //no past bookings
                }
            }
            else
            {
                return null;    //user has no bookings
            }
        }

        //Function to update booking with problemID
        [HttpPost("addFocusPointToBooking")]
        public Bookings addFocusPointToBooking(BookingProblem booking)
        {
            Bookings book = (from b in db.Bookings
                             where b.BookingID.Equals(booking.bookingID)
                             select b).FirstOrDefault();
            if(book != null)
            {
                book.ProblemID = booking.problemID;

                try 
                {
                    db.SaveChanges();
                    return new Bookings
                    {
                        BookingID = book.BookingID,
                        Date = book.Date,
                        Time = book.Time,
                        Cancelled = book.Cancelled,
                        Type = book.Type,
                        CalendarID = book.CalendarID,
                        PairID = book.PairID,
                        NoteID = book.NoteID,
                        ProblemID = book.ProblemID
                    };
                }
                catch (Exception ex)
                {
                    ex.GetBaseException();
                    return null;
                }
            }
            else
            {
                return null;
            }
        }

        //function that takes any userId and returns that type of user's bookings
        [HttpGet("getUserBookingsByUserID/{userId}")]
        public List<Bookings> getUserBookingsByUserID(int userId)
        {
            //check what type of user
            var user = db.User.Where(u => u.UserID.Equals(userId)).FirstOrDefault();
            List<Bookings> bkgs = null;

            if (user != null)
            {
                if (user.UserType.Equals("Psychologist"))
                {
                    //get the psych's bookings
                    bkgs = getPsychBookings(user.UserID);
                }
                else if(user.UserType.Equals("Child") || user.UserType.Equals("Parent"))
                {
                    //get user bookings
                    bkgs = GetUserBookings(user.UserID);
                }

                return bkgs;
            }
            else
            {
                return null;      //invalid user ID
            }
        }

        /// <summary>
        /// Function to determine whether a user already has recurring bookings with a psychologist
        /// </summary>
        /// <param name="psyPat">
        ///     ChildID, PsychID
        /// </param>
        /// <returns>
        ///     true - if child already has recurring bookings with the psych
        ///     false - if no recurring bookings with the given psych
        /// </returns>
        [HttpPost("alreadyHasRecurringBookings")]
        public Boolean alreadyHasRecurringBookings(PsychPatientPair psyPat)
        {
            var allBookings = getUserBookingsWithPsych(psyPat);

            if(allBookings != null)
            {
                var firstRecurringBooking = allBookings.Exists(b => b.Type.Equals("Repeat"));

                return firstRecurringBooking;
            }

            return false;   //no bookings for the pair
        }

        [HttpGet("getVirtualSession/{userId}")]
        public Bookings getVirtualSession(int userId)
        {
            List<Appointment> usrBookings = getAllUpcomingBookings(userId);
            if(usrBookings != null)
            {
                DateTime currentDate = DateTime.Now;
                DateTime meetingTime = currentDate;


                Appointment found = usrBookings.Find(x => x.bookingInfo.MeetingID != null && x.bookingInfo.Date == currentDate.Date
                && (x.bookingInfo.Time - meetingTime.TimeOfDay).Minutes <= 65);

                if(found != null)
                {
                    return found.bookingInfo;
                }else
                {
                    return new Bookings {BookingID = 0};
                }
            }
            else
            {
                return null;
            }
        }

        [HttpGet("getMeetingCodes/{bookingID}")]
        public Bookings getMeetingCodes(int bookingID)
        {
            var booking = db.Bookings.Where(b => b.BookingID.Equals(bookingID)).FirstOrDefault();
            if (booking != null)
            {
                return booking;
            }
            return new Bookings { BookingID = -1 };
        }

        //save meetingID and pass
        [HttpPost("saveVirtualSession")]
        public int saveVirtualSession(Bookings videoBooking)
        {
            var booking = db.Bookings.Where(b => b.BookingID.Equals(videoBooking.BookingID)).FirstOrDefault();
            if (booking != null)
            {
                booking.MeetingID = videoBooking.MeetingID;
                booking.MeetingPassword = videoBooking.MeetingPassword;

                try
                {
                    db.SaveChanges();
                    return 1; //great success
                }
                catch (Exception ex)
                {
                    ex.GetBaseException();
                    return -1;  //failed to update booking
                }
            }
            return 0;
        }

        [HttpPost("changeBookingStatus")]
        public int changeBookingStatus(Bookings updatedBooking)
        {
            var booking = db.Bookings.Where(b => b.BookingID.Equals(updatedBooking.BookingID)).FirstOrDefault();
            if(booking != null)
            {
                booking.Cancelled = updatedBooking.Cancelled;
                try
                {
                    db.SaveChanges();
                    return 1; //great success
                }
                catch (Exception ex)
                {
                    ex.GetBaseException();
                    return -1;  //failed to update booking
                }
            }
            return 0;
        }

        [HttpGet("checkAnyBookings/{psychID}")]
        public bool checkAnyBookings(int psychID)
        {
            List<int> psychCalendars = getPsychCalendars(psychID);

            foreach (int pc in psychCalendars)
            {
                var booking = db.Bookings.Where(b => b.CalendarID.Equals(pc)).FirstOrDefault();
                if (booking != null)
                {
                    return true;
                }
            }
            return false;
        }
    }
}
