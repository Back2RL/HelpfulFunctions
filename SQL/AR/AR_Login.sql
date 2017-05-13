set autocommit = 0;
start transaction;

-- get user id after login -> result must be 1 line
SELECT user.id
FROM ar.user
where
user.name = 'back2rl'
and user.password = md5('password');

-- check if user is already logged in -> result must be empty
SELECT session.id
FROM ar.session
where ar.session.user = 1
and ar.session.ended is null;

-- create new session for user from userid, ip, sessionkey and current time
INSERT INTO ar.session(id,ip,sessionkey,user,begin,updated,ended)
VALUES
(default,'127.0.0.1',md5('somerandomkey'),1,default,default,null);


commit;
rollback;