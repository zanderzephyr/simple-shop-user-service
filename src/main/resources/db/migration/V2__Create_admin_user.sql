INSERT INTO [User]
(UserName, FirstName, LastName, Email, UserType, LastChange, PasswordHash)
VALUES
('admin', 'Mr', 'Admin', 'op@simple-shop.com', 'ADMIN', '2026-08-20 10:00:00.0000000','$argon2id$v=19$m=16384,t=2,p=1$wmi6dhXWFy7bU+J4os+5fA$ETWuswBDYiiuFXkiKKXOOX22SfFtWXXAm6D74PQDiCk');

INSERT INTO UserHistoryState
(UserId, UserName, FirstName, LastName, Email, UserType, LastChange, Action, Version)
VALUES
(1, 'admin', 'Mr', 'Admin', 'op@simple-shop.com', 'ADMIN', '2026-08-20 10:00:00.0000000', 'CREATE', 0)
;
