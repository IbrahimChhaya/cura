CREATE TABLE [dbo].[ChildTest]
(
	[ChildTestID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY, 
    [Date] DATETIME NOT NULL, 
    [Status] VARCHAR(50) NOT NULL, 
    [ChildID] INT NOT NULL, 
    [TestID] INT NOT NULL,
    [NoteID] INT,
    FOREIGN KEY ([ChildID]) REFERENCES [dbo].[User](UserID),
    FOREIGN KEY ([TestID]) REFERENCES [dbo].[Tests](TestID),
    FOREIGN KEY ([NoteID]) REFERENCES [dbo].[Notes](NoteID)
)
