drop database ar;
create database ar;
use ar;
set time_zone = "+00:00";

create table if not exists user
(
  id int(10) unsigned not null auto_increment,
  name varchar(128) collate utf8mb4_unicode_ci not null,
  password varchar(256) collate utf8mb4_unicode_ci not null,
  created datetime not null default current_timestamp,
  lastonline datetime default current_timestamp,
  primary key (id),
  unique key name_uniq (name)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_unicode_ci;


create table session
(
  id int(10) unsigned not null auto_increment,
  user int(11) unsigned not null,
  begin datetime not null default current_timestamp,
  updated datetime not null default current_timestamp,
  primary key (id),
  foreign key user_fk (user) references user(id)
) engine=innodb default charset=utf8mb4 collate=utf8mb4_unicode_ci;


create table itemname
(
  name varchar(128) collate utf8mb4_unicode_ci not null,
  primary key (name)
);

create table prefix
(
  id int(10) unsigned not null auto_increment,
  name varchar(128) collate utf8mb4_unicode_ci not null,
  primary key (id)
);

create table suffix
(
  id int(10) unsigned not null auto_increment,
  name varchar(128) collate utf8mb4_unicode_ci not null,
  primary key (id)
);

create table effect
(
  name varchar(128) collate utf8mb4_unicode_ci not null,
  primary key (name)
);

create table itemtype
(
  name varchar(128) collate utf8mb4_unicode_ci not null,
  primary key (name)
);

create table attributetype
(
  name varchar(128) collate utf8mb4_unicode_ci not null,
  primary key (name)
);

create table item_has_types
(
  item varchar(128) collate utf8mb4_unicode_ci not null,
  itemtype varchar(128) collate utf8mb4_unicode_ci not null,
  primary key (item, itemtype),
  foreign key (item) references itemname(name),
  foreign key (itemtype) references itemtype(name)
);

create table item
(
  id int(10) unsigned not null auto_increment,
  owner int(10) unsigned not null,
  name varchar(128) collate utf8mb4_unicode_ci not null,
  prefix int(10) unsigned not null,
  suffix int(10) unsigned not null,
  primary key (id),
  foreign key (owner) references user(id),
  foreign key (name) references itemname(name),
  foreign key (prefix) references prefix(id),
  foreign key (suffix) references suffix(id)
);

create table attribute
(
  id int(10) unsigned not null auto_increment,
  value int(10) not null,
  min int(10) not null,
  max int(10) not null,
  name varchar(128) collate utf8mb4_unicode_ci not null,
  primary key (id),
  foreign key (name) references attributetype(name)
);

create table prefix_gives
(
  prefix int(10) unsigned not null auto_increment,
  attribute int(10) unsigned not null,
  primary key (prefix, attribute),
  foreign key (prefix) references prefix(id),
  foreign key (attribute) references attribute(id)
);

create table suffix_gives
(
  suffix int(10) unsigned not null auto_increment,
  attribute int(10) unsigned not null,
  primary key (suffix, attribute),
  foreign key (suffix) references suffix(id),
  foreign key (attribute) references attribute(id)
);

create table itemtype_gives
(
  itemtype varchar(128) collate utf8mb4_unicode_ci not null,
  attribute int(10) unsigned not null,
  primary key (itemtype, attribute),
  foreign key (itemtype) references itemtype(name),
  foreign key (attribute) references attribute(id)
);

create table attribute_has_effect
(
  attribute int(10) unsigned not null auto_increment,
  effect varchar(128) collate utf8mb4_unicode_ci not null,
  primary key (attribute, effect),
  foreign key (attribute) references attribute(id),
  foreign key (effect) references effect(name)
);

create table item_has_upgrades
(
  item int(10) unsigned not null,
  attribute int(10) unsigned not null,
  rank int(10) not null,
  primary key (item, attribute),
  foreign key (item) references item(id),
  foreign key (attribute) references attribute(id)
);
