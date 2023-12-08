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
    public class NotesController : ControllerBase
    {
        private MHCDatabaseDBContext db;

        public NotesController(MHCDatabaseDBContext db)
        {
            this.db = db;
        }

        //method to get note
        [HttpGet("getNote/{noteID}")]
        public Notes getNote(int noteID)
        {
            //Notes Note = new Notes();
            var note = (from n in db.Notes
                        where n.NoteID.Equals(noteID)
                        select n).FirstOrDefault();
            if(note != null)
            {
                return note;
            }
            else
            {
                return null;
            }
        }

        //method to fetch all notes linked to a child
        [HttpPost("fetchAllChildsNotes")]
        public List<Notes> fetchAllChildsNotes(PsychPatientPair psyPat)
        {
            List<Notes> childNotes = new List<Notes>();

            //check if child exists
            var child = db.User.Where(u => u.UserID.Equals(psyPat.ChildID)).FirstOrDefault();

            if(child != null)
            {
                //get the child's pair id
                var pairId = getChildPairId(child.UserID);

                if(pairId != 0)
                {
                    //fetch a list of notes with the pair ID id
                    var idNotes = (from n in db.Notes
                                    where n.PairID.Equals(pairId) && n.PsychID.Equals(psyPat.PsychID)
                                    select n);

                    if(idNotes.Any())
                    {
                        //add each note to the list
                        foreach(Notes note in idNotes)
                        {
                            childNotes.Add(note);
                        }
                    }

                    return childNotes;
                }
                else
                {
                    //child not paired, so can't have notes
                    return null;
                }
            }
            else
            {
                //child not found
                return null;
            }
        }

        /*method to save a note made for a child, 
         * given the child note object (same as note but takes in child ID instead of pair id*/
        [HttpPost("saveNote")]
        public Notes saveNote(ChildNote note)
        {
            //check that valid child id was given
            var child = db.User.Where(u => u.UserID.Equals(note.ChildID)).FirstOrDefault();

            if(child != null)
            {
                //get child's pairId
                var pairId = getChildPairId(child.UserID);

                if(pairId != 0)
                {
                    //create note
                    Notes newNote = new Notes
                    {
                        Feedback = note.Feedback,
                        DateCreated = DateTime.Now,
                        Type = note.Type,
                        PsychID = note.PsychID,
                        PairID = pairId
                    };

                    db.Notes.Add(newNote);

                    try
                    {
                        db.SaveChanges();

                        var savedToOtherTable = saveNoteToOtherTable(newNote.Type, note.OtherTableID, newNote.NoteID);
                        
                        if (savedToOtherTable == 0)
                            return newNote;
                        else
                            return new Notes { NoteID = -2 }; //falied to save note id to corresponding table

                    }
                    catch (Exception ex)
                    {
                        ex.GetBaseException();
                        return null;
                    }
                }
                else
                {
                    //no pair
                    return new Notes { NoteID = -1 };
                }
            }
            else
            {
                //child does not exist
                return new Notes { NoteID = 0 };
            }
        }

        //utility method: add note to relevant corresponding table
        private int saveNoteToOtherTable(String type, int id, int noteId)
        {
            if (type.Equals("Appointment"))
            {
                //find booking
                var booking = db.Bookings.Where(b => b.BookingID.Equals(id)).FirstOrDefault();

                if (booking != null)
                    booking.NoteID = noteId;
            }
            else if (type.Equals("Test"))
            {
                var test = db.ChildTest.Where(t => t.ChildTestID.Equals(id)).FirstOrDefault();

                if (test != null)
                    test.NoteID = noteId;
            }
            else if (type.Equals("Activity"))
            {
                var activity = db.ChildActivity.Where(t => t.ActivityID.Equals(id)).FirstOrDefault();

                if (activity != null)
                    activity.NoteID = noteId;
            }

            try
            {
                db.SaveChanges();
                return 0;
            }
            catch (Exception ex)
            {
                ex.GetBaseException();
                return -1;
            }

        }

        //utility method: get child's pairId
        private int getChildPairId(int childID)
        {
            var pairId = (from p in db.Pair
                          where p.ChildID.Equals(childID)
                          select p.PairID).FirstOrDefault();

            return pairId;
        }


        //method to get all the feedback for the child based on child ID
        [HttpGet("fetchAllChildsNotesByID/{childID}")]
        public List<Notes> fetchAllChildsNotesByID(int childID)
        {
            List<Notes> childNotes = new List<Notes>();

            //check if child exists
            var child = db.User.Where(u => u.UserID.Equals(childID)).FirstOrDefault();

            if (child != null)
            {
                //get the child's pair id
                var pairId = getChildPairId(child.UserID);

                if (pairId != 0)
                {
                    //fetch a list of notes with the pair ID id
                    var idNotes = (from n in db.Notes
                                   where n.PairID.Equals(pairId)
                                   select n);

                    if (idNotes.Any())
                    {
                        //add each note to the list
                        foreach (Notes note in idNotes)
                        {
                            childNotes.Add(note);
                        }
                    }

                    return childNotes;
                }
                else
                {
                    //child not paired, so can't have notes
                    return null;
                }
            }
            else
            {
                //child not found
                return null;
            }
        }

    }
}
