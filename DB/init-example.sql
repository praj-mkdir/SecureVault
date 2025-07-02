-- Run this script to create the DB and user.
-- Create a new database user
CREATE USER <User-name-here> WITH PASSWORD '<User-password-here>'; -- change the password while going live

-- Create the securevault database
CREATE DATABASE <Database-name-here> OWNER <User-name-here>;

-- Grant all privileges on the database to the user
GRANT ALL PRIVILEGES ON DATABASE <Database-name-here> TO <User-name-here>;



