-- Ensure we are operating at the master server level to create logins
USE master;
GO

-- Create the database container if it doesn't exist yet
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'simple_shop_users')
BEGIN
    CREATE DATABASE simple_shop_users;
END
GO

-- Create the server-Level logins (Authentication)
-- This creates the credentials that match the environment variables
CREATE LOGIN simple_shop_user_service_db_admin 
    WITH PASSWORD = 'YourAdminSecurePassword123!', 
    DEFAULT_DATABASE = simple_shop_users;
GO

CREATE LOGIN simple_shop_user_service_db_user 
    WITH PASSWORD = 'YourUserSecurePassword123!', 
    DEFAULT_DATABASE = simple_shop_users;
GO

-- Switch into the shop database context
USE simple_shop_users;
GO

-- Map the logins to DB-level users (Authorization)
CREATE USER simple_shop_user_service_db_admin 
    FOR LOGIN simple_shop_user_service_db_admin
    WITH DEFAULT_SCHEMA = simple_shop;
GO

CREATE USER simple_shop_user_service_db_user 
    FOR LOGIN simple_shop_user_service_db_user
    WITH DEFAULT_SCHEMA = simple_shop;
GO

-- Grant DDL permissions to the Flyway admin user
-- In SQL Server, the built-in 'db_owner' role gives full schema management 
-- authority (CREATE, ALTER, DROP, TRUNCATE) over this specific database.
ALTER ROLE db_owner ADD MEMBER simple_shop_user_service_db_admin;
GO

CREATE SCHEMA simple_shop AUTHORIZATION simple_shop_user_service_db_admin;
GO

-- Grant DML permissions to the regular user for the 'simple_shop' schema ONLY
-- Instead of giving global db_datareader/writer roles, we strictly grant permissions
-- for the custom schema namespace.
GRANT SELECT, INSERT, UPDATE, DELETE ON SCHEMA::simple_shop TO simple_shop_user_service_db_user;
GO