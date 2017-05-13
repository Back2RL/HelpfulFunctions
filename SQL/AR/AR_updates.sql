set autocommit = 0;
start transaction;

-- update session
UPDATE ar.session
SET
updated = now()
WHERE 
id = 0;


commit;