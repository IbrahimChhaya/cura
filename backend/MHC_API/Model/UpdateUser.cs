using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class UpdateUser
    {
        public int userID { get; set; }
        public String oldPassword { get; set; }
        public String newPassword { get; set; }
        public String newStatus { get; set; }

        public String newName { get; set; }
        public String oldEmail { get; set; }
        public String newEmail { get; set; }
        public DateTime newDOB { get; set; }
        public String newProfilePicture { get; set; }
        public String grade { get; set; }

        //Is this needed?
        // public String UserType { get; set; }

    }
}
