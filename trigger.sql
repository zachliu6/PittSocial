CREATE OR REPLACE FUNCTION wipeUser() RETURNS TRIGGER AS $$
    BEGIN
        DELETE FROM groupMember g WHERE g.userid = old.userid;
        DELETE FROM messagerecipient mr WHERE mr.userid = old.userid;
        DELETE FROM messages m WHERE m.fromid IS NULL AND m.touserid IS NULL;
    END
    $$;

CREATE TRIGGER dropUser AFTER DELETE ON profile
    FOR EACH ROW
    EXECUTE PROCEDURE wipeUser();

CREATE OR REPLACE FUNCTION send_msg_to_groupmembers() RETURNS TRIGGER AS $$
    DECLARE
    group_members_cursor CURSOR
        FOR SELECT userid
            FROM groupmember gm
            where gm.gid = new.togroupid;
    user_in_group profile%rowtype;
    BEGIN
        OPEN group_members_cursor;
        for user_in_group in group_members_cursor
        LOOP
            FETCH group_members_cursor INTO user_in_group;
           EXIT WHEN NOT FOUND;
            insert into messagerecipient
            values (new.msgid, user_in_group);
    END LOOP;
    CLOSE group_members_cursor;
    END;
    $$ language plpgsql;

CREATE TRIGGER trig_send_message_to_group
    AFTER insert ON messages
    FOR EACH ROW
    when ( new.touserid is null and new.togroupid is not null )
    EXECUTE PROCEDURE send_msg_to_groupmembers()

CREATE OR REPLACE FUNCTION sendMessage() RETURNS TRIGGER AS $$
    BEGIN
	IF new.toGroupID IS NULL THEN
       	 insert into messageRecipient(msgID, userID)
         values (new.msgID, new.toUserID);
	END IF;
    END;
    $$ language plpgsql;

CREATE TRIGGER addMessageRecipient AFTER INSERT ON messages
    FOR EACH ROW
    EXECUTE PROCEDURE sendMessage();
