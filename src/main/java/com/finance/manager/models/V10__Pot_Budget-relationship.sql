ALTER TABLE pots
    ADD budget_id BIGINT;

ALTER TABLE pots
    ADD CONSTRAINT uc_pots_userid_name UNIQUE (user_id, name);

ALTER TABLE pots
    ADD CONSTRAINT FK_POTS_ON_BUDGET FOREIGN KEY (budget_id) REFERENCES budgets (id);

ALTER TABLE pots
    ALTER COLUMN user_id SET NOT NULL;