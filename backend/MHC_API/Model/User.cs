using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class User
    {
        [Key]
        public int UserID { get; set; }
        [Column(TypeName="varchar(MAX)")]
        public String Name { get; set; }
        public String Email { get; set; }
        public String Password { get; set; }

        [Column(TypeName ="datetime")]
        public Nullable<DateTime> DOB { get; set; }
        public String UserType { get; set; }

        public String Grade { get; set; }
        public String ProfilePicture { get; set; }
        public DateTime DateRegistered { get; set; }
        public String Status { get; set; }

        public String PairCode { get; set; }
        public String Spice { get; set; }

        public Boolean? PicturePass { get; set; }

    }
}
