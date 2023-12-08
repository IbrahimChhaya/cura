CREATE TABLE [dbo].[Pair]
(
	[PairID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY, 
    [ParentID] INT NOT NULL, 
    [ChildID] INT NOT NULL,
    FOREIGN KEY ([ParentID]) REFERENCES [dbo].[User](UserID),
    FOREIGN KEY ([ChildID]) REFERENCES [dbo].[User](UserID)
)
