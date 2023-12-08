CREATE TABLE [dbo].[Notes]
(
	[NoteID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY, 
    [DateCreated] DATETIME NOT NULL,
    [Feedback] VARCHAR(MAX) NOT NULL, 
    [Type] VARCHAR(50) NOT NULL, 
    [PsychID] INT NOT NULL, 
    [PairID] INT NOT NULL,
    FOREIGN KEY ([PsychID]) REFERENCES [dbo].[Psychologist](PsychID),
    FOREIGN KEY ([PairID]) REFERENCES [dbo].Pair(PairID)
)
