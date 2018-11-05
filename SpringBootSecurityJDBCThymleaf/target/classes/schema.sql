create table APP_USER
(
  USER_ID integer not null,
  USER_NAME varchar(255) not null,
  ENCRYTED_PASSWORD varchar(600) not null,
  ENABLED integer not null,
 primary key(USER_ID),
 unique(USER_NAME)
) ;


create table APP_ROLE
(
  ROLE_ID   integer not null,
  ROLE_NAME varchar(255) not null,
  primary key (ROLE_ID),
  unique (ROLE_NAME)
) ;

create table USER_ROLE
(
  ID      integer not null,
  USER_ID integer not null,
  ROLE_ID integer not null,
   primary key (ID),
  unique (USER_ID, ROLE_ID),
 foreign key (USER_ID) references APP_USER (USER_ID),
foreign key (ROLE_ID) references APP_ROLE (ROLE_ID)
);

CREATE TABLE Persistent_Logins (
 
    username varchar(64) not null,
    series varchar(64) not null,
    token varchar(64) not null,
    last_used timestamp not null,
    PRIMARY KEY (series)
     
);