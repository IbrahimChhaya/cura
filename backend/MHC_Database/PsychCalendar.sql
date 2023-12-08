CREATE TABLE [dbo].[PsychCalendar]
(
	[PsychCalendarID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY, 
    [DayOfWeek] VARCHAR(10) NOT NULL, 
    [SingleStart] DATETIME NOT NULL, 
    [SingleEnd] DATETIME NOT NULL, 
    [RepeatStart] DATETIME NOT NULL, 
    [RepeatEnd] DATETIME NOT NULL, 
    [PsychID] INT NOT NULL,
    [Closed] BIT NOT NULL, 
    FOREIGN KEY ([PsychID]) REFERENCES [dbo].[Psychologist](PsychID)
)
