-- See https://learn.microsoft.com/de-de/sql/relational-databases/system-stored-procedures/sp-rename-transact-sql

-- Rename constraints for User table
EXEC sp_rename 'simple_shop.CHECK_USER_LAST_CHANGED_BY', 'CHECK_User_LastChangedBy', 'OBJECT';

-- Rename constraint for UserHistoryState table
EXEC sp_rename 'simple_shop.ID_CHECK', 'CHECK_UserHistoryState_UserId', 'OBJECT';
EXEC sp_rename 'simple_shop.CHECK_USER_HISTORY_LAST_CHANGED_BY', 'CHECK_UserHistoryState_LastChangedBy', 'OBJECT';