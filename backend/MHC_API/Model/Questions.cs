﻿using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class Questions
    {
        [Key]
        public int QuestionID { get; set; }
        public String Question { get; set; }
        public int TestID { get; set; }
        public int? Position { get; set; }
        public String? Status { get; set; }
    }
}
