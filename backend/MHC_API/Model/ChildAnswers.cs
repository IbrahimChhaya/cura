using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class ChildAnswers
    {
        [Key]
        public int ChildAnswerID { get; set; }
        public int ChildTestID { get; set; }
        public int AnswerID { get; set; }
    }
   
}
