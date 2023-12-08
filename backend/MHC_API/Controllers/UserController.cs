using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using MHC_API.Data;
using MHC_API.Model;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace MHC_API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UserController : ControllerBase
    {
        private MHCDatabaseDBContext  db;
        public UserController(MHCDatabaseDBContext db)
        {
            this.db = db;
        }

        //Login Function
        [HttpPost("login")]
        public User login(User usr)
        {
            // var user = db.User.Where(x => x.Email == usr.Email && x.Password == usr.Password).FirstOrDefault();

           var user = (from u in db.User
                        where u.Email.Equals(usr.Email)
                        select u).FirstOrDefault();

            if (user != null)
            {
                if(user.Password.Equals(Secrecy.HashPassword(user.Spice + usr.Password)))
                {
                    
                    return new User
                    {
                        UserID = user.UserID,
                        Name = user.Name,
                        Email = user.Email,
                        DOB = user.DOB,
                        UserType = user.UserType,
                        Grade = user.Grade,
                        ProfilePicture = user.ProfilePicture,
                        DateRegistered = user.DateRegistered,
                        Status = user.Status
                    };
                }
                else
                {
                    //password is incorrect
                    return new User { UserID = 0, Password = "Incorrect Password" };
                }
                
            }
            else
            {
                return new User { UserID = 0};  //user does not exist
            }

        }

        //register function
        [HttpPost("Register")]
        public User register(User usr)
        {
            //check if a user with the given email exists
            var user = (from u in db.User
                        where u.Email.Equals(usr.Email)
                        select u).FirstOrDefault();

            //if the given email is unique
            if (user == null)
            {
                String spice = Guid.NewGuid().ToString();

                var newUser = new User
                {
                    Name = usr.Name,
                    Email = usr.Email,
                    Password = Secrecy.HashPassword(spice + usr.Password),
                    DOB = usr.DOB,
                    UserType = usr.UserType,
                    Grade = usr.Grade,
                    ProfilePicture = usr.ProfilePicture,
                    DateRegistered = usr.DateRegistered,
                    Status = usr.Status,
                    Spice = spice,
                    PicturePass = usr.PicturePass
                };
                db.User.Add(newUser);

                try
                {
                    //all is well
                    db.SaveChanges();
                    return new User
                    {
                        UserID = newUser.UserID,
                        Name = newUser.Name,
                        Email = newUser.Email,
                        DOB = newUser.DOB,
                        UserType = newUser.UserType,
                        Grade = newUser.Grade,
                        ProfilePicture = newUser.ProfilePicture,
                        DateRegistered = newUser.DateRegistered,
                        Status = newUser.Status
                    };
                }
                catch (Exception ex)
                {
                    ex.GetBaseException();
                    return new User { UserID = -1 };
                }
            }
            else
            {
                //a user with the given email already exists
                return new User { UserID = 0 };
            }
        }

        //Function for registering a counsellor
        [HttpPost("RegisterCounsellor")]
        public Counsellor RegisterCounsellor(CounsellorReg counsellor)
        {
            User usr = new User
            {
                Name = counsellor.Name,
                Email = counsellor.Email,
                Password = counsellor.Password,
                DOB = counsellor.DOB,
                UserType = "Counsellor",
                Grade = null,
                ProfilePicture = counsellor.ProfilePicture,
                DateRegistered = DateTime.Now.Date,
                Status = "Pending",
                Spice = null
            };

            User newUser = register(usr);

            if(newUser.UserID > 0)
            {
                Counsellor newCouns = new Counsellor
                {
                    CounsellorID = newUser.UserID,
                    PracticeNum = counsellor.PracticeNum,
                    HighestCertificate = counsellor.HighestCertificate
                };

                db.Counsellor.Add(newCouns);

                try
                {
                    db.SaveChanges();
                    return newCouns;
                }
                catch(Exception ex)
                {
                    ex.GetBaseException();
                    return new Counsellor { CounsellorID = -2 }; //Something went wrong registering counsellor
                }
            }
            else if(newUser.UserID == 0)
            {
                return new Counsellor { CounsellorID = 0 };  //email is already taken
            }
            else
            {
                return new Counsellor { CounsellorID = -1 }; //Something else went wrong with user registration
            }

        }

        //function to register a psychologist
        [HttpPost("RegisterPsychologist")]
        public Psychologist RegisterPsychologist(PsychologistReg psych)
        {
            User usr = new User
            {
                Name = psych.Name,
                Email = psych.Email,
                Password = psych.Password,
                DOB = psych.DOB,
                UserType = "Psychologist",
                Grade = null,
                ProfilePicture = psych.ProfilePicture,
                DateRegistered = DateTime.Now.Date,
                Status = "Pending",
                Spice = null
            };

            User newUser = register(usr);

            if (newUser.UserID > 0)
            {
                Psychologist newPsych = new Psychologist
                {
                    PsychID = newUser.UserID,
                    Address = psych.Address,
                    Qualification = psych.Qualification,
                    RegNumber = psych.RegNumber,
                    Description = psych.Description,
                    Speciality = psych.Speciality,
                    Status = "Pending"
                };

                db.Psychologist.Add(newPsych);

                try
                {
                    db.SaveChanges();

                    //create default calendar times for new psychologist
                    PsychologistController psychController = new PsychologistController(db);
                    int createdCalendars = psychController.createDefaultCalendarsForPsych(newPsych.PsychID);

                    if(createdCalendars < 0)
                    {
                        //could not create calendars
                        return new Psychologist { PsychID = -3 };
                    }


                    return newPsych;
                }
                catch (Exception ex)
                {
                    ex.GetBaseException();
                    return new Psychologist { PsychID = -2 }; //Something went wrong registering Psychologist
                }
            }
            else if (newUser.UserID == 0)
            {
                return new Psychologist { PsychID = 0 };  //email is already taken
            }
            else
            {
                return new Psychologist { PsychID = -1 }; //Something else went wrong with user registration
            }
        }

        //Function for guardian to add a child i.e. use for guardian registering a child
        //pass guardian ID and normal child info for registration
        [HttpPost("addChild")]
        public User addChild(ChildReg info)
        {
            //register the child
            info.childInfo.UserType = "Child";
            info.childInfo.DateRegistered = DateTime.Now;
            info.childInfo.Status = "Active";
            User newChild = register(info.childInfo);

            if(newChild.UserID > 0)
            {
                //create new pair
                Pair newPair = new Pair
                {
                    ChildID = newChild.UserID,
                    ParentID = info.guardianID
                };

                db.Pair.Add(newPair);

                try
                {
                    db.SaveChanges();
                    return newChild;    //everything went well
                }
                catch(Exception ex)
                {
                    ex.GetBaseException();
                    return new User { UserID = -2 }; //failed to create pair
                }
            }
            else if(newChild.UserID == 0)
            {
                return new User { UserID = 0 }; //email already taken
            }
            else
            {
                return new User { UserID = -1 }; //Something else went wrong during child registration
            }
        }


        //Function for resetting a user's password
        [HttpPost("ChangePassword")]
        public User ChangePassword(UpdateUser usr)
        {
            //get the user
            var user = db.User.Where(x => x.UserID.Equals(usr.userID)).FirstOrDefault();

            if(user != null)
            {
                //password matches
                if (user.Password.Equals(Secrecy.HashPassword(user.Spice + usr.oldPassword)))
                {
                    var newSpice = Guid.NewGuid().ToString();

                    user.Spice = newSpice;
                    user.Password = Secrecy.HashPassword(newSpice + usr.newPassword);

                    try
                    {
                        //all is well
                        db.SaveChanges();
                        return user;
                    }
                    catch (Exception ex)
                    {
                        ex.GetBaseException();
                        return new User { UserID = -1};
                    }

                }
                else
                {
                    return new User { UserID = -2 }; //passwords don't match
                }
            }
            else
            {
                return new User { UserID = 0};  //user does not exist
            }

        }

        //Function to update a user's account details -- Used to change fields that require a password
        [HttpPost("UpdateUserDetails")]
        public User UpdateUserDetails(UpdateUser usr)
        {
            //check if an email was provided
            if(usr.newEmail != null)
            {
                //check if the email already exists
                User userEmail = db.User.Where(x => x.Email.Equals(usr.newEmail) && x.UserID != usr.userID).FirstOrDefault();

                if (userEmail != null )
                    return new User { UserID = -3 };  //email is already taken

            }

            // User user = db.User.Where(x => x.UserID.Equals(usr.userID) && x.Password.Equals(Secrecy.HashPassword(x.Spice + usr.oldPassword))).FirstOrDefault();

            User user = (from u in db.User
                         where u.UserID.Equals(usr.userID)
                         select u).FirstOrDefault();

            if(user != null)
            {
                if(user.Password.Equals(Secrecy.HashPassword(user.Spice + usr.oldPassword)))
                {
                    user.Name = usr.newName;
                    user.Email = usr.newEmail;
                    user.DOB = usr.newDOB;
                    user.ProfilePicture = usr.newProfilePicture;
                    user.Grade = usr.grade;

                    try
                    {
                        db.SaveChanges();
                        return new User
                        {
                            UserID = user.UserID,
                            Name = user.Name,
                            Email = user.Email,
                            DOB = user.DOB,
                            UserType = user.UserType,
                            Grade = user.Grade,
                            ProfilePicture = user.ProfilePicture,
                            DateRegistered = user.DateRegistered,
                            Status = user.Status
                        };
                    }
                    catch (Exception ex)
                    {
                        ex.GetBaseException();
                        return new User { UserID = -1 }; //something went wrong with saving the changes
                    }
                }
                else
                {
                    return new User { UserID = -2 }; //incorrect password
                }
               
            }
            else
            {
                return new User { UserID = 0};  //user not found
            }
        }

        //function to update user's account details - no password required
        [HttpPost("UpdateAccount")]
        public User UpdateAccount(UpdateUser usr)
        {
            User user = (from u in db.User
                         where u.UserID.Equals(usr.userID)
                         select u).FirstOrDefault();

            if (user != null)
            {
                user.Name = usr.newName;
                user.DOB = usr.newDOB;
                user.ProfilePicture = usr.newProfilePicture;

                try
                {
                    db.SaveChanges();
                    return new User
                    {
                        UserID = user.UserID,
                        Name = user.Name,
                        Email = user.Email,
                        DOB = user.DOB,
                        UserType = user.UserType,
                        Grade = user.Grade,
                        ProfilePicture = user.ProfilePicture,
                        DateRegistered = user.DateRegistered,
                        Status = user.Status
                    };
                }
                catch (Exception ex)
                {
                    ex.GetBaseException();
                    return new User { UserID = -1 }; //something went wrong with saving the changes
                }

            }
            else
            {
                return new User { UserID = 0 };  //user not found
            }
        }

        //Function for removing a user's account (change status to "Inactive")
        [HttpPost("removeAccount")]
        public int removeAccount(User usr)
        {
            User user = db.User.Where(u => u.UserID.Equals(usr.UserID) && u.Email.Equals(usr.Email)).FirstOrDefault();

            if(user != null)
            {
               
                user.Status = "Inactive";

                try
                {
                    //all is well
                    db.SaveChanges();
                    return 0;
                }
                catch (Exception ex)
                {
                    ex.GetBaseException();
                    return -1; //Failed to change status
                }
            }
            else
            {
                return -2; //user not found
            }
        }

        //Function to reactivate a user's account
        [HttpPost("reactivateAccount")]
        public User reactivateAccount(User usr)
        {
            var user = (from u in db.User
                        where u.Email.Equals(usr.Email)
                        select u).FirstOrDefault();

            if(user != null)
            {
                if (user.Password.Equals(Secrecy.HashPassword(user.Spice + usr.Password)))
                {
                    user.Status = "Active";

                    try
                    {
                        //all is well
                        db.SaveChanges();
                        return new User
                        {
                            UserID = user.UserID,
                            Name = user.Name,
                            Email = user.Email,
                            DOB = user.DOB,
                            UserType = user.UserType,
                            Grade = user.Grade,
                            ProfilePicture = user.ProfilePicture,
                            DateRegistered = user.DateRegistered,
                            Status = user.Status
                        };
                    }
                    catch (Exception ex)
                    {
                        ex.GetBaseException();
                        return new User { UserID = -1 }; //Failed to change status
                    }
                }
                else
                {
                    return new User { UserID = 0, Password = "Incorrect Password" }; //incorrect password
                }
                   

            }
            else
            {
                return new User { UserID = 0 }; //user not found
            }
        }

        //Function to change a counsellor or psychologist's status
        [HttpPost("ChangeUserStatus")]
        public String ChangeUserStatus(UpdateUser usr)
        {
            User user = db.User.Where(x => x.UserID.Equals(usr.userID)).FirstOrDefault();

            if(user != null)
            {
                user.Status = usr.newStatus;

                try
                {
                    //all is well
                    db.SaveChanges();

                    if(user.UserType == "Psychologist")
                    {
                        Psychologist psych = db.Psychologist.Where(x => x.PsychID.Equals(usr.userID)).FirstOrDefault();
                        psych.Status = usr.newStatus;

                        try
                        {
                            //all is well
                            db.SaveChanges();
                            return "Successfully updated counsellor information";
                        }
                        catch (Exception ex)
                        {
                            ex.GetBaseException();
                            return "Failed to change status";
                        }
                    }
                    return "Successfully changed status";
                }
                catch (Exception ex)
                {
                    ex.GetBaseException();
                    return "Failed to change status";
                }
            }
            else
            {
                return "User does not exist";
            }


        }

        /* Fetch Psychologist, given psychID */
        [HttpPost("fetchPsychologist")]
        public Psychologist fetchPsychologist(Psychologist psychologist)
        {
            var psych = db.Psychologist.Where(p => p.PsychID.Equals(psychologist.PsychID)).FirstOrDefault();

            if(psych != null)
            {
                return psych; //succesfully found psychologist
            }
            else
            {
                return new Psychologist { PsychID = -1 }; //could not find psychologist
            }
        }

        /* Fetch counsellor, given counsellorID */
        [HttpPost("fetchCounsellor")]
        public Counsellor fetchCounsellor(Counsellor counsellor)
        {
            var couns = db.Counsellor.Where(c => c.CounsellorID.Equals(counsellor.CounsellorID)).FirstOrDefault();

            if (couns != null)
            {
                return couns; //succesfully found counsellor
            }
            else
            {
                return new Counsellor { CounsellorID = -1 }; //could not find counsellor
            }
        }

        /* Update Counsellor details */
        [HttpPost("updateCounsellorInfo")]
        public String updateCounsellorInfo(Counsellor couns)
        {
            //check if counsellor exists
            var findCouns = db.Counsellor.Where(c => c.CounsellorID.Equals(couns.CounsellorID)).FirstOrDefault();

            if(findCouns != null)
            {
                findCouns.PracticeNum = couns.PracticeNum;
                findCouns.HighestCertificate = couns.HighestCertificate;

                try
                {
                    //all is well
                    db.SaveChanges();
                    return "Successfully updated counsellor information";
                }
                catch (Exception ex)
                {
                    ex.GetBaseException();
                    return "An error occurred. Please try again later";
                }

            }
            else
            {
                return "Counsellor does not exist";
            }
        }

        /* Update Psychologist details */
        [HttpPost("updatePsychologistInfo")]
        public String updatePsychologistInfo(Psychologist psych)
        {
            //check if counsellor exists
            var findPsych = db.Psychologist.Where(p => p.PsychID.Equals(psych.PsychID)).FirstOrDefault();

            if (findPsych != null)
            {
                findPsych.Address = psych.Address;
                findPsych.Qualification = psych.Qualification;
                findPsych.RegNumber = psych.RegNumber;
                findPsych.Description = psych.Description;
                findPsych.Speciality = psych.Speciality;

                try
                {
                    //all is well
                    db.SaveChanges();
                    return "Successfully updated psychologist information";
                }
                catch (Exception ex)
                {
                    ex.GetBaseException();
                    return "An error occurred. Please try again later";
                }

            }
            else
            {
                return "Psychologist does not exist";
            }
        }

        /* Generate 10-character alphanumeric string*/
        [HttpPost("GeneratePairCode")]
        public String generatePairCode(User usr)
        {
            var userGettingCode = db.User.Where(x => x.UserID.Equals(usr.UserID)).FirstOrDefault();

            if(userGettingCode != null)
            {
                
                if (userGettingCode.UserType.Equals("Child"))
                {
                    //check if child is already paired
                    var pair = db.Pair.Where(p => p.ChildID.Equals(userGettingCode.UserID)).FirstOrDefault();

                    if(pair != null)
                    {
                        var parent = getUserById(pair.ParentID);
                        return "You already have a guardian '" + parent.Name + "' linked. To pair with someone else, " + parent.Name + " will need remove this link.";
                    }
                }

                //this is the list of characters that can be chosen
                String src = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
                int length = 10; //pair code must be 10 characters long
                Random rand = new Random();
                var strBuilder = new StringBuilder();

                for (int i = 0; i < length; i++)
                {
                    var character = src[rand.Next(0, src.Length)]; //randomly select a character
                    strBuilder.Append(character); //append character to the string builder
                }

                String code = strBuilder.ToString();

                //get all the users
                var users = (from u in db.User
                             select u);

                //int userCode = usr.UserID;
                //if the generated code is not unique, generate a new one
                foreach (User x in users)
                {

                    if (usr.PairCode != null && usr.PairCode.Equals(code))
                    {
                        generatePairCode(usr);
                    }
                }

                userGettingCode.PairCode = code;
                db.SaveChanges();


                return code;
            }
            else
            {
                return "Unauthorised";  //user does not exist or is not allowed to generate codes
            }
            

        }

        //function to get a user by their id
        [HttpGet("getUserById/{userId}")]
        public User getUserById(int userId)
        {
            return db.User.Where(u => u.UserID.Equals(userId)).FirstOrDefault();

        }

        //given a user id, return the pair id
        [HttpGet("getPairId/{userId}")]
        public int getPairId(int userId)
        {
            User user = getUserById(userId);

            int pairId = 0;
            if(user != null)
            {
                if (user.UserType.Equals("Child"))
                {
                    pairId = (from u in db.Pair
                              where u.ChildID.Equals(userId)
                              select u.PairID).FirstOrDefault();
                }
                else if (user.UserType.Equals("Parent"))
                {
                    pairId = (from u in db.Pair
                              where u.ParentID.Equals(userId)
                              select u.PairID).FirstOrDefault();
                }
            }

            return pairId;      //returns 0 if there is no pair id
        }

        //get a child user from a pairID
        [HttpGet("GetPairChild/{pairId}")]
        public User GetPairChild(int pairId)
        {
            var childId = (from p in db.Pair
                           where p.PairID.Equals(pairId)
                           select p.ChildID).FirstOrDefault();

            User child = new User();
            if(childId != 0)
            {
                child = (from u in db.User
                         where u.UserID.Equals(childId)
                         select u).FirstOrDefault();
            }

            return child;
        }

        //given childId, find the guardian
        [HttpGet("GetGuardian/{childId}")]
        public User GetGuardian(int childId)
        {
            var parentId = (from p in db.Pair
                            where p.ChildID.Equals(childId)
                            select p.ParentID).FirstOrDefault();

            if(parentId != 0)
            {
                return db.User.Where(u => u.UserID == parentId).FirstOrDefault();
            }
            else
            {
                return new User();
            }
        }

        //get a user's pairing code
        [HttpPost("GetPairCode")]
        public String GetPairCode(User usr)
        {
            var child = db.User.Where(u => u.UserID.Equals(usr.UserID)).FirstOrDefault();

            if(child != null)
            {
                return child.PairCode;
            }
            else
            {
                return null;
            }
        }



        //create a new pair record
        [HttpPost("Pair")]
        public String Pair(PairingInfo info)
        {
            //check that the user trying to pair exists
            var usr = db.User.Where(u => u.UserID.Equals(info.userId)).FirstOrDefault();

            if(usr != null)   //valid user
            {
                //find the user with matching pair code
                //var usrToFind = db.User.Where(x => x.PairCode.Equals(info.pairCode)).FirstOrDefault();

                var users = db.User.Where(u => u.PairCode.Equals(info.pairCode)).ToList();
                //Database is case insensitive, need to filter extra to do the case sensitive search
                var usrToFind = users.FirstOrDefault(u => u.PairCode.Equals(info.pairCode));

                if (usrToFind != null)
                {
                    var newPair = new Pair { 
                        ParentID = usr.UserID,
                        ChildID = usrToFind.UserID
                    };

                    db.Pair.Add(newPair);
                    usrToFind.PairCode = null;
                    try
                    {
                        //all is well
                        db.SaveChanges();
                        return "Paired";
                    }
                    catch (Exception ex)
                    {
                        ex.GetBaseException();
                        return "Something went wrong. Please try again";
                    }

                }
                else
                {
                    //incorrect/non-existent pair code
                    return "The pair code is incorrect";
                }

            }
            else
            {
                return null;    //given userId is not found i.e. user does not exist
            }
        }

        [HttpGet("getAllPsychologists")]
        public List<PsychologistInfo> getAllPsychologists()
        {
            List<PsychologistInfo> psychologists = new List<PsychologistInfo>();

            var psychs = (from p in db.Psychologist
                          select p);

            if(psychs.Any())
            {
                foreach(Psychologist p in psychs)
                {
                    var tempPsych = fetchPsychInfo(p.PsychID);

                    if (tempPsych.UserID != 0)
                        psychologists.Add(tempPsych);
                }

                return psychologists;
            }
            else
            {
                return null; //no psychologists found
            }
        }

        //utility method to get all information linked to a psychologist
        [HttpGet("fetchPsychInfo/{psychId}")]
        public PsychologistInfo fetchPsychInfo(int psychId)
        {
            PsychologistInfo psychInfo = new PsychologistInfo();

            //get information from user table
            var personalInfo = db.User.Where(u => u.UserID.Equals(psychId)).FirstOrDefault();
            //get practice details
            var practiceDetails = db.Psychologist.Where(p => p.PsychID.Equals(psychId)).FirstOrDefault();

            if (personalInfo != null && practiceDetails != null)
            {
                //populate personal details
                psychInfo.UserID = personalInfo.UserID;
                psychInfo.Name = personalInfo.Name;
                psychInfo.Email = personalInfo.Email;
                psychInfo.ProfilePicture = personalInfo.ProfilePicture;
                psychInfo.DateRegistered = personalInfo.DateRegistered;
                psychInfo.Status = personalInfo.Status;

                //populate practice details
                psychInfo.PsychID = practiceDetails.PsychID;
                psychInfo.Address = practiceDetails.Address;
                psychInfo.Qualification = practiceDetails.Qualification;
                psychInfo.RegNumber = practiceDetails.RegNumber;
                psychInfo.Description = practiceDetails.Description;
                psychInfo.Speciality = practiceDetails.Speciality;
                
            }
            else
            {
                //psychologist does not exist
                return new PsychologistInfo { UserID = 0 };
            }

            return psychInfo;
        }


        //Function to check if the user is linked to the psychologist
        [HttpPost("isLinked")]
        public int isLinked(UserPsych userPsych)
        {
            //find the pair ID given the userID
            var pairId = (from p in db.Pair
                            where p.ChildID.Equals(userPsych.UserID)
                            select p.PairID).FirstOrDefault();
            //if pair exists
            if(pairId != 0)
            {
                dynamic psychID = (from ps in db.Link
                                   where ps.PairID.Equals(pairId)
                                   select ps.PsychID).FirstOrDefault();

                //check if the psych ID exists, if it does, return it else return 0
                if(psychID == userPsych.PsychID)
                {
                    return psychID;
                }else
                {
                    return 0;
                }
            }else
            {
                //means that the pai is not yet made
                return -1;
            }
        }

        //Function to check if the user is linked to the psychologist
        [HttpGet("isLinkedToParent/{usrID}")]
        public int isLinkedToParent(int usrID)
        {
            //find the pair ID given the userID
            var pairId = (from p in db.Pair
                          where p.ParentID.Equals(usrID)
                          select p.PairID).FirstOrDefault();
            //if pair exists
            if (pairId != 0)
            {
                dynamic psychID = (from ps in db.Link
                                   where ps.PairID.Equals(pairId)
                                   select ps.PsychID).FirstOrDefault();

                //check if the psych ID exists, if it does, return it else return 0
                if (psychID != 0)
                {
                    return psychID;
                }
                else
                {
                    return 0;
                }
            }
            else
            {
                //means that the pai is not yet made
                return -1;
            }
        }

        [HttpGet("getLinkedChildren/{parentID}")]
        public List<int> getLinkedChildren(int parentID)
        {
            //find the pair ID given the parentID
            var pair = (from p in db.Pair
                          select p);

            List<int> listOfChildrenID = new List<int>();
            if(pair.Any())
            {
                foreach (Pair pr in pair)
                {
                    if (pr.ParentID.Equals(parentID))
                    {
                        listOfChildrenID.Add(pr.ChildID);
                    }
                }
                return listOfChildrenID;
            }else
            {
                //means no pair exists
                return null;
            }
            
        }

        //method to get all users
        [HttpGet("getAllUsers")]
        public List<User> getAllUsers()
        {
            List<User> userList = new List<User>();
            var users = (from u in db.User
                         select u);

            if(users.Any())
            {
                foreach(User usr in users)
                {
                    userList.Add(usr);
                }
            }else
            {
                return null;
            }

            return userList;
        }

        //get a child user from a userID, and psychID
        [HttpPost("GetChild")]
        public User GetChild(PsychPatientPair psyPat)
        {
            User child = new User();
            child = (from u in db.User
                    where u.UserID.Equals(psyPat.ChildID)
                    select u).FirstOrDefault();

            if(child != null)
            {
                BookingsController bkgController = new BookingsController(db);

                //check if the child has bookings with the psychologist
                List<Bookings> bookingsWithPsych = bkgController.getUserBookingsWithPsych(psyPat);

                int link = isLinked(new UserPsych { PsychID = psyPat.PsychID, UserID = psyPat.ChildID });

                if (bookingsWithPsych != null || link > 0)
                {
                    return child;
                }
                else
                {
                    return new User { UserID = -1 };    //psychologist is not authorised to see the child's info
                }
            }
            else
            {
                return new User { UserID = -2 };    //child not found
            }

        }

        //function to change a forgotten password
        [HttpPost("ForgotPassword")]
        public string ForgotPassword(User usr)
        {
            var user = db.User.Where(u => u.UserID.Equals(usr.UserID)).FirstOrDefault();

            if (user != null)
            {
                if (user.UserType == "Child")
                {

                    var newSpice = Guid.NewGuid().ToString();

                    user.Spice = newSpice;
                    user.Password = Secrecy.HashPassword(newSpice + "HotdogCrocodileRed");
                }
                else
                {
                    var newSpice = Guid.NewGuid().ToString();

                    user.Spice = newSpice;
                    user.Password = Secrecy.HashPassword(newSpice + "Password");
                }

                try
                {
                    db.SaveChanges();
                    return "Success";
                }
                catch (Exception ex)
                {
                    ex.GetBaseException();
                    return "Error saving";

                }
            }
            else
                return "Could not find user";
        }

        [HttpGet("hasPicturePass/{email}")]
        public Boolean hasPicturePass(string email)
        {
            //get user with given email
            var usr = db.User.Where(u => u.Email.Equals(email)).FirstOrDefault();

            if(usr != null && usr.PicturePass != null)
            {
                return usr.PicturePass.Value;
            }

            return false;
        }

        [HttpGet("getUserAge/{userId}")]
        public int getUserAge(int userId)
        {
            int currentDate = DateTime.Now.Year;
            var user = db.User.Where(u => u.UserID.Equals(userId)).FirstOrDefault();

            if(user != null && user.DOB != null)
            {
                int dob = user.DOB.Value.Year;
                int years = currentDate - dob;

                var birthdayThisYear = user.DOB.Value.AddYears(years);
                return birthdayThisYear > DateTime.Now ? years - 1 : years;
            }

            return 0;
        }

        [HttpGet("getPendingAccounts")]
        public List<User> getPendingAccounts()
        {
            var users = db.User.Where(u => (u.UserType.Equals("Psychologist") || u.UserType.Equals("Counsellor")) && u.Status.Equals("Pending"));
            List<User> pendingAccounts = new List<User>();

            if(users.Any())
            {
                foreach (User usr in users)
                {
                    pendingAccounts.Add(usr);
                }
            }
            else
            {
                return null;
            }

            return pendingAccounts;
        }

        [HttpGet("getRejectedAccounts")]
        public List<User> getRejectedAccounts()
        {
            var users = db.User.Where(u => (u.UserType.Equals("Psychologist") || u.UserType.Equals("Counsellor")) && u.Status.Equals("Rejected"));
            List<User> pendingAccounts = new List<User>();

            if (users.Any())
            {
                foreach (User usr in users)
                {
                    pendingAccounts.Add(usr);
                }
            }
            else
            {
                return null;
            }

            return pendingAccounts;
        }

        [HttpGet("getAllCounsellors")]
        public List<CounsellorInfo> getAllCounsellors()
        {
            List<CounsellorInfo> counsellors = new List<CounsellorInfo>();

            var couns = (from p in db.Counsellor
                          select p);

            if (couns.Any())
            {
                foreach (Counsellor p in couns)
                {
                    var tempCouns = fetchCounsInfo(p.CounsellorID);

                    if (tempCouns.counsellorInfo.CounsellorID != 0)
                        counsellors.Add(tempCouns);
                }

                return counsellors;
            }
            else
            {
                return null; //no psychologists found
            }
        }

        //utility method to get all information linked to a psychologist
        [HttpGet("fetchCounsInfo/{counsID}")]
        private CounsellorInfo fetchCounsInfo(int counsID)
        {
            CounsellorInfo counsellor = new CounsellorInfo();
            counsellor.userInfo = new User();
            counsellor.counsellorInfo = new Counsellor();

            //get information from user table
            var personalInfo = db.User.Where(u => u.UserID.Equals(counsID)).FirstOrDefault();
            //get practice details

            var practiceDetails = db.Counsellor.Where(p => p.CounsellorID.Equals(counsID)).FirstOrDefault();

            if (personalInfo != null && practiceDetails != null)
            {
                counsellor.userInfo.UserID = personalInfo.UserID;
                counsellor.userInfo.Name = personalInfo.Name;
                counsellor.userInfo.Email = personalInfo.Email;
                counsellor.userInfo.ProfilePicture = personalInfo.ProfilePicture;
                counsellor.userInfo.DateRegistered = personalInfo.DateRegistered;
                counsellor.userInfo.Status = personalInfo.Status;

                //populate practice details
                counsellor.counsellorInfo.CounsellorID = practiceDetails.CounsellorID;
                counsellor.counsellorInfo.PracticeNum = practiceDetails.PracticeNum;
                counsellor.counsellorInfo.HighestCertificate = practiceDetails.HighestCertificate;
            }

            return counsellor;
        }

        //count psychologists
        [HttpGet("getNumPsychologists")]
        public int getNumPsychologists()
        {
            return db.User.Where(u => u.UserType.Equals("Psychologist")).Count();
        }

        //count counsellors
        [HttpGet("getNumCounsellors")]
        public int getNumCounsellors()
        {
            return db.User.Where(u => u.UserType.Equals("Counsellor")).Count();
        }

        [HttpGet("getNumGuardians")]
        public int getNumGuardians()
        {
            return db.User.Where(u => u.UserType.Equals("Parent")).Count();
        }

        [HttpGet("getNumChild")]
        public int getNumChild()
        {
            return db.User.Where(u => u.UserType.Equals("Child")).Count();
        }
    }
}
