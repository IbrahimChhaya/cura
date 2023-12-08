using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using MHC_API.Model;


namespace MHC_API.Data
{
    public class MHCDatabaseDBContext : DbContext
    {
        public MHCDatabaseDBContext(DbContextOptions<MHCDatabaseDBContext> options) : base(options)
        {

        }

        public DbSet<User> User { get; set; }
        public DbSet<Psychologist> Psychologist { get; set; }
        public DbSet<Pair> Pair { get; set; }
        public DbSet<Link> Link { get; set; }
        public DbSet<PsychCalendar> PsychCalendar { get; set; }
        public DbSet<Bookings> Bookings { get; set; }
        public DbSet<Counsellor> Counsellor {get; set;}
        public DbSet<Holidays> Holidays { get; set; }
        public DbSet<Groups> Groups { get; set; }
        public DbSet<Notes> Notes { get; set; }
        public DbSet<Recommendations> Recommendations { get; set; }
        public DbSet<RecLink> RecLink { get; set; }
        public DbSet<Issues> Issues { get; set; }
        public DbSet<ToDo> ToDo { get; set; }
        public DbSet<ToDoItem> ToDoItem { get; set; }
        public DbSet<HappyPoints> HappyPoints { get; set; }
        public DbSet<ChildActivity> ChildActivity { get; set; }
        public DbSet<Tests> Tests { get; set; }
        public DbSet<ChildTest> ChildTest { get; set; }
        public DbSet<Questions> Questions { get; set; }
        public DbSet<Answers> Answers { get; set; }
        public DbSet<ChildAnswers> ChildAnswers { get; set; }
        public DbSet<Locations> Locations { get; set; }
        public DbSet<RHubProblems> RHubProblems { get; set; }
        public DbSet<Images> Images { get; set; }
        public DbSet<RHubUserBridge> RHubUserBridge { get; set; }
        public DbSet<KeywordsScript> KeywordsScript { get; set; }
        public DbSet<Chats> Chats { get; set; }
        public DbSet<CounsellorChat> CounsellorChat { get; set; }
        public DbSet<CounsellorChatMessages> CounsellorChatMessages { get; set; }


    }
}
