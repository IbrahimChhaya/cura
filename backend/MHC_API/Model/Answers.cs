using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class Answers
    {
        [Key]
        public int AnswerID { get; set; }
        public String Answer { get; set; }
        public int AnswerValue { get; set; }
        public int QuestionID { get; set; }
        public int? Position { get; set; }
    }
}
