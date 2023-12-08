using MHC_API.Data;
using MHC_API.Model;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ReportsController : ControllerBase
    {
        private MHCDatabaseDBContext db;

        public ReportsController(MHCDatabaseDBContext db)
        {
            this.db = db;
        }

        //meetings attended per month (for each week of the month)
        [HttpGet("getAttendedMeetingsPerMonth/{userId}")]
        public List<DateNumPair> getAttendedMeetingsPerMonth(int userId)
        {
            List<DateNumPair> monthsAttendedBookings = new List<DateNumPair>();

            BookingsController bkgController = new BookingsController(db);
            //get week start dates for month
            List<DateTime> weekDates = getWeekStartDateOfMonth(DateTime.Now);
            List<Bookings> bkgs = bkgController.getUserBookingsByUserID(userId);


            if (bkgs != null)
            {
                bkgs = bkgs.Where(b => b.Cancelled == "Attended").ToList(); //find only missed bookings

                monthsAttendedBookings = getBookingDateNumPairs(weekDates, bkgs);
            }
            else
                return null; //no user bookings found

            return monthsAttendedBookings;
        }

        //meetings cancelled per month (for each week of the month)
        [HttpGet("getCancelledMeetingsPerMonth/{userId}")]
        public List<DateNumPair> getCancelledMeetingsPerMonth(int userId)
        {
            List<DateNumPair> monthsCancelledBookings = new List<DateNumPair>();

            BookingsController bkgController = new BookingsController(db);
            //get week start dates for month
            List<DateTime> weekDates = getWeekStartDateOfMonth(DateTime.Now);
            List<Bookings> bkgs = bkgController.getUserBookingsByUserID(userId);


            if (bkgs != null)
            {
                bkgs = bkgs.Where(b => b.Cancelled == "Cancelled").ToList(); //find only missed bookings

                monthsCancelledBookings = getBookingDateNumPairs(weekDates, bkgs);
            }
            else
                return null; //no user bookings found

            return monthsCancelledBookings;
        }

        //meetings missed per month (for each week of the month)
        [HttpGet("getMissedMeetingsPerMonth/{userId}")]
        public List<DateNumPair> getMissedMeetingsPerMonth(int userId)
        {
            List<DateNumPair> monthsMissedBookings = new List<DateNumPair>();

            BookingsController bkgController = new BookingsController(db);
            //get week start dates for month
            List<DateTime> weekDates = getWeekStartDateOfMonth(DateTime.Now);
            List<Bookings> bkgs = bkgController.getUserBookingsByUserID(userId);


            if (bkgs != null)
            {
                bkgs = bkgs.Where(b => b.Cancelled == "Missed").ToList(); //find only missed bookings

                monthsMissedBookings = getBookingDateNumPairs(weekDates, bkgs);
            }
            else
                return null; //no user bookings found

            return monthsMissedBookings;
        }

        //total number of bookings for the month (for each week of the month)
        [HttpGet("getNumberOfBookingForMonth/{userId}")]
        public List<DateNumPair> getNumberOfBookingForMonth(int userId)
        {
            List<DateNumPair> numBookingPerWeekForMonth = new List<DateNumPair>();

            BookingsController bkgController = new BookingsController(db);
            List<Bookings> bkgs = bkgController.getUserBookingsByUserID(userId);
            //get week start dates for month
            List<DateTime> weekDates = getWeekStartDateOfMonth(DateTime.Now);

            if (bkgs != null)
                numBookingPerWeekForMonth = getBookingDateNumPairs(weekDates, bkgs);
            else
                return null;    //no bookngs found for user

             return numBookingPerWeekForMonth;
        }

        //utility function: given list of week dates and bookings, return list of date num pairs
        private List<DateNumPair> getBookingDateNumPairs(List<DateTime> dates, List<Bookings> bookings)
        {
            List<DateNumPair> datesAndCounts = new List<DateNumPair>();

            foreach(DateTime d in dates)
            {
                int count = getNumBookingForWeek(d, bookings);

                DateNumPair temp = new DateNumPair
                {
                    WeekStartDate = d,
                    Count = count
                };

                datesAndCounts.Add(temp);
            }

            return datesAndCounts;
        }

        //utility function: get number of bookings for the week of a given date
        private int getNumBookingForWeek(DateTime date, List<Bookings> bookings)
        {
            List<DateTime> weekDates = getWeekDates(date);

            int bookingsCount = 0;

            if(weekDates != null)
            {
                foreach (DateTime d in weekDates)
                {
                    List<Bookings> tempBkgs = bookings.FindAll(
                        delegate(Bookings bkg)
                        {
                            return bkg.Date == d.Date; //get all bookings with the same date
                        }
                    );

                    if(tempBkgs.Any())
                        bookingsCount += tempBkgs.Count();
                }
            }

            return bookingsCount;
        }

        //common focus points assigned to patients (for beta it will use all patients not just those linked to the psychologist)
        [HttpGet("getCommonFocusPointsAssigned/{psychId}")]
        public List<CommonFocusPoint> getCommonFocusPointsAssigned(int psychId)
        {
            List<CommonFocusPoint> commonPoints = new List<CommonFocusPoint>();

            //get all psychologist's patients
            //PatientController pc = new PatientController(db);
            //var psychPatients = pc.getPsychPatients(psychId);

            //if(psychPatients != null)
            //{

            //}


            //get point sums grouped by point ID
            var pointSums = (from p in db.RHubUserBridge
                            group p by p.ProblemID
                            into pointSum
                            select new { ProblemID = pointSum.Key, PointTotal = pointSum.Count() }).ToList();

            if (pointSums.Any())
            {
                //sort list in order of ascending sum
                pointSums.Sort((x, y) => y.PointTotal.CompareTo(x.PointTotal));

                HubController hubController = new HubController(db);

                foreach(var point in pointSums)
                {
                    RHubProblems tempPoint = hubController.getFocusPointByID(point.ProblemID);

                    if(tempPoint != null)
                    {
                        CommonFocusPoint cfp = new CommonFocusPoint
                        {
                            PointID = point.ProblemID,
                            PointName = tempPoint.Problem,
                            PointTotal = point.PointTotal
                        };

                        commonPoints.Add(cfp);
                    }
                }

                return commonPoints;    //returns list of point in order of descending Point frequency
            }
            else
            {
                return null; //no points found in bridge table
            }
        }

        //get number of new patients linked for the month
        [HttpGet("getNumberOfNewPatientsThisMonth/{psychId}")]
        public int getNumberOfNewPatientsThisMonth(int psychId)
        {
            //get current month
            DateTime currentDate = DateTime.Now;

            return getGivenMonthsNewPatients(currentDate, psychId);
            
        }

        //percentage increase in patients
        [HttpGet("getPatientPercentIncrease/{psychId}")]
        public double getPatientPercentIncrease(int psychId)
        {
            //get current month
            DateTime currentDate = DateTime.Now;

            //get prev month's date
            int monthCurrent = currentDate.Month;
            DateTime lastMonthDate;
            if (monthCurrent > 1)
            {
                lastMonthDate = new DateTime(currentDate.Year, (monthCurrent - 1), currentDate.Day);
            }
            else
            {
                //current month is Jan, so prev month will be a different year
                int currentYear = currentDate.Year;
                lastMonthDate = new DateTime(currentYear - 1, 12, currentDate.Day);
            }

            //get this month's patients
            int currentMonthPatients = getGivenMonthsNewPatients(currentDate, psychId);

            //get last month's patients
            int prevMonthPatients = getGivenMonthsNewPatients(lastMonthDate, psychId);

            //percent increase = (current - past * 100)/past
            int increase = (currentMonthPatients - prevMonthPatients);///prevMonthPatients;
            double percentInc;

            if (prevMonthPatients > 0)
                percentInc = (increase * 100) / prevMonthPatients;
            else
                percentInc = 100;   //not sure how we want to handle this

            return percentInc;
        }

        //utility function: get a month's no. of new patients - given date and psychId
        private int getGivenMonthsNewPatients(DateTime date, int psychId)
        {
            int month = date.Month; //1 for Jan, etc
            DateTime oldDate = date.AddDays(-30);

            var psychLinks = (from l in db.Link
                              where l.PsychID.Equals(psychId) && l.DateLinked > oldDate && l.DateLinked < date //.Month.Equals(month)
                              select l);
            //where l.PsychID.Equals(psychId) && l.DateLinked.Value.Month.Equals(month)

            if (psychLinks != null)
            {
                return psychLinks.Count();
            }
            else
            {
                return 0; //no links found for this month
            }
        }

        //utility method: get dates for the given week
        private List<DateTime> getWeekDates(DateTime date)
        {
            List<DateTime> dates = new List<DateTime>();
            DateTime currentDate = date.Date;
            dates.Add(currentDate);
            DateTime startDate = currentDate;

            for (int i = 0; i < 6; i++)
            {
                DateTime nextDate = startDate.AddDays(1); //add 1 day to given date to get next date
                dates.Add(nextDate);

                startDate = nextDate;

            }

            dates.Sort((x, y) => x.CompareTo(y));

            return dates;
        }

        //utility method: get month dates, given a date
        private List<DateTime> getMonthDates()
        {
            List<DateTime> monthDates = new List<DateTime>();
            DateTime date = DateTime.Now.Date;

            int month = date.Month;
            //get first day of month
            DateTime startDate = new DateTime(date.Year, month, 1);
            //monthDates.Add(startDate);

            while (startDate.Month == month)
            {
                monthDates.Add(startDate);
                startDate = startDate.AddDays(1);
            }

            return monthDates;
        }

        //utility function: get dates for the beginning of each week in a month
        private List<DateTime> getWeekStartDateOfMonth(DateTime date)
        {
            DateTime first = new DateTime(date.Year, date.Month, 1);
            DateTime startDate = first;

            List<DateTime> weekStartDates = new List<DateTime>();

            while(startDate.Month == date.Month)
            {
                weekStartDates.Add(startDate);
                startDate = startDate.AddDays(7);   //add 7 days to current date
            }

            return weekStartDates;
            
        }


        //Function: get number of cancelled bookings for the month
        [HttpGet("getNumCancelledBookingsForMonth/{userId}")]
        public int getNumCancelledBookingsForMonth(int userId)
        {
            BookingsController bkgController = new BookingsController(db);
            List<Bookings> userBkgs = bkgController.getUserBookingsByUserID(userId);

            if (userBkgs != null)
                return calcTotalXBookings(DateTime.Now, "Cancelled", userBkgs);
            else
                return 0;
        }

        //Function: get number of attended bookings for the month
        [HttpGet("getNumAttendedBookingsForMonth/{userId}")]
        public int getNumAttendedBookingsForMonth(int userId)
        {
            BookingsController bkgController = new BookingsController(db);
            List<Bookings> userBkgs = bkgController.getUserBookingsByUserID(userId);

            if (userBkgs != null)
                return calcTotalXBookings(DateTime.Now, "Attended", userBkgs);
            else
                return 0;
        }

        //Function: get number of attended bookings for the month
        [HttpGet("getNumMissedBookingsForMonth/{userId}")]
        public int getNumMissedBookingsForMonth(int userId)
        {
            BookingsController bkgController = new BookingsController(db);
            List<Bookings> userBkgs = bkgController.getUserBookingsByUserID(userId);

            if (userBkgs != null)
                return calcTotalXBookings(DateTime.Now, "Missed", userBkgs);
            else
                return 0;
        }

        //Function: get number of attended bookings for the month
        [HttpGet("getNumFutureBookingsForMonth/{userId}")]
        public int getNumFutureBookingsForMonth(int userId)
        {
            BookingsController bkgController = new BookingsController(db);
            List<Bookings> userBkgs = bkgController.getUserBookingsByUserID(userId);

            if (userBkgs != null)
                return calcTotalXBookings(DateTime.Now, "Future", userBkgs);
            else
                return 0;
        }

        //utility function: get number of specified type of bookings for the given month
        private int calcTotalXBookings(DateTime date, string bkgStatus, List<Bookings> bookings)
        {
            List<Bookings> xTypeBookings = bookings.FindAll(
                delegate(Bookings bkg)
                {
                    return bkg.Date.Month == date.Month && bkg.Date.Year == date.Year && bkg.Cancelled == bkgStatus;
                }    
            );

            if (xTypeBookings.Any())
                return xTypeBookings.Count();
            else
                return 0;
        }

        //function for getting commonly booked days - Actually just returns a count of no. bookings for each day in descending order
        [HttpGet("getCommonlyBookedDays/{psychId}")]
        public List<CommonDaysBooked> getCommonlyBookedDays(int psychId)
        {
            List<CommonDaysBooked> commonlyBookedDays = new List<CommonDaysBooked>();

            //get the psychologist's bookings
            BookingsController bkgContrroller = new BookingsController(db);
            List<Bookings> psychBookings = bkgContrroller.getPsychBookings(psychId);

            if(psychBookings != null)
            {
                //group bookings by calendar id
                var dailyBookingsSum = (from b in db.Bookings
                                        group b by b.CalendarID
                                        into daySum
                                        select new { calendarID = daySum.Key, dayTotal = daySum.Count() }).ToList();

                if (dailyBookingsSum.Any())
                {
                    //sort list in ascending order
                    dailyBookingsSum.Sort((x, y) => y.dayTotal.CompareTo(x.dayTotal));

                    foreach (var sum in dailyBookingsSum)
                    {
                        //get day of week from calendar id
                        var calendar = db.PsychCalendar.Where(pc => pc.PsychCalendarID.Equals(sum.calendarID)).FirstOrDefault();

                        CommonDaysBooked temp = new CommonDaysBooked
                        {
                            BookingDay = calendar.DayOfWeek,
                            TotalBookings = sum.dayTotal
                        };

                        commonlyBookedDays.Add(temp);
                    }

                    return commonlyBookedDays;
                }
                else
                    return null;
            }
            else
            {
                return null;    // psych has no bookings
            }
        }

        //function to get common focus points
        [HttpGet("getCommonChatFocus/{counsellorID}")]
        public List<CommonFocusPoint> getCommonChatFocus(int counsellorID)
        {
            List<CommonFocusPoint> commonPoints = new List<CommonFocusPoint>();

            //get point sums grouped by point ID
            var pointSums = (from p in db.CounsellorChat
                             where p.Focus != null && p.CounsellorID.Equals(counsellorID)
                             group p by p.Focus
                             into focusSum
                             select new { ProblemID = focusSum.Key, PointTotal = focusSum.Count() }).ToList();

            if (pointSums.Any())
            {
                //sort list in order of ascending sum
                pointSums.Sort((x, y) => y.PointTotal.CompareTo(x.PointTotal));

                HubController hubController = new HubController(db);

                foreach (var point in pointSums)
                {
                    RHubProblems tempPoint = hubController.getFocusPointByID(point.ProblemID.Value);

                    if (tempPoint != null)
                    {
                        CommonFocusPoint cfp = new CommonFocusPoint
                        {
                            PointID = (int)point.ProblemID,
                            PointName = tempPoint.Problem,
                            PointTotal = point.PointTotal
                        };

                        commonPoints.Add(cfp);
                    }
                }

                //if (commonPoints.Count > 4)
                //    return commonPoints.GetRange(0, 4);    //returns list of point in order of descending Point frequency
                //else
                    return commonPoints;
            }
            else
            {
                return null; //no points found in bridge table
            }
        }

        [HttpGet("getNumCounsellorChatsForWeek/{counsellorID}")]
        public List<DateNumPair> getNumCounsellorChatsForWeek(int counsellorID)
        {
            List<DateNumPair> result = new List<DateNumPair>();
            //get current date
            DateTime currentDate = DateTime.Now.Date;

            var weekDates = getPastWeekDates(currentDate);

            //get all chat IDs for counsellor
            var counsellorChats = (from c in db.CounsellorChat
                                   where c.CounsellorID.Equals(counsellorID)
                                   select c.CounsellorChatID).ToList();

            if (counsellorChats.Any())
            {
                var t = (from m in db.CounsellorChatMessages
                         where counsellorChats.Contains(m.CounsellorChatID)
                         && weekDates.Contains(m.Date.Date)
                         group m by (m.Date.Date)
                         into g
                         select new DateNumPair { WeekStartDate = g.Key, Count = g.Count() });

                foreach(DateTime date in weekDates)
                {
                    if(!t.Any(x => x.WeekStartDate == date))
                    {
                        result.Add(new DateNumPair { WeekStartDate = date, Count = 0 });
                    }
                    else
                    {
                        result.Add(t.FirstOrDefault(x => x.WeekStartDate == date));
                    }
                }
            }

            return result;

        }

        //utility method: get past dates for the given week
        private List<DateTime> getPastWeekDates(DateTime date)
        {
            List<DateTime> dates = new List<DateTime>();
            DateTime currentDate = date.Date;
            dates.Add(currentDate);
            DateTime startDate = currentDate;

            for (int i = 6; i > 0; i--)
            {
                DateTime nextDate = startDate.AddDays(-1); //add 1 day to given date to get next date
                dates.Add(nextDate);

                startDate = nextDate;

            }

            dates.Sort((x, y) => x.CompareTo(y));

            return dates;
        }

        //returns number of childAnswers per answer for a question
        [HttpGet("getNumChildAnswersPerAnswer/{questionID}")]
        public List<int> getNumChildAnswersPerAnswer(int questionID)
        {
            List<int> numChildAnswers = new List<int>();

            TestsController tstController = new TestsController(db);
            List<Answers> questionAnswers = tstController.getQuestionAnswers(questionID);

            List<Answers> sortedAnswers = questionAnswers.OrderBy(Answers => Answers.Position).ToList();
            foreach(Answers qa in sortedAnswers)
            {
                var numAnswers = db.ChildAnswers.Where(ca => ca.AnswerID.Equals(qa.AnswerID)).Count();
                numChildAnswers.Add(numAnswers);
            }

            return numChildAnswers;
        }

        //get number of attended bookings with psychID
        [HttpGet("getNumEachBookingPerPsych/{psychID}")]
        public numAttendedCancelledMissed getNumEachBookingPerPsych(int psychID)
        {
            int numAttended = 0;
            int numCancelled = 0;
            int numMissed = 0;
            DateTime dateNow = DateTime.Now;
            DateTime oldDate = dateNow.AddDays(-30);

            BookingsController bookingController = new BookingsController(db);
            List<Bookings> pastBookings = bookingController.getPsychBookings(psychID);

            foreach(Bookings b in pastBookings)
            {
                if(b.Cancelled == "Attended" && b.Date > oldDate)
                {
                    numAttended++;
                }
                else if (b.Cancelled == "Cancelled" && b.Date > oldDate)
                {
                    numCancelled++;
                }
                else if (b.Cancelled == "Missed" && b.Date > oldDate)
                {
                    numMissed++;
                }
            }

            numAttendedCancelledMissed numEachBookings = new numAttendedCancelledMissed
            {
                StartDate = dateNow,
                EndDate = oldDate,
                Attended = numAttended,
                Cancelled = numCancelled,
                Missed = numMissed
            };

            return numEachBookings;
        }

        //get number of attended bookings with psychID
        [HttpPost("getNumBookingsThisMonth")]
        public int getNumBookingsThisMonth(BookingDatePsych datePsych)
        {
            int numBookings = 0;
            DateTime myBirthday = new DateTime(1999, 6, 15);
            if(datePsych.dateNow.Equals(myBirthday))
            {
                datePsych.dateNow = DateTime.Now;
            }
            DateTime oldDate = datePsych.dateNow.AddDays(-30);

            BookingsController bookingController = new BookingsController(db);
            List<Bookings> pastBookings = bookingController.getPsychBookings(datePsych.psychID);

            foreach (Bookings b in pastBookings)
            {
                if (b.Date > oldDate)
                {
                    numBookings++;
                }
            }

            return numBookings;
        }

        //percentage increase in patients
        [HttpGet("getBookingsPercentIncrease/{psychId}")]
        public double getBookingsPercentIncrease(int psychId)
        {
            //get current month
            DateTime currentDate = DateTime.Now;
            BookingDatePsych currentDatePsych = new BookingDatePsych
            {
                dateNow = currentDate,
                psychID = psychId
            };

            //get prev month's date
            int monthCurrent = currentDate.Month;
            DateTime lastMonthDate;
            
            if (monthCurrent > 1)
            {
                lastMonthDate = new DateTime(currentDate.Year, (monthCurrent - 1), currentDate.Day);
            }
            else
            {
                //current month is Jan, so prev month will be a different year
                int currentYear = currentDate.Year;
                lastMonthDate = new DateTime(currentYear - 1, 12, currentDate.Day);
            }
            BookingDatePsych lastDatePsych = new BookingDatePsych
            {
                dateNow = lastMonthDate,
                psychID = psychId
            };
            //get this month's patients
            int currentMonthBookings = getNumBookingsThisMonth(currentDatePsych);

            //get last month's patients
            int prevMonthBookings = getNumBookingsThisMonth(lastDatePsych);

            //percent increase = (current - past * 100)/past
            int increase = (currentMonthBookings - prevMonthBookings);///prevMonthPatients;

            double percentInc = 0;
            if (prevMonthBookings != 0)
                percentInc = (increase * 100) / prevMonthBookings;
            else
                percentInc = (increase * 100);

            return percentInc;
        }

        //function to get all the bookings per month (returns the number of specific types of bookings per month based on parameter)
        [HttpGet("getAllBookingsThisMonth/{typeCode}")]
        public int getAllBookingsThisMonth(int typeCode)
        {
            //List<DateNumPair> monthsAttendedBookings = new List<DateNumPair>();
            int AllBookingsPerMonth = 0;
            List<DateNumPair> bookingPairs = new List<DateNumPair>();

            DateTime dateNow = DateTime.Now;
            DateTime oldDate = dateNow.AddDays(-30);


            var allBookings = (from b in db.Bookings
                               select b);
     

            if (bookingPairs!= null)
            {

                if (typeCode == 1) //return the number of attended bookings
                {
                    foreach (Bookings bkg in allBookings)
                    {
                        if (bkg.Cancelled.Equals("Attended") && bkg.Date>oldDate && bkg.Date<dateNow)
                        {
                            AllBookingsPerMonth++;
                        }
                    }

                }
                else if (typeCode == 2) //return the number of future bookings
                {
                    foreach (Bookings bkg in allBookings)
                    {
                        if (bkg.Cancelled.Equals("Future") && bkg.Date > oldDate && bkg.Date < dateNow)
                        {
                            AllBookingsPerMonth++;
                        }
                    }
                }
                else if (typeCode == 3) //return all cancelled meetings
                {
                    foreach (Bookings bkg in allBookings)
                    {
                        if (bkg.Cancelled.Equals("Cancelled") && bkg.Date > oldDate && bkg.Date < dateNow)
                        {
                            AllBookingsPerMonth++;
                        }
                    }
                }
                else if (typeCode == 4)   //number of bookings missed
                {
                    foreach (Bookings bkg in allBookings)
                    {
                        if (bkg.Cancelled.Equals("Missed") && bkg.Date > oldDate && bkg.Date < dateNow)
                        {
                            AllBookingsPerMonth++;
                        }
                    }
                }else if (typeCode == 5) //return all the bookings irrespective of the type
                {
                    foreach (Bookings bkg in allBookings)
                    {
                        if(bkg.Date > oldDate && bkg.Date < dateNow)
                        {
                            AllBookingsPerMonth++;
                        }
                             
                    }
                }

            }else
            {
                return 0; //no bookings are in the table
            }


            return AllBookingsPerMonth;



        }

        //function to get the number of children on the app who are registered but not paired with a guardian
        [HttpGet("getOnlyKids")] 
        public int getOnlyKids()
        {
            int numPairedChildren = 0;
            int numTotalChildren = 0;

            var pairedChildren = (from p in db.Pair
                                  select p);

            var allChildren = (from u in db.User
                               where u.UserType.Equals("Child")
                               select u);

            foreach(User u in allChildren)
            {
                numTotalChildren++;

            }
            foreach (Pair p in pairedChildren)
            {
                numPairedChildren++;
            }

            return numTotalChildren - numPairedChildren;

        }

        //function to get only guardians who are registered on the app
        [HttpGet("getOnlyGuardians")]
        public int getOnlyGuardians()
        {
            int numPairedGuardian = 0;
            int numTotalGuardian = 0;

            List<int> pairedGuardianList = new List<int>();

            var pairedGuardian = (from p in db.Pair
                                  select p);
          

            var allGuardian = (from u in db.User
                               where u.UserType.Equals("Parent")
                               select u);

            foreach (User u in allGuardian)
            {
                numTotalGuardian++;

            }
            foreach (Pair p in pairedGuardian)
            {
                pairedGuardianList.Add(p.ParentID);
            }

            //removing the duplicate parent IDs
            pairedGuardianList = pairedGuardianList.Distinct().ToList();

            foreach(int i in pairedGuardianList)
            {
                numPairedGuardian++;
            }

            return numTotalGuardian - numPairedGuardian;

        }

        //function to get number of guardian child pairs
        [HttpGet("getNumPairs")]
        public int getNumPairs()
        {
            int pairCount = 0;

            //get all the pairs
            var pair = (from p in db.Pair
                        select p);

            foreach(Pair pr in pair)
            {
                pairCount++;
            }
            return pairCount;
        }
        //function to get the number of pairs that are linked to the psychologist
        [HttpGet("getNumPairsLinked")]
        public int getNumPairsLinked()
        {
            int linkCount = 0;
            List<int> uniqueLinks = new List<int>();

            //get all the pairs
            var link = (from l in db.Link
                        select l);


            foreach(Link lk in link)
            {
                uniqueLinks.Add(lk.PairID);
            }
            //removing duplicate links
            uniqueLinks = uniqueLinks.Distinct().ToList();

            foreach(int i in uniqueLinks)
            {
                linkCount++;
            }

            return linkCount;

        }

        //function to get the number of pairs tha are not linked to a psychologist
        [HttpGet("getNumPairsNotLinked")]
        public int getNumPairsNotLinked()
        {
            int numPairsLinked = getNumPairsLinked();
            int totalPairs = getNumPairs();

            return totalPairs - numPairsLinked;

        }

        //function to get all ages of kids registered

        //count of bookings per month grouped by age
        [HttpGet("getBookingsMonthlyAgeCounts/{psychId}")]
        public List<FocusCounts> getBookingsMonthlyAgeCounts(int psychId)
        {
            List<FocusCounts> result = new List<FocusCounts>();

            //get all psych patients
            PatientController patientController = new PatientController(db);
            List<User> psychPatients = patientController.GetPsychologistPatients(psychId);

            //get list of all psych's bookings
            BookingsController bkgController = new BookingsController(db);
            List<Bookings> allPsychBokings = bkgController.getPsychBookings(psychId);

            //get pair IDs
            var pairIds = allPsychBokings.Select(x => x.PairID);

            //get ages
            var ages = getAges(psychPatients).Distinct().ToList();

            ages.Sort();
            //get years when need to be born for those ages

            //count booking for each month for diff ages

            if (psychPatients != null)
            {
                foreach(int age in ages)
                {
                    int dobYear = getDOB(age).Year;

                    //if dobYear == 2021 it means dob is null

                    List<Pair> xOldPatients = null;

                    if(dobYear == DateTime.Now.Year)
                    {
                        xOldPatients = (from p in psychPatients
                                        where p.DOB == null
                                        join pair in db.Pair on p.UserID equals pair.ChildID
                                        select pair).ToList();
                    }
                    else
                    {
                        //fetch all x-year old patients pairIds
                        xOldPatients = (from p in psychPatients
                                        where p.DOB != null && p.DOB.Value.Year == dobYear
                                        join pair in db.Pair on p.UserID equals pair.ChildID
                                        select pair).ToList();
                    }

                    FocusCounts temp = new FocusCounts();
                    temp.focusName = age.ToString();
                    temp.monthlyCounts = new List<int>();

                    for(int i = 1; i <= 12; i++)
                    {
                        //count number of times pairid is found in psych bookings
                        var t = (from b in allPsychBokings
                                 where b.Date.Date.Month == i && b.Date.Date.Year == DateTime.Now.Year
                                 join p in xOldPatients on b.PairID equals p.PairID
                                 group b by new { b.Date.Date.Month}
                                 into grp
                                 select new { grp.Key, count = grp.Count() });

                        //list does not have the the current iteration of the month in it
                        if (!t.Any(x => x.Key.Month == i))
                        {
                            temp.monthlyCounts.Add(0);
                        }
                        else
                        {
                            var found = t.FirstOrDefault(x =>x.Key.Month == i);

                            temp.monthlyCounts.Add(found.count);
                        }

                    }
                    result.Add(temp);
                    
                }
            }

            return result;
        }

        // count of focus points per age
        [HttpGet("getFocusCountsPerAge/{psychId}")]
        public List<FocusAgeCount> getFocusCountsPerAge(int psychId)
        {
            List<FocusAgeCount> result = new List<FocusAgeCount>();

            //get psych patients
            PatientController patientController = new PatientController(db);
            List<User> psychPatients = patientController.GetPsychologistPatients(psychId);

            // get list of all focus points
            HubController hubController = new HubController(db);
            List<RHubProblems> allFocusPoints = hubController.fetchAllFocusPoints();


            if (psychPatients != null)
            {
                foreach (var point in allFocusPoints)
                {
                    //create new Focus age count object
                    FocusAgeCount temp = new FocusAgeCount();
                    temp.counts = new List<int>();
                    temp.focusPoint = point.Problem;

                    for (int i = 7; i <= 13; i++)
                    {
                        //get year for current age in iteration
                        int DOBYear = getDOB(i).Year;

                        //return patients that are born in the year DOBYear
                        var t = (from p in psychPatients
                                 where p.DOB != null && p.DOB.Value.Year == DOBYear
                                 join u in db.RHubUserBridge on p.UserID equals u.UserID
                                 group u by u.ProblemID
                                 into g
                                 select new { g.Key, Count = g.Count() });


                        if (!t.Any(x => x.Key == point.ProblemID))
                        {
                            temp.counts.Add(0);
                        }
                        else
                        {
                            var found = t.FirstOrDefault(x => x.Key == point.ProblemID);

                            temp.counts.Add(found.Count);
                        }
                    }

                    result.Add(temp);
                }
                
            }

            return result;
        }

        //utility function: given a list of users, return the age
        private List<int> getAges(List<User> users)
        {
            List<int> ages = new List<int>();

            UserController userController = new UserController(db);

            foreach(User u in users)
            {
                ages.Add(userController.getUserAge(u.UserID));
            }

            return ages;
        }

        //utility function: given age, return DOB
        private DateTime getDOB(int age)
        {
            return DateTime.Now.AddYears(-age);
        }

        //count of focus per month
        [HttpGet("getBookingsMonthlyFocusCounts/{psychId}")]
        public List<FocusCounts> getBookingsMonthlyFocusCounts(int psychId)
        {
            List<FocusCounts> result = new List<FocusCounts>();

            //get list of all psych's bookings
            BookingsController bkgController = new BookingsController(db);
            List<Bookings> allPsychBokings = bkgController.getPsychBookings(psychId);

            //get list of all focus points
            HubController hubController = new HubController(db);
            List<RHubProblems> allFocusPoints = hubController.fetchAllFocusPoints();

            var focusIDs = allFocusPoints.Select(x => x.ProblemID);

            if(allPsychBokings != null)
            {
                //count focus point occurences in bookings
                var combo = (from b in allPsychBokings
                             where b.ProblemID != null
                             && focusIDs.Contains(b.ProblemID.Value) && b.Date.Date.Year == DateTime.Now.Year
                             group b by new { b.Date.Date.Month, b.ProblemID }
                             into g
                             select new { g.Key, Count = g.Count()});

               // return new List<FocusCounts>();

                foreach(var p in allFocusPoints)
                {
                    FocusCounts temp = new FocusCounts();
                    temp.focusName = p.Problem;
                    temp.monthlyCounts = new List<int>();

                    for(int i = 1; i <= 12; i++)
                    {
                        //list does not have the the current iteration of the focus in it
                        if (!combo.Any(x => x.Key.ProblemID == p.ProblemID && x.Key.Month == i))
                        {
                            temp.monthlyCounts.Add(0);
                        }
                        else
                        {
                            var found = combo.FirstOrDefault(x => x.Key.ProblemID == p.ProblemID && x.Key.Month == i);

                            temp.monthlyCounts.Add(found.Count);
                        }
                    }

                    result.Add(temp);
                    
                }
            }

            return result;

        }

        //count of bookings per month
        [HttpGet("getBookingsCountsPerMonth/{psychId}")]
        public List<MonthBookingCount> getBookingsCountsPerMonth(int psychId)
        {
            List<MonthBookingCount> bookingsPerMonth = new List<MonthBookingCount>();

            List<int> months = new List<int>(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 });

            //get all psych's bookings
            BookingsController bkgController = new BookingsController(db);
            List<Bookings> allPsychBokings = bkgController.getPsychBookings(psychId);

            if(allPsychBokings != null)
            {
                //group bookings according to date
                var result = (from b in allPsychBokings
                              where months.Contains(b.Date.Date.Month) && b.Date.Date.Year == DateTime.Now.Year
                              group b by (b.Date.Date.Month)
                              into g
                              select new { g.Key, Count = g.Count() });

                foreach(var month in months)
                {
                    //create string representation of the month in iteration
                    var monthStr = new DateTime(DateTime.Now.Year, month, 1).ToString("MMM", CultureInfo.InvariantCulture);

                    //list does not have the current month iteration in it
                    if (!result.Any(x => x.Key == month))
                    {
                        bookingsPerMonth.Add(new MonthBookingCount {month = monthStr, bookingCount = 0 });
                    }
                    else
                    {
                        var found = result.FirstOrDefault(x => x.Key == month);

                        bookingsPerMonth.Add(new MonthBookingCount { month = monthStr, bookingCount = found.Count });
                    }
                }

            }

            return bookingsPerMonth;
        }

        //count of bookings per month
        [HttpGet("getBookingsCountsPerMonthAdmin")]
        public List<MonthBookingCount> getBookingsCountsPerMonthAdmin()
        {
            List<MonthBookingCount> bookingsPerMonth = new List<MonthBookingCount>();

            List<int> months = new List<int>(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 });

            //if (allPsychBokings != null)
            {
                //group bookings according to date
                var result = (from b in db.Bookings
                              where months.Contains(b.Date.Date.Month) && b.Date.Date.Year == DateTime.Now.Year
                              group b by (b.Date.Date.Month)
                              into g
                              select new { g.Key, Count = g.Count() });

                foreach (var month in months)
                {
                    //create string representation of the month in iteration
                    var monthStr = new DateTime(DateTime.Now.Year, month, 1).ToString("MMM", CultureInfo.InvariantCulture);

                    //list does not have the current month iteration in it
                    if (!result.Any(x => x.Key == month))
                    {
                        bookingsPerMonth.Add(new MonthBookingCount { month = monthStr, bookingCount = 0 });
                    }
                    else
                    {
                        var found = result.FirstOrDefault(x => x.Key == month);

                        bookingsPerMonth.Add(new MonthBookingCount { month = monthStr, bookingCount = found.Count });
                    }
                }

            }

            return bookingsPerMonth;
        }

        //return number of bookings that happened last year this coming month
        [HttpGet("numBookingsLastYearPerMonth/{psychID}")]
        public double numBookingsLastYearPerMonth(int psychID)
        {
            //var numBookings = db.Bookings.Where(ca => ca.AnswerID.Equals(qa.AnswerID)).Count();
            int numBookings = 0;

            int nextMonth = 0;
            if (DateTime.Now.Month != 12)
            {
                nextMonth = DateTime.Now.Month + 1;
            }
            else
            {
                nextMonth = 1;
            }

            BookingsController bookingsController = new BookingsController(db);
            List<Bookings> bookings = bookingsController.getPsychBookings(psychID);

            foreach(Bookings b in bookings)
            {
                if(b.Date.Month == nextMonth && b.Date.Year != DateTime.Now.Year)
                {
                    numBookings++;
                }
            }

            //var monthCount = db.Bookings.Where(b => b.Date.Month.Equals(nextMonth)).Count();
            double avg;

            //if (monthCount != 0)
            //    avg = (double) numBookings / (double) monthCount;
            //else
                avg = (double) numBookings;

            return avg;
        }

        //count of user registrations per month and type
        [HttpGet("getUserTypeRegistrationsPerMonth/{userType}")]
        public List<MonthBookingCount> getUserTypeRegistrationsPerMonth(String userType)
        {
            List<MonthBookingCount> registersPerMonth = new List<MonthBookingCount>();

            List<int> months = new List<int>(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 });

                //group registrations according to date
            var result = (from b in db.User
                            where months.Contains(b.DateRegistered.Date.Month) && b.DateRegistered.Date.Year == DateTime.Now.Year && b.UserType.Equals(userType)
                            group b by (b.DateRegistered.Date.Month)
                            into g
                            select new { g.Key, Count = g.Count() });

            foreach (var month in months)
            {
                //create string representation of the month in iteration
                var monthStr = new DateTime(DateTime.Now.Year, month, 1).ToString("MMM", CultureInfo.InvariantCulture);

                //list does not have the current month iteration in it
                if (!result.Any(x => x.Key == month))
                {
                    registersPerMonth.Add(new MonthBookingCount { month = monthStr, bookingCount = 0 });
                }
                else
                {
                    var found = result.FirstOrDefault(x => x.Key == month);

                    registersPerMonth.Add(new MonthBookingCount { month = monthStr, bookingCount = found.Count });
                }
            }

            return registersPerMonth;
        }

        //count of user registrations per month and type
        [HttpGet("getPopularTimes/{psychID}")]
        public List<MonthBookingCount> getPopularTimes(int psychID)
        {
            List<MonthBookingCount> popularTimesCounts = new List<MonthBookingCount>();

            //group registrations according to date
            var result = (from b in db.Bookings
                          group b by (b.Time)
                          into g
                          select new { g.Key, Count = g.Count() });

            foreach (var count in result)
            {
                //create string representation of the month in iteration
                //var monthStr = new DateTime(DateTime.Now.Year, month, 1).ToString("MMM", CultureInfo.InvariantCulture);

                //list does not have the current month iteration in it

                popularTimesCounts.Add(new MonthBookingCount { month = count.Key.ToString(), bookingCount = count.Count });
            }

            //popularTimesCounts = popularTimesCounts.OrderByDescending(MonthBookingCount => MonthBookingCount.bookingCount).ToList();
            popularTimesCounts = popularTimesCounts.OrderBy(MonthBookingCount => MonthBookingCount.month).ToList();


            return popularTimesCounts;
        }
    }
}
