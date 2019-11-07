CREATE TABLE groupMember(
    gID integer,
    userID integer,
    role varchar(20),
    CONSTRAINT groupMember_pk PRIMARY KEY (gID, userID)
);

CREATE TABLE pendingGroupMember(
    gID integer,
    userID integer,
    message varchar(200),
    CONSTRAINT pendingGroupMember_pk PRIMARY KEY (gID, userID)
);