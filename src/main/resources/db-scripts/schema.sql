DROP TABLE IF EXISTS auth_history;
DROP TABLE IF EXISTS accounts_roles;
DROP TABLE IF EXISTS platform_accounts;
DROP TABLE IF EXISTS social_accounts;
DROP TABLE IF EXISTS accounts;
DROP TYPE IF EXISTS status;
DROP TYPE IF EXISTS role;
DROP TYPE IF EXISTS platform;
DROP TYPE IF EXISTS social;


CREATE TYPE status as ENUM  (
    'ACTIVE', 'INACTIVE', 'SUSPENDED', 'BANNED'
    );

CREATE TYPE role as ENUM (
    'USER', 'SUPPORT', 'ADMIN', 'DEVELOPER'
    );

CREATE TYPE platform as ENUM (
    'PSN', 'XBOX', 'ACTI_BLIZZARD'
    );

CREATE TYPE social as ENUM (
    'TWITTER', 'DISCORD'
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

CREATE TABLE social_accounts (
    account_id uuid NOT NULL,
    social social NOT NULL,
    social_username varchar(50) NOT NULL,
    UNIQUE (account_id, social),
    CONSTRAINT fk_account
        FOREIGN KEY (account_id)
            REFERENCES accounts (id)
                ON DELETE CASCADE
);

CREATE TABLE auth_history (
    id uuid PRIMARY KEY,
    account_id uuid NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    ip_address INET NOT NULL,
    country varchar(20),
    user_agent varchar(150) NOT NULL,
    successful boolean NOT NULL,
    CONSTRAINT fk_account
        FOREIGN KEY (account_id)
            REFERENCES accounts(id)
            ON DELETE CASCADE
);