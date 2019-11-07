CREATE TABLE groupMember(
    gID integer,
    userID integer,
    role varchar(20),
    CONSTRAINT groupMember_pk PRIMARY KEY (gID, userID)
);

CREATE TABLE pendingGroupMemeber(
    gID integer,
    userID integer,
    message varchar(200),
    CONSTRAINT pendingGroupMemeber_pk PRIMARY KEY (gID, userID)
);