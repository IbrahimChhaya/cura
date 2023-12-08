CREATE TABLE [dbo].[ChildActivity]
(
	[ActivityID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY, 
    [FileName] VARCHAR(MAX) NOT NULL, 
    [Date] DATETIME NOT NULL, 
    [ChildID] INT NOT NULL,
    [NoteID] INT NULL, 
    [PsychID] INT NULL, 
    [ActivityName] VARCHAR(50) NOT NULL, 
    FOREIGN KEY ([ChildID]) REFERENCES [dbo].[User](UserID),
    FOREIGN KEY ([NoteID]) REFERENCES [dbo].[Notes](NoteID),
    FOREIGN KEY ([PsychID]) REFERENCES [dbo].[Psychologist](PsychID)
)
