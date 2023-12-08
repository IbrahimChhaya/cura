CREATE TABLE [dbo].[Locations]
(
	[LocationID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY, 
    [Facility] VARCHAR(MAX) NOT NULL, 
    [Type] VARCHAR(50) NOT NULL, 
    [Location] VARCHAR(MAX) NOT NULL
)
