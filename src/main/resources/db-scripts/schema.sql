DROP TABLE IF EXISTS accounts_roles;
DROP TABLE IF EXISTS platform_accounts;
DROP TABLE IF EXISTS accounts;
DROP TYPE IF EXISTS status;
DROP TYPE IF EXISTS role;
DROP TYPE IF EXISTS platform;


CREATE TYPE status as ENUM  (
    'ACTIVE', 'INACTIVE', 'SUSPENDED', 'BANNED'
    );

CREATE TYPE role as ENUM (
    'USER', 'SUPPORT', 'ADMIN', 'DEVELOPER'
    );

CREATE TYPE platform as ENUM (
    'PSN', 'XBOX', 'ACTI_BLIZZARD'
    );

CREATE TABLE accounts (
    id uuid PRIMARY KEY,
    username VARCHAR (15) UNIQUE NOT NULL,
    password VARCHAR (60) NOT NULL,
    email VARCHAR (50) UNIQUE NOT NULL,
    profile_picture VARCHAR(50),
    verified BOOLEAN DEFAULT FALSE,
    status status NOT NULL DEFAULT 'ACTIVE'
);

CREATE TABLE accounts_roles (
  account_id uuid NOT NULL,
  role role NOT NULL,
  UNIQUE (account_id, role),
  CONSTRAINT fk_account
      FOREIGN KEY (account_id)
          REFERENCES accounts(id)
          ON DELETE CASCADE
);

CREATE TABLE platform_accounts (
    account_id        uuid         NOT NULL,
    platform       platform         NOT NULL,
    platform_username varchar(50) NOT NULL,
    UNIQUE (account_id, platform),
    CONSTRAINT fk_account
        FOREIGN KEY (account_id)
            REFERENCES accounts (id)
            ON DELETE CASCADE
);