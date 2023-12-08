CREATE TABLE [dbo].[ToDo]
(
	[ToDoID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY, 
    [Status] VARCHAR(50) NOT NULL, 
    [IssueID] INT NOT NULL,
    [NoteID] INT,
    FOREIGN KEY ([IssueID]) REFERENCES [dbo].[Issues](IssueID),
    FOREIGN KEY ([NoteID]) REFERENCES [dbo].[Notes](NoteID)
)
