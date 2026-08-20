INSERT INTO [User]
(UserName, FirstName, LastName, Email, UserType, LastChange, PasswordHash)
VALUES
('admin', 'Mr', 'Admin', 'op@simple-shop.com', 'ADMIN', '2026-08-20 10:00:00.0000000','$argon2id$v=19$m=16384,t=2,p=1$JubIa9wyKLHRFWboySxCdw$zgTyeM0ODVluhr0I7bijXi8VV5TlYBFNYD3/PMIgw5E');

INSERT INTO UserHistoryState
(UserId, UserName, FirstName, LastName, Email, UserType, LastChange, Action, Version)
VALUES
(1, 'admin', 'Mr', 'Admin', 'op@simple-shop.com', 'ADMIN', '2026-08-20 10:00:00.0000000', 'CREATE', 0)
;
