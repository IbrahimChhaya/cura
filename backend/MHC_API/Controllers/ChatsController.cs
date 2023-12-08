using MHC_API.Data;
using MHC_API.Model;
//using MHC_API.Model.CounsellorChat;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ChatsController : ControllerBase
    {
        private MHCDatabaseDBContext db;

        public ChatsController(MHCDatabaseDBContext db)
        {
            this.db = db;
        }


        /************ Psychologist chat functions - START ***********************************************************************************************************/

        //function to save message - given the message, psychID, userID
        
        [HttpPost("sendMessage")]
        public Chats sendMessage(ChatInfo message)
        {
            //get the link between the psychologist and user
            Link link = getLink(message.PractitionerID, message.UserID);

            if(link != null && link.LinkID > 0)
            {
                Chats newChat = new Chats
                {
                    LinkID = link.LinkID,
                    Message = message.MessageContent,
                    Date = DateTime.Now,
                    Read = false,
                    SenderID = message.SenderID
                };
                db.Chats.Add(newChat);

                try
                {
                    db.SaveChanges();
                    return newChat; //successfully saved
                }
                catch(Exception ex)
                {
                    ex.GetBaseException();
                    return new Chats { MessageID = -1}; //failed to save message
                }
            }
            else
            {
                return new Chats { MessageID = -2}; //no link to psych
            }
        }

        //utility function: get a link id, given a psych ID and user ID
        private Link getLink(int psychId, int userId)
        {
            //check that valid psychId was given
            var psych = db.Psychologist.Where(p => p.PsychID.Equals(psychId)).FirstOrDefault();

            if(psych != null)
            {
                //get user's pair Id
                UserController usrController = new UserController(db);
                int pairId = usrController.getPairId(userId);

                if (pairId > 0)
                {
                    //find the entry in the link table that has the matching pair ID and psych ID
                    var link = db.Link.Where(l => l.PsychID.Equals(psychId) && l.PairID.Equals(pairId)).FirstOrDefault();

                    return link; //will be null if no link was found
                }
                else
                {
                    return new Link { LinkID = -1 }; //no pair found for the given user
                }
            }
            else
            {
                return null; // invalid psych ID passed
            }

            
        }

        //function to get all a user's messages - given user ID and psych ID
        [HttpPost("getChatMessages")]
        public List<Chats> getChatMessages(PsychPatientPair psyPat)
        {
            List<Chats> chats = new List<Chats>();

            //get link ID
            var link = getLink(psyPat.PsychID, psyPat.ChildID);

            if (link != null && link.LinkID > 0)
            {
                chats = db.Chats.Where(c => c.LinkID.Equals(link.LinkID)).ToList();
                chats.Sort((x, y) => DateTime.Compare(x.Date, y.Date));
            }

            return chats;
        }

        //function to get psychList view
        [HttpGet("getChildPsychologists/{childID}")]
        public List<PsychChatListItem> getChildPsychologists(int childID)
        {
            //get the child's pair id
            UserController usrController = new UserController(db);
            int pairId = usrController.getPairId(childID);

            if (pairId != 0)
            {
                //get all the links for the pair id found
                var allPsychs = (from l in db.Link
                                 join p in db.User on l.PsychID equals p.UserID
                                 where l.PairID.Equals(pairId)
                                 select new PsychChatListItem { psychID = p.UserID, psychName = p.Name, profilePicture = p.ProfilePicture }).ToList();

                if (allPsychs.Any())
                {
                    foreach(PsychChatListItem item in allPsychs)
                    {
                        PsychPatientPair tempPair = new PsychPatientPair { PsychID = item.psychID, ChildID = childID };
                        var chats = getChatMessages(tempPair);

                        if (chats.Any())
                            item.lastMessage = chats[chats.Count() - 1];
                        else
                            item.lastMessage = new Chats { Message = " "};
                    }

                    return allPsychs;
                }
                else
                {
                    return null;    //no messages sent yet
                }
            }
            else
                return null;    //no pair id = no messages


        }


        //function to mark message as read
        [HttpPost("markPsychChatMessagesAsRead")]
        public List<PsychHomeChatList> markPsychChatMessagesAsRead(Chats chatInfo)
        {
            //get all messages with the given link ID sent by the other person i.e. where message.SenderID != chatInfo.SenderID
            var allReceivedMessages = (from c in db.Chats
                                       where c.LinkID.Equals(chatInfo.LinkID) && c.SenderID != chatInfo.SenderID
                                       select c);

            if (allReceivedMessages.Any())
            {
                foreach(Chats chat in allReceivedMessages)
                {
                    chat.Read = true;
                }

                try
                {
                    db.SaveChanges();
                    return getPsychHomeChatList(chatInfo.SenderID);
                }
                catch(Exception ex)
                {
                    ex.GetBaseException();
                    return null;
                }
            }

            return new List<PsychHomeChatList>();
        }


        //function to get psych home list
        [HttpGet("getPsychHomeChatList/{psychID}")]
        public List<PsychHomeChatList> getPsychHomeChatList(int psychID)
        {
            List<PsychHomeChatList> chatList = new List<PsychHomeChatList>();

            //get list of links
            var psychLinks = db.Link.Where(l => l.PsychID.Equals(psychID));

            if (psychLinks.Any())
            {
                foreach (Link l in psychLinks)
                {
                    PsychHomeChatList temp = new PsychHomeChatList();

                    //get chats for given link
                    var messages = db.Chats.Where(m => m.LinkID.Equals(l.LinkID)).ToList();

                    if (messages.Any())
                    {
                        var child = getChildIDFromLink(l.LinkID);

                        if (child != null)
                        {
                            temp.chatterPerson = child;
                            temp.lastMessage = messages[messages.Count() - 1];
                            temp.LinkID = l.LinkID;
                            temp.nUnreadMessages = getNumUnreadMessages(psychID, l.LinkID);

                            chatList.Add(temp);
                        }
                        else
                            continue;
                    }
                    else
                        continue;    //psych has no messages for current link
                }

                chatList = chatList.OrderByDescending(item => item.lastMessage.Date).ToList();
                return chatList;
            }
            else
                return null;    //psych has no patients
        }

        //utility function: get child from link ID
        private User getChildIDFromLink(int linkID)
        {
            var pairId = db.Link.Where(l => l.LinkID == linkID).Select(l => l.PairID).FirstOrDefault();

            if (pairId != 0)
            {
                UserController usrController = new UserController(db);
                User child = usrController.GetPairChild(pairId);

                return createPublicKnowledgeUser(child);
            }
            else
                return null;
        }

        //utility function: get number of unread messages i.e. m.senderID != senderId
        private int getNumUnreadMessages(int senderID, int linkID)
        {
            var messages = (from c in db.Chats
                            where c.LinkID.Equals(linkID) && c.SenderID != senderID && c.Read == false
                            select c);

            return messages.Count();

        }

        [HttpGet("getPsychChatList/{psychID}")]
        public List<PsychHomeChatList> getPsychChatList(int psychID)
        {
            //get all chats
            var nonEmptyChats = getPsychHomeChatList(psychID);

            if (nonEmptyChats != null)
            {
                PatientController pc = new PatientController(db);
                var allPsychPatients = pc.GetPsychologistPatients(psychID);

                if (allPsychPatients != null)
                {
                    var emptyChats = allPsychPatients.Where((patient) => !nonEmptyChats.Any((item) => patient.UserID == item.chatterPerson.UserID));

                    foreach (var patient in emptyChats)
                    {
                        //find the child's link ID 
                        var link = (from l in db.Link
                                    join p in db.Pair on l.PairID equals p.PairID
                                    where p.ChildID.Equals(patient.UserID)
                                    select l).FirstOrDefault();

                        if(link != null)
                        {
                            PsychHomeChatList temp = new PsychHomeChatList
                            {
                                chatterPerson = patient,
                                LinkID = link.LinkID
                            };

                            nonEmptyChats.Add(temp);
                        }
                    }

                    return nonEmptyChats;
                }
                else
                    return nonEmptyChats;
            }

            return nonEmptyChats;
        }

        /************ Psychologist chat functions - END ***********************************************************************************************************/



        /************ Counsellor chat functions - START ***********************************************************************************************************/

        /// <summary>
        /// Function for sending/saving a counsellor chat message
        /// </summary>
        /// <param name="message">
        /// provide the message content, senderID, CounsellorChatID(optional)
        /// </param>
        /// <returns> message just saved </returns>
        [HttpPost("saveCounsellorChatMessage")]
        public CounsellorChatMessages saveCounsellorChatMessage(CounsellorChatMessageInfo message)
        {
            CounsellorChatMessages newMessage = new CounsellorChatMessages
            {
                CounsellorChatID = message.CounsellorChatID,
                Date = DateTime.Now,
                SenderID = message.SenderID,
                Message = message.Message,
                Read = false

            };

            if (newMessage.CounsellorChatID == 0)
            {
                //create a new link and then save the message
                CounsellorChat newChatLink = initiateCounsellorChat(message);
               
                if(newChatLink.CounsellorChatID != -1)
                   newMessage.CounsellorChatID = newChatLink.CounsellorChatID;
                else
                    return new CounsellorChatMessages { ChatID = -1 };      //failed to save new link
            }

            db.CounsellorChatMessages.Add(newMessage);
            

            try
            {
                db.SaveChanges();
                return newMessage;
            }
            catch(Exception ex)
            {
                ex.GetBaseException();
                return new CounsellorChatMessages { ChatID = -2 };  //failed to save message
            }
        }

        /**function to fetch all chat messages between a child and a counsellor
         Params: CounsellorID and ChildID
         */
        [HttpPost("getCounsellingChatMessages")]
        public List<CounsellorChatMessages> getCounsellingChatMessages(CounsellorChat chatParties)
        {
            //check that valid IDs were provided
            var counsellor = db.Counsellor.Where(c => c.CounsellorID.Equals(chatParties.CounsellorID)).FirstOrDefault();

            var child = db.User.Where(u => u.UserID.Equals(chatParties.ChildID)).FirstOrDefault();

            if(counsellor != null && child != null)
            {
                //get chat ID
                int chatID = getCounsellorChatID(chatParties.CounsellorID, chatParties.ChildID);

                if(chatID != 0)
                {
                    //get all counsellor's chat messages
                    var allCounsellorChats = db.CounsellorChatMessages.Where(cc => cc.CounsellorChatID.Equals(chatID)).ToList();

                    if (allCounsellorChats.Any())
                    {
                        allCounsellorChats.Sort((x, y) => DateTime.Compare(x.Date, y.Date)); //sort messages in ascending date order
                        return allCounsellorChats;
                    }
                    else
                    {
                        //no counsellor chats found
                        return null;
                    }
                }
                else
                {
                    //parties haven't chatted yet
                    return null;
                }
            }
            else
            {
                //either incorrect counsellor ID or child ID  was passed
                return null;
            }

        }

        //utility function: get the chat parties' Chat ID
        [HttpPost("getCounsellorChatID")]
        public int getCounsellorChatID(int counsellorId, int childId)
        {
            var chatID = (from c in db.CounsellorChat
                          where c.CounsellorID.Equals(counsellorId) && c.ChildID.Equals(childId)
                          select c.CounsellorChatID).FirstOrDefault();

            if (chatID > 0)
                return chatID;
            else
                return 0; //could not find the specified chat
        }

        /*
         Function to get list of chats for counsellor chat list
         Params: Counsellor ID
         Return: Object = {ChatterPerson, last message, no. unread messages with this person, ChatFocus}
         */
        [HttpGet("getCounsellorChatList/{counsellorID}")]
        public List<CounsellorChatListItem> getCounsellorChatList(int counsellorID)
        {
            List<CounsellorChatListItem> counsellorChatList = new List<CounsellorChatListItem>();

            //check that counsellor with the given ID exists
            var counsellor = db.Counsellor.Where(c => c.CounsellorID.Equals(counsellorID)).FirstOrDefault();

            if(counsellor != null)
            {
                //get all counsellor's Chats
                var allCounsellorsChats = db.CounsellorChat.Where(cc => cc.CounsellorID.Equals(counsellor.CounsellorID));

                if(allCounsellorsChats.Any())
                {
                    UserController usrController = new UserController(db);
                    foreach(CounsellorChat chat in allCounsellorsChats)
                    {
                        User child = usrController.getUserById(chat.ChildID);

                        if(child != null)
                        {
                            //get CounsellorChatID
                            int chatID = getCounsellorChatID(counsellor.CounsellorID, child.UserID);

                            User publicUser = createPublicKnowledgeUser(child);
                            var numUnreadMessages = getNumUnreadMessages(child.UserID, counsellor.CounsellorID, child.UserID);
                            var lastMessage = getLastMessage(counsellor.CounsellorID, child.UserID);
                            RHubProblems chatFocus = null;
                            if (chat.Focus != null)
                                chatFocus = getChatFocusPoint((int)chat.Focus);

                            if(lastMessage != null)
                            {
                                CounsellorChatListItem tempListItem = new CounsellorChatListItem
                                {
                                    ChatterPerson = publicUser,
                                    LastMessage = lastMessage,
                                    nUnreadMessages = numUnreadMessages,
                                    ChatFocus = chatFocus,
                                    CounsellorChatID = chatID
                                };

                                counsellorChatList.Add(tempListItem);
                            }
                        }
                    }

                    //counsellorChatList.Sort((x, y) => x.nUnreadMessages.CompareTo(y.nUnreadMessages));
                    //counsellorChatList.Sort((x, y) => x.LastMessage.Date.CompareTo(y.LastMessage.Date));

                    counsellorChatList = counsellorChatList.OrderByDescending(item => item.LastMessage.Date).ThenByDescending(item => item.nUnreadMessages).ToList();
                    return counsellorChatList;
                }
                else
                {
                    //counsellor has not yet chatted
                    return null;
                }
            }
            else
            {
                //invalid counsellor ID passed
                return null;
            }
        }

        //Utility method: create user without sensitive info included
        private User createPublicKnowledgeUser(User usr)
        {
            User publicUser = new User
            {
                UserID = usr.UserID,
                Name = usr.Name,
                Email = usr.Email,
                DOB = usr.DOB,
                UserType = usr.UserType,
                Grade = usr.Grade,
                ProfilePicture = usr.ProfilePicture,
                DateRegistered = usr.DateRegistered
            };

            return publicUser;
        }

        //Utility function: used to get the last message exchanged between a counsellor and the given child
        private CounsellorChatMessages getLastMessage(int counsID, int childID)
        {
            CounsellorChat chatParties = new CounsellorChat { CounsellorID = counsID, ChildID = childID };
            var allExchanges = getCounsellingChatMessages(chatParties);

            if(allExchanges != null)
            {
                return allExchanges[allExchanges.Count() - 1];
            }
            else
            {
                return null;    //no messages exchanges between the two parties
            }
        }

        /*
         * Utility function to get counsellor's number of unread messages from specific user
         * Params: SenderID, CounsellorID, ChildID
         * Return: int
         */
        private int getNumUnreadMessages(int senderID, int counsellorId, int childId)
        {
            CounsellorChat chatParties = new CounsellorChat { CounsellorID = counsellorId, ChildID = childId };
            List<CounsellorChatMessages> allExchanges = getCounsellingChatMessages(chatParties);

            if (allExchanges != null)
            {
                List<CounsellorChatMessages> unreadByCounsellor = allExchanges.FindAll(
                    delegate (CounsellorChatMessages chat)
                    {
                        return chat.SenderID == senderID && chat.Read == false;
                    }
                );

                return unreadByCounsellor.Count();
            }
            else
                return 0;   //no chats exchanged between the given parties
        }

        //Utility function: returns chat focus point 
        private RHubProblems getChatFocusPoint(int fpID)
        {
            return db.RHubProblems.Where(fp => fp.ProblemID.Equals(fpID)).FirstOrDefault();

        }

        /*
         * Function to assign a focus point to a chat
         * Params: CounsellorID, ChildID, FocusPointID
         */
        [HttpPost("assignChatFocus")]
        public int assignChatFocus(CounsellorChat info)
        {
            //check that valid focus point ID was passed
            var focusPoint = db.RHubProblems.Where(fp => fp.ProblemID.Equals(info.Focus)).FirstOrDefault();

            if(focusPoint != null)
            {
                //find the relevant chat 
                var chat = (from c in db.CounsellorChat
                            where c.CounsellorID.Equals(info.CounsellorID) && c.ChildID.Equals(info.ChildID)
                            select c).FirstOrDefault();

                if(chat != null)
                {
                    chat.Focus = focusPoint.ProblemID;

                    try
                    {
                        db.SaveChanges();
                        return 0;           //everything saved as intended
                    }
                    catch(Exception ex)
                    {
                        ex.GetBaseException();
                        return -1;              //trouble saving change to chat
                    }
                }
                else
                {
                    return -3; //could not find chat with the given counsellor and child IDs
                }
            }
            else
            {
                return -2; //invalid focus point ID passed
            }
        }

        //function to fetch the counsellor that a child is speaking to
        [HttpGet("fetchChildsCounsellor/{childID}")]
        public User fetchChildsCounsellor(int childID)
        {
            Boolean hasCounsellor = alreadyHasCounsellor(childID);

            if (hasCounsellor)
            {
                //find the counsellorID linked to the child
                var counsellorID = db.CounsellorChat.Where(c => c.ChildID.Equals(childID)).Select(c => new { counsellorID = c.CounsellorID }).FirstOrDefault();

                if (counsellorID != null)
                {
                    return getCounsellorInfoByID(counsellorID.counsellorID);
                }
                else
                    return null;
            }
            else
            {
                User newCounsellor = getRandomCounsellor();
                return newCounsellor;
            }
                
        }


        //function to check if a child already started a chat with a counsellor
        [HttpGet("alreadyHasCounsellor/{childID}")]
        public Boolean alreadyHasCounsellor(int childID)
        {
            var found = db.CounsellorChat.Where(cc => cc.ChildID.Equals(childID)).FirstOrDefault();

            return (found != null);
        }

        //function to get a randomly selected counsellor
        [HttpGet("getRandomCounsellor")]
        public User getRandomCounsellor()
        {
            //get list of counsellors
            var counsellorList = getActiveCounsellors();

            Random rand = new Random(DateTime.Now.Millisecond);
            int randomNum = rand.Next(0, counsellorList.Count());

            //select random consellor
            var selectedCounsellor = counsellorList[randomNum];

            var publicUserInfo = createPublicKnowledgeUser(selectedCounsellor.userInfo);
            selectedCounsellor.userInfo = publicUserInfo;

            return selectedCounsellor.userInfo;
        }

        //utility function to get a list of all counsellors
        private List<CounsellorInfo> getAllCounsellors()
        {
            var allInfo = (from u in db.User
                            join c in db.Counsellor on u.UserID equals c.CounsellorID
                            where u.UserType == "Counsellor"
                            select new CounsellorInfo { userInfo = u, counsellorInfo = c }).ToList();

             return allInfo;
        }

        //utility function to get a list of all counsellors
        private List<CounsellorInfo> getActiveCounsellors()
        {
            var allInfo = (from u in db.User
                           join c in db.Counsellor on u.UserID equals c.CounsellorID
                           where u.UserType == "Counsellor" && u.Status == "Active"
                           select new CounsellorInfo { userInfo = u, counsellorInfo = c }).ToList();

            return allInfo;
        }

        //utility function: get a specific counsellor's info
        private User getCounsellorInfoByID(int counsellorID)
        {
            var allCounsellors = getAllCounsellors();

            if (allCounsellors.Any())
            {
                var counsellor = allCounsellors.Find(c => c.userInfo.UserID == counsellorID);

                if (counsellor != null)
                {
                    //only return non-sensitive counsellor info
                    counsellor.userInfo = createPublicKnowledgeUser(counsellor.userInfo);

                    return counsellor.userInfo;
                }
                else
                    return null;
            }
            else
                return null;
        }

        //utility function to initiate a chat with a counsellor
        private CounsellorChat initiateCounsellorChat(CounsellorChatMessageInfo messageInfo)
        {
            //create chat link
            CounsellorChat chatLink = new CounsellorChat
            {
                CounsellorID = messageInfo.CounsellorID,
                ChildID = messageInfo.ChildID
            };

            db.CounsellorChat.Add(chatLink);

            try
            {
                db.SaveChanges();
                return chatLink;
            }
            catch(Exception ex)
            {
                ex.GetBaseException();
                return new CounsellorChat { CounsellorChatID = -1 }; //could not save the link
            }
        }


        /// <summary>
        /// Function to mark last few messages as read
        /// </summary>
        /// <param name="messageInfo">
        ///     SenderID, CounsellorChatID
        /// </param>
        /// <returns></returns>
        [HttpPost("markMessagesAsRead")]
        public List<CounsellorChatListItem> markMessagesAsRead(CounsellorChatMessageInfo messageInfo)
        {
            //get all the messages sent by the opposite chat party i.e. where m.SenderID != messageInfo.SenderID
            var allMessages = (from m in db.CounsellorChatMessages
                               where m.CounsellorChatID.Equals(messageInfo.CounsellorChatID) && m.SenderID != messageInfo.SenderID
                               select m);

            if (allMessages.Any())
            {
                foreach(CounsellorChatMessages message in allMessages)
                {
                    message.Read = true;
                }

                try
                {
                    db.SaveChanges();
                    return getCounsellorChatList(messageInfo.CounsellorID);    //all unread messages marked as read 
                }
                catch(Exception ex)
                {
                    ex.GetBaseException();
                    return null;    //could not save changes
                }
            }
            else
            {
                return new List<CounsellorChatListItem>();      //no messages found
            }
        }

        [HttpPost("markMobileMessagesAsRead")]
        public int markMobileMessagesAsRead(CounsellorChatMessageInfo messageInfo)
        {
            var editedChatList = markMessagesAsRead(messageInfo);

            if (editedChatList != null && editedChatList.Any())
            {
                return 0;
            }
            else
                return -1;      //couldn't save the changes, or no messages were found
        }

        //utility function: get all counsellor's messages
        private List<CounsellorChatMessages> getAllCounsellorMessages(int counsellorId)
        {
            //get all counsellor chat messages
            var allCounsellorMessages = (from cc in db.CounsellorChat
                                         join cm in db.CounsellorChatMessages on cc.CounsellorChatID equals cm.CounsellorChatID
                                         where cc.CounsellorID.Equals(counsellorId)
                                         select new CounsellorChatMessages { 
                                             ChatID = cm.ChatID, 
                                             CounsellorChatID = cm.CounsellorChatID, 
                                             Date = cm.Date, 
                                             SenderID = cm.SenderID,
                                             Message = cm.Message,
                                             Read = cm.Read
                                         }).ToList();

            return allCounsellorMessages;
        }

        //function to get the initial response time between two messages
        private TimeSpan getInitialResponseTime(DateTime firstMessageDate, DateTime firstResponseDate)
        {
            TimeSpan dateDiff = firstResponseDate - firstMessageDate;
            return dateDiff;
        }


        /// <summary>
        /// Function to get a counsellor's average initial response time
        /// </summary>
        /// <param name="counsellorID"></param>
        /// <returns> String representation of average in minutes </returns>
        [HttpGet("getCounsellorAverageResponseTime/{counsellorID}")]
        public TimeSpan getCounsellorAverageResponseTime(int counsellorID)
        {
            List<TimeSpan> responseTimes = new List<TimeSpan>();

            //get counsellor chatList
            var chatList = getCounsellorChatList(counsellorID);

            //get CounsellorChatIDs from list
            var chatIDs = chatList.Select(c => c.CounsellorChatID);

            if (chatList != null)
            {
                foreach(CounsellorChatListItem item in chatList)
                {
                    //get messages for this chat id
                    var messages = db.CounsellorChatMessages.Where(c => c.CounsellorChatID.Equals(item.CounsellorChatID)).ToList();

                    if (messages.Any())
                    {
                        //find initial message
                        CounsellorChatMessages initialMessage = messages[0];

                        //find initial response message
                        CounsellorChatMessages initialResponse = null;
                        DateTime responseDate = DateTime.Now;
                        TimeSpan responseTime = new TimeSpan();


                        if (messages.Count() > 1)
                        {
                            initialResponse = messages.Where(m => m.SenderID.Equals(counsellorID)).FirstOrDefault();
                        }   

                        if(initialResponse != null)
                        {
                            responseDate = initialResponse.Date;
                        }

                        //get response time
                        responseTime = getInitialResponseTime(initialMessage.Date, responseDate);
                        responseTimes.Add(responseTime);

                    }

                }

                double averageTicks = responseTimes.Average(t => t.Ticks);
                long longAverageTicks = Convert.ToInt64(averageTicks);

                return new TimeSpan(longAverageTicks);

            }
            else
            {
                //no chats with counsellor
                return new TimeSpan();
            }

        }


        /************ Counsellor chat functions - END ***********************************************************************************************************/

    }
}
