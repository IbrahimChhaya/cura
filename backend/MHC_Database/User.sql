CREATE TABLE [dbo].[User]
(
	[UserID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY, 
    [Name] VARCHAR(MAX) NOT NULL, 
    [Email] VARCHAR(MAX) NOT NULL, 
    [Password] VARCHAR(MAX) NOT NULL, 
    [DOB] DATETIME NULL, 
    [UserType] VARCHAR(50) NOT NULL, 
    [Grade] VARCHAR(2) NULL,
    [ProfilePicture] VARCHAR(MAX) NULL, 
    [DateRegistered] DATETIME NOT NULL, 
    [Status] VARCHAR(10) NOT NULL, 
    [PairCode] CHAR(10)  NULL,
    [Spice] VARCHAR(50) NULL, 
    [PicturePass] BIT NULL
    
)
