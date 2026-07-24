-- Switch to master level to perform database management
USE master;
GO

-- Kill any active connection to prevent "Database is in use" errors
IF EXISTS (SELECT * FROM sys.databases WHERE name = 'simple_shop_users')
BEGIN
    ALTER DATABASE simple_shop_users SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
END
GO

-- Drop the database container (wipes out tables, schemas, and users)
DROP DATABASE IF EXISTS simple_shop_users;
GO

-- Delete the server-level authentication logins
IF EXISTS (SELECT * FROM sys.server_principals WHERE name = 'simple_shop_user_service_db_admin')
BEGIN
    DROP LOGIN simple_shop_user_service_db_admin;
END
GO
IF EXISTS (SELECT * FROM sys.server_principals WHERE name = 'simple_shop_user_service_db_user')
BEGIN
    DROP LOGIN simple_shop_user_service_db_user;
END
GO