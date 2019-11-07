CREATE TABLE groupMember(
    gID integer,
    userID integer,
    role varchar(20),
    PRIMARY KEY (gID, userID),
    FOREIGN KEY (gID) REFERENCES group(gID),
    FOREIGN KEY (userID) REFERENCES profile(userID)
);

CREATE TABLE pendingGroupMember(
    gID integer,
    userID integer,
    message varchar(200),
    PRIMARY KEY (gID, userID),
    FOREIGN KEY (gID) REFERENCES group(gID),
    FOREIGN KEY (userID) REFERENCES profile(userID)
);

create table profile
(
    userID  int,
    name    varchar(50),
    email   varchar(50),
    password varchar(50),
    date_of_birth   date,
    lastlogin   timestamp,
    Constraint PROFILE_PK primary key (userID)
);

create table friend(
    userID1  int,
    userID2  int,
    JDate    date,
    message  varchar(200),
    Constraint friend_PK primary key (userID1, userID2)
);

create table pendingFiend(
    fromID    int,
    toID      int,
    message varchar(200),
    CONSTRAINT pendingFiend_PK primary key (fromID, toID)
);
