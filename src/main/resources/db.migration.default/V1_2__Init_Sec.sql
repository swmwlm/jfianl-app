-- create role--

INSERT INTO sec_role(name, value, intro, pid,left_code,right_code,created_at)
VALUES ('超级管理员','R_ADMIN','',0,1,8, current_timestamp),
       ('系统管理员','R_MANAGER','',1,2,7,current_timestamp),
       ('会员','R_MEMBER','',2,3,4,current_timestamp),
       ('普通用户','R_USER','',2,5,6,current_timestamp);

-- create permission--
INSERT INTO sec_permission(name, value, url, intro,pid,left_code,right_code, created_at)
VALUES ('管理员目录','P_D_ADMIN','/admin/**','',0,1,6,current_timestamp),
       ('角色权限管理','P_ROLE','/admin/role/**','',1,2,3,current_timestamp),
       ('用户管理','P_USER','/admin/user/**','',1,4,5,current_timestamp),
       ('会员目录','P_D_MEMBER','/member/**','',0,9,10,current_timestamp),
       ('普通用户目录','P_D_USER','/user/**','',0,11,12,current_timestamp);


INSERT INTO sec_role_permission(role_id, permission_id)
VALUES (1,1),(1,2),
       (1,3),(1,4),
       (1,5),

       (2,1),(2,3),
       (2,4),(2,5),

       (3,4),(3,5),

       (4,5);

INSERT INTO sec_menu(name, icon, url, intro,pid,left_code,right_code, created_at)
VALUES ('Center','user','/center','User Center',0,1,2,current_timestamp);

-- user data--
-- create  admin--
INSERT INTO sec_user(username, providername, email, mobile, password, hasher, salt, avatar_url, first_name, last_name, full_name,department_id, created_at)
VALUES ('admin','dreampie','wangrenhui1990@gmail.com','18611434500','$shiro1$SHA-256$500000$ZMhNGAcL3HbpTbNXzxxT1Q==$wRi5AF6BK/1FsQdvISIY1lJ9Rm/aekBoChjunVsqkUU=','default_hasher','','','仁辉','王','仁辉·王',1,current_timestamp),
       ('liujintong','dreampie','liujintong1000@gmail.com','18511400000','$shiro1$SHA-256$500000$ZMhNGAcL3HbpTbNXzxxT1Q==$wRi5AF6BK/1FsQdvISIY1lJ9Rm/aekBoChjunVsqkUU=','default_hasher','','','金彤','刘','金彤·刘',2,current_timestamp);

-- create user_info--
INSERT INTO sec_user_info(user_id, creator_id, gender,province_id,city_id,county_id,street,created_at)
VALUES (1,0,0,1,2,3,'人民大学',current_timestamp),
       (2,0,0,1,2,3,'人民大学',current_timestamp);

-- create user_role--
INSERT INTO sec_user_role(user_id, role_id)
VALUES (1,1),(2,2);