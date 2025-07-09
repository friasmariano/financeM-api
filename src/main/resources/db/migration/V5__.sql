ALTER TABLE pots
    ADD CONSTRAINT uc_pots_userid_name UNIQUE (user_id, name);

ALTER TABLE persons
    ALTER COLUMN email SET NOT NULL;

ALTER TABLE pots
    ALTER COLUMN user_id SET NOT NULL;