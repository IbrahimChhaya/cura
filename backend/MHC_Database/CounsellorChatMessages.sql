CREATE TABLE [dbo].[CounsellorChatMessages]
(
	[ChatID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY, 
    [CounsellorChatID] INT NOT NULL, 
    [Date] DATETIME NOT NULL,
    [SenderID] INT NOT NULL, 
    [Message] VARCHAR(MAX) NOT NULL, 
    [Read] BIT NOT NULL,
    FOREIGN KEY ([CounsellorChatID]) REFERENCES [dbo].[CounsellorChat](CounsellorChatID),
    FOREIGN KEY ([SenderID]) REFERENCES [dbo].[User](UserID)
)
