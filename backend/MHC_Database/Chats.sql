CREATE TABLE [dbo].[Chats]
(
	[MessageID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY, 
    [LinkID] INT NOT NULL, 
    [Message] VARCHAR(MAX) NOT NULL, 
    [Date] DATETIME NOT NULL,
    [Read] BIT NOT NULL, 
    [SenderID] INT NOT NULL, 
    FOREIGN KEY ([LinkID]) REFERENCES [dbo].[Link](LinkID),
    FOREIGN KEY ([SenderID]) REFERENCES [dbo].[User](UserID)
)
