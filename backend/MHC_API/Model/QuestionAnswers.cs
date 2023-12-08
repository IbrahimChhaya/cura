using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class QuestionAnswers
    {
        [Key]
        public Questions Question { get; set; }
        public List<Answers> Answers { get; set; }
    }
}
