using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class ChildTestAssigned
    {
        [Key]
        public User Child { get; set; }
        public Boolean Status { get; set; }
    }
}
