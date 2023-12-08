using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class ToDoItem
    {
        [Key]
        public int ItemID { get; set; }
        public String Item { get; set; }
        public Boolean Status { get; set; }
        public int ToDoID { get; set; }
    }
}
