DELETE
FROM user_roles;
DELETE
FROM DISH;
delete
from VOTE;
delete
from RESTAURANT;
DELETE
FROM users;



insert into USERS(id, name, email, password)
values (10000, 'admin', 'admin@gmail.com', '{noop}admin'),
       (10001, 'user', 'user@gmail.com', '{noop}user');

INSERT INTO user_roles (role, user_id)
VALUES ('ADMIN', 10000),
       ('USER', 10001);
insert into RESTAURANT(ID, NAME)
values (10002, 'Ласточка'),
       (10003, 'СССР');
insert into VOTE
values (10004,'10002', '2020-01-30 10:00:00', '10000'),
       (10005,'10002', '2020-01-29 10:00:00', '10000'),
       (10006,'10003', '2020-01-30 10:00:00', '10001'),
       (10007,'10003', '2020-01-29 10:00:00', '10001');

INSERT INTO DISH
VALUES (10008,'супчик', '500', '10002'),
       (10009,'мяско', '500', '10002'),
       (10010,'кролик', '500', '10002'),
       (10011,'рагу', '500', '10002'),
       (10012,'еда', '500', '10003'),
       (10013,'пища', '500', '10003'),
       (10014,'ролы', '500', '10003'),
       (10015,'суши', '500', '10003');
insert into VOTE
values (10016,'10003','2020-02-29 10:00:00', '10000');


