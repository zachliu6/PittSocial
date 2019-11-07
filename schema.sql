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

CREATE TABLE messages(
    msgID integer,
    fromID integer,
    message varchar(200),
    toUserID integer,
    toGroupID integer,
    timeSent timestamp,
    CONSTRAINT messages_pk primary key (msgID),
    CONSTRAINT messages_fk foreign key (fromID, toUserID) references profile(userID)
);

CREATE TABLE messageRecipient(
    msgID integer,
    userID integer,
    CONSTRAINT messageRecipient_pk primary key (msgID, userID),
    CONSTRAINT messages_fk foreign key (msgID) references messages(msgID),
    CONSTRAINT messages_fk foreign key (userID) references profile(userID)
);

CREATE TABLE group(
    gID integer,
    name varchar(50),
    limit integer,
    description varchar(200),
    CONSTRAINT group_pk primary key (gID),
);
