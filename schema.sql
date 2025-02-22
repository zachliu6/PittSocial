
create table profile
(
    userID  SERIAL,
    name    varchar(50),
    email   varchar(50) UNIQUE,
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
    Constraint friend_PK primary key (userID1, userID2),
    CONSTRAINT friend_FK foreign key (userID1) references profile(userID),
    constraint friend_FK_2 foreign key (userID2) references profile(userID)
);

create table pendingFriend(
    fromID    int,
    toID      int,
    message varchar(200),
    CONSTRAINT pendingFriend_PK primary key (fromID, toID),
    CONSTRAINT pendingFriend_FK foreign key (fromID) references profile(userID),
    constraint pendingFriend_FK_2 foreign key (toID) references profile(userID)
);

CREATE TABLE messageInfo(
    msgID SERIAL PRIMARY KEY,
    fromID integer,
    message varchar(200),
    toUserID integer,
    toGroupID integer,
    timeSent timestamp,
    CONSTRAINT messages_fk foreign key (fromID) references profile (userID),
    constraint messages_fk_2 foreign key (toUserID) references profile (userID)
);

CREATE TABLE messageRecipient(
    msgID integer,
    userID integer,
    CONSTRAINT messageRecipient_pk primary key (msgID, userID),
    CONSTRAINT messagesRecipient_fk foreign key (msgID) references messageInfo(msgID),
    CONSTRAINT messagesRecipient_fk_2 foreign key (userID) references profile(userID)
);

CREATE TABLE groupInfo(
    gID SERIAL,
    name varchar(50) UNIQUE,
    size integer,
    description varchar(200),
    CONSTRAINT group_pk primary key (gID)
);

CREATE TABLE groupMember(
    gID integer,
    userID integer,
    role varchar(20),
    PRIMARY KEY (gID, userID),
    CONSTRAINT groupMember_fk1 FOREIGN KEY (gID) REFERENCES groupinfo(gID),
    CONSTRAINT groupMember_fk2 FOREIGN KEY (userID) REFERENCES profile(userID)
);

CREATE TABLE pendingGroupMember(
    gID integer,
    userID integer,
    message varchar(200),
    PRIMARY KEY (gID, userID),
    CONSTRAINT pendingGroupMember_fk1 FOREIGN KEY (gID) REFERENCES groupinfo(gID),
    CONSTRAINT pendingGroupMember_fk2 FOREIGN KEY (userID) REFERENCES profile(userID)
);


