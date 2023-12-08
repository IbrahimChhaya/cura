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
    public class HubController : ControllerBase
    {
        private MHCDatabaseDBContext db;
        public HubController(MHCDatabaseDBContext db)
        {
            this.db = db;
        }


        //Function to get all focus points (problems) a child has
        [HttpGet("fetchChildFocusPoints/{childID}")]
        public List<RHubProblems> fetchChildFocusPoints(int childID)
        {
            List<RHubProblems> focusPoints = new List<RHubProblems>();

            //check that child exists
            var child = db.User.Where(u => u.UserID.Equals(childID)).FirstOrDefault();

            if (child != null)
            {
                //get list of problem ids linked to a child
                var problemIds = (from r in db.RHubUserBridge
                                  where r.UserID.Equals(child.UserID)
                                  select r.ProblemID);

                if (problemIds != null)
                {
                    foreach (int id in problemIds)
                    {
                        //fetch complete problem
                        var tempProblem = getProblemByID(id);

                        if (tempProblem != null)
                            focusPoints.Add(tempProblem);
                    }
                }
                else
                {
                    //no focus points linked to the child
                    return null;
                }


                //return list of problems
                return focusPoints;
            }
            else
            {
                //invalid child id given
                return null;
            }
        }

        //utility method: get problem by id
        private RHubProblems getProblemByID(int id)
        {
            var problem = db.RHubProblems.Where(p => p.ProblemID.Equals(id)).FirstOrDefault();

            return problem;
        }

        //function to save/assign a focus point
        [HttpPost("assignChildFocusPoint")]
        public RHubUserBridge assignChildFocusPoint(RHubUserBridge r)
        {
            //check that a valid problem id was passed
            var problem = db.RHubProblems.Where(p => p.ProblemID.Equals(r.ProblemID)).FirstOrDefault();

            if (problem != null)
            {
                var newUserFocusPoint = new RHubUserBridge
                {
                    ProblemID = r.ProblemID,
                    UserID = r.UserID
                };

                db.RHubUserBridge.Add(newUserFocusPoint);

                try
                {
                    db.SaveChanges();
                    return newUserFocusPoint;
                }
                catch (Exception ex)
                {
                    ex.GetBaseException();
                    return new RHubUserBridge { BridgeID = -1 };   //Error while saving to db
                }
            }
            else
                return new RHubUserBridge { BridgeID = -2 };    //The problem you're trying to link to doesn't exist
        }

        //remove focus point assignment from child
        [HttpPost("removeChildFocusPoint")]
        public int removeChildFocusPoint(RHubUserBridge r)
        {
            //check that a valid problem id was passed
            var problem = (from fp in db.RHubUserBridge
                           where fp.UserID.Equals(r.UserID) && fp.ProblemID.Equals(r.ProblemID)
                           select fp).FirstOrDefault();

            if (problem != null)
            {
                //delete from table
                db.RHubUserBridge.Remove(problem);

                try
                {
                    db.SaveChanges();
                    return 0;
                }
                catch (Exception ex)
                {
                    ex.GetBaseException();
                    return -1;   //Error while saving to db
                }
            }
            else
                return -2;    //The focus point you're trying to remove doesn't exist
        }

        //Function to fetch all standard focs points
        [HttpGet("fetchAllFocusPoints")]
        public List<RHubProblems> fetchAllFocusPoints()
        {
            List<RHubProblems> focusPoints = new List<RHubProblems>();

            var points = (from p in db.RHubProblems
                          select p);

            if (points.Any())
            {
                foreach (RHubProblems rp in points)
                {
                    focusPoints.Add(rp);
                }

                return focusPoints;
            }
            else
            {
                return null;    //no focus points were added
            }

        }

        //Function to add a focus point
        [HttpPost("addNewFocusPoint")]
        public RHubProblems addNewFocusPoint(RHubProblems problem)
        {
            var newProblem = new RHubProblems
            {
                Problem = problem.Problem,
                Description = problem.Description
            };
            db.RHubProblems.Add(newProblem);

            try
            {
                db.SaveChanges();
                return newProblem;
            }
            catch (Exception ex)
            {
                ex.GetBaseException();
                return new RHubProblems { ProblemID = -1 };  //Failed to save new focus point
            }
        }


        //method to return all the images and descriptions for a problem (based on problem ID)
        [HttpGet("fetchResourceHubImages/{problemID}")]
        public List<Images> fetchResourceHubImages(int problemID)
        {
            List<Images> imageList = new List<Images>();

            var images = (from i in db.Images
                          where i.ProblemID.Equals(problemID)
                          select i);

            if(images != null)
            {
                foreach(Images img in images)
                {
                    imageList.Add(img);
                }
            }
            else
            {
                return null;
            }

            return imageList;
        }

        //method to return all the images and descriptions for a problem (based on problem ID)
        [HttpGet("getRHubProblem/{problemID}")]
        public RHubProblems getRHubProblem(int problemID)
        {

            var problem = (from p in db.RHubProblems
                           where p.ProblemID.Equals(problemID)
                           select p).FirstOrDefault();

            if (problem != null)
            {
                return problem;
            }
            else
            {
                return null;
            }
        }

        //method to get a focus  point by its ID
        [HttpGet("getFocusPointByID/{problemID}")]
        public RHubProblems getFocusPointByID(int problemID)
        {
            var point = db.RHubProblems.Where(p => p.ProblemID.Equals(problemID)).FirstOrDefault();

            if (point != null)
                return point;
            else
                return null; //point not found
        }


        //function to add resource hub problems
        [HttpPost("AddResourceHubProblem")]
        public RHubProblems AddResourceHubProblem(RHubProblems rProblem)
        {

            var newProblem = new RHubProblems
            {
                Problem = rProblem.Problem,
                Description = rProblem.Description,
                TitleImage = rProblem.TitleImage,
                Colour = rProblem.Colour

            };
            db.RHubProblems.Add(newProblem);
            try
            {
                //all is well
                db.SaveChanges();
                return new RHubProblems
                {
                    ProblemID = newProblem.ProblemID,
                    Problem = newProblem.Problem,
                    Description = newProblem.Description,
                    TitleImage = newProblem.TitleImage,
                    Colour = newProblem.Colour
                };
            }
            catch (Exception ex)
            {
                ex.GetBaseException();
                return new RHubProblems { ProblemID = -1 };
            }
        }

        //function to add resource hub problems to image table
        [HttpPost("AddRHubProblemImage")]
        public Images AddRHubProblemImage(Images rProblem)
        {

            var newProblem = new Images
            {
                Description = rProblem.Description,
                FileName = rProblem.FileName,
                ProblemID = rProblem.ProblemID

            };
            db.Images.Add(newProblem);
            try
            {
                //all is well
                db.SaveChanges();
                return new Images
                {
                    Description = newProblem.Description,
                    FileName = newProblem.FileName,
                    ProblemID = newProblem.ProblemID
                };
            }
            catch (Exception ex)
            {
                ex.GetBaseException();
                return new Images { ProblemID = -1 };
            }
        }

        //function to update resource hub problems
        [HttpPost("updateRHubProblem")]
        public int updateRHubProblem(RHubProblems rProblem)
        {
            var problem = db.RHubProblems.Where(rhp => rhp.ProblemID.Equals(rProblem.ProblemID)).FirstOrDefault();

            if (problem != null)
            {
                problem.Problem = rProblem.Problem;
                problem.Description = rProblem.Description;
                problem.Colour = rProblem.Colour;
                problem.TitleImage = rProblem.TitleImage;

                try
                {
                    db.SaveChanges();
                    return problem.ProblemID;
                }
                catch (Exception ex)
                {
                    ex.GetBaseException();
                    return -1;
                }
            }
            else
                return 0;
        }

        //function to update resource hub problems
        [HttpPost("updateRHubProblemImage")]
        public int updateRHubProblemImage(Images rProblem)
        {
            var problem = db.Images.Where(rhp => rhp.ProblemID.Equals(rProblem.ProblemID)).FirstOrDefault();

            if (problem != null)
            {
                problem.Description = rProblem.Description;
                problem.FileName= rProblem.FileName;

                try
                {
                    db.SaveChanges();
                    return 1;
                }
                catch (Exception ex)
                {
                    ex.GetBaseException();
                    return -1;
                }
            }
            else
                return 0;
        }
    }
}
