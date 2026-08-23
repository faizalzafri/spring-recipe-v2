-- PostgreSQL Initialization Script
-- Connect as postgres superuser

-- Create Service Accounts
DO
$do$
BEGIN
   IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = 'recipe_dev_user') THEN
      CREATE USER recipe_dev_user WITH ENCRYPTED PASSWORD '1234';
   END IF;
   IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = 'recipe_prod_user') THEN
      CREATE USER recipe_prod_user WITH ENCRYPTED PASSWORD '1234';
   END IF;
END
$do$;

-- Create Databases
SELECT 'CREATE DATABASE recipe_dev OWNER recipe_dev_user'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'recipe_dev')\gexec

SELECT 'CREATE DATABASE recipe_prod OWNER recipe_prod_user'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'recipe_prod')\gexec

-- Grant Privileges
GRANT ALL PRIVILEGES ON DATABASE recipe_dev TO recipe_dev_user;
GRANT ALL PRIVILEGES ON DATABASE recipe_prod TO recipe_prod_user;