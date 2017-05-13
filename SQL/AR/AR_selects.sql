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


commit;