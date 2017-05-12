
-- update session
UPDATE ar.session
SET
updated = now()
WHERE id = 0;
