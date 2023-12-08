/*
Post-Deployment Script Template							
--------------------------------------------------------------------------------------
 This file contains SQL statements that will be appended to the build script.		
 Use SQLCMD syntax to include a file in the post-deployment script.			
 Example:      :r .\myfile.sql								
 Use SQLCMD syntax to reference a variable in the post-deployment script.		
 Example:      :setvar TableName MyTable							
               SELECT * FROM [$(TableName)]					
--------------------------------------------------------------------------------------
*/

/*Populate Holidays Table*/
INSERT INTO [dbo].[Holidays] ([Name], [Date]) VALUES (N'New Year''s Day', N'2021-01-01 00:00:00')
INSERT INTO [dbo].[Holidays] ([Name], [Date]) VALUES (N'Human Rights Day', N'2021-03-21 00:00:00')
INSERT INTO [dbo].[Holidays] ([Name], [Date]) VALUES (N'Good Friday', N'2021-04-02 00:00:00')
INSERT INTO [dbo].[Holidays] ([Name], [Date]) VALUES (N'Family Day', N'2021-04-05 00:00:00')
INSERT INTO [dbo].[Holidays] ([Name], [Date]) VALUES (N'Freedom Day', N'2021-04-27 00:00:00')
INSERT INTO [dbo].[Holidays] ([Name], [Date]) VALUES (N'International Workers'' Day', N'2021-05-01 00:00:00')
INSERT INTO [dbo].[Holidays] ([Name], [Date]) VALUES (N'Youth Day', N'2021-06-16 00:00:00')
INSERT INTO [dbo].[Holidays] ([Name], [Date]) VALUES (N'National Women''s Day', N'2021-08-09 00:00:00')
INSERT INTO [dbo].[Holidays] ([Name], [Date]) VALUES (N'Heritage Day', N'2021-09-24 00:00:00')
INSERT INTO [dbo].[Holidays] ([Name], [Date]) VALUES (N'Day of Reconciliation', N'2021-12-16 00:00:00')
INSERT INTO [dbo].[Holidays] ([Name], [Date]) VALUES (N'Christmas Day', N'2021-12-25 00:00:00')
INSERT INTO [dbo].[Holidays] ([Name], [Date]) VALUES (N'Boxing Day', N'2021-12-26 00:00:00')


/*Populate User Table*/
SET IDENTITY_INSERT [dbo].[User] ON
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (1, N'Thabo Smith', N'ibi@gmail.com', N'90d06abae2d5098e316ac8ebd808def3c47267', N'2010-01-01 00:00:00', N'Child', N'5', N'boy.png', N'2021-04-14 00:00:00', N'Active', NULL, N'7d715aed-196b-4172-84ec-ce88eb04d024', 1)
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (2, N'Zeerak', N'zeerak@gmail.com', N'04005a8b19a1197e9afdf0ed973b0d10291e08', N'1998-09-21 00:00:00', N'Admin', NULL, N'adult4.png', N'2021-03-15 00:00:00', N'Active', NULL, N'47840a52-9bbb-4343-a424-8f077355377f', NULL)
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (3, N'Ubaid', N'ubaid@gmail.com', N'0e85501867a33d6be4529fdecde2d8734fd411', N'1990-09-16 00:00:00', N'Parent', NULL, N'ubaid.png', N'2021-04-15 00:00:00', N'Active', NULL, N'22e504d6-1bf8-4c05-bea4-a3ef2fea925f', NULL)
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (4, N'Sue Lee', N'sue@gmail.com', N'beea2d0a5c4e2a813296e6bdd568a478224c47', N'1978-04-12 00:00:00', N'Psychologist', NULL, N'adult3.png', N'2021-04-15 00:00:00', N'Active', NULL, N'abbbbbcf-0961-415e-a0c5-8003d2a4135e', NULL)
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (5, N'Nathan Kagdi', N'nathan@gmail.com', N'5ae63864e8318fd29bab4b340e29c28cd332ac', N'2011-12-08 00:00:00', N'Child', N'3', N'boy2.jpg', N'2021-03-12 00:00:00', N'Active', NULL, N'58fee790-76f3-485a-9216-d696e688fcf9', 1)
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (6, N'John Cena', N'ucantcme@gmail.com', N'd7af2fe62f7270825d24f0e15071ccf7af9264', N'2004-07-15 00:00:00', N'Child', N'11', N'child0.png', N'2004-07-15 00:00:00', N'Active', NULL, N'4953c396-3b99-4fc4-b4df-e088bcc8dde7', NULL)
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (7, N'ligin', N'login@gmail.com', N'f7ee63777f6e780cb341212d36788c16430f63', N'2015-01-15 00:00:00', N'Child', N'1', N'child0.png', N'2021-05-26 00:00:00', N'Active', NULL, N'ac49e982-6d90-4dc4-9c8b-1fca8485a1d0', NULL)
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (8, N'hey', N'hey@gmail.com', N'fa2a0d68d753ee48fd58b5ae34079eb6b10ea0', N'1999-10-10 00:00:00', N'Child', N'12', N'child0.png', N'2021-05-26 00:00:00', N'Active', NULL, N'f4667aee-e040-43c7-b858-dc3cb93f0864', NULL)
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (9, N'John de Lange', N'john@gmail.com', N'f2255d0a06154cc048d2925e98a12630faa544', N'1992-06-16 00:00:00', N'Parent', NULL, N'zeerak.png', N'1992-06-16 00:00:00', N'Active', NULL, N'054216f6-b7b4-4123-925f-29bf05e3da7e', NULL)
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (10, N'Humaira de Lange', N'humaira@gmail.com', N'7b9b0e78980ea2a5595e5a7110892a0e7f2a32', N'2010-06-16 00:00:00', N'Child', N'5', N'child3.png', N'2021-05-26 00:00:00', N'Active', NULL, N'1814cc6c-e958-4dc4-86c2-29f7dbd89d36', NULL)
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (11, N'Billy', N'billy@gmail.com', N'f059730069bd36b0b25770a1abea49ca222dec', N'2005-05-18 00:00:00', N'Child', N'10', N'child4.png', N'2021-05-26 00:00:00', N'Active', NULL, N'b92c3863-8ba2-4f22-8cab-702540a056c6', NULL)
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (12, N'Travolta John', N'travolta@gmail.com', N'9f85efa83409bf51a8a9e09ef51e8794b91a42', N'1973-06-25 00:00:00', N'Parent', NULL, N'ibrahim.png', N'1973-06-25 00:00:00', N'Active', NULL, N'05314da5-74bd-4ed1-900b-14aa1f41efc6', NULL)
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (13, N'Alex John', N'alex@gmail.com', N'32c61b9b13a0db8edba8d854f073bdbfa70fd7', N'2014-05-16 00:00:00', N'Child', N'1', N'child5.png', N'2021-05-26 00:00:00', N'Active', NULL, N'98e17c18-ea17-46d3-b8df-5f96ab75bbf6', NULL)
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (14, N'Mhudi', N'mhudi@gmail.com', N'199f7c3d85be2699cf19a38a2b1922a2e395b8', N'2004-09-19 00:00:00', N'Child', N'9', N'child0.png', N'2021-05-26 00:00:00', N'Active', NULL, N'a97fb142-0f83-456a-a60d-0b108f3be52d', NULL)
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (3001, N'Rob', N'rob@gmail.com', N'dd412fdbb011b106904444456370d1f2985ce7', N'1987-06-02 00:00:00', N'Counsellor', NULL, N'adult2.png', N'2021-09-06 00:00:00', N'Active', NULL, N'16889c9c-c3ca-430a-9780-7b4e4de387e3', NULL)
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (3002, N'Payton', N'payton@gmail.com', N'17697782e2e61793e67cdd14e708789609ad1b', NULL, N'Parent', NULL, N'adult5.png', N'2021-09-14 00:00:00', N'Active', NULL, N'cd4bf4d9-c37f-430b-8bd0-d961a40d964f', NULL)
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (3003, N'James', N'james@gmail.com', N'6e12eff41eb0db57349443b84d0059767bc004', N'2011-08-16 00:00:00', N'Child', N'0', N'child0.png', N'2021-09-14 21:35:20', N'Active', NULL, N'10f5b7b5-5eb5-412d-b8ea-505ffb397c29', NULL)
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (3004, N'Susan', N'susan@gmail.com', N'c0d009321d6f86561048eae188f056eb5063bd', N'2011-08-16 00:00:00', N'Child', N'3', N'child3.png', N'2021-09-14 00:00:00', N'Active', NULL, N'20da6387-cbd0-4bf5-93bc-0c9effb06f83', NULL)
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (3006, N'Johnny', N'Johnny@gmail.com', N'd22aea83d5abd02b2c49406d89914824ab42dc', NULL, N'Parent', NULL, N'adult2.png', N'2021-09-14 00:00:00', N'Active', NULL, N'74aeccfd-7e59-4c86-80d5-9a0b0b099bdc', NULL)
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (3007, N'Bill', N'bill@gmail.com', N'cec96aab041216f5b448aa3aa75cf19c4ee865', N'2014-08-16 00:00:00', N'Child', N'0', N'child1.png', N'2021-09-14 22:00:37', N'Active', NULL, N'a6b715be-2a0e-45ed-9bc8-bef8aea558b9', NULL)
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (3008, N'Layla', N'layla@gmail.com', N'f5e1fd5aed48d5c36eb33106333103ef0bcd1c', N'2014-08-16 00:00:00', N'Child', N'2', N'child4.png', N'2021-09-14 00:00:00', N'Active', NULL, N'66911fda-ba16-4f4a-bdfe-17042a6bab24', NULL)
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (4001, N'Tatjana', N'tatjana@gmail.com', N'9b921a2acdfbdebfbc3197ae61e58ea2788ded', NULL, N'Parent', NULL, N'adult1.png', N'2021-09-15 00:00:00', N'Active', NULL, N'cabfa067-347e-47b9-a113-8451bad91e51', NULL)
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (4002, N'Miguel', N'miguel@gmail.com', N'99210ce72f30404f8d67b052a82e59b9ff2d28', N'2013-08-16 00:00:00', N'Child', N'0', N'child1.png', N'2021-09-15 09:13:57', N'Active', NULL, N'c51d9d39-d9de-436f-a9d2-ea66dee932a0', NULL)
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (4003, N'Fatima', N'fatima@gmail.com', N'725862b23a48184454aeded701e3186d0bd3b1', N'2019-08-16 00:00:00', N'Child', N'2', N'child3.png', N'2021-09-15 00:00:00', N'Active', N'kWUx6ubhud', N'cce72cc1-fb21-450d-bd0f-3f85457c5f6b', NULL)
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (4004, N'Tayyibah', N'tay@gmail.com', N'e1694e0cd3431ca71820ad6a6a4133f508e26a', N'1996-09-10 00:00:00', N'Counsellor', NULL, N'adult7.png', N'2021-09-15 00:00:00', N'Active', NULL, N'9c7c0a03-c667-49a1-975a-0fba926205fc', NULL)
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (4005, N'TestUser', N'testUser@gmail.com', N'35dbb4a6ebc126c5c4c7d5132888e89c69c967', N'2021-09-20 00:00:00', N'Parent', NULL, NULL, N'2021-09-20 00:00:00', N'Active', NULL, N'79543584-e134-4959-88e1-e69265d90ddf', NULL)
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (4006, N'TestUser2', N'testUser2@gmail.com', N'ca77bbd2ffa7bfc6fd202a92f06c5a2ee2b23f', N'2021-09-20 00:00:00', N'Parent', NULL, NULL, N'2021-09-20 00:00:00', N'Active', NULL, N'f8fee264-9e38-459c-8089-f275c9d44d77', NULL)
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (4007, N'TestUser3', N'testUser3@gmail.com', N'1b173c6e4b6d2cd37172b03cceb66be421d196', N'2021-09-20 00:00:00', N'Parent', NULL, NULL, N'2021-09-20 00:00:00', N'Active', NULL, N'2c28f4f1-8dab-49f8-88fe-842b55bf3509', NULL)
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (4008, N'TestUser4', N'testUser4@gmail.com', N'13d4e2389025618392815304eaa29c833e2d36', N'2021-09-20 00:00:00', N'Parent', NULL, NULL, N'2021-09-20 00:00:00', N'Active', NULL, N'82182f0a-84f2-4d3e-9821-73c41b806d36', NULL)
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (4009, N'TestUser5', N'testUser5@gmail.com', N'926477409266e3e9a7323e632a354808977bfe', N'2021-09-20 00:00:00', N'Parent', NULL, NULL, N'2021-09-20 00:00:00', N'Active', NULL, N'e3e7f4a5-8f6c-484b-8144-445676131c13', NULL)
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (4010, N'TestUser6', N'testUser6@gmail.com', N'3cf4c7ab2991d2c0214fad39827ac9f830a217', N'2021-09-20 00:00:00', N'Parent', NULL, NULL, N'2021-09-20 00:00:00', N'Active', NULL, N'73a85ae0-1037-4dbe-8790-4830bfd478e8', NULL)
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (4011, N'boy2', N'boy2@gmail.com', N'9f7fc2648798c0e198517a380d8a848a6b3563', NULL, N'Child', NULL, N'child0.png', N'2021-09-29 00:00:00', N'Active', NULL, N'f5315b31-f46e-4c44-acc6-4db0d482bcec', 1)
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (5013, N'Kid', N'kid@gmail.com', N'49d4dd657bc44e93241969bf9551c775e068ea', N'2019-08-16 00:00:00', N'Child', N'0', N'child0.png', N'2021-10-01 01:34:41', N'Active', NULL, N'f3233f3e-1200-4306-be65-aa383b33ba6d', 1)
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (5014, N'bob', N'bob@gmail.com', N'b27cdc10aafb427877f00353167eebcda0d8da', N'1984-05-10 00:00:00', N'Counsellor', NULL, N'adult6.png', N'2021-10-01 00:00:00', N'Active', NULL, N'845ef05e-a3f3-4a42-914d-33f9f93b5898', NULL)
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (5015, N'max', N'max@gmail.com', N'da1dad26b77881b14b100ea22a519aff4e3abd', N'2009-08-16 00:00:00', N'Child', N'0', N'child0.png', N'2021-10-01 10:42:14', N'Active', NULL, N'66f394ae-053e-4d5d-baf8-e3d027752082', 1)
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (5016, N'maximus', N'maximus@gmail.com', N'88bdcd24c369f361926d04e8245357d3fa0ef3', N'2009-08-16 00:00:00', N'Child', N'0', N'child0.png', N'2021-10-01 00:00:00', N'Active', NULL, N'b50eef73-98ef-4dd4-8646-52d4dd2d3cef', 0)
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (6014, N'James ', N'jamesC@gmail.com', N'0d042c3e19f97ad89d6bac0c52e3a68fe66400', N'1995-02-09 00:00:00', N'Counsellor', NULL, N'adult6.png', N'2021-10-03 00:00:00', N'Pending', NULL, N'7ed90771-9fe1-407e-9e1e-90bf6aa2d4fb', NULL)
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (6015, N'Paula Smith', N'paulas@gmail.com', N'f77d69965547c46609b409025b2d5e0fccb568', NULL, N'Parent', NULL, N'adult5.png', N'2021-10-04 00:00:00', N'Active', NULL, N'5f9a89f4-5535-4008-93d0-860e83d0d3f8', NULL)
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (6016, N'Bianca Smith', N'bianca@gmail.com', N'4d6cebbe3d16e2e2d56f8b386e21786dac27d3', N'2009-08-16 00:00:00', N'Child', N'0', N'child3.png', N'2021-10-04 00:05:20', N'Active', NULL, N'7674c958-f43d-4660-b112-8652b8f62e1b', 1)
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (6017, N'Ben Smith', N'ben@gmail.com', N'7fa0e1424a59b8572dc0d68f6204aca5cef0fd', N'2007-08-16 00:00:00', N'Child', N'0', N'child1.png', N'2021-10-04 00:00:00', N'Active', NULL, N'3c433bea-dbc5-49aa-88e7-6dfa0a2a48d3', 0)
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (6018, N'Atish', N'atish@gmail.com', N'f3d94310ecc78f2d186f0c6ca48f861d36ee81', N'1993-05-06 00:00:00', N'Counsellor', NULL, N'adult8.png', N'2021-10-04 00:00:00', N'Active', NULL, N'e71754e5-79b4-4af6-baf3-f21e639fef86', NULL)
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (6019, N'Mia Smith', N'mias@gmail.com', N'bc3f74eba9802fe0d9f0cd7cefa790ec246039', N'2012-08-17 00:00:00', N'Child', N'0', N'child3.png', N'2021-10-04 12:59:19', N'Active', NULL, N'10ba0c7c-1ce5-4a71-82e7-4e692eb03c7e', 1)
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (6020, N'Alexander Smith', N'alexs@gmail.com', N'a7435a12aff91a6ba2f13e99329879ad37be94', N'2007-08-16 00:00:00', N'Child', N'0', N'child1.png', N'2021-10-04 00:00:00', N'Active', NULL, N'3142db85-3c36-41d1-828d-80366a1abdd7', 0)
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (6021, N'Jane', N'jane2@gmail.com', N'd8770d6b33cc0b8fbb4cf06305a667ef22a81b', N'1995-03-10 00:00:00', N'Counsellor', NULL, N'adult3.png', N'2021-10-04 00:00:00', N'Pending', NULL, N'65a6be96-c4c8-464e-b619-c2a9ebd3bb5f', NULL)
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (6022, N'MiaSanMi', N'mia@gmail.com', N'2342188d345bd01c3bd7cbbdb86c05c217ca7b', NULL, N'Child', NULL, N'child2.png', N'2021-10-04 17:21:47', N'Active', NULL, N'873b377b-b934-4075-8cf1-1887cffa558b', 1)
INSERT INTO [dbo].[User] ([UserID], [Name], [Email], [Password], [DOB], [UserType], [Grade], [ProfilePicture], [DateRegistered], [Status], [PairCode], [Spice], [PicturePass]) VALUES (6023, N'Brian Smith', N'brian@gmail.com', N'728aaa1920696db1290edaef919835ac41a10d', NULL, N'Child', NULL, N'child0.png', N'2021-10-04 17:43:57', N'Active', NULL, N'5011c5ca-8c05-4285-a806-9fc29ccb861f', 1)
SET IDENTITY_INSERT [dbo].[User] OFF



/*Populate Psychologist table*/
INSERT INTO [dbo].[Psychologist] ([PsychID], [Address], [Qualification], [RegNumber], [Description], [Speciality], [Status]) VALUES (4, N'123 Rose Road, Brackendowns', N'Bsc in Psychology', N'Psy123', N'Bachelor of science in Psychology', N'Counselling', N'Active')


/*Populate PsychCalendar table*/
SET IDENTITY_INSERT [dbo].[PsychCalendar] ON
INSERT INTO [dbo].[PsychCalendar] ([PsychCalendarID], [DayOfWeek], [SingleStart], [SingleEnd], [RepeatStart], [RepeatEnd], [PsychID], [Closed]) VALUES (1, N'Monday', N'2021-06-28 08:00:00', N'2021-06-28 11:00:00', N'2021-06-28 12:00:00', N'2021-06-28 17:00:00', 4, 1)
INSERT INTO [dbo].[PsychCalendar] ([PsychCalendarID], [DayOfWeek], [SingleStart], [SingleEnd], [RepeatStart], [RepeatEnd], [PsychID], [Closed]) VALUES (2, N'Tuesday', N'2021-06-28 09:00:00', N'2021-06-28 11:00:00', N'2021-06-28 12:00:00', N'2021-06-28 17:00:00', 4, 0)
INSERT INTO [dbo].[PsychCalendar] ([PsychCalendarID], [DayOfWeek], [SingleStart], [SingleEnd], [RepeatStart], [RepeatEnd], [PsychID], [Closed]) VALUES (3, N'Wednesday', N'2021-06-28 00:00:00', N'2021-06-28 00:00:00', N'2021-06-28 00:00:00', N'2021-06-28 00:00:00', 4, 1)
INSERT INTO [dbo].[PsychCalendar] ([PsychCalendarID], [DayOfWeek], [SingleStart], [SingleEnd], [RepeatStart], [RepeatEnd], [PsychID], [Closed]) VALUES (4, N'Thursday', N'2021-06-28 00:00:00', N'2021-06-28 00:00:00', N'2021-06-28 00:00:00', N'2021-06-28 00:00:00', 4, 1)
INSERT INTO [dbo].[PsychCalendar] ([PsychCalendarID], [DayOfWeek], [SingleStart], [SingleEnd], [RepeatStart], [RepeatEnd], [PsychID], [Closed]) VALUES (1001, N'Friday', N'2021-06-28 08:00:00', N'2021-06-28 11:00:00', N'2021-06-28 12:00:00', N'2021-06-28 16:00:00', 4, 0)
INSERT INTO [dbo].[PsychCalendar] ([PsychCalendarID], [DayOfWeek], [SingleStart], [SingleEnd], [RepeatStart], [RepeatEnd], [PsychID], [Closed]) VALUES (1002, N'Saturday', N'2021-06-28 08:00:00', N'2021-06-28 11:00:00', N'2021-06-28 12:00:00', N'2021-06-28 16:00:00', 4, 0)
INSERT INTO [dbo].[PsychCalendar] ([PsychCalendarID], [DayOfWeek], [SingleStart], [SingleEnd], [RepeatStart], [RepeatEnd], [PsychID], [Closed]) VALUES (1003, N'Sunday', N'2021-06-28 08:00:00', N'2021-06-28 11:00:00', N'2021-06-28 12:00:00', N'2021-06-28 16:00:00', 4, 0)
INSERT INTO [dbo].[PsychCalendar] ([PsychCalendarID], [DayOfWeek], [SingleStart], [SingleEnd], [RepeatStart], [RepeatEnd], [PsychID], [Closed]) VALUES (1004, N'Holidays', N'2021-06-28 08:00:00', N'2021-06-28 11:00:00', N'2021-06-28 12:00:00', N'2021-06-28 16:00:00', 4, 0)
SET IDENTITY_INSERT [dbo].[PsychCalendar] OFF



/*Populate Pair table*/
SET IDENTITY_INSERT [dbo].[Pair] ON
INSERT INTO [dbo].[Pair] ([PairID], [ParentID], [ChildID]) VALUES (1, 6015, 1)
INSERT INTO [dbo].[Pair] ([PairID], [ParentID], [ChildID]) VALUES (2, 3, 5)
INSERT INTO [dbo].[Pair] ([PairID], [ParentID], [ChildID]) VALUES (3, 3002, 3003)
INSERT INTO [dbo].[Pair] ([PairID], [ParentID], [ChildID]) VALUES (4, 3002, 3004)
INSERT INTO [dbo].[Pair] ([PairID], [ParentID], [ChildID]) VALUES (5, 3006, 3007)
INSERT INTO [dbo].[Pair] ([PairID], [ParentID], [ChildID]) VALUES (6, 3006, 3008)
INSERT INTO [dbo].[Pair] ([PairID], [ParentID], [ChildID]) VALUES (1001, 4001, 4002)
INSERT INTO [dbo].[Pair] ([PairID], [ParentID], [ChildID]) VALUES (2001, 3, 5013)
INSERT INTO [dbo].[Pair] ([PairID], [ParentID], [ChildID]) VALUES (3001, 4001, 5015)
INSERT INTO [dbo].[Pair] ([PairID], [ParentID], [ChildID]) VALUES (3002, 4001, 5016)
INSERT INTO [dbo].[Pair] ([PairID], [ParentID], [ChildID]) VALUES (4001, 3, 6016)
INSERT INTO [dbo].[Pair] ([PairID], [ParentID], [ChildID]) VALUES (4002, 3, 6017)
INSERT INTO [dbo].[Pair] ([PairID], [ParentID], [ChildID]) VALUES (5001, 6015, 6019)
INSERT INTO [dbo].[Pair] ([PairID], [ParentID], [ChildID]) VALUES (5002, 6015, 6020)
INSERT INTO [dbo].[Pair] ([PairID], [ParentID], [ChildID]) VALUES (5003, 6015, 6022)
INSERT INTO [dbo].[Pair] ([PairID], [ParentID], [ChildID]) VALUES (5004, 6015, 6023)
SET IDENTITY_INSERT [dbo].[Pair] OFF



/*Populate Link table*/
SET IDENTITY_INSERT [dbo].[Link] ON
INSERT INTO [dbo].[Link] ([LinkID], [PairID], [PsychID], [Status], [Rating], [DateLinked]) VALUES (1, 1, 4, N'Y', 8, N'2021-08-10 00:00:00')
INSERT INTO [dbo].[Link] ([LinkID], [PairID], [PsychID], [Status], [Rating], [DateLinked]) VALUES (2, 2, 4, N'Y', 7, N'2021-07-02 00:00:00')
INSERT INTO [dbo].[Link] ([LinkID], [PairID], [PsychID], [Status], [Rating], [DateLinked]) VALUES (1003, 3, 4, N'Y', 5, N'2021-09-14 21:36:28')
INSERT INTO [dbo].[Link] ([LinkID], [PairID], [PsychID], [Status], [Rating], [DateLinked]) VALUES (1004, 5, 4, N'Y', 5, N'2021-09-14 22:01:36')
INSERT INTO [dbo].[Link] ([LinkID], [PairID], [PsychID], [Status], [Rating], [DateLinked]) VALUES (2002, 1001, 4, N'Y', 5, N'2021-09-15 09:14:52')
INSERT INTO [dbo].[Link] ([LinkID], [PairID], [PsychID], [Status], [Rating], [DateLinked]) VALUES (3002, 3001, 4, N'Y', 5, N'2021-10-01 10:43:19')
INSERT INTO [dbo].[Link] ([LinkID], [PairID], [PsychID], [Status], [Rating], [DateLinked]) VALUES (4002, 4001, 4, N'Y', 5, N'2021-10-04 00:06:20')
INSERT INTO [dbo].[Link] ([LinkID], [PairID], [PsychID], [Status], [Rating], [DateLinked]) VALUES (5002, 5001, 4, N'Y', 5, N'2021-10-04 13:09:01')
INSERT INTO [dbo].[Link] ([LinkID], [PairID], [PsychID], [Status], [Rating], [DateLinked]) VALUES (5003, 5003, 4, N'Y', 5, N'2021-10-04 17:22:34')
INSERT INTO [dbo].[Link] ([LinkID], [PairID], [PsychID], [Status], [Rating], [DateLinked]) VALUES (5004, 5004, 4, N'Y', 5, N'2021-10-04 17:44:24')
SET IDENTITY_INSERT [dbo].[Link] OFF





--/*Populate Bookings table*/
SET IDENTITY_INSERT [dbo].[Bookings] ON
INSERT INTO [dbo].[Bookings] ([BookingID], [Date], [Time], [Cancelled], [Type], [CalendarID], [PairID], [NoteID], [ProblemID], [MeetingID], [MeetingPassword]) VALUES (1, N'2021-08-19', N'09:00:00', N'Attended', N'Single', 1, 1, NULL, NULL, NULL, NULL)
INSERT INTO [dbo].[Bookings] ([BookingID], [Date], [Time], [Cancelled], [Type], [CalendarID], [PairID], [NoteID], [ProblemID], [MeetingID], [MeetingPassword]) VALUES (2, N'2021-09-22', N'11:00:00', N'Missed', N'Repeat', 4, 1, NULL, NULL, NULL, NULL)
INSERT INTO [dbo].[Bookings] ([BookingID], [Date], [Time], [Cancelled], [Type], [CalendarID], [PairID], [NoteID], [ProblemID], [MeetingID], [MeetingPassword]) VALUES (3, N'2021-09-26', N'13:00:00', N'Cancelled', N'Repeat', 2, 1, NULL, NULL, NULL, NULL)
INSERT INTO [dbo].[Bookings] ([BookingID], [Date], [Time], [Cancelled], [Type], [CalendarID], [PairID], [NoteID], [ProblemID], [MeetingID], [MeetingPassword]) VALUES (4, N'2021-10-06', N'13:00:00', N'Future', N'Single', 1, 2, NULL, 17, NULL, NULL)
INSERT INTO [dbo].[Bookings] ([BookingID], [Date], [Time], [Cancelled], [Type], [CalendarID], [PairID], [NoteID], [ProblemID], [MeetingID], [MeetingPassword]) VALUES (1003, N'2021-07-30', N'13:00:00', N'Attended', N'Single', 1, 2, NULL, 5, NULL, NULL)
INSERT INTO [dbo].[Bookings] ([BookingID], [Date], [Time], [Cancelled], [Type], [CalendarID], [PairID], [NoteID], [ProblemID], [MeetingID], [MeetingPassword]) VALUES (1004, N'2021-09-15', N'14:00:00', N'Attended', N'Repeat', 1, 1, NULL, 5, NULL, NULL)
INSERT INTO [dbo].[Bookings] ([BookingID], [Date], [Time], [Cancelled], [Type], [CalendarID], [PairID], [NoteID], [ProblemID], [MeetingID], [MeetingPassword]) VALUES (1005, N'2021-09-30', N'10:00:00', N'Attended', N'Single', 1001, 3, 1, 9, NULL, NULL)
INSERT INTO [dbo].[Bookings] ([BookingID], [Date], [Time], [Cancelled], [Type], [CalendarID], [PairID], [NoteID], [ProblemID], [MeetingID], [MeetingPassword]) VALUES (1006, N'2021-09-28', N'10:00:00', N'Attended', N'Single', 2, 5, 3, 9, NULL, NULL)
INSERT INTO [dbo].[Bookings] ([BookingID], [Date], [Time], [Cancelled], [Type], [CalendarID], [PairID], [NoteID], [ProblemID], [MeetingID], [MeetingPassword]) VALUES (2003, N'2021-09-29', N'08:00:00', N'Attended', N'Single', 1001, 1001, 1002, 9, NULL, NULL)
INSERT INTO [dbo].[Bookings] ([BookingID], [Date], [Time], [Cancelled], [Type], [CalendarID], [PairID], [NoteID], [ProblemID], [MeetingID], [MeetingPassword]) VALUES (3003, N'2021-10-05', N'12:00:00', N'Future', N'Repeat', 2, 1, NULL, NULL, NULL, NULL)
INSERT INTO [dbo].[Bookings] ([BookingID], [Date], [Time], [Cancelled], [Type], [CalendarID], [PairID], [NoteID], [ProblemID], [MeetingID], [MeetingPassword]) VALUES (3004, N'2021-10-12', N'12:00:00', N'Future', N'Repeat', 2, 2, NULL, NULL, NULL, NULL)
INSERT INTO [dbo].[Bookings] ([BookingID], [Date], [Time], [Cancelled], [Type], [CalendarID], [PairID], [NoteID], [ProblemID], [MeetingID], [MeetingPassword]) VALUES (3005, N'2021-10-19', N'12:00:00', N'Future', N'Repeat', 2, 2, NULL, 17, NULL, NULL)
INSERT INTO [dbo].[Bookings] ([BookingID], [Date], [Time], [Cancelled], [Type], [CalendarID], [PairID], [NoteID], [ProblemID], [MeetingID], [MeetingPassword]) VALUES (3006, N'2021-10-26', N'12:00:00', N'Future', N'Repeat', 2, 2, NULL, NULL, NULL, NULL)
INSERT INTO [dbo].[Bookings] ([BookingID], [Date], [Time], [Cancelled], [Type], [CalendarID], [PairID], [NoteID], [ProblemID], [MeetingID], [MeetingPassword]) VALUES (4003, N'2021-10-01', N'12:05:00', N'Future', N'Single', 2, 3001, NULL, NULL, N'96999840648', N'1uGNpP')
INSERT INTO [dbo].[Bookings] ([BookingID], [Date], [Time], [Cancelled], [Type], [CalendarID], [PairID], [NoteID], [ProblemID], [MeetingID], [MeetingPassword]) VALUES (5003, N'2021-10-06', N'08:00:00', N'Future', N'Single', 3, 4001, NULL, NULL, NULL, NULL)
INSERT INTO [dbo].[Bookings] ([BookingID], [Date], [Time], [Cancelled], [Type], [CalendarID], [PairID], [NoteID], [ProblemID], [MeetingID], [MeetingPassword]) VALUES (6003, N'2021-10-04', N'13:50:00', N'Future', N'Single', 4, 5001, NULL, NULL, N'98023635865', N'iFbX9G')
INSERT INTO [dbo].[Bookings] ([BookingID], [Date], [Time], [Cancelled], [Type], [CalendarID], [PairID], [NoteID], [ProblemID], [MeetingID], [MeetingPassword]) VALUES (6004, N'2021-05-26', N'14:00:00', N'Missed', N'Repeat', 4, 1, NULL, 12, NULL, NULL)
INSERT INTO [dbo].[Bookings] ([BookingID], [Date], [Time], [Cancelled], [Type], [CalendarID], [PairID], [NoteID], [ProblemID], [MeetingID], [MeetingPassword]) VALUES (6005, N'2021-08-21', N'12:00:00', N'Attended', N'Single', 2, 2, NULL, NULL, NULL, NULL)
INSERT INTO [dbo].[Bookings] ([BookingID], [Date], [Time], [Cancelled], [Type], [CalendarID], [PairID], [NoteID], [ProblemID], [MeetingID], [MeetingPassword]) VALUES (6008, N'2021-06-06', N'14:00:00', N'Cancelled', N'Repeat', 1, 1, NULL, NULL, NULL, NULL)
INSERT INTO [dbo].[Bookings] ([BookingID], [Date], [Time], [Cancelled], [Type], [CalendarID], [PairID], [NoteID], [ProblemID], [MeetingID], [MeetingPassword]) VALUES (6009, N'2021-06-07', N'14:00:00', N'Attended', N'Repeat', 2, 2, NULL, NULL, NULL, NULL)
INSERT INTO [dbo].[Bookings] ([BookingID], [Date], [Time], [Cancelled], [Type], [CalendarID], [PairID], [NoteID], [ProblemID], [MeetingID], [MeetingPassword]) VALUES (6010, N'2021-06-09', N'12:00:00', N'Missed', N'Single', 3, 1001, NULL, NULL, NULL, NULL)
INSERT INTO [dbo].[Bookings] ([BookingID], [Date], [Time], [Cancelled], [Type], [CalendarID], [PairID], [NoteID], [ProblemID], [MeetingID], [MeetingPassword]) VALUES (6011, N'2021-04-13', N'14:00:00', N'Attended', N'Repeat', 4, 1, NULL, 5, NULL, NULL)
INSERT INTO [dbo].[Bookings] ([BookingID], [Date], [Time], [Cancelled], [Type], [CalendarID], [PairID], [NoteID], [ProblemID], [MeetingID], [MeetingPassword]) VALUES (6012, N'2021-04-01', N'08:00:00', N'Attended', N'Single', 1, 2, NULL, NULL, NULL, NULL)
INSERT INTO [dbo].[Bookings] ([BookingID], [Date], [Time], [Cancelled], [Type], [CalendarID], [PairID], [NoteID], [ProblemID], [MeetingID], [MeetingPassword]) VALUES (6014, N'2021-04-05', N'09:00:00', N'Missed', N'Repeat', 1, 2, NULL, NULL, NULL, NULL)
INSERT INTO [dbo].[Bookings] ([BookingID], [Date], [Time], [Cancelled], [Type], [CalendarID], [PairID], [NoteID], [ProblemID], [MeetingID], [MeetingPassword]) VALUES (6015, N'2021-04-19', N'15:00:00', N'Attended', N'Repeat', 1, 1, NULL, NULL, NULL, NULL)
INSERT INTO [dbo].[Bookings] ([BookingID], [Date], [Time], [Cancelled], [Type], [CalendarID], [PairID], [NoteID], [ProblemID], [MeetingID], [MeetingPassword]) VALUES (6016, N'2021-03-08', N'12:00:00', N'Cancelled', N'Single', 1, 1001, NULL, 8, NULL, NULL)
INSERT INTO [dbo].[Bookings] ([BookingID], [Date], [Time], [Cancelled], [Type], [CalendarID], [PairID], [NoteID], [ProblemID], [MeetingID], [MeetingPassword]) VALUES (6017, N'2021-03-27', N'13:00:00', N'Missed', N'Repeat', 1, 1, NULL, 20, NULL, NULL)
INSERT INTO [dbo].[Bookings] ([BookingID], [Date], [Time], [Cancelled], [Type], [CalendarID], [PairID], [NoteID], [ProblemID], [MeetingID], [MeetingPassword]) VALUES (6018, N'2021-02-28', N'16:00:00', N'Attended', N'Repeat', 4, 1001, NULL, 20, NULL, NULL)
INSERT INTO [dbo].[Bookings] ([BookingID], [Date], [Time], [Cancelled], [Type], [CalendarID], [PairID], [NoteID], [ProblemID], [MeetingID], [MeetingPassword]) VALUES (6020, N'2021-01-29', N'10:00:00', N'Attended', N'Repeat', 1001, 1001, NULL, 20, NULL, NULL)
INSERT INTO [dbo].[Bookings] ([BookingID], [Date], [Time], [Cancelled], [Type], [CalendarID], [PairID], [NoteID], [ProblemID], [MeetingID], [MeetingPassword]) VALUES (6021, N'2021-01-12', N'11:00:00', N'Cancelled', N'Single', 1002, 3, NULL, 18, NULL, NULL)
INSERT INTO [dbo].[Bookings] ([BookingID], [Date], [Time], [Cancelled], [Type], [CalendarID], [PairID], [NoteID], [ProblemID], [MeetingID], [MeetingPassword]) VALUES (6022, N'2021-01-09', N'13:00:00', N'Missed', N'Single', 1001, 3001, NULL, 18, NULL, NULL)
INSERT INTO [dbo].[Bookings] ([BookingID], [Date], [Time], [Cancelled], [Type], [CalendarID], [PairID], [NoteID], [ProblemID], [MeetingID], [MeetingPassword]) VALUES (6023, N'2021-01-29', N'08:00:00', N'Attended', N'Single', 2, 5, NULL, 17, NULL, NULL)
INSERT INTO [dbo].[Bookings] ([BookingID], [Date], [Time], [Cancelled], [Type], [CalendarID], [PairID], [NoteID], [ProblemID], [MeetingID], [MeetingPassword]) VALUES (6024, N'2020-11-04', N'12:00:00', N'Attended', N'Repeat', 2, 1, NULL, 8, NULL, NULL)
INSERT INTO [dbo].[Bookings] ([BookingID], [Date], [Time], [Cancelled], [Type], [CalendarID], [PairID], [NoteID], [ProblemID], [MeetingID], [MeetingPassword]) VALUES (6025, N'2020-11-28', N'13:00:00', N'Attended', N'Repeat', 3, 2, NULL, NULL, NULL, NULL)
INSERT INTO [dbo].[Bookings] ([BookingID], [Date], [Time], [Cancelled], [Type], [CalendarID], [PairID], [NoteID], [ProblemID], [MeetingID], [MeetingPassword]) VALUES (6026, N'2020-11-18', N'12:00:00', N'Missed', N'Repeat', 4, 2, NULL, 17, NULL, NULL)
INSERT INTO [dbo].[Bookings] ([BookingID], [Date], [Time], [Cancelled], [Type], [CalendarID], [PairID], [NoteID], [ProblemID], [MeetingID], [MeetingPassword]) VALUES (6027, N'2021-10-04', N'17:50:00', N'Future', N'Single', 2, 5003, NULL, NULL, N'95142096892', N'0Cwfrd')
INSERT INTO [dbo].[Bookings] ([BookingID], [Date], [Time], [Cancelled], [Type], [CalendarID], [PairID], [NoteID], [ProblemID], [MeetingID], [MeetingPassword]) VALUES (6028, N'2021-10-04', N'19:30:00', N'Future', N'Single', 3, 5004, NULL, NULL, N'94545383416', N'1bMB0M')
SET IDENTITY_INSERT [dbo].[Bookings] OFF



/*Populate resourceHub Problems table*/
SET IDENTITY_INSERT [dbo].[RHubProblems] ON
INSERT INTO [dbo].[RHubProblems] ([ProblemID], [Problem], [Description], [TitleImage]) VALUES (5, N'Anxiety', N'A mental health condition characterized by severe sensations of worry, anxiety, or fear that interfere with daily activities. It is typical to have some uneasiness. You''ll feel anxious or nervous in the event that you have got to handle an issue at school, take a test or make an vital choice. And uneasiness can indeed be useful. For illustration, uneasiness helps us take note perilous circumstances and centres our consideration, so we remain secure.In case you have got a panic disorder, you get strongly, sudden panic attacks. These attacks frequently highlight more grounded, more seriously sentiments than other sorts of anxiety disorders. The feelings of fear may begin suddenly and suddenly, or they may come from a trigger, like confronting a circumstance you fear. Panic attacks can take after heart assaults. In case there is any chance you are encountering a heart attack, go to the crisis room. It is way better to err on the side of caution and have a healthcare proficient check you.', N'RHub/social-anxiety.png')
INSERT INTO [dbo].[RHubProblems] ([ProblemID], [Problem], [Description], [TitleImage]) VALUES (8, N'Bipolar', N'A disorder related with scenes of disposition swings extending from depressive to hyper highs. This sort of bipolar clutter is characterized by hyper scenes, with or without sadness indications. On the off chance that you have got this sort of bipolar, your hyper scenes will final a week or longer. Your lunacy may be so terrible that it requires you to be hospitalized to ease the symptoms. Although you do not need to have sadness to be analysed with bipolar 1, it may moreover show with discouragement that endures over two weeks.', N'RHub/bipolar.png')
INSERT INTO [dbo].[RHubProblems] ([ProblemID], [Problem], [Description], [TitleImage]) VALUES (9, N'Depression', N'Depression is a constant feeling of sadness and loss of interest, which stops you doing your normal activities. Major depression, or major depressive disorder is the specialized term utilized by wellbeing experts and analysts to depict the foremost common sort of misery. Other terms in some cases utilized incorporate unipolar sadness or clinical discouragement. Depression can be depicted as gentle, direct, or extreme. Melancholia is an older term for sadness and is still in some cases utilized to portray a more serious form of depression with a solid natural premise, where numerous of the physical indications of discouragement are especially apparent. For example, one of the major changes is that the individual can be observed to move more gradually, or to be experiencing noteworthy changes to their rest pattern and craving. An individual with melancholia is additionally more likely to have a discouraged disposition that is characterised by total misfortune of delight in everything or nearly everything.', N'RHub/depression.png')
INSERT INTO [dbo].[RHubProblems] ([ProblemID], [Problem], [Description], [TitleImage]) VALUES (11, N'Eating disorder', N'Eating disorders are a extend of mental conditions that cause undesirable eating habits to develop. They might begin with a fixation with nourishment, body weight, or body shape. It mostly develops during adolescence and tend to affect more women than men. Individuals with anorexia for the most part see themselves as overweight, indeed if they are perilously underweight. They tend to continually screen their weight, dodge eating certain sorts of nourishments, and seriously confine their calories. Like anorexia, bulimia tends to create amid puberty and early adulthood and shows up to be less common among men than women. People with bulimia as often as possible eat curiously expansive sums of nourishment in a particular period. Each orgy eating scene as a rule proceeds until the individual gets to be horrendously full.', N'eating_icon.png')
INSERT INTO [dbo].[RHubProblems] ([ProblemID], [Problem], [Description], [TitleImage]) VALUES (12, N'OCD', N'Excessive thoughts (obsessions) that lead to dreary practices (compulsions). Obsessive-compulsive disorder is characterised by outlandish contemplations and fears (obsessions) that lead to compulsive behaviours. OCD regularly centres on topics such as a fear of germs or the got to organize objects in a particular way. Some sysmptoms include fear of contamination, the need to have things lined up in a particular way, ruminations and intrusive thoughts, checking locks, oven, alarm systems, or thinking you have certain medical condition. ', N'RHub/ocd.png')
INSERT INTO [dbo].[RHubProblems] ([ProblemID], [Problem], [Description], [TitleImage]) VALUES (14, N'PTSD', N'A disorder characterised by disappointment to recoup after encountering or seeing a terrifying event. The condition may last months or a long time, with triggers that can bring back memories of the trauma accompanied by strongly passionate and physical responses. Some symptoms include anxiety or depressed mood, nightmare or flashbacks, avoidance of situations that bring back trauma, hopeless about the future, difficulty maintaining close relationship, feeling emotionally numb, being easily frightened, trouble sleeping, trouble concentrating and overwhelming guilt or shame.', N'RHub/ptsd.png')
INSERT INTO [dbo].[RHubProblems] ([ProblemID], [Problem], [Description], [TitleImage]) VALUES (17, N'ADHD', N'A chronic condition including attention trouble, hyperactivity, and impulsiveness. ADHD regularly starts in childhood and can hold on into adulthood. It may contribute to low self-esteem, troubled connections and trouble at school or work. Some symptoms include being easily distracted, difficulty sitting still, interrupting people while they are talking and trouble concentrating on tasks.', N'adhd_icon.png')
INSERT INTO [dbo].[RHubProblems] ([ProblemID], [Problem], [Description], [TitleImage]) VALUES (18, N'Bullying', N'When you are being harmed by someone either in a physical or emotional way. Bullying is very difficult for children, or anyone, to deal with. It makes you feel afraid and degraded and often it makes a person feel like they are worthless. Unfortunately, bullying also makes you stop wanting to go out because you are scared you might see the person bullying you. Many children who are bullied even start asking themselves if they can do anything right? Some ways you can be bullied, people calling you names, making things up to get you into trouble. hitting, pinching, biting, pushing and shoving, taking things away from you, damaging your belongings, stealing your money, taking your friends away from you, spreading rumours and threats and intimidation.', N'abuse_icon.png')
INSERT INTO [dbo].[RHubProblems] ([ProblemID], [Problem], [Description], [TitleImage]) VALUES (19, N'Insecurity', N'It is when you lack confidence. Insecurity is a feeling of inadequacy (not being good enough) and uncertainty. It produces anxiety about your goals, relationships, and ability to handle certain situations. Everybody deals with insecurity from time to time. It can appear in all areas of life and come from a variety of causes. It might stem from a traumatic event, patterns of previous experience, social conditioning (learning rules by observing others), or local environments such as school, work, or home. It can also stem from general instability. People who experience unpredictable upsets in daily life are more likely to feel insecure about ordinary resources and routines. ', N'insecurity_icon.png')
INSERT INTO [dbo].[RHubProblems] ([ProblemID], [Problem], [Description], [TitleImage]) VALUES (20, N'Abuse', N'It is when you are treated in a cruel way.', N'bullying_icon.png')
SET IDENTITY_INSERT [dbo].[RHubProblems] OFF

/*Populate KewordsScript table*/
SET IDENTITY_INSERT [dbo].[KeywordsScript] ON
INSERT INTO [dbo].[KeywordsScript] ([ScriptID], [Keyword], [Answers]) VALUES (4, N'Anxiety', N'1')
INSERT INTO [dbo].[KeywordsScript] ([ScriptID], [Keyword], [Answers]) VALUES (7, N'Bipolar disorder', N'4')
INSERT INTO [dbo].[KeywordsScript] ([ScriptID], [Keyword], [Answers]) VALUES (8, N'Depression', N'5')
INSERT INTO [dbo].[KeywordsScript] ([ScriptID], [Keyword], [Answers]) VALUES (10, N'Eating disorder', N'7')
INSERT INTO [dbo].[KeywordsScript] ([ScriptID], [Keyword], [Answers]) VALUES (11, N'OCD', N'8')
INSERT INTO [dbo].[KeywordsScript] ([ScriptID], [Keyword], [Answers]) VALUES (13, N'PTSD', N'10')
INSERT INTO [dbo].[KeywordsScript] ([ScriptID], [Keyword], [Answers]) VALUES (16, N'ADHD', N'13')
INSERT INTO [dbo].[KeywordsScript] ([ScriptID], [Keyword], [Answers]) VALUES (17, N'Bullying', N'14')
INSERT INTO [dbo].[KeywordsScript] ([ScriptID], [Keyword], [Answers]) VALUES (18, N'Insecurity', N'15')
SET IDENTITY_INSERT [dbo].[KeywordsScript] OFF

/*Populate Location table*/
SET IDENTITY_INSERT [dbo].[Locations] ON
INSERT INTO [dbo].[Locations] ([LocationID], [Facility], [Type], [Location]) VALUES (2, N'Impact Therapy Inc Fourways', N'Clinic', N'2 Blinkblaar Ave, Witkoppen, Fourways, 2068')
INSERT INTO [dbo].[Locations] ([LocationID], [Facility], [Type], [Location]) VALUES (3, N'Johannesburg Parent & Child Counselling Centre', N'Clinic', N' Gate 13, Joubert Street Extension, Street, Johannesburg, 2017')
INSERT INTO [dbo].[Locations] ([LocationID], [Facility], [Type], [Location]) VALUES (4, N'Family and Child Therapy Centre', N'Clinic', N'22 1st Ave, Melville, Johannesburg, 2109')
INSERT INTO [dbo].[Locations] ([LocationID], [Facility], [Type], [Location]) VALUES (5, N'Rahima Moosa Mother & Child Hospital - Psychology & Psychiatry', N'Hospital', N'Cnr Oudtshoorn & Fuel Road, Coronationville, Johannesburg, 2093')
INSERT INTO [dbo].[Locations] ([LocationID], [Facility], [Type], [Location]) VALUES (6, N'Oxford Healthcare Centre', N'Clinic', N'75 Oxford Rd, Randburg, 2132')
INSERT INTO [dbo].[Locations] ([LocationID], [Facility], [Type], [Location]) VALUES (7, N'Merchant logo The Day Clinic', N'Clinic', N'Oxford Healthcare Centre, 75 Oxford Rd, Johannesburg, 2196')
INSERT INTO [dbo].[Locations] ([LocationID], [Facility], [Type], [Location]) VALUES (8, N'Gauteng Association for Infant Mental Health South Africa', N'Clinic', N'Wits Science Stadium, 1 Jan Smuts Ave, Johannesburg, 2000')
INSERT INTO [dbo].[Locations] ([LocationID], [Facility], [Type], [Location]) VALUES (9, N'Child Psychologist', N'Clinic', N'74 Van Buuren Rd, Bedfordview, Johannesburg, 2008')
INSERT INTO [dbo].[Locations] ([LocationID], [Facility], [Type], [Location]) VALUES (10, N'Lefika La Phodiso', N'Clinic', N'22 Joubert St, Parktown, Johannesburg, 2001')
INSERT INTO [dbo].[Locations] ([LocationID], [Facility], [Type], [Location]) VALUES (12, N'Dr. Shabeer Jeeva''s ADHD Clinic', N'Clinic', N'13 Scott St, Waverley, Johannesburg, 2090')
INSERT INTO [dbo].[Locations] ([LocationID], [Facility], [Type], [Location]) VALUES (13, N'Educational Psychologist: Dr Seabi', N'Clinic', N'Clinic, 9 Gleneagles Rd, Greenside, Randburg, 2093')
INSERT INTO [dbo].[Locations] ([LocationID], [Facility], [Type], [Location]) VALUES (14, N'Psychomatics Health', N'Clinic', N'17, Marble Towers, 208-212 Jeppe St & Von Wielligh S, Johannesburg CBD, Johannesburg, 2001')
INSERT INTO [dbo].[Locations] ([LocationID], [Facility], [Type], [Location]) VALUES (15, N'Helen Joseph Hospital - Psychiatry Clinic', N'Hospital', N'Helen Joseph Hospital, Perth Road, Auckland Park, Johannesburg, 2092')
INSERT INTO [dbo].[Locations] ([LocationID], [Facility], [Type], [Location]) VALUES (16, N'Vally SA Clinical Psychologist', N'Clinic', N'88 Church St, Mayfair, Randburg, 2092')
INSERT INTO [dbo].[Locations] ([LocationID], [Facility], [Type], [Location]) VALUES (17, N'Tara Hospital - Child Psychiatric Ward', N'Hospital', N'Tara Hospital - Child Psychiatric Ward, 50 Saxon Road, Hurlingham, Sandton, 2196')
INSERT INTO [dbo].[Locations] ([LocationID], [Facility], [Type], [Location]) VALUES (18, N'Li.Ve Counselling', N'Counselling Clinic', N'101 Ring Rd, Three Rivers, Vereeniging, 1929')
INSERT INTO [dbo].[Locations] ([LocationID], [Facility], [Type], [Location]) VALUES (19, N'Saps Johannesburg Central Police Station', N'Police Station', N'1 Commissioner St, Ferreiras Dorp, Johannesburg, 2001')
INSERT INTO [dbo].[Locations] ([LocationID], [Facility], [Type], [Location]) VALUES (20, N'Ivory Park Police Station', N'Police Station', N'21 August Dr, Ivory Park, Midrand, 1693')
INSERT INTO [dbo].[Locations] ([LocationID], [Facility], [Type], [Location]) VALUES (21, N'Booysens Police Station', N'Police Station', N'4-6 Booysens Rd, Booysens, Johannesburg, 2091')
INSERT INTO [dbo].[Locations] ([LocationID], [Facility], [Type], [Location]) VALUES (22, N'Johannesburg Metropolitan Police Department', N'Police Station', N'Village Main Rd & Loveday St, Wemmer, Johannesburg, 2001')
INSERT INTO [dbo].[Locations] ([LocationID], [Facility], [Type], [Location]) VALUES (23, N'Hillbrow Police Station - South African Police Service', N'Police Station', N'01 Clarendon Pl, Hillbrow, Johannesburg, 2196')
INSERT INTO [dbo].[Locations] ([LocationID], [Facility], [Type], [Location]) VALUES (24, N'Brixton Police Station - South African Police Service', N'Police Station', N'High St & Mercury St, Brixton, Johannesburg, 2019')
INSERT INTO [dbo].[Locations] ([LocationID], [Facility], [Type], [Location]) VALUES (25, N'Yeoville Police Station', N'Police Station', N'51 Becker St, Yeoville, Johannesburg, 2198')
INSERT INTO [dbo].[Locations] ([LocationID], [Facility], [Type], [Location]) VALUES (26, N'Jeppe Police Station', N'Police Station', N'1439 Albertina Sisulu Rd, Jeppestown, Johannesburg, 2043')
INSERT INTO [dbo].[Locations] ([LocationID], [Facility], [Type], [Location]) VALUES (27, N'Sophiatown Police Station', N'Police Station', N'186 Main Rd, Newlands, Johannesburg, 2092')
INSERT INTO [dbo].[Locations] ([LocationID], [Facility], [Type], [Location]) VALUES (28, N'Rosebank Police Station', N'Police Station', N'15 Sturdee Ave, Rosebank, Johannesburg, 2196')
INSERT INTO [dbo].[Locations] ([LocationID], [Facility], [Type], [Location]) VALUES (29, N'Parkview Police Station', N'Police Station', N'71 Dundalk Ave, Parkview, Randburg, 2193')
INSERT INTO [dbo].[Locations] ([LocationID], [Facility], [Type], [Location]) VALUES (30, N'Mondeor Police Station', N'Police Station', N'263 Royal Park Dr, Mondeor, Johannesburg South, 2091')
INSERT INTO [dbo].[Locations] ([LocationID], [Facility], [Type], [Location]) VALUES (1002, N'JHB Parent & Child Counselling Centre', N'Counselling Centre', N'2nd Floor (Entrance 13), CMI Building, Cnr. Empire & Hillside, Parktown, 2001, South Africa')
INSERT INTO [dbo].[Locations] ([LocationID], [Facility], [Type], [Location]) VALUES (1003, N'Childline Gauteng Province', N'Childline', N'Cnr Empire Road & Hillside 13 TMI Building 3rd Floor, Office Parktown, 2193')
SET IDENTITY_INSERT [dbo].[Locations] OFF


/*Populate Image table*/
SET IDENTITY_INSERT [dbo].[Images] ON
INSERT INTO [dbo].[Images] ([ImageID], [FileName], [Description], [ProblemID]) VALUES (1, N'emotional_description.png', N'Emotional disorders (such as depression and anxiety disorders) are a group of chronic and frequently repeated psychiatric disorders that cause major impairment in quality of life, productivity, and interpersonal functioning. Some symptoms include withdrawal from family and friends, feeling sad, significant tiredness, low energy, problem sleeping, excessive fears or worries and extreme feeling of guilt.', 7)
INSERT INTO [dbo].[Images] ([ImageID], [FileName], [Description], [ProblemID]) VALUES (4, N'behaviour_description.png', N'Behavioural disorders in children are defined as a pattern of disruptive behaviours that lasts at least 6 months and causes issues at school, at home, and in social interactions. Behavioural disorders in children are defined as a pattern of disruptive behaviours that lasts at least 6 months and causes issues at school, at home, and in social interactions. Almost everyone exhibits some of these traits from time to time. Behavioural disorders may involves inattention, hyperactivity, impulsivity, defiant behaviour, drug use and criminal activity.', 6)
INSERT INTO [dbo].[Images] ([ImageID], [FileName], [Description], [ProblemID]) VALUES (1005, N'bipolar_discription.png', N'A disorder related with scenes of disposition swings extending from depressive to hyper highs. This sort of bipolar clutter is characterized by hyper scenes, with or without sadness indications. On the off chance that you have got this sort of bipolar, your hyper scenes will final a week or longer. Your lunacy may be so terrible that it requires you to be hospitalized to ease the symptoms. Although you do not need to have sadness to be analysed with bipolar 1, it may moreover show with discouragement that endures over two weeks.', 8)
INSERT INTO [dbo].[Images] ([ImageID], [FileName], [Description], [ProblemID]) VALUES (1006, N'depression_description.png', N'Depression is a constant feeling of sadness and loss of interest, which stops you doing your normal activities. Major depression, or major depressive disorder is the specialized term utilized by wellbeing experts and analysts to depict the foremost common sort of misery. Other terms in some cases utilized incorporate unipolar sadness or clinical discouragement. Depression can be depicted as gentle, direct, or extreme. Melancholia is an older term for sadness and is still in some cases utilized to portray a more serious form of depression with a solid natural premise, where numerous of the physical indications of discouragement are especially apparent. For example, one of the major changes is that the individual can be observed to move more gradually, or to be experiencing noteworthy changes to their rest pattern and craving. An individual with melancholia is additionally more likely to have a discouraged disposition that is characterised by total misfortune of delight in everything or nearly everything.', 9)
INSERT INTO [dbo].[Images] ([ImageID], [FileName], [Description], [ProblemID]) VALUES (1007, N'dissociative_description.png', N'Disconnection and lack of continuity between thoughts, memories, surroundings, actions, and identity. Dissociative amnesia is when an individual cannot keep in mind the subtle elements of a traumatic or upsetting occasion, although they do figure it out, they are encountering memory misfortune. Usually too known as psychogenic amnesia. This type of amnesia can be final from a couple of days to one or more a long time. Dissociative amnesia may be connected to other disarranges such as an uneasiness clutter. The four categories of dissociative amnesia include localised amnesia, selective amnesia, generalised amnesia and systematised amnesia. ', 10)
INSERT INTO [dbo].[Images] ([ImageID], [FileName], [Description], [ProblemID]) VALUES (1008, N'eating_description.png', N'Eating disorders are a extend of mental conditions that cause undesirable eating habits to develop. They might begin with a fixation with nourishment, body weight, or body shape. It mostly develops during adolescence and tend to affect more women than men. Individuals with anorexia for the most part see themselves as overweight, indeed if they are perilously underweight. They tend to continually screen their weight, dodge eating certain sorts of nourishments, and seriously confine their calories. Like anorexia, bulimia tends to create amid puberty and early adulthood and shows up to be less common among men than women. People with bulimia as often as possible eat curiously expansive sums of nourishment in a particular period. Each orgy eating scene as a rule proceeds until the individual gets to be horrendously full.', 11)
INSERT INTO [dbo].[Images] ([ImageID], [FileName], [Description], [ProblemID]) VALUES (1009, N'ocd_description.png', N'Excessive thoughts (obsessions) that lead to dreary practices (compulsions). Obsessive-compulsive disorder is characterised by outlandish contemplations and fears (obsessions) that lead to compulsive behaviours. OCD regularly centres on topics such as a fear of germs or the got to organize objects in a particular way. Some sysmptoms include fear of contamination, the need to have things lined up in a particular way, ruminations and intrusive thoughts, checking locks, oven, alarm systems, or thinking you have certain medical condition. ', 12)
INSERT INTO [dbo].[Images] ([ImageID], [FileName], [Description], [ProblemID]) VALUES (1010, N'paranoia_description.png', N'Paranoia could be a thought prepare that causes you to have an unreasonable doubt or doubt of others. Individuals with paranoia may feel like they are being persecuted or that somebody is out to induce them. They may feel the danger of physical hurt indeed in case they are not in peril. Individuals with dementia in some cases have distrustfulness, and it too can happen in individuals who mishandle drugs. Neurotic considerations can moreover be an indication of a mental sickness or an identity disorder. Some symptoms include mistrust of others, feeling misunderstood or disbelieved, isolation, constant stress and feeling victimised when there is not a threat.', 13)
INSERT INTO [dbo].[Images] ([ImageID], [FileName], [Description], [ProblemID]) VALUES (1011, N'ptsd_description.png', N'A disorder characterised by disappointment to recoup after encountering or seeing a terrifying event. The condition may last months or a long time, with triggers that can bring back memories of the trauma accompanied by strongly passionate and physical responses. Some symptoms include anxiety or depressed mood, nightmare or flashbacks, avoidance of situations that bring back trauma, hopeless about the future, difficulty maintaining close relationship, feeling emotionally numb, being easily frightened, trouble sleeping, trouble concentrating and overwhelming guilt or shame.', 14)
INSERT INTO [dbo].[Images] ([ImageID], [FileName], [Description], [ProblemID]) VALUES (1012, N'psychosis_description.png', N'A mental disorder characterised by a detachment from reality. Psychosis may happen because of a psychiatric sickness such as schizophrenia. In other occurrences, it may be caused by a wellbeing condition, medicine, or drug use. Psychosis does not start suddenly. It follows a pattern. Warning before psychosis begins with progressive changes within the way you think around and get it the world. You or your family individuals may take note of drop in grades, lack of hygiene, spending more time alone than usual, no emotions at all and trouble concentrating. Sign of early psychosis include, pull away from family and friends, stop taking care of yourself, not able to think properly, hear, see, or taste things other don''t, and hang on unusual thoughts.', 15)
INSERT INTO [dbo].[Images] ([ImageID], [FileName], [Description], [ProblemID]) VALUES (1013, N'schizophrenia_description.png', N'A disorder that influences a person''s capacity to think, feel and carry on clearly. The correct cause of schizophrenia is not known, but a combination of genetics, environment and modified brain chemistry and structure may play a role. Schizophrenia is characterised by contemplations or encounters that appear out of touch with reality, disrupted discourse or conduct and diminished support in day-by-day exercises. Trouble with concentration and memory may also be present. Some sysmptoms include constant feeling of being watched, strange body positioning, change in personality, inappropriate behaviour, extreme preoccupation with religion or the occult, inability to sleep, peculiar way of speaking or writing, hearing or seeing something that isn''t there, and fearful, angry or irrational response to family and friends.', 16)
INSERT INTO [dbo].[Images] ([ImageID], [FileName], [Description], [ProblemID]) VALUES (1015, N'adhd_description.png', N'A chronic condition including attention trouble, hyperactivity, and impulsiveness. ADHD regularly starts in childhood and can hold on into adulthood. It may contribute to low self-esteem, troubled connections and trouble at school or work. Some symptoms include being easily distracted, difficulty sitting still, interrupting people while they are talking and trouble concentrating on tasks.', 17)
INSERT INTO [dbo].[Images] ([ImageID], [FileName], [Description], [ProblemID]) VALUES (1016, N'bullying_description.png', N'When you are being harmed by someone either in a physical or emotional way. Bullying is very difficult for children, or anyone, to deal with. It makes you feel afraid and degraded and often it makes a person feel like they are worthless. Unfortunately, bullying also makes you stop wanting to go out because you are scared you might see the person bullying you. Many children who are bullied even start asking themselves if they can do anything right? Some ways you can be bullied, people calling you names, making things up to get you into trouble. hitting, pinching, biting, pushing and shoving, taking things away from you, damaging your belongings, stealing your money, taking your friends away from you, spreading rumours and threats and intimidation.', 18)
INSERT INTO [dbo].[Images] ([ImageID], [FileName], [Description], [ProblemID]) VALUES (1017, N'insecurity_description.png', N'It is when you lack confidence. Insecurity is a feeling of inadequacy (not being good enough) and uncertainty. It produces anxiety about your goals, relationships, and ability to handle certain situations. Everybody deals with insecurity from time to time. It can appear in all areas of life and come from a variety of causes. It might stem from a traumatic event, patterns of previous experience, social conditioning (learning rules by observing others), or local environments such as school, work, or home. It can also stem from general instability. People who experience unpredictable upsets in daily life are more likely to feel insecure about ordinary resources and routines. ', 19)
INSERT INTO [dbo].[Images] ([ImageID], [FileName], [Description], [ProblemID]) VALUES (1018, N'abuse_description.png', N'Abuse', 20)
INSERT INTO [dbo].[Images] ([ImageID], [FileName], [Description], [ProblemID]) VALUES (1020, N'anxiety_description.png', N'A mental health condition characterized by severe sensations of worry, anxiety, or fear that interfere with daily activities. It is typical to have some uneasiness. You''ll feel anxious or nervous in the event that you have got to handle an issue at school, take a test or make an vital choice. And uneasiness can indeed be useful. For illustration, uneasiness helps us take note perilous circumstances and centres our consideration, so we remain secure.In case you have got a panic disorder, you get strongly, sudden panic attacks. These attacks frequently highlight more grounded, more seriously sentiments than other sorts of anxiety disorders. The feelings of fear may begin suddenly and suddenly, or they may come from a trigger, like confronting a circumstance you fear. Panic attacks can take after heart assaults. In case there is any chance you are encountering a heart attack, go to the crisis room. It is way better to err on the side of caution and have a healthcare proficient check you.', 5)
SET IDENTITY_INSERT [dbo].[Images] OFF

/*Populate Answers Table*/
SET IDENTITY_INSERT [dbo].[Answers] ON
INSERT INTO [dbo].[Answers] ([AnswerID], [Answer], [AnswerValue], [QuestionID], [Position]) VALUES (6, N'I feel very happy', 5, 1, 1)
INSERT INTO [dbo].[Answers] ([AnswerID], [Answer], [AnswerValue], [QuestionID], [Position]) VALUES (7, N'I feel happy', 4, 1, 2)
INSERT INTO [dbo].[Answers] ([AnswerID], [Answer], [AnswerValue], [QuestionID], [Position]) VALUES (8, N'I feel normal', 3, 1, 3)
INSERT INTO [dbo].[Answers] ([AnswerID], [Answer], [AnswerValue], [QuestionID], [Position]) VALUES (9, N'I feel sad', 2, 1, 4)
INSERT INTO [dbo].[Answers] ([AnswerID], [Answer], [AnswerValue], [QuestionID], [Position]) VALUES (10, N'I feel very sad', 1, 1, 5)
INSERT INTO [dbo].[Answers] ([AnswerID], [Answer], [AnswerValue], [QuestionID], [Position]) VALUES (11, N'I rarely eat', 1, 2, 0)
INSERT INTO [dbo].[Answers] ([AnswerID], [Answer], [AnswerValue], [QuestionID], [Position]) VALUES (12, N'I eat when I feel hungry', 1, 2, 1)
INSERT INTO [dbo].[Answers] ([AnswerID], [Answer], [AnswerValue], [QuestionID], [Position]) VALUES (13, N'I eat normally', 1, 2, 2)
INSERT INTO [dbo].[Answers] ([AnswerID], [Answer], [AnswerValue], [QuestionID], [Position]) VALUES (14, N'I eat all the time', 1, 2, 3)
INSERT INTO [dbo].[Answers] ([AnswerID], [Answer], [AnswerValue], [QuestionID], [Position]) VALUES (19, N'I do not feel sad', 0, 4, 0)
INSERT INTO [dbo].[Answers] ([AnswerID], [Answer], [AnswerValue], [QuestionID], [Position]) VALUES (20, N'I feel sad', 0, 4, 1)
INSERT INTO [dbo].[Answers] ([AnswerID], [Answer], [AnswerValue], [QuestionID], [Position]) VALUES (21, N'Very sad', 0, 4, 2)
INSERT INTO [dbo].[Answers] ([AnswerID], [Answer], [AnswerValue], [QuestionID], [Position]) VALUES (22, N'So sad and tired', 0, 4, 3)
SET IDENTITY_INSERT [dbo].[Answers] OFF

/*Populate Questions Table*/
SET IDENTITY_INSERT [dbo].[Questions] ON
INSERT INTO [dbo].[Questions] ([QuestionID], [Question], [TestID]) VALUES (1, N'How are you feeling today?', 1)
INSERT INTO [dbo].[Questions] ([QuestionID], [Question], [TestID], [Position], [Status]) VALUES (2, N'How often do you eat?', 2, 0, N'Active')
INSERT INTO [dbo].[Questions] ([QuestionID], [Question], [TestID], [Position], [Status]) VALUES (4, N'Do you feel sad?', 3004, 0, NULL)
SET IDENTITY_INSERT [dbo].[Questions] OFF

/*Populate Tests Table*/
SET IDENTITY_INSERT [dbo].[Tests] ON
INSERT INTO [dbo].[Tests] ([TestID], [Name], [Total], [PsychID], [DateCreated], [LastEdited], [Status]) VALUES (1, N'Mood Tracker', 5, NULL, N'2021-09-13 21:39:40', N'2021-09-13 21:39:40', N'Active')
INSERT INTO [dbo].[Tests] ([TestID], [Name], [Total], [PsychID], [DateCreated], [LastEdited], [Status]) VALUES (2, N'Depression Index', 50, 4, N'2021-09-13 21:39:40', N'2021-09-13 21:39:40', N'Active')
INSERT INTO [dbo].[Tests] ([TestID], [Name], [Total], [PsychID], [DateCreated], [LastEdited], [Status]) VALUES (3004, N'Anxiety Checklist', 0, 4, N'2021-09-14 00:00:00', N'2021-09-14 22:04:42', N'Active')
SET IDENTITY_INSERT [dbo].[Tests] OFF

/*ChildTest*/
SET IDENTITY_INSERT [dbo].[ChildTest] ON
INSERT INTO [dbo].[ChildTest] ([ChildTestID], [Date], [Status], [ChildID], [TestID], [NoteID]) VALUES (2, N'2021-07-18 00:00:00', N'Completed', 1, 2, NULL)
INSERT INTO [dbo].[ChildTest] ([ChildTestID], [Date], [Status], [ChildID], [TestID], [NoteID]) VALUES (2002, N'2021-04-28 00:00:00', N'Completed', 1, 2, NULL)
INSERT INTO [dbo].[ChildTest] ([ChildTestID], [Date], [Status], [ChildID], [TestID], [NoteID]) VALUES (2004, N'2021-09-14 21:37:02', N'Completed', 3003, 1, NULL)
INSERT INTO [dbo].[ChildTest] ([ChildTestID], [Date], [Status], [ChildID], [TestID], [NoteID]) VALUES (2006, N'2021-09-14 22:02:08', N'Completed', 3007, 1, NULL)
INSERT INTO [dbo].[ChildTest] ([ChildTestID], [Date], [Status], [ChildID], [TestID], [NoteID]) VALUES (2007, N'2021-09-14 22:07:32', N'Completed', 3007, 3004, NULL)
INSERT INTO [dbo].[ChildTest] ([ChildTestID], [Date], [Status], [ChildID], [TestID], [NoteID]) VALUES (3003, N'2021-09-15 09:15:22', N'Completed', 4002, 1, NULL)
INSERT INTO [dbo].[ChildTest] ([ChildTestID], [Date], [Status], [ChildID], [TestID], [NoteID]) VALUES (4004, N'2021-10-01 11:01:58', N'Completed', 5015, 1, NULL)
INSERT INTO [dbo].[ChildTest] ([ChildTestID], [Date], [Status], [ChildID], [TestID], [NoteID]) VALUES (4005, N'2021-10-01 11:02:43', N'Completed', 5015, 2, NULL)
INSERT INTO [dbo].[ChildTest] ([ChildTestID], [Date], [Status], [ChildID], [TestID], [NoteID]) VALUES (4007, N'2021-10-01 11:13:40', N'Completed', 5016, 1, NULL)
INSERT INTO [dbo].[ChildTest] ([ChildTestID], [Date], [Status], [ChildID], [TestID], [NoteID]) VALUES (5004, N'2021-10-04 00:08:57', N'Completed', 6016, 1, NULL)
INSERT INTO [dbo].[ChildTest] ([ChildTestID], [Date], [Status], [ChildID], [TestID], [NoteID]) VALUES (6003, N'2021-10-04 12:06:55', N'Completed', 1, 3004, NULL)
INSERT INTO [dbo].[ChildTest] ([ChildTestID], [Date], [Status], [ChildID], [TestID], [NoteID]) VALUES (6004, N'2021-10-04 12:07:34', N'Completed', 6016, 3004, NULL)
INSERT INTO [dbo].[ChildTest] ([ChildTestID], [Date], [Status], [ChildID], [TestID], [NoteID]) VALUES (6005, N'2021-10-04 12:08:21', N'Assigned', 3003, 3004, NULL)
INSERT INTO [dbo].[ChildTest] ([ChildTestID], [Date], [Status], [ChildID], [TestID], [NoteID]) VALUES (6006, N'2021-10-04 12:11:52', N'Completed', 5, 3004, NULL)
INSERT INTO [dbo].[ChildTest] ([ChildTestID], [Date], [Status], [ChildID], [TestID], [NoteID]) VALUES (6007, N'2021-10-04 12:08:21', N'Assigned', 3007, 3004, NULL)
INSERT INTO [dbo].[ChildTest] ([ChildTestID], [Date], [Status], [ChildID], [TestID], [NoteID]) VALUES (6008, N'2021-10-04 12:08:21', N'Assigned', 4002, 3004, NULL)
INSERT INTO [dbo].[ChildTest] ([ChildTestID], [Date], [Status], [ChildID], [TestID], [NoteID]) VALUES (6009, N'2021-10-04 12:09:07', N'Completed', 5015, 3004, NULL)
INSERT INTO [dbo].[ChildTest] ([ChildTestID], [Date], [Status], [ChildID], [TestID], [NoteID]) VALUES (6010, N'2021-10-04 12:29:22', N'Completed', 1, 1, NULL)
INSERT INTO [dbo].[ChildTest] ([ChildTestID], [Date], [Status], [ChildID], [TestID], [NoteID]) VALUES (6011, N'2021-10-01 10:30:22', N'Completed', 1, 1, NULL)
INSERT INTO [dbo].[ChildTest] ([ChildTestID], [Date], [Status], [ChildID], [TestID], [NoteID]) VALUES (6012, N'2021-09-25 00:00:00', N'Completed', 1, 1, NULL)
INSERT INTO [dbo].[ChildTest] ([ChildTestID], [Date], [Status], [ChildID], [TestID], [NoteID]) VALUES (6013, N'2021-09-24 00:00:00', N'Completed', 1, 1, NULL)
INSERT INTO [dbo].[ChildTest] ([ChildTestID], [Date], [Status], [ChildID], [TestID], [NoteID]) VALUES (6014, N'2021-05-06 00:00:00', N'Completed', 1, 1, NULL)
INSERT INTO [dbo].[ChildTest] ([ChildTestID], [Date], [Status], [ChildID], [TestID], [NoteID]) VALUES (6015, N'2021-06-15 00:00:00', N'Completed', 1, 1, NULL)
INSERT INTO [dbo].[ChildTest] ([ChildTestID], [Date], [Status], [ChildID], [TestID], [NoteID]) VALUES (6016, N'2021-07-03 00:00:00', N'Completed', 1, 1, NULL)
INSERT INTO [dbo].[ChildTest] ([ChildTestID], [Date], [Status], [ChildID], [TestID], [NoteID]) VALUES (6017, N'2021-08-27 00:00:00', N'Completed', 1, 1, NULL)
INSERT INTO [dbo].[ChildTest] ([ChildTestID], [Date], [Status], [ChildID], [TestID], [NoteID]) VALUES (6019, N'2021-10-04 13:11:27', N'Completed', 6019, 1, NULL)
INSERT INTO [dbo].[ChildTest] ([ChildTestID], [Date], [Status], [ChildID], [TestID], [NoteID]) VALUES (6020, N'2021-10-04 17:38:38', N'Completed', 6022, 6012, NULL)
INSERT INTO [dbo].[ChildTest] ([ChildTestID], [Date], [Status], [ChildID], [TestID], [NoteID]) VALUES (6021, N'2021-10-04 17:38:09', N'Completed', 6022, 1, NULL)
SET IDENTITY_INSERT [dbo].[ChildTest] OFF


/*Child Answers*/
SET IDENTITY_INSERT [dbo].[ChildAnswers] ON
INSERT INTO [dbo].[ChildAnswers] ([ChildAnswerID], [ChildTestID], [AnswerID]) VALUES (2002, 2, 11)
INSERT INTO [dbo].[ChildAnswers] ([ChildAnswerID], [ChildTestID], [AnswerID]) VALUES (2003, 2004, 10)
INSERT INTO [dbo].[ChildAnswers] ([ChildAnswerID], [ChildTestID], [AnswerID]) VALUES (2005, 2006, 8)
INSERT INTO [dbo].[ChildAnswers] ([ChildAnswerID], [ChildTestID], [AnswerID]) VALUES (2006, 2007, 21)
INSERT INTO [dbo].[ChildAnswers] ([ChildAnswerID], [ChildTestID], [AnswerID]) VALUES (3002, 3003, 8)
INSERT INTO [dbo].[ChildAnswers] ([ChildAnswerID], [ChildTestID], [AnswerID]) VALUES (4004, 4005, 13)
INSERT INTO [dbo].[ChildAnswers] ([ChildAnswerID], [ChildTestID], [AnswerID]) VALUES (4005, 4007, 6)
INSERT INTO [dbo].[ChildAnswers] ([ChildAnswerID], [ChildTestID], [AnswerID]) VALUES (5002, 5004, 8)
INSERT INTO [dbo].[ChildAnswers] ([ChildAnswerID], [ChildTestID], [AnswerID]) VALUES (6002, 6003, 22)
INSERT INTO [dbo].[ChildAnswers] ([ChildAnswerID], [ChildTestID], [AnswerID]) VALUES (6003, 6004, 20)
INSERT INTO [dbo].[ChildAnswers] ([ChildAnswerID], [ChildTestID], [AnswerID]) VALUES (6004, 6009, 21)
INSERT INTO [dbo].[ChildAnswers] ([ChildAnswerID], [ChildTestID], [AnswerID]) VALUES (6005, 6006, 21)
INSERT INTO [dbo].[ChildAnswers] ([ChildAnswerID], [ChildTestID], [AnswerID]) VALUES (6006, 6010, 8)
INSERT INTO [dbo].[ChildAnswers] ([ChildAnswerID], [ChildTestID], [AnswerID]) VALUES (6007, 6011, 9)
INSERT INTO [dbo].[ChildAnswers] ([ChildAnswerID], [ChildTestID], [AnswerID]) VALUES (6008, 6012, 6)
INSERT INTO [dbo].[ChildAnswers] ([ChildAnswerID], [ChildTestID], [AnswerID]) VALUES (6009, 6013, 10)
INSERT INTO [dbo].[ChildAnswers] ([ChildAnswerID], [ChildTestID], [AnswerID]) VALUES (6010, 6014, 9)
INSERT INTO [dbo].[ChildAnswers] ([ChildAnswerID], [ChildTestID], [AnswerID]) VALUES (6011, 6015, 7)
INSERT INTO [dbo].[ChildAnswers] ([ChildAnswerID], [ChildTestID], [AnswerID]) VALUES (6012, 6016, 7)
INSERT INTO [dbo].[ChildAnswers] ([ChildAnswerID], [ChildTestID], [AnswerID]) VALUES (6013, 6017, 8)
INSERT INTO [dbo].[ChildAnswers] ([ChildAnswerID], [ChildTestID], [AnswerID]) VALUES (6014, 6019, 9)
SET IDENTITY_INSERT [dbo].[ChildAnswers] OFF



/*ChildActivity*/
SET IDENTITY_INSERT [dbo].[ChildActivity] ON
INSERT INTO [dbo].[ChildActivity] ([ActivityID], [FileName], [Date], [ChildID], [NoteID], [PsychID], [ActivityName]) VALUES (1, N'sexystalin.png', N'2021-07-29 00:00:00', 1, NULL, 4, N'a drawing')
SET IDENTITY_INSERT [dbo].[ChildActivity] OFF


/*RHubUserBridge FocusPoints*/
SET IDENTITY_INSERT [dbo].[RHubUserBridge] ON
INSERT INTO [dbo].[RHubUserBridge] ([BridgeID], [ProblemID], [UserID]) VALUES (1, 5, 1)
INSERT INTO [dbo].[RHubUserBridge] ([BridgeID], [ProblemID], [UserID]) VALUES (2, 18, 1)
INSERT INTO [dbo].[RHubUserBridge] ([BridgeID], [ProblemID], [UserID]) VALUES (4, 19, 1)
INSERT INTO [dbo].[RHubUserBridge] ([BridgeID], [ProblemID], [UserID]) VALUES (2002, 9, 1)
INSERT INTO [dbo].[RHubUserBridge] ([BridgeID], [ProblemID], [UserID]) VALUES (2003, 9, 3003)
INSERT INTO [dbo].[RHubUserBridge] ([BridgeID], [ProblemID], [UserID]) VALUES (2004, 9, 3007)
INSERT INTO [dbo].[RHubUserBridge] ([BridgeID], [ProblemID], [UserID]) VALUES (3002, 9, 4002)
INSERT INTO [dbo].[RHubUserBridge] ([BridgeID], [ProblemID], [UserID]) VALUES (4002, 14, 3003)
SET IDENTITY_INSERT [dbo].[RHubUserBridge] OFF


/*Counsellor*/
INSERT INTO [dbo].[Counsellor] ([CounsellorID], [PracticeNum], [HighestCertificate]) VALUES (3001, N'Couns123', N'Counselling')


/*Counsellor Chat*/
SET IDENTITY_INSERT [dbo].[CounsellorChat] ON
INSERT INTO [dbo].[CounsellorChat] ([CounsellorChatID], [CounsellorID], [ChildID], [Focus]) VALUES (1, 3001, 5, 8)
INSERT INTO [dbo].[CounsellorChat] ([CounsellorChatID], [CounsellorID], [ChildID], [Focus]) VALUES (3, 3001, 1, 18)
INSERT INTO [dbo].[CounsellorChat] ([CounsellorChatID], [CounsellorID], [ChildID], [Focus]) VALUES (3008, 3001, 3002, 5)
SET IDENTITY_INSERT [dbo].[CounsellorChat] OFF

/*Counsellor Chat Messages*/
SET IDENTITY_INSERT [dbo].[CounsellorChatMessages] ON
INSERT INTO [dbo].[CounsellorChatMessages] ([ChatID], [CounsellorChatID], [Date], [SenderID], [Message], [Read]) VALUES (1, 1, N'2021-09-06 10:30:00', 5, N'Hi there', 1)
INSERT INTO [dbo].[CounsellorChatMessages] ([ChatID], [CounsellorChatID], [Date], [SenderID], [Message], [Read]) VALUES (2, 3, N'2021-09-07 10:45:00', 1, N'Hi', 1)
INSERT INTO [dbo].[CounsellorChatMessages] ([ChatID], [CounsellorChatID], [Date], [SenderID], [Message], [Read]) VALUES (3, 1, N'2021-09-06 21:00:00', 3001, N'Hi, how are you?', 1)
INSERT INTO [dbo].[CounsellorChatMessages] ([ChatID], [CounsellorChatID], [Date], [SenderID], [Message], [Read]) VALUES (12, 3, N'2021-09-07 20:03:48', 3001, N'How can I help you today?', 0)
INSERT INTO [dbo].[CounsellorChatMessages] ([ChatID], [CounsellorChatID], [Date], [SenderID], [Message], [Read]) VALUES (3002, 3, N'2021-09-12 11:35:42', 1, N'I am worried about school', 1)
INSERT INTO [dbo].[CounsellorChatMessages] ([ChatID], [CounsellorChatID], [Date], [SenderID], [Message], [Read]) VALUES (3003, 3008, N'2021-09-12 11:49:19', 3002, N'Hi Rob. I need help managing my time. It makes me stressed when I have too many things to do', 1)
INSERT INTO [dbo].[CounsellorChatMessages] ([ChatID], [CounsellorChatID], [Date], [SenderID], [Message], [Read]) VALUES (3004, 3008, N'2021-09-12 11:50:50', 3002, N'Please help', 1)
INSERT INTO [dbo].[CounsellorChatMessages] ([ChatID], [CounsellorChatID], [Date], [SenderID], [Message], [Read]) VALUES (3005, 3008, N'2021-09-12 11:54:38', 3001, N'Hi Peter. I find it best to prioritse the tasks you need to complete', 0)
INSERT INTO [dbo].[CounsellorChatMessages] ([ChatID], [CounsellorChatID], [Date], [SenderID], [Message], [Read]) VALUES (3006, 3008, N'2021-09-12 14:07:27', 3002, N'What does prioritse mean?', 1)
INSERT INTO [dbo].[CounsellorChatMessages] ([ChatID], [CounsellorChatID], [Date], [SenderID], [Message], [Read]) VALUES (3007, 3008, N'2021-09-12 14:09:08', 3001, N'It means that you spend more time on the most importatnt or urgent tasks you have', 0)
INSERT INTO [dbo].[CounsellorChatMessages] ([ChatID], [CounsellorChatID], [Date], [SenderID], [Message], [Read]) VALUES (4002, 3, N'2021-09-13 11:35:05', 1, N'Hi Rob. I am being bullied at school', 1)
INSERT INTO [dbo].[CounsellorChatMessages] ([ChatID], [CounsellorChatID], [Date], [SenderID], [Message], [Read]) VALUES (4003, 3, N'2021-09-13 11:36:32', 3001, N'Ah that''s so sad to hear', 0)
SET IDENTITY_INSERT [dbo].[CounsellorChatMessages] OFF

/*Psych Chat*/
SET IDENTITY_INSERT [dbo].[Chats] ON
INSERT INTO [dbo].[Chats] ([MessageID], [LinkID], [Message], [Date], [Read], [SenderID]) VALUES (1007, 1, N'Hi Ibrahim. Just checking in: are you still coping', N'2021-08-17 23:48:27', 0, 4)
INSERT INTO [dbo].[Chats] ([MessageID], [LinkID], [Message], [Date], [Read], [SenderID]) VALUES (1008, 1, N'Hi Sue. Yes I am hanging in there', N'2021-08-17 23:52:27', 0, 1)
INSERT INTO [dbo].[Chats] ([MessageID], [LinkID], [Message], [Date], [Read], [SenderID]) VALUES (3013, 1002, N'Hi Miguel. I noticed that you missed our last meeting. Are you okay?', N'2021-09-12 18:48:26', 0, 4)
INSERT INTO [dbo].[Chats] ([MessageID], [LinkID], [Message], [Date], [Read], [SenderID]) VALUES (3014, 1002, N'Let me know if you;d like to re-schedule', N'2021-09-12 18:48:38', 0, 4)
INSERT INTO [dbo].[Chats] ([MessageID], [LinkID], [Message], [Date], [Read], [SenderID]) VALUES (3017, 1, N'How can I improve?', N'2021-09-12 21:49:23', 0, 1)
INSERT INTO [dbo].[Chats] ([MessageID], [LinkID], [Message], [Date], [Read], [SenderID]) VALUES (5002, 1002, N'Hi Sue. Yes, I am okay, it was just a busy week. Please can we re-schedule', N'2021-09-13 21:27:15', 0, 1002)
INSERT INTO [dbo].[Chats] ([MessageID], [LinkID], [Message], [Date], [Read], [SenderID]) VALUES (5003, 1, N'Not sure what you mean?', N'2021-09-14 01:01:46', 0, 4)
SET IDENTITY_INSERT [dbo].[Chats] OFF

