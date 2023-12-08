CREATE TABLE [dbo].[Link]
(
    [LinkID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
	[PairID] INT NOT NULL, 
    [PsychID] INT NOT NULL, 
    [Status] CHAR NOT NULL, 
    [Rating] INT NOT NULL,
    [DateLinked] DATETIME NULL, 
    FOREIGN KEY ([PairID]) REFERENCES [dbo].[Pair](PairID),
    FOREIGN KEY ([PsychID]) REFERENCES [dbo].[Psychologist](PsychID)
)
