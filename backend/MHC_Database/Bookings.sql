CREATE TABLE [dbo].[Bookings]
(
	[BookingID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY, 
    [Date] DATE NOT NULL, 
    [Time] TIME NOT NULL, 
    [Cancelled] VARCHAR(50) NOT NULL, 
    [Type] VARCHAR(50) NOT NULL, 
    [CalendarID] INT NOT NULL, 
    [PairID] INT NOT NULL,
    [NoteID] INT,
    [ProblemID] INT NULL, 

    [MeetingID] VARCHAR(MAX) NULL, 
    [MeetingPassword] VARCHAR(MAX) NULL, 
    FOREIGN KEY ([CalendarID]) REFERENCES [dbo].[PsychCalendar](PsychCalendarID),
    FOREIGN KEY ([PairID]) REFERENCES [dbo].[Pair](PairID),
    FOREIGN KEY ([NoteID]) REFERENCES [dbo].[Notes](NoteID),
    FOREIGN KEY ([ProblemID]) REFERENCES [dbo].[RHubProblems](ProblemID)
)
