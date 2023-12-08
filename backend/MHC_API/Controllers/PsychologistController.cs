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
    public class PsychologistController : ControllerBase
    {
        private MHCDatabaseDBContext db;

        public PsychologistController(MHCDatabaseDBContext context)
        {
            db = context;
        }

        //function to update a psychologist's calendar
        [HttpPost("updatePsychCalendar")]
        public PsychCalendar updatePsychCalendar(PsychCalendar calendar)
        {
            //get the calendar to be updated
            var pCalendar = db.PsychCalendar.Where(p => p.PsychCalendarID.Equals(calendar.PsychCalendarID)).FirstOrDefault();

            if(pCalendar != null)
            {
                pCalendar.PsychCalendarID = calendar.PsychCalendarID;
                pCalendar.DayOfWeek = calendar.DayOfWeek;
                pCalendar.SingleStart = calendar.SingleStart;
                pCalendar.SingleEnd = calendar.SingleEnd;
                pCalendar.RepeatStart = calendar.RepeatStart;
                pCalendar.RepeatEnd = calendar.RepeatEnd;
                pCalendar.PsychID = calendar.PsychID;
                pCalendar.Closed = calendar.Closed;

                try
                {
                    db.SaveChanges();
                    return pCalendar;   //all is well
                }
                catch(Exception ex)
                {
                    ex.GetBaseException();
                    return new PsychCalendar { PsychCalendarID = -1 }; //could not save changes
                }
            }
            else
            {
                return new PsychCalendar { PsychCalendarID = 0 }; //could not find calendar
            }
        }

        //Function to create default calendar times for a psychologist
        [HttpGet("createDefaultCalendarsForPsych/{psychId}")]
        public int createDefaultCalendarsForPsych(int psychId)
        {
            List<String> weekdays = new List<string>();
            weekdays.Add("Monday");
            weekdays.Add("Tuesday");
            weekdays.Add("Wednesday");
            weekdays.Add("Thursday");
            weekdays.Add("Friday");

            List<String> weekends_holidays = new List<string>();
            weekends_holidays.Add("Saturday");
            weekends_holidays.Add("Sunday");
            weekends_holidays.Add("Holidays");


            //check if psych exists
            var psych = db.Psychologist.Where(p => p.PsychID.Equals(psychId)).FirstOrDefault();

            if(psych != null)
            {
                DateTime date = DateTime.Now;
                DateTime singleStart = new DateTime(date.Year, date.Month, date.Day, 8, 0, 0);
                DateTime singleEnd = new DateTime(date.Year, date.Month, date.Day, 11, 0, 0);
                DateTime repeatStart = new DateTime(date.Year, date.Month, date.Day, 12, 0, 0);
                DateTime repeatEnd = new DateTime(date.Year, date.Month, date.Day, 16, 0, 0);

                //start creating calendars

                //creating calendars for weekdays
                foreach (String day in weekdays)
                {
                    PsychCalendar temp = new PsychCalendar
                    {
                        DayOfWeek = day,
                        SingleStart = singleStart,
                        SingleEnd = singleEnd,
                        RepeatStart = repeatStart,
                        RepeatEnd = repeatEnd,
                        PsychID = psych.PsychID,
                        Closed = false
                    };

                    db.PsychCalendar.Add(temp);
                }

                //create calendars for weekends and puc=blic holidays
                foreach(String day in weekends_holidays)
                {
                    PsychCalendar temp = new PsychCalendar
                    {
                        DayOfWeek = day,
                        SingleStart = singleStart,
                        SingleEnd = singleEnd,
                        RepeatStart = repeatStart,
                        RepeatEnd = repeatEnd,
                        PsychID = psych.PsychID,
                        Closed = true
                    };

                    db.PsychCalendar.Add(temp);
                }

                try
                {
                    db.SaveChanges();
                    return 0;   //all is well
                }
                catch (Exception ex)
                {
                    ex.GetBaseException();
                    return -1; //could not save changes
                }

            }
            else
            {
                return -2; //invalid psych id (psych does not exist)
            }
        }

        //function to get the ratings for the psychologist
        [HttpGet("GetPsychologistRating/{userId}")]
        public double GetPsychologistRating(int userId)
        {
            List<int> ratingList = new List<int>();

            dynamic link = (from l in db.Link
                            where l.PsychID.Equals(userId)
                            select l);
            double totalRating = 0.0;
            int count = 0;

            if (link != null)
            {
                foreach(Link l in link)
                {
                    count++;
                    ratingList.Add(l.Rating);
                }

                foreach (int i in ratingList)
                {
                    totalRating += i;
                }
            }
            else
            {
                return 0.0;
            }

            double averageRating = totalRating / count;

            return averageRating;
        }

        [HttpGet("GetAllPsychologistRatings/{psychID}")]
        public List<Ratings> GetAllPsychologistRatings(int psychID)
        {
            //get all links with psychID
            var links = db.Link.Where(l => l.PsychID.Equals(psychID));

            //get all Users
            var users = db.User.Select(u => u).ToList();

            if(links != null)
            {
                var linkAndPair = (from l in links
                                   join p in db.Pair on l.PairID equals p.PairID
                                   select new Link { LinkID = p.ParentID, Rating = l.Rating }).ToList();

                if (linkAndPair != null)
                {
                    var distinctItems = linkAndPair.GroupBy(x => x.LinkID).Select(y => y.First());

                    UserController usrController = new UserController(db);
                    List<Ratings> ratings = new List<Ratings>();

                    foreach(Link link in distinctItems)
                    {
                        var temp = usrController.getUserById(link.LinkID);

                        if(temp != null)
                        {
                            Ratings tempRating = new Ratings
                            {
                                Name = temp.Name,
                                Rating = link.Rating
                            };

                            ratings.Add(tempRating);
                        }

                    }

                    return ratings;
                }
                return null;    //no matching pairs found for the links found
            }

            return null; //no patients linked to psych
        }

        //set rating using psychID and guardianID
        [HttpPost("setRating")]
        public int setRating(PsychRating psychRating)
        {
            //get me all the pairIDs for this guardian
            var pairIDs = (from p in db.Pair
                           where p.ParentID.Equals(psychRating.GuardianID)
                           select p.PairID).ToList();

            //if any
            if (pairIDs.Any())
            {
                //for each pairID
                foreach (int pid in pairIDs)
                {
                    //get me all the links for these pairs with this psych
                    var links = (from l in db.Link
                                 where l.PsychID.Equals(psychRating.PsychID) && l.PairID.Equals(pid)
                                 select l).ToList();
                    //if any
                    if (links.Any())
                    {
                        //for each link with this psych and this pair, set the rating (means all kids with this guardian set the same rating for the psych)
                        foreach (Link link in links)
                        {
                            link.Rating = psychRating.Rating;

                            try
                            {
                                db.SaveChanges();
                            }
                            catch (Exception ex)
                            {
                                ex.GetBaseException();
                                return 0; //falied to change link rating
                            }
                        }
                    }
                }
            }
            return 1;
        }
    }
}
