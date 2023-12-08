using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class ChildTestQuestion
    {
        [Key]
        public int TestID { get; set; }
        public int QuestionID { get; set; }
        public int ChildID { get; set; }
    }
}
