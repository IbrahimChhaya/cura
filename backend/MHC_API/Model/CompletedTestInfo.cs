﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class CompletedTestInfo
    {
        public int ChildTestID { get; set; }
        public String testName { get; set; }
        public DateTime dateCompleted { get; set; }
    }
}
