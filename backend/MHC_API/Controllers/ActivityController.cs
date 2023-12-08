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
    public class ActivityController : ControllerBase
    {
        private MHCDatabaseDBContext db;

        public ActivityController(MHCDatabaseDBContext db)
        {
            this.db = db;
        }

        //function to get all the activities a child has done - given child id
        [HttpGet("getAllChildActivities/{childId}")]
        public List<ChildActivity> getAllChildActivities(int childId)
        {
            List<ChildActivity> allActivities = new List<ChildActivity>();

            //get all activities with matching childId
            var activities = db.ChildActivity.Where(a => a.ChildID.Equals(childId));

            if (activities.Any())
            {
                foreach(ChildActivity act in activities)
                {
                    allActivities.Add(act);
                }

                return allActivities;
            }
            else
            {
                //no activities found for the child
                return null;
            }
        }

        //Function to get all activities completed with a specific psychologist
        [HttpPost("getAllChildPsychActivities")]
        public List<ChildActivity> getAllChildPsychActivities(PsychPatientPair psyPat)
        {
            //get a list of all activities a psychologist has assigned
            List<ChildActivity> psychActivities = getPsychActivities(psyPat.PsychID);

            if (psychActivities != null)
            {
                //find all the psych activities that have the matching child id
                List<ChildActivity> childPsychActivities = psychActivities.FindAll(
                    delegate(ChildActivity act)
                    {
                        return act.ChildID == psyPat.ChildID;
                    });

                if (childPsychActivities.Any())
                {
                    //sort the activities by date
                    childPsychActivities.Sort((x, y) => y.Date.CompareTo(x.Date));
                    return childPsychActivities;
                }
                else
                {
                    return null; //Did not find any psych activities for the child
                }
            }
            else
            {
                //no activities asigned by this psychologist
                return null;
            }
        }

        //utility function: get all activities assigned by a psychologist
        private List<ChildActivity> getPsychActivities(int psychId)
        {
            List<ChildActivity> psychActivities = new List<ChildActivity>();

            var acts = db.ChildActivity.Where(a => a.PsychID.Equals(psychId));

            if (acts.Any())
            {
                foreach (ChildActivity act in acts)
                    psychActivities.Add(act);

                return psychActivities;
            }
            else
            {
                //no activities were assigned by the given psychologist
                return null;
            }
        }

    }
}
