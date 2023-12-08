CREATE TABLE [dbo].[HappyPoints]
(
	[ChildID] INT NOT NULL PRIMARY KEY, 
    [Points] INT NOT NULL,
	FOREIGN KEY ([ChildID]) REFERENCES [dbo].[User](UserID)
)
