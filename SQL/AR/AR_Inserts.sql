set autocommit = 0;
start transaction;

-- users
replace INTO ar.user(id,name,password,created)
VALUES
(default,'Back2RL',md5('password'),default);

-- Itemtypes
replace INTO ar.itemtype (name,parenttype)VALUES('Weapon',null);
replace INTO ar.itemtype (name,parenttype)VALUES('Engine',null);
replace INTO ar.itemtype (name,parenttype)VALUES('Armor',null);
replace INTO ar.itemtype (name,parenttype)VALUES('Shield',null);
replace INTO ar.itemtype (name,parenttype)VALUES('Radar',null);
replace INTO ar.itemtype (name,parenttype)VALUES('Resource',null);
replace INTO ar.itemtype (name,parenttype)VALUES('Upgrade',null);
replace INTO ar.itemtype (name,parenttype)VALUES('Missile','Weapon');

-- prefixes
replace INTO ar.prefix(id,name)VALUES(default,'Garuda');

-- suffixes

replace INTO ar.suffix(id,name)VALUES(default,'of Garuda');


commit;