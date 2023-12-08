using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace MHC_API.Model
{
    public class Notes
    {
        [Key]
        public int NoteID { get; set; }

        [Column(TypeName = "varchar(MAX)")]
        public String Feedback { get; set; }
        public DateTime DateCreated { get; set; }
        public String Type { get; set; }
        public int PsychID { get; set; }
        public int PairID { get; set; }
    }
}
