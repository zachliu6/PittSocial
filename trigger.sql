CREATE OR REPLACE FUNCTION wipeUser() RETURNS TRIGGER AS $$
    BEGIN
        DELETE FROM "group" WHERE old.userid = userid;
    END
    $$;

CREATE TRIGGER dropUser AFTER DELETE ON profile
    FOR EACH ROW
    EXECUTE PROCEDURE wipeUser()