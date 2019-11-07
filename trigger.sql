CREATE OR REPLACE FUNCTION wipeUser() RETURNS TRIGGER AS $$
    BEGIN
        DELETE FROM groupMember g WHERE g.userid = old.userid;
    END
    $$;

CREATE TRIGGER dropUser AFTER DELETE ON profile
    FOR EACH ROW
    EXECUTE PROCEDURE wipeUser()