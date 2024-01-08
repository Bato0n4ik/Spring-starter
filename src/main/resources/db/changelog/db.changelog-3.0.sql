--liquibase formatted sql

--changeset andreev:1
ALTER TABLE users ADD COLUMN image VARCHAR(64);


--changeset andreev:2
ALTER TABLE users_aud ADD COLUMN image VARCHAR(64);