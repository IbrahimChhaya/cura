using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using MHC_API.Data;
using MHC_API.Model;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace MHC_API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class HolidaysController : ControllerBase
    {
        private MHCDatabaseDBContext db;

        public HolidaysController(MHCDatabaseDBContext context)
        {
            db = context;
        }

        //Function to get a list of all holidays
        [HttpGet("getAllHolidays")]
        public List<Holidays> getAllHolidays()
        {
            List<Holidays> publicHolidays = new List<Holidays>();

            var holidays = (from h in db.Holidays
                            select h);

            foreach(Holidays h in holidays)
            {
                publicHolidays.Add(h);
            }

            return publicHolidays;
        }
    }
}
