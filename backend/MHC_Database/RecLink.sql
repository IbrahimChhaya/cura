CREATE TABLE [dbo].[RecLink]
(
	[recLinkID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY, 
    [RecID] INT NOT NULL, 
    [UserID] INT NOT NULL,
    FOREIGN KEY ([RecID]) REFERENCES [dbo].[Recommendations](RecID),
    FOREIGN KEY ([UserID]) REFERENCES [dbo].[User](UserID)
)
