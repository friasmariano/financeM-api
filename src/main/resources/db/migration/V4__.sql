ALTER TABLE persons
    ADD CONSTRAINT uc_persons_email UNIQUE (email);

ALTER TABLE persons
    ALTER COLUMN email SET NOT NULL;