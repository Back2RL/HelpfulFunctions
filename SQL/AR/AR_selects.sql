set autocommit = 0;
start transaction;

-- get user id after login
SELECT user.id
FROM ar.user
where
user.name = 'back2rl'
and user.password = md5('password');

SELECT session.id
FROM ar.session
where ar.session.user = 0;

-- get Inventoryitems
select * 
from user as player 
left join session on user = player.id 
left join item as it on it.owner = player.id 
left join prefix as pre on prefix = pre.id 
left join suffix as suf on suffix = suf.id
left join item_has_types as ity on it.id = ity.item;


commit;